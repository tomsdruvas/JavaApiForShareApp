package com.example.demo.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository) {

        this.portfolioRepository = portfolioRepository;

    }

    public List<Portfolio> getAll() {
        return portfolioRepository.findAll();
    }

    public Portfolio getById(long portfolioId) throws EntityNotFoundException {
        boolean exists = portfolioRepository.existsById(portfolioId);
        if (!exists) {
            throw new EntityNotFoundException("Portfolio with" + portfolioId + "doesn't exist");
        }

        return portfolioRepository.findById(portfolioId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(portfolioId)));
    }

    public Portfolio save(Portfolio newPortfolio) {
        return portfolioRepository.save(newPortfolio);
    }

    public void removePortfolioByID(Long portfolioId) throws EntityNotFoundException {
        boolean exists = portfolioRepository.existsById(portfolioId);
        if (!exists) {
            throw new EntityNotFoundException("Portfolio with" + portfolioId + "doesn't exist");
        }
        portfolioRepository.deleteById(portfolioId);
    }


}
