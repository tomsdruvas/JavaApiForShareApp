package com.rateMyPortfolio.webClient;


import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebClientToGetAPI {

    private final WebClient webClient;
    private final WebClientToGetShareItemProperties properties;


    public String getShareInfoFromApiBySymbol(String symbol) {
        String baseUrl = properties.getBaseUrl();
        String endPoint = properties.getEndPoint();

        return webClient
                .get()
                .uri(baseUrl + endPoint, symbol)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
