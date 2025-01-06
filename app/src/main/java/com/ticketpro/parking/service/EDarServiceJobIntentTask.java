package com.ticketpro.parking.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.ticketpro.parking.activity.TPApplication;

public class EDarServiceJobIntentTask extends JobIntentService {
    private static final String TAG = "EDarServiceJobIntentTask";

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, EDarServiceJobIntentTask.class, 234, work);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        try {
            String witch = intent.getStringExtra("WITCH");
            if (witch.equals("Three")) {
                String startTime = intent.getStringExtra("START");
                String endTime = intent.getStringExtra("END");
                String id = intent.getStringExtra("4");
                String taskrecordId=intent.getStringExtra("TaskRecordId");
                DarReport.callTaskReport(startTime, endTime, id,taskrecordId);
            }
            if (witch.equals("Two")){
                String startTime="";
                String endTime="";
                String id="";
                String AssignReoprtId="";
                if(!intent.getStringExtra("START").isEmpty())
                {
                    startTime = intent.getStringExtra("START");
                }else {
                    startTime = "";
                }
                if(!intent.getStringExtra("END").isEmpty())
                {
                    endTime = intent.getStringExtra("END");
                }else {
                    endTime = "" ;
                }
                if (!intent.getStringExtra("ID").isEmpty())
                {
                    id = intent.getStringExtra("ID");
                }
                else {
                    id = "";
                }
                if (!intent.getStringExtra("assignment_report_id").isEmpty())
                {
                    AssignReoprtId= intent.getStringExtra("assignment_report_id");
                }else {
                    AssignReoprtId= "";
                }


                DarReport.callAssignmentLocationReport(startTime, endTime, id,AssignReoprtId);
            }
            if (witch.equals("One")){
                String assign_report_id="";
                String endTime="";
                String startTime="";
                if (!intent.getStringExtra("START").isEmpty())
                {
                     startTime= intent.getStringExtra("START");
                }
                else {
                     startTime="";
                }

                if (intent.getStringExtra("END")!= null)
                {
                    endTime = intent.getStringExtra("END");
                }
                else
                {
                    endTime = "";
                }

                String id = intent.getStringExtra("ID");
                if (!intent.getStringExtra("assignment_report_id").isEmpty())
                {
                    assign_report_id=intent.getStringExtra("assignment_report_id");
                }else {
                    assign_report_id="";
                }

                DarReport.callAssignmentReport(startTime, endTime, id,assign_report_id);
            }

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
