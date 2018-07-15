package com.n26.bankstats.services;

import com.n26.bankstats.beans.Transaction;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

public class TransactionCache {
    private static final long statsDuration = 60L;


    ConcurrentNavigableMap<Long, List<Transaction>> cache = new ConcurrentSkipListMap<Long, List<Transaction>>();


    @Scheduled(fixedDelay = 5000)
    public void clearStaleTransactions() {
    if(!cache.isEmpty()){
        long relativeSecond = getRelativeEpochSecond();
        cache.headMap(relativeSecond).clear();
    }
    }

    private long getRelativeEpochSecond() {
        return Instant.now().minusSeconds(statsDuration).getEpochSecond();
    }

    public boolean saveTransaction(Transaction transaction) {
        boolean proceed = isWithinStatsDuration(transaction.getTimestamp());
        if (proceed) {
            addTransactionToCache(transaction);
            return true;
        }
        return false;
    }

    public void addTransactionToCache(Transaction transaction) {
//        long seconds = ChronoUnit.SECONDS.between(Instant.now(), Instant.ofEpochSecond(transaction.getTimestamp()));

        long epochSecond = Instant.ofEpochMilli(transaction.getTimestamp()).getEpochSecond();
        List<Transaction> instanceTransactions = cache.get(epochSecond);
        if (instanceTransactions == null) {
            instanceTransactions = new ArrayList<Transaction>();

        }
        instanceTransactions.add(transaction);
        cache.put(epochSecond, instanceTransactions);
    }

    public boolean isWithinStatsDuration(long timestamp) {
//       return Instant.ofEpochMilli(timestamp).until(Instant.now(),ChronoUnit.DAYS) > statsDuration ? false:true;
        return  ChronoUnit.SECONDS.between(Instant.ofEpochMilli(timestamp),Instant.now() ) > statsDuration ? false : true;
    }

    public List<Transaction> getTransaction() {
        long relativeSecond = getRelativeEpochSecond();
        return cache.tailMap(relativeSecond).values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    public ConcurrentNavigableMap<Long, List<Transaction>> getCache() {
        return cache;
    }

}
