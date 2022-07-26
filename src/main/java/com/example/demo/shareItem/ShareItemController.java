package com.example.demo.shareItem;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/shareitem")

public class ShareItemController {


    private final ShareItemService shareItemService;
    @Autowired
    public ShareItemController(ShareItemService shareItemService) {
        this.shareItemService = shareItemService;
    }

    @GetMapping
    public List<ShareItem> getShareItems(){
        return shareItemService.getShareItems();
    }

    @GetMapping(path = "/{symbol}")
    public ShareItem getShareItemBySymbolNew(@PathVariable("symbol") String symbol) throws IOException, InterruptedException {

        return shareItemService.getShareItemNew(symbol);
    }

}
