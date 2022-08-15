package com.example.demo.shareDataWeekly;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ShareDataWeeklyRepository extends JpaRepository <ShareDataWeekly, Long> {
    List<ShareDataWeekly> findShareDataWeeklyBySymbol(String symbol);
}

