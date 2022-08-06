package com.example.demo.shareItem;

import com.example.demo.shareDataDaily.ShareDataDailyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ShareItemServiceTest {
    @Mock
    private ShareItemRepository shareItemRepository;
    @Mock
    ShareDataDailyRepository shareDataDailyRepository;
    private ShareItemService underTest;

    @BeforeEach
    void setUp() {

        underTest = new ShareItemService(shareItemRepository, shareDataDailyRepository);

    }


    @Test
    void canGetAllShareItems() {
        underTest.getShareItems();
        verify(shareItemRepository).findAll();
    }

}