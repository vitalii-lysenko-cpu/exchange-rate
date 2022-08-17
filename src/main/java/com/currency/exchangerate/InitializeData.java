package com.currency.exchangerate;

import com.currency.exchangerate.service.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitializeData {
    private ExchangeRatesService ratesService;

    @Autowired
    public InitializeData(ExchangeRatesService ratesService) {
        this.ratesService = ratesService;
    }

    @PostConstruct
    public void firstInitializeData() {
        ratesService.updateExchangeRates();
    }
}
