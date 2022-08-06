package com.example.demo.shareDataDaily;

import com.example.demo.utils.ShareObjectMapper;
import com.example.demo.webClient.WebClientToGetAPI;
import com.example.demo.webClient.WebClientToGetShareItemProperties;
import com.example.demo.webClient.WebClientUrlEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Service
public class ShareDataDailyService {

    private final ShareDataDailyRepository shareDataDailyRepository;

    @Autowired
    public ShareDataDailyService(ShareDataDailyRepository shareDataDailyRepository) {
        this.shareDataDailyRepository = shareDataDailyRepository;
    }

    public List<ShareDataDaily> getShareDataDaily(String symbol) {

//        WebClientToGetShareItemProperties properties = new WebClientToGetShareItemProperties();
//        properties.setBaseUrl(WebClientUrlEnum.BASE_URL.getUrl());
//        properties.setEndPoint(WebClientUrlEnum.DAILY_DATA.getUrl());
//
//        WebClientToGetAPI webClientToGetAPI = new WebClientToGetAPI(WebClient.create(), properties);
//
//        String dailyDataObj = webClientToGetAPI.getShareInfoFromApiBySymbol(symbol);
//
//        List<ShareDataDaily> listOfData = ShareObjectMapper.shareDataObjectMapper(dailyDataObj);

        return shareDataDailyRepository.findShareDataDailyBySymbol(symbol);
    }
}
