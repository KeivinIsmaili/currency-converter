package com.themobilelife.currencyconverter.cache.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.themobilelife.currencyconverter.cache.CustomExpiry;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CaffeineConfiguration {

    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfter(new CustomExpiry<>());
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.getCache("rates");
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

}