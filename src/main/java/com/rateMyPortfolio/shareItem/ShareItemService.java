package com.rateMyPortfolio.shareItem;

import com.rateMyPortfolio.shareDataDaily.ShareDataDailyRepository;
import com.rateMyPortfolio.shareDataWeekly.ShareDataWeeklyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShareItemService {
    private final ShareItemRepository shareItemRepository;
    private final ShareDataDailyRepository shareDataDailyRepository;

    private final ShareDataWeeklyRepository shareDataWeeklyRepository;


    @Autowired
    public ShareItemService(ShareItemRepository shareItemRepository, ShareDataDailyRepository shareDataDailyRepository, ShareDataWeeklyRepository shareDataWeeklyRepository) {
        this.shareItemRepository = shareItemRepository;
        this.shareDataDailyRepository = shareDataDailyRepository;
        this.shareDataWeeklyRepository = shareDataWeeklyRepository;
    }

    public List<ShareItem> getShareItems(){
        return shareItemRepository.findAll();
    }

    public ShareItem getShareItem(String symbol) {
        return shareItemRepository.findShareItemBySymbol(symbol);

    }

    public ShareItem save(ShareItem newShareItem) {
        newShareItem.setOutstandingTask(true);
        newShareItem.setUpdatedAt(Date.valueOf(LocalDate.now()));
        newShareItem.setAddedAt(LocalDateTime.now());
        return shareItemRepository.save(newShareItem);
    }



}
