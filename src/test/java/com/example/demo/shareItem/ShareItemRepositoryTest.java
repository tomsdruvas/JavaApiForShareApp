package com.example.demo.shareItem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ShareItemRepositoryTest {

    @Autowired
    private ShareItemRepository underTest;

    @BeforeEach
    void setUp() {
        ShareItem shareItem = new ShareItem("Amazon", "AMZN", 10.00, LocalDateTime.now());
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


        assertThat(foundInDB.getPrice()).isEqualTo(10);
        assertThat(notfoundInDB).isEqualTo(null);

    }

    @Test
    void itShouldSayIfShareItemExistsBySymbol() {

        boolean expectedTrue = underTest.existsBySymbol("AMZN");
        boolean expectedFalse = underTest.existsBySymbol("MSFT");

        assertThat(expectedTrue).isTrue();
        assertThat(expectedFalse).isFalse();

    }
}