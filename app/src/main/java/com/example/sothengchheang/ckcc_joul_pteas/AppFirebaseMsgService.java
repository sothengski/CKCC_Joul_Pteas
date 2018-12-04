package com.example.sothengchheang.ckcc_joul_pteas;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class AppFirebaseMsgService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String message = remoteMessage.getNotification().getBody();
        Log.d("app","Message received: " + message);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        Log.d("app","onNewToken: "+ s);
    }
}
