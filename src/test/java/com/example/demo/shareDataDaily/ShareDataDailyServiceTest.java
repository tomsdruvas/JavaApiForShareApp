package com.example.demo.shareDataDaily;

import com.example.demo.shareItem.ShareItemRepository;
import com.example.demo.shareItem.ShareItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class ShareDataDailyServiceTest {


        @Mock
        ShareDataDailyRepository shareDataDailyRepository;
        private ShareDataDailyService underTest;

        @BeforeEach
        void setUp() {

            underTest = new ShareDataDailyService(shareDataDailyRepository);

        }


        @Test
        void canGetAllShareDataDailyPricesBySymbol() {
            underTest.getShareDataDaily("AMZN");
            verify(shareDataDailyRepository).findShareDataDailyBySymbol("AMZN");
        }
        
}