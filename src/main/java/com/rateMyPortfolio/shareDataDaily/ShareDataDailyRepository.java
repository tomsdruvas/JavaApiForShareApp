package com.rateMyPortfolio.shareDataDaily;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShareDataDailyRepository extends JpaRepository <ShareDataDaily, Long> {
    List<ShareDataDaily> findShareDataDailyBySymbol(String symbol);
}
