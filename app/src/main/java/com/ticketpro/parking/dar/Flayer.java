package com.ticketpro.parking.dar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGenerator;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.HomeActivity;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.DarTicketResult;
import com.ticketpro.parking.dar.model.FlayerModel;
import com.ticketpro.parking.dar.model.LunchBreakModel;
import com.ticketpro.parking.dar.model.RideAlongModel;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.params.ParamFlayer;
import com.ticketpro.parking.dar.model.params.ParamLunchBreak;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.request.FlayerJSON_rpc;
import com.ticketpro.parking.dar.model.request.LunchBreakJson_rpc;
import com.ticketpro.parking.dar.model.request.TaskReport_rpc;
import com.ticketpro.parking.service.EDarServiceJobIntentTask;
import com.ticketpro.util.DateUtil;
import com.ticketpro.util.Preference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Flayer extends BaseActivityImpl implements Validator.ValidationListener {
    private Button _btnBack;
    private Button _mSave;
    private Button _mEndShift;
    private Button _mLogout;
    private ProgressDialog progressDialog;
    @NotEmpty
    private EditText _mFlyerDateTime;
    @NotEmpty
    private EditText _mFlyerComment;
    private Validator validator;
    private int assignmentId;
    private String inTime = null;
    private Preference preference;
    DarSJPDlogoutJob darSJPDlogoutJob= new DarSJPDlogoutJob();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flayer);
        setLogger(Flayer.class.getName());
        Intent intent = new Intent();
        assignmentId = intent.getIntExtra("ASSIGNMENT", 0);
        _btnBack = findViewById(R.id.bc_btnBack);
      //  _btnBack.setOnClickListener(v -> finish());
        _mEndShift = findViewById(R.id.bc_btnEndShift);
        _mLogout = findViewById(R.id.bc_btn_logout);
        _mSave = findViewById(R.id.bc_btn_save);

        preference = Preference.getInstance(Flayer.this);
        inTime = preference.getString("IN_DATE");
        _mFlyerDateTime = findViewById(R.id.nonf_datetime12);
        _mFlyerDateTime.setText(DateUtil.getCurrentDateTime());
        _mFlyerComment = findViewById(R.id.nonf_flyer_comment);

        validator = new Validator(this);
        validator.setValidationListener(this);

        _btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    callTaskReport("",DateUtil.getCurrentDateTime1(),String.valueOf(assignmentId),TPApplication.getInstance().getDarTaskReoprtId(),"5");
                } catch (IOException e) {
                    e.printStackTrace();
                }


               /* Intent serviceIntent = new Intent(Flayer.this, EDarServiceJobIntentTask.class);
                serviceIntent.putExtra("START", "");
                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                serviceIntent.putExtra("ID", assignmentId);
                serviceIntent.putExtra("WITCH", "Three");
                serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                EDarServiceJobIntentTask.enqueueWork(Flayer.this, serviceIntent);
                finish();*/
            }
        });
        _mEndShift.setOnClickListener(v -> {
           /* Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntent.class);
            if (isNetworkConnected()) {
                EDarServiceJobIntent.enqueueWork(TaskForm22500.this, serviceIntent);
            }*/

            new iOSDialogBuilder(Flayer.this)
                    .setSubtitle("SJPD Radio logoff \n Performed?")
                    .setPositiveListener("Yes", new iOSDialogClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(iOSDialog dialog) {

                            darSJPDlogoutJob._sjpdlogout(Flayer.this);
                            Intent serviceIntent = new Intent(Flayer.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", "");
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", assignmentId);
                            serviceIntent.putExtra("WITCH", "Three");
                            serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                            EDarServiceJobIntentTask.enqueueWork(Flayer.this, serviceIntent);

                            TPApplication.getInstance().setDarStartTimeTask(null);
                          /*  Intent serviceIntent1 = new Intent(OffsiteNonEnforcement.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(id_assg_loc));
                            serviceIntent.putExtra("WITCH", "Two");
                            EDarServiceJobIntentTask.enqueueWork(OffsiteNonEnforcement.this, serviceIntent1);*/

                            //  dialog.dismiss();

                            dialog.dismiss();
                            String  Id_colunn=preference.getString("coulmunId");
                            String  Start_mileage= TPApp.getMileage();

                            MilageDialog milageDialog= new MilageDialog();

                            try {
                                if (isNetworkConnected())
                                {
                                    milageDialog.EndMilageDialogShift(Flayer.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                }
                                else
                                {
                                    milageDialog.EndMilageDialogShift(Flayer.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    })
                    .setNegativeListener("No", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {
                            dialog.dismiss();
                        }
                    })
                    .build().show();
        });
        _mLogout.setOnClickListener(v -> {
            try {




                new iOSDialogBuilder(Flayer.this)
                        .setSubtitle("SJPD Radio logoff \n Performed?")
                        .setPositiveListener("Yes", new iOSDialogClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(iOSDialog dialog) {

                                darSJPDlogoutJob._sjpdlogout(Flayer.this);
                                Intent serviceIntent = new Intent(Flayer.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", "");
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", assignmentId);
                                serviceIntent.putExtra("WITCH", "Three");
                                serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                                EDarServiceJobIntentTask.enqueueWork(Flayer.this, serviceIntent);

                                dialog.dismiss();
                                //finish();

                                String  Id_colunn=preference.getString("coulmunId");
                                String  Start_mileage= TPApp.getMileage();

                                MilageDialog milageDialog= new MilageDialog();

                                try {
                                    if (isNetworkConnected())
                                    {
                                        milageDialog.EndMilageDialogLogout(Flayer.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                    }
                                    else
                                    {
                                        milageDialog.EndMilageDialogLogout(Flayer.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        })
                        .setNegativeListener("No", new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .build().show();

                //new ProxyImpl().updateDarTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        _mSave.setOnClickListener(v -> validator.validate());
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
    public void onValidationSucceeded() {
        try {
            __save();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void __save() throws IOException {
        progressDialog = ProgressDialog.show(Flayer.this, "", "Inserting...");

        FlayerJSON_rpc rpc = new FlayerJSON_rpc();
        ParamFlayer param = new ParamFlayer();
        List<FlayerModel> aList = new ArrayList<>();
        FlayerModel model = new FlayerModel();

        model.setDatetime(_mFlyerDateTime.getText().toString());
        model.setComment(_mFlyerComment.getText().toString());
        model.setNonInforcementDdElementId(String.valueOf(assignmentId));
        model.setEquipmentId(TPApp.getEquipment());
        model.setSubEquipmentId(TPApp.getEquipmentChild());
        model.setVehicle(TPApp.getVehicleType());
        model.setMileage(TPApp.getMileage());
        model.setDisinfecting(TPApp.getDisinfecting());
        model.setVdr(TPApp.getVdr());
        model.setAssignmentId(5);
        model.setDeviceid(String.valueOf(TPApp.deviceId));
        model.setAppId((int) FlayerModel.getNextPrimaryId());
        model.setUserId(String.valueOf(TPApp.userId));
        if (!preference.getString("coulmunId").isEmpty() || !preference.getString("coulmunId").equals(""))
            model.setMileageId(preference.getString("coulmunId"));
        else
            model.setMileageId("0");

        if (TPApplication.getInstance().getDarTaskReoprtId() != null) {
            model.setDarTaskReportId(TPApplication.getInstance().getDarTaskReoprtId());
        } else {
            model.setDarTaskReportId("");
        }

        aList.add(model);
        param.setDetails(aList);
        param.setCustId(TPApp.custId);
        rpc.setParams(param);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("OffSiteNonInforcementFlyerFormInsert");
        System.out.println("RequestObj**"+new Gson().toJson(rpc));
        log.info(new Gson().toJson(rpc));
        if (isNetworkConnected()) {
            try {
              //  FlayerModel.insertFlayerModel(model);
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertFlayer(rpc).enqueue(new Callback<DarTicketResponse>() {
                    @Override
                    public void onResponse(Call<DarTicketResponse> call, Response<DarTicketResponse> response) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (response.code() == 200 && response.isSuccessful()) {
                            try {

                                if (response.body().getResult()!= null)
                                {
                                    if (response.body().getResult().getResult() == true){

                                        Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();

                                        _mFlyerComment.setText("");
                                    }else
                                    {
                                        Failed();
                                        log.error("response incorrect");
                                    }
                                }else
                                {
                                    Failed();
                                    log.error("response incorrect");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                log.error(e.getMessage());
                            }

                        }else
                        {
                            Failed();
                            log.error("server not responding");
                        }
                    }

                    @Override
                    public void onFailure(Call<DarTicketResponse> call, Throwable t) {
                        log.error(t.getMessage());
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Failed();
                        log.error("Failure");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Failed();
                log.error(e.getMessage());
            }
        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            FlayerModel.insertFlayerModel(model);
            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
            _mFlyerComment.setText("");
        }

    }

    @Override
    public void onBackPressed() {
        try {
            callTaskReport("",DateUtil.getCurrentDateTime1(),String.valueOf(assignmentId),TPApplication.getInstance().getDarTaskReoprtId(),"5");
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* Intent serviceIntent = new Intent(Flayer.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", "");
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID", assignmentId);
        serviceIntent.putExtra("WITCH", "Three");
        serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
        EDarServiceJobIntentTask.enqueueWork(Flayer.this, serviceIntent);
        super.onBackPressed();*/
    }


    public  void callTaskReport(String startTime, String endTime, String id,String TaskRecordId,String taskid) throws IOException {
        progressDialog = ProgressDialog.show(Flayer.this, "", "Please Wait...");
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
                                TPApplication.getInstance().setDarTaskReoprtId(response.body().getResult().getDarTaskReportId().get(0));
                                finish();
                            }
                        } catch (Exception e) {

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
                }
            });

        }
        else{
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            log.error("No Internet");
        }
    }

    public void Failed() {

        new iOSDialogBuilder(Flayer.this)
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


   /*  final DarTicketResult result = response.body().getResult();
                                final List<Integer> appId = result.getAppId();
                                if (appId!=null && appId.size()>0) {
                                    for (int i = 0; i < appId.size(); i++) {
                                        try {
                                            FlayerModel.removeById(appId.get(i).intValue());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();

                                _mFlyerComment.setText("");*/