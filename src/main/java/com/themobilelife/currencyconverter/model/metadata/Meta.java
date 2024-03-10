package com.themobilelife.currencyconverter.model.metadata;

import com.fasterxml.jackson.annotation.*;
import java.util.Objects;

public class Meta {

    @JsonProperty("last_updated_at")
    private String  last_updated_at;

    public Meta() {}

    public Meta(String  last_updated_at) {
        this.last_updated_at = last_updated_at;
    }

    public String  getLast_updated_at() {
        return last_updated_at;
    }

    public void setLast_updated_at(String  last_updated_at) {
        this.last_updated_at = last_updated_at;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "last_updated_at=" + last_updated_at +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meta meta = (Meta) o;
        return Objects.equals(last_updated_at, meta.last_updated_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(last_updated_at);
    }

}
