package com.n26.bankstats.beans;

import java.io.Serializable;

public class Statistics implements Serializable {

    /**
     * total sum of transaction value in the last 60 seconds
     **/
    private double sum;
    /**
     * average amount of transaction value in the last 60 seconds
     **/
    private double average;
    /**
     * single lowest transaction value in the last 60 seconds
     **/
    private double min;

    /**
     * single highest transaction value in the last 60 seconds
     **/
    private double max;
    /**
     * total number of transactions happened in the last 60
     * seconds
     **/
    private double count;

    public Statistics(double sum, double average, double min, double max, double count) {
        this.sum = sum;
        this.average = average;
        this.min = min;
        this.max = max;
        this.count = count;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Statistics.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Statistics other = (Statistics) obj;

        if (this.average != other.average) {
            return false;
        }
        if (this.sum != other.sum) {
            return false;
        }
        if (this.min    != other.min) {
            return false;
        }
        if (this.max != other.max) {
            return false;
        }
        if (this.count != other.count) {
            return false;
        }
        return true;
    }

}
