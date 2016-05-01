package com.example.group2.pillpal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class RefillConfirmationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refill_confirmation);


        Intent intent = getIntent();
        final String arrival_date = intent.getStringExtra("arrival_date");

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                TextView status_text = (TextView) findViewById(R.id.confirmation_text);
                status_text.setText("Your package has successfully been delivered!");
            }
        });

        Intent confirmation_intent = new Intent(this, WatchToPhoneService.class);
        confirmation_intent.putExtra("DATA", "refill/confirmation");
        this.startService(confirmation_intent);
    }
}
