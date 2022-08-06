package com.example.demo.shareDataDaily;

import com.example.demo.shareItem.ShareItem;
import com.example.demo.shareItem.ShareItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ShareDataDailyRepositoryTest {

    @Autowired
    private ShareDataDailyRepository underTest;

    @Autowired
    private ShareItemRepository underTest2;

    @BeforeEach
    void setUp() {
        ShareItem shareItem = new ShareItem("Amazon", "AMZN", 10.00, LocalDateTime.now());
        underTest2.save(shareItem);
        ShareDataDaily shareDataDaily1 = new ShareDataDaily("AMZN", "2022-08-01", 20.51, shareItem);
        ShareDataDaily shareDataDaily = new ShareDataDaily("AMZN", "2022-08-02", 20.56, shareItem);
        List<ShareDataDaily> listOfDailyPrices = new ArrayList<>();
        listOfDailyPrices.add(shareDataDaily);
        listOfDailyPrices.add(shareDataDaily1);

        underTest.save(shareDataDaily1);
        underTest.save(shareDataDaily);

        shareItem.setShareDataDailies(listOfDailyPrices);
        underTest2.save(shareItem);


    }

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }


    @Test
    void itShouldFindShareDataDailyBySymbol() {

        List<ShareDataDaily> foundInDB = underTest.findShareDataDailyBySymbol("AMZN");
        List<ShareDataDaily> notfoundInDB = underTest.findShareDataDailyBySymbol("MSFT");


        assertThat(foundInDB.get(0).getSymbol()).isEqualTo("AMZN");
        assertThat(foundInDB.size()).isEqualTo(2);
        assertThat(notfoundInDB.size()).isEqualTo(0);

    }

    @Test
    void shouldBeAbleToGetDataFromShareITem(){
        ShareItem shareItem = underTest2.findShareItemBySymbol("AMZN");
        List<ShareDataDaily> shareDataDaily = shareItem.getShareDataDailies();

        assertThat(shareItem.getSymbol()).isEqualTo("AMZN");
        assertThat(shareDataDaily.size()).isEqualTo(2);
    }

}