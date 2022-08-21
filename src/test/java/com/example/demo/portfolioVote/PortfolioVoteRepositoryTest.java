package com.example.demo.portfolioVote;

import com.example.demo.comment.Comment;
import com.example.demo.commentVote.CommentVote;
import com.example.demo.investor.Investor;
import com.example.demo.investor.InvestorRepository;
import com.example.demo.portfolio.Portfolio;
import com.example.demo.portfolio.PortfolioRepository;
import com.example.demo.utils.VoteEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PortfolioVoteRepositoryTest {

    @Autowired
    private PortfolioVoteRepository underTest;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private InvestorRepository investorRepository;

    @Test
    void shouldBeAbleToCreateAPortfolioVote_success(){
        Investor investor = new Investor("Jack", "ir@financialshop.com");
        investorRepository.save(investor);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), investor, true);
        portfolioRepository.save(portfolio);

        PortfolioVote portfolioVote = new PortfolioVote(investor.getId(), portfolio.getId(), VoteEnum.UP);
        underTest.save(portfolioVote);

        List<PortfolioVote> portfolioVoteList = underTest.findAll();

        assertEquals(portfolioVoteList.size(),1);
        assertEquals(portfolioVoteList.get(0).getVoteDirection().toString(), "UP");

    }

    @Test
    void shouldBeAbleToUpdateAPortfolioVote_success(){

        Investor investor = new Investor("Jack", "ir@financialshop.com");
        investorRepository.save(investor);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), investor, true);
        portfolioRepository.save(portfolio);


        PortfolioVote portfolioVote = new PortfolioVote(investor.getId(), portfolio.getId(), VoteEnum.UP);
        underTest.save(portfolioVote);

        List<PortfolioVote> portfolioVoteList = underTest.findAll();
        PortfolioVote portfolioVoteEntity = portfolioVoteList.get(0);
        portfolioVoteEntity.setVoteDirection(VoteEnum.DOWN);
        underTest.save(portfolioVoteEntity);


        List<PortfolioVote> updatedPortfolioVoteList = underTest.findAll();
        assertEquals(updatedPortfolioVoteList.get(0).getVoteDirection().toString(), "DOWN");

    }

    @Test
    void findPortfolioVoteById_success() {
        Investor investor = new Investor("Jack", "ir@financialshop.com");
        investorRepository.save(investor);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), investor, true);
        portfolioRepository.save(portfolio);


        PortfolioVote portfolioVote = new PortfolioVote(investor.getId(), portfolio.getId(), VoteEnum.UP);
        underTest.save(portfolioVote);

        List<PortfolioVote> updatedPortfolioVoteList = underTest.findAll();
        assertEquals(updatedPortfolioVoteList.get(0).getVoteDirection().toString(), "UP");

    }

    @Test
    void sameUserShouldNotBeAbleToVoteTwiceOnTheSamePortfolio_Exception  (){

        Investor investor = new Investor("Jack", "ir@financialshop.com");
        investorRepository.save(investor);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), investor, true);
        portfolioRepository.save(portfolio);

        PortfolioVote portfolioVote = new PortfolioVote(investor.getId(), portfolio.getId(), VoteEnum.UP);
        underTest.save(portfolioVote);

        PortfolioVote portfolioVoteDown = new PortfolioVote(investor.getId(), portfolio.getId(), VoteEnum.DOWN);
        assertThrows(DataIntegrityViolationException.class, () -> underTest.saveAndFlush(portfolioVoteDown));

    }

    @Test
    void shouldBeAbleToDeleteAPortfolioVoteById_success(){

        Investor investor = new Investor("Jack", "ir@financialshop.com");
        investorRepository.save(investor);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), investor, true);
        portfolioRepository.save(portfolio);

        PortfolioVote portfolioVote = new PortfolioVote(investor.getId(), portfolio.getId(), VoteEnum.UP);
        underTest.save(portfolioVote);

        List<PortfolioVote> portfolioVoteList = underTest.findAll();
        assertEquals(portfolioVoteList.size(), 1);

        Long portfolioVoteEntityId = portfolioVoteList.get(0).getId();
        underTest.deleteById(portfolioVoteEntityId);

        List<PortfolioVote> updatedCommentVoteList = underTest.findAll();
        assertEquals(updatedCommentVoteList.size(), 0);

    }
}