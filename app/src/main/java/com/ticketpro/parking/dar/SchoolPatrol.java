package com.ticketpro.parking.dar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.ticketpro.model.ChalkVehicle;
import com.ticketpro.model.Ticket;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.ChalkVehicleActivity;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.activity.WriteTicketActivity;
import com.ticketpro.parking.activity.handlers.Schoolitemlistner;
import com.ticketpro.parking.dar.model.DarAssignmentLocationJob;
import com.ticketpro.parking.dar.model.DarDutyReportJob;
import com.ticketpro.parking.dar.model.DarSchoolDropDownElement;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.School;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.params.ParamSchool;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.request.SchoolJson_rpc;
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

public class SchoolPatrol extends BaseActivityImpl implements Schoolitemlistner, Validator.ValidationListener, View.OnClickListener {
    @NotEmpty
    private EditText mPtcoName;
    @NotEmpty
    private EditText mDateTime;
    @NotEmpty
    private EditText mSchoolName;
    @NotEmpty
    private EditText mSchoolType;
    @NotEmpty
    private EditText mSchoolAddress;
    @NotEmpty
    private EditText mSchoolDisct;
    @NotEmpty
    private EditText mCouncilDist;
    @NotEmpty
    private EditText mPrinciple;
    @NotEmpty
    private EditText mStaffName;
    @NotEmpty
    private EditText mIssueCon;
    @NotEmpty
    private EditText mCommonViol;
    @NotEmpty
    private EditText mReason;
    @NotEmpty
    private EditText mNumberWrng;
    @NotEmpty
    private EditText mCitIssue;
    @NotEmpty
    private EditText mDropTime;
    @NotEmpty
    private EditText mPickTime;
    @NotEmpty
    private EditText mResonNoSchool;

    private TextView mActivity;
    private CheckBox mYes;
    private CheckBox mNo;

    private Button mBack;
    private Button mEndShift;
    private Button mLogOut;
    private Button mSave;

    private ProgressDialog progressDialog;
    private Validator validator;

    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int currentHour;
    private int currentMinute;
    private String amPm;

