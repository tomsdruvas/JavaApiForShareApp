package com.example.demo.shareItem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

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
    @Disabled
    void createShareItemObject() {
    }

    @Test
    @Disabled
    void httpClientToGetShareInfo() {
    }

    @Test
    void canAddShareItem() {

        ShareItem shareItem = new ShareItem("Amazon", "AMZN", 10, LocalDateTime.now());
//        underTest.save(shareItem);
    }
}