package com.n26.bankstats.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.bankstats.BankStatsApp;
import com.n26.bankstats.beans.Statistics;
import com.n26.bankstats.beans.Transaction;
import com.n26.bankstats.services.StatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BankStatsApp.class)

public class BankControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private StatisticsService bankStaticsService;


    @Test
    public void getStatistics() throws Exception {
        Statistics statistics = new Statistics(4, 2, 1, 3, 2);
        when(bankStaticsService.getStatistics()).thenReturn(statistics);
        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("sum", is(4.0)))
                .andExpect(jsonPath("average", is(2.0)))
                .andExpect(jsonPath("min", is(1.0)))
                .andExpect(jsonPath("max", is(3.0)))
                .andExpect(jsonPath("count", is(2.0)));

        verify(bankStaticsService, times(1)).getStatistics();
        verifyNoMoreInteractions(bankStaticsService);
    }


    @Test
    public void addTransaction() throws Exception {
        Transaction transaction = new Transaction(12.3, Instant.now().minusSeconds(1).toEpochMilli());
        when(bankStaticsService.saveTransaction(any(Transaction.class))).thenReturn(true);

        mockMvc.perform(post("/transactions").characterEncoding(APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(transaction))
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andReturn();

        verify(bankStaticsService, times(1)).saveTransaction(any(Transaction.class));
        verifyNoMoreInteractions(bankStaticsService);
    }

    @Test
    public void dontAddTransaction() throws Exception {
        Transaction transaction = new Transaction(12.3, Instant.now().minusSeconds(67).toEpochMilli());
        when(bankStaticsService.saveTransaction(any(Transaction.class))).thenReturn(false);

        mockMvc.perform(post("/transactions").characterEncoding(APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(transaction))
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(bankStaticsService, times(1)).saveTransaction(any(Transaction.class));
        verifyNoMoreInteractions(bankStaticsService);
    }

}