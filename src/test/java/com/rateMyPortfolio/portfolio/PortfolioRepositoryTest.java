package com.rateMyPortfolio.portfolio;

import com.rateMyPortfolio.investor.Investor;
import com.rateMyPortfolio.investor.InvestorRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class PortfolioRepositoryTest {

    @Autowired
    private PortfolioRepository underTest;

    @Autowired
    private InvestorRepository investorRepository;


    private Investor investor;
    private Portfolio portfolio;



    @BeforeEach
    void init(){
        investor = new Investor("Jack", "ir@financialshop.com");
        portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), investor, true);
        investorRepository.save(investor);
        underTest.save(portfolio);

    }

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
        investorRepository.deleteAll();
    }

    @Test
    void shouldBeAbleToCreateAPortfolio(){
        List<Portfolio> portfolioList = underTest.findAll();
        assertEquals(portfolioList.size(),1);
    }

    @Test
    void shouldBeAbleToUpdatePortfolioById(){

        Portfolio updatedDetails = new Portfolio("Tech stocks updated", Date.valueOf("2022-08-03"), investor, false);

        Portfolio portfolioFromDB = underTest.findPortfolioById((portfolio.getId()));

        portfolioFromDB.setName(updatedDetails.getName());
        portfolioFromDB.setInvestor(updatedDetails.getInvestor());
        portfolioFromDB.setCreatedDate(updatedDetails.getCreatedDate());
        portfolioFromDB.setIsPublic(updatedDetails.getIsPublic());


        Portfolio updatedPortfolio = underTest.save(portfolioFromDB);

        assertThat(updatedPortfolio.getName()).isEqualTo("Tech stocks updated");
        assertThat(updatedPortfolio.getCreatedDate()).isEqualTo(Date.valueOf("2022-08-03"));

    }




}