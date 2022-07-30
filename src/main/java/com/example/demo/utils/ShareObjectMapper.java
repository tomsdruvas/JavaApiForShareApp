package com.example.demo.utils;

import com.example.demo.shareDataDaily.ShareDataDaily;
import com.example.demo.shareItem.ShareItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public class ShareObjectMapper {

    public ShareDataDaily shareDataObjectMapper(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode shareDataObject = mapper.readTree(response);
        ShareDataDaily shareDataDaily = new ShareDataDaily();

        shareDataDaily.setSymbol(shareDataObject.get("Meta Data").get("2. Symbol").asText());
        shareDataDaily.setUpdatedAt(LocalDateTime.now());
        shareDataDaily.setDailyData(shareDataObject.get("Time Series (Daily)").asText());

        return shareDataDaily;
    }

    public static ShareItem shareItemObjectMapper(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode shareItemObject = mapper.readTree(response).get("Global Quote");
        ShareItem shareItem = new ShareItem();
        shareItem.setName(shareItemObject.get("01. symbol").asText());
        shareItem.setSymbol(shareItemObject.get("01. symbol").asText());
        shareItem.setPrice(shareItemObject.get("05. price").asDouble());
        shareItem.setUpdatedAt(LocalDateTime.now());

        return shareItem;
    }


}
