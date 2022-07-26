package com.rateMyPortfolio.shareDataDaily;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.rateMyPortfolio.shareItem.ShareItem;
import com.rateMyPortfolio.shareItem.ShareItemRepository;
import com.rateMyPortfolio.utils.CurrencyEnum;

@DataJpaTest
class ShareDataDailyRepositoryTest {

    @Autowired
    private ShareDataDailyRepository underTest;

    @Autowired
    private ShareItemRepository shareItemRepository;

    @Test
    void itShouldFindShareDataDailyBySymbol() {

        ShareItem shareItem = new ShareItem("Amazon", "AMZN", new BigDecimal("10.00"), CurrencyEnum.USD, Date.valueOf("2022-08-02"), LocalDateTime.now(), true);
        shareItemRepository.save(shareItem);

        ShareDataDaily shareDataDaily = new ShareDataDaily("AMZN", Date.valueOf("2022-08-02"), new BigDecimal("20.45"), shareItem);
        ShareDataDaily shareDataDaily1 = new ShareDataDaily("AMZN", Date.valueOf("2022-08-01"), new BigDecimal("20.45"), shareItem);
        underTest.save(shareDataDaily1);
        underTest.save(shareDataDaily);

        List<ShareDataDaily> foundInDB = underTest.findShareDataDailyBySymbol("AMZN");
        List<ShareDataDaily> notfoundInDB = underTest.findShareDataDailyBySymbol("MSFT");


        assertThat(foundInDB.get(0).getSymbol()).isEqualTo("AMZN");
        assertThat(foundInDB.size()).isEqualTo(2);
        assertThat(notfoundInDB.size()).isEqualTo(0);

    }


    @Test
    void shareDataDailyAreUniqueByDateAndSymbol(){
        ShareItem shareItem = new ShareItem("Amazon", "AMZN", new BigDecimal("10.00"), CurrencyEnum.USD, Date.valueOf("2022-08-02"), LocalDateTime.now(), true);
        shareItemRepository.save(shareItem);

        ShareDataDaily shareDataDaily = new ShareDataDaily("AMZN", Date.valueOf("2022-08-02"), new BigDecimal("20.45"), shareItem);
        underTest.save(shareDataDaily);

        ShareDataDaily shareDataDaily2 = new ShareDataDaily("AMZN", Date.valueOf("2022-08-02"), new BigDecimal("20.45"), shareItem);
        assertThrows(DataIntegrityViolationException.class, () -> underTest.saveAndFlush(shareDataDaily2));
    }

    @Test
    void canDeleteShareData(){
        ShareItem shareItem = new ShareItem("Amazon", "AMZN", new BigDecimal("10.00"), CurrencyEnum.USD, Date.valueOf("2022-08-02"), LocalDateTime.now(), true);
        shareItemRepository.save(shareItem);

        ShareDataDaily shareDataDaily = new ShareDataDaily("AMZN", Date.valueOf("2022-08-02"), new BigDecimal("20.45"), shareItem);
        underTest.save(shareDataDaily);


        ShareDataDaily shareDataDailyFromDB = underTest.findAll().get(0);
        underTest.deleteById(shareDataDailyFromDB.getId());
        List<ShareDataDaily> foundInDB = underTest.findShareDataDailyBySymbol("AMZN");


        assertThat(foundInDB.size()).isEqualTo(0);
    }
}