package com.ticketpro.parking.dar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.ticketpro.model.TicketResponse;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.HomeActivity;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.DarAssignmentLocationJob;
import com.ticketpro.parking.dar.model.DarBreakAndLunchDropDown;
import com.ticketpro.parking.dar.model.DarDutyReportJob;
import com.ticketpro.parking.dar.model.DarFieldContactDropdown;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.DarTicketResult;
import com.ticketpro.parking.dar.model.LunchBreakModel;
import com.ticketpro.parking.dar.model.TaskForm22500Model;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.params.ParamLunchBreak;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.request.LunchBreakJson_rpc;
import com.ticketpro.parking.dar.model.request.TaskReport_rpc;
import com.ticketpro.parking.service.EDarServiceJobIntentTask;
import com.ticketpro.util.DateUtil;
import com.ticketpro.util.Preference;
import com.ticketpro.util.TPUtility;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreakLunch extends BaseActivityImpl implements Validator.ValidationListener, MyTracker.ADLocationListener {
    @NotEmpty
    private EditText _mLocation;
    private Button _mGps;
    private Validator validator;
    private Button _mSave;
    private Button _mEndShift;
    private Button _mLogout;
    private ProgressDialog progressDialog;
    private TextView _spinnerView;
    private int asgId = 0;
    private int locId = 0;
    private int taskId = 0;
    private List<DarBreakAndLunchDropDown> dpList;
    private Integer id;
    SharedPreferences sharedPreferences;
    private Preference preference;
    private String inTime = null;
    Button BtnstartTime,BtnendTime,BtnstartTimehide,BtnendTimehide;
    @NotEmpty
    EditText StartTimeET;
    @NotEmpty
    EditText EndTimeET;
    String previoustartT=" ",previousendT=" ";
    DarAssignmentLocationJob darAssignmentLocationJob= new DarAssignmentLocationJob();
    DarDutyReportJob darDutyReportJob= new DarDutyReportJob();
    DarSJPDlogoutJob darSJPDlogoutJob= new DarSJPDlogoutJob();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_lunch);
        setLogger(BreakLunch.class.getName());
        preference = Preference.getInstance(BreakLunch.this);
        TPApplication.getInstance().setDarStartTimeTask(DateUtil.getCurrentDateTime1());
        validator = new Validator(this);
        validator.setValidationListener(this);
        Intent it = getIntent();
        asgId = it.getIntExtra("ASG_ID", 0);
        locId = it.getIntExtra("LOCATION_ID", 0);
        taskId = it.getIntExtra("TASK_ID", 0);
        inTime = preference.getString("IN_DATE");
        sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        Button _btnBack = findViewById(R.id.bc_btnBack);
        _btnBack.setOnClickListener(v -> {
            try {
                callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*Intent serviceIntent = new Intent(BreakLunch.this, EDarServiceJobIntentTask.class);
            serviceIntent.putExtra("START","");
            serviceIntent.putExtra("END",DateUtil.getCurrentDateTime1());
            serviceIntent.putExtra("ID",String.valueOf(locId));
            serviceIntent.putExtra("WITCH","Three");
            serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
            EDarServiceJobIntentTask.enqueueWork(BreakLunch.this, serviceIntent);
            TPApplication.getInstance().setDarStartTimeTask(null);

            finish();*/
        });

        _spinnerView = findViewById(R.id.bc_activity_spinner);
        _mLocation = findViewById(R.id.bc_location);
        _mGps = findViewById(R.id.bc_btnLocation);
        _mEndShift = findViewById(R.id.bc_btnEndShift);
        _mLogout = findViewById(R.id.bc_btn_logout);
        _mSave = findViewById(R.id.bc_btn_save);
        BtnstartTime=findViewById(R.id.bc_btnStartTime);
        BtnendTime=findViewById(R.id.bc_btnEndTime);
        StartTimeET=findViewById(R.id.bc_StartTime);
        EndTimeET=findViewById(R.id.bc_EndTime);
        BtnstartTimehide=findViewById(R.id.bc_btnStartTimehide);
        BtnendTimehide=findViewById(R.id.bc_btnEndTimehide);
        BtnstartTime.setVisibility(View.VISIBLE);
        BtnendTime.setVisibility(View.VISIBLE);
        BtnstartTimehide.setVisibility(View.GONE);
        BtnendTimehide.setVisibility(View.GONE);


        BtnstartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTimeET.setText(DateUtil.getCurrentDateTime1());
                BtnstartTime.setVisibility(View.GONE);
                BtnstartTimehide.setVisibility(View.VISIBLE);

            }
        });
        StartTimeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previoustartT=StartTimeET.getText().toString();
                if (!StartTimeET.getText().toString().isEmpty())
                {
                    StartEtdateDialog();
                }

            }
        });

        EndTimeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousendT=EndTimeET.getText().toString();
                if (!EndTimeET.getText().toString().isEmpty())
                {
                    EndETdatedialo();
                }
            }
        });
        BtnendTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*try {
                    if (DateUtil.isTimeAfter(StartTimeET.getText().toString(),DateUtil.getCurrentDateTime1())){
                        EndTimeET.setText(DateUtil.getCurrentDateTime1());
                    }else {
                        Toast.makeText(BreakLunch.this, "End time must be greater then dispatch time", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                if (StartTimeET.getText().toString().isEmpty()){

                }
                else
                {
                    //dateDialog();
                    boolean result=checkTimings(StartTimeET.getText().toString(),DateUtil.getCurrentDateTime1());
                    if (result==true)
                    {
                        EndTimeET.setText(DateUtil.getCurrentDateTime1());
                        BtnendTime.setVisibility(View.GONE);
                        BtnendTimehide.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        BtnendTime.setVisibility(View.GONE);
                        BtnendTimehide.setVisibility(View.VISIBLE);
                       // EndTimeET.setError("Invalid input type");
                        EndBtndatedialog();
                    }

                }
               // EndTimeET.setText(DateUtil.getCurrentDateTime1());
                /*BtnendTime.setVisibility(View.GONE);
                BtnendTimehide.setVisibility(View.VISIBLE);*/
            }
        });

        _mEndShift.setOnClickListener(v -> {



           /* Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntent.class);
            if (isNetworkConnected()) {
                EDarServiceJobIntent.enqueueWork(TaskForm22500.this, serviceIntent);
            }*/

            new iOSDialogBuilder(BreakLunch.this)
                    .setSubtitle("SJPD Radio logoff \n Performed?")
                    .setPositiveListener("Yes", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {
                            log.info("Clicked on end shift button");
/*
                            Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                            intent.setClass(BreakLunch.this, HomeActivity.class);
                            startActivity(intent);*/
                            darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(),BreakLunch.this,TPApplication.getInstance().getDarParkingdutyreportId());
                            darAssignmentLocationJob.callAssignLocationAPI("",DateUtil.getCurrentDateTime1(),String.valueOf(asgId),TPApplication.getInstance().getAssignReportId(),TPApplication.getInstance().getAssLocRecId(),BreakLunch.this);
                            darSJPDlogoutJob._sjpdlogout(BreakLunch.this);

                            Intent serviceIntent = new Intent(BreakLunch.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START","");
                            serviceIntent.putExtra("END",DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID",String.valueOf(locId));
                            serviceIntent.putExtra("WITCH","Three");
                            serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
                            EDarServiceJobIntentTask.enqueueWork(BreakLunch.this, serviceIntent);

                            TPApplication.getInstance().setDarStartTimeTask(null);
                          /*  Intent serviceIntent1 = new Intent(BreakLunch.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(asgId));
                            serviceIntent.putExtra("WITCH", "Two");
                            EDarServiceJobIntentTask.enqueueWork(BreakLunch.this, serviceIntent1);*/

                            dialog.dismiss();
                            String  Id_colunn=preference.getString("coulmunId");
                            String  Start_mileage= TPApp.getMileage();

                            MilageDialog milageDialog= new MilageDialog();

                            try {
                                if (isNetworkConnected())
                                {
                                    milageDialog.EndMilageDialogShift(BreakLunch.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                }
                                else
                                {
                                    milageDialog.EndMilageDialogShift(BreakLunch.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
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

                new iOSDialogBuilder(BreakLunch.this)
                        .setSubtitle("SJPD Radio logoff \n Performed?")
                        .setPositiveListener("Yes", new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                                log.info("Clicked on logout button");
                               /* Intent intent = new Intent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                                intent.setClass(BreakLunch.this, DarEquipmentNew.class);
                                startActivity(intent);*/
                                darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(),BreakLunch.this,TPApplication.getInstance().getDarParkingdutyreportId());
                                darAssignmentLocationJob.callAssignLocationAPI("",DateUtil.getCurrentDateTime1(),String.valueOf(asgId),TPApplication.getInstance().getAssignReportId(),TPApplication.getInstance().getAssLocRecId(),BreakLunch.this);
                                darSJPDlogoutJob._sjpdlogout(BreakLunch.this);

                                Intent serviceIntent = new Intent(BreakLunch.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START","");
                                serviceIntent.putExtra("END",DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID",String.valueOf(locId));
                                serviceIntent.putExtra("WITCH","Three");
                                serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
                                EDarServiceJobIntentTask.enqueueWork(BreakLunch.this, serviceIntent);

                                TPApplication.getInstance().setDarStartTimeTask(null);
                              /*  Intent serviceIntent1 = new Intent(BreakLunch.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", String.valueOf(asgId));
                                serviceIntent.putExtra("WITCH", "Two");
                                EDarServiceJobIntentTask.enqueueWork(BreakLunch.this, serviceIntent1);*/


                                dialog.dismiss();
                                //finish();

                                String  Id_colunn=preference.getString("coulmunId");
                                String  Start_mileage= TPApp.getMileage();

                                MilageDialog milageDialog= new MilageDialog();

                                try {
                                    if (isNetworkConnected())
                                    {
                                        milageDialog.EndMilageDialogLogout(BreakLunch.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                    }
                                    else
                                    {
                                        milageDialog.EndMilageDialogLogout(BreakLunch.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
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

        _mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findLoc();
            }
        });


        _mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_spinnerView.getText().toString().isEmpty())
                {
                    //_spinnerView.setError(" ");
                    SpinnerFailedDialog();
                }
                else
                {
                    validator.validate();
                }


            }
        });

        dpList = DarBreakAndLunchDropDown._getDarFieldContactDropdownList(TPApp.custId);
        _spinnerView.setOnClickListener(v -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(BreakLunch.this);
            //builderSingle.setIcon(R.drawable.icon);
            builderSingle.setTitle("Select Activity");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(BreakLunch.this, android.R.layout.select_dialog_singlechoice);
            for (int i = 0; i < dpList.size(); i++) {
                final DarBreakAndLunchDropDown dropdown = dpList.get(i);
                String s = dropdown.getDdItem();
                arrayAdapter.add(s);
            }
            builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                _spinnerView.setText(arrayAdapter.getItem(which).toString());
                dpList.forEach((n) -> {
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
        try {
            __save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void __save() throws IOException {
        progressDialog = ProgressDialog.show(BreakLunch.this, "", "Inserting...");

        LunchBreakJson_rpc rpc = new LunchBreakJson_rpc();
        ParamLunchBreak paramSignCheck = new ParamLunchBreak();
        List<LunchBreakModel> aList = new ArrayList<>();
        LunchBreakModel model = new LunchBreakModel();
        model.setLocation(_mLocation.getText().toString());
        model.setDdItem(String.valueOf(id));
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
        if (TPApplication.getInstance().getDarTaskReoprtId()!=null)
        {
            model.setDarTaskReportId(TPApplication.getInstance().getDarTaskReoprtId());
        }
        else
        {
            model.setDarTaskReportId("");
        }

        if (!preference.getString("coulmunId").isEmpty() || !preference.getString("coulmunId").equals(""))
            model.setMileageId(preference.getString("coulmunId"));
        else
            model.setMileageId("0");

        model.setStarttime(StartTimeET.getText().toString());
        model.setEndtime(EndTimeET.getText().toString());
        model.setUserId(String.valueOf(TPApp.userId));
        model.setDeviceid(String.valueOf(TPApp.deviceId));
        model.setAppId((int) LunchBreakModel.getNextPrimaryId());
        aList.add(model);
        paramSignCheck.setDetails(aList);
        paramSignCheck.setCustId(TPApp.custId);
        rpc.setParams(paramSignCheck);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("dar_breakandlunch");

        System.out.println("RequestObj**"+new Gson().toJson(rpc));
        log.info(new Gson().toJson(rpc));
        if (isNetworkConnected()){
            try {
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertBreakLunch(rpc).enqueue(new Callback<DarTicketResponse>() {
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

                                    _mLocation.setText("");
                                    StartTimeET.setText("");
                                    EndTimeET.setText("");
                                    _spinnerView.setText("");
                                    BtnstartTime.setVisibility(View.VISIBLE);
                                    BtnendTime.setVisibility(View.VISIBLE);
                                    BtnstartTimehide.setVisibility(View.GONE);
                                    BtnendTimehide.setVisibility(View.GONE);
                                }
                                else {
                                    Failed();
                                    log.error("response incorrect");
                                }
                            }
                            else {
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
        }else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            LunchBreakModel.insertLunchBreakModel(model);
            _mLocation.setText("");
            StartTimeET.setText("");
            EndTimeET.setText("");
            _spinnerView.setText("");
            BtnstartTime.setVisibility(View.VISIBLE);
            BtnendTime.setVisibility(View.VISIBLE);
            BtnstartTimehide.setVisibility(View.GONE);
            BtnendTimehide.setVisibility(View.GONE);
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
        new GetLocation1(BreakLunch.this, this).track();
    }

    public  void StartEtdateDialog()
    {
        final Calendar calenderInstance = Calendar.getInstance();
        int hr = calenderInstance.get(Calendar.HOUR_OF_DAY);
        int min = calenderInstance.get(Calendar.MINUTE);
        TimePickerDialog.OnTimeSetListener onTimeListner = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    String min=String.valueOf(minute);
                    calenderInstance.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calenderInstance.set(Calendar.MINUTE, minute);
                   int day_months=calenderInstance.get(Calendar.DAY_OF_MONTH);
                   int month=calenderInstance.get(Calendar.MONTH);
                   int year=calenderInstance.get(Calendar.YEAR);
                    if (String.valueOf(minute).length()==1)
                    {
                        min="0"+minute;
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                    Date date= new Date(day_months,month,day_months);
                    String dateformate=sdf.format(date);
                   StartTimeET.setText(year+"-"+dateformate+" "+String.valueOf(hourOfDay)+":"+String.valueOf(min)+":"+"00");
                    try {

                        if (!EndTimeET.getText().toString().isEmpty())
                        {
                            boolean timestatus=checktimings(StartTimeET.getText().toString(),EndTimeET.getText().toString());
                            if (timestatus==true)
                            {
                                StartTimeET.setText(year+"-"+dateformate+" "+String.valueOf(hourOfDay)+":"+String.valueOf(min)+":"+"00");
                            }
                            else
                            {   StartTimeET.setText(previoustartT);
                                /*StartTimeET.setError("");*/
                                Dialog();
                            }
                        }

                    }catch (Exception e)
                    {

                    }

                }

                Log.d("timee",String.valueOf(hourOfDay)+String.valueOf(minute));
            }

        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(BreakLunch.this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                onTimeListner, hr, min, false);
        timePickerDialog.setTitle("Start Time");
        Objects.requireNonNull(timePickerDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    private boolean checktimings(String time, String endtime) {

        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if(date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }
    private boolean checkTimings(String time, String endtime) {

        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if(date2.after(date1)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }

    public  void Dialog(){

        new iOSDialogBuilder(BreakLunch.this)
                .setSubtitle("Start time should be earlier than the end time.")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })

                .build().show();
        //new ProxyImpl().updateDarTable();
    }
    public  void SpinnerFailedDialog(){

        new iOSDialogBuilder(BreakLunch.this)
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
    public  void EndBtndatedialog()
    {
        final Calendar calenderInstance = Calendar.getInstance();
        int hr = calenderInstance.get(Calendar.HOUR_OF_DAY);
        int min = calenderInstance.get(Calendar.MINUTE);
        TimePickerDialog.OnTimeSetListener onTimeListner = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    String min=String.valueOf(minute);
                    calenderInstance.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calenderInstance.set(Calendar.MINUTE, minute);
                    int day_months=calenderInstance.get(Calendar.DAY_OF_MONTH);
                    int month=calenderInstance.get(Calendar.MONTH);
                    if (String.valueOf(minute).length()==1)
                    {
                        min="0"+minute;
                    }
                    int year=calenderInstance.get(Calendar.YEAR);

                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                    Date date= new Date(day_months,month,day_months);
                    String dateformate=sdf.format(date);
                   EndTimeET.setText(year+"-"+dateformate+" "+String.valueOf(hourOfDay)+":"+String.valueOf(min)+":"+"00");
                    try {
                       // previousendT=EndTimeET.getText().toString();

                        if (!StartTimeET.getText().toString().isEmpty() || !StartTimeET.getText().toString().equals(" "))
                        {
                            boolean timestatus=checkTimings(StartTimeET.getText().toString(),EndTimeET.getText().toString());
                            if (timestatus==true)
                            {
                                EndTimeET.setText(year+"-"+dateformate+" "+String.valueOf(hourOfDay)+":"+String.valueOf(min)+":"+"00");
                            }
                            else
                            {   EndTimeET.setText(previousendT);
                                /*StartTimeET.setError("");*/
                                Dialog();
                            }
                        }
                    }catch (Exception e)
                    {
                    }
                }
                Log.d("timee",String.valueOf(hourOfDay)+String.valueOf(minute));
            }

        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(BreakLunch.this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                onTimeListner, hr, min, false);
        timePickerDialog.setTitle("End Time");
        Objects.requireNonNull(timePickerDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }
    public  void EndETdatedialo()
    {
        final Calendar calenderInstance = Calendar.getInstance();
        int hr = calenderInstance.get(Calendar.HOUR_OF_DAY);
        int min = calenderInstance.get(Calendar.MINUTE);
        TimePickerDialog.OnTimeSetListener onTimeListner = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    String min=String.valueOf(minute);
                    calenderInstance.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calenderInstance.set(Calendar.MINUTE, minute);
                    int day_months=calenderInstance.get(Calendar.DAY_OF_MONTH);
                    int month=calenderInstance.get(Calendar.MONTH);
                    if (String.valueOf(minute).length()==1)
                    {
                        min="0"+minute;
                    }
                    int year=calenderInstance.get(Calendar.YEAR);

                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                    Date date= new Date(day_months,month,day_months);
                    String dateformate=sdf.format(date);
                    EndTimeET.setText(year+"-"+dateformate+" "+String.valueOf(hourOfDay)+":"+String.valueOf(min)+":"+"00");
                    try {

                        if (!StartTimeET.getText().toString().isEmpty()||  !StartTimeET.getText().toString().equals(" "))
                        {
                            boolean timestatus=checkTimings(StartTimeET.getText().toString(),EndTimeET.getText().toString());
                            if (timestatus==true)
                            {
                                EndTimeET.setText(year+"-"+dateformate+" "+String.valueOf(hourOfDay)+":"+String.valueOf(min)+":"+"00");
                            }
                            else
                            {   EndTimeET.setText(previousendT);
                                /*StartTimeET.setError("");*/
                                Dialog();
                            }
                        }
                    }catch (Exception e)
                    {
                    }
                }
                Log.d("timee",String.valueOf(hourOfDay)+String.valueOf(minute));
            }

        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(BreakLunch.this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                onTimeListner, hr, min, false);
        timePickerDialog.setTitle("End Time");
        Objects.requireNonNull(timePickerDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    @Override
    public void onBackPressed() {

        try {
            callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    
       /* Intent serviceIntent = new Intent(BreakLunch.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START","");
        serviceIntent.putExtra("END",DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID",String.valueOf(locId));
        serviceIntent.putExtra("WITCH","Three");
        serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
        EDarServiceJobIntentTask.enqueueWork(BreakLunch.this, serviceIntent);
        TPApplication.getInstance().setDarStartTimeTask(null);
        finish();
        super.onBackPressed();*/
    }


    public void callTaskReport(String startTime, String endTime, String id, String TaskRecordId, String taskid) throws IOException {
        progressDialog = ProgressDialog.show(BreakLunch.this, "", "Please Wait...");
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

        new iOSDialogBuilder(BreakLunch.this)
                .setSubtitle("Please try again")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();

                    }
                })
                .build().show();

    }

    /*
                            final DarTicketResult result = response.body().getResult();
                            final List<Integer> appId = result.getAppId();
                            if (appId != null && appId.size()>0) {
                                for (int i = 0; i < appId.size(); i++) {
                                    try {
                                        LunchBreakModel.removeById(appId.get(i).intValue());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }*/
}