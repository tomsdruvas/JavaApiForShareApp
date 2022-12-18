package com.rateMyPortfolio.portfolioVote;

import com.rateMyPortfolio.applicationUser.ApplicationUser;
import com.rateMyPortfolio.applicationUser.InvestorRepository;
import com.rateMyPortfolio.portfolio.Portfolio;
import com.rateMyPortfolio.portfolio.PortfolioRepository;
import com.rateMyPortfolio.utils.VoteEnum;
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
class PortfolioVoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PortfolioRepository portfolioRepository;
    private Portfolio portfolio;
    private Portfolio portfolio2;

    @Autowired
    private InvestorRepository investorRepository;
    private ApplicationUser applicationUser;
    private ApplicationUser applicationUser2;

    @Autowired
    private PortfolioVoteRepository portfolioVoteRepository;

    private PortfolioVote portfolioVote;
    private PortfolioVote portfolioVote2;
    private PortfolioVote portfolioVote3;



    @BeforeEach
    void setUp() {
        applicationUser = new ApplicationUser("Carl","Carl@mail.com");
        applicationUser2 = new ApplicationUser("Vincent","Vincent@mail.com");
        Long investorId = investorRepository.save(applicationUser).getId();
        Long investorId2 = investorRepository.save(applicationUser2).getId();

        portfolio = new Portfolio("Tech stocks updated", Date.valueOf("2022-08-03"), investorId, false);
        portfolio2 = new Portfolio("Food and Beverage", Date.valueOf("2022-08-10"), investorId2, false);
        Long portfolioId = portfolioRepository.save(portfolio).getId();
        Long portfolioId2 = portfolioRepository.save(portfolio2).getId();

        portfolioVote = new PortfolioVote(investorId, portfolioId2, VoteEnum.UP);

        portfolioVoteRepository.save(portfolioVote);




    }

    @AfterEach
    void tearDown() {
        portfolioVoteRepository.deleteAll();
        portfolioRepository.deleteAll();
        investorRepository.deleteAll();
    }

    @Test
    void canCrateNewPortfolioVote_success() throws Exception {
        Long investorId = investorRepository.findAll().get(0).getId();
        Long portfolioId = portfolioRepository.findAll().get(0).getId();
        portfolioVote3 = new PortfolioVote(investorId, portfolioId, VoteEnum.valueOf("DOWN"));

        mockMvc.perform(post("/api/portfoliovote")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(portfolioVote3)))
                .andExpect(status().isCreated());

        PortfolioVote portfolioVoteEntity = portfolioVoteRepository.findAll().get(1);
        assertThat(portfolioVoteEntity.getInvestorId()).isEqualTo(investorId);
        assertThat(portfolioVoteEntity.getPortfolioId()).isEqualTo(portfolioId);
        assertThat(portfolioVoteEntity.getVoteDirection()).isEqualTo(VoteEnum.DOWN);


    }

    @Test
    void updatePortfolioVote_success() throws Exception {
        PortfolioVote portfolioVoteEntity = portfolioVoteRepository.findAll().get(0);

        Long portfolioVoteEntityId = portfolioVoteEntity.getId();

        Long investorId = portfolioVoteEntity.getInvestorId();
        Long portfolioVoteId2 = portfolioVoteEntity.getPortfolioId();
        PortfolioVote updatePortfolioVote = new PortfolioVote(investorId, portfolioVoteId2, VoteEnum.valueOf("UP"));

        String updatePortfolioVoteJson = objectMapper.writeValueAsString(updatePortfolioVote);


        mockMvc.perform(put(("/api/portfoliovote/" + portfolioVoteEntityId))
                        .content(updatePortfolioVoteJson)
                        .contentType("application/json"))
                .andExpect(status().isOk());

        PortfolioVote updatedPortfolioVoteEntity = portfolioVoteRepository.findAll().get(0);
        assertThat(updatedPortfolioVoteEntity.getVoteDirection()).isEqualTo(VoteEnum.UP);

    }

    @Test
    void deletePortfolioVote_success() throws Exception {
        Long portfolioVoteEntityId = portfolioVoteRepository.findAll().get(0).getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/portfoliovote/" + portfolioVoteEntityId)
                        .contentType("application/json"))
                .andExpect(status().isOk());

        assertThat(portfolioVoteRepository.existsById(portfolioVoteEntityId)).isFalse();

    }
}