package com.ticketpro.parking.dar;

import android.Manifest;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
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
import com.ticketpro.parking.activity.HomeActivity;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.Dar22500DisposionDropDownElement;
import com.ticketpro.parking.dar.model.DarAssignmentLocationJob;
import com.ticketpro.parking.dar.model.DarDutyReportJob;
import com.ticketpro.parking.dar.model.DarFieldContactDropdown;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.DarTicketResult;
import com.ticketpro.parking.dar.model.FieldContactDetails;
import com.ticketpro.parking.dar.model.TaskForm22500Model;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.VehMaintenanceModel;
import com.ticketpro.parking.dar.model.params.ParamFieldContact;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.request.FieldContactJson_rpc;
import com.ticketpro.parking.dar.model.request.TaskReport_rpc;
import com.ticketpro.parking.service.EDarServiceJobIntentTask;
import com.ticketpro.parking.service.LocationGps;
import com.ticketpro.util.DateUtil;
import com.ticketpro.util.Preference;
import com.ticketpro.util.TPUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FieldContract extends BaseActivityImpl implements Validator.ValidationListener, MyTracker.ADLocationListener {

    private TextView _spinnerView;
    @NotEmpty
    private EditText _mLocation;
    @NotEmpty
    private EditText _mNotes;
    private Button _mGps;
    private Button _mSave, _mEndShift, _mLogout;

    private Validator validator;
    private ProgressDialog progressDialog;
    private ArrayList<DarFieldContactDropdown> darFieldContactDropdowns;
    private Integer id;
    private int asgId = 0;
    private int locId = 0;
    private int taskId = 0;
    private EditText _note;
    SharedPreferences sharedPreferences;
    private Preference preference;
    private String inTime = null;
    DarAssignmentLocationJob darAssignmentLocationJob= new DarAssignmentLocationJob();
    DarDutyReportJob darDutyReportJob= new DarDutyReportJob();
    DarSJPDlogoutJob darSJPDlogoutJob= new DarSJPDlogoutJob();
    boolean permission_value=false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_contract);
        setLogger(FieldContract.class.getName());
        preference = Preference.getInstance(FieldContract.this);
        TPApplication.getInstance().setDarStartTimeTask(DateUtil.getCurrentDateTime1());
        Intent it = getIntent();
        asgId = it.getIntExtra("ASG_ID", 0);
        locId = it.getIntExtra("LOCATION_ID", 0);
        taskId = it.getIntExtra("TASK_ID", 0);
        validator = new Validator(this);
        //_note=findViewById(R.id.fld_note);
       // _note.setImeOptions(EditorInfo.IME_ACTION_DONE);
       /* _note.setRawInputType(InputType.TYPE_CLASS_TEXT);*/
   /*     _note.setFilters(new InputFilter[]{new InputFilter.AllCaps()});*/
     /*   _note.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});*/
        validator.setValidationListener(this);
        inTime = preference.getString("IN_DATE");
        sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Button _btnBack = findViewById(R.id.fld_btnBack);

       // locationPermission();
        _btnBack.setOnClickListener(v -> {

            try {
                callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
            } catch (IOException e) {
                e.printStackTrace();
            }

           /* Intent serviceIntent = new Intent(FieldContract.this, EDarServiceJobIntentTask.class);
            serviceIntent.putExtra("START", "");
            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
            serviceIntent.putExtra("ID", String.valueOf(locId));
            serviceIntent.putExtra("WITCH", "Three");
            serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
            EDarServiceJobIntentTask.enqueueWork(FieldContract.this, serviceIntent);
            TPApplication.getInstance().setDarStartTimeTask(null);

            finish();*/
        });

        __initView();


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void __initView() {
        _mLocation = findViewById(R.id.fld_location);
        _mNotes = findViewById(R.id.fld_note);
        _mGps = findViewById(R.id.fld_btn_gps);
        _mSave = findViewById(R.id.fld_btn_save);
        _mEndShift = findViewById(R.id.fld_btnEndShift);
        _mLogout = findViewById(R.id.fld_btn_logout);
        _spinnerView = findViewById(R.id.fld_activity_spinner);

        _mEndShift.setOnClickListener(v -> {



           /* Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntent.class);
            if (isNetworkConnected()) {
                EDarServiceJobIntent.enqueueWork(TaskForm22500.this, serviceIntent);
            }*/

            new iOSDialogBuilder(FieldContract.this)
                    .setSubtitle("SJPD Radio logoff \n Performed?")
                    .setPositiveListener("Yes", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {

                          /*  Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                            intent.setClass(FieldContract.this, HomeActivity.class);
                            startActivity(intent);*/
                            darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(),FieldContract.this,TPApplication.getInstance().getDarParkingdutyreportId());
                            darAssignmentLocationJob.callAssignLocationAPI("",DateUtil.getCurrentDateTime1(),String.valueOf(asgId),TPApplication.getInstance().getAssignReportId(),TPApplication.getInstance().getAssLocRecId(),FieldContract.this);
                            darSJPDlogoutJob._sjpdlogout(FieldContract.this);

                            Intent serviceIntent = new Intent(FieldContract.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", "");
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(locId));
                            serviceIntent.putExtra("WITCH", "Three");
                            serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
                            EDarServiceJobIntentTask.enqueueWork(FieldContract.this, serviceIntent);

                            TPApplication.getInstance().setDarStartTimeTask(null);
                           /* Intent serviceIntent1 = new Intent(FieldContract.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(asgId));
                            serviceIntent.putExtra("WITCH", "Two");
                            EDarServiceJobIntentTask.enqueueWork(FieldContract.this, serviceIntent1);
                           */ dialog.dismiss();

                            String  Id_colunn=preference.getString("coulmunId");
                            String  Start_mileage= TPApp.getMileage();

                            MilageDialog milageDialog= new MilageDialog();

                            try {
                                if (isNetworkConnected())
                                {
                                    milageDialog.EndMilageDialogShift(FieldContract.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                }
                                else
                                {
                                    milageDialog.EndMilageDialogShift(FieldContract.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
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


                new iOSDialogBuilder(FieldContract.this)
                        .setSubtitle("SJPD Radio logoff \n Performed?")
                        .setPositiveListener("Yes", new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                                /*Intent intent = new Intent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                                intent.setClass(FieldContract.this, DarEquipmentNew.class);
                                startActivity(intent);*/
                                darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(),FieldContract.this,TPApplication.getInstance().getDarParkingdutyreportId());
                                darAssignmentLocationJob.callAssignLocationAPI("",DateUtil.getCurrentDateTime1(),String.valueOf(asgId),TPApplication.getInstance().getAssignReportId(),TPApplication.getInstance().getAssLocRecId(),FieldContract.this);
                                darSJPDlogoutJob._sjpdlogout(FieldContract.this);


                                Intent serviceIntent = new Intent(FieldContract.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", "");
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", String.valueOf(locId));
                                serviceIntent.putExtra("WITCH", "Three");
                                serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
                                EDarServiceJobIntentTask.enqueueWork(FieldContract.this, serviceIntent);

                                TPApplication.getInstance().setDarStartTimeTask(null);
                              /*  Intent serviceIntent1 = new Intent(FieldContract.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", String.valueOf(asgId));
                                serviceIntent.putExtra("WITCH", "Two");
                                EDarServiceJobIntentTask.enqueueWork(FieldContract.this, serviceIntent1);*/
                                dialog.dismiss();
                                //finish();
                                String  Id_colunn=preference.getString("coulmunId");
                                String  Start_mileage= TPApp.getMileage();

                                MilageDialog milageDialog= new MilageDialog();

                                try {
                                    if (isNetworkConnected())
                                    {
                                        milageDialog.EndMilageDialogLogout(FieldContract.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                    }
                                    else
                                    {
                                        milageDialog.EndMilageDialogLogout(FieldContract.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
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

       /* _mNotes.setRawInputType(InputType.TYPE_CLASS_TEXT);
        _mNotes.setFilters(new InputFilter[]{new InputFilter.AllCaps()});*/
        _mSave.setOnClickListener(v -> {
            validator.validate();
        });
        _mGps.setOnClickListener(v -> {
       /*     progressDialog= new ProgressDialog(FieldContract.this);
            progressDialog.setMessage("Please wait");
            progressDialog.show();
            progressDialog.setCancelable(true);*/
            /*if (permission_value==true)
            {
                findLoc();
            }*/

            findLoc();

        });
        darFieldContactDropdowns = DarFieldContactDropdown._getDarFieldContactDropdownList(TPApp.custId);
        _spinnerView.setOnClickListener(v -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(FieldContract.this);
            //builderSingle.setIcon(R.drawable.icon);
            builderSingle.setTitle("Select Activity");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(FieldContract.this, android.R.layout.select_dialog_singlechoice);
            for (int i = 0; i < darFieldContactDropdowns.size(); i++) {
                final DarFieldContactDropdown dropdown = darFieldContactDropdowns.get(i);
                String s = dropdown.getMenuName();
                arrayAdapter.add(s);
            }
            builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                _spinnerView.setText(arrayAdapter.getItem(which).toString());
                darFieldContactDropdowns.forEach((n) -> {
                    if (n.getMenuName().equals(arrayAdapter.getItem(which)))
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
        try {
            __save();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void __save() throws IOException {
        progressDialog = ProgressDialog.show(FieldContract.this, "", "Inserting...");
        FieldContactJson_rpc jsonRpc = new FieldContactJson_rpc();
        List<FieldContactDetails> aList = new ArrayList<>();
        ParamFieldContact param = new ParamFieldContact();

        FieldContactDetails details = new FieldContactDetails();
        details.setDdItem(String.valueOf("2"));
        details.setLocation(_mLocation.getText().toString());
        details.setNotes(_mNotes.getText().toString());
        details.setUserId(String.valueOf(TPApp.userId));
        details.setDeviceId(String.valueOf(TPApp.deviceId));
        if (TPApplication.getInstance().getDarTaskReoprtId()!=null)
        {
            details.setDarTaskReportId(TPApplication.getInstance().getDarTaskReoprtId());
        }
        else
        {
            details.setDarTaskReportId("");
        }
        if (!preference.getString("coulmunId").isEmpty() || !preference.getString("coulmunId").equals(""))
            details.setMileageId(preference.getString("coulmunId"));
        else
            details.setMileageId("0");
        details.setEquipmentId(TPApp.getEquipment());
        details.setSubEquipmentId(TPApp.getEquipmentChild());
        details.setVehicle(TPApp.getVehicleType());
        details.setMileage(TPApp.getMileage());
        details.setDisinfecting(TPApp.getDisinfecting());
        details.setVdr(TPApp.getVdr());
        details.setAppId((int) FieldContactDetails.getNextPrimaryId());
        details.setTaskDate(DateUtil.getCurrentDateTime1());
        details.setAssignmentId(asgId);
        details.setLocationId(String.valueOf(locId));
        details.setTaskId(String.valueOf(taskId));
        //details.setActionId("1");
        aList.add(details);

        param.setCustId(TPApp.custId);
        param.setDetails(aList);
        jsonRpc.setJsonrpc("2.o");
        jsonRpc.setMethod("dar_FieldContactInsert");
        jsonRpc.setId("82F85DB43CBF6");
        jsonRpc.setParams(param);
        System.out.println("RequestObj**"+new Gson().toJson(jsonRpc));

        if (isNetworkConnected()) {
            try {
               // FieldContactDetails.insertFieldContactDetails(details);
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertFieldContact(jsonRpc).enqueue(new Callback<DarTicketResponse>() {
                    @Override
                    public void onResponse(Call<DarTicketResponse> call, Response<DarTicketResponse> response) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (response.isSuccessful() && response.code() == 200) {

                            if (response.body().getResult() != null)
                            {
                                if (response.body().getResult().getResult()== true)
                                {
                                    Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
                                    _mLocation.setText("");
                                    _mNotes.setText("");
                                }
                                else {
                                    Failed();
                                    log.error("response incorrect");
                                }
                            }else {
                                Failed();
                                log.error("response incorrect");
                            }



                        }else {

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
                        Log.d("Error", t.getMessage());
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
            FieldContactDetails.insertFieldContactDetails(details);
            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
            _mLocation.setText("");
            _mNotes.setText("");
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
        _mLocation.setText(TPUtility.getFullAddress(gpsAddress));
    }

    private void findLoc() {

        new GetLocation1(FieldContract.this, this).track();

        /*OneTimeWorkRequest locationRequest = new OneTimeWorkRequest.Builder(LocationGps.class)
                .setInitialDelay(3, TimeUnit.SECONDS)  // Delay before the work starts
                .build();


        WorkManager.getInstance(this).enqueue(locationRequest);
*/
    }

    @Override
    public void onBackPressed() {

        try {
            callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* Intent serviceIntent = new Intent(FieldContract.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", "");
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID", String.valueOf(locId));
        serviceIntent.putExtra("WITCH", "Three");
        serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
        EDarServiceJobIntentTask.enqueueWork(FieldContract.this, serviceIntent);
        TPApplication.getInstance().setDarStartTimeTask(null);
        finish();
        super.onBackPressed();*/
    }


    public void callTaskReport(String startTime, String endTime, String id, String TaskRecordId, String taskid) throws IOException {
        progressDialog = ProgressDialog.show(FieldContract.this, "", "Please Wait...");
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
                                log.info("Task logout"+DateUtil.getCurrentDateTime1());
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

        new iOSDialogBuilder(FieldContract.this)
                .setSubtitle("Please try again")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();

                    }
                })
                .build().show();

    }

    public void locationPermission()
    {
        Dexter.withActivity(FieldContract.this)
                .withPermissions(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {


                        if (report.areAllPermissionsGranted()) {

                            permission_value = true;

                        } else {

                            Toast.makeText(FieldContract.this, " Location Permission not granted", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                    }
                })
                .onSameThread()
                .check();
    }

    public static void updateTextViewOnUiThread (String address){

        FieldContract myActivity = FieldContract.getRunningInstance();
        if (myActivity != null) {

            myActivity._mLocation.setText(address);
            if (myActivity.progressDialog.isShowing())
            {
                myActivity.progressDialog.dismiss();
            }
        }
        else {
            if (myActivity.progressDialog.isShowing())
            {
                myActivity.progressDialog.dismiss();
            }
        }
    }

    private static FieldContract runningInstance;

    private static FieldContract getRunningInstance () {
        return runningInstance;
    }

    @Override
    protected void onResume () {
        super.onResume();
        runningInstance = this;
    }

    @Override
    protected void onPause () {
        super.onPause();
        runningInstance = null;
    }

     /* final DarTicketResult result = response.body().getResult();
                            final List<Integer> appId = result.getAppId();
                            if (appId !=null && appId.size()>0){
                                for (int i = 0; i < appId.size(); i++) {
                                    try {
                                        FieldContactDetails.removeById(appId.get(i).intValue());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
*/
}