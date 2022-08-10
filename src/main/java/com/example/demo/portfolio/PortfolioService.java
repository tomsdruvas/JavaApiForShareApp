package com.example.demo.portfolio;


import com.example.demo.investor.Investor;
import com.example.demo.investor.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final InvestorRepository investorRepository;

    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository, InvestorRepository investorRepository) {

        this.portfolioRepository = portfolioRepository;
        this.investorRepository = investorRepository;
    }

    public List<Portfolio> getAll() {
        return portfolioRepository.findAll();
    }

    public Portfolio getById(long id) {
        return portfolioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public Portfolio save(Portfolio newPortfolio) {
        return portfolioRepository.save(newPortfolio);
    }

    public void removePortfolioByID(Long PortfolioId) throws EntityNotFoundException {
        boolean exists = portfolioRepository.existsById(PortfolioId);
        if (!exists) {
            throw new EntityNotFoundException("Investor with" + PortfolioId + "doesn't exist");
        }
        portfolioRepository.deleteById(PortfolioId);
    }


}
