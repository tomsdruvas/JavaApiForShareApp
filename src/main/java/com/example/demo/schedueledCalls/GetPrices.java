package com.example.demo.schedueledCalls;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class GetPrices {

    @Scheduled(fixedDelay = 2000)
    public void logMyName() throws InterruptedException {
        System.out.println("Hello my name is Tom");
        Thread.sleep(4000);
    }
}
