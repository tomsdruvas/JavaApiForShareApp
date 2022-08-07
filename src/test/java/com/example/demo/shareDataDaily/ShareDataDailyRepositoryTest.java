package com.example.demo.shareDataDaily;

import com.example.demo.shareItem.ShareItem;
import com.example.demo.shareItem.ShareItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;


import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        ShareDataDaily shareDataDaily = new ShareDataDaily("AMZN", Date.valueOf("2022-08-02"), 20.56, shareItem);
        ShareDataDaily shareDataDaily1 = new ShareDataDaily("AMZN", Date.valueOf("2022-08-01"), 20.51, shareItem);
        List<ShareDataDaily> listOfDailyPrices = new ArrayList<>();
        listOfDailyPrices.add(shareDataDaily);
        listOfDailyPrices.add(shareDataDaily1);

        underTest.save(shareDataDaily1);
        underTest.save(shareDataDaily);

        shareItem.setShareDataDailies(listOfDailyPrices);
        underTest2.save(shareItem);


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

    @Test
    void shareDataDailyAreUniqueByDateAndSymbol(){

        ShareItem shareItem = underTest2.findShareItemBySymbol("AMZN");
        ShareDataDaily shareDataDaily3 = new ShareDataDaily("AMZN", Date.valueOf("2022-08-02"), 20.56, shareItem);
        Exception exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            underTest.saveAndFlush(shareDataDaily3);

        });

        String expectedMessage = "could not execute statement";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void canDeleteShareData(){
        ShareDataDaily shareDataDaily = underTest.findAll().get(0);
        underTest.deleteById(shareDataDaily.getId());
        List<ShareDataDaily> foundInDB = underTest.findShareDataDailyBySymbol("AMZN");


        assertThat(foundInDB.size()).isEqualTo(1);

    }

}