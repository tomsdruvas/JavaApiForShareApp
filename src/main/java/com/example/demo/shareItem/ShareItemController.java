package com.example.demo.shareItem;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

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
    @ResponseStatus(code = HttpStatus.OK)
    public ShareItem getShareItemBySymbol(@PathVariable("symbol") String symbol) {

        return shareItemService.getShareItem(symbol);
    }

}
