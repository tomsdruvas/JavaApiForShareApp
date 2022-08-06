package com.example.demo.utils;

import com.example.demo.shareDataDaily.ShareDataDaily;
import com.example.demo.shareItem.ShareItem;
import org.junit.jupiter.api.Test;



import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class ShareObjectMapperTest {

    @Test
    void testShareDataObjectMapper() {
        String mockData = getJson("mock-api-call-response-share-data-daily.json");
        List<ShareDataDaily> listOfData;

        String mockDataShareItem = getJson("mock-api-call-response-share-item.json");
        ShareItem shareItem = ShareObjectMapper.shareItemObjectMapper(mockDataShareItem);

        listOfData = ShareObjectMapper.shareDataObjectMapper(mockData, shareItem);
        assertEquals(6, listOfData.size());

    }

    @Test
    void testShareItemObjectMapper() {
        String mockDataShareItem = getJson("mock-api-call-response-share-item.json");
        ShareItem actual = ShareObjectMapper.shareItemObjectMapper(mockDataShareItem);
        assertEquals(actual.getPrice().toString(), "121.14");
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