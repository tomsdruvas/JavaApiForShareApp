package com.example.demo.shareItem;

import com.example.demo.shareDataDaily.ShareDataDailyRepository;
import com.example.demo.shareDataWeekly.ShareDataWeeklyRepository;
import com.example.demo.utils.CurrencyEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.sql.Date;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ShareItemServiceTest {
    @Mock
    private ShareItemRepository shareItemRepository;
    @Mock
    private ShareDataDailyRepository shareDataDailyRepository;
    @Mock
    private ShareDataWeeklyRepository shareDataWeeklyRepository;
    private ShareItemService underTest;

    private ShareItem shareItem;

    @BeforeEach
    void setUp() {
        underTest = new ShareItemService(shareItemRepository, shareDataDailyRepository, shareDataWeeklyRepository);
        shareItem = new ShareItem("Amazon","AMZN", 30.00, CurrencyEnum.USD, Date.valueOf("2022-02-20"), LocalDateTime.now(), true);
        shareItemRepository.save(shareItem);

    }


    @Test
    void canGetAllShareItems() {
        underTest.getShareItems();
        verify(shareItemRepository).findAll();
    }

    @Test
    void canGetShareItemFromService() {
        ShareItem shareItemEntity = new ShareItem("Amazon","AMZN", 30.00, CurrencyEnum.USD, Date.valueOf("2022-02-20"), LocalDateTime.now(), true);

        doReturn(shareItemEntity).when(shareItemRepository).findShareItemBySymbol("AMZN");

        ShareItem shareItemEntityFromService = underTest.getShareItem("AMZN");

        assertThat(shareItemEntityFromService.getId()).isEqualTo(shareItemEntity.getId());
        assertThat(shareItemEntityFromService.getName()).isEqualTo(shareItemEntity.getName());
        verify(shareItemRepository).findShareItemBySymbol("AMZN");
    }



}