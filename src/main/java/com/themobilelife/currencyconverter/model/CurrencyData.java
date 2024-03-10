package com.themobilelife.currencyconverter.model;

import com.themobilelife.currencyconverter.model.data.Currency;
import com.themobilelife.currencyconverter.model.metadata.Meta;

import java.util.Map;
import java.util.Objects;

public class CurrencyData {

    private Meta meta;
    private Map<String, Currency> data;

    public CurrencyData() {
    }

    public CurrencyData(Meta meta, Map<String, Currency> data) {
        this.meta = meta;
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Map<String, Currency> getData() {
        return data;
    }

    public void setData(Map<String, Currency> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CurrencyData{" +
                "meta=" + meta +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyData that = (CurrencyData) o;
        return Objects.equals(meta, that.meta) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meta, data);
    }
}