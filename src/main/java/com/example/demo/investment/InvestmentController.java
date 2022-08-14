package com.example.demo.investment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/investment")
public class InvestmentController {

    private final InvestmentService investmentService;

    @Autowired
    public InvestmentController(InvestmentService investmentService){
        this.investmentService = investmentService;
    }

    @GetMapping
    public ResponseEntity<List<Investment>>getAll(){
        return new ResponseEntity<>(investmentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Investment getInvestmentById(@PathVariable(value = "id") Long investmentId){
        try {
            return investmentService.getById(investmentId);
        }
        catch (EntityNotFoundException exc){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Investment Not Found", exc);
        }
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Investment newInvestment(@Valid @RequestBody Investment newInvestment) {
        return investmentService.save(newInvestment);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Investment> updateInvestment(@PathVariable long id, @RequestBody Investment updatedInvestmentDetails){
        Investment updateInvestment = investmentService.updateById(id, updatedInvestmentDetails);

        return ResponseEntity.ok(updateInvestment);

    }

    @DeleteMapping("/{investmentId}")
    public void  deleteInvestment(@PathVariable(value = "investmentId") Long investmentId){
        try {
            investmentService.removeInvestmentById(investmentId);
        }
        catch (EntityNotFoundException exc){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Investment Not Found", exc);
        }
    }





}
