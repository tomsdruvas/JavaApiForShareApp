package com.example.demo.comment;

import com.example.demo.investment.Investment;
import com.example.demo.investor.Investor;
import com.example.demo.investor.InvestorRepository;
import com.example.demo.portfolio.Portfolio;
import com.example.demo.portfolio.PortfolioRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentRepository commentRepository;

    private Comment comment;
    private Comment comment2;
    private Comment comment3;

    private Comment comment4;

    @Autowired
    private PortfolioRepository portfolioRepository;
    private Portfolio portfolio;
    private Portfolio portfolio2;

    @Autowired
    private InvestorRepository investorRepository;
    private Investor investor;
    private Investor investor2;

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
        comment3 = new Comment(investorId, portfolioId2,  Date.valueOf("2022-02-24"), "This looks great");
        comment4 = new Comment(investorId, portfolioId2,  Date.valueOf("2022-02-23"), "Commenting for reference");

        commentRepository.save(comment);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        portfolioRepository.deleteAll();
        investorRepository.deleteAll();
    }

    @Test
    void shouldBeAbleToGetAllCommentsList() throws Exception {
        mockMvc.perform(get("/api/comment")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getAllByPortfolioId() throws Exception {
        Long portfolioId = portfolioRepository.findAll().get(0).getId();
        mockMvc.perform(get("/api/comment/portfolio/" + portfolioId)
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void canGetCommentById_success() throws Exception {

        Long commentId = commentRepository.findAll().get(0).getId();

        mockMvc.perform(get("/api/comment/" + commentId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("This portfolio has so much potential"));
    }

    @Test
    void canCreateNewComment_success() throws Exception {
        mockMvc.perform(post("/api/comment")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(comment4)))
                .andExpect(status().isCreated());

        List<Comment> commentListEntity = commentRepository.findAll();
        assertThat(commentListEntity.size()).isEqualTo(4);
        assertThat(commentListEntity.get(3).getContent()).isEqualTo("Commenting for reference");


    }

    @Test
    void canUpdateComment_success() throws Exception {
        Comment commentEntity = commentRepository.findAll().get(0);
        Long investorId = commentEntity.getInvestorId();
        Long portfolioId = commentEntity.getPortfolioId();
        Comment commentUpdate = new Comment(investorId, portfolioId, Date.valueOf("2022-02-20"), "Updated comment");

        mockMvc.perform(put(("/api/comment/" + commentEntity.getId()))
                        .content(objectMapper.writeValueAsString(commentUpdate))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Updated comment"));

        List<Comment> commentListEntity = commentRepository.findAll();
        assertThat(commentListEntity.get(0).getContent()).isEqualTo("Updated comment");

    }

    @Test
    void canDeleteComment_success() throws Exception {
        Long commentEntityId = commentRepository.findAll().get(0).getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/comment/" + commentEntityId)
                        .contentType("application/json"))
                .andExpect(status().isOk());

        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList.size()).isEqualTo(2);
    }
}