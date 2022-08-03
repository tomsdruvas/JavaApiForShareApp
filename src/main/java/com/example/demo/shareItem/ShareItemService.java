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


    @Autowired
    public ShareItemService(ShareItemRepository shareItemRepository) {
        this.shareItemRepository = shareItemRepository;


    }

    public List<ShareItem> getShareItems(){
        return shareItemRepository.findAll();
    }

    public ShareItem getShareItem(String symbol) throws IOException, InterruptedException {
        boolean doesSymbolExistInDb = shareItemRepository.existsBySymbol(symbol);
        if (doesSymbolExistInDb){
            return shareItemRepository.findShareItemBySymbol(symbol);
        }

        WebClientToGetShareItemProperties properties = new WebClientToGetShareItemProperties();
        properties.setBaseUrl(WebClientUrlEnum.BASE_URL.getUrl());
        properties.setEndPoint(WebClientUrlEnum.GLOBAL_QUOTE.getUrl());

        WebClientToGetAPI webClientToGetAPI = new WebClientToGetAPI(WebClient.create(), properties);

        ShareItem shareItem = ShareObjectMapper.shareItemObjectMapper(webClientToGetAPI.getShareInfoFromApiBySymbol(symbol));

//        properties.setEndPoint(WebClientUrlEnum.DAILY_DATA.getUrl());
//        WebClientToGetAPI webClientToGetDaily = new WebClientToGetAPI(WebClient.create(), properties);
//        ShareDataDaily shareDataDaily = ShareObjectMapper.shareDataObjectMapper(webClientToGetDaily.getShareInfoFromApiBySymbol(symbol));
//        shareItem.setShareDataDaily(shareDataDaily);



        return shareItemRepository.save(shareItem);
    }
}
