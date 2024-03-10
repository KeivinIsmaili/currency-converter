package com.themobilelife.currencyconverter.service.impl;

import com.themobilelife.currencyconverter.service.ConvertRatesService;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import static com.themobilelife.currencyconverter.utils.Constants.CURRENCY_RATES_NOT_AVAILABLE;

@Service
public class ConvertRatesServiceImpl implements ConvertRatesService {

    Logger logger = LogManager.getLogger(ConvertRatesServiceImpl.class);

    @Value("${end-point}")
    private String currencyApiRatesEndPoint;

    @Value("${api-key}")
    private String apiKey;

    private CacheManager cacheManager;

    public ConvertRatesServiceImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*
    * Retrieving currency rates from the third party api
    * Caching value until 23:59:59 of every day, since after this time values provided by the api are refreshed
    * Using circuit breaker to handle exception
    */
    @Override
    @Cacheable(cacheNames = "rates")
    @CircuitBreaker(name = "convertRatesService", fallbackMethod = "fallbackMethod")
    public String findRates() {

        RestTemplate restTemplate = restTemplate();

        String url = UriComponentsBuilder
                .fromHttpUrl(currencyApiRatesEndPoint)
                .queryParam("apikey", apiKey)
                .toUriString();

        logger.log(Level.INFO, "Method findRates is invoked");

        return restTemplate.getForEntity(url, String.class).getBody();
    }

    //fallback method in case api doesn't return appropriate status
    public String fallbackMethod(Throwable t) {
        logger.log(Level.INFO, "Circuit Open fall back method is invoked");
        return CURRENCY_RATES_NOT_AVAILABLE;
    }

}