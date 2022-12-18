package com.rateMyPortfolio.portfolioVote;

import com.rateMyPortfolio.applicationUser.ApplicationUser;
import com.rateMyPortfolio.applicationUser.InvestorRepository;
import com.rateMyPortfolio.portfolio.Portfolio;
import com.rateMyPortfolio.portfolio.PortfolioRepository;
import com.rateMyPortfolio.utils.VoteEnum;

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
        ApplicationUser applicationUser = new ApplicationUser("Jack", "ir@financialshop.com");
        investorRepository.save(applicationUser);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), applicationUser, true);
        portfolioRepository.save(portfolio);

        PortfolioVote portfolioVote = new PortfolioVote(applicationUser.getId(), portfolio.getId(), VoteEnum.UP);
        underTest.save(portfolioVote);

        List<PortfolioVote> portfolioVoteList = underTest.findAll();

        assertEquals(portfolioVoteList.size(),1);
        assertEquals(portfolioVoteList.get(0).getVoteDirection().toString(), "UP");

    }

    @Test
    void shouldBeAbleToUpdateAPortfolioVote_success(){

        ApplicationUser applicationUser = new ApplicationUser("Jack", "ir@financialshop.com");
        investorRepository.save(applicationUser);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), applicationUser, true);
        portfolioRepository.save(portfolio);


        PortfolioVote portfolioVote = new PortfolioVote(applicationUser.getId(), portfolio.getId(), VoteEnum.UP);
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
        ApplicationUser applicationUser = new ApplicationUser("Jack", "ir@financialshop.com");
        investorRepository.save(applicationUser);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), applicationUser, true);
        portfolioRepository.save(portfolio);


        PortfolioVote portfolioVote = new PortfolioVote(applicationUser.getId(), portfolio.getId(), VoteEnum.UP);
        underTest.save(portfolioVote);

        List<PortfolioVote> updatedPortfolioVoteList = underTest.findAll();
        assertEquals(updatedPortfolioVoteList.get(0).getVoteDirection().toString(), "UP");

    }

    @Test
    void sameUserShouldNotBeAbleToVoteTwiceOnTheSamePortfolio_Exception  (){

        ApplicationUser applicationUser = new ApplicationUser("Jack", "ir@financialshop.com");
        investorRepository.save(applicationUser);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), applicationUser, true);
        portfolioRepository.save(portfolio);

        PortfolioVote portfolioVote = new PortfolioVote(applicationUser.getId(), portfolio.getId(), VoteEnum.UP);
        underTest.save(portfolioVote);

        PortfolioVote portfolioVoteDown = new PortfolioVote(applicationUser.getId(), portfolio.getId(), VoteEnum.DOWN);
        assertThrows(DataIntegrityViolationException.class, () -> underTest.saveAndFlush(portfolioVoteDown));

    }

    @Test
    void shouldBeAbleToDeleteAPortfolioVoteById_success(){

        ApplicationUser applicationUser = new ApplicationUser("Jack", "ir@financialshop.com");
        investorRepository.save(applicationUser);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), applicationUser, true);
        portfolioRepository.save(portfolio);

        PortfolioVote portfolioVote = new PortfolioVote(applicationUser.getId(), portfolio.getId(), VoteEnum.UP);
        underTest.save(portfolioVote);

        List<PortfolioVote> portfolioVoteList = underTest.findAll();
        assertEquals(portfolioVoteList.size(), 1);

        Long portfolioVoteEntityId = portfolioVoteList.get(0).getId();
        underTest.deleteById(portfolioVoteEntityId);

        List<PortfolioVote> updatedCommentVoteList = underTest.findAll();
        assertEquals(updatedCommentVoteList.size(), 0);

    }
}