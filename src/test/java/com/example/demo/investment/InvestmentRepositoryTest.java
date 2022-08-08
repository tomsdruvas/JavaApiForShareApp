package com.example.demo.investment;

import com.example.demo.investor.Investor;
import com.example.demo.investor.InvestorRepository;
import com.example.demo.portfolio.Portfolio;
import com.example.demo.portfolio.PortfolioRepository;
import com.example.demo.shareItem.ShareItem;
import com.example.demo.shareItem.ShareItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InvestmentRepositoryTest {

    @Autowired
    private InvestmentRepository underTest;

    @Autowired
    private ShareItemRepository shareItemRepository;

    @Autowired
    private InvestorRepository investorRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Test
    void shouldBeAbleToCreateAnInvestment(){
        ShareItem shareItem = new ShareItem("Amazon", "AMZN", 10.00, Date.valueOf("2022-08-02"));
        shareItemRepository.save(shareItem);

        Investor investor = new Investor("Jack", "ir@financialshop.com");
        investorRepository.save(investor);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), investor, true);
        portfolioRepository.save(portfolio);

        Investment investment = new Investment(shareItem, 3.00, 10.00, Date.valueOf("2022-08-02" ), portfolio);
        underTest.save(investment);
        List<Investment> investmentList = underTest.findAll();

        assertEquals(investmentList.size(),1);

    }



}