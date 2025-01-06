package com.ticketpro.parking.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.ticketpro.parking.proxy.ProxyImpl;

public class EDarServiceJobIntent extends JobIntentService {
    private static final String TAG = "JobIntentService";

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, EDarServiceJobIntent.class, 123, work);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        try {
            System.out.println("================Intent============");
           new ProxyImpl().updateDarTable();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public boolean onStopCurrentWork() {
        Log.d(TAG, "onStopCurrentWork");
        return super.onStopCurrentWork();
    }
}
