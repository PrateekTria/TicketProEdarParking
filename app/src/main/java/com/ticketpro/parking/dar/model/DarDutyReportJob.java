package com.ticketpro.parking.dar.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGenerator;
import com.ticketpro.model.DutyReport;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.params.ParamAssignmentLocationReport;
import com.ticketpro.parking.dar.model.params.ParamsDutyRequestResponse;
import com.ticketpro.parking.dar.model.request.AssignmentLocationReport_rpc;
import com.ticketpro.parking.dar.model.request.DarDutyRequestJson_rpc;
import com.ticketpro.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DarDutyReportJob {

    public  void callDutyReportAPI(int duty_id, Context context,String duty_report_id)
    {
        DarDutyRequestJson_rpc rpc = new DarDutyRequestJson_rpc();
        ParamsDutyRequestResponse param = new ParamsDutyRequestResponse();
        List<DutyReport> aList = new ArrayList<>();
        DutyReport model = new DutyReport();
        model.setCustId(TPApplication.getInstance().custId);
        model.setDateIn(TPApplication.getInstance().getDatein());
        model.setDateOut( new Date());
        model.setDeviceId(TPApplication.getInstance().deviceId);
        model.setDutyId(TPApplication.getInstance().getDardutyId());
        model.setDuty_report_id(duty_report_id);
        model.setReportId(0);
        model.setDate_string(TPApplication.getInstance().dutyInTime);
        model.setUserId(TPApplication.getInstance().userId);
        aList.add(model);
        param.setChalkId(0);
        param.setDutyReports(aList);
        rpc.setParams(param);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("updateDutyReports");
        System.out.println("RequestObj1**"+new Gson().toJson(rpc));
        DutyReport.insertDutyReport(model);
        ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
        api.insertDutyReport(rpc).enqueue(new Callback<DarDutyResponseR>() {
            @Override
            public void onResponse(Call<DarDutyResponseR> call, Response<DarDutyResponseR> response) {
                if (response.isSuccessful()){
                    if (response.body().getResult().getResult()==true)
                    {

                    }

                }
            }

            @Override
            public void onFailure(Call<DarDutyResponseR> call, Throwable t) {
                Log.d("Dar Report", t.getMessage());

            }
        });

    }

}

