package com.example.demo.investor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InvestorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InvestorRepository investorRepository;

    @Test
    void addingInvestorWorksThroughTheController() throws Exception {
        Investor investor = new Investor("Jack","Jack@mail.com");

        mockMvc.perform(post("/api/investors/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(investor)))
                        .andExpect(status().isOk());

        Investor investorEntity = investorRepository.findByName("Jack");
        assertThat(investorEntity.getEmail()).isEqualTo("Jack@mail.com");
    }
}