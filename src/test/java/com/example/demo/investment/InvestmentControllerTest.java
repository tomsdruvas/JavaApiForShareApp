package com.example.demo.investment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.investor.Investor;
import com.example.demo.investor.InvestorRepository;
import com.example.demo.portfolio.Portfolio;
import com.example.demo.portfolio.PortfolioRepository;
import com.example.demo.shareItem.ShareItem;
import com.example.demo.shareItem.ShareItemRepository;
import com.example.demo.utils.CurrencyEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class InvestmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InvestmentRepository investmentRepository;
    private Investment investment;
    private Investment investment2;

    private Investment investment3;


    @Autowired
    private InvestorRepository investorRepository;
    private Investor investor;
    private Investor investor2;

    @Autowired
    private ShareItemRepository shareItemRepository;

    private ShareItem shareItem;
    private ShareItem shareItem2;

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

        shareItem = new ShareItem("Amazon", "AMZN", new BigDecimal("10.00"), CurrencyEnum.USD, Date.valueOf("2022-08-02"), LocalDateTime.now(), true);
        shareItem2 = new ShareItem("Microsoft", "MSFT", new BigDecimal("30.00"), CurrencyEnum.USD, Date.valueOf("2022-08-02"), LocalDateTime.now(), true);
        Long shareItemId = shareItemRepository.save(shareItem).getId();
        Long shareItemId2 = shareItemRepository.save(shareItem2).getId();

        portfolio = new Portfolio("Tech stocks updated", Date.valueOf("2022-08-03"), investorId, false);
        portfolio2 = new Portfolio("Food and Beverage", Date.valueOf("2022-08-10"), investorId2, false);
        Long portfolioId = portfolioRepository.save(portfolio).getId();
        Long portfolioId2 = portfolioRepository.save(portfolio2).getId();

        investment = new Investment(shareItemId, 3.00, 10.00, Date.valueOf("2022-08-02" ), portfolioId);
        investment2 = new Investment(shareItemId2, 8.00, 15.00, Date.valueOf("2022-08-02" ), portfolioId2);
        investmentRepository.save(investment);
        investmentRepository.save(investment2);
    }

    @AfterEach
    void tearDown() {
        investmentRepository.deleteAll();
        portfolioRepository.deleteAll();
        shareItemRepository.deleteAll();
        investorRepository.deleteAll();
    }

    @Test
    void shouldBeAbleToGetAListOfInvestments() throws Exception {

        mockMvc.perform(get("/api/investment")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    void should_ReturnError_When_InvestorNotFound() throws Exception {
        mockMvc.perform(get("/api/investment/12345")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void canGetInvestmentById_success() throws Exception {
        Long investmentEntityId = investmentRepository.findAll().get(0).getId();

        mockMvc.perform(get("/api/investment/" + investmentEntityId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(3));

    }

    @Test
    void canCrateNewInvestment_success() throws Exception {

        Long shareItemId = shareItemRepository.findAll().get(0).getId();
        Long portfolioId = portfolioRepository.findAll().get(0).getId();
        investment3 = new Investment(shareItemId, 6.00, 20.00, Date.valueOf("2022-08-03" ), portfolioId);

        mockMvc.perform(post("/api/investment")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(investment3)))
                .andExpect(status().isCreated());

        Investment investmentEntity = investmentRepository.findAll().get(2);
        assertThat(investmentEntity.getQuantity()).isEqualTo(6.00);
        assertThat(investmentEntity.getEntryPrice()).isEqualTo(20.00);
    }

    @Test
    void canUpdateInvestment_success() throws Exception {
        Long shareItemId = shareItemRepository.findAll().get(0).getId();
        Long portfolioId = portfolioRepository.findAll().get(0).getId();
        Investment updateInvestment = new Investment(shareItemId, 8.00, 23.00, Date.valueOf("2022-08-03" ), portfolioId);

        String investmentEntityId = investmentRepository.findAll().get(0).getId().toString();

        String updateInvestmentJson = objectMapper.writeValueAsString(updateInvestment);

        mockMvc.perform(put(("/api/investment/" + investmentEntityId))
                        .content(updateInvestmentJson)
                        .contentType("application/json"))
                .andExpect(status().isOk());

        Investment updatedInvestment = investmentRepository.findAll().get(0);
        assertThat(updatedInvestment.getQuantity()).isEqualTo(8.00);
        assertThat(updatedInvestment.getEntryPrice()).isEqualTo(23.00);
    }

    @Test
    void canDeleteInvestment_success() throws Exception {

        String investmentEntityId = investmentRepository.findAll().get(0).getId().toString();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/investment/" + investmentEntityId)
                        .contentType("application/json"))
                .andExpect(status().isOk());

        List<Investment> investmentList = investmentRepository.findAll();
        assertThat(investmentList.size()).isEqualTo(1);
        assertThat(investmentList.get(0).getQuantity()).isEqualTo(8);

    }
}