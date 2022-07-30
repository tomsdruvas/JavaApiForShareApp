package com.example.demo.shareItem;

import com.example.demo.shareDataDaily.ShareDataDaily;
import com.example.demo.utils.ShareObjectMapper;
import com.example.demo.webClient.WebClientToGetShareItem;
import com.example.demo.webClient.WebClientToGetShareItemProperties;
import com.example.demo.webClient.WebClientUrlEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDateTime;
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

        WebClientToGetShareItem webClientToGetShareItem = new WebClientToGetShareItem(WebClient.create(), properties);

        ShareItem shareItem = ShareObjectMapper.shareItemObjectMapper(webClientToGetShareItem.getShareItemFromApiBySymbol(symbol));
        return shareItemRepository.save(shareItem);
    }
}
