package com.example.demo.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.demo.shareDataDaily.ShareDataDaily;
import com.example.demo.shareDataWeekly.ShareDataWeekly;
import com.example.demo.shareItem.ShareItem;

class ShareObjectMapperTest {


    @Test
    void testShareDataWeeklyObjectMapper(){
        String mockData = getJson("mock-api-call-response-share-data-weekly.json");
        List<ShareDataWeekly> listOfData;

        String mockDataShareItem = getJson("mock-api-call-response-share-item.json");
        ShareItem shareItem = ShareObjectMapper.shareItemObjectMapper(mockDataShareItem);

        listOfData = ShareObjectMapper.shareDataWeeklyObjectMapper(mockData, shareItem);
        assertEquals(5, listOfData.size());
    }

    @Test
    void testShareDataDailyObjectMapper() {
        String mockData = getJson("mock-api-call-response-share-data-daily.json");
        List<ShareDataDaily> listOfData;

        String mockDataShareItem = getJson("mock-api-call-response-share-item.json");
        ShareItem shareItem = ShareObjectMapper.shareItemObjectMapper(mockDataShareItem);

        listOfData = ShareObjectMapper.shareDataDailyObjectMapper(mockData, shareItem);
        assertEquals(6, listOfData.size());

    }

    @Test
    void testShareItemObjectMapper() {
        String mockDataShareItem = getJson("mock-api-call-response-share-item.json");
        ShareItem actual = ShareObjectMapper.shareItemObjectMapper(mockDataShareItem);
        assertEquals(actual.getPrice().toString(), "121.1400");
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