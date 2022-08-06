package com.example.demo.shareDataDaily;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


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
