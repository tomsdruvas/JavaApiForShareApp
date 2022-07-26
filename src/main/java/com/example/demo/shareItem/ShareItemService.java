package com.example.demo.shareItem;

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



    public ShareItem shareItemObjectMapper(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode shareItemObject = mapper.readTree(response).get("Global Quote");
        ShareItem shareItem = new ShareItem();
        shareItem.setName(shareItemObject.get("01. symbol").asText());
        shareItem.setSymbol(shareItemObject.get("01. symbol").asText());
        shareItem.setPrice(shareItemObject.get("05. price").asDouble());
        shareItem.setUpdatedAt(LocalDateTime.now());

        return shareItem;
    }




    public ShareItem getShareItem(String symbol) throws IOException, InterruptedException {
        boolean doesSymbolExistInDb = shareItemRepository.existsBySymbol(symbol);
        if (doesSymbolExistInDb){
            return shareItemRepository.findShareItemBySymbol(symbol);
        }

        WebClientToGetShareItemProperties properties = new WebClientToGetShareItemProperties();
        properties.setBaseUrl("https://www.alphavantage.co");

        WebClientToGetShareItem webClientToGetShareItem = new WebClientToGetShareItem(WebClient.create(), properties);

        ShareItem shareItem = shareItemObjectMapper(webClientToGetShareItem.getShareItemFromApiBySymbol(symbol));
        return shareItemRepository.save(shareItem);
    }
}
