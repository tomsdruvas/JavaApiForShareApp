package com.example.demo.shareItem;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WebClientToGetShareItem {

    private final WebClient webClient;
    private final WebClientToGetShareItemProperties properties;


    public String getShareItemFromApiBySymbol(String symbol) {
        String baseUrl = properties.getBaseUrl();

        return webClient
                .get()
                .uri(baseUrl + "/query?function=GLOBAL_QUOTE&symbol={symbol}&apikey=undefined", symbol)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
