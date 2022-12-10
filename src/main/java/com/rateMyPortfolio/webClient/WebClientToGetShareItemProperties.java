package com.rateMyPortfolio.webClient;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WebClientToGetShareItemProperties {

    private String baseUrl;
    private String endPoint;

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }


}
