package com.example.currencyapp;

//FOR FUTURE DEVELOPMENT
public class CurrencyResponse {
    private double value;

    public CurrencyResponse(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
