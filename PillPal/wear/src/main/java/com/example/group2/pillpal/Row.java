package com.example.group2.pillpal;


import java.util.ArrayList;

public class Row {

    ArrayList<StatsItem> mPagesRow = new ArrayList<StatsItem>();

    public void addPages(StatsItem page) {
        mPagesRow.add(page);
    }

    public StatsItem getPages(int index) {
        return mPagesRow.get(index);
    }

    public int size(){
        return mPagesRow.size();
    }
}