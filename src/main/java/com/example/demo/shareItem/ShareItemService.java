package com.example.demo.shareItem;

import com.example.demo.investor.Investor;
import com.example.demo.shareDataDaily.ShareDataDaily;
import com.example.demo.shareDataDaily.ShareDataDailyRepository;
import com.example.demo.shareDataWeekly.ShareDataWeekly;
import com.example.demo.shareDataWeekly.ShareDataWeeklyRepository;
import com.example.demo.utils.ShareObjectMapper;
import com.example.demo.webClient.WebClientToGetAPI;
import com.example.demo.webClient.WebClientToGetShareItemProperties;
import com.example.demo.webClient.WebClientUrlEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
