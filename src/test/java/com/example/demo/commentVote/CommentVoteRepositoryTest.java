package com.example.demo.commentVote;

import com.example.demo.comment.Comment;
import com.example.demo.comment.CommentRepository;
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
    void shouldBeAbleToCreateACommentVote_success(){

        Investor investor = new Investor("Jack", "ir@financialshop.com");
        investorRepository.save(investor);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), investor, true);
        portfolioRepository.save(portfolio);

        Comment comment = new Comment(investor, portfolio, Date.valueOf("2022-08-02"), "This portfolio is looking nice");
        commentRepository.save(comment);

        CommentVote commentVote = new CommentVote(investor, comment, VoteEnum.UP);
        underTest.save(commentVote);

        List<CommentVote> commentVoteList = underTest.findAll();

        assertEquals(commentVoteList.size(),1);
        assertEquals(commentVoteList.get(0).getVoteDirection().toString(), "UP");

    }

    @Test
    void shouldBeAbleToUpdateACommentVote_success(){

        Investor investor = new Investor("Jack", "ir@financialshop.com");
        investorRepository.save(investor);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), investor, true);
        portfolioRepository.save(portfolio);

        Comment comment = new Comment(investor, portfolio, Date.valueOf("2022-08-02"), "This portfolio is looking nice");
        commentRepository.save(comment);

        CommentVote commentVote = new CommentVote(investor, comment, VoteEnum.UP);
        underTest.save(commentVote);

        List<CommentVote> commentVoteList = underTest.findAll();

        CommentVote commentVoteEntity = commentVoteList.get(0);
        commentVoteEntity.setVoteDirection(VoteEnum.DOWN);
        underTest.save(commentVoteEntity);

        List<CommentVote> updatedCommentVoteList = underTest.findAll();
        assertEquals(updatedCommentVoteList.get(0).getVoteDirection().toString(), "DOWN");

    }



    @Test
    void sameUserShouldNotBeAbleToVoteTwiceOnTheSameComment_Exception  (){

        Investor investor = new Investor("Jack", "ir@financialshop.com");
        investorRepository.save(investor);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), investor, true);
        portfolioRepository.save(portfolio);

        Comment comment = new Comment(investor, portfolio, Date.valueOf("2022-08-02"), "This portfolio is looking nice");
        commentRepository.save(comment);

        CommentVote commentVoteUp = new CommentVote(investor.getId(), comment.getId(), VoteEnum.UP);
        underTest.save(commentVoteUp);

        CommentVote commentVoteDown = new CommentVote(investor.getId(), comment.getId(), VoteEnum.DOWN);
        assertThrows(DataIntegrityViolationException.class, () -> underTest.saveAndFlush(commentVoteDown));

    }

    @Test
    void shouldBeAbleToDeleteACommentVoteById_success(){

        Investor investor = new Investor("Jack", "ir@financialshop.com");
        investorRepository.save(investor);

        Portfolio portfolio = new Portfolio("Tech Stocks", Date.valueOf("2022-08-02"), investor, true);
        portfolioRepository.save(portfolio);

        Comment comment = new Comment(investor, portfolio, Date.valueOf("2022-08-02"), "This portfolio is looking nice");
        commentRepository.save(comment);

        CommentVote commentVote = new CommentVote(investor, comment, VoteEnum.UP);
        underTest.save(commentVote);

        List<CommentVote> commentVoteList = underTest.findAll();

        underTest.deleteById(commentVoteList.get(0).getId());

        List<CommentVote> updatedCommentVoteList = underTest.findAll();
        assertEquals(updatedCommentVoteList.size(), 0);

    }



}