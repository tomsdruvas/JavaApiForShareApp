package com.example.demo.shareItem;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


class WebClientToGetShareItemTest {

            private static MockWebServer mockWebServer;

            private WebClientToGetShareItem webClientToGetShareItem;

            @BeforeEach
            void setupMockWebServer() {
                mockWebServer = new MockWebServer();

                WebClientToGetShareItemProperties properties = new WebClientToGetShareItemProperties();
                properties.setBaseUrl(mockWebServer.url("/").url().toString());

                webClientToGetShareItem = new WebClientToGetShareItem(WebClient.create(), properties);
            }

            @AfterAll
            public static void tearDown() throws IOException {

                mockWebServer.shutdown();
            }

            @Test
            void makesTheCorrectRequest() throws InterruptedException {
                mockWebServer.enqueue(
                        new MockResponse().setResponseCode(200)
                                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .setBody(getJson("mock-api-call-response.json"))
                );

                webClientToGetShareItem.getShareItemFromApiBySymbol("AMZN");

                RecordedRequest request = mockWebServer.takeRequest();

                assertThat(request.getMethod()).isEqualTo("GET");
                assertThat(request.getPath()).isEqualTo("/query?function=GLOBAL_QUOTE&symbol=AMZN&apikey=undefined");
            }



            @Test
            void outputShouldMatchMockData() throws InterruptedException, JsonProcessingException {
                mockWebServer.enqueue(
                        new MockResponse().setResponseCode(200)
                                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .setBody(getJson("mock-api-call-response.json"))
                );

                String result = webClientToGetShareItem.getShareItemFromApiBySymbol("AMZN");
                ObjectMapper mapper = new ObjectMapper();

                assertEquals(mapper.readTree(getJson("mock-api-call-response.json")), mapper.readTree(result));

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



