package com.rateMyPortfolio.investment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.rateMyPortfolio.investor.Investor;
import com.rateMyPortfolio.investor.InvestorRepository;
import com.rateMyPortfolio.portfolio.Portfolio;
import com.rateMyPortfolio.portfolio.PortfolioRepository;
import com.rateMyPortfolio.shareItem.ShareItem;
import com.rateMyPortfolio.shareItem.ShareItemRepository;
import com.rateMyPortfolio.utils.CurrencyEnum;

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
        ShareItem shareItem = new ShareItem("Amazon", "AMZN", new BigDecimal("10.00"), CurrencyEnum.USD, Date.valueOf("2022-08-02"), LocalDateTime.now(), true);
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