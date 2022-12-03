package com.example.demo.commentVote;

import com.example.demo.comment.Comment;
import com.example.demo.comment.CommentRepository;


import com.example.demo.investor.Investor;
import com.example.demo.investor.InvestorRepository;
import com.example.demo.portfolio.Portfolio;
import com.example.demo.portfolio.PortfolioRepository;

import com.example.demo.utils.VoteEnum;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CommentVoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentVoteRepository commentVoteRepository;
    private CommentVote commentVote;
    private CommentVote commentVote2;




    @Autowired
    private InvestorRepository investorRepository;
    private Investor investor;
    private Investor investor2;

    @Autowired
    private CommentRepository commentRepository;

    private Comment comment;
    private Comment comment2;


    @Autowired
    private PortfolioRepository portfolioRepository;
    private Portfolio portfolio;
    private Portfolio portfolio2;

    @BeforeEach
    void setUp() {
        investor = new Investor("Carl","Carl@mail.com");
        investor2 = new Investor("Vincent","Vincent@mail.com");
        Long investorId = investorRepository.save(investor).getId();
        Long investorId2 = investorRepository.save(investor2).getId();

        portfolio = new Portfolio("Tech stocks updated", Date.valueOf("2022-08-03"), investorId, false);
        portfolio2 = new Portfolio("Food and Beverage", Date.valueOf("2022-08-10"), investorId2, false);
        Long portfolioId = portfolioRepository.save(portfolio).getId();
        Long portfolioId2 = portfolioRepository.save(portfolio2).getId();

        comment = new Comment(investorId, portfolioId, Date.valueOf("2022-02-20"), "This portfolio has so much potential");
        comment2 = new Comment(investorId, portfolioId2, Date.valueOf("2022-02-21"), "This portfolio is not diversified enough for my taste");

        Long commentId = commentRepository.save(comment).getId();

        commentVote = new CommentVote(investorId2, commentId, VoteEnum.valueOf("DOWN"));
        commentVoteRepository.save(commentVote);



    }

    @AfterEach
    void tearDown() {
        commentVoteRepository.deleteAll();
        commentRepository.deleteAll();
        portfolioRepository.deleteAll();
        investorRepository.deleteAll();
    }

    @Test
    void canCrateNewCommentVote_success() throws Exception {
        Long investorId = investorRepository.findAll().get(0).getId();
        Long commentId2 = commentRepository.findAll().get(0).getId();
        commentVote2 = new CommentVote(investorId, commentId2, VoteEnum.valueOf("DOWN"));

        mockMvc.perform(post("/api/commentvote")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(commentVote2)))
                .andExpect(status().isCreated());

        CommentVote commentVoteEntity = commentVoteRepository.findAll().get(1);
        assertThat(commentVoteEntity.getInvestorId()).isEqualTo(investorId);
        assertThat(commentVoteEntity.getCommentId()).isEqualTo(commentId2);
        assertThat(commentVoteEntity.getVoteDirection()).isEqualTo(VoteEnum.DOWN);


    }

    @Test
    void updateCommentVote_success() throws Exception {
        CommentVote commentVoteEntity = commentVoteRepository.findAll().get(0);

        Long commentVoteEntityId = commentVoteEntity.getId();

        Long investorId = commentVoteEntity.getInvestorId();
        Long commentId2 = commentVoteEntity.getCommentId();
        CommentVote updateCommentVote = new CommentVote(investorId, commentId2, VoteEnum.valueOf("UP"));

        String updateCommentVoteJson = objectMapper.writeValueAsString(updateCommentVote);


        mockMvc.perform(put(("/api/commentvote/" + commentVoteEntityId))
                        .content(updateCommentVoteJson)
                        .contentType("application/json"))
                .andExpect(status().isOk());

        CommentVote updatedCommentVoteEntity = commentVoteRepository.findAll().get(0);
        assertThat(updatedCommentVoteEntity.getVoteDirection()).isEqualTo(VoteEnum.UP);

    }

    @Test
    void deleteCommentVote() throws Exception {
        Long commentVoteEntityId = commentVoteRepository.findAll().get(0).getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/commentvote/" + commentVoteEntityId)
                        .contentType("application/json"))
                .andExpect(status().isOk());

        assertThat(commentVoteRepository.existsById(commentVoteEntityId)).isFalse();

    }
}