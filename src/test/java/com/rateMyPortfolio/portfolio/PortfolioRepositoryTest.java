package com.rateMyPortfolio.portfolio;

import com.rateMyPortfolio.applicationUser.ApplicationUser;
import com.rateMyPortfolio.applicationUser.InvestorRepository;

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


    private ApplicationUser applicationUser;
    private Portfolio portfolio;



    @BeforeEach
    void init(){
        applicationUser = new ApplicationUser("Jack", "ir@financialshop.com");
        portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), applicationUser, true);
        investorRepository.save(applicationUser);
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

        Portfolio updatedDetails = new Portfolio("Tech stocks updated", Date.valueOf("2022-08-03"), applicationUser, false);

        Portfolio portfolioFromDB = underTest.findPortfolioById((portfolio.getId()));

        portfolioFromDB.setName(updatedDetails.getName());
        portfolioFromDB.setApplicationUser(updatedDetails.getApplicationUser());
        portfolioFromDB.setCreatedDate(updatedDetails.getCreatedDate());
        portfolioFromDB.setIsPublic(updatedDetails.getIsPublic());


        Portfolio updatedPortfolio = underTest.save(portfolioFromDB);

        assertThat(updatedPortfolio.getName()).isEqualTo("Tech stocks updated");
        assertThat(updatedPortfolio.getCreatedDate()).isEqualTo(Date.valueOf("2022-08-03"));

    }




}