package com.example.demo.investor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InvestorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InvestorRepository investorRepository;

    @AfterEach
    void tearDown(){
        investorRepository.deleteAll();
    }

    @Test
    void shouldBeAbleToGetAListOfInvestors() throws Exception {
        Investor investor = new Investor("Carl","Carl@mail.com");
        Investor investor2 = new Investor("Vincent","Vincent@mail.com");

        investorRepository.save(investor);
        investorRepository.save(investor2);

        mockMvc.perform(get("/api/investors/")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));


    }

    @Test
    void addingInvestorWorksThroughTheController() throws Exception {
        Investor investor = new Investor("Jack","Jack@mail.com");

        mockMvc.perform(post("/api/investors")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(investor)))
                        .andExpect(status().isOk());

        Investor investorEntity = investorRepository.findByName("Jack");
        assertThat(investorEntity.getEmail()).isEqualTo("Jack@mail.com");
    }

    @Test
    void shouldBeAbleToGetInvestorById() throws Exception {
        Investor investor = new Investor("John","John@mail.com");
        String investorEntityId = investorRepository.save(investor).getId().toString();

        mockMvc.perform(get("/api/investors/" + investorEntityId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));

    }

    @Test
    void should_ReturnError_When_InvestorNotFound() throws Exception {
        mockMvc.perform(get("/api/investors/12345")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}