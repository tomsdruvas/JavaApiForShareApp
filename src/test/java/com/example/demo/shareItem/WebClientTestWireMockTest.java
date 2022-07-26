package com.example.demo.shareItem;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;


import org.springframework.http.HttpHeaders;

import org.springframework.http.MediaType;


import java.io.IOException;
import java.io.InputStream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest()
@ExtendWith(SpringExtension.class)
public class WebClientTestWireMockTest {
    private WireMockServer wireMockServer;

    private WebClientToGetShareItem webClientToGetShareItem;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(
                WireMockConfiguration.wireMockConfig().dynamicPort());
        wireMockServer.start();

        WebClientToGetShareItemProperties properties = new WebClientToGetShareItemProperties();
        properties.setBaseUrl(wireMockServer.baseUrl());

        webClientToGetShareItem = new WebClientToGetShareItem(WebClient.create(), properties);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void test_getShareItemInfo() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode expected = mapper.readTree(getJson("mock-api-call-response.json"));

        wireMockServer.stubFor(get("/query?function=GLOBAL_QUOTE&symbol=AMZN&apikey=undefined").willReturn(
                        WireMock.aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withJsonBody(expected)
                )
        );

        String response = webClientToGetShareItem.getShareItemFromApiBySymbol("AMZN");
        JsonNode actual = mapper.readTree(response);

        assertEquals(actual, expected);
        wireMockServer.verify(getRequestedFor(urlEqualTo("/query?function=GLOBAL_QUOTE&symbol=AMZN&apikey=undefined")));
    }


    private String getJson(String path) {
        try {
            InputStream jsonStream = this.getClass().getClassLoader().getResourceAsStream(path);
            assert jsonStream != null;
            return new String(jsonStream.readAllBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


    }
}
