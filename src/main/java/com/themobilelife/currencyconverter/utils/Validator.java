package com.themobilelife.currencyconverter.utils;

import com.themobilelife.currencyconverter.model.CurrencyData;
import org.springframework.stereotype.Service;

@Service
public class Validator {

    public static boolean isValidCurrency(String currency, CurrencyData currencyData)  {

        for (String key : currencyData.getData().keySet()) {
            if (currency.equals(key)) {
                return true;
            }
        }
        return false;

    }

}