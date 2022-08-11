package com.example.demo.shareItem;

import com.example.demo.investor.Investor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShareItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ShareItemRepository shareItemRepository;

    private ShareItem shareItem;

    @BeforeEach
    void init() {
        shareItem = new ShareItem("Amazon", "AMZN", 10.00, Date.valueOf("2022-08-02"));
        shareItemRepository.save(shareItem);

    }



    @AfterEach
    void tearDown(){
        shareItemRepository.deleteAll();
    }


    @Test
    void getShareItems() throws Exception {
        mockMvc.perform(get("/api/shareitem")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void canGetShareItemBySymbolPathVariable() throws Exception {

//        ShareItem shareItem1 = new ShareItem("Amazon", "AMZN", 10.00, Date.valueOf("2022-08-02"));
        String shareItemEntitySymbol = shareItemRepository.save(shareItem).getSymbol();

        mockMvc.perform(get("/api/shareitem/" + shareItemEntitySymbol)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Amazon"));

    }
}