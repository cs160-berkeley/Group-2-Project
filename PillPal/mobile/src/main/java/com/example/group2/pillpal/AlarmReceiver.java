package com.example.group2.pillpal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String time = intent.getStringExtra("DATA");
//        Toast.makeText(context, "Alarm Ringing", Toast.LENGTH_LONG).show();
        Intent sendIntent = new Intent(context, PhoneToWatchService.class);
        sendIntent.putExtra("DATA", "reminder");
        sendIntent.putExtra("time", time);
        context.startService(sendIntent);
    }
}
