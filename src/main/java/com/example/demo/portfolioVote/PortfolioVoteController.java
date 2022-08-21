package com.example.demo.portfolioVote;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(path = "api/portfoliovote")
public class PortfolioVoteController {

    private final PortfolioVoteService portfolioVoteService;

    @Autowired
    public PortfolioVoteController(PortfolioVoteService portfolioVoteService) {
        this.portfolioVoteService = portfolioVoteService;
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public PortfolioVote newPortfolioVote(@Valid @RequestBody PortfolioVote newPortfolioVote) {
        return portfolioVoteService.save(newPortfolioVote);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<PortfolioVote> updatePortfolioVote(@PathVariable long id, @RequestBody PortfolioVote updatedPortfolioVote){
        PortfolioVote updatePortfolioVote = portfolioVoteService.updateById(id, updatedPortfolioVote);

        return ResponseEntity.ok(updatePortfolioVote);

    }

    @DeleteMapping("/{id}")
    public void  deletePortfolioVote(@PathVariable(value = "id") Long id){
        try {
            portfolioVoteService.removePortfolioVoteById(id);
        }
        catch (EntityNotFoundException exc){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "PortfolioVote Not Found", exc);
        }
    }

}
