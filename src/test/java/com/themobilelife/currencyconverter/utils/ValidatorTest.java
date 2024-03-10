package com.themobilelife.currencyconverter.utils;

import com.themobilelife.currencyconverter.model.CurrencyData;
import com.themobilelife.currencyconverter.model.data.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static com.themobilelife.currencyconverter.utils.Constants.TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ValidatorTest {

    @InjectMocks
    @Spy
    private Validator sut;

    @Test
    void when_currency_is_not_valid_then_return_false() {

        CurrencyData currencyData = new CurrencyData();
        Map<String, Currency> data = new HashMap<>();
        data.put("USD", new Currency("USD", 1.0));
        currencyData.setData(data);


        boolean isValid = Validator.isValidCurrency(TEST, currencyData);

        assertEquals(false, isValid);

    }

    @Test
    void when_currency_is_valid_then_true() {

        CurrencyData currencyData = new CurrencyData();
        Map<String, Currency> data = new HashMap<>();
        data.put("USD", new Currency("USD", 1.0));
        currencyData.setData(data);

        boolean isValid = Validator.isValidCurrency("USD", currencyData);

        assertEquals(true, isValid);

    }



}