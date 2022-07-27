package com.example.demo.webClient;

public enum WebClientUrlEnum
{
    BASE_URL("https://www.alphavantage.co"),
    GLOBAL_QUOTE("/query?function=GLOBAL_QUOTE&symbol={symbol}&apikey=undefined"),
    DAILY_DATA("/query?function=TIME_SERIES_DAILY&symbol={symbol}&apikey=undefined"),
    WEEKLY_DATA("/query?function=TIME_SERIES_WEEKLY&symbol={symbol}&apikey=undefined"),
    MONTHLY_DATA("/query?function=TIME_SERIES_MONTHLY&symbol={symbol}&apikey=undefined");

    private String url;

    WebClientUrlEnum(String envUrl){
        this.url = envUrl;
    }

    public String getUrl() {
        return url;
    }
}
