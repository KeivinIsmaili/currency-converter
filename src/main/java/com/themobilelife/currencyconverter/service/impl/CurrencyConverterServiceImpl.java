package com.themobilelife.currencyconverter.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.themobilelife.currencyconverter.model.Convert;
import com.themobilelife.currencyconverter.model.CurrencyCode;
import com.themobilelife.currencyconverter.model.CurrencyData;
import com.themobilelife.currencyconverter.model.data.Currency;
import com.themobilelife.currencyconverter.service.CurrencyConverterService;
import com.themobilelife.currencyconverter.utils.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    Logger logger = LogManager.getLogger(CurrencyConverterServiceImpl.class);

    private final ConvertRatesServiceImpl convertRatesService;

    private final Validator validator;

    public CurrencyConverterServiceImpl(ConvertRatesServiceImpl convertRatesService, Validator validator) {
        this.convertRatesService = convertRatesService;
        this.validator = validator;
    }

    public double findExchangeRate(CurrencyData currencyData, String currencyToConvert) {
        logger.log(Level.INFO, "Method findExchangeRate is invoked");
        Currency currencyObject = currencyData.getData().get(currencyToConvert);
        return currencyObject.getValue();
    }

    public BigDecimal convert(Convert convert) throws Exception {
        String allRates = convertRatesService.findRates();
        CurrencyData currencyData = mapToCurrencyData(allRates);

        //validate currencies
        if (!checkCurrencies(currencyData, convert)) {
            throw new IllegalArgumentException("Invalid input: Currencies are not valid");
        }

        logger.log(Level.INFO, "Method convert is invoked");

        //when currencyFrom is same as currencyTo
        if (convert.getCurrencyFrom().equals(convert.getCurrencyTo())) {
            return convert.getAmount();
        }
        //when converting from dollar to another currency
        else if (CurrencyCode.USD.getCode().equals(convert.getCurrencyFrom())) {
            double exchangeRateFromDollar = findExchangeRate(currencyData, convert.getCurrencyTo());
            return dollarsToOtherCurrencies(convert.getAmount(), BigDecimal.valueOf(exchangeRateFromDollar));
        }
        //when converting from another currency to dollars
        else if (CurrencyCode.USD.getCode().equals(convert.getCurrencyTo())) {
            double exchangeRateFromOtherCurrencyToDollar = findExchangeRate(currencyData, convert.getCurrencyFrom());
            return otherCurrenciesToDollar(convert.getAmount(), BigDecimal.valueOf(exchangeRateFromOtherCurrencyToDollar));
        }
        //when neither currency is in dollars
        else {
            return otherCurrenciesToOtherCurrencies(currencyData, convert.getCurrencyFrom(), convert.getCurrencyTo(), convert.getAmount());
        }
    }

    //convert the obj returned from the api to a Pojo
    @Override
    public CurrencyData mapToCurrencyData(String allRates) throws IOException {
        logger.log(Level.INFO, "Method mapToCurrencyData is invoked");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(allRates, CurrencyData.class);
    }

    public BigDecimal dollarsToOtherCurrencies(BigDecimal dollars, BigDecimal dollarToOtherCurrencyExchangeRate) {
        return dollars.multiply(dollarToOtherCurrencyExchangeRate);
    }

    public BigDecimal otherCurrenciesToDollar(BigDecimal otherCurrency, BigDecimal dollarToOtherCurrencyExchangeRate) {
        //Inverse exchange rate
        double inverseExchangeRate = 1 / Double.parseDouble(String.valueOf(dollarToOtherCurrencyExchangeRate));
        return otherCurrency.multiply(BigDecimal.valueOf(inverseExchangeRate));
    }

    public BigDecimal otherCurrenciesToOtherCurrencies(CurrencyData currencyData, String currencyFrom, String currencyTo, BigDecimal amount) throws Exception {
        double fromFirstCurrencyToDollars = findExchangeRate(currencyData, currencyFrom);
        BigDecimal valueOfFirsCurrencyInDollars = otherCurrenciesToDollar(amount, BigDecimal.valueOf(fromFirstCurrencyToDollars));
        double secondCurrency = findExchangeRate(currencyData, currencyTo);
        return dollarsToOtherCurrencies(valueOfFirsCurrencyInDollars, BigDecimal.valueOf(secondCurrency));
    }

    //validate if both currencies are valid
    @Override
    public boolean checkCurrencies(CurrencyData data, Convert convert) {
        logger.log(Level.INFO, "Method checkCurrencies is invoked");
        boolean isCurrencyFromAvailable = Validator.isValidCurrency(convert.getCurrencyFrom(), data);
        boolean isCurrencyToAvailable = Validator.isValidCurrency(convert.getCurrencyTo(), data);
        return isCurrencyFromAvailable && isCurrencyToAvailable ? true : false;
    }

}