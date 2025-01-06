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
import android.widget.CheckBox;
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
import com.ticketpro.model.ChalkVehicle;
import com.ticketpro.model.Ticket;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.ChalkVehicleActivity;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.activity.WriteTicketActivity;
import com.ticketpro.parking.dar.model.Dar22500DisposionDropDownElement;
import com.ticketpro.parking.dar.model.DarAssignmentLocationJob;
import com.ticketpro.parking.dar.model.DarDutyReportJob;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.DarTicketResult;
import com.ticketpro.parking.dar.model.TaskForm22500Model;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.params.ParamTaskForm22500;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.request.Task22500Jsonrpc;
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

public class TaskForm22500 extends BaseActivityImpl implements Validator.ValidationListener, View.OnClickListener, MyTracker.ADLocationListener {

    private Button _btnBack, _btnSave, _btnEndShift, _btnLogOut;
    private Button _writeTicket;
    private Button _chalkVehicle;
    private TextView _title;
    private TextView _spinnerText;
    private EditText _pdEvent;
    @NotEmpty
    private EditText _location;
    private Button _btnGPS;
    private Button _btnTow;
    private CheckBox mCheckbox;
    @NotEmpty
    private EditText _note;
    private Validator validator;
    private ProgressDialog progressDialog;
    private ArrayList<Dar22500DisposionDropDownElement> element;
    private int asgId = 0;
    private int locId = 0;
    private int taskId = 0;
    private Integer id;
    private Integer checkId = 0;
    MilageDialog milageDialog;
    SharedPreferences sharedPreferences;
    DarAssignmentLocationJob darAssignmentLocationJob = new DarAssignmentLocationJob();
    DarDutyReportJob darDutyReportJob = new DarDutyReportJob();
    DarSJPDlogoutJob darSJPDlogoutJob = new DarSJPDlogoutJob();
    private Preference preference;
    private String inTime = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form22500);
        setLogger(TaskForm22500.class.getName());
        preference = Preference.getInstance(TaskForm22500.this);
        TPApplication.getInstance().setDarStartTimeTask(DateUtil.getCurrentDateTime1());
        inTime = preference.getString("IN_DATE");
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Intent it = getIntent();
        asgId = it.getIntExtra("ASG_ID", 0);
        locId = it.getIntExtra("LOCATION_ID", 0);
        taskId = it.getIntExtra("TASK_ID", 0);
        getDatafromDB();
        validator = new Validator(this);
        validator.setValidationListener(this);
        _btnBack = findViewById(R.id.nonf_btnBack);
        _writeTicket = findViewById(R.id.f22_write_ticket);
        _chalkVehicle = findViewById(R.id.f22_chalk);

        _btnEndShift = findViewById(R.id.nonf_btnEndShift);
        _btnSave = findViewById(R.id.nonf_btn_save);
        _btnLogOut = findViewById(R.id.nonf_btn_logout);
        _spinnerText = findViewById(R.id.f22500_activity_spinner);
        _title = findViewById(R.id.nonf_title);
        _pdEvent = findViewById(R.id.f22500_event);
        _location = findViewById(R.id.f22500_location);
        _btnGPS = findViewById(R.id.f22500_btn_gps);
        _btnTow = findViewById(R.id.f22_tow);
        mCheckbox = findViewById(R.id.f22500_checkBox2);
        _note = findViewById(R.id.f22500_note);
        _pdEvent.requestFocus();
        _note.setImeOptions(EditorInfo.IME_ACTION_DONE);
        /*_note.setImeOptions(EditorInfo.IME_ACTION_DONE);
        _note.setRawInputType(InputType.TYPE_CLASS_TEXT);
         _note.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200) });*/
       // _note.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        _btnGPS.setOnClickListener(v -> findLoc());
        //  _btnSave.setOnClickListener(v -> validator.validate());
        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spinnerText = _spinnerText.getText().toString().trim();
                String pdEventText = _pdEvent.getText().toString().trim();

                if (spinnerText.isEmpty() || spinnerText.equals("Select")) {
                    SpinnerFailedDialog();
                } else if (spinnerText.equals("Mark Vehicle") && pdEventText.isEmpty()) {
                    validator.validate();
                } else if (pdEventText.isEmpty()) {
                    _pdEvent.setError("Please enter Event/Case#");
                } else {
                    validator.validate();
                }

            }
        });
        _btnBack.setOnClickListener(v ->
        {

            try {
                callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
            } catch (IOException e) {
                e.printStackTrace();
            }

          /*  Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntentTask.class);
            serviceIntent.putExtra("START", "");
            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
            serviceIntent.putExtra("ID", String.valueOf(locId));
            serviceIntent.putExtra("WITCH", "Three");
            serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
            EDarServiceJobIntentTask.enqueueWork(TaskForm22500.this, serviceIntent);
            TPApplication.getInstance().setDarStartTimeTask(null);
            finish();*/
        });


        _btnEndShift.setOnClickListener(v -> {




           /* Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntent.class);
            if (isNetworkConnected()) {
                EDarServiceJobIntent.enqueueWork(TaskForm22500.this, serviceIntent);
            }*/

            new iOSDialogBuilder(TaskForm22500.this)
                    .setSubtitle("SJPD Radio logoff \n Performed?")
                    .setPositiveListener("Yes", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {

                            /*Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                            intent.setClass(TaskForm22500.this, HomeActivity.class);
                            startActivity(intent);*/
                            darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(), TaskForm22500.this, TPApplication.getInstance().getDarParkingdutyreportId());
                            darAssignmentLocationJob.callAssignLocationAPI("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getAssignReportId(), TPApplication.getInstance().getAssLocRecId(), TaskForm22500.this);
                            darSJPDlogoutJob._sjpdlogout(TaskForm22500.this);

                            Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", "");
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(locId));
                            serviceIntent.putExtra("WITCH", "Three");
                            serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                            EDarServiceJobIntentTask.enqueueWork(TaskForm22500.this, serviceIntent);        /* TPApplication.getInstance().setDarStartTimeTask(null);
                            Intent serviceIntent1 = new Intent(TaskForm22500.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(asgId));
                            serviceIntent.putExtra("WITCH", "Two");
                            EDarServiceJobIntentTask.enqueueWork(TaskForm22500.this, serviceIntent1);*/
                            // finish();
                            dialog.dismiss();
                            String Id_colunn = preference.getString("coulmunId");
                            String Start_mileage = TPApp.getMileage();

                            MilageDialog milageDialog = new MilageDialog();

                            try {
                                if (isNetworkConnected()) {
                                    milageDialog.EndMilageDialogShift(TaskForm22500.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "Yes", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                                } else {
                                    milageDialog.EndMilageDialogShift(TaskForm22500.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "No", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
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
        _btnLogOut.setOnClickListener(v -> {
            try {


                new iOSDialogBuilder(TaskForm22500.this)
                        .setSubtitle("SJPD Radio logoff \n Performed?")
                        .setPositiveListener("Yes", new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                               /* Intent intent = new Intent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                                intent.setClass(TaskForm22500.this, DarEquipmentNew.class);
                                startActivity(intent);
                                */
                                darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(), TaskForm22500.this, TPApplication.getInstance().getDarParkingdutyreportId());
                                darAssignmentLocationJob.callAssignLocationAPI("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getAssignReportId(), TPApplication.getInstance().getAssLocRecId(), TaskForm22500.this);
                                darSJPDlogoutJob._sjpdlogout(TaskForm22500.this);
                                Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", "");
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", String.valueOf(locId));
                                serviceIntent.putExtra("WITCH", "Three");
                                serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                                EDarServiceJobIntentTask.enqueueWork(TaskForm22500.this, serviceIntent);     /*TPApplication.getInstance().setDarStartTimeTask(null);
                                Intent serviceIntent1 = new Intent(TaskForm22500.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", String.valueOf(asgId));
                                serviceIntent.putExtra("WITCH", "Two");
                                EDarServiceJobIntentTask.enqueueWork(TaskForm22500.this, serviceIntent1);*/

                                dialog.dismiss();

                                // finish();
                                //finish();
                                String Id_colunn = preference.getString("coulmunId");
                                String Start_mileage = TPApp.getMileage();

                                MilageDialog milageDialog = new MilageDialog();

                                try {
                                    if (isNetworkConnected()) {
                                        milageDialog.EndMilageDialogLogout(TaskForm22500.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "Yes", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                                    } else {
                                        milageDialog.EndMilageDialogLogout(TaskForm22500.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "No", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
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

        mCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkId = 1;
            } else {
                checkId = 0;
            }
        });
        _chalkVehicle.setOnClickListener(v -> {
            ChalkVehicle chalk = TPApp.createNewChalk();
            TPApp.setActiveChalk(chalk);
            Intent i = new Intent();
            i.setClass(TaskForm22500.this, ChalkVehicleActivity.class);
            startActivity(i);
        });
        _writeTicket.setOnClickListener(v -> {
            Ticket ticket = TPApp.createNewTicket();
            TPApp.setActiveTicket(ticket);
            Intent i = new Intent();
            i.setClass(TaskForm22500.this, WriteTicketActivity.class);
            startActivity(i);
        });
        _btnTow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(TaskForm22500.this, TowSupport.class);
                startActivity(i);
            }
        });
        _spinnerText.setOnClickListener(v -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(TaskForm22500.this);
            //builderSingle.setIcon(R.drawable.icon);
            builderSingle.setTitle("Select");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(TaskForm22500.this, android.R.layout.select_dialog_singlechoice);
            for (int i = 0; i < element.size(); i++) {
                final Dar22500DisposionDropDownElement dar22500DisposionDropDownElement = element.get(i);
                String s = dar22500DisposionDropDownElement.getMenuName();
                arrayAdapter.add(s);
            }
            builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                _spinnerText.setText(arrayAdapter.getItem(which).toString());
                element.forEach((n) -> {
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
        progressDialog = ProgressDialog.show(TaskForm22500.this, "", "Inserting...");
        Task22500Jsonrpc form = new Task22500Jsonrpc();
        ParamTaskForm22500 form22500 = new ParamTaskForm22500();
        TaskForm22500Model model = new TaskForm22500Model();
        List<TaskForm22500Model> aList = new ArrayList<>();

        model.setUserId(Integer.toString(TPApp.userId));
        model.setDispositionDdElemId(id);
        model.setPdEvent(_pdEvent.getText().toString());
        model.setNotes(_note.getText().toString());
        model.setLocation(_location.getText().toString());
        model.setUserId(String.valueOf(TPApp.userId));
        model.setDeviceId(String.valueOf(TPApp.deviceId));
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
        model.setCustomerContact(String.valueOf(checkId));
        model.setActionId("1");
        model.setTaskDate(DateUtil.getCurrentDateTime1());
        if (!preference.getString("coulmunId").isEmpty() || !preference.getString("coulmunId").equals(""))
            model.setMileageId(preference.getString("coulmunId"));
        else
            model.setMileageId("0");
        model.setAppId((int) TaskForm22500Model.getNextPrimaryId());
        aList.add(model);

        form22500.setDetails(aList);
        form22500.setCustId(TPApp.custId);
        form.setParams(form22500);
        form.setJsonrpc("2.0");
        form.setId("82F85DB43CBF6");
        form.setMethod("dar22500Insert");
       // TaskForm22500Model.insertTaskForm22500(model);
        System.out.println("RequestObj**" + new Gson().toJson(form));
       log.info(new Gson().toJson(form));
        if (isNetworkConnected()) {
            try {

                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertFrom22500(form).enqueue(new Callback<DarTicketResponse>() {
                    @Override
                    public void onResponse(Call<DarTicketResponse> call, Response<DarTicketResponse> response) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (response.code() == 200 && response.isSuccessful()) {

                            if (response.body().getResult() != null)
                            {
                                if (response.body().getResult().getResult()== true)
                                {
                                    Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
                                    _pdEvent.setText("");
                                    // _spinnerText.setHint("Select Activity");
                                    _spinnerText.setText("Select");
                                    _location.setText("");
                                    _note.setText("");
                                    mCheckbox.setChecked(false);
                                }else {
                                    Failed();
                                    log.error("response incorrect");
                                }
                            }else {
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
            try {
                TaskForm22500Model.insertTaskForm22500(model);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
            _pdEvent.setText("");
            // _spinnerText.setHint("Select Activity");
            _spinnerText.setText("Select");
            _location.setText("");
            _note.setText("");
            mCheckbox.setChecked(false);
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

    void getDatafromDB() {
        element = Dar22500DisposionDropDownElement.getElement(TPApp.custId);
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
        new GetLocation1(TaskForm22500.this, this).track();
    }

    public void SpinnerFailedDialog() {

        new iOSDialogBuilder(TaskForm22500.this)
                .setSubtitle("Please Select Disposition")
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
        /*Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", "");
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID", String.valueOf(locId));
        serviceIntent.putExtra("WITCH", "Three");
        serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
        EDarServiceJobIntentTask.enqueueWork(TaskForm22500.this, serviceIntent);
        TPApplication.getInstance().setDarStartTimeTask(null);
        finish();
        super.onBackPressed();
*/
    }


    public void callTaskReport(String startTime, String endTime, String id, String TaskRecordId, String taskid) throws IOException {
        progressDialog = ProgressDialog.show(TaskForm22500.this, "", "Please Wait...");
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

        new iOSDialogBuilder(TaskForm22500.this)
                .setSubtitle("Please try again")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();

                    }
                })
                .build().show();

    }

      /*  final DarTicketResult result = response.body().getResult();
                            final List<Integer> appId = result.getAppId();
                            if (appId != null && appId.size() > 0) {
                                for (int i = 0; i < appId.size(); i++) {
                                    try {
                                        TaskForm22500Model.removeById(appId.get(i).intValue());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }*/
}