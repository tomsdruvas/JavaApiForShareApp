package com.example.demo.portfolioVote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PortfolioVoteService {

    private final PortfolioVoteRepository portfolioVoteRepository;

    @Autowired
    public PortfolioVoteService(PortfolioVoteRepository portfolioVoteRepository) {
        this.portfolioVoteRepository = portfolioVoteRepository;
    }

    public List<PortfolioVote> getAll() {
        return portfolioVoteRepository.findAll();
    }

    public PortfolioVote save(PortfolioVote newPortfolioVote) {
        return portfolioVoteRepository.save(newPortfolioVote);
    }

    public PortfolioVote updateById(long id, PortfolioVote updatedPortfolioVote) {
        boolean exists = portfolioVoteRepository.existsById(id);
        if (!exists) {
            throw new EntityNotFoundException("PortfolioVote with " + id + " doesn't exist");
        }
        PortfolioVote portfolioVote = portfolioVoteRepository.findPortfolioVoteById(id);

        portfolioVote.setVoteDirection(updatedPortfolioVote.getVoteDirection());
        return portfolioVoteRepository.save(portfolioVote);
    }

    public void removePortfolioVoteById(Long portfolioVoteId) throws EntityNotFoundException {
        boolean exists = portfolioVoteRepository.existsById(portfolioVoteId);
        if (!exists) {
            throw new EntityNotFoundException("PortfolioVote with" + portfolioVoteId + "doesn't exist");
        }
        portfolioVoteRepository.deleteById(portfolioVoteId);
    }


}
