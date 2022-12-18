package com.rateMyPortfolio.applicationUser;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "api/investors")
public class InvestorController {


    private final InvestorService investorService;

    @Autowired
    public InvestorController(InvestorService investorService) {
        this.investorService = investorService;
    }

    @GetMapping
    public ResponseEntity<List<ApplicationUser>> getAll() {
        return new ResponseEntity<>(investorService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ApplicationUser getInvestorById(@PathVariable(value = "id") Long investorId){
        try {
            return investorService.getById(investorId);
        }
        catch (EntityNotFoundException exc){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Investor Not Found", exc);
        }
    }

    @PostMapping()
    public ApplicationUser newInvestor(@Valid @RequestBody ApplicationUser newApplicationUser) {
        return investorService.save(newApplicationUser);
    }


    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ApplicationUser> updateInvestor(@PathVariable long id, @RequestBody ApplicationUser updatedApplicationUserDetails){
        ApplicationUser updateApplicationUser = investorService.updateById(id, updatedApplicationUserDetails);

        return ResponseEntity.ok(updateApplicationUser);

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
