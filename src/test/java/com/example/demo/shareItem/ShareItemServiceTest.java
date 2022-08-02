package com.example.demo.shareItem;

import com.example.demo.shareDataDaily.ShareDataDailyRepository;
import com.example.demo.utils.ShareObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ShareItemServiceTest {
    @Mock
    private ShareItemRepository shareItemRepository;
    private ShareItemService underTest;

    @BeforeEach
    void setUp() {

        underTest = new ShareItemService(shareItemRepository);

    }


    @Test
    void canGetAllShareItems() {
        underTest.getShareItems();
        verify(shareItemRepository).findAll();
    }

    @Test
    void testShareItemObjectMapper() throws JsonProcessingException {
        String mockData = getJson("mock-api-call-response-share-item.json");
        ShareItem actual = ShareObjectMapper.shareItemObjectMapper(mockData);
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