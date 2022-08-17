package com.currency.exchangerate.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "giphyClient", url = "${giphy.url.general}")
public interface FeignGiphyClient extends GiphyClient {
    @Override
    @GetMapping("/random")
    ResponseEntity<Map> getRandomGiphy(
            @RequestParam("api_key") String apiKey,
            @RequestParam("tag") String tag
    );
}
