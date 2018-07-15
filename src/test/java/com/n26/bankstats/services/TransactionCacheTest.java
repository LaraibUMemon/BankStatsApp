package com.n26.bankstats.services;

import com.n26.bankstats.beans.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.List;

public class TransactionCacheTest {

    @Test
    public void clearStaleTransactions() throws InterruptedException {

        TransactionCache transactionCache = new TransactionCache();

        Transaction t1 = new Transaction(1, Instant.now().minusSeconds(59).toEpochMilli());
        transactionCache.saveTransaction(t1);

        Thread.sleep(5000);

        transactionCache.clearStaleTransactions();

        Assert.assertTrue(transactionCache.getCache().isEmpty());
    }

    @Test
    public void clearPartialStaleTransactions() throws InterruptedException {

        TransactionCache transactionCache = new TransactionCache();

        Transaction t1 = new Transaction(1, Instant.now().minusSeconds(59).toEpochMilli());
        transactionCache.saveTransaction(t1);

        Transaction t2 = new Transaction(3, Instant.now().minusSeconds(2).toEpochMilli());
        transactionCache.saveTransaction(t2);

        Thread.sleep(5000);

        transactionCache.clearStaleTransactions();

        Assert.assertFalse(transactionCache.getCache().isEmpty());
    }

    @Test
    public void saveTransaction() {
        TransactionCache transactionCache = new TransactionCache();

        Transaction t1 = new Transaction(1, Instant.now().minusSeconds(1).toEpochMilli());
        transactionCache.saveTransaction(t1);
        Assert.assertFalse(transactionCache.getCache().isEmpty());
    }

    @Test
    public void addTransactionToCache() {
        TransactionCache transactionCache = new TransactionCache();

        Transaction t1 = new Transaction(1, Instant.now().minusSeconds(1).toEpochMilli());
        transactionCache.addTransactionToCache(t1);
        Transaction t2 = new Transaction(2, Instant.now().minusSeconds(15).toEpochMilli());
        transactionCache.addTransactionToCache(t2);
        Transaction t3 = new Transaction(3, Instant.now().minusSeconds(15).toEpochMilli());
        transactionCache.addTransactionToCache(t3);


        long epochSecond = Instant.ofEpochMilli(t1.getTimestamp()).getEpochSecond();
        List<Transaction> instanceTransactions = transactionCache.getCache().get(epochSecond);
        Assert.assertEquals(instanceTransactions.size(), 1);

        epochSecond = Instant.ofEpochMilli(t2.getTimestamp()).getEpochSecond();
        instanceTransactions = transactionCache.getCache().get(epochSecond);
        Assert.assertEquals(instanceTransactions.size(), 2);

    }

    @Test
    public void isWithinStatsDuration() {
        TransactionCache transactionCache = new TransactionCache();
        Assert.assertTrue(transactionCache.isWithinStatsDuration(Instant.now().minusSeconds(1).toEpochMilli()));

    }


    @Test
    public void isNotWithinStatsDuration() {
        TransactionCache transactionCache = new TransactionCache();
        Assert.assertFalse(transactionCache.isWithinStatsDuration(Instant.now().minusSeconds(61).toEpochMilli()));

    }


    @Test
    public void getTransaction() {
        TransactionCache transactionCache = new TransactionCache();

        Transaction t1 = new Transaction(1, Instant.now().minusSeconds(1).toEpochMilli());
        transactionCache.saveTransaction(t1);

        Transaction t2 = new Transaction(3, Instant.now().minusSeconds(2).toEpochMilli());

        transactionCache.saveTransaction(t2);
        List<Transaction> transactionList = transactionCache.getTransaction();
        Assert.assertEquals(transactionList.size(), 2);
    }
}