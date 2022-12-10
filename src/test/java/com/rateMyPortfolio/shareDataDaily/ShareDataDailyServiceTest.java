package com.rateMyPortfolio.shareDataDaily;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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