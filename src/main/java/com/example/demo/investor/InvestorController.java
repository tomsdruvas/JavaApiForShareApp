package com.example.demo.investor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/investors")
public class InvestorController {


    private final InvestorService investorService;

    @Autowired
    public InvestorController(InvestorService investorService) {
        this.investorService = investorService;
    }

    @GetMapping("investors")
    public ResponseEntity<List<Investor>> getAll() {
        return new ResponseEntity<>(investorService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Optional<Investor> getInvestorById(@PathVariable(value = "id") Long investorId){
        return investorService.getById(investorId);
    }

    @PostMapping()
    public Investor newInvestor(@Valid @RequestBody Investor newInvestor) {
        return investorService.save(newInvestor);
    }
}
