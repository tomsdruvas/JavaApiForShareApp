package com.example.demo.shareItem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "api/shareitem/")
public class ShareItemController {


    @GetMapping
    public List<ShareItem> getShareItems(){
        return List.of(
                new ShareItem(
                        1L,
                        "Amazon",
                        "AMZN",
                        10,
                        LocalDateTime.now()
                ));
    }
}
