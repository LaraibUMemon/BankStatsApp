package com.n26.bankstats.services;

import com.n26.bankstats.BankStatsApp;
import com.n26.bankstats.beans.Statistics;
import com.n26.bankstats.beans.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.junit.Assert.*;
@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BankStatsApp.class)

public class StaticsServiceImplTest {
    @Autowired
    StatisticsService bankStaticsService;
    @Test
    public void dontSaveTransactionTest() {
        Transaction t= new Transaction(34.5, Instant.now().minusSeconds(61).toEpochMilli());
        boolean isSaved = bankStaticsService.saveTransaction(t);
        Assert.assertFalse(isSaved);
    }
    @Test
    public void saveTransactionTest() {
        Transaction t= new Transaction(34.5, Instant.now().minusSeconds(5).toEpochMilli());
        boolean isSaved = bankStaticsService.saveTransaction(t);
        Assert.assertTrue(isSaved);
    }


    @Test
    public void getStatistics() {
        Transaction t1= new Transaction(1, Instant.now().minusSeconds(5).toEpochMilli());
        Transaction t2= new Transaction(2, Instant.now().minusSeconds(61).toEpochMilli());
        Transaction t3= new Transaction(3, Instant.now().minusSeconds(2).toEpochMilli());

        bankStaticsService.saveTransaction(t1);
        bankStaticsService.saveTransaction(t2);
        bankStaticsService.saveTransaction(t3);
        Statistics correctStats =  new Statistics( 4,2,1,3,2);
        Statistics stats  = bankStaticsService.getStatistics();
        Assert.assertTrue(stats.equals(correctStats));

    }
}