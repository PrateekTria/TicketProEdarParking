package com.ticketpro.parking.dar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.gson.Gson;
import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGenerator;
import com.ticketpro.model.ChalkVehicle;
import com.ticketpro.model.Ticket;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.ChalkVehicleActivity;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.activity.WriteTicketActivity;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.request.TaskReport_rpc;
import com.ticketpro.util.DateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Enforcement extends BaseActivityImpl {

    private Button mWriteTicket;
    private Button mChalk;
    int asgId = 0;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enforcement);
        setLogger(Enforcement.class.getName());
        Intent it = getIntent();
        asgId = it.getIntExtra("LOCATION_ID", 0);
        mChalk = findViewById(R.id.btnChalk);
        mWriteTicket = findViewById(R.id.btnWriteTicket);
        Button _btnBack = findViewById(R.id.btnBak);
        /*getIntent().getStringExtra("ID")*/
        _btnBack.setOnClickListener(v -> {

            try {
                callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
            } catch (IOException e) {
                e.printStackTrace();
            }
/*
            Intent serviceIntent = new Intent(Enforcement.this, EDarServiceJobIntentTask.class);
            serviceIntent.putExtra("START","");
            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
            serviceIntent.putExtra("ID",String.valueOf(asgId));
            serviceIntent.putExtra("WITCH","Three");
            serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
            EDarServiceJobIntentTask.enqueueWork(Enforcement.this, serviceIntent);
            TPApplication.getInstance().setDarStartTimeTask(null);
            finish();*/
        });
        mWriteTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ticket ticket = TPApp.createNewTicket();
                TPApp.setActiveTicket(ticket);
                Intent i = new Intent();
                i.setClass(Enforcement.this, WriteTicketActivity.class);
                i.putExtra("LOCATION_ID",String.valueOf(asgId));
                startActivity(i);
            }
        });

        mChalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChalkVehicle chalk = TPApp.createNewChalk();
                TPApp.setActiveChalk(chalk);
                Intent i = new Intent();
                i.setClass(Enforcement.this, ChalkVehicleActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void bindDataAtLoadingTime() throws Exception {

    }

    @Override
    public void handleVoiceInput(String text) {

    }

    @Override
    public void handleVoiceMode(boolean voiceMode) {

    }

    @Override
    public void handleNetworkStatus(boolean connected, boolean isFastConnection) {

    }

    @Override
    public void onBackPressed() {

        try {
            callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
      /*  Intent serviceIntent = new Intent(Enforcement.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", "");
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID",String.valueOf(asgId));
        serviceIntent.putExtra("WITCH","Three");
        serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
        EDarServiceJobIntentTask.enqueueWork(Enforcement.this, serviceIntent);
        TPApplication.getInstance().setDarStartTimeTask(null);
        finish();
        super.onBackPressed();*/
    }

    public void callTaskReport(String startTime, String endTime, String id, String TaskRecordId, String taskid) throws IOException {
        progressDialog = ProgressDialog.show(Enforcement.this, "", "Please Wait...");
        TaskReport_rpc rpc = new TaskReport_rpc();
        ParamTaskReport param = new ParamTaskReport();
        List<TaskReportModel> aList = new ArrayList<>();
        TaskReportModel model = new TaskReportModel();
        model.setAssignmentLocReportId(id);
        model.setTaskId(taskid);
        model.setStartTime(startTime);
        model.setEndTime(endTime);
        if (TPApplication.getInstance().getDarTaskReoprtId() != null && !TaskRecordId.equals("0")) {
            model.setDartaskReportId(TPApplication.getInstance().getDarTaskReoprtId());
        } else {
            model.setDartaskReportId("");
        }
        model.setUserid(TPApplication.getInstance().userId);
        model.setAppId((int) TaskReportModel.getNextPrimaryId());
        if (TPApplication.getInstance().getAssLocRecId() != null)
            model.setAsslocationReportId(TPApplication.getInstance().getAssLocRecId());
        else
            model.setAsslocationReportId("");
        aList.add(model);
        param.setDetails(aList);
        param.setCustId(TPApplication.getInstance().custId);
        rpc.setParams(param);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("dar_task_report");
        System.out.println("RequestObj**" + new Gson().toJson(rpc));
        TaskReportModel.insertTaskReport(model);
        if (isNetworkConnected()) {
            ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
            api.insertTaskReport(rpc).enqueue(new Callback<DarTaskReportResponse>() {
                @Override
                public void onResponse(Call<DarTaskReportResponse> call, Response<DarTaskReportResponse> response) {
                    if (response.isSuccessful() && response.code()==200) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        final List<Integer> appId = response.body().getResult().getAppId();
                        try {
                            if (response.body().getResult().getDarTaskReportId().get(0) != null) {
                                log.info("Task logout"+DateUtil.getCurrentDateTime1());
                                TPApplication.getInstance().setDarTaskReoprtId(response.body().getResult().getDarTaskReportId().get(0));
                                finish();
                            }
                        } catch (Exception e) {
                           log.error(e.getMessage().toString());
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DarTaskReportResponse> call, Throwable t) {
                    Log.d("Dar Report", t.getMessage());
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    log.error(t.getMessage());
                    Failed();
                }
            });

        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            Failed();
            log.error("No Internet");
        }
    }

    public void Failed() {

        new iOSDialogBuilder(Enforcement.this)
                .setSubtitle("Please try again")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();

                    }
                })
                .build().show();

    }
}