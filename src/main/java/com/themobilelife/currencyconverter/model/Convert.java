package com.themobilelife.currencyconverter.model;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

public class Convert {

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String currencyFrom;

    @NotNull
    private String currencyTo;

    public Convert() {}

    public Convert(BigDecimal amount, String currencyFrom, String currencyTo)
    {
        this.amount = amount;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    @Override
    public String toString()
    {
        return "Convert{" + "amount=" + amount +
                ", currencyFrom='" + currencyFrom + '\'' +
                ", currencyTo='" + currencyTo + '\'' +  '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Convert convert = (Convert) o;
        return Objects.equals(amount, convert.amount) && Objects.equals(currencyFrom, convert.currencyFrom) &&
                Objects.equals(currencyTo, convert.currencyTo);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(amount, currencyFrom, currencyTo);
    }
}
