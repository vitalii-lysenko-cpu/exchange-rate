package com.currency.exchangerate.model;

import lombok.Data;

import java.util.Map;

@Data
public class ExchangeRates {
    private String timestamp;
    private String base;
    private Map<String, Double> rates;
}

