package com.ticketpro.parking.dar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGenerator;
import com.ticketpro.model.TicketResponse;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.DarOffsiteDistrictDropdown;
import com.ticketpro.parking.dar.model.DarOffsiteTrainerDropdown;
import com.ticketpro.parking.dar.model.DarSignCheck;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.DarTicketResult;
import com.ticketpro.parking.dar.model.FieldContactDetails;
import com.ticketpro.parking.dar.model.FlayerModel;
import com.ticketpro.parking.dar.model.RideAlongModel;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.params.ParamFlayer;
import com.ticketpro.parking.dar.model.params.ParamRideAlong;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.request.FlayerJSON_rpc;
import com.ticketpro.parking.dar.model.request.RideAlongJSON_rpc;
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

public class RideAlong extends BaseActivityImpl implements Validator.ValidationListener {

    private Button _btnBack;
    private int assignmentId;
    private Button _mSave;
    private Button _mEndShift;
    private Button _mLogout;
    private EditText _mRideDate;
    private ProgressDialog progressDialog;
    private TextView _mRideSpinner;
    private TextView _mRideSpinner2;
    private Integer id = 0;
    private Integer id1 = 0;

    private Validator validator;
    @NotEmpty
    private EditText _mTrainee;
    @NotEmpty
    private EditText _mDistrict;
    private String inTime = null;
    private Preference preference;
    DarSJPDlogoutJob darSJPDlogoutJob= new DarSJPDlogoutJob();



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_along);
        setLogger(RideAlong.class.getName());

        validator = new Validator(this);
        validator.setValidationListener(this);
        preference = Preference.getInstance(RideAlong.this);
        inTime = preference.getString("IN_DATE");

        Intent intent = new Intent();
        assignmentId = intent.getIntExtra("ASSIGNMENT", 0);

        _btnBack = findViewById(R.id.bc_btnBack);
        _btnBack.setOnClickListener(v -> {
           /* Intent serviceIntent = new Intent(RideAlong.this, EDarServiceJobIntentTask.class);
            serviceIntent.putExtra("START", "");
            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
            serviceIntent.putExtra("ID", assignmentId);
            serviceIntent.putExtra("WITCH", "Three");
            serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
            EDarServiceJobIntentTask.enqueueWork(RideAlong.this, serviceIntent);
            finish();
            finish();*/
            try {
                callTaskReport("",DateUtil.getCurrentDateTime1(),String.valueOf(assignmentId),TPApplication.getInstance().getDarTaskReoprtId(),"3");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        _mRideDate = findViewById(R.id.nonf_datetime1);
        _mRideDate.setText(DateUtil.getCurrentDateTime());
        _mRideSpinner = findViewById(R.id.nonf_activity_spinner1);
        _mRideSpinner2 = findViewById(R.id.nonf_activity_spinner2);
        _mTrainee = findViewById(R.id.et_trainer);
        _mDistrict = findViewById(R.id.et_district);
        _mEndShift = findViewById(R.id.bc_btnEndShift);
        _mLogout = findViewById(R.id.bc_btn_logout);
        _mSave = findViewById(R.id.bc_btn_save);

        final ArrayList<DarOffsiteDistrictDropdown> darOffsiteDistrictDropdowns = DarOffsiteDistrictDropdown._getDarOffsiteDistrictDropdownList(TPApp.custId);
        final ArrayList<DarOffsiteTrainerDropdown> darOffsiteTrainerDropdowns = DarOffsiteTrainerDropdown._getDarOffStreetPatrolDropDownList(TPApp.custId);

        _mRideSpinner.setOnClickListener(v -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(RideAlong.this);
            //builderSingle.setIcon(R.drawable.icon);
            builderSingle.setTitle("Select Activity");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RideAlong.this, android.R.layout.select_dialog_singlechoice);
            for (int i = 0; i < darOffsiteTrainerDropdowns.size(); i++) {
                final DarOffsiteTrainerDropdown darSignCheck = darOffsiteTrainerDropdowns.get(i);
                String s = darSignCheck.getMenuName();
                if (s != null) {

                    arrayAdapter.add(s);
                }
            }
            builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                _mRideSpinner.setText(arrayAdapter.getItem(which).toString());
                darOffsiteTrainerDropdowns.forEach((n) -> {
                    if (n.getMenuName().equals(arrayAdapter.getItem(which)))
                        id = n.getId();
                });
                dialog.dismiss();
            });
            builderSingle.show();
        });

        _mEndShift.setOnClickListener(v -> {
           /* Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntent.class);
            if (isNetworkConnected()) {
                EDarServiceJobIntent.enqueueWork(TaskForm22500.this, serviceIntent);
            }*/

            new iOSDialogBuilder(RideAlong.this)
                    .setSubtitle("SJPD Radio logoff \n Performed?")
                    .setPositiveListener("Yes", new iOSDialogClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(iOSDialog dialog) {

                            darSJPDlogoutJob._sjpdlogout(RideAlong.this);

                            Intent serviceIntent = new Intent(RideAlong.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", "");
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", assignmentId);
                            serviceIntent.putExtra("WITCH", "Three");
                            serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                            EDarServiceJobIntentTask.enqueueWork(RideAlong.this, serviceIntent);

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
                                    milageDialog.EndMilageDialogShift(RideAlong.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                }
                                else
                                {
                                    milageDialog.EndMilageDialogShift(RideAlong.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                }
                            } catch (IOException e) {
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

                new iOSDialogBuilder(RideAlong.this)
                        .setSubtitle("SJPD Radio logoff \n Performed?")
                        .setPositiveListener("Yes", new iOSDialogClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(iOSDialog dialog) {

                                darSJPDlogoutJob._sjpdlogout(RideAlong.this);
                                Intent serviceIntent = new Intent(RideAlong.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", "");
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", assignmentId);
                                serviceIntent.putExtra("WITCH", "Three");
                                serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                                EDarServiceJobIntentTask.enqueueWork(RideAlong.this, serviceIntent);

                                dialog.dismiss();
                                //finish();

                                String  Id_colunn=preference.getString("coulmunId");
                                String  Start_mileage= TPApp.getMileage();

                                MilageDialog milageDialog= new MilageDialog();

                                try {
                                    if (isNetworkConnected())
                                    {
                                        milageDialog.EndMilageDialogLogout(RideAlong.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                    }
                                    else
                                    {
                                        milageDialog.EndMilageDialogLogout(RideAlong.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                    }
                                } catch (IOException e) {
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

        _mRideSpinner2.setOnClickListener(v -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(RideAlong.this);
            //builderSingle.setIcon(R.drawable.icon);
            builderSingle.setTitle("Select Activity");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RideAlong.this, android.R.layout.select_dialog_singlechoice);
            for (int i = 0; i < darOffsiteDistrictDropdowns.size(); i++) {
                final DarOffsiteDistrictDropdown darSignCheck = darOffsiteDistrictDropdowns.get(i);
                String s = darSignCheck.getMenuName();
                if (s != null) {

                    arrayAdapter.add(s);
                }
            }
            builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                _mRideSpinner2.setText(arrayAdapter.getItem(which).toString());
                darOffsiteDistrictDropdowns.forEach((n) -> {
                    if (n.getMenuName().equals(arrayAdapter.getItem(which)))
                        id1 = n.getId();
                });
                dialog.dismiss();
            });
            builderSingle.show();
        });


        _mSave.setOnClickListener(v -> {
            validator.validate();
        });
    }

    private void __save() throws IOException {
        progressDialog = ProgressDialog.show(RideAlong.this, "", "Inserting...");

        RideAlongJSON_rpc rpc = new RideAlongJSON_rpc();
        ParamRideAlong param = new ParamRideAlong();
        List<RideAlongModel> aList = new ArrayList<>();
        RideAlongModel model = new RideAlongModel();

        model.setDatetime(_mRideDate.getText().toString());
        model.setDistrictDdElementId(_mDistrict.getText().toString());
        model.setNameOfTrainerDdElementId(_mTrainee.getText().toString());

        model.setNonInforcementDdElementId("3");
        model.setEquipmentId(TPApp.getEquipment());
        model.setSubEquipmentId(TPApp.getEquipmentChild());
        model.setVehicle(TPApp.getVehicleType());
        model.setMillage(TPApp.getMileage());
        model.setDisinfecting(TPApp.getDisinfecting());
        model.setVdr(TPApp.getVdr());
        model.setAssignmentId(5);
        model.setAppId((int) RideAlongModel.getNextPrimaryId());
        model.setUserId(String.valueOf(TPApp.userId));
        model.setDeviceid(String.valueOf(TPApp.deviceId));

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
        rpc.setMethod("OffSiteNonInforcementRideAlongFormInsert");
        System.out.println("RequestObj**"+new Gson().toJson(rpc));
        log.info(new Gson().toJson(rpc));

        if (isNetworkConnected()) {
            try {
               // RideAlongModel.insertRideAlongModel(model);
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertRideAlong(rpc).enqueue(new Callback<DarTicketResponse>() {
                    @Override
                    public void onResponse(Call<DarTicketResponse> call, Response<DarTicketResponse> response) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (response.code() == 200 && response.isSuccessful()) {

                            if (response.body().getResult()!= null)
                            {
                                if (response.body().getResult().getResult() == true){

                                    Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
                                    _mDistrict.setText("");
                                    _mTrainee.setText("");

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

                        }else
                        {
                            Failed();
                            log.error("Server not responding");
                        }
                    }

                    @Override
                    public void onFailure(Call<DarTicketResponse> call, Throwable t) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Failed();
                        log.error("Failure");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Failed();
            }
        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            RideAlongModel.insertRideAlongModel(model);
            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
        }

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

    @Override
    public void onBackPressed() {

        try {
            callTaskReport("",DateUtil.getCurrentDateTime1(),String.valueOf(assignmentId),TPApplication.getInstance().getDarTaskReoprtId(),"3");
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* Intent serviceIntent = new Intent(RideAlong.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", "");
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID", assignmentId);
        serviceIntent.putExtra("WITCH", "Three");
        serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
        EDarServiceJobIntentTask.enqueueWork(RideAlong.this, serviceIntent);
        finish();
        super.onBackPressed();*/
    }

    public  void callTaskReport(String startTime, String endTime, String id,String TaskRecordId,String taskid) throws IOException {
        progressDialog = ProgressDialog.show(RideAlong.this, "", "Please Wait...");
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

        new iOSDialogBuilder(RideAlong.this)
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
                            if (appId != null && appId.size() > 0) {
                                for (int i = 0; i < appId.size(); i++) {
                                    try {
                                        RideAlongModel.removeById(appId.get(i).intValue());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }*/