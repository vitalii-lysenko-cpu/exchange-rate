package com.currency.exchangerate.client;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface GiphyClient {
    ResponseEntity<Map> getRandomGiphy(String apiKey, String tag);
}
