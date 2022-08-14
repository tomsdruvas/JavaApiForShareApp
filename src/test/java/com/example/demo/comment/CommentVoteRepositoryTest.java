package com.example.demo.comment;

import com.example.demo.commentVote.CommentVote;
import com.example.demo.commentVote.CommentVoteRepository;
import com.example.demo.investor.Investor;
import com.example.demo.investor.InvestorRepository;
import com.example.demo.portfolio.Portfolio;
import com.example.demo.portfolio.PortfolioRepository;
import com.example.demo.utils.VoteEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentVoteRepositoryTest {

    @Autowired
    private CommentVoteRepository underTest;

    @Autowired
    private CommentRepository commentRepository;

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
        commentRepository.save(comment);

        CommentVote commentVote = new CommentVote(investor, comment, VoteEnum.UP);
        underTest.save(commentVote);

        List<CommentVote> commentVoteList = underTest.findAll();

        assertEquals(commentVoteList.size(),1);
        assertEquals(commentVoteList.get(0).getVoteDirection().toString(), "UP");

    }



    @Test
    void sameUserShouldNotBeAbleToVoteTwiceOnTheSameComment(){

        Investor investor = new Investor("Jack", "ir@financialshop.com");
        investorRepository.save(investor);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), investor, true);
        portfolioRepository.save(portfolio);

        Comment comment = new Comment(investor, portfolio, Date.valueOf("2022-08-02"), "This porfolio is looking nice");
        commentRepository.save(comment);

        CommentVote commentVoteUp = new CommentVote(investor, comment, VoteEnum.UP);
        underTest.save(commentVoteUp);

        CommentVote commentVoteDown = new CommentVote(investor, comment, VoteEnum.UP);
        assertThrows(DataIntegrityViolationException.class, () -> underTest.saveAndFlush(commentVoteDown));

    }

    @Test
    @Sql("/test-db-setup.sql")
    void findCommentsByPortfolioId(){
        Long id = portfolioRepository.findAll().get(0).getId();
        List<Comment> commentList = commentRepository.findCommentsByPortfolioId(id);

        assertTrue(commentList.size() > 0);

    }

}