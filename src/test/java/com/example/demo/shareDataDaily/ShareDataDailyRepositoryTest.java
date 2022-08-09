package com.example.demo.shareDataDaily;

import com.example.demo.shareItem.ShareItem;
import com.example.demo.shareItem.ShareItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;


import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ShareDataDailyRepositoryTest {

    @Autowired
    private ShareDataDailyRepository underTest;

    @Autowired
    private ShareItemRepository shareItemRepository;

    @Test
    void itShouldFindShareDataDailyBySymbol() {

        ShareItem shareItem = new ShareItem("Amazon", "AMZN", 10.00, Date.valueOf("2022-08-02"));
        shareItemRepository.save(shareItem);

        ShareDataDaily shareDataDaily = new ShareDataDaily("AMZN", Date.valueOf("2022-08-02"), 20.56, shareItem);
        ShareDataDaily shareDataDaily1 = new ShareDataDaily("AMZN", Date.valueOf("2022-08-01"), 20.51, shareItem);
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
        ShareItem shareItem = new ShareItem("Amazon", "AMZN", 10.00, Date.valueOf("2022-08-02"));
        shareItemRepository.save(shareItem);

        ShareDataDaily shareDataDaily = new ShareDataDaily("AMZN", Date.valueOf("2022-08-02"), 20.56, shareItem);
        underTest.save(shareDataDaily);

        ShareDataDaily shareDataDaily2 = new ShareDataDaily("AMZN", Date.valueOf("2022-08-02"), 20.56, shareItem);
        assertThrows(DataIntegrityViolationException.class, () -> underTest.saveAndFlush(shareDataDaily2));
    }

    @Test
    void canDeleteShareData(){
        ShareItem shareItem = new ShareItem("Amazon", "AMZN", 10.00, Date.valueOf("2022-08-02"));
        shareItemRepository.save(shareItem);

        ShareDataDaily shareDataDaily = new ShareDataDaily("AMZN", Date.valueOf("2022-08-02"), 20.56, shareItem);
        underTest.save(shareDataDaily);


        ShareDataDaily shareDataDailyFromDB = underTest.findAll().get(0);
        underTest.deleteById(shareDataDailyFromDB.getId());
        List<ShareDataDaily> foundInDB = underTest.findShareDataDailyBySymbol("AMZN");


        assertThat(foundInDB.size()).isEqualTo(0);

    }

}