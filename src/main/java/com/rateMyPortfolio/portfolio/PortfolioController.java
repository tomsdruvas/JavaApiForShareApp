package com.rateMyPortfolio.portfolio;

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
@RequestMapping(path = "api/portfolio")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping
    public ResponseEntity<List<Portfolio>> getAll() {
        return new ResponseEntity<>(portfolioService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Portfolio getPortfolioById(@PathVariable(value = "id") Long portfolioId){
        try {
            return portfolioService.getById(portfolioId);
        }
        catch (EntityNotFoundException exc){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Portfolio Not Found", exc);
        }
    }


    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Portfolio> updatePortfolio(@PathVariable long id, @RequestBody Portfolio updatedPortfolioDetails){
        Portfolio updatePortfolio = portfolioService.updateById(id, updatedPortfolioDetails);

        return ResponseEntity.ok(updatePortfolio);

    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Portfolio newPortfolio(@Valid @RequestBody Portfolio portfolioRequest){

        return portfolioService.save(portfolioRequest);
    }

    @DeleteMapping("/{id}")
    public void  deletePortfolio(@PathVariable(value = "id") Long id){
        try {
            portfolioService.removePortfolioByID(id);
        }
        catch (EntityNotFoundException exc){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Portfolio Not Found", exc);
        }
    }
}
