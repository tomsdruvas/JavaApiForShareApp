package com.example.demo.scheduledCalls;

import com.example.demo.shareDataDaily.ShareDataDaily;
import com.example.demo.shareDataDaily.ShareDataDailyRepository;
import com.example.demo.shareDataWeekly.ShareDataWeekly;
import com.example.demo.shareDataWeekly.ShareDataWeeklyRepository;
import com.example.demo.shareItem.ShareItem;
import com.example.demo.shareItem.ShareItemRepository;
import com.example.demo.utils.ShareObjectMapper;
import com.example.demo.webClient.WebClientToGetAPI;
import com.example.demo.webClient.WebClientToGetShareItemProperties;
import com.example.demo.webClient.WebClientUrlEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class GetPrices {

    ShareItemRepository shareItemRepository;

    ShareDataDailyRepository shareDataDailyRepository;

    ShareDataWeeklyRepository shareDataWeeklyRepository;

    @Autowired
    public GetPrices(ShareItemRepository shareItemRepository, ShareDataDailyRepository shareDataDailyRepository, ShareDataWeeklyRepository shareDataWeeklyRepository) {
        this.shareItemRepository = shareItemRepository;
        this.shareDataDailyRepository = shareDataDailyRepository;
        this.shareDataWeeklyRepository = shareDataWeeklyRepository;
    }

    @Scheduled(fixedDelay = 8000)
    public void getMissingPrices() throws InterruptedException {

        List<ShareItem> shareItemList = shareItemRepository.findShareItemsByOutstandingTaskTrueOrderByAddedAtAsc();



        switch (shareItemList.size()){
            case 0:
                System.out.println("There are " + 0 + " in the queue");
                break;
            default:
                ShareItem currentShareItem = shareItemList.get(0);
                System.out.println("There are " + shareItemList.size() + " in the queue. Next is " + currentShareItem.getName());
                getDailyData(currentShareItem);
                getWeeklyData(currentShareItem);

                currentShareItem.setOutstandingTask(false);
                shareItemRepository.save(currentShareItem);
        }

        Thread.sleep(8000);
    }

    public void getDailyData(ShareItem shareItem){
        WebClientToGetShareItemProperties properties = new WebClientToGetShareItemProperties();
        properties.setBaseUrl(WebClientUrlEnum.BASE_URL.getUrl());
        properties.setEndPoint(WebClientUrlEnum.DAILY_DATA.getUrl());
        WebClientToGetAPI webClientToGetDaily = new WebClientToGetAPI(WebClient.create(), properties);

        String shareDataDailyObj = webClientToGetDaily.getShareInfoFromApiBySymbol(shareItem.getSymbol());

        List<ShareDataDaily> shareDataDailies = ShareObjectMapper.shareDataDailyObjectMapper(shareDataDailyObj, shareItem);

        shareDataDailyRepository.saveAll(shareDataDailies);
    }

    public void getWeeklyData(ShareItem shareItem){
        WebClientToGetShareItemProperties properties = new WebClientToGetShareItemProperties();
        properties.setBaseUrl(WebClientUrlEnum.BASE_URL.getUrl());
        properties.setEndPoint(WebClientUrlEnum.WEEKLY_DATA.getUrl());
        WebClientToGetAPI webClientToGetWeekly = new WebClientToGetAPI(WebClient.create(), properties);

        String shareDataWeeklyObj = webClientToGetWeekly.getShareInfoFromApiBySymbol(shareItem.getSymbol());

        List<ShareDataWeekly> shareDataWeeklies = ShareObjectMapper.shareDataWeeklyObjectMapper(shareDataWeeklyObj, shareItem);

        shareDataWeeklyRepository.saveAll(shareDataWeeklies);
    }
}
