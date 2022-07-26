package com.rateMyPortfolio.scheduledCalls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rateMyPortfolio.shareDataDaily.ShareDataDaily;
import com.rateMyPortfolio.shareDataDaily.ShareDataDailyRepository;
import com.rateMyPortfolio.shareDataWeekly.ShareDataWeekly;
import com.rateMyPortfolio.shareDataWeekly.ShareDataWeeklyRepository;
import com.rateMyPortfolio.shareItem.ShareItem;
import com.rateMyPortfolio.shareItem.ShareItemRepository;
import com.rateMyPortfolio.utils.ShareObjectMapper;
import com.rateMyPortfolio.webClient.WebClientToGetAPI;
import com.rateMyPortfolio.webClient.WebClientToGetShareItemProperties;
import com.rateMyPortfolio.webClient.WebClientUrlEnum;

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
                System.out.println("There are " + 0 + " tasks in the queue");
                break;
            default:
                ShareItem currentShareItem = shareItemList.get(0);
                System.out.println("There are " + shareItemList.size() + " tasks in the queue. Next is " + currentShareItem.getName());
//                getDailyData(currentShareItem);
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
