package com.example.group2.pillpal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;

public class SnoozeActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
    }

    public void snooze(final View view) {
        Intent i = new Intent(SnoozeActivity.this, WatchToPhoneService.class);
        i.putExtra("DATA", "snooze");
        this.startService(i);
    }

    public void close(final View view) {
        Intent i = new Intent(SnoozeActivity.this, StatsDetailActivity.class);
        startActivity(i);
    }
}
