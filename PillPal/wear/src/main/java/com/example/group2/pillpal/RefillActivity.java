package com.example.group2.pillpal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

public class RefillActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refill_status);


        Intent intent = getIntent();
        final String arrival_date = intent.getStringExtra("arrival_date");

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                TextView confirmation_text = (TextView) findViewById(R.id.confirmation_text);
                confirmation_text.setText("Your package has been shipped! Expected to arrive on: " + arrival_date);
            }
        });


    }

}
