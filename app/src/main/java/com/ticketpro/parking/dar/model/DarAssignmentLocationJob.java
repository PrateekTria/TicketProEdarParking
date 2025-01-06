package com.ticketpro.parking.dar.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGenerator;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.VehicleMaintainance;
import com.ticketpro.parking.dar.model.params.ParamAssignmentLocationReport;
import com.ticketpro.parking.dar.model.request.AssignmentLocationReport_rpc;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DarAssignmentLocationJob {

    ProgressDialog progressDialog;
    public  void callAssignLocationAPI(String startTime, String endTime, String id,String  AssignReoprtId,String AssignLocationReportId,Context context)
    {
      //  progressDialog=ProgressDialog.show(context, "", "Please wait...");
        AssignmentLocationReport_rpc rpc = new AssignmentLocationReport_rpc();
        ParamAssignmentLocationReport param = new ParamAssignmentLocationReport();
        List<AssignmentLocationReportModel> aList = new ArrayList<>();
        AssignmentLocationReportModel model = new AssignmentLocationReportModel();
        //
        model.setAssignmentReportId(id);
        model.setStartTime(startTime);
        model.setEndTime(endTime);
        model.setAssignmentLocId("");
        model.setUserid(TPApplication.getInstance().userId);
        model.setAppId((int) AssignmentLocationReportModel.getNextPrimaryId());
        if (!AssignLocationReportId.isEmpty())
        model.setAssLocationReportId(AssignLocationReportId);
        else
            model.setAssLocationReportId("");
        if(!AssignReoprtId.isEmpty())
        model.setAssReportId(AssignReoprtId);
        else
            model.setAssReportId("");
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
                if (response.isSuccessful() && response.code()==200){
                   /* if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }*/
                    List<String> assign_report_idd= null;
                    final List<Integer> appId = response.body().getResult().getAppId();

                    try {
                        if (response.body().getResult().getAssignmentReportId().get(0)!=null)
                        {
                            TPApplication.getInstance().setAssLocRecId(response.body().getResult().getAssignmentReportId().get(0));
                           /* List<String> assign_report_idassign_report_id=response.body().getResult().getAssignmentReportId();
                            assign_report_idd=assign_report_idassign_report_id;*/
                        }

                    }catch (Exception e)
                    {
                       /* if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }*/
                        e.printStackTrace();
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
                   /* if (assign_report_idd.size()>0)
                    {
                        for (int j=0;j<assign_report_idd.size();j++)
                        {
                            String as=assign_report_idd.get(j);
                            TPApplication.getInstance().setAssLocRecId(as);
                            Log.d("entLocationReportId",assign_report_idd.get(j));
                        }

                    }*/
                }
                else {
                    /*if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }*/
                }
            }

            @Override
            public void onFailure(Call<DarAssignmentLocationReportResponse> call, Throwable t) {
               /* if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }*/
                Log.d("Dar Report", t.getMessage());

            }
        });

    }

    }

