package com.currency.exchangerate.controller;

import com.currency.exchangerate.service.ExchangeRatesService;
import com.currency.exchangerate.service.GiphyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MyController {
    private final ExchangeRatesService exchangeRatesService;
    private final GiphyService giphyService;
    @Value("${giphy.rich}")
    private String rich;
    @Value("${giphy.broke}")
    private String broke;
    @Value("${giphy.error}")
    private String error;

    @Autowired
    public MyController(ExchangeRatesService exchangeRatesService, GiphyService giphyService) {
        this.exchangeRatesService = exchangeRatesService;
        this.giphyService = giphyService;
    }

    @GetMapping("/getcurrencycodes")
    public List<String> getCurrencyCodes() {
        return exchangeRatesService.availableExchangeRateCodes();
    }

    @GetMapping("getgif/{resultComparedCurrencies}")
    public ResponseEntity<Map> getGif(@PathVariable @NotNull String resultComparedCurrencies) {
        int comparedKey = exchangeRatesService.compareExchangeRates(resultComparedCurrencies);
        switch (comparedKey) {
            case 1:
            case 0:
                return giphyService.getGiphy(this.rich);
            case -1:
                return giphyService.getGiphy(this.broke);
            default:
                return giphyService.getGiphy(this.error);
        }
    }
}

