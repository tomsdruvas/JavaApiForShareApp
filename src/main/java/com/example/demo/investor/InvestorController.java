package com.example.demo.investor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/investors")
public class InvestorController {


    private InvestorService investorService;

    @Autowired
    public InvestorController(InvestorService investorService) {
        this.investorService = investorService;
    }

    @GetMapping("investors")
    public ResponseEntity<List<Investor>> getAll() {
        return new ResponseEntity<>(investorService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/post")
    public Investor newInvestor(@Valid @RequestBody Investor newInvestor) {
        return investorService.save(newInvestor);
    }
}
