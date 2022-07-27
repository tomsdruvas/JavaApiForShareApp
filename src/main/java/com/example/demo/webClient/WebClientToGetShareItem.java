package com.example.demo.webClient;


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
        String endPoint = properties.getEndPoint();

        return webClient
                .get()
                .uri(baseUrl + endPoint, symbol)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
