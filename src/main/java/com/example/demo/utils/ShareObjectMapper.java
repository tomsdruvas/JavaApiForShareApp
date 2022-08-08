package com.example.demo.utils;

import com.example.demo.shareDataDaily.ShareDataDaily;
import com.example.demo.shareItem.ShareItem;
import org.json.JSONObject;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ShareObjectMapper {

    public static List<ShareDataDaily> shareDataObjectMapper(String response, ShareItem shareItem){
        JSONObject dailyDataJsonObj = new JSONObject(response);

        String symbolFromObj = dailyDataJsonObj.getJSONObject("Meta Data").getString("2. Symbol");
        JSONObject timeSeriesDailyJsonObj = dailyDataJsonObj.getJSONObject("Time Series (Daily)");

        List<ShareDataDaily> listOfData = timeSeriesDailyJsonObj.keySet().stream()
                .map(date -> new ShareDataDaily(symbolFromObj, Date.valueOf(date), timeSeriesDailyJsonObj.getJSONObject(date).getDouble("1. open"), shareItem))
                .sorted(Comparator.comparing(ShareDataDaily::getDate))
                .collect(Collectors.toList());

        return listOfData;

    }

    public static ShareItem shareItemObjectMapper(String response) {
        Date date = Date.valueOf(LocalDate.now());
        JSONObject dailyDataJsonObj = new JSONObject(response).getJSONObject("Global Quote");
        ShareItem shareItem = new ShareItem();
        shareItem.setName(dailyDataJsonObj.getString("01. symbol"));
        shareItem.setSymbol(dailyDataJsonObj.getString("01. symbol"));
        shareItem.setPrice(dailyDataJsonObj.getDouble("05. price"));
        shareItem.setUpdatedAt(date);

        return shareItem;
    }


}
