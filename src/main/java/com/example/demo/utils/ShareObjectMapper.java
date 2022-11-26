package com.example.demo.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import com.example.demo.shareDataDaily.ShareDataDaily;
import com.example.demo.shareDataWeekly.ShareDataWeekly;
import com.example.demo.shareItem.ShareItem;

public class ShareObjectMapper {

    public static List<ShareDataDaily> shareDataDailyObjectMapper(String response, ShareItem shareItem){
        JSONObject dailyDataJsonObj = new JSONObject(response);

        String symbolFromObj = dailyDataJsonObj.getJSONObject("Meta Data").getString("2. Symbol");
        JSONObject timeSeriesDailyJsonObj = dailyDataJsonObj.getJSONObject("Time Series (Daily)");

        List<ShareDataDaily> listOfData = timeSeriesDailyJsonObj.keySet().stream()
                .map(date -> new ShareDataDaily(symbolFromObj, Date.valueOf(date), timeSeriesDailyJsonObj.getJSONObject(date).getBigDecimal("1. open"), shareItem))
                .sorted(Comparator.comparing(ShareDataDaily::getDate))
                .collect(Collectors.toList());

        return listOfData;

    }

    public static List<ShareDataWeekly> shareDataWeeklyObjectMapper(String response, ShareItem shareItem){
        JSONObject weeklyDataJsonObj = new JSONObject(response);

        String symbolFromObj = weeklyDataJsonObj.getJSONObject("Meta Data").getString("2. Symbol");
        JSONObject timeSeriesWeeklyJsonObj = weeklyDataJsonObj.getJSONObject("Weekly Time Series");

        return timeSeriesWeeklyJsonObj.keySet().stream()
                .map(date -> new ShareDataWeekly(symbolFromObj, Date.valueOf(date), timeSeriesWeeklyJsonObj.getJSONObject(date).getBigDecimal("1. open").setScale(5, RoundingMode.HALF_UP), shareItem))
                .sorted(Comparator.comparing(ShareDataWeekly::getDate))
                .toList();

    }

    public static ShareItem shareItemObjectMapper(String response) {
        Date date = Date.valueOf(LocalDate.now());
        JSONObject dailyDataJsonObj = new JSONObject(response).getJSONObject("Global Quote");
        ShareItem shareItem = new ShareItem();
        shareItem.setName(dailyDataJsonObj.getString("01. symbol"));
        shareItem.setSymbol(dailyDataJsonObj.getString("01. symbol"));
        shareItem.setPrice(dailyDataJsonObj.getBigDecimal("05. price"));
        shareItem.setUpdatedAt(date);

        return shareItem;
    }


}
