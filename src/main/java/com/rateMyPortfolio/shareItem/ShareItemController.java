package com.rateMyPortfolio.shareItem;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ShareItem getShareItemBySymbol(@PathVariable("symbol") String symbol) {

        return shareItemService.getShareItem(symbol);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('portfolio:write')")
    public ShareItem newShareItem(@Valid @RequestBody ShareItem newShareItem) {
        System.out.println("registernewShareItem");
        System.out.println(newShareItem);
        return shareItemService.save(newShareItem);
    }

}
