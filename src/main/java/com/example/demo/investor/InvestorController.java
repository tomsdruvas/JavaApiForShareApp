package com.example.demo.investor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
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

    @GetMapping
    public ResponseEntity<List<Investor>> getAll() {
        return new ResponseEntity<>(investorService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Investor getInvestorById(@PathVariable(value = "id") Long investorId){
        try {
            return investorService.getById(investorId);
        }
        catch (EntityNotFoundException exc){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Investor Not Found", exc);
        }
    }

    @PostMapping()
    public Investor newInvestor(@Valid @RequestBody Investor newInvestor) {
        return investorService.save(newInvestor);
    }


    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Investor> updateInvestor(@PathVariable long id, @RequestBody Investor updatedInvestorDetails){
        Investor updateInvestor = investorService.updateById(id, updatedInvestorDetails);

        return ResponseEntity.ok(updateInvestor);

    }

    @DeleteMapping("/{investorId}")
    public void  deleteInvestor(@PathVariable(value = "investorId") Long investorId){
        try {
            investorService.removeInvestorByID(investorId);
        }
        catch (EntityNotFoundException exc){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Investor Not Found", exc);
        }
    }
}
