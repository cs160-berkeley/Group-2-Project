package com.example.group2.pillpal;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

public class WatchToPhoneService extends Service {
    public WatchToPhoneService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private GoogleApiClient mWatchApiClient;
    private List<Node> nodes = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mWatchApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                    }
                    @Override
                    public void onConnectionSuspended(int cause) {
                    }
                }).build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWatchApiClient.disconnect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();

        if (extras != null) {
            final String data = extras.getString("DATA");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mWatchApiClient.connect();
                    if (data.equals("refill/confirmation")) {
                        System.out.println("GOINGBACKTOPHONE!!!");
                        sendMessage("/refill/confirmation", data);
                    } else if (data.equals("snooze")) {
                        sendMessage("/snooze", data);
                    }
                }
            }).start();
        }
        return START_STICKY;
    }

    private void sendMessage(final String path, final String text ) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mWatchApiClient).await();
                for (Node node : nodes.getNodes()) {
                    Log.i("PATHNAME: ", path);
                    System.out.println(node);
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mWatchApiClient, node.getId(), path, text.getBytes()
                    ).await();
                }
            }
        }).start();
    }
}
