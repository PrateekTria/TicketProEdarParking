package com.ticketpro.parking.service;

import android.util.Log;

import com.google.gson.Gson;
import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGenerator;
import com.ticketpro.model.TicketResponse;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.SignCheck;
import com.ticketpro.parking.dar.model.AssignmentLocationReportModel;
import com.ticketpro.parking.dar.model.AssignmentReportModel;
import com.ticketpro.parking.dar.model.DarAssignmentLocationReportResponse;
import com.ticketpro.parking.dar.model.DarAssignmentreportResponse;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.DarTaskResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.TaskForm22500Model;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.params.ParamAssignmentLocationReport;
import com.ticketpro.parking.dar.model.params.ParamAssignmentReport;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.request.AssignmentLocationReport_rpc;
import com.ticketpro.parking.dar.model.request.AssignmentReport_rpc;
import com.ticketpro.parking.dar.model.request.TaskReport_rpc;
import com.ticketpro.util.Preference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DarReport {
    public static void callAssignmentReport(String startTime, String endTime, String id,String assignReportId){
        AssignmentReport_rpc rpc = new AssignmentReport_rpc();
        ParamAssignmentReport param = new ParamAssignmentReport();
        List<AssignmentReportModel> aList = new ArrayList<>();
        AssignmentReportModel model = new AssignmentReportModel();
        model.setAssignmentId(id);
        model.setStartTime(startTime);
        model.setEndTime(endTime);
        model.setUserid(TPApplication.getInstance().userId);
        model.setAppId((int) AssignmentLocationReportModel.getNextPrimaryId());
        model.setAssignmentreportId(assignReportId);
        aList.add(model);
        param.setDetails(aList);
        param.setCustId(TPApplication.getInstance().custId);
        rpc.setParams(param);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("dar_assignment_report");
        System.out.println("RequestObj**"+new Gson().toJson(rpc));
        AssignmentReportModel.insertFieldContactDetails(model);
        ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
        api.insertAssignmentReport(rpc).enqueue(new Callback<DarAssignmentreportResponse>() {
            @Override
            public void onResponse(Call<DarAssignmentreportResponse> call, Response<DarAssignmentreportResponse> response) {
                if (response.isSuccessful() && response.code()==200){
                    try {
                        final List<Integer> appId = response.body().getResult().getAppId();
                        final List<String>  assignmentReportId=response.body().getResult().getAssignmentReportId();
                        if (appId != null && appId.size()>0) {
                            for (int i = 0; i < appId.size(); i++) {
                                try {
                                    AssignmentReportModel.removeById(appId.get(i));

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        try {
                            if (assignmentReportId.size()>0)
                            {
                                for (int j=0;j<assignmentReportId.size();j++)
                                {
                                    TPApplication.getInstance().setAssignReportId(assignmentReportId.get(j));
                                    Log.d("assignmentReportId",assignmentReportId.get(j));
                                }

                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<DarAssignmentreportResponse> call, Throwable t) {
                Log.d("Dar Report", t.getMessage());

            }
        });

    }

    public static void callAssignmentLocationReport(String startTime, String endTime, String id,String  AssignReoprtId){
        AssignmentLocationReport_rpc rpc = new AssignmentLocationReport_rpc();
        ParamAssignmentLocationReport param = new ParamAssignmentLocationReport();
        List<AssignmentLocationReportModel> aList = new ArrayList<>();
        AssignmentLocationReportModel model = new AssignmentLocationReportModel();
        //
        model.setAssignmentReportId(id);
        model.setStartTime(startTime);
        model.setEndTime(endTime);
        model.setUserid(TPApplication.getInstance().userId);
        model.setAppId((int) AssignmentLocationReportModel.getNextPrimaryId());
        model.setAssLocationReportId("");
        model.setAssReportId(AssignReoprtId);
        aList.add(model);
        param.setDetails(aList);
        param.setCustId(TPApplication.getInstance().custId);
        rpc.setParams(param);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("dar_assignment_location_report");
        System.out.println("RequestObj**"+new Gson().toJson(rpc));
        AssignmentLocationReportModel.insertFieldContactDetails(model);
        ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
        api.insertAssignmentLocationReport(rpc).enqueue(new Callback<DarAssignmentLocationReportResponse>() {
            @Override
            public void onResponse(Call<DarAssignmentLocationReportResponse> call, Response<DarAssignmentLocationReportResponse> response) {
                if (response.isSuccessful()){
                    List<String> assign_report_id=null;
                    final List<Integer> appId = response.body().getResult().getAppId();
                    if (!response.body().getResult().getAssignmentReportId().isEmpty())
                    {
                        assign_report_id=response.body().getResult().getAssignmentReportId();
                    }


                    if (appId != null && appId.size()>0) {
                        for (int i = 0; i < appId.size(); i++) {
                            try {
                                AssignmentLocationReportModel.removeById(appId.get(i));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (assign_report_id.size()>0)
                    {
                        for (int j=0;j<assign_report_id.size();j++)
                        {
                            TPApplication.getInstance().setAssignmentLocationReportid(assign_report_id.get(j));
                            Log.d("entLocationReportId",assign_report_id.get(j));
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<DarAssignmentLocationReportResponse> call, Throwable t) {
                Log.d("Dar Report", t.getMessage());

            }
        });

    }
    public static void callTaskReport(String startTime, String endTime, String id,String TaskRecordId){
        TaskReport_rpc rpc = new TaskReport_rpc();
        ParamTaskReport param = new ParamTaskReport();
        List<TaskReportModel> aList = new ArrayList<>();
        TaskReportModel model = new TaskReportModel();
        //
        model.setAssignmentLocReportId(id);
        model.setTaskId("0");
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
