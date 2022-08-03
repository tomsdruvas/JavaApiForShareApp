package com.example.demo.utils;

import com.example.demo.shareDataDaily.ShareDataDaily;
import com.example.demo.shareItem.ShareItem;
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

    public static ShareItem shareItemObjectMapper(String response) {
        JSONObject dailyDataJsonObj = new JSONObject(response).getJSONObject("Global Quote");
        ShareItem shareItem = new ShareItem();
        shareItem.setName(dailyDataJsonObj.getString("01. symbol"));
        shareItem.setSymbol(dailyDataJsonObj.getString("01. symbol"));
        shareItem.setPrice(dailyDataJsonObj.getDouble("05. price"));
        shareItem.setUpdatedAt(LocalDateTime.now());

        return shareItem;
    }


}
