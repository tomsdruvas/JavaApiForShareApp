package com.rateMyPortfolio.shareItem;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.rateMyPortfolio.utils.CurrencyEnum;

@DataJpaTest
class ShareItemRepositoryTest {

    @Autowired
    private ShareItemRepository underTest;

    @BeforeEach
    void setUp() {
        ShareItem shareItem = new ShareItem("Amazon", "AMZN", new BigDecimal("10.00"), CurrencyEnum.USD, Date.valueOf("2022-08-02"), LocalDateTime.now(), true);
        underTest.save(shareItem);
    }

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }


    @Test
    void itShouldFindShareItemBySymbol() {

        ShareItem foundInDB = underTest.findShareItemBySymbol("AMZN");
        ShareItem notfoundInDB = underTest.findShareItemBySymbol("MSFT");


        assertThat(foundInDB.getPrice()).isEqualTo(new BigDecimal("10.00"));
        assertThat(notfoundInDB).isNull();

    }

    @Test
    void itShouldSayIfShareItemExistsBySymbol() {

        boolean expectedTrue = underTest.existsBySymbol("AMZN");
        boolean expectedFalse = underTest.existsBySymbol("MSFT");

        assertThat(expectedTrue).isTrue();
        assertThat(expectedFalse).isFalse();

    }
}