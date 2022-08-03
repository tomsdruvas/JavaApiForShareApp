package com.example.demo.shareDataDaily;


import com.example.demo.shareItem.ShareItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ShareDataDailyRepository extends JpaRepository <ShareDataDaily, Long> {


    List<ShareDataDaily> findShareDataDailyBySymbol(String symbol);
    boolean existsBySymbol(String symbol);
}
