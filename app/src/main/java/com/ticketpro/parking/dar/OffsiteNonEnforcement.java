package com.ticketpro.parking.dar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGenerator;
import com.ticketpro.model.Duty;
import com.ticketpro.model.User;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.AssignmentLocationReportModel;
import com.ticketpro.parking.dar.model.AssignmentReportModel;
import com.ticketpro.parking.dar.model.DarAssignmentLocation;
import com.ticketpro.parking.dar.model.DarAssignmentLocationJob;
import com.ticketpro.parking.dar.model.DarAssignmentLocationReportResponse;
import com.ticketpro.parking.dar.model.DarAssignmentreportResponse;
import com.ticketpro.parking.dar.model.DarDutyReportJob;
import com.ticketpro.parking.dar.model.DarOffSiteNonInforcementForm;
import com.ticketpro.parking.dar.model.DarSeniorDutiesElements;
import com.ticketpro.parking.dar.model.DarTaskInOut;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.DarTicketResult;
import com.ticketpro.parking.dar.model.Params;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.TrafficDetails;
import com.ticketpro.parking.dar.model.params.ParamAssignmentLocationReport;
import com.ticketpro.parking.dar.model.params.ParamAssignmentReport;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.request.AssignmentLocationReport_rpc;
import com.ticketpro.parking.dar.model.request.AssignmentReport_rpc;
import com.ticketpro.parking.dar.model.request.TaskReport_rpc;
import com.ticketpro.parking.service.EDarServiceJobIntentTask;
import com.ticketpro.util.DateUtil;
import com.ticketpro.util.Preference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffsiteNonEnforcement extends BaseActivityImpl implements Validator.ValidationListener {
    @NotEmpty
    private EditText _mCommunityDate, _mCommunityLocation, _userName,
            _badgeNum, _dateTime, _eventName, _startShift,
            _releaseShit, _endShift, _mRideDate, _mFlyerDateTime,
            _mFlyerComment;
    private TextView _spinnerText, _title, _mRideSpinner, _mRideSpinner2;
    private Button _btnBack, _btnSave, _btnEndShift, _btnLogOut,
            _btnStartShift, _btnReleasePost, _btnEndShift1,
            _mCommunityComment;
    private List<Duty> locationDetails;
    //private ListView _mListView;
    private String[] activity;
    private DatePickerDialog picker;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int currentHour;
    private int currentMinute;
    private String amPm;
    private User userInfo;
    private ProgressDialog progressDialog;
    private int id_assg_loc = 0;
    private String mName;
    private LinearLayout _mTraining, _mCommunity, _mRide, _mTraffic, _mFlyer, _mSeniorDuty;
    int menupos = 100;

    private ArrayList<DarSeniorDutiesElements> _mList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    SharedPreferences sharedPreferences;
    private Preference preference;
    private String inTime = null;
    private Validator validator;
    DarAssignmentLocationJob darAssignmentLocationJob = new DarAssignmentLocationJob();
    DarDutyReportJob darDutyReportJob = new DarDutyReportJob();
    DarTaskInOut darTaskInOut = new DarTaskInOut();
    DarSJPDlogoutJob darSJPDlogoutJob=new DarSJPDlogoutJob();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offsite_non_enforcement);
        setLogger(OffsiteNonEnforcement.class.getName());
        TPApplication.getInstance().setDarStartTimeTask(DateUtil.getCurrentDateTime1());
        validator = new Validator(this);
        validator.setValidationListener(this);
        Intent i = getIntent();
        id_assg_loc = i.getIntExtra("id_assg_loc", 0);
        loadlocation(id_assg_loc);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        preference = Preference.getInstance(OffsiteNonEnforcement.this);
        inTime = preference.getString("IN_DATE");

        _initialLoadData();
        if (preference.getString("assignmentApiHits").equals("Y"))
        {
            try {
                callAssignmentReportLogout("",DateUtil.getCurrentDateTime1(),String.valueOf(id_assg_loc), TPApplication.getInstance().getAssignReportId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            progressDialog= new ProgressDialog(OffsiteNonEnforcement.this);
            progressDialog.setMessage("Please wait.....");
            try {
                callAssignmentReport(DateUtil.getCurrentDateTime1(), "", String.valueOf(id_assg_loc), "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            progressDialog= new ProgressDialog(OffsiteNonEnforcement.this);
            progressDialog.setMessage("Please wait.....");
            try {
                callAssignmentReport(DateUtil.getCurrentDateTime1(), "", String.valueOf(id_assg_loc), "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void _initialLoadData() {
        _userName = findViewById(R.id.nonf_user_name);
        _badgeNum = findViewById(R.id.nonf_badge);
        _dateTime = findViewById(R.id.nonf_datetime);
        _eventName = findViewById(R.id.nonf_event);
        _startShift = findViewById(R.id.nonf_et_start_shift);
        _releaseShit = findViewById(R.id.nonf_et_r_post);
        _endShift = findViewById(R.id.nonf_endshift);
        //Button Control
        _btnBack = findViewById(R.id.nonf_btnBack);
        _btnEndShift = findViewById(R.id.nonf_btnEndShift);
        _btnSave = findViewById(R.id.nonf_btn_save);
        _btnLogOut = findViewById(R.id.nonf_btn_logout);
        _btnStartShift = findViewById(R.id.nonf_btn_start_shift);
        _btnReleasePost = findViewById(R.id.nonf_btn_r_post);
        _btnEndShift1 = findViewById(R.id.nonf_btn_end_shift1);
        //Layout control
        _mCommunity = findViewById(R.id.layout_community_meeting);
        _mRide = findViewById(R.id.layout_ride_along);
        _mTraffic = findViewById(R.id.linearEquipment);
        _mFlyer = findViewById(R.id.layout_flyer);
        //Layout Community
        _mCommunityDate = findViewById(R.id.nonf_datetime2);
        _mCommunityDate.setText(DateUtil.getCurrentDateTime());
        _mCommunityLocation = findViewById(R.id.nonf_location);
        _mCommunityComment = findViewById(R.id.nonf_btn_comment);
        _mSeniorDuty = findViewById(R.id.layout_senior_duties);

        //Layout Rode-along
        _mRideDate = findViewById(R.id.nonf_datetime1);
        _mRideDate.setText(DateUtil.getCurrentDateTime());
        _mRideSpinner = findViewById(R.id.nonf_activity_spinner1);
        _mRideSpinner2 = findViewById(R.id.nonf_activity_spinner2);

        //Layout flyer
        _mFlyerDateTime = findViewById(R.id.nonf_datetime12);
        _mFlyerDateTime.setText(DateUtil.getCurrentDateTime());
        _mFlyerComment = findViewById(R.id.nonf_flyer_comment);

        mRecyclerView = (RecyclerView) findViewById(R.id.nonf_list);
        LinearLayoutManager manager = new LinearLayoutManager(OffsiteNonEnforcement.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);

        _spinnerText = findViewById(R.id.nonf_activity_spinner);
        _title = findViewById(R.id.nonf_title);

        __loadDataFromDb();
        _startShift.setText(DateUtil.getCurrentDateTime());
        activity = getResources().getStringArray(R.array.offsite_dropdown);


        // darAssignmentLocationJob.callAssignLocationAPI(DateUtil.getCurrentDateTime1(),"",String.valueOf(5),TPApplication.getInstance().getAssignReportId(),"",OffsiteNonEnforcement.this);
        _btnEndShift.setOnClickListener(v -> {
           /* Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntent.class);
            if (isNetworkConnected()) {
                EDarServiceJobIntent.enqueueWork(TaskForm22500.this, serviceIntent);
            }*/


            new iOSDialogBuilder(OffsiteNonEnforcement.this)
                    .setSubtitle("SJPD Radio logoff \n Performed?")
                    .setPositiveListener("Yes", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {

                           /* Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                            intent.setClass(OffsiteNonEnforcement.this, HomeActivity.class);
                            startActivity(intent);*/
                            darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(), OffsiteNonEnforcement.this,TPApplication.getInstance().getDarParkingdutyreportId());
                            darAssignmentLocationJob.callAssignLocationAPI("", DateUtil.getCurrentDateTime1(), String.valueOf(id_assg_loc), TPApplication.getInstance().getAssignReportId(), TPApplication.getInstance().getAssLocRecId(), OffsiteNonEnforcement.this);
                            darSJPDlogoutJob._sjpdlogout(OffsiteNonEnforcement.this);

                            Intent serviceIntent = new Intent(OffsiteNonEnforcement.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", "");
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(id_assg_loc));
                            serviceIntent.putExtra("WITCH", "Three");
                            serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                            EDarServiceJobIntentTask.enqueueWork(OffsiteNonEnforcement.this, serviceIntent);

                            TPApplication.getInstance().setDarStartTimeTask(null);
                          /*  Intent serviceIntent1 = new Intent(OffsiteNonEnforcement.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(id_assg_loc));
                            serviceIntent.putExtra("WITCH", "Two");
                            EDarServiceJobIntentTask.enqueueWork(OffsiteNonEnforcement.this, serviceIntent1);*/

                            //  dialog.dismiss();

                            dialog.dismiss();
                            String Id_colunn = preference.getString("coulmunId");
                            String Start_mileage = TPApp.getMileage();


                            MilageDialog milageDialog = new MilageDialog();

                            try {
                                if (isNetworkConnected()) {
                                    milageDialog.EndMilageDialogShift(OffsiteNonEnforcement.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "Yes", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                                } else {
                                    milageDialog.EndMilageDialogShift(OffsiteNonEnforcement.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "No", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
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


                new iOSDialogBuilder(OffsiteNonEnforcement.this)
                        .setSubtitle("SJPD Radio logoff \n Performed?")
                        .setPositiveListener("Yes", new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                               /* Intent intent = new Intent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                                intent.setClass(OffsiteNonEnforcement.this, DarEquipmentNew.class);
                                startActivity(intent);*/
                                darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(), OffsiteNonEnforcement.this,TPApplication.getInstance().getDarParkingdutyreportId());
                                darAssignmentLocationJob.callAssignLocationAPI("", DateUtil.getCurrentDateTime1(), String.valueOf(id_assg_loc), TPApplication.getInstance().getAssignReportId(), TPApplication.getInstance().getAssLocRecId(), OffsiteNonEnforcement.this);
                                darSJPDlogoutJob._sjpdlogout(OffsiteNonEnforcement.this);

                                Intent serviceIntent = new Intent(OffsiteNonEnforcement.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", "");
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", String.valueOf(id_assg_loc));
                                serviceIntent.putExtra("WITCH", "Three");
                                serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                                EDarServiceJobIntentTask.enqueueWork(OffsiteNonEnforcement.this, serviceIntent);

                                TPApplication.getInstance().setDarStartTimeTask(null);
                               /* Intent serviceIntent1 = new Intent(OffsiteNonEnforcement.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", String.valueOf(id_assg_loc));
                                serviceIntent.putExtra("WITCH", "Two");
                                EDarServiceJobIntentTask.enqueueWork(OffsiteNonEnforcement.this, serviceIntent1);*/

                                //  dialog.dismiss();

                                dialog.dismiss();
                                //finish();

                                String Id_colunn = preference.getString("coulmunId");
                                String Start_mileage = TPApp.getMileage();

                                MilageDialog milageDialog = new MilageDialog();

                                try {
                                    if (isNetworkConnected()) {
                                        milageDialog.EndMilageDialogLogout(OffsiteNonEnforcement.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "Yes", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                                    } else {
                                        milageDialog.EndMilageDialogLogout(OffsiteNonEnforcement.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "No", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
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

        _btnBack.setOnClickListener(v -> {

            try {
                Logoutassignment();
            } catch (IOException e) {
                e.printStackTrace();
            }
            darAssignmentLocationJob.callAssignLocationAPI("", DateUtil.getCurrentDateTime1(), String.valueOf(id_assg_loc), TPApplication.getInstance().getAssignReportId(), TPApplication.getInstance().getAssLocRecId(), OffsiteNonEnforcement.this);

            if (menupos == 1) {

                Intent serviceIntent = new Intent(OffsiteNonEnforcement.this, EDarServiceJobIntentTask.class);
                serviceIntent.putExtra("START", "");
                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                serviceIntent.putExtra("ID", String.valueOf(id_assg_loc));
                serviceIntent.putExtra("WITCH", "Three");
                serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                EDarServiceJobIntentTask.enqueueWork(OffsiteNonEnforcement.this, serviceIntent);


               // finish();
                TPApplication.getInstance().setDarStartTimeTask(null);
            } else {
              //  finish();
            }
           /* new iOSDialogBuilder(OffsiteNonEnforcement.this)
                    .setSubtitle("SJPD Radio logoff \n Performed?")
                    .setPositiveListener("Yes", new iOSDialogClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(iOSDialog dialog) {

                            dialog.dismiss();
                            String Id_colunn = preference.getString("coulmunId");
                            String Start_mileage = TPApp.getMileage();



                            MilageDialog milageDialog = new MilageDialog();

                            if (isNetworkConnected()) {
                                milageDialog.EndMilageDialogLogout(OffsiteNonEnforcement.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.deviceId), "Yes", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                            } else {
                                milageDialog.EndMilageDialogLogout(OffsiteNonEnforcement.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.deviceId), "No", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                            }

                        }
                    })
                    .setNegativeListener("No", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {
                            dialog.dismiss();
                        }
                    })
                    .build().show();*/


        });
        _spinnerText.setText("SELECT ACTIVITY");

        _spinnerText.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(OffsiteNonEnforcement.this);
            dialog.setTitle("SELECT ACTIVITY");
            dialog.setItems(activity, (dialog1, position) -> {


                if (activity[position].equalsIgnoreCase("Traffic Control")) {
                    _spinnerText.setText(activity[position]);
                    /*_mCommunity.setVisibility(View.GONE);
                    _mTraffic.setVisibility(View.VISIBLE);
                    _mRide.setVisibility(View.GONE);
                    _mFlyer.setVisibility(View.GONE);
                    _mSeniorDuty.setVisibility(View.GONE);*/
                    if (menupos != 1) {
                        try {
                            callTaskReport(DateUtil.getCurrentDateTime1().toString(), "", String.valueOf(id_assg_loc), "0", 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    menupos = 1;

                }
                if (activity[position].equalsIgnoreCase("Community meeting")) {

                    if (menupos==1)
                    {
                        Intent serviceIntent = new Intent(OffsiteNonEnforcement.this, EDarServiceJobIntentTask.class);
                        serviceIntent.putExtra("START", "");
                        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                        serviceIntent.putExtra("ID", String.valueOf(id_assg_loc));
                        serviceIntent.putExtra("WITCH", "Three");
                        serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                        EDarServiceJobIntentTask.enqueueWork(OffsiteNonEnforcement.this, serviceIntent);
                    }
                    menupos =0;
                   /* _mCommunity.setVisibility(View.VISIBLE);
                    _mTraffic.setVisibility(View.GONE);
                    _mRide.setVisibility(View.GONE);
                    _mFlyer.setVisibility(View.GONE);
                    _mSeniorDuty.setVisibility(View.GONE);*/

                    _spinnerText.setText("SELECT ACTIVITY");

                    try {
                        callTaskReport(DateUtil.getCurrentDateTime1().toString(), "", String.valueOf(id_assg_loc), "0", 2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Intent i = new Intent();
                    i.putExtra("ASSIGNMENT", id_assg_loc);
                    i.setClass(OffsiteNonEnforcement.this, CommunityMeeting.class);
                    startActivity(i);
                }
                if (activity[position].equalsIgnoreCase("Ride along")) {
                  /*  _mCommunity.setVisibility(View.GONE);
                    _mTraffic.setVisibility(View.GONE);
                    _mRide.setVisibility(View.VISIBLE);
                    _mFlyer.setVisibility(View.GONE);
                    _mSeniorDuty.setVisibility(View.GONE);*/

                    if (menupos==1)
                    {
                        Intent serviceIntent = new Intent(OffsiteNonEnforcement.this, EDarServiceJobIntentTask.class);
                        serviceIntent.putExtra("START", "");
                        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                        serviceIntent.putExtra("ID", String.valueOf(id_assg_loc));
                        serviceIntent.putExtra("WITCH", "Three");
                        serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                        EDarServiceJobIntentTask.enqueueWork(OffsiteNonEnforcement.this, serviceIntent);
                    }
                    menupos =0;

                    _spinnerText.setText("SELECT ACTIVITY");

                    try {
                        callTaskReport(DateUtil.getCurrentDateTime1().toString(), "", String.valueOf(id_assg_loc), "0", 3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Intent i = new Intent();
                    i.putExtra("ASSIGNMENT", id_assg_loc);
                    i.setClass(OffsiteNonEnforcement.this, RideAlong.class);
                    startActivity(i);

                }
                if (activity[position].equalsIgnoreCase("Training")) {
                  /*  _mCommunity.setVisibility(View.GONE);
                    _mTraffic.setVisibility(View.GONE);
                    _mRide.setVisibility(View.VISIBLE);
                    _mFlyer.setVisibility(View.GONE);
                    _mSeniorDuty.setVisibility(View.GONE);*/

                    if (menupos==1)
                    {
                        Intent serviceIntent = new Intent(OffsiteNonEnforcement.this, EDarServiceJobIntentTask.class);
                        serviceIntent.putExtra("START", "");
                        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                        serviceIntent.putExtra("ID", String.valueOf(id_assg_loc));
                        serviceIntent.putExtra("WITCH", "Three");
                        serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                        EDarServiceJobIntentTask.enqueueWork(OffsiteNonEnforcement.this, serviceIntent);
                    }
                    menupos =0;

                    _spinnerText.setText("SELECT ACTIVITY");

                    try {
                        callTaskReport(DateUtil.getCurrentDateTime1().toString(), "", String.valueOf(id_assg_loc), "0", 4);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent i = new Intent();
                    i.putExtra("ASSIGNMENT", id_assg_loc);
                    i.setClass(OffsiteNonEnforcement.this, Training.class);
                    startActivity(i);

                }
                if (activity[position].equalsIgnoreCase("Flyer")) {
                   /* _mCommunity.setVisibility(View.GONE);
                    _mTraffic.setVisibility(View.GONE);
                    _mRide.setVisibility(View.GONE);
                    _mFlyer.setVisibility(View.VISIBLE);
                    _mSeniorDuty.setVisibility(View.GONE);*/


                    if (menupos==1)
                    {
                        Intent serviceIntent = new Intent(OffsiteNonEnforcement.this, EDarServiceJobIntentTask.class);
                        serviceIntent.putExtra("START", "");
                        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                        serviceIntent.putExtra("ID", String.valueOf(id_assg_loc));
                        serviceIntent.putExtra("WITCH", "Three");
                        serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                        EDarServiceJobIntentTask.enqueueWork(OffsiteNonEnforcement.this, serviceIntent);
                    }
                    menupos =0;

                    _spinnerText.setText("SELECT ACTIVITY");

                    try {
                        callTaskReport(DateUtil.getCurrentDateTime1().toString(), "", String.valueOf(id_assg_loc), "0", 5);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Intent i = new Intent();
                    i.putExtra("ASSIGNMENT", id_assg_loc);
                    i.setClass(OffsiteNonEnforcement.this, Flayer.class);
                    startActivity(i);
                }
                if (activity[position].equalsIgnoreCase("Senior Duties")) {
                    /*_mCommunity.setVisibility(View.GONE);
                    _mTraffic.setVisibility(View.GONE);
                    _mRide.setVisibility(View.GONE);
                    _mFlyer.setVisibility(View.GONE);
                    _mSeniorDuty.setVisibility(View.VISIBLE);


                    _mList = DarSeniorDutiesElements._getList(TPApp.custId);
                    mAdapter = new SeniorDutyAdapter(_mList,this);
                    mRecyclerView.setAdapter(mAdapter);*/

                    if (menupos==1)
                    {
                        Intent serviceIntent = new Intent(OffsiteNonEnforcement.this, EDarServiceJobIntentTask.class);
                        serviceIntent.putExtra("START", "");
                        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                        serviceIntent.putExtra("ID", String.valueOf(id_assg_loc));
                        serviceIntent.putExtra("WITCH", "Three");
                        serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                        EDarServiceJobIntentTask.enqueueWork(OffsiteNonEnforcement.this, serviceIntent);
                    }
                    menupos =0;

                    _spinnerText.setText("SELECT ACTIVITY");

                    try {
                        callTaskReport(DateUtil.getCurrentDateTime1().toString(), "", String.valueOf(id_assg_loc), "0", 6);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Intent i = new Intent();
                    i.putExtra("ASSIGNMENT", id_assg_loc);
                    i.setClass(OffsiteNonEnforcement.this, SeniorDuties.class);
                    startActivity(i);
                }
            });
            dialog.setPositiveButton("Cancel", (dialog12, which) -> dialog12.dismiss());
            AlertDialog alert = dialog.create();
            alert.show();
        });

        _btnStartShift.setOnClickListener(v -> {
            calendar = Calendar.getInstance();
            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendar.get(Calendar.MINUTE);

            timePickerDialog = new TimePickerDialog(OffsiteNonEnforcement.this, (timePicker, hourOfDay, minutes) -> {
                if (hourOfDay >= 12) {
                    amPm = "PM";
                } else {
                    amPm = "AM";
                }
                _startShift.setText(DateUtil.getCurrentDate1() + " " + String.format("%02d:%02d", hourOfDay, minutes) + amPm);
            }, currentHour, currentMinute, false);

            timePickerDialog.show();
        });

        _btnReleasePost.setOnClickListener(v -> {
            calendar = Calendar.getInstance();
            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendar.get(Calendar.MINUTE);

            timePickerDialog = new TimePickerDialog(OffsiteNonEnforcement.this, (timePicker, hourOfDay, minutes) -> {
                if (hourOfDay >= 12) {
                    amPm = "PM";
                } else {
                    amPm = "AM";
                }
                _releaseShit.setText(DateUtil.getCurrentDate1() + " " + String.format("%02d:%02d ", hourOfDay, minutes) + amPm);
            }, currentHour, currentMinute, false);

            timePickerDialog.show();
        });

        _btnEndShift1.setOnClickListener(v -> {
            calendar = Calendar.getInstance();
            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendar.get(Calendar.MINUTE);

            timePickerDialog = new TimePickerDialog(OffsiteNonEnforcement.this, (timePicker, hourOfDay, minutes) -> {
                if (hourOfDay >= 12) {
                    amPm = "PM";
                } else {
                    amPm = "AM";
                }
                _endShift.setText(DateUtil.getCurrentDate1() + " " + String.format("%02d:%02d", hourOfDay, minutes) + amPm);
            }, currentHour, currentMinute, false);

            timePickerDialog.show();
        });

        //   _btnSave.setOnClickListener(v -> );
        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_spinnerText.getText().toString().isEmpty() || _spinnerText.getText().toString().equals("SELECT ACTIVITY")) {
                    SpinnerFailedDialog();
                } else {
                    validator.validate();
                }

            }
        });
        _startShift.setOnClickListener(v -> {
            calendar = Calendar.getInstance();
            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendar.get(Calendar.MINUTE);

            timePickerDialog = new TimePickerDialog(OffsiteNonEnforcement.this, (timePicker, hourOfDay, minutes) -> {
                if (hourOfDay >= 12) {
                    amPm = "PM";
                } else {
                    amPm = "AM";
                }
                _startShift.setText(DateUtil.getCurrentDate1() + " " + String.format("%02d:%02d", hourOfDay, minutes) + amPm);
            }, currentHour, currentMinute, false);

            timePickerDialog.show();
        });
    }

    private void __loadDataFromDb() {
        userInfo = User.getUserInfo(TPApp.userId);
        try {
            if (userInfo.getFullName() != null && !TextUtils.isEmpty(userInfo.getFullName())) {
                _userName.setText(userInfo.getFullName());
                _badgeNum.setText(userInfo.getBadge());
                _dateTime.setText(DateUtil.getCurrentDateTime());
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception failure");
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

    public void __save() throws IOException {

        progressDialog = ProgressDialog.show(OffsiteNonEnforcement.this, "", "Inserting...");

        DarOffSiteNonInforcementForm form = new DarOffSiteNonInforcementForm();
        Params params = new Params();
        List<TrafficDetails> details = new ArrayList<>();
        TrafficDetails detail = new TrafficDetails();
        detail.setNonInforcementDdElementId(String.valueOf(1));
        detail.setUserId(String.valueOf(userInfo.getUserId()));
        detail.setBadge(userInfo.getBadge());
        detail.setName(userInfo.getFullName());
        detail.setEndShift(_endShift.getText().toString());
        detail.setDeviceid(String.valueOf(TPApp.deviceId));
        detail.setDatetime(_dateTime.getText().toString());
        detail.setEventName(_eventName.getText().toString());
        detail.setStartShift(_startShift.getText().toString());
        detail.setReleasePostTime(_releaseShit.getText().toString());
        detail.setEquipmentId(TPApp.getEquipment());
        detail.setAssignmentId(id_assg_loc);

     /*   if (TPApplication.getInstance().getDarTaskReoprtId() != null) {
            detail.setDarTaskReportId(TPApplication.getInstance().getDarTaskReoprtId());
        } else {
            detail.setDarTaskReportId("");
        }*/


        if (!preference.getString("coulmunId").isEmpty() || !preference.getString("coulmunId").equals(""))
            detail.setMileageId(preference.getString("coulmunId"));
        else
            detail.setMileageId("0");

        if (TPApplication.getInstance().getDarTaskReoprtId() != null) {
            detail.setDarTaskReportId(TPApplication.getInstance().getDarTaskReoprtId());
        } else {
            detail.setDarTaskReportId("");
        }

        detail.setEquipmentId(TPApp.getEquipment());
        detail.setSubEquipmentId(TPApp.getEquipmentChild());
        detail.setVehicle(TPApp.getVehicleType());
        detail.setMileage(TPApp.getMileage());
        detail.setDisinfecting(TPApp.getDisinfecting());
        detail.setVdr(TPApp.getVdr());
        detail.setAssignmentId(id_assg_loc);
        detail.setAppId((int) TrafficDetails.getNextPrimaryId());
        details.add(detail);
        params.setDetails(details);
        params.setCustId(TPApp.custId);
        form.setParams(params);
        form.setJsonrpc("2.0");
        form.setId("82F85DB43CBF6");
        form.setMethod("darOffSiteNonInforcementFormTrafficControlInsert");
        System.out.println("RequestObj**" + new Gson().toJson(form));
        log.info(new Gson().toJson(form));

        if (isNetworkConnected()) {
           // TrafficDetails.insertTrafficDetails(detail);
            ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
            api.insertDarOffSiteNonInforcementForm(form).enqueue(new Callback<DarTicketResponse>() {
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
                                _eventName.setText("");
                                _releaseShit.setText("");
                                _endShift.setText("");
                                _badgeNum.setText("");
                                _dateTime.setText("");
                                _mCommunityLocation.setText("");
                                _mCommunityLocation.setText("");
                                _mCommunityDate.setText("");
                                _mRideSpinner.setText("");
                                _mRideSpinner2.setText("");
                                Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();

                            }else {
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
                        log.error("server not responding");
                    }
                }

                @Override
                public void onFailure(Call<DarTicketResponse> call, Throwable t) {
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
            TrafficDetails.insertTrafficDetails(detail);
            _eventName.setText("");
            _releaseShit.setText("");
            _endShift.setText("");
            _badgeNum.setText("");
            _dateTime.setText("");
            _mCommunityLocation.setText("");
            _mCommunityLocation.setText("");
            _mCommunityDate.setText("");
            _mRideSpinner.setText("");
            _mRideSpinner2.setText("");
            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();

        }

    }

    public void SpinnerFailedDialog() {

        new iOSDialogBuilder(OffsiteNonEnforcement.this)
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
            Logoutassignment();
        } catch (IOException e) {
            e.printStackTrace();
        }
        darAssignmentLocationJob.callAssignLocationAPI("", DateUtil.getCurrentDateTime1(), String.valueOf(id_assg_loc), TPApplication.getInstance().getAssignReportId(), TPApplication.getInstance().getAssLocRecId(), OffsiteNonEnforcement.this);

        if (menupos == 1) {

            Intent serviceIntent = new Intent(OffsiteNonEnforcement.this, EDarServiceJobIntentTask.class);
            serviceIntent.putExtra("START", "");
            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
            serviceIntent.putExtra("ID", String.valueOf(id_assg_loc));
            serviceIntent.putExtra("WITCH", "Three");
            serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
            EDarServiceJobIntentTask.enqueueWork(OffsiteNonEnforcement.this, serviceIntent);
          //  finish();
            TPApplication.getInstance().setDarStartTimeTask(null);
        } else {
            //finish();
        }

       /* new iOSDialogBuilder(OffsiteNonEnforcement.this)
                .setSubtitle("SJPD Radio logoff \n Performed?")
                .setPositiveListener("Yes", new iOSDialogClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(iOSDialog dialog) {

                        dialog.dismiss();
                        String Id_colunn = preference.getString("coulmunId");
                        String Start_mileage = TPApp.getMileage();






                        MilageDialog milageDialog = new MilageDialog();

                        if (isNetworkConnected()) {
                            milageDialog.EndMilageDialogLogout(OffsiteNonEnforcement.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.deviceId), "Yes", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                        } else {
                            milageDialog.EndMilageDialogLogout(OffsiteNonEnforcement.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.deviceId), "No", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                        }

                    }


                })
                .setNegativeListener("No", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build().show();*/


    }

    public void callAssignLocationAPI(String startTime, String endTime, String id, String AssignReoprtId, String AssignLocationReportId, String AssignmentLocationId, Context context) throws IOException {
      //  progressDialog = ProgressDialog.show(context, "", "Please wait...");
        AssignmentLocationReport_rpc rpc = new AssignmentLocationReport_rpc();
        ParamAssignmentLocationReport param = new ParamAssignmentLocationReport();
        List<AssignmentLocationReportModel> aList = new ArrayList<>();
        AssignmentLocationReportModel model = new AssignmentLocationReportModel();
        model.setAssignmentLocId(AssignmentLocationId);
        model.setAssignmentReportId(id);
        model.setStartTime(startTime);
        model.setEndTime(endTime);
        model.setUserid(TPApplication.getInstance().userId);
        model.setAppId((int) AssignmentLocationReportModel.getNextPrimaryId());
        if (!AssignLocationReportId.isEmpty())
            model.setAssLocationReportId(AssignLocationReportId);
        else
            model.setAssLocationReportId("");
        if (!AssignReoprtId.isEmpty())
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
        System.out.println("RequestObj**" + new Gson().toJson(rpc));
        if (isNetworkConnected()) {

            AssignmentLocationReportModel.insertFieldContactDetails(model);
            ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
            api.insertAssignmentLocationReport(rpc).enqueue(new Callback<DarAssignmentLocationReportResponse>() {
                @Override
                public void onResponse(Call<DarAssignmentLocationReportResponse> call, Response<DarAssignmentLocationReportResponse> response) {
                    if (response.code() == 200 && response.isSuccessful()) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        List<String> assign_report_idd = null;
                        final List<Integer> appId = response.body().getResult().getAppId();

                        try {
                            if (response.body().getResult().getAssignmentReportId().get(0) != null) {
                                TPApplication.getInstance().setAssLocRecId(response.body().getResult().getAssignmentReportId().get(0));
                           /* List<String> assign_report_idassign_report_id=response.body().getResult().getAssignmentReportId();
                            assign_report_idd=assign_report_idassign_report_id;*/
                            }

                        } catch (Exception e) {
                       /* if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }*/
                            e.printStackTrace();
                            log.error("Exception failure");
                        }


                        if (appId != null && appId.size() > 0) {
                            for (int i = 0; i < appId.size(); i++) {
                                try {
                                    AssignmentLocationReportModel.removeById(appId.get(i));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    log.error("Exception failure");
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
                    } else {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    }
                }

                @Override
                public void onFailure(Call<DarAssignmentLocationReportResponse> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                 //   Log.d("Dar Report", "Network Fail"());
                   log.error(t.getMessage());

                }
            });
        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            NoInternetDialog();
        }

    }

    public void callAssignmentReport(String startTime, String endTime, String id, String assignReportId) throws IOException {
        progressDialog.show();
        progressDialog.setCancelable(false);
        AssignmentReport_rpc rpc = new AssignmentReport_rpc();
        ParamAssignmentReport param = new ParamAssignmentReport();
        List<AssignmentReportModel> aList = new ArrayList<>();
        AssignmentReportModel model = new AssignmentReportModel();
        model.setAssignmentId(id);
        model.setStartTime(startTime);
        model.setEndTime(endTime);
        model.setUserid(TPApplication.getInstance().userId);
        model.setAppId((int) AssignmentLocationReportModel.getNextPrimaryId());
        model.setAssignmentreportId(assignReportId);
        model.setDeviceid(String.valueOf(TPApp.deviceId));

        aList.add(model);
        param.setDetails(aList);
        param.setCustId(TPApplication.getInstance().custId);
        rpc.setParams(param);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("dar_assignment_report");
        System.out.println("RequestObj**" + new Gson().toJson(rpc));
        AssignmentReportModel.insertFieldContactDetails(model);
        ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
        if (isNetworkConnected()) {
            api.insertAssignmentReport(rpc).enqueue(new Callback<DarAssignmentreportResponse>() {
                @Override
                public void onResponse(Call<DarAssignmentreportResponse> call, Response<DarAssignmentreportResponse> response) {


                    if (response.isSuccessful() && response.code()==200) {
                        preference.putString("AssSubId", "1");
                        preference.putString("AssignId", id);
                        final List<Integer> appId = response.body().getResult().getAppId();
                        final List<String> assignmentReportId = response.body().getResult().getAssignmentReportId();

                        if (appId != null && appId.size() > 0) {
                            for (int i = 0; i < appId.size(); i++) {
                                try {
                                    AssignmentReportModel.removeById(appId.get(i));

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    log.error("Exception failure");
                                }
                            }
                        }
                        if (assignmentReportId != null && assignmentReportId.size() > 0) {
                            for (int j = 0; j < assignmentReportId.size(); j++) {
                                TPApplication.getInstance().setAssignReportId(assignmentReportId.get(j));
                                if (locationDetails.size()>0)
                                {

                                    log.info("Offsite/Non-Inforcement Login");
                                    int AssignmentLoc_ID=locationDetails.get(0).getId();
                                    try {
                                        callAssignLocationAPI(DateUtil.getCurrentDateTime1(), "", String.valueOf(id_assg_loc), assignmentReportId.get(j), "", String.valueOf(AssignmentLoc_ID), OffsiteNonEnforcement.this);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                                Log.d("assignmentReportId", assignmentReportId.get(j));
                            }

                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                           // Dialog();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        finish();

                       // Dialog();
                    }
                }

                @Override
                public void onFailure(Call<DarAssignmentreportResponse> call, Throwable t) {
                    Log.d("Dar Report", "Network Fail");
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    log.error(t.getMessage());
              //      log.error("Network Fail"());
                   // log.error("Failure");
                    Dialog();
                    finish();

                }
            });

        } else {
            //Toast.makeText(DarAssignmentActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            NoInternetDialog();
        }

    }


    public void Dialog() {

        new iOSDialogBuilder(OffsiteNonEnforcement.this)
                .setSubtitle("Plaese try again")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                               /* Intent intent = new Intent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                                intent.setClass(SignCheck.this, DarEquipmentNew.class);
                                startActivity(intent);*/
                        dialog.dismiss();


                    }
                })
                /*.setNegativeListener("No", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })*/
                .build().show();
        //new ProxyImpl().updateDarTable();
    }

    public void NoInternetDialog() {

        new iOSDialogBuilder(OffsiteNonEnforcement.this)
                .setSubtitle("Plaese check your internet connectivity")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                               /* Intent intent = new Intent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                                intent.setClass(SignCheck.this, DarEquipmentNew.class);
                                startActivity(intent);*/
                        dialog.dismiss();
                        finish();

                    }
                })
                /*.setNegativeListener("No", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })*/
                .build().show();
        //new ProxyImpl().updateDarTable();
    }

    public void Logoutassignment() throws IOException {
       /* Intent serviceIntent = new Intent(OffsiteNonEnforcement.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", "");
        serviceIntent.putExtra("END",DateUtil.getCurrentDateTime1());
        if (!TPApplication.getInstance().getAssignReportId().isEmpty())
            serviceIntent.putExtra("assignment_report_id",TPApplication.getInstance().getAssignReportId());
        else
            serviceIntent.putExtra("assignment_report_id","");
        serviceIntent.putExtra("ID","");
        serviceIntent.putExtra("WITCH","One");
        EDarServiceJobIntentTask.enqueueWork(OffsiteNonEnforcement.this, serviceIntent);*/
        callAssignmentLogout("",DateUtil.getCurrentDateTime1(),"",TPApplication.getInstance().getAssignReportId());
    }

    private void loadlocation(int idAsg) {
        locationDetails = new ArrayList<>();
        try {
            for (Duty model :  Duty.getDutiesList(idAsg))
                locationDetails.add(model);
        }catch (Exception e)
        {
            e.printStackTrace();

        }

    }

    public void callAssignmentReportLogout(String startTime, String endTime, String id, String assignReportId) throws IOException {
        //progressDialog = ProgressDialog.show(DarAssignmentActivity.this, "", "Please wait....");
        AssignmentReport_rpc rpc = new AssignmentReport_rpc();
        ParamAssignmentReport param = new ParamAssignmentReport();
        List<AssignmentReportModel> aList = new ArrayList<>();
        AssignmentReportModel model = new AssignmentReportModel();
        model.setAssignmentId(id);
        model.setStartTime(startTime);
        model.setEndTime(endTime);
        model.setUserid(TPApplication.getInstance().userId);
        model.setAppId((int) AssignmentLocationReportModel.getNextPrimaryId());
        model.setAssignmentreportId(assignReportId);
        aList.add(model);
        param.setDetails(aList);
        param.setCustId(TPApplication.getInstance().custId);
        rpc.setParams(param);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("dar_assignment_report");
        preference.putString("assignmentreportId", "0");
        System.out.println("RequestObj**" + new Gson().toJson(rpc));
        AssignmentReportModel.insertFieldContactDetails(model);
        ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
        if (isNetworkConnected()) {
            api.insertAssignmentReport(rpc).enqueue(new Callback<DarAssignmentreportResponse>() {
                @Override
                public void onResponse(Call<DarAssignmentreportResponse> call, Response<DarAssignmentreportResponse> response) {

                   /* if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
*/
                    if (response.isSuccessful() && response.code()== 200) {
                        preference.putString("AssSubId", "1");
                        final List<Integer> appId = response.body().getResult().getAppId();
                        final List<String> assignmentReportId = response.body().getResult().getAssignmentReportId();

                        if (appId != null && appId.size() > 0) {
                            for (int i = 0; i < appId.size(); i++) {
                                try {
                                    AssignmentReportModel.removeById(appId.get(i));

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    log.error("Exception failure");
                                }
                            }
                        }
                      /*  if (assignmentReportId != null && assignmentReportId.size() > 0) {
                            for (int j = 0; j < assignmentReportId.size(); j++) {
                                TPApplication.getInstance().setAssignReportId(assignmentReportId.get(j));
                                Log.d("assignmentReportId", assignmentReportId.get(j));
                                preference.putString("assignmentreportId", assignmentReportId.get(j));
                                AssignmentId_API=Integer.valueOf(id);
                                callAssignLocationAPI(DateUtil.getCurrentDateTime1(), "", String.valueOf(id), TPApplication.getInstance().getAssignReportId(), "", String.valueOf(AssignmentReportId), DarAssignmentActivity.this);
                            }

                        } else {
                            Dialog();
                        }*/
                    } else {
                        Dialog();
                    }
                }

                @Override
                public void onFailure(Call<DarAssignmentreportResponse> call, Throwable t) {
                    Log.d("Dar Report", "Network Fail");
                     log.error(t.getMessage());


                }
            });

        } else {
            //Toast.makeText(DarAssignmentActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
           /* if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }*/
            NoInternetDialog();
        }

    }


    public void callTaskReport(String startTime, String endTime, String id, String TaskRecordId, int Id_Task) throws IOException {
        progressDialog = ProgressDialog.show(OffsiteNonEnforcement.this, "", "Please wait....");
        TaskReport_rpc rpc = new TaskReport_rpc();
        ParamTaskReport param = new ParamTaskReport();
        List<TaskReportModel> aList = new ArrayList<>();
        TaskReportModel model = new TaskReportModel();
        if (!preference.getString("coulmunId").isEmpty() || !preference.getString("coulmunId").equals(""))
            model.setMileageId(preference.getString("coulmunId"));
        else
            model.setMileageId("0");
        //
        model.setAssignmentLocReportId("4");
        model.setTaskId(String.valueOf(Id_Task));
        model.setStartTime(startTime);
        model.setEndTime(endTime);
        model.setMileage(TPApp.getMileage());
        model.setDisinfecting(TPApp.getDisinfecting());
        model.setVdr(TPApp.getVdr());
        model.setActionId("5");
        model.setAssignmentId(2);
        model.setLocationId("9");
        model.setEquipmentId(TPApp.getEquipment());
        model.setSubEquipmentId(TPApp.getEquipmentChild());
        if (TPApplication.getInstance().getDarTaskReoprtId() != null && !TaskRecordId.equals("0")) {
            model.setDartaskReportId("");
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

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.isSuccessful() && response.code()==200) {
                        final List<Integer> appId = response.body().getResult().getAppId();
                        try {
                            if (response.body().getResult() != null) {
                                if (response.body().getResult().getDarTaskReportId().get(0) != null) {
                                    TPApplication.getInstance().setDarTaskReoprtId(response.body().getResult().getDarTaskReportId().get(0));

                                }
                            }
                        } catch (Exception e) {
                            FailedtDialog();
                            log.error("Exception failure");
                        }
                        if (appId != null && appId.size() > 0) {
                            for (int i = 0; i < appId.size(); i++) {
                                try {
                                    TaskReportModel.removeById(appId.get(i));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    log.error(e.getMessage());

                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<DarTaskReportResponse> call, Throwable t) {
                    Log.d("Dar Report", "Network Fail");
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    log.error(t.getMessage());
                    FailedtDialog();
                }
            });
        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            FailedtDialog();
        }

    }

    public void FailedtDialog() {

        new iOSDialogBuilder(OffsiteNonEnforcement.this)
                .setSubtitle("Plaese try again")
                .setPositiveListener("OK", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build().show();

    }


    public void callAssignmentLogout(String startTime, String endTime, String id, String assignReportId) throws IOException {
        progressDialog = ProgressDialog.show(OffsiteNonEnforcement.this, "", "Please wait....");
        AssignmentReport_rpc rpc = new AssignmentReport_rpc();
        ParamAssignmentReport param = new ParamAssignmentReport();
        List<AssignmentReportModel> aList = new ArrayList<>();
        AssignmentReportModel model = new AssignmentReportModel();
        model.setAssignmentId(id);
        model.setStartTime(startTime);
        model.setEndTime(endTime);
        model.setUserid(TPApplication.getInstance().userId);
        model.setAppId((int) AssignmentLocationReportModel.getNextPrimaryId());
        model.setAssignmentreportId(assignReportId);
        aList.add(model);
        param.setDetails(aList);
        param.setCustId(TPApplication.getInstance().custId);
        rpc.setParams(param);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("dar_assignment_report");
        preference.putString("assignmentreportId", "0");
        System.out.println("RequestObj**" + new Gson().toJson(rpc));
        AssignmentReportModel.insertFieldContactDetails(model);
        ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
        if (isNetworkConnected()) {
            api.insertAssignmentReport(rpc).enqueue(new Callback<DarAssignmentreportResponse>() {
                @Override
                public void onResponse(Call<DarAssignmentreportResponse> call, Response<DarAssignmentreportResponse> response) {

                    if (response.code() == 200 && response.isSuccessful()) {

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        finish();
                       // preference.putString("AssSubId", "1");
                       // final List<Integer> appId = response.body().getResult().getAppId();
                       // final List<String> assignmentReportId = response.body().getResult().getAssignmentReportId();

                       /* if (appId != null && appId.size() > 0) {
                            for (int i = 0; i < appId.size(); i++) {
                                try {
                                    AssignmentReportModel.removeById(appId.get(i));

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    log.error(e.getMessage().toString());
                                }
                            }
                        }*/

                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        FailedtDialog();
                        //    Dialog();
                    }
                }

                @Override
                public void onFailure(Call<DarAssignmentreportResponse> call, Throwable t) {
                    Log.d("Dar Report", t.getMessage());
                    log.error(t.getMessage());

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    FailedtDialog();
                }
            });

        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            log.error("No Internet");
            NoInternet();
        }

    }

    public void NoInternet() {

        new iOSDialogBuilder(OffsiteNonEnforcement.this)
                .setSubtitle("Plaese check your internet connectivity")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                      //  finish();
                    }
                })
                .build().show();

    }



    public void Failed() {

        new iOSDialogBuilder(OffsiteNonEnforcement.this)
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
                                    TrafficDetails.removeById(appId.get(i).intValue());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        //finish();
                        _eventName.setText("");
                        _releaseShit.setText("");
                        _endShift.setText("");
                        _badgeNum.setText("");
                        _dateTime.setText("");
                        _mCommunityLocation.setText("");
                        _mCommunityLocation.setText("");
                        _mCommunityDate.setText("");
                        _mRideSpinner.setText("");
                        _mRideSpinner2.setText("");
                        Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
*/