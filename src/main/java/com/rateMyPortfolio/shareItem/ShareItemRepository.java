package com.rateMyPortfolio.shareItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShareItemRepository extends JpaRepository <ShareItem, Long> {


    ShareItem findShareItemBySymbol(String symbol);

    boolean existsBySymbol(String symbol);

    List<ShareItem> findShareItemsByOutstandingTaskTrueOrderByAddedAtAsc();

}