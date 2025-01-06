package com.ticketpro.parking.dar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.dar.model.DarAuthorityResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.DarTowCompanyDrop;
import com.ticketpro.parking.dar.model.DarTowReasonDropDown;
import com.ticketpro.parking.dar.model.TowModel;
import com.ticketpro.parking.dar.model.params.ParamTow;
import com.ticketpro.parking.dar.model.request.TowJson_rpc;
import com.ticketpro.util.DateUtil;
import com.ticketpro.util.TPUtility;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TowSupport extends BaseActivityImpl implements Validator.ValidationListener, MyTracker.ADLocationListener {

    @NotEmpty
    private EditText mPDEvent;
    @NotEmpty
    private EditText mDate;
    @NotEmpty
    private EditText mTime;
    @NotEmpty
    private EditText mDispatchTime;
    @NotEmpty
    private EditText mTowArrival;
    @NotEmpty
    private EditText mLocation;
    @NotEmpty
    private EditText mVlPlate;

    private EditText mNote;

    private TextView mDropDown1;
    private TextView mDropDown2;
    private TextView mDropDownAuthority;
    private Button mStart;
    private Button mStop;
    private Button mGps;
    private Button mSave;
    private Button mEnd;
    private Button mLogout;
    private CheckBox mYes;
    private CheckBox mNo;
    private Integer id;
    private Integer id1 = 0;
    private Integer id2 = 0;
    private int asgId = 0;
    private int locId = 0;
    private int taskId = 0;
    private String isAccept;
    private ProgressDialog progressDialog;
    private Validator validator;
    private ArrayList<DarTowCompanyDrop> darTowCompanyDrops;
    private ArrayList<DarTowReasonDropDown> darTowReasonDropDowns;
    private ArrayList<DarAuthorityResponse> darAuthorityResponses;
    private String dispatch_time = "000";
    private String arrival_time = "000";
    private Date pickerDate;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tow_support);
        setLogger(TowSupport.class.getName());

        Intent it = getIntent();
        asgId = it.getIntExtra("ASG_ID", 0);
        locId = it.getIntExtra("LOCATION_ID", 0);
        taskId = it.getIntExtra("TASK_ID", 0);

        __getTowCompanyDow();
        __getTowCancelDow();
        __getTowAuthority();
        validator = new Validator(this);
        validator.setValidationListener(this);


        Button _btnBack = findViewById(R.id.tow_btnBack);
        _btnBack.setOnClickListener(v -> finish());

        mPDEvent = findViewById(R.id.tow_event);
        mDate = findViewById(R.id.tow_date);
        mTime = findViewById(R.id.tow_time);
        mDispatchTime = findViewById(R.id.tow_dispatchtime);
        mTowArrival = findViewById(R.id.tow_arrival);
        mLocation = findViewById(R.id.tow_location);
        mVlPlate = findViewById(R.id.tow_plate);
        mNote = findViewById(R.id.tow_note);
        mStart = findViewById(R.id.tow_btn_start);
        mStop = findViewById(R.id.tow_btn_stop);
        mGps = findViewById(R.id.tow_btn_gps);
        mDropDown1 = findViewById(R.id.tow_activity_spinner);
        mDropDown2 = findViewById(R.id.tow_activity_spinner11);
        mDropDownAuthority = findViewById(R.id.tow_dd_authority);
        mSave = findViewById(R.id.tow_btn_save);
        mYes = findViewById(R.id.tow_checkBoxY);
        mNo = findViewById(R.id.tow_checkBoxN);
    /*    mNote.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mNote.setRawInputType(InputType.TYPE_CLASS_TEXT);
        mNote.setFilters(new InputFilter[]{new InputFilter.AllCaps()});*/

       /* mLocation.setRawInputType(InputType.TYPE_CLASS_TEXT);
        mLocation.setFilters(new InputFilter[]{new InputFilter.AllCaps()});*/

      /*  mPDEvent.setRawInputType(InputType.TYPE_CLASS_TEXT);
        mPDEvent.setFilters(new InputFilter[]{new InputFilter.AllCaps()});*/
     /*   mVlPlate.setRawInputType(InputType.TYPE_CLASS_TEXT);
        mVlPlate.setFilters(new InputFilter[]{new InputFilter.AllCaps()});*/

        //mPDEvent.requestFocus();
        mDate.setText(DateUtil.getCurrentDateTime());
        //Form Validation
        //   mSave.setOnClickListener(v -> validator.validate());
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDropDown1.getText().toString().equals("")) {
                    Toast.makeText(TowSupport.this, "Please select tow company", Toast.LENGTH_SHORT).show();
                } else if (mDropDownAuthority.getText().toString().equals("")) {
                    Toast.makeText(TowSupport.this, "Please select Tow Authority", Toast.LENGTH_SHORT).show();

                } else if (!mDropDown2.getText().toString().equals("") && mNote.getText().toString().equals("")) {
                    if (mNote.getText().toString().equals("")) {
                        mNote.setError("Please add notes");
                    }

                }
                // removed on Elie mail request on 12_01_2024
                /*else
                    if (mDropDown2.getText().toString().equals("")){
                    Toast.makeText(TowSupport.this, "Please select Reason tow was cancelled", Toast.LENGTH_SHORT).show();
                }*/
                else {
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

        mLocation.setOnLongClickListener(v -> {
            mLocation.setText("");
            return true;
        });

        mStart.setOnClickListener(v -> {
            showStartDatePicker(true);
           /* mDispatchTime.setText(DateUtil.getCurrentTimeTow());
            dispatch_time=DateUtil.getCurrentDateTime1();
            mTowArrival.setText("");*/

        });
        mStop.setOnClickListener(v -> {
            try {
                /*if (DateUtil.isTimeAfter(mDispatchTime.getText().toString(),DateUtil.getCurrentTimeTow())){
                    mTowArrival.setText(DateUtil.getCurrentTimeTow());
                    arrival_time=DateUtil.getCurrentDateTime1();
                }else {
                    Toast.makeText(TowSupport.this, "End time must be greater then dispatch time", Toast.LENGTH_LONG).show();
                }*/
                showEndDatePicker(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        mGps.setOnClickListener(v -> {
            findLoc();
        });

        mDropDown1.setOnClickListener(v -> {
            try {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(TowSupport.this);
                //builderSingle.setIcon(R.drawable.icon);
                builderSingle.setTitle("Tow Company");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(TowSupport.this, android.R.layout.select_dialog_singlechoice);
                for (int i = 0; i < darTowCompanyDrops.size(); i++) {
                    final DarTowCompanyDrop darTowCompanyDrop = darTowCompanyDrops.get(i);
                    String s = darTowCompanyDrop.getMenuName();
                    arrayAdapter.add(s);
                }
                builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

                builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                    mDropDown1.setText(arrayAdapter.getItem(which).toString());
                    darTowCompanyDrops.forEach((n) -> {
                        if (n.getMenuName().equals(arrayAdapter.getItem(which)))
                            id = n.getId();
                    });
                    dialog.dismiss();
                });
                builderSingle.show();
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        });

        mDropDown2.setOnClickListener(v -> {
            try {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(TowSupport.this);
                //builderSingle.setIcon(R.drawable.icon);
                builderSingle.setTitle("Reason tow was cancelled");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(TowSupport.this, android.R.layout.select_dialog_singlechoice);
                for (int i = 0; i < darTowReasonDropDowns.size(); i++) {
                    final DarTowReasonDropDown darTowCompanyDrop = darTowReasonDropDowns.get(i);
                    String s = darTowCompanyDrop.getMenuName();
                    arrayAdapter.add(s);
                }
                builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

                builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                    mDropDown2.setText(arrayAdapter.getItem(which).toString());
                    darTowReasonDropDowns.forEach((n) -> {
                        if (n.getMenuName().equals(arrayAdapter.getItem(which)))
                            id1 = n.getId();
                    });
                    dialog.dismiss();
                });
                builderSingle.show();
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        });

        mDropDownAuthority.setOnClickListener(v -> {
            try {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(TowSupport.this);
                //builderSingle.setIcon(R.drawable.icon);
                builderSingle.setTitle("Tow Authority");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(TowSupport.this, android.R.layout.select_dialog_singlechoice);
                for (int i = 0; i < darAuthorityResponses.size(); i++) {
                    final DarAuthorityResponse darTowCompanyDrop = darAuthorityResponses.get(i);
                    String s = darTowCompanyDrop.getMenuName();
                    arrayAdapter.add(s);
                }
                builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

                builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                    mDropDownAuthority.setText(arrayAdapter.getItem(which).toString());
                    darAuthorityResponses.forEach((n) -> {
                        if (n.getMenuName().equals(arrayAdapter.getItem(which)))
                            id2 = n.getId();
                    });
                    dialog.dismiss();
                });
                builderSingle.show();
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        });


    }

    private void __getTowAuthority() {
        try {
            darAuthorityResponses = DarAuthorityResponse.getElement(TPApp.custId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    private void __getTowCancelDow() {
        try {
            darTowReasonDropDowns = DarTowReasonDropDown._getDarTowReasonDropDownList(TPApp.custId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    private void __getTowCompanyDow() {

        try {
            darTowCompanyDrops = DarTowCompanyDrop._getDarTowCompanyDropList(TPApp.custId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
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
            __saveTowDetails();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void __saveTowDetails() throws IOException {
        TowJson_rpc towJson_rpc = new TowJson_rpc();
        ParamTow paramTow = new ParamTow();
        ArrayList<TowModel> arrayList = new ArrayList<>();
        TowModel towModel = new TowModel();
        towModel.setAppId((int) TowModel.getNextPrimaryId());
        towModel.setUserId(String.valueOf(TPApp.userId));
        towModel.setDeviceid(String.valueOf(TPApp.deviceId));
        towModel.setAssignmentId(asgId);
        towModel.setEquipmentId(TPApp.getEquipment());
        towModel.setSubEquipmentId(TPApp.getEquipmentChild());
        towModel.setTaskId(String.valueOf(taskId));
        towModel.setVdr(TPApp.getVdr());
        towModel.setDisinfecting(TPApp.getDisinfecting());
        towModel.setMileage(TPApp.getMileage());
        towModel.setVehicle(TPApp.getVehicleType());
        towModel.setPdEvent(mPDEvent.getText().toString());
        towModel.setTowCompanyDdElemId(String.valueOf(id));
        towModel.setTowAuthorityDdElemId(String.valueOf(id2));

        if (id1 == 0) {
            towModel.setTowRefusedDdElemId("");
        } else {
            towModel.setTowRefusedDdElemId(String.valueOf(id1));
        }
        towModel.setDateTime(mDate.getText().toString());
        towModel.setDispatchTime(mDispatchTime.getText().toString());
        towModel.setTowArrival(mTowArrival.getText().toString());
        towModel.setLocation(mLocation.getText().toString());
        towModel.setVehicleLicencePlate(mVlPlate.getText().toString());
        towModel.setComments(mNote.getText().toString());
        arrayList.add(towModel);
        paramTow.setDetails(arrayList);
        paramTow.setCustId(TPApp.custId);
        towJson_rpc.setJsonrpc("2.0");
        towJson_rpc.setId("82F85DB43CBF6");
        towJson_rpc.setParams(paramTow);
        towJson_rpc.setMethod("darTowDetailInsert");
        log.info(new Gson().toJson(towJson_rpc));
        System.out.println("RequestObj**" + new Gson().toJson(towJson_rpc));
        if (isNetworkConnected()) {
            // TowModel.insertTowModelTask(towModel);
            progressDialog = ProgressDialog.show(TowSupport.this, "", "Inserting...");
            ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
            api.insertTow(towJson_rpc).enqueue(new Callback<DarTicketResponse>() {
                @Override
                public void onResponse(Call<DarTicketResponse> call, Response<DarTicketResponse> response) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    try {
                        if (response.code() == 200 && response.isSuccessful()) {
                            if (response.body().getResult() != null) {
                                if (response.body().getResult().getResult() == true) {
                                    Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
                                    mPDEvent.setText("");
                                    mDispatchTime.setText("");
                                    mTowArrival.setText("");
                                    mLocation.setText("");
                                    mVlPlate.setText("");
                                    mNote.setText("");
                                    mNo.setChecked(false);
                                    mYes.setChecked(false);
                                    id = 0;
                                    id1 = 0;
                                    id2 = 0;
                                    mDropDown1.setText("");
                                    mDropDown1.setHint(getString(R.string.spinner_tow_company));
                                    mDropDown2.setText("");
                                    mDropDown2.setHint(getString(R.string.spinner_tow_cancelled));
                                    mDropDownAuthority.setText("");
                                    mDropDownAuthority.setHint(getString(R.string.spinner_tow_authority));
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
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error(e.getMessage());
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
            try {
                TowModel.insertTowModelTask(towModel);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
            mPDEvent.setText("");
            mDispatchTime.setText("");
            mTowArrival.setText("");
            mLocation.setText("");
            mVlPlate.setText("");
            mNote.setText("");
            mNo.setChecked(false);
            mYes.setChecked(false);
            id = 0;
            id1 = 0;
            id2 = 0;
            mDropDown1.setText("");
            mDropDown1.setHint(getString(R.string.spinner_tow_company));
            mDropDown2.setText("");
            mDropDown2.setHint(getString(R.string.spinner_tow_cancelled));
            mDropDownAuthority.setText("");
            mDropDownAuthority.setHint(getString(R.string.spinner_tow_authority));
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
        mLocation.setText(TPUtility.getFullAddress(gpsAddress));
    }

    private void findLoc() {
        new GetLocation1(TowSupport.this, this).track();
    }

    public void Failed() {

        new iOSDialogBuilder(TowSupport.this)
                .setSubtitle("Please try again")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();

                    }
                })
                .build().show();

    }

    private void showStartDatePicker(boolean dateRequired) {
        final Calendar cal = Calendar.getInstance();

        final TimePickerDialog.OnTimeSetListener timePicker = (view, hourOfDay, minute) -> {
            pickerDate.setHours(hourOfDay);
            pickerDate.setMinutes(minute);

           /* if (pickerDate.getTime() > new java.util.Date().getTime()) {
                TPUtility.showErrorToast(TowSupport.this, "Ticket date can not be a future date.");

                showStartDatePicker(false);
                return;
            }*/

            mDispatchTime.setText(DateUtil.getStringFromDate(pickerDate));
            //  activeTicket.setTimeMarked(pickerDate);
        };


        final DatePickerDialog.OnDateSetListener datePicker = (view, year, monthOfYear, dayOfMonth) -> {
            pickerDate.setYear(year - 1900);
            pickerDate.setMonth(monthOfYear);
            pickerDate.setDate(dayOfMonth);
            if (Build.VERSION.SDK_INT > 22) {
                new TimePickerDialog(TowSupport.this, android.R.style.Theme_DeviceDefault_Light_NoActionBar, timePicker, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
            } else {
                new TimePickerDialog(TowSupport.this, timePicker, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
            }
        };

        if (pickerDate == null)
            pickerDate = new java.util.Date();

        if (dateRequired) {
            DatePickerDialog datePickerDialog;
            if (android.os.Build.VERSION.SDK_INT > 22) {
                datePickerDialog = new DatePickerDialog(TowSupport.this, android.R.style.Theme_DeviceDefault_Light_NoActionBar, datePicker,
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            } else {
                datePickerDialog = new DatePickerDialog(TowSupport.this, datePicker,
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            }

            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());

            datePickerDialog.show();

        } else {
            if (android.os.Build.VERSION.SDK_INT > 22) {
                new TimePickerDialog(TowSupport.this, android.R.style.Theme_DeviceDefault_Light_NoActionBar, timePicker, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
            } else {
                new TimePickerDialog(TowSupport.this, timePicker, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
            }
        }
    }

    private void showEndDatePicker(boolean dateRequired) {
        final Calendar cal = Calendar.getInstance();

        final TimePickerDialog.OnTimeSetListener timePicker = (view, hourOfDay, minute) -> {
            pickerDate.setHours(hourOfDay);
            pickerDate.setMinutes(minute);

            if (!mDispatchTime.getText().toString().equals("")) {
                Boolean Comapre = compareTime(mDispatchTime.getText().toString(), DateUtil.getStringFromDate(pickerDate));
                if (Comapre) {
                    mTowArrival.setText(DateUtil.getStringFromDate(pickerDate));
                } else {
                    FailedTime();
                    //Toast.makeText(TowSupport.this, "Stop time must be greater than Start time", Toast.LENGTH_LONG);
                }
            }

          //  mTowArrival.setText(DateUtil.getStringFromDate(pickerDate));
            //  activeTicket.setTimeMarked(pickerDate);
        };


        final DatePickerDialog.OnDateSetListener datePicker = (view, year, monthOfYear, dayOfMonth) -> {
            pickerDate.setYear(year - 1900);
            pickerDate.setMonth(monthOfYear);
            pickerDate.setDate(dayOfMonth);
            if (Build.VERSION.SDK_INT > 22) {
                new TimePickerDialog(TowSupport.this, android.R.style.Theme_DeviceDefault_Light_NoActionBar, timePicker, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
            } else {
                new TimePickerDialog(TowSupport.this, timePicker, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
            }
        };

        if (pickerDate == null)
            pickerDate = new java.util.Date();

        if (dateRequired) {
            DatePickerDialog datePickerDialog;
            if (android.os.Build.VERSION.SDK_INT > 22) {
                datePickerDialog = new DatePickerDialog(TowSupport.this, android.R.style.Theme_DeviceDefault_Light_NoActionBar, datePicker,
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            } else {
                datePickerDialog = new DatePickerDialog(TowSupport.this, datePicker,
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            }

            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());

            datePickerDialog.show();

        } else {
            if (android.os.Build.VERSION.SDK_INT > 22) {
                new TimePickerDialog(TowSupport.this, android.R.style.Theme_DeviceDefault_Light_NoActionBar, timePicker, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
            } else {
                new TimePickerDialog(TowSupport.this, timePicker, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
            }
        }
    }

    private boolean compareTime(String time, String endtime) {

        String pattern = "MM/dd/yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if (date2.after(date1)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void FailedTime() {

        new iOSDialogBuilder(TowSupport.this)
                .setSubtitle("Stop time must be greater than Start time")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();

                    }
                })
                .build().show();

    }

}



        /* final DarTicketResult result = response.body().getResult();
                            final List<Integer> appId = result.getAppId();
                            if (appId != null && appId.size() > 0) {
                                for (int i = 0; i < appId.size(); i++) {
                                    try {
                                        TowModel.removeById(appId.get(i).intValue());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
                            mPDEvent.setText("");
                            mDispatchTime.setText("");
                            mTowArrival.setText("");
                            mLocation.setText("");
                            mVlPlate.setText("");
                            mNote.setText("");
                            mNo.setChecked(false);
                            mYes.setChecked(false);
                            id=0;
                            id1=0;
                            id2=0;
                            mDropDown1.setText("");
                            mDropDown1.setHint("Tow Company");
                            mDropDown2.setText("");
                            mDropDown2.setHint("Reason tow was cancelled");*/