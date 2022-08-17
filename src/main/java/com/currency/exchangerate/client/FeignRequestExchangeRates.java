package com.currency.exchangerate.client;

import com.currency.exchangerate.model.ExchangeRates;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "RequestExchangeRates", url = "${openexchangerates.url.api}")
public interface FeignRequestExchangeRates extends RequestExchangeRates{
    @Override
    @GetMapping("/latest.json")
    ExchangeRates getCurrentRates(@RequestParam("app_id") String appId);

    @Override
    @GetMapping("/historical/{historicalDate}.json")
    ExchangeRates getHistoricalRates(@PathVariable String historicalDate, @RequestParam("app_id") String appId);
}
