package com.example.android.cdocs.service;

import com.example.android.cdocs.data.DataManager;
import com.example.android.cdocs.ui.model.Docs;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * to handle the creation, rotation, and updating of registration tokens.
 * This is required for sending to specific devices or for creating device groups.
 */
public class FcmService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            DataManager dataManager = DataManager.getInstance(this);
            // Insert single item into database
            dataManager.insertSingleItemToDatabase(
                    new Docs(
                            remoteMessage.getData().get("TITLE"),
                            remoteMessage.getData().get("TYPE")));
        }
    }
}
