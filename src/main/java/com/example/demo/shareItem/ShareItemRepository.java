package com.example.demo.shareItem;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ShareItemRepository extends JpaRepository <ShareItem, Long> {


    ShareItem findShareItemBySymbol(String symbol);

    boolean existsBySymbol(String symbol);

    List<ShareItem> findShareItemsByOutstandingTaskTrueOrderByAddedAtAsc();

}