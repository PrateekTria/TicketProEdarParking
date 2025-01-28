package com.ticketpro.parking.dar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialog;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGenerator;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.HomeActivity;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.DarTicketResult;
import com.ticketpro.parking.dar.model.FieldContactDetails;
import com.ticketpro.parking.dar.model.Mileage;
import com.ticketpro.parking.dar.model.SJPDModel;
import com.ticketpro.parking.dar.model.params.ParamFieldContact;
import com.ticketpro.parking.dar.model.params.ParamMileage;
import com.ticketpro.parking.dar.model.params.ParamSjpd;
import com.ticketpro.parking.dar.model.params.ParamsEndMileage;
import com.ticketpro.parking.dar.model.request.EndMileageJson_rpc;
import com.ticketpro.parking.dar.model.request.FieldContactJson_rpc;
import com.ticketpro.parking.dar.model.request.MileageJson_rpc;
import com.ticketpro.parking.dar.model.request.SJPDJson_rpc;
import com.ticketpro.parking.dar.model.response.EndMileageResponse;
import com.ticketpro.parking.service.EDarServiceJobIntentTask;
import com.ticketpro.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MilageDialog extends BaseActivityImpl {

     String custId1="00";
     String deviceId1="00";
     String Stat_us="y";
     String Id_colunn="0";
     String logintime="0";
     Integer assin_id=0;
     String  vehicle_id="0";
     String  vehicle_Number="0";
     private SharedPreferences sharedPreferences;
     ProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void EndMilageDialogShift(Context context,String custId,String deviceId,String status,String Id,String start_mileage,String LoginTime,String assignID,String VehicleId,String VehicleNumber) {
        custId1=custId;
        deviceId1=deviceId;
        Stat_us=status;
        Id_colunn=Id;
        logintime=LoginTime;
        vehicle_id=VehicleId;
        vehicle_Number=VehicleNumber;

        if (assignID!=null){

            assin_id=Integer.parseInt(assignID);
        }


        Dialog EndDialog = new Dialog(context, R.style.Theme_Dialog);
        EndDialog.setTitle("End Mileage?");
        View customlayout = EndDialog.getLayoutInflater().inflate(R.layout.endmilagedialoglayouy, null);
        EndDialog.setContentView(customlayout);
        EndDialog.setCancelable(true);
        Button btn_submit = EndDialog.findViewById(R.id.endmilage_submit);
        Button btn_back=EndDialog.findViewById(R.id.endmilage_back);
        EditText EndMileage= EndDialog.findViewById(R.id.end_mileage);
        btn_submit.setOnClickListener(view -> {
            String EndMileageValue=EndMileage.getText().toString();

            if (EndMileageValue.isEmpty() || EndMileageValue.equals(""))
            {
                EndMileage.setError("Enter End Mileage");
            }else  if(Integer.parseInt(start_mileage)> Integer.parseInt(EndMileageValue))
            {
                EndMileage.setError("Enter Correct End  Mileage");

            }
            else {
                EndDialog.dismiss();
                saveEndMileage(EndMileageValue,context,1);
                AssignmenetLogout(context);
              //  _sjpdInsert1(LoginTime,DateUtil.getCurrentDateTime1(),assin_id,context);
            }
           // ;

        });
        EndDialog.show();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EndDialog.dismiss();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void EndMilageDialogLogout(Context context,String custId,String deviceId,String status,String Id,String start_mileage,String LoginTime,String assignID,String VehicleId,String VehicleNumber) {
        custId1=custId;
        deviceId1=deviceId;
        Stat_us=status;
        Id_colunn=Id;
        logintime=LoginTime;
        vehicle_id=VehicleId;
        vehicle_Number=VehicleNumber;

        if (assignID!=null ){

            assin_id=Integer.parseInt(assignID);
        }


        Dialog EndDialog = new Dialog(context, R.style.Theme_Dialog);
        EndDialog.setTitle("End Mileage?");
        View customlayout = EndDialog.getLayoutInflater().inflate(R.layout.endmilagedialoglayouy, null);
        EndDialog.setContentView(customlayout);
        EndDialog.setCancelable(true);
        Button btn_submit = EndDialog.findViewById(R.id.endmilage_submit);
        Button btn_back=EndDialog.findViewById(R.id.endmilage_back);
        EditText EndMileage= EndDialog.findViewById(R.id.end_mileage);
        btn_submit.setOnClickListener(view -> {
            String EndMileageValue=EndMileage.getText().toString();

            if (EndMileageValue.isEmpty() || EndMileageValue.equals(""))
            {
                EndMileage.setError("Enter End Mileage");
            }else  if(Integer.parseInt(start_mileage)> Integer.parseInt(EndMileageValue))
            {
                EndMileage.setError("Enter Correct End  Mileage");

            }
            else {
                EndDialog.dismiss();
                saveEndMileage(EndMileageValue,context,2);
                 AssignmenetLogout(context);
             //   _sjpdInsert1(LoginTime,DateUtil.getCurrentDateTime1(),assin_id,context);
            }
            // ;

        });
        EndDialog.show();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EndDialog.dismiss();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)

    public void saveEndMileage(String endMileage,Context context,int i) {
        progressDialog= ProgressDialog.show(context,"","Please wait");
        EndMileageJson_rpc jsonRpc = new EndMileageJson_rpc();
        List<Mileage> aList = new ArrayList<>();
        ParamsEndMileage param = new ParamsEndMileage();
        Mileage details = new Mileage();
        details.setUserId(Integer.parseInt(deviceId1));
        details.setDeviceid(Integer.parseInt(custId1));
        details.setEndMileage(endMileage);
        details.setVehicleId(vehicle_id);
        details.setVehicleNumber(vehicle_Number);

        //details.setActionId("1");
        aList.add(details);
        param.setCustId(Integer.parseInt(Id_colunn));
        param.setDetails(aList);
        jsonRpc.setJsonrpc("2.0");
        jsonRpc.setMethod("updateMileage");
        jsonRpc.setId("82F85DB43CBF6");
        jsonRpc.setParams(param);
        System.out.println("RequestObj**"+new Gson().toJson(jsonRpc));

        if (Stat_us.equals("Yes")) {
            try {
          //     Mileage.insertMileage(details);
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertEndMileage(jsonRpc).enqueue(new Callback<EndMileageResponse>() {
                    @Override
                    public void onResponse(Call<EndMileageResponse> call, Response<EndMileageResponse> response) {
                        Log.d("ResponseBody",response.body().getResult().toString());
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        if ( response.code() == 200 && response.body().getResult().getResult()==true) {



                            Intent intent = new Intent();
                            if (i==1)
                            intent.setClass(context, HomeActivity.class);
                            else
                                intent.setClass(context, DarEquipmentNew.class);
                            context.startActivity(intent);
                          ///  Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();

                            /*final DarTicketResult result = response.body().getResult();
                            final List<Integer> appId = result.getAppId();
                            if (appId != null && appId.size() > 0) {
                                for (int i = 0; i < appId.size(); i++) {
                                    try {
                                        FieldContactDetails.removeById(appId.get(i).intValue());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
*/
                           // Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<EndMileageResponse> call, Throwable t) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        Log.d("Error", t.getMessage());
                    }
                });
            } catch (Exception e) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                e.printStackTrace();
                log.error(e.getMessage());

            }
        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            SpinnerFailedDialog(context);
            //Mileage.insertMileage(details);
        /*    Intent intent = new Intent();
            intent.setClass(context, HomeActivity.class);
            context.startActivity(intent);
            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();*/

        }

    }

    /*public void _sjpdInsert1(String loginTime, String logOutTime,int id,Context context){
        *//* progressDialog = ProgressDialog.show(DarAssignmentActivity.this, "", "Saving...");*//*
        SJPDJson_rpc sjpdJson_rpc = new SJPDJson_rpc();
        ParamSjpd paramSjpd = new ParamSjpd();
        ArrayList<SJPDModel> sjpdModels = new ArrayList<>();

        SJPDModel sjpdModel = new SJPDModel();
        sjpdModel.setLogin(loginTime);
        sjpdModel.setLogout(logOutTime);
        sjpdModel.setUserId(deviceId1);

        sjpdModels.add(sjpdModel);

        paramSjpd.setCustId(Integer.parseInt(custId1));
        paramSjpd.setDetails(sjpdModels);

        sjpdJson_rpc.setJsonrpc("2.0");
        sjpdJson_rpc.setId("82F85DB43CBF6");
        sjpdJson_rpc.setMethod("sjpdFormInsert");
        sjpdJson_rpc.setParams(paramSjpd);
          System.out.println("RequestObj**"+new Gson().toJson(sjpdJson_rpc));
        if (Stat_us.equals("Yes")){
            try {
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertSJPD(sjpdJson_rpc).enqueue(new Callback<DarTicketResponse>() {
                    @Override
                    public void onResponse(Call<DarTicketResponse> call, Response<DarTicketResponse> response) {
                        *//*if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }*//*
                        if (response.isSuccessful() && response.code()==200){
                            Log.d("DarAssignment", String.valueOf(response.raw()));
                            Intent serviceIntent = new Intent(context, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", "");
                            serviceIntent.putExtra("END",DateUtil.getCurrentDateTime1());
                            if (!TPApplication.getInstance().getAssignReportId().isEmpty())
                                serviceIntent.putExtra("assignment_report_id",TPApplication.getInstance().getAssignReportId());
                            else
                                serviceIntent.putExtra("assignment_report_id","");
                            serviceIntent.putExtra("ID","");
                            serviceIntent.putExtra("WITCH","One");
                            EDarServiceJobIntentTask.enqueueWork(context, serviceIntent);
                            *//* finish();*//*

                        }
                    }

                    @Override
                    public void onFailure(Call<DarTicketResponse> call, Throwable t) {

                        log.error("DarAssignment "+t.getMessage());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }

        }else {
            //Toast.makeText(DarAssignmentActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
           *//* if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }*//*
        }


    }
*/
    @Override
    public void onClick(View view) {

    }

    @Override
    public void bindDataAtLoadingTime() throws Exception {

    }

    @Override
    public void handleVoiceInput(String text) throws Exception {

    }

    @Override
    public void handleVoiceMode(boolean voiceMode) {

    }

    @Override
    public void handleNetworkStatus(boolean connected, boolean isFastConnection) {

    }

    public  void SpinnerFailedDialog(Context context){

        new iOSDialogBuilder(context)
                .setSubtitle("No Internet Connection")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })

                .build().show();
        //new ProxyImpl().updateDarTable();
    }

    public void AssignmenetLogout(Context context)
    {
        Intent serviceIntent = new Intent(context, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", "");
        serviceIntent.putExtra("END",DateUtil.getCurrentDateTime1());
        if (!TPApplication.getInstance().getAssignReportId().isEmpty())
            serviceIntent.putExtra("assignment_report_id",TPApplication.getInstance().getAssignReportId());
        else
            serviceIntent.putExtra("assignment_report_id","");
        serviceIntent.putExtra("ID","");
        serviceIntent.putExtra("WITCH","One");
        EDarServiceJobIntentTask.enqueueWork(context, serviceIntent);
    }
}

