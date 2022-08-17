package com.currency.exchangerate.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface GiphyService {
    ResponseEntity<Map> getGiphy(String wordKey);
}
