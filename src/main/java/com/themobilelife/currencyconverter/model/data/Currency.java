package com.themobilelife.currencyconverter.model.data;

import java.util.Objects;

public class Currency {

    private String code;
    private double value;

    public Currency() {
    }

    public Currency(String code, double value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "code='" + code + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Double.compare(currency.value, value) == 0 && Objects.equals(code, currency.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, value);
    }
}