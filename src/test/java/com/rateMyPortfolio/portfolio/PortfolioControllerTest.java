package com.rateMyPortfolio.portfolio;

import com.rateMyPortfolio.applicationUser.ApplicationUser;
import com.rateMyPortfolio.applicationUser.InvestorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
@AutoConfigureMockMvc(addFilters = false)
class PortfolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private InvestorRepository investorRepository;

    private ApplicationUser applicationUser;
    private ApplicationUser applicationUser2;
    private Portfolio portfolio;
    private Portfolio portfolio2;


    @BeforeEach
    void init() {
        applicationUser = new ApplicationUser("Jack", "Jack@mail.com");
        applicationUser2 = new ApplicationUser("John", "John@mail.com");
        Long id = investorRepository.save(applicationUser).getId();
        Long id2 = investorRepository.save(applicationUser2).getId();


        portfolio = new Portfolio("Tech stocks updated", Date.valueOf("2022-08-03"), id, false);
        portfolio2 = new Portfolio("Food and Beverage", Date.valueOf("2022-08-10"), id2, false);
        portfolioRepository.save(portfolio);
        portfolioRepository.save(portfolio2);
    }

    @AfterEach
    void tearDown() {
        portfolioRepository.deleteAll();
        investorRepository.deleteAll();
    }

    @Test
    void shouldBeAbleToGetAListOfPortfolios() throws Exception {
        mockMvc.perform(get("/api/portfolio")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getPortfolioByIdTest() throws Exception {
        String portfolioEntityId = portfolio.getId().toString();

        mockMvc.perform(get("/api/portfolio/" + portfolioEntityId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tech stocks updated"));

    }

    @Test
    void newPortfolioTest() throws Exception {

        Portfolio portfolio3 = new Portfolio("Shopping stocks", Date.valueOf("2022-08-10"), applicationUser2.getId(), false);

        mockMvc.perform(post("/api/portfolio")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(portfolio3)))
                .andExpect(status().isCreated());

        Portfolio portfolioEntity = portfolioRepository.findPortfolioByName(portfolio3.getName());
        assertThat(portfolioEntity.getIsPublic()).isEqualTo(false);
        assertThat(portfolioEntity.getApplicationUser().getName()).isEqualTo("John");
    }

    @Test
    void deletePortfolioTest() throws Exception {
        String portfolioEntityId = portfolio.getId().toString();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/portfolio/" + portfolioEntityId)
                        .contentType("application/json"))
                .andExpect(status().isOk());

        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList.size()).isEqualTo(1);
        assertThat(portfolioList.get(0).getName()).isEqualTo("Food and Beverage");
    }

    @Test
    void should_ReturnError_When_InvestorNotFound() throws Exception {
        mockMvc.perform(get("/api/portfolio/12345")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updatePortfolioById_success() throws Exception {
        Portfolio updatePortfolio = new Portfolio("Tech stocks updated", Date.valueOf("2022-08-03"), applicationUser.getId(), true);

        String portfolioEntityId = portfolio.getId().toString();

        String updatePortfolioJson = objectMapper.writeValueAsString(updatePortfolio);

        mockMvc.perform(put(("/api/portfolio/" + portfolioEntityId))
                        .content(updatePortfolioJson)
                        .contentType("application/json"))
                .andExpect(status().isOk());

        Portfolio updatedPortfolio = portfolioRepository.findPortfolioById(portfolio.getId());
        assertThat(updatedPortfolio.getName()).isEqualTo("Tech stocks updated");
        assertThat(updatedPortfolio.getIsPublic()).isEqualTo(true);

    }
}