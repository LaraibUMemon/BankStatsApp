package com.n26.bankstats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class BankStatsApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(BankStatsApp.class);
    }
}
