package com.n26.bankstats.beans;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Transaction implements Serializable {

    /**
     * transaction amount
     */
    private double amount;
    /**
     * unix time format in milliseconds
     */
    private long timestamp;

    @JsonCreator
    public Transaction(@JsonProperty("amount") double amount, @JsonProperty("timestamp") long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
