package com.rateMyPortfolio.applicationUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ApplicationUserControllerTest {
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
        ApplicationUser applicationUser = new ApplicationUser("Carl","Carl@mail.com");
        ApplicationUser applicationUser2 = new ApplicationUser("Vincent","Vincent@mail.com");

        investorRepository.save(applicationUser);
        investorRepository.save(applicationUser2);

        mockMvc.perform(get("/api/investors/")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));


    }

    @Test
    void addingInvestorWorksThroughTheController() throws Exception {
        ApplicationUser applicationUser = new ApplicationUser("Jack","Jack@mail.com");

        mockMvc.perform(post("/api/investors")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(applicationUser)))
                        .andExpect(status().isOk());

        ApplicationUser applicationUserEntity = investorRepository.findByName("Jack");
        assertThat(applicationUserEntity.getEmail()).isEqualTo("Jack@mail.com");
    }

    @Test
    void shouldBeAbleToGetInvestorById() throws Exception {
        ApplicationUser applicationUser = new ApplicationUser("John","John@mail.com");
        String investorEntityId = investorRepository.save(applicationUser).getId().toString();

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

    @Test
    void shouldBeAbleToDeleteInvestorById() throws Exception {
        ApplicationUser applicationUser = new ApplicationUser("John","John@mail.com");
        ApplicationUser applicationUser1 = new ApplicationUser("Jack","Jack@mail.com");

        String investorEntityId = investorRepository.save(applicationUser).getId().toString();
        investorRepository.save(applicationUser1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/investors/" + investorEntityId)
                        .contentType("application/json"))
                .andExpect(status().isOk());

        List<ApplicationUser> applicationUserList = investorRepository.findAll();
        assertThat(applicationUserList.size()).isEqualTo(1);
        assertThat(applicationUserList.get(0).getName()).isEqualTo("Jack");

    }

    @Test
    void updateInvestorById_success() throws Exception {
        ApplicationUser applicationUser = new ApplicationUser("John","John@mail.com");
        ApplicationUser updateApplicationUser = new ApplicationUser("Updated","Jack@mail.com");

        String investorEntityId = investorRepository.save(applicationUser).getId().toString();

        String updateInvestorJson = objectMapper.writeValueAsString(updateApplicationUser);

        mockMvc.perform(put(("/api/investors/" + investorEntityId))
                .content(updateInvestorJson)
                .contentType("application/json"))
                .andExpect(status().isOk());

        List<ApplicationUser> applicationUserList = investorRepository.findAll();
        assertThat(applicationUserList.size()).isEqualTo(1);
        assertThat(applicationUserList.get(0).getName()).isEqualTo("Updated");

    }

}