package com.ticketpro.parking.dar;

import android.content.Context;
import android.view.View;

import com.google.gson.Gson;
import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGenerator;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.SJPDModel;
import com.ticketpro.parking.dar.model.SJPDResonse;
import com.ticketpro.parking.dar.model.params.ParamSjpd;
import com.ticketpro.parking.dar.model.request.SJPDJson_rpc;
import com.ticketpro.util.DateUtil;
import com.ticketpro.util.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DarSJPDlogoutJob  {

    public void _sjpdlogout( Context context) {
        //  progressDialog = ProgressDialog.show(DarAssignmentActivity.this, "", "Please wait...");
       /* progressDialog.show();
        progressDialog.setCancelable(false);*/
        Preference preference = Preference.getInstance(context);
        String SJPDIndexId = " ";
        SJPDIndexId = preference.getString("SJPD_RecordId");
        SJPDJson_rpc sjpdJson_rpc = new SJPDJson_rpc();
        ParamSjpd paramSjpd = new ParamSjpd();
        ArrayList<SJPDModel> sjpdModels = new ArrayList<>();
        SJPDModel sjpdModel = new SJPDModel();
        sjpdModel.setLogin("");
        sjpdModel.setLogout(DateUtil.getCurrentDateTime1());
//        sjpdModel.setLogout("");
        sjpdModel.setUserId(String.valueOf(TPApplication.getInstance().userId));
        sjpdModel.setAppId("10");

        if (!SJPDIndexId.equals(" "))
            sjpdModel.setDar_call_signin_id(SJPDIndexId);
        else
            sjpdModel.setDar_call_signin_id("");

        sjpdModels.add(sjpdModel);
        paramSjpd.setCustId(TPApplication.getInstance().custId);
        paramSjpd.setDeviceId(TPApplication.getInstance().deviceId);
        paramSjpd.setDetails(sjpdModels);
        sjpdJson_rpc.setJsonrpc("2.0");
        sjpdJson_rpc.setId("82F85DB43CBF6");
        sjpdJson_rpc.setMethod("sjpdFormInsert");
        sjpdJson_rpc.setParams(paramSjpd);
        System.out.println("RequestObj1**" + new Gson().toJson(sjpdJson_rpc));
        {
            try {
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertSJPD(sjpdJson_rpc).enqueue(new Callback<SJPDResonse>() {
                    @Override
                    public void onResponse(Call<SJPDResonse> call, Response<SJPDResonse> response) {
                      /*  if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }*/
                       /* if (response.body().getResult() != null) {
                            ///LogoutandsubmitEndMileage(StartMileage, Id_coulmn);
                        } else {
                           //// Failed();
                        }*/
                        //  preference.putString("SJPD_RecordId", response.body().getResult().getDarCallSigninId().get(0));

                    }

                    @Override
                    public void onFailure(Call<SJPDResonse> call, Throwable t) {
                        /*if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }*/

                        //  Failed();

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                ;
                // Failed();
            }

        }

    }


}
