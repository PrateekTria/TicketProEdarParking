package com.ticketpro.parking.dar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.ticketpro.parking.dar.model.DarAssignmentLocationJob;
import com.ticketpro.parking.dar.model.DarDutyReportJob;
import com.ticketpro.parking.dar.model.DarSignCheck;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.SignCheckModel;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.params.ParamSignCheck;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.request.SignCheckJson_rpc;
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

public class SignCheck extends BaseActivityImpl implements Validator.ValidationListener, MyTracker.ADLocationListener {

    private Button _btnBack, _btnSave, _btnEndShift, _btnLogOut;
    private TextView _spinnerText;
    @NotEmpty
    private EditText _location;
    private Button _btnGPS;
    @NotEmpty
    private EditText _mPermit;
    @NotEmpty
    private EditText _mEnf;
    @NotEmpty
    private EditText _note;

    ArrayList<DarSignCheck> darSignChecks;
    private Integer id;
    private Validator validator;
    private ProgressDialog progressDialog;
    private int asgId = 0;
    private int locId = 0;
    private int taskId = 0;
    private RadioGroup RG_Enf;
    private RadioButton RB_Enf, RB_NEnf;
    private String ForceableType = "";
    private Preference preference;
    private String inTime = null;
    private SharedPreferences sharedPreferences;
    DarAssignmentLocationJob darAssignmentLocationJob = new DarAssignmentLocationJob();
    DarDutyReportJob darDutyReportJob = new DarDutyReportJob();
    DarSJPDlogoutJob darSJPDlogoutJob = new DarSJPDlogoutJob();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_check);
        setLogger(SignCheck.class.getName());
        preference = Preference.getInstance(SignCheck.this);
        TPApplication.getInstance().setDarStartTimeTask(DateUtil.getCurrentDateTime1());
        Intent it = getIntent();
        asgId = it.getIntExtra("ASG_ID", 0);
        locId = it.getIntExtra("LOCATION_ID", 0);
        taskId = it.getIntExtra("TASK_ID", 0);
        _note = findViewById(R.id.signcheck_note);
        _note.setImeOptions(EditorInfo.IME_ACTION_DONE);
       /* _note.setRawInputType(InputType.TYPE_CLASS_TEXT);
        _note.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        _note.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});*/
        _btnBack = findViewById(R.id.sc_btnBack);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        inTime = preference.getString("IN_DATE");

        _btnBack.setOnClickListener(v -> {

            try {
                callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
            } catch (IOException e) {
                e.printStackTrace();
            }

           /* Intent serviceIntent = new Intent(SignCheck.this, EDarServiceJobIntentTask.class);
            serviceIntent.putExtra("START", "");
            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
            serviceIntent.putExtra("ID",String.valueOf(locId));
            serviceIntent.putExtra("WITCH","Three");
            serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
            EDarServiceJobIntentTask.enqueueWork(SignCheck.this, serviceIntent);
            TPApplication.getInstance().setDarStartTimeTask(null);

            finish();*/
        });
        validator = new Validator(this);
        validator.setValidationListener(this);
        _spinnerText = findViewById(R.id.sc_activity_spinner);
        _mPermit = findViewById(R.id.sc_permit);
        _mPermit = findViewById(R.id.sc_permit);
        _location = findViewById(R.id.sc_etLocation);
        _btnGPS = findViewById(R.id.sc_btn_end_shift1);
        _mEnf = findViewById(R.id.sc_enf);
        _btnSave = findViewById(R.id.sc_btn_save);
        _btnEndShift = findViewById(R.id.sc_btnEndShift);
        _btnLogOut = findViewById(R.id.sc_btn_logout);
        RG_Enf = findViewById(R.id.rg_sc_enf);
        RB_Enf = findViewById(R.id.rb_sc_enf);
        RB_NEnf = findViewById(R.id.rb_sc_nenf);
        RG_Enf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_sc_enf) {
                    ForceableType = "Enforceable";
                } else if (i == R.id.rb_sc_nenf) {
                    ForceableType = "NonEnforceable";
                } else {

                }
            }
        });

        _btnEndShift.setOnClickListener(v -> {


           /* Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntent.class);
            if (isNetworkConnected()) {
                EDarServiceJobIntent.enqueueWork(TaskForm22500.this, serviceIntent);
            }*/

            new iOSDialogBuilder(SignCheck.this)
                    .setSubtitle("SJPD Radio logout \n Performed?")
                    .setPositiveListener("Yes", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {

                            String Id_colunn = preference.getString("coulmunId");
                            String Start_mileage = TPApp.getMileage();


                            MilageDialog milageDialog = new MilageDialog();

                            try {
                                if (isNetworkConnected()) {
                                    milageDialog.EndMilageDialogShift(SignCheck.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "Yes", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                                } else {
                                    milageDialog.EndMilageDialogShift(SignCheck.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "No", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                           /* Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                            intent.setClass(SignCheck.this, HomeActivity.class);
                            startActivity(intent);*/
                            darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(), SignCheck.this, TPApplication.getInstance().getDarParkingdutyreportId());
                            darAssignmentLocationJob.callAssignLocationAPI("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getAssignReportId(), TPApplication.getInstance().getAssLocRecId(), SignCheck.this);
                            darSJPDlogoutJob._sjpdlogout(SignCheck.this);


                            Intent serviceIntent = new Intent(SignCheck.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", "");
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(locId));
                            serviceIntent.putExtra("WITCH", "Three");
                            serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                            EDarServiceJobIntentTask.enqueueWork(SignCheck.this, serviceIntent);

                            TPApplication.getInstance().setDarStartTimeTask(null);
                         /*   Intent serviceIntent1 = new Intent(SignCheck.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(asgId));
                            serviceIntent.putExtra("WITCH", "Two");
                            EDarServiceJobIntentTask.enqueueWork(SignCheck.this, serviceIntent1);*/

                            dialog.dismiss();

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
        _btnLogOut.setOnClickListener(v -> {
            try {


                new iOSDialogBuilder(SignCheck.this)
                        .setSubtitle("SJPD Radio logout \n Performed?")
                        .setPositiveListener("Yes", new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                               /* Intent intent = new Intent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                                intent.setClass(SignCheck.this, DarEquipmentNew.class);
                                startActivity(intent);*/
                                darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(), SignCheck.this, TPApplication.getInstance().getDarParkingdutyreportId());
                                darAssignmentLocationJob.callAssignLocationAPI("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getAssignReportId(), TPApplication.getInstance().getAssLocRecId(), SignCheck.this);
                                darSJPDlogoutJob._sjpdlogout(SignCheck.this);

                                Intent serviceIntent = new Intent(SignCheck.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", "");
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", String.valueOf(locId));
                                serviceIntent.putExtra("WITCH", "Three");
                                serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                                EDarServiceJobIntentTask.enqueueWork(SignCheck.this, serviceIntent);

                                TPApplication.getInstance().setDarStartTimeTask(null);
                               /* Intent serviceIntent1 = new Intent(SignCheck.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", String.valueOf(asgId));
                                serviceIntent.putExtra("WITCH", "Two");
                                EDarServiceJobIntentTask.enqueueWork(SignCheck.this, serviceIntent1);*/

                                dialog.dismiss();
                                /*finish();*/
                                String Id_colunn = preference.getString("coulmunId");
                                String Start_mileage = TPApp.getMileage();

                                MilageDialog milageDialog = new MilageDialog();

                                try {
                                    if (isNetworkConnected()) {
                                        milageDialog.EndMilageDialogLogout(SignCheck.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "Yes", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                                    } else {
                                        milageDialog.EndMilageDialogLogout(SignCheck.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "No", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
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
        _btnGPS.setOnClickListener(v -> findLoc());
        //  _btnSave.setOnClickListener(v -> validator.validate());
        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_spinnerText.getText().toString().isEmpty() || _spinnerText.getText().toString().equals("Select Activity")) {
                    SpinnerFailedDialog();
                } else {
                    validator.validate();
                }
            }
        });

        darSignChecks = DarSignCheck._getDarSignCheckList(TPApp.custId);
        _spinnerText.setOnClickListener(v -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(SignCheck.this);
            //builderSingle.setIcon(R.drawable.icon);
            builderSingle.setTitle("Select Activity");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SignCheck.this, android.R.layout.select_dialog_singlechoice);
            for (int i = 0; i < darSignChecks.size(); i++) {
                final DarSignCheck darSignCheck = darSignChecks.get(i);
                String s = darSignCheck.getDdItem();
                arrayAdapter.add(s);
            }
            builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                _spinnerText.setText(arrayAdapter.getItem(which).toString());
                darSignChecks.forEach((n) -> {
                    if (n.getDdItem().equals(arrayAdapter.getItem(which)))
                        id = n.getId();
                });
                dialog.dismiss();
            });
            builderSingle.show();
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
    public void onValidationSucceeded() {
        if (!ForceableType.isEmpty() || !ForceableType.equals("")) {
            try {
                __save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Dialog();
        }

    }

    private void __save() throws IOException {

        progressDialog = ProgressDialog.show(SignCheck.this, "", "Inserting...");
        SignCheckJson_rpc rpc = new SignCheckJson_rpc();
        ParamSignCheck paramSignCheck = new ParamSignCheck();
        List<SignCheckModel> aList = new ArrayList<>();
        SignCheckModel model = new SignCheckModel();
        model.setLocation(_location.getText().toString());
        model.setSigncheckDdElemId(String.valueOf(id));
        model.setPermitNumber(_mPermit.getText().toString());
        model.setEnforceableInput(ForceableType);
        model.setUserId(String.valueOf(TPApp.userId));
        model.setDeviceid(String.valueOf(TPApp.deviceId));
        model.setNotes(_note.getText().toString());
        model.setTaskDate(DateUtil.getCurrentDateTime1());

        if (!preference.getString("coulmunId").isEmpty() || !preference.getString("coulmunId").equals(""))
            model.setMileageId(preference.getString("coulmunId"));
        else
            model.setMileageId("0");

        if (TPApplication.getInstance().getDarTaskReoprtId() != null) {
            model.setDarTaskReportId(TPApplication.getInstance().getDarTaskReoprtId());
        } else {
            model.setDarTaskReportId("");
        }
        model.setEquipmentId(TPApp.getEquipment());
        model.setSubEquipmentId(TPApp.getEquipmentChild());
        model.setVehicle(TPApp.getVehicleType());
        model.setMileage(TPApp.getMileage());
        model.setDisinfecting(TPApp.getDisinfecting());
        model.setVdr(TPApp.getVdr());
        model.setAssignmentId(asgId);
        model.setLocationId(String.valueOf(locId));
        model.setTaskId(String.valueOf(taskId));
        model.setActionId("1");
        /* model.setForceable(ForceableType);*/
        model.setAppId((int) SignCheckModel.getNextPrimaryId());
        aList.add(model);
        paramSignCheck.setDetails(aList);
        paramSignCheck.setCustId(TPApp.custId);
        rpc.setParams(paramSignCheck);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("darSignCheckDetailInsert");

        System.out.println("RequestObj**" + new Gson().toJson(rpc));
        log.info(new Gson().toJson(rpc));
        if (isNetworkConnected()) {
            try {

                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertSignCheck(rpc).enqueue(new Callback<DarTicketResponse>() {
                    @Override
                    public void onResponse(Call<DarTicketResponse> call, Response<DarTicketResponse> response) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (response.code() == 200 && response.isSuccessful()) {
                            if (response.body().getResult() != null) {
                                if (response.body().getResult().getResult() == true) {
                                    Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
                                    _spinnerText.setHint("Select Activity");
                                    _mPermit.setText("");
                                    _mEnf.setText("");
                                    _location.setText("");
                                    _note.setText("");
                                    RG_Enf.clearCheck();
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
            SignCheckModel.insertSignCheckModel(model);
            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
            _spinnerText.setHint("Select Activity");
            _mPermit.setText("");
            _mEnf.setText("");
            _location.setText("");
            _note.setText("");
            RG_Enf.clearCheck();
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
    public void whereIAM(ADLocation loc) {
        String latitude = String.valueOf(loc.lat);
        String longitude = String.valueOf(loc.longi);
        System.out.println();
        //String[] split = loc.address.split(",");
        Address gpsAddress = new Address();
        gpsAddress.setLocation(loc.address);
        gpsAddress.setStreetNumber(loc.streetNumber);
        _location.setText(TPUtility.getFullAddress(gpsAddress));
    }

    private void findLoc() {
        new GetLocation1(SignCheck.this, this).track();
    }

    public void SpinnerFailedDialog() {

        new iOSDialogBuilder(SignCheck.this)
                .setSubtitle("Please Select Activity")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })

                .build().show();
        //new ProxyImpl().updateDarTable();
    }

    public void Dialog() {

        new iOSDialogBuilder(SignCheck.this)
                .setSubtitle("Please Checked required items!")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })

                .build().show();
        //new ProxyImpl().updateDarTable();
    }

    @Override
    public void onBackPressed() {

        try {
            callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
        } catch (IOException e) {
            e.printStackTrace();
        }

      /*  Intent serviceIntent = new Intent(SignCheck.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", "");
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID",String.valueOf(locId));
        serviceIntent.putExtra("WITCH","Three");
        serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
        EDarServiceJobIntentTask.enqueueWork(SignCheck.this, serviceIntent);
        TPApplication.getInstance().setDarStartTimeTask(null);

        finish();
        super.onBackPressed();*/
    }

    public void callTaskReport(String startTime, String endTime, String id, String TaskRecordId, String taskid) throws IOException {
        progressDialog = ProgressDialog.show(SignCheck.this, "", "Please Wait...");
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

        new iOSDialogBuilder(SignCheck.this)
                .setSubtitle("Please try again")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();

                    }
                })
                .build().show();

    }

      /*final DarTicketResult result = response.body().getResult();
                            final List<Integer> appId = result.getAppId();
                            if (appId != null && appId.size()>0) {
                                for (int i = 0; i < appId.size(); i++) {
                                    try {
                                        SignCheckModel.removeById(appId.get(i).intValue());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }*/

}