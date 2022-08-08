package com.example.demo.portfolio;

import com.example.demo.investor.Investor;
import com.example.demo.investor.InvestorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class PortfolioRepositoryTest {

    @Autowired
    private PortfolioRepository underTest;

    @Autowired
    private InvestorRepository investorRepository;

    @Test
    void shouldBeAbleToCreateAPortfolio(){
        Investor investor = new Investor("Jack", "ir@financialshop.com");
        investorRepository.save(investor);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), investor, true);
        underTest.save(portfolio);

        List<Portfolio> portfolioList = underTest.findAll();

        assertEquals(portfolioList.size(),1);
    }
}