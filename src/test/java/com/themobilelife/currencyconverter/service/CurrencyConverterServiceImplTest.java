package com.themobilelife.currencyconverter.service;

import com.themobilelife.currencyconverter.model.Convert;
import com.themobilelife.currencyconverter.model.CurrencyData;
import com.themobilelife.currencyconverter.model.data.Currency;
import com.themobilelife.currencyconverter.service.impl.ConvertRatesServiceImpl;
import com.themobilelife.currencyconverter.service.impl.CurrencyConverterServiceImpl;
import com.themobilelife.currencyconverter.utils.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static com.themobilelife.currencyconverter.utils.Constants.FAKE_OBJ;
import static com.themobilelife.currencyconverter.utils.Constants.TEST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyConverterServiceImplTest {

    @InjectMocks
    @Spy
    private CurrencyConverterServiceImpl sut;

    @Mock
    private ConvertRatesServiceImpl convertRatesService;

    @Mock
    private Validator validator;

    @Test
    void when_currencies_are_not_available_then_error() {

        Convert convert = new Convert();
        convert.setAmount(BigDecimal.TEN);
        convert.setCurrencyFrom(TEST);
        convert.setCurrencyTo(TEST);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            sut.convert(convert);
        });

        // Assert
        assertEquals("argument \"content\" is null", exception.getMessage());
    }

    @Test
    void testFindExchangeRate() {
        CurrencyData currencyData = new CurrencyData();
        Map<String, Currency> data = new HashMap<>();
        data.put("USD", new Currency("USD", 1.0));
        currencyData.setData(data);

        double exchangeRate = sut.findExchangeRate(currencyData, "USD");

        assertEquals(1.0, exchangeRate);
    }

    @Test
    void testMapToCurrencyData() throws IOException {
        String ratesJson = "{\"data\":{\"USD\":{\"code\":\"USD\",\"value\":1.0}}}";

        CurrencyData currencyData = sut.mapToCurrencyData(ratesJson);

        assertNotNull(currencyData);
        assertEquals(1, currencyData.getData().size());
        assertTrue(currencyData.getData().containsKey("USD"));
        assertEquals(1.0, currencyData.getData().get("USD").getValue());
    }

    @Test
    void testConvert_SameCurrency() throws Exception {
        Convert convert = new Convert(new BigDecimal("100.00"), "USD", "USD");

        CurrencyData currencyData = new CurrencyData();
        Map<String, Currency> data = new HashMap<>();
        data.put("USD", new Currency("USD", 1.0));
        currencyData.setData(data);

        when(convertRatesService.findRates()).thenReturn(FAKE_OBJ);

        BigDecimal result = sut.convert(convert);

        assertEquals(new BigDecimal("100.00"), result);
    }

    @Test
    void testConvert_fromDollarToOtherCurrencies() throws Exception {
        Convert convert = new Convert(new BigDecimal("100.00"), "USD", "EUR");

        CurrencyData currencyData = new CurrencyData();
        Map<String, Currency> data = new HashMap<>();
        data.put("USD", new Currency("USD", 1.093));
        currencyData.setData(data);

        when(convertRatesService.findRates()).thenReturn(FAKE_OBJ);

        BigDecimal result = sut.convert(convert);

        assertEquals(new BigDecimal(109.3).setScale(5, RoundingMode.HALF_UP), result);
    }

    @Test
    void testConvert_fromOtherCurrenciesToDollars() throws Exception {
        Convert convert = new Convert(new BigDecimal("100.00"), "EUR", "USD");

        CurrencyData currencyData = new CurrencyData();
        Map<String, Currency> data = new HashMap<>();
        data.put("USD", new Currency("USD", 1.093));
        currencyData.setData(data);

        when(convertRatesService.findRates()).thenReturn(FAKE_OBJ);

        BigDecimal result = sut.convert(convert);

        assertEquals(new BigDecimal(91.49131).setScale(5, RoundingMode.HALF_UP),
                result.setScale(5, RoundingMode.HALF_UP));
    }

    @Test
    void testConvert_fromOtherCurrenciesToOtherCurrencies() throws Exception {
        Convert convert = new Convert(new BigDecimal("100.00"), "EUR", "ALL");

        CurrencyData currencyData = new CurrencyData();
        Map<String, Currency> data = new HashMap<>();
        data.put("USD", new Currency("USD", 1.00));
        data.put("USD", new Currency("EUR", 1.093));
        data.put("ALL", new Currency("ALL", 97.3));
        currencyData.setData(data);

        when(convertRatesService.findRates()).thenReturn(FAKE_OBJ);

        BigDecimal result = sut.convert(convert);

        assertEquals(new BigDecimal(8902.10430).setScale(5, RoundingMode.HALF_UP),
                result.setScale(5, RoundingMode.HALF_UP));
    }

}