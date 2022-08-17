package com.currency.exchangerate.service.impl;

import com.currency.exchangerate.client.GiphyClient;
import com.currency.exchangerate.service.GiphyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GiphyServiceImpl implements GiphyService {
    private final GiphyClient giphyClient;
    @Value("${giphy.api.key}")
    private String apiKey;

    @Autowired
    public GiphyServiceImpl(GiphyClient giphyClient) {
        this.giphyClient = giphyClient;
    }

    @Override
    public ResponseEntity<Map> getGiphy(String tag) {
        ResponseEntity<Map> result = giphyClient.getRandomGiphy(this.apiKey, tag);
        result.getBody().put("compareResult", tag);
        return result;
    }
}
