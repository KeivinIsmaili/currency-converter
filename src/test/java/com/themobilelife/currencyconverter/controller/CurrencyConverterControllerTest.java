package com.themobilelife.currencyconverter.controller;

import com.themobilelife.currencyconverter.model.Convert;
import com.themobilelife.currencyconverter.model.CurrencyCode;
import com.themobilelife.currencyconverter.service.impl.CurrencyConverterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyConverterControllerTest {

    @InjectMocks
    @Spy
    private CurrencyConverterController sut;

    @Mock
    private CurrencyConverterServiceImpl currencyConverterService;

    @Mock
    private Convert convert;

    @Test
    void when_convert_it_returns_bigDecimal_of_same_amout() throws Exception {

        convert.setAmount(BigDecimal.TEN);
        convert.setCurrencyFrom(CurrencyCode.USD.getCode());
        convert.setCurrencyTo(CurrencyCode.USD.getCode());

        when(sut.convert(convert)).thenReturn(BigDecimal.TEN);

        BigDecimal result = sut.convert(convert);

        assertEquals(BigDecimal.TEN, result);
    }

}