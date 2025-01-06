package com.ticketpro.parking.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ticketpro.model.Feature;
import com.ticketpro.util.FirebaseDBUpdater;

public class FirebaseReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Feature.isFeatureAllowed(Feature.FIELD_TRACKER)) {
            FirebaseDBUpdater firebaseDBUpdater = new FirebaseDBUpdater(context);
           // firebaseDBUpdater.updateFirebaseFromDB();
        }
    }
}