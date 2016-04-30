package com.example.group2.pillpal;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class TestAdapter extends FragmentStatePagerAdapter {

    public TestAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Daily";
        } else if (position == 1) {
            return "Weekly";
        } else {
            return "Monthly";
        }
    }
    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment[] myLovelyFragments = new Fragment[3];
        myLovelyFragments[0] = new DailyStatsFragment();
        myLovelyFragments[1] = new WeeklyStatsFragment();
        myLovelyFragments[2] = new StatsFragment();

        return myLovelyFragments[position];
    }
}