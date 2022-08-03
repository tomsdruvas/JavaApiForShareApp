package com.example.demo.utils;

import com.example.demo.shareDataDaily.ShareDataDaily;
import com.example.demo.shareItem.ShareItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ShareObjectMapper {

    public static List<ShareDataDaily> shareDataObjectMapper(String response){
        JSONObject dailyDataJsonObj = new JSONObject(response);

        String symbolFromObj = dailyDataJsonObj.getJSONObject("Meta Data").getString("2. Symbol");
        JSONObject timeSeriesDailyJsonObj = dailyDataJsonObj.getJSONObject("Time Series (Daily)");

        List<ShareDataDaily> listOfData = timeSeriesDailyJsonObj.keySet().stream()
                .map(date -> new ShareDataDaily(symbolFromObj, date, timeSeriesDailyJsonObj.getJSONObject(date).getDouble("1. open")))
                .collect(Collectors.toList());

        return listOfData;

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
