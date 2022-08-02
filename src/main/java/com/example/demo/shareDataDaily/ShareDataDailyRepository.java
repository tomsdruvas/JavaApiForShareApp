package com.example.demo.shareDataDaily;


import com.example.demo.shareItem.ShareItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ShareDataDailyRepository extends JpaRepository <ShareDataDaily, Long> {


    ShareDataDaily findShareDataDailyBySymbol(String symbol);
    boolean existsBySymbol(String symbol);
}
