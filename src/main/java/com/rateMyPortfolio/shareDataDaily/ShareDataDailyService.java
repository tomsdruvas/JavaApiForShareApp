package com.rateMyPortfolio.shareDataDaily;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ShareDataDailyService {

    private final ShareDataDailyRepository shareDataDailyRepository;

    @Autowired
    public ShareDataDailyService(ShareDataDailyRepository shareDataDailyRepository) {
        this.shareDataDailyRepository = shareDataDailyRepository;
    }

    public List<ShareDataDaily> getShareDataDaily(String symbol) {
        return shareDataDailyRepository.findShareDataDailyBySymbol(symbol);
    }
}
