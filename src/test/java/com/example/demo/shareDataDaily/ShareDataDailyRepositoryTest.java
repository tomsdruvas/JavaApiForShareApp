package com.example.demo.shareDataDaily;

import com.example.demo.shareItem.ShareItem;
import com.example.demo.shareItem.ShareItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        String dailyData = """
                {
                "2022-07-29": {
                "1. open": "277.7000",
                "2. high": "282.0000",
                "3. low": "276.6300",
                "4. close": "280.7400",
                "5. volume": "32152752"
                },
                "2022-07-28": {
                "1. open": "269.7500",
                "2. high": "277.8400",
                "3. low": "267.8700",
                "4. close": "276.4100",
                "5. volume": "33459327"
                }}""";


        ShareDataDaily shareDataDaily = new ShareDataDaily("AMZN", LocalDateTime.now(), shareItem);
        underTest.save(shareDataDaily);
    }

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }


    @Test
    void itShouldFindShareItemBySymbol() {

        ShareDataDaily foundInDB = underTest.findShareDataDailyBySymbol("AMZN");
        ShareDataDaily notfoundInDB = underTest.findShareDataDailyBySymbol("MSFT");


        assertThat(foundInDB.getSymbol()).isEqualTo("AMZN");
        assertThat(notfoundInDB).isEqualTo(null);

    }

}