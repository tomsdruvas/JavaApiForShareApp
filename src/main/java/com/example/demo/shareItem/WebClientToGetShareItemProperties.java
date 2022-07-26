package com.example.demo.shareItem;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WebClientToGetShareItemProperties {

    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }


}
