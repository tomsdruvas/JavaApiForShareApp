package com.rateMyPortfolio.shareDataDaily;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping(path = "api/sharedatadaily")
public class ShareDataDailyController {

    private final ShareDataDailyService shareDataDailyService;


    @Autowired
    public ShareDataDailyController(ShareDataDailyService shareDataDailyService) {
        this.shareDataDailyService = shareDataDailyService;
    }


    @GetMapping(path = "/{symbol}")
    public List<ShareDataDaily> getShareDataDailyBySymbol(@PathVariable("symbol") String symbol){

        return shareDataDailyService.getShareDataDaily(symbol);
    }
}
