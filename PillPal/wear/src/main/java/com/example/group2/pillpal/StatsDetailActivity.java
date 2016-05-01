package com.example.group2.pillpal;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

public class StatsDetailActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final GridViewPager mGridPager = (GridViewPager) findViewById(R.id.pager);
            final String StatsValues = extras.getString("StatsValues");
            try {
                mGridPager.setAdapter(new PagerAdapter(this, getFragmentManager(), StatsValues));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d("T", "WTF");
        }
    }
}
