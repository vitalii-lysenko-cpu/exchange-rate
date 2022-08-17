package com.currency.exchangerate.client;

import com.currency.exchangerate.model.ExchangeRates;

public interface RequestExchangeRates {
    ExchangeRates getCurrentRates(String appId);

    ExchangeRates getHistoricalRates(String historicalDate, String appId);
}
