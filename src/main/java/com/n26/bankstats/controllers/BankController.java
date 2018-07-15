package com.n26.bankstats.controllers;

import com.n26.bankstats.beans.Statistics;
import com.n26.bankstats.beans.Transaction;
import com.n26.bankstats.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@EnableAutoConfiguration
public class BankController {

    @Autowired
    StatisticsService bankStaticsService;

     /**
     * Every Time a new transaction happened, this endpoint will be called
     **/
    @PostMapping("/transactions")
    public void addTransaction(@RequestBody Transaction transaction, HttpServletResponse response) {
        if (bankStaticsService.saveTransaction(transaction)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        }

        else response.setStatus(HttpServletResponse.SC_NO_CONTENT);


    }

    /**
     * returns the statistic based on the transactions which happened in the last 60 seconds
     **/
    @GetMapping("/statistics")
    Statistics getStatistics() {
        return bankStaticsService.getStatistics();
    }
}