    private Button _writeTicket;
    private Button _chalkVehicle;
    private String isAccept;
    private Integer id;
    private int asgId = 0;
    private int locId = 0;
    private int taskId = 0;
    int getvalue = 0;
    SharedPreferences sharedPreferences;
    private Preference preference;
    private String inTime = null;
    ArrayList<String> Schoollist_Item = new ArrayList<>();
    ArrayList<String> SchoolList_id = new ArrayList<>();
    ArrayList<String> SchoolList_ItemList = new ArrayList<>();
    DarSchoolListAdapter adapter;
    String mActivity_VALUE;
    Dialog builderSingle;
    DarAssignmentLocationJob darAssignmentLocationJob = new DarAssignmentLocationJob();
    DarDutyReportJob darDutyReportJob = new DarDutyReportJob();
    DarSJPDlogoutJob darSJPDlogoutJob = new DarSJPDlogoutJob();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_patrol);
        setLogger(SchoolPatrol.class.getName());
        preference = Preference.getInstance(SchoolPatrol.this);
        TPApplication.getInstance().setDarStartTimeTask(DateUtil.getCurrentDateTime1());
        Intent it = getIntent();
        asgId = it.getIntExtra("ASG_ID", 0);
        locId = it.getIntExtra("LOCATION_ID", 0);
        taskId = it.getIntExtra("TASK_ID", 0);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        inTime = preference.getString("IN_DATE");

        validator = new Validator(this);
        validator.setValidationListener(this);
        Button _btnBack = findViewById(R.id.sch_btnBack);
        _btnBack.setOnClickListener(v -> {

            try {
                callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
            } catch (IOException e) {
                e.printStackTrace();
            }

          /*  Intent serviceIntent = new Intent(SchoolPatrol.this, EDarServiceJobIntentTask.class);
            serviceIntent.putExtra("START", "");
            serviceIntent.putExtra("END",DateUtil.getCurrentDateTime1());
            serviceIntent.putExtra("ID",String.valueOf(locId));
            serviceIntent.putExtra("WITCH","Three");
            serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
            EDarServiceJobIntentTask.enqueueWork(SchoolPatrol.this, serviceIntent);
            TPApplication.getInstance().setDarStartTimeTask(null);
            finish();*/
        });

        //UI Binding
        __initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void __initView() {
        mPtcoName = findViewById(R.id.sch_ptcoName);
        mDateTime = findViewById(R.id.sch_datetime);
        mSchoolName = findViewById(R.id.sch_school_name);
        mSchoolType = findViewById(R.id.sch_school_type);
        mSchoolDisct = findViewById(R.id.sch_school_dist);
        mCouncilDist = findViewById(R.id.sch_council_dist);
        mSchoolAddress = findViewById(R.id.sch_school_address);
        mPrinciple = findViewById(R.id.sch_principle_name);
        mStaffName = findViewById(R.id.sch_staff_name);
        mIssueCon = findViewById(R.id.sch_issue);
        mCommonViol = findViewById(R.id.sch_common_violation);
        mReason = findViewById(R.id.sch_reason);
        mCitIssue = findViewById(R.id.sch_number_of_cit_issue);
        mNumberWrng = findViewById(R.id.sch_number_of_wrng);
        mDropTime = findViewById(R.id.sch_drop_time);
        mPickTime = findViewById(R.id.sch_pick_time);
        mResonNoSchool = findViewById(R.id.sch_reason_for_no_school);
        mEndShift = findViewById(R.id.sch_btnEndShift);
        mLogOut = findViewById(R.id.sch_btn_logout);
        mSave = findViewById(R.id.sch_btn_save);
        mYes = findViewById(R.id.checkBox2);
        mNo = findViewById(R.id.checkBox11);
        mActivity = findViewById(R.id.sch_activity_spinner);
        _writeTicket = findViewById(R.id.f22_write_ticket);
        _chalkVehicle = findViewById(R.id.f22_chalk);
        mPtcoName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(48)});
        mSchoolName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(85)});
        mSchoolType.setFilters(new InputFilter[]{new InputFilter.LengthFilter(48)});
        mSchoolDisct.setFilters(new InputFilter[]{new InputFilter.LengthFilter(48)});
        mCouncilDist.setFilters(new InputFilter[]{new InputFilter.LengthFilter(48)});
        mSchoolAddress.setFilters(new InputFilter[]{new InputFilter.LengthFilter(48)});
        mPrinciple.setFilters(new InputFilter[]{new InputFilter.LengthFilter(48)});
        mStaffName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(48)});
        mIssueCon.setFilters(new InputFilter[]{new InputFilter.LengthFilter(210)});
        mCommonViol.setFilters(new InputFilter[]{new InputFilter.LengthFilter(210)});
        mReason.setFilters(new InputFilter[]{new InputFilter.LengthFilter(210)});
        mResonNoSchool.setFilters(new InputFilter[]{new InputFilter.LengthFilter(210)});
        ArrayList<DarSchoolDropDownElement> darSchoolDropDownElements = DarSchoolDropDownElement._getDarSchoolDropDownElementList(TPApp.custId);

        mActivity.setOnClickListener(v -> {

            builderSingle = new Dialog(SchoolPatrol.this);
            /*   builderSingle.setIcon(R.drawable.icon);*/
            builderSingle.setTitle("Select School");
            View customLayout = getLayoutInflater().inflate(R.layout.alert_dialog_schoollist, null);
            builderSingle.setContentView(customLayout);
            Schoollist_Item.clear();
            SchoolList_id.clear();
            SchoolList_ItemList.clear();
            /* final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SchoolPatrol.this,R.layout.alert_dialog_schoollist);*/
            for (int i = 0; i < darSchoolDropDownElements.size(); i++) {
                final DarSchoolDropDownElement dar22500DisposionDropDownElement = darSchoolDropDownElements.get(i);
                String s = dar22500DisposionDropDownElement.getDdItem();
                int id = dar22500DisposionDropDownElement.getId();
                /* arrayAdapter.add(s);*/
                Schoollist_Item.add(s);
                SchoolList_ItemList.add(s);
                SchoolList_id.add(String.valueOf(id));
            }

            RecyclerView recyclerView = customLayout.findViewById(R.id.recy_school_list);
            SearchView searchView = customLayout.findViewById(R.id.searchView_school);
            searchView.setIconifiedByDefault(false);
            searchView.setFocusable(true);
            searchView.setIconified(false);
            searchView.requestFocusFromTouch();
            searchView.setQueryHint("Serach School");
            adapter = new DarSchoolListAdapter(SchoolPatrol.this, Schoollist_Item, this, SchoolList_id, builderSingle, SchoolList_ItemList);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
           /* recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(activity, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {

                            Toast.makeText(activity, "" + position, Toast.LENGTH_SHORT).show();
                        }
                    })
            );*/


            /*final AlertDialog alert = builderSingle.create();*/

           /* alert.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            alert.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            Window window = alert.getWindow();
            window.setGravity(Gravity.CENTER);
            *//*alert.show();*/

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    filter(s);
                    return false;
                }
            });

            /* builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());*/


           /* builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                mActivity.setText(arrayAdapter.getItem(which).toString());
                darSchoolDropDownElements.forEach((n)->{
                    if (n.getDdItem().equals(arrayAdapter.getItem(which)))
                        id = n.getId();
                });
                dialog.dismiss();
            });*/
            builderSingle.show();
        });

        mEndShift.setOnClickListener(v -> {




           /* Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntent.class);
            if (isNetworkConnected()) {
                EDarServiceJobIntent.enqueueWork(TaskForm22500.this, serviceIntent);
            }*/

            new iOSDialogBuilder(SchoolPatrol.this)
                    .setSubtitle("SJPD Radio logoff \n Performed?")
                    .setPositiveListener("Yes", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {

                            /*Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                            intent.setClass(SchoolPatrol.this, HomeActivity.class);
                            startActivity(intent);*/
                            darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(), SchoolPatrol.this, TPApplication.getInstance().getDarParkingdutyreportId());
                            darAssignmentLocationJob.callAssignLocationAPI("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getAssignReportId(), TPApplication.getInstance().getAssLocRecId(), SchoolPatrol.this);
                            darSJPDlogoutJob._sjpdlogout(SchoolPatrol.this);

                            Intent serviceIntent = new Intent(SchoolPatrol.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", "");
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(locId));
                            serviceIntent.putExtra("WITCH", "Three");
                            serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                            EDarServiceJobIntentTask.enqueueWork(SchoolPatrol.this, serviceIntent);

                            TPApplication.getInstance().setDarStartTimeTask(null);
                          /*  Intent serviceIntent1 = new Intent(SchoolPatrol.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(asgId));
                            serviceIntent.putExtra("WITCH", "Two");
                            EDarServiceJobIntentTask.enqueueWork(SchoolPatrol.this, serviceIntent1);*/

                            dialog.dismiss();
                            String Id_colunn = preference.getString("coulmunId");
                            String Start_mileage = TPApp.getMileage();

                            MilageDialog milageDialog = new MilageDialog();

                            try {
                                if (isNetworkConnected()) {
                                    milageDialog.EndMilageDialogShift(SchoolPatrol.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "Yes", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                                } else {
                                    milageDialog.EndMilageDialogShift(SchoolPatrol.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "No", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
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
        mLogOut.setOnClickListener(v -> {
            try {


                new iOSDialogBuilder(SchoolPatrol.this)
                        .setSubtitle("SJPD Radio logoff \n Performed?")
                        .setPositiveListener("Yes", new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                              /*  Intent intent = new Intent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                                intent.setClass(SchoolPatrol.this, DarEquipmentNew.class);
                                startActivity(intent);*/
                                darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(), SchoolPatrol.this, TPApplication.getInstance().getDarParkingdutyreportId());
                                darAssignmentLocationJob.callAssignLocationAPI("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getAssignReportId(), TPApplication.getInstance().getAssLocRecId(), SchoolPatrol.this);
                                darSJPDlogoutJob._sjpdlogout(SchoolPatrol.this);

                                Intent serviceIntent = new Intent(SchoolPatrol.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", "");
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", String.valueOf(locId));
                                serviceIntent.putExtra("WITCH", "Three");
                                serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                                EDarServiceJobIntentTask.enqueueWork(SchoolPatrol.this, serviceIntent);

                                TPApplication.getInstance().setDarStartTimeTask(null);
                               /* Intent serviceIntent1 = new Intent(SchoolPatrol.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", String.valueOf(asgId));
                                serviceIntent.putExtra("WITCH", "Two");
                                EDarServiceJobIntentTask.enqueueWork(SchoolPatrol.this, serviceIntent1);*/

                                dialog.dismiss();
                                //finish();
                                String Id_colunn = preference.getString("coulmunId");
                                String Start_mileage = TPApp.getMileage();

                                MilageDialog milageDialog = new MilageDialog();

                                try {
                                    if (isNetworkConnected()) {
                                        milageDialog.EndMilageDialogLogout(SchoolPatrol.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "Yes", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                                    } else {
                                        milageDialog.EndMilageDialogLogout(SchoolPatrol.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.userId), "No", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
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
        //Form Validation
        ///  mSave.setOnClickListener(v -> validator.validate());
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivity.getText().toString().isEmpty() || mActivity.getText().toString().equals("Select School")) {
                    SpinnerFailedDialog();
                } else {
                    validator.validate();
                }

            }
        });
        //Check validation
        mYes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mNo.setChecked(false);
            if (isChecked)
                mYes.setChecked(true);
            isAccept = "YES";
        });
        mNo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mYes.setChecked(false);
            if (isChecked)
                mNo.setChecked(true);
            isAccept = "NO";
        });

        mDateTime.setText(DateUtil.getCurrentDateTime());
        mPickTime.setOnClickListener(v -> {
            calendar = Calendar.getInstance();
            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendar.get(Calendar.MINUTE);

            timePickerDialog = new TimePickerDialog(SchoolPatrol.this, (timePicker, hourOfDay, minutes) -> {
                if (hourOfDay >= 12) {
                    amPm = "PM";
                } else {
                    amPm = "AM";
                }
                mPickTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
            }, currentHour, currentMinute, false);

            timePickerDialog.show();
        });

        mDropTime.setOnClickListener(v -> {
            calendar = Calendar.getInstance();
            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendar.get(Calendar.MINUTE);

            timePickerDialog = new TimePickerDialog(SchoolPatrol.this, (timePicker, hourOfDay, minutes) -> {
                if (hourOfDay >= 12) {
                    amPm = "PM";
                } else {
                    amPm = "AM";
                }
                mDropTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
            }, currentHour, currentMinute, false);

            timePickerDialog.show();
        });

        _chalkVehicle.setOnClickListener(v -> {
            ChalkVehicle chalk = TPApp.createNewChalk();
            TPApp.setActiveChalk(chalk);
            Intent i = new Intent();
            i.setClass(SchoolPatrol.this, ChalkVehicleActivity.class);
            startActivity(i);
        });
        _writeTicket.setOnClickListener(v -> {
            Ticket ticket = TPApp.createNewTicket();
            TPApp.setActiveTicket(ticket);
            Intent i = new Intent();
            i.setClass(SchoolPatrol.this, WriteTicketActivity.class);
            startActivity(i);
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
            __saveDetails();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void __saveDetails() throws IOException {
        progressDialog = ProgressDialog.show(SchoolPatrol.this, "", "Inserting...");
        SchoolJson_rpc schoolJson_rpc = new SchoolJson_rpc();
        List<School> aList = new ArrayList<>();
        ParamSchool paramSchool = new ParamSchool();
        School school = new School();

        school.setSchoolddId(String.valueOf(id));
        school.setSchoolType(mSchoolType.getText().toString().toUpperCase());
        school.setSchoolDistrict(mSchoolDisct.getText().toString().toUpperCase());
        if (TPApplication.getInstance().getDarTaskReoprtId() != null) {
            school.setDarTaskReportId(TPApplication.getInstance().getDarTaskReoprtId());
        } else {
            school.setDarTaskReportId("");
        }
        school.setSchoolAddress(mSchoolAddress.getText().toString().toUpperCase());
        school.setCouncilDistrict(mCouncilDist.getText().toString().toUpperCase());
        school.setContactsPrincipal(mPrinciple.getText().toString().toUpperCase());
        school.setSchoolName(mSchoolName.getText().toString().toUpperCase());
        school.setContactsStaff(mStaffName.getText().toString().toUpperCase());
        school.setIssuesConcerns(mIssueCon.getText().toString());
        school.setCommonViolations(mCommonViol.getText().toString());
        school.setSpeedDisplaySignDeployed(isAccept);
        school.setReason(mReason.getText().toString());

        if (!preference.getString("coulmunId").isEmpty() || !preference.getString("coulmunId").equals(""))
            school.setMileageId(preference.getString("coulmunId"));
        else
            school.setMileageId("0");
        school.setNumberOfCitationsIssued(mCitIssue.getText().toString());
        school.setNumberOfWarningsIssued(mNumberWrng.getText().toString());
        //school.setDate(mDateTime.getText().toString());
        school.setDropOffTimes(mDropTime.getText().toString());
        school.setPickUpTime(mPickTime.getText().toString());
        school.setAppId((int) School.getNextPrimaryId());
        school.setReasonForNoSchoolVisit(mResonNoSchool.getText().toString());
        school.setUserId(String.valueOf(TPApp.userId));
        school.setDeviceid(String.valueOf(TPApp.deviceId));

        school.setEquipmentId(TPApp.getEquipment());
        school.setSubEquipmentId(TPApp.getEquipmentChild());
        school.setVehicle(TPApp.getVehicleType());
        school.setMileage(TPApp.getMileage());
        school.setDisinfecting(TPApp.getDisinfecting());
        school.setVdr(TPApp.getVdr());
        school.setAssignmentId(String.valueOf(asgId));
        school.setLocationId(String.valueOf(locId));
        school.setTaskId(String.valueOf(taskId));
        school.setActionId("1");

        aList.add(school);
        paramSchool.setDetails(aList);
        paramSchool.setCustId(TPApp.custId);
        schoolJson_rpc.setJsonrpc("2.0");
        schoolJson_rpc.setMethod("insertDarSchoolForm");
        schoolJson_rpc.setId("82F85DB43CBF6");
        schoolJson_rpc.setParams(paramSchool);
        log.info(new Gson().toJson(schoolJson_rpc));
        /* System.out.println("RequestObj**"+new Gson().toJson(schoolJson_rpc));*/

        if (isNetworkConnected()) {
            try {
                //School.insertFieldContactDetails(school);
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertSchool(schoolJson_rpc).enqueue(new Callback<DarTicketResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<DarTicketResponse> call, @NonNull Response<DarTicketResponse> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful() && response.code() == 200) {
                            if (response.body().getResult() != null) {
                                if (response.body().getResult().getResult() == true) {
                                    clearAllView(response);
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
                            log.error("server not responding");
                        }


                    }

                    @Override
                    public void onFailure(Call<DarTicketResponse> call, Throwable t) {
                        Log.e("SchoolPatrol======>", t.getMessage());
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Failed();
                        log.error("Api failure");
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
                School.insertFieldContactDetails(school);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
            mActivity.setText("");
            mPtcoName.setText("");
            mDateTime.setText("");
            mSchoolName.setText("");
            mSchoolType.setText("");
            mSchoolDisct.setText("");
            mCouncilDist.setText("");
            mSchoolAddress.setText("");
            mPrinciple.setText("");
            mStaffName.setText("");
            mIssueCon.setText("");
            mCommonViol.setText("");
            mReason.setText("");
            mCitIssue.setText("");
            mNumberWrng.setText("");
            mDropTime.setText("");
            mPickTime.setText("");
            mResonNoSchool.setText("");
            mYes.setChecked(false);
            mNo.setChecked(false);
            mDateTime.setText(DateUtil.getCurrentDateTime());
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

    /**
     * Clear all view after response is success
     *
     * @param response
     */
    private void clearAllView(Response<DarTicketResponse> response) {

        mActivity.setText("");
        mPtcoName.setText("");
        mDateTime.setText("");
        mSchoolName.setText("");
        mSchoolType.setText("");
        mSchoolDisct.setText("");
        mCouncilDist.setText("");
        mSchoolAddress.setText("");
        mPrinciple.setText("");
        mStaffName.setText("");
        mIssueCon.setText("");
        mCommonViol.setText("");
        mReason.setText("");
        mCitIssue.setText("");
        mNumberWrng.setText("");
        mDropTime.setText("");
        mPickTime.setText("");
        mResonNoSchool.setText("");
        mYes.setChecked(false);
        mNo.setChecked(false);
        mDateTime.setText(DateUtil.getCurrentDateTime());
        Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
    }


    public void buildrecyle() {
      /*  adapter = new DarSchoolListAdapter(SchoolPatrol .this, Schoollist_Item);
        LinearLayoutManager manager = new LinearLayoutManager(this);*/

    }

    private void filter(String text) {

        ArrayList<String> filteredlist = new ArrayList<String>();


        for (String item : Schoollist_Item) {

            if (item.toLowerCase().contains(text.toLowerCase())) {

                filteredlist.add(item);
            }
        }
        /*if (filteredlist.isEmpty()) {

         *//* Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();*//*
        }*/
        adapter.filterList(filteredlist);
    }


    @Override
    public void onget(String pos, String item) {
        mSchoolDisct.setText("");
        mSchoolType.setText("");
        mSchoolAddress.setText("");
        builderSingle.setCancelable(true);
        if (!item.equals(""))
            mActivity.setText(item);
        mSchoolName.setText(item);
        if (!pos.equals("")) {
            id = Integer.parseInt(pos);
            GetSchoolDetilas(Integer.parseInt(pos));
        }
    }

    public void GetSchoolDetilas(int IID) {
        try {
            ArrayList<DarSchoolDropDownElement> darSchoolDropDownElements = DarSchoolDropDownElement._getDarSchoolDropDowndetailss(IID);
            if (darSchoolDropDownElements.get(0).getDistrict().equals(""))
                mSchoolDisct.setText("");
            else
                mSchoolDisct.setText(darSchoolDropDownElements.get(0).getDistrict());
            if (darSchoolDropDownElements.get(0).getSchooltype().equals(""))
                mSchoolType.setText("");
            else
                mSchoolType.setText(darSchoolDropDownElements.get(0).getSchooltype());
            if (darSchoolDropDownElements.get(0).getStreet().equals("")) {
                mSchoolAddress.setText("");
            } else {
                mSchoolAddress.setText(darSchoolDropDownElements.get(0).getStreet());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }


    }

    public void SpinnerFailedDialog() {

        new iOSDialogBuilder(SchoolPatrol.this)
                .setSubtitle("Please Select School")
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

   /*     Intent serviceIntent = new Intent(SchoolPatrol.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", "");
        serviceIntent.putExtra("END",DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID",String.valueOf(locId));
        serviceIntent.putExtra("WITCH","Three");
        serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
        EDarServiceJobIntentTask.enqueueWork(SchoolPatrol.this, serviceIntent);
        TPApplication.getInstance().setDarStartTimeTask(null);
        finish();
        super.onBackPressed();*/
    }

    public void callTaskReport(String startTime, String endTime, String id, String TaskRecordId, String taskid) throws IOException {
        progressDialog = ProgressDialog.show(SchoolPatrol.this, "", "Please Wait...");
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
                            log.error(e.getMessage());
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

        new iOSDialogBuilder(SchoolPatrol.this)
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



 /* if (response.isSuccessful() && response.code() == 200) {
            final DarTicketResult result = response.body().getResult();
            final List<Integer> appId = result.getAppId();
            if (appId !=null && appId.size()>0) {
                for (int i = 0; i < appId.size(); i++) {
                    try {
                        School.removeById(appId.get(i).intValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }*/