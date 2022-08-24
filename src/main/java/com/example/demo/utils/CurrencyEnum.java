package com.example.demo.utils;

public enum CurrencyEnum {

    GBX("GBX"),
    USD("USD"),
    CAD("CAD"),
    EUR("EUR"),
    BRL("BRL");

    private final String currency;

    CurrencyEnum(String currency){
        this.currency = currency;

    }

    public String getCurrency(){
        return currency;
    }
}
