package com.currency.exchangerate.service;

import java.util.List;

public interface ExchangeRatesService {
    List<String> availableExchangeRateCodes();

    int compareExchangeRates(String exchangeRateCodes);

    void updateExchangeRates();
}
