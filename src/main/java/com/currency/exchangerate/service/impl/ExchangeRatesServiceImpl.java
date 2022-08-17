package com.currency.exchangerate.service.impl;

import com.currency.exchangerate.client.RequestExchangeRates;
import com.currency.exchangerate.model.ExchangeRates;
import com.currency.exchangerate.service.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {
    private ExchangeRates previousExchangeRates;
    private ExchangeRates currentExchangeRates;
    private final RequestExchangeRates requestExchangeRates;
    @Value("${open-exchange-rates.app.id}")
    private String appId;
    @Value("${open-exchange-rates.base}")
    private String base;

    @Autowired
    public ExchangeRatesServiceImpl(RequestExchangeRates requestExchangeRates) {
        this.requestExchangeRates = requestExchangeRates;
    }

    @Override
    public List<String> availableExchangeRateCodes() {
        if (this.currentExchangeRates.getRates() != null) {
            return new ArrayList<>(this.currentExchangeRates.getRates().keySet());
        }
        return null;
    }

    @Override
    public int compareExchangeRates(String exchangeRateCode) {
        this.updateExchangeRates();
        Double previousRatioBetweenExchangeRates = this.ratioBetweenExchangeRates(this.previousExchangeRates, exchangeRateCode);
        Double currentRatioBetweenExchangeRates = this.ratioBetweenExchangeRates(this.currentExchangeRates, exchangeRateCode);
        return previousRatioBetweenExchangeRates != null && currentRatioBetweenExchangeRates != null
                ? Double.compare(previousRatioBetweenExchangeRates, currentRatioBetweenExchangeRates)
                : -2;
    }

    private Double ratioBetweenExchangeRates(ExchangeRates exchangeRates, String exchangeRateCode) {
        if (exchangeRates != null && exchangeRates.getRates() != null) {
            Map<String, Double> mapExchangeRates = exchangeRates.getRates();
            Double exchangeRateDesiredCurrency = mapExchangeRates.get(exchangeRateCode);
            Double baseExchangeRate = mapExchangeRates.get(this.base);
            if (exchangeRateDesiredCurrency != null && baseExchangeRate != null) {
                return new BigDecimal(baseExchangeRate * exchangeRateDesiredCurrency)
                        .setScale(4, RoundingMode.UP)
                        .doubleValue();
            }
        }
        return null;
    }

    @Override
    public void updateExchangeRates() {
        LocalDate historicalDate = LocalDate.now().minusDays(1);
        this.updateCurrentExchangeRates();
        this.updateHistoricalExchangeRates(historicalDate);
    }

    private void updateHistoricalExchangeRates(LocalDate historicalDate) {
        this.previousExchangeRates = requestExchangeRates.getHistoricalRates(historicalDate.toString(), appId);
    }

    private void updateCurrentExchangeRates() {
        if (this.currentExchangeRates == null) {
            this.currentExchangeRates = requestExchangeRates.getCurrentRates(this.appId);
        }
    }
}
