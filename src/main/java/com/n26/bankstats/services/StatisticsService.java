package com.n26.bankstats.services;

import com.n26.bankstats.beans.Statistics;
import com.n26.bankstats.beans.Transaction;

public interface StatisticsService {
    /**
     * saves new transaction in the TransactionCache provided it occured in last 60 seconds
     *
     * @param transaction object to save
     * @return success / failure
     **/
    public boolean saveTransaction(Transaction transaction);

    /**
     * returns the statistic based on the transactions which happened in the last 60 seconds
     *
     * @return Statistics
     **/
    public Statistics getStatistics();
}
