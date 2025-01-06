package com.ticketpro.parking.dar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import com.ticketpro.gpslibrary.ADLocation;
import com.ticketpro.gpslibrary.GetLocation1;
import com.ticketpro.gpslibrary.MyTracker;
import com.ticketpro.model.Address;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.Admin;
import com.ticketpro.parking.dar.model.DarAdminDropdown;
import com.ticketpro.parking.dar.model.DarAssignmentLocationJob;
import com.ticketpro.parking.dar.model.DarDutyReportJob;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.params.ParamAdmin;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.request.AdminJson_rpc;
import com.ticketpro.parking.dar.model.request.TaskReport_rpc;
import com.ticketpro.parking.service.EDarServiceJobIntentTask;
import com.ticketpro.util.DateUtil;
import com.ticketpro.util.Preference;
import com.ticketpro.util.TPUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdministrativeForm extends BaseActivityImpl implements Validator.ValidationListener, MyTracker.ADLocationListener {
    private Button _mSave;
    private Button _mLogout;
    private Button _mEndShift;
    private Button _mGps;
    @NotEmpty
    private EditText _mlocation;
    private TextView mSpinner;
    private Integer id = 0;
    private ProgressDialog progressDialog;
    private int asgId = 0;
    private int locId = 0;
    private int taskId = 0;
    @NotEmpty
    private EditText _note;
    private Preference preference;
    private String inTime = null;
    private Validator validator;
    SharedPreferences sharedPreferences;
    DarAssignmentLocationJob darAssignmentLocationJob = new DarAssignmentLocationJob();
    DarDutyReportJob darDutyReportJob = new DarDutyReportJob();
    DarSJPDlogoutJob darSJPDlogoutJob = new DarSJPDlogoutJob();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrative_form);
        setLogger(AdministrativeForm.class.getName());
        TPApplication.getInstance().setDarStartTimeTask(DateUtil.getCurrentDateTime1());
        Intent it = getIntent();
        asgId = it.getIntExtra("ASG_ID", 0);
        locId = it.getIntExtra("LOCATION_ID", 0);
        taskId = it.getIntExtra("TASK_ID", 0);
        validator = new Validator(this);
        validator.setValidationListener(this);

        Button _btnBack = findViewById(R.id.nonf_btnBack);
        _mSave = findViewById(R.id.adm_btn_save);
        _mlocation = findViewById(R.id.adm_location);
        _mGps = findViewById(R.id.adm_btn_gps);
        _mEndShift = findViewById(R.id.adm_btnEndShift);
        _mLogout = findViewById(R.id.adm_btn_logout);
        _note = findViewById(R.id.administrative_note);
        _note.setImeOptions(EditorInfo.IME_ACTION_DONE);
       /* _note.setRawInputType(InputType.TYPE_CLASS_TEXT);
        _note.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        _note.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});*/
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        preference = Preference.getInstance(AdministrativeForm.this);
        inTime = preference.getString("IN_DATE");
        _mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findLoc();
            }
        });
        _mEndShift.setOnClickListener(v -> {





           /* Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntent.class);
            if (isNetworkConnected()) {
                EDarServiceJobIntent.enqueueWork(TaskForm22500.this, serviceIntent);
            }*/

            new iOSDialogBuilder(AdministrativeForm.this)
                    .setSubtitle("SJPD Radio logoff \n Performed?")
                    .setPositiveListener("Yes", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {

                           /* Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                            intent.setClass(AdministrativeForm.this, HomeActivity.class);
                            startActivity(intent);*/
                            darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(), AdministrativeForm.this, TPApplication.getInstance().getDarParkingdutyreportId());
                            darAssignmentLocationJob.callAssignLocationAPI("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getAssignReportId(), TPApplication.getInstance().getAssLocRecId(), AdministrativeForm.this);
                            darSJPDlogoutJob._sjpdlogout(AdministrativeForm.this);

                            Intent serviceIntent = new Intent(AdministrativeForm.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", "");
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(locId));
                            serviceIntent.putExtra("WITCH", "Three");
                            serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                            EDarServiceJobIntentTask.enqueueWork(AdministrativeForm.this, serviceIntent);

                            TPApplication.getInstance().setDarStartTimeTask(null);
                            /*Intent serviceIntent1 = new Intent(AdministrativeForm.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(asgId));
                            serviceIntent.putExtra("WITCH", "Two");
                            EDarServiceJobIntentTask.enqueueWork(AdministrativeForm.this, serviceIntent1);*/

                            dialog.dismiss();
                            String Id_colunn = preference.getString("coulmunId");
                            String Start_mileage = TPApp.getMileage();

                            MilageDialog milageDialog = new MilageDialog();

                            try {
                                if (isNetworkConnected()) {
                                    milageDialog.EndMilageDialogShift(AdministrativeForm.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "Yes", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                                } else {
                                    milageDialog.EndMilageDialogShift(AdministrativeForm.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "No", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
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


                new iOSDialogBuilder(AdministrativeForm.this)
                        .setSubtitle("SJPD Radio logoff \n Performed?")
                        .setPositiveListener("Yes", new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                                /*Intent intent = new Intent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                                intent.setClass(AdministrativeForm.this, DarEquipmentNew.class);
                                startActivity(intent);*/
                                darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(), AdministrativeForm.this, TPApplication.getInstance().getDarParkingdutyreportId());
                                darAssignmentLocationJob.callAssignLocationAPI("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getAssignReportId(), TPApplication.getInstance().getAssLocRecId(), AdministrativeForm.this);
                                darSJPDlogoutJob._sjpdlogout(AdministrativeForm.this);

                                Intent serviceIntent = new Intent(AdministrativeForm.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", "");
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", String.valueOf(locId));
                                serviceIntent.putExtra("WITCH", "Three");
                                serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                                EDarServiceJobIntentTask.enqueueWork(AdministrativeForm.this, serviceIntent);

                                TPApplication.getInstance().setDarStartTimeTask(null);
                               /* Intent serviceIntent1 = new Intent(AdministrativeForm.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", String.valueOf(asgId));
                                serviceIntent.putExtra("WITCH", "Two");
                                EDarServiceJobIntentTask.enqueueWork(AdministrativeForm.this, serviceIntent1);

                               */
                                dialog.dismiss();
                                // finish();
                                String Id_colunn = preference.getString("coulmunId");
                                String Start_mileage = TPApp.getMileage();

                                MilageDialog milageDialog = new MilageDialog();

                                try {
                                    if (isNetworkConnected()) {
                                        milageDialog.EndMilageDialogLogout(AdministrativeForm.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "Yes", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                                    } else {
                                        milageDialog.EndMilageDialogLogout(AdministrativeForm.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "No", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
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
        _btnBack.setOnClickListener(v -> {

            try {
                callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
            } catch (IOException e) {
                e.printStackTrace();
            }

          /*  Intent serviceIntent = new Intent(AdministrativeForm.this, EDarServiceJobIntentTask.class);
            serviceIntent.putExtra("START","");
            serviceIntent.putExtra("END",DateUtil.getCurrentDateTime1());
            serviceIntent.putExtra("ID",String.valueOf(locId));
            serviceIntent.putExtra("WITCH","Three");
            serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
            EDarServiceJobIntentTask.enqueueWork(AdministrativeForm.this, serviceIntent);
            TPApplication.getInstance().setDarStartTimeTask(null);

            finish();*/
        });
        mSpinner = findViewById(R.id.adm_activity_spinner);
        final ArrayList<DarAdminDropdown> darAdminDropdowns = DarAdminDropdown._getDarAdminDropdownList(TPApp.custId);
        mSpinner.setOnClickListener(v -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(AdministrativeForm.this);
            //builderSingle.setIcon(R.drawable.icon);
            builderSingle.setTitle("Select Activity");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AdministrativeForm.this, android.R.layout.select_dialog_singlechoice);
            for (int i = 0; i < darAdminDropdowns.size(); i++) {
                final DarAdminDropdown darAdminDropdown = darAdminDropdowns.get(i);
                String s = darAdminDropdown.getMenuName();
                arrayAdapter.add(s);
            }
            builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                mSpinner.setText(arrayAdapter.getItem(which));
                darAdminDropdowns.forEach((n) -> {
                    if (n.getMenuName().equals(arrayAdapter.getItem(which)))
                        id = n.getId();
                    System.out.println("ID IS================>" + id);
                });
                dialog.dismiss();
            });
            builderSingle.show();
        });


        _mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (id > 0) {
                        validator.validate();
                    } else {
                        Toast.makeText(getInstance(), "Select Activity", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void _saveDetails() throws IOException {
        progressDialog = ProgressDialog.show(AdministrativeForm.this, "", "Inserting...");

        AdminJson_rpc rpc = new AdminJson_rpc();
        ParamAdmin paramAdmin = new ParamAdmin();
        List<Admin> aList = new ArrayList<>();

        Admin model = new Admin();
        model.setDdItem(String.valueOf(id));
        if (!preference.getString("coulmunId").isEmpty() || !preference.getString("coulmunId").equals(""))
            model.setMileageId(preference.getString("coulmunId"));
        else
            model.setMileageId("0");
        model.setEquipmentId(TPApp.getEquipment());
        model.setSubEquipmentId(TPApp.getEquipmentChild());
        model.setVehicle(TPApp.getVehicleType());
        model.setMileage(TPApp.getMileage());
        model.setGPSlocation(_mlocation.getText().toString());

        model.setDisinfecting(TPApp.getDisinfecting());
        model.setVdr(TPApp.getVdr());

        if (TPApplication.getInstance().getDarTaskReoprtId() != null) {
            model.setDarTaskReportId(TPApplication.getInstance().getDarTaskReoprtId());
        } else {
            model.setDarTaskReportId("");
        }
        model.setAssignmentId(String.valueOf(asgId));
        model.setLocationId(String.valueOf(locId));
        model.setTaskId(String.valueOf(taskId));
        model.setNotes(_note.getText().toString());
        model.setUserId(String.valueOf(TPApp.userId));
        model.setDeviceid(String.valueOf(TPApp.deviceId));
        model.setAppId((int) Admin.getNextPrimaryId());
        model.setTaskDate(DateUtil.getCurrentDateTime1());
        aList.add(model);
        paramAdmin.setDetails(aList);
        paramAdmin.setCustId(TPApp.custId);
        rpc.setParams(paramAdmin);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("darAdminDetailsInsert");
        System.out.println("RequestObj**" + new Gson().toJson(rpc));
        //  Admin.insertAdmin(model);
        log.info(new Gson().toJson(rpc));
        if (isNetworkConnected()) {
            try {

                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertAdmin(rpc).enqueue(new Callback<DarTicketResponse>() {
                    @Override
                    public void onResponse(Call<DarTicketResponse> call, Response<DarTicketResponse> response) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        if (response.code() == 200 && response.isSuccessful()) {

                            if (response.body().getResult() != null) {
                                if (response.body().getResult().getResult() == true) {
                                    _note.setText("");
                                    _mlocation.setText("");
                                    Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();

                                } else {
                                    Failed();
                                    log.error("response incorrect");
                                }
                            } else {
                                Failed();
                                log.error("response incorrect");
                            }


                        } else {
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
            Admin.insertAdmin(model);
            _note.setText("");
            _mlocation.setText("");
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
            _saveDetails();
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
            callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Intent serviceIntent = new Intent(AdministrativeForm.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START","");
        serviceIntent.putExtra("END",DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID",String.valueOf(locId));
        serviceIntent.putExtra("WITCH","Three");
        serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
        EDarServiceJobIntentTask.enqueueWork(AdministrativeForm.this, serviceIntent);
        TPApplication.getInstance().setDarStartTimeTask(null);

        finish();
        super.onBackPressed();*/
    }


    public void callTaskReport(String startTime, String endTime, String id, String TaskRecordId, String taskid) throws IOException {
        progressDialog = ProgressDialog.show(AdministrativeForm.this, "", "Please Wait...");
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
                    if (response.isSuccessful() && response.code() == 200) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        final List<Integer> appId = response.body().getResult().getAppId();
                        try {
                            if (response.body().getResult().getDarTaskReportId().get(0) != null) {
                                log.info("Task logout" + DateUtil.getCurrentDateTime1());
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

        new iOSDialogBuilder(AdministrativeForm.this)
                .setSubtitle("Please try again")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();

                    }
                })
                .build().show();

    }

    private void findLoc() {

        new GetLocation1(AdministrativeForm.this, this).track();

        /*OneTimeWorkRequest locationRequest = new OneTimeWorkRequest.Builder(LocationGps.class)
                .setInitialDelay(3, TimeUnit.SECONDS)  // Delay before the work starts
                .build();


        WorkManager.getInstance(this).enqueue(locationRequest);
*/
    }



    @Override
    public void whereIAM(ADLocation loc) {
        try {
            String latitude = String.valueOf(loc.lat);
            String longitude = String.valueOf(loc.longi);
            System.out.println();
            //String[] split = loc.address.split(",");
            Address gpsAddress = new Address();
            gpsAddress.setLocation(loc.address);
            gpsAddress.setStreetNumber(loc.streetNumber);
            _mlocation.setText(TPUtility.getFullAddress(gpsAddress));
        } catch (Exception e) {

        }
    }

    /* final DarTicketResult result = response.body().getResult();
                            final List<Integer> appId = result.getAppId();
                            if (appId!=null && appId.size()>0) {
                                for (int i = 0; i < appId.size(); i++) {
                                    try {
                                        Admin.removeById(appId.get(i).intValue());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }*/
}