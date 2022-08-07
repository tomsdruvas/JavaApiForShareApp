package com.example.demo.shareItem;

import com.example.demo.shareDataDaily.ShareDataDaily;
import com.example.demo.shareDataDaily.ShareDataDailyRepository;
import com.example.demo.utils.ShareObjectMapper;
import com.example.demo.webClient.WebClientToGetAPI;
import com.example.demo.webClient.WebClientToGetShareItemProperties;
import com.example.demo.webClient.WebClientUrlEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

@Service
public class ShareItemService {
    private final ShareItemRepository shareItemRepository;
    private final ShareDataDailyRepository shareDataDailyRepository;


    @Autowired
    public ShareItemService(ShareItemRepository shareItemRepository, ShareDataDailyRepository shareDataDailyRepository) {
        this.shareItemRepository = shareItemRepository;
        this.shareDataDailyRepository = shareDataDailyRepository;
    }

    public List<ShareItem> getShareItems(){
        return shareItemRepository.findAll();
    }

    public ShareItem getShareItem(String symbol) {
        boolean doesSymbolExistInDb = shareItemRepository.existsBySymbol(symbol);
        if (doesSymbolExistInDb){
            return shareItemRepository.findShareItemBySymbol(symbol);
        }

        //Get the ShareItem info from 3rd party API
        WebClientToGetShareItemProperties properties = new WebClientToGetShareItemProperties();
        properties.setBaseUrl(WebClientUrlEnum.BASE_URL.getUrl());
        properties.setEndPoint(WebClientUrlEnum.GLOBAL_QUOTE.getUrl());

        WebClientToGetAPI webClientToGetAPI = new WebClientToGetAPI(WebClient.create(), properties);

        String shareItemObj = webClientToGetAPI.getShareInfoFromApiBySymbol(symbol);

        ShareItem shareItem = ShareObjectMapper.shareItemObjectMapper(shareItemObj);
        shareItemRepository.save(shareItem);

        //Get the ShareDataDailies info from 3rd party API
        properties.setEndPoint(WebClientUrlEnum.DAILY_DATA.getUrl());
        WebClientToGetAPI webClientToGetDaily = new WebClientToGetAPI(WebClient.create(), properties);

        String shareDataDailyObj = webClientToGetDaily.getShareInfoFromApiBySymbol(symbol);


        List<ShareDataDaily> shareDataDailies = ShareObjectMapper.shareDataObjectMapper(shareDataDailyObj, shareItem);

        shareDataDailyRepository.saveAll(shareDataDailies);

        shareItem.setShareDataDailies(shareDataDailies);

        return shareItem;
    }


}
