package com.example.demo.webClient;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebClientUrlEnumTest {

    @Test
    void getBaseUrl() {

        String actual = WebClientUrlEnum.BASE_URL.getUrl();
        String expected = "https://www.alphavantage.co";
        assertEquals(actual, expected);
    }

    @Test
    void getGlobalUrl() {

        String actual = WebClientUrlEnum.GLOBAL_QUOTE.getUrl();
        String expected = "/query?function=GLOBAL_QUOTE&symbol={symbol}&apikey=undefined";
        assertEquals(actual, expected);
    }

}