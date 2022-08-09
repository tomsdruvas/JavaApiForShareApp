package com.example.demo.comment;

import com.example.demo.investor.Investor;
import com.example.demo.investor.InvestorRepository;
import com.example.demo.portfolio.Portfolio;
import com.example.demo.portfolio.PortfolioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

}