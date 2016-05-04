package com.example.group2.pillpal;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class PhoneListenerService extends WearableListenerService {
    private static UserInstance currentUser;

    public PhoneListenerService() {
        currentUser = UserInstance.getInstance();
    }

    private static final String REFILL_ARRIVAL = "/refill/confirmation";
    private static final String SNOOZE = "/snooze";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        System.out.println("UGHHHH");
        if (messageEvent.getPath().equalsIgnoreCase( REFILL_ARRIVAL )) {
            currentUser.refillHistory.add(currentUser.currentRefillRequest);
            currentUser.currentRefillRequest = new HashMap<String, String>();
            currentUser.refillRequested = false;
            // UPDATE USER
        } else if (messageEvent.getPath().equalsIgnoreCase( SNOOZE )) {
            // Snooze Alarm
        }
    }
}
