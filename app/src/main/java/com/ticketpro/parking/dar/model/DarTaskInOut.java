package com.ticketpro.parking.dar.model;

import android.util.Log;

import com.google.gson.Gson;
import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGenerator;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.request.TaskReport_rpc;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DarTaskInOut {

    public  void callTaskReport(String startTime, String endTime, String id,String TaskRecordId,String taskid){
        TaskReport_rpc rpc = new TaskReport_rpc();
        ParamTaskReport param = new ParamTaskReport();
        List<TaskReportModel> aList = new ArrayList<>();
        TaskReportModel model = new TaskReportModel();
        //
        model.setAssignmentLocReportId(id);
        model.setTaskId(taskid);
        model.setStartTime(startTime);
        model.setEndTime(endTime);
        if (TPApplication.getInstance().getDarTaskReoprtId()!=null && !TaskRecordId.equals("0") )
        {
            model.setDartaskReportId(TPApplication.getInstance().getDarTaskReoprtId());
        }
        else
        {
            model.setDartaskReportId("");
        }

        model.setUserid(TPApplication.getInstance().userId);
        model.setAppId((int) TaskReportModel.getNextPrimaryId());
        if (TPApplication.getInstance().getAssLocRecId()!=null)
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
        System.out.println("RequestObj**"+new Gson().toJson(rpc));
        TaskReportModel.insertTaskReport(model);
        ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
        api.insertTaskReport(rpc).enqueue(new Callback<DarTaskReportResponse>() {
            @Override
            public void onResponse(Call<DarTaskReportResponse> call, Response<DarTaskReportResponse> response) {
                if (response.isSuccessful() && response.code()==200){
                    final List<Integer> appId = response.body().getResult().getAppId();
                    try {
                        if (response.body().getResult().getDarTaskReportId().get(0)!=null)
                        {
                            TPApplication.getInstance().setDarTaskReoprtId(response.body().getResult().getDarTaskReportId().get(0));
                        }
                    }catch (Exception e)
                    {

                    }
                    if (appId != null && appId.size()>0) {
                        for (int i = 0; i < appId.size(); i++) {
                            try {
                                TaskReportModel.removeById(appId.get(i));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DarTaskReportResponse> call, Throwable t) {
                Log.d("Dar Report", t.getMessage());
            }
        });

    }
}
