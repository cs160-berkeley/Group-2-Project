package com.example.group2.pillpal;


/**
 * Created by Hamza on 3/6/16.
 */
public class StatsItem {
    private String hormone;
    private String period;
    private String difference;
    private String status;


    public StatsItem(String hormone, String period, String difference, String status) {
        this.hormone = hormone;
        this.period = period;
        this.difference = difference;
        this.status = status;
    }

    public String getHormone() {
        return this.hormone;
    }

    public String getPeriod() {
        return this.period;
    }

    public String getDifference() {
        return this.difference;
    }

    public String getStatus() {
        return this.status;
    }

}
