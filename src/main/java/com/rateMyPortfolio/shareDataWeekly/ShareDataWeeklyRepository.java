package com.rateMyPortfolio.shareDataWeekly;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShareDataWeeklyRepository extends JpaRepository <ShareDataWeekly, Long> {
    List<ShareDataWeekly> findShareDataWeeklyBySymbol(String symbol);
}

