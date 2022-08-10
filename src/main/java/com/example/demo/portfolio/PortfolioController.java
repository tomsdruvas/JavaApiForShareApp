package com.example.demo.portfolio;

import com.example.demo.investor.Investor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

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

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Portfolio newInvestor(@Valid @RequestBody Portfolio portfolioRequest){

        return portfolioService.save(portfolioRequest);
    }
}