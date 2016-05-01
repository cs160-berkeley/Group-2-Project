package com.example.group2.pillpal;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

public class PhoneListenerService extends WearableListenerService {
    public PhoneListenerService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Hmmm");
        return START_STICKY;
    }

    private static final String REFILL_ARRIVAL = "/refill/confirmation";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        System.out.println("UGHHHH");
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if (messageEvent.getPath().equalsIgnoreCase( REFILL_ARRIVAL )) {
            // UPDATE USER
        }

    }
}
