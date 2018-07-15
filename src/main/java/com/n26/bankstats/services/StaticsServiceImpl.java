package com.n26.bankstats.services;

import com.n26.bankstats.beans.Statistics;
import com.n26.bankstats.beans.Transaction;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaticsServiceImpl implements StatisticsService {
    private TransactionCache transactionCache   = new TransactionCache();

    @Override
    public boolean saveTransaction(Transaction transaction) {
        return transactionCache.saveTransaction(transaction);
    }

    @Override
    public Statistics getStatistics() {
        List<Transaction> transactions = transactionCache.getTransaction();
        if (!transactions.isEmpty()) {
            DoubleSummaryStatistics stats = transactions.parallelStream().collect(Collectors.summarizingDouble(Transaction::getAmount));
            return new Statistics(stats.getSum(), stats.getAverage(),
                    stats.getMin() == Double.POSITIVE_INFINITY ? 0 : stats.getMin(),
                    stats.getMax() == Double.NEGATIVE_INFINITY ? 0 : stats.getMax(),
                    stats.getCount());
        } else return null;
    }
}
