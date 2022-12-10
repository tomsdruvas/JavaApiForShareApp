package com.rateMyPortfolio.comment;

import com.rateMyPortfolio.investor.Investor;
import com.rateMyPortfolio.investor.InvestorRepository;
import com.rateMyPortfolio.portfolio.Portfolio;
import com.rateMyPortfolio.portfolio.PortfolioRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository underTest;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private InvestorRepository investorRepository;

    @Test
    void shouldBeAbleToCreateAComment(){
        Investor investor = new Investor("Jack", "ir@financialshop.com");
        investorRepository.save(investor);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), investor, true);
        portfolioRepository.save(portfolio);

        Comment comment = new Comment(investor, portfolio, Date.valueOf("2022-08-02"), "This porfolio is looking nice");

        underTest.save(comment);

        List<Comment> commentList = underTest.findAll();


        assertEquals(commentList.size(),1);
        assertEquals(commentList.get(0).getContent(),"This porfolio is looking nice");
    }

    @Test
    @Sql("/test-db-setup.sql")
    void findCommentsByPortfolioId(){
        Long id = portfolioRepository.findAll().get(0).getId();
        List<Comment> commentList = underTest.findCommentsByPortfolioId(id);

        assertTrue(commentList.size() > 0);

    }

}