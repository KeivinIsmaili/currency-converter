package com.themobilelife.currencyconverter.service;

import com.themobilelife.currencyconverter.model.Convert;
import com.themobilelife.currencyconverter.model.CurrencyData;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.math.BigDecimal;

public interface CurrencyConverterService {

    BigDecimal convert(Convert convert) throws Exception;

    CurrencyData mapToCurrencyData(String allRates) throws IOException;

    boolean checkCurrencies(CurrencyData data, Convert convert);

}
