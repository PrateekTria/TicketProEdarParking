package com.ticketpro.parking.dar;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.gson.Gson;
import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGenerator;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.DarInsertMieageRequest;
import com.ticketpro.parking.dar.model.Mileage;
import com.ticketpro.parking.dar.model.params.ParamMileage;
import com.ticketpro.parking.dar.model.request.MileageJson_rpc;
import com.ticketpro.util.DateUtil;
import com.ticketpro.util.Preference;

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

public class ScheduleShift extends BaseActivityImpl {
    private TextView starttime_txtview, endtime_txtview;
    Button btn_back;
    Button btn_next;
    RadioGroup shiftRadio;
    String shiftType="";
    LinearLayout note_linearlayout;
    EditText et_NoteOvertime;
    private ProgressDialog progressDialog;
    Preference preference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_shift);
        setLogger(ScheduleShift.class.getName());
        preference = Preference.getInstance(ScheduleShift.this);
        starttime_txtview = findViewById(R.id.starttime_txt);
        endtime_txtview = findViewById(R.id.endtime_txt);
        btn_back = findViewById(R.id.shift_back_btn);
        btn_next = findViewById(R.id.shift_btnNext);
        shiftRadio=findViewById(R.id.shift_type);
        note_linearlayout=findViewById(R.id.linear_note);
        et_NoteOvertime=findViewById(R.id.overtime_note);

        TPApp.setShiftStartTime(starttime_txtview.getText().toString());
        TPApp.setShiftEndTime(endtime_txtview.getText().toString());
        TPApp.setShiftType(shiftType);

        starttime_txtview.setText(DateUtil.getCurrentDateTime1());
        endtime_txtview.setText(DateUtil.getCurrentDateTime1());

        starttime_txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startdateDialog();
            }
        });

        shiftRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.shift_regular)
                {
                    shiftType="Regular";
                    note_linearlayout.setVisibility(View.GONE);
                } else if (i==R.id.shift_overtime) {
                    shiftType="Overtime";
                    note_linearlayout.setVisibility(View.VISIBLE);

                }
                else {
                   shiftType="";
                }


            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shiftType!="" || !shiftType.isEmpty())

                {
                    if (shiftType.equals("Overtime") && et_NoteOvertime.getText().toString().equals("") )
                    {
                        enptyNoteDialog();
                    }else{

                        /*TPApp.setShiftStartTime(starttime_txtview.getText().toString());
                        TPApp.setShiftEndTime(endtime_txtview.getText().toString());
                        TPApp.setShiftType(shiftType);
                        Intent i = new Intent(ScheduleShift.this,DarAssignmentActivity.class);
                        startActivity(i);*/
                        try {
                            if (!et_NoteOvertime.getText().toString().equals(""))
                            {
                                TPApp.setOvertime_Note(et_NoteOvertime.getText().toString());
                            }else{
                                TPApp.setOvertime_Note("");
                            }
                            if (TPApplication.getInstance().getVehicleString().trim().equals("NV")){
                                InsertMileage();
                            }else {
                                TPApp.setShiftStartTime(starttime_txtview.getText().toString());
                                TPApp.setShiftEndTime(endtime_txtview.getText().toString());
                                TPApp.setShiftType(shiftType);
                                Intent i = new Intent(ScheduleShift.this,DarAssignmentActivity.class);
                                startActivity(i);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }else {
                       nonSelection();
                }

            }
        });

        endtime_txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!starttime_txtview.getText().toString().equals("")) {
                    enddateDialog();
                } else {
                    Toast.makeText(ScheduleShift.this, "Please select start time first", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    public void startdateDialog() {
        final Calendar calenderInstance = Calendar.getInstance();
        int hr = calenderInstance.get(Calendar.HOUR_OF_DAY);
        int min = calenderInstance.get(Calendar.MINUTE);
        TimePickerDialog.OnTimeSetListener onTimeListner = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    String min = String.valueOf(minute);
                    String hour_count=String.valueOf(hourOfDay);
                    calenderInstance.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calenderInstance.set(Calendar.MINUTE, minute);
                    int day_months = calenderInstance.get(Calendar.DAY_OF_MONTH);
                    int month = calenderInstance.get(Calendar.MONTH);
                    int year = calenderInstance.get(Calendar.YEAR);
                    if (String.valueOf(minute).length() == 1) {
                        min = "0" + minute;
                    }
                    if (String.valueOf(hourOfDay).length()==1)
                    {
                        hour_count="0" + hourOfDay;
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                    Date date = new Date(day_months, month, day_months);
                    String dateformate = sdf.format(date);
                    String starttime=(year + "-" + dateformate + " " + hour_count + ":" + min + ":" + "00");
                  //  starttime_txtview.setText(year + "-" + dateformate + " " + String.valueOf(hourOfDay) + ":" + String.valueOf(min) + ":" + "00");
                    try {

                        if (!starttime.equals("") || !starttime.isEmpty())
                        {
                            boolean timestatus=checkTimings(starttime,endtime_txtview.getText().toString());
                            if (timestatus==true)
                            {
                                starttime_txtview.setText(year+"-"+dateformate+" "+hour_count+":"+min+":"+"00");
                            }
                            else
                            {   /*StartTimeET.setText(previoustartT);
                                StartTimeET.setError("");
                                Dialog();*/
                                unequality();
                               // Toast.makeText(ScheduleShift.this,"End time must be greater than start time",Toast.LENGTH_SHORT).show();

                            }
                        }

                    }catch (Exception e)
                    {

                    }
                }

                Log.d("timee", String.valueOf(hourOfDay) + String.valueOf(minute));
            }

        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(ScheduleShift.this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                onTimeListner, hr, min, false);
        timePickerDialog.setTitle("Start Time");
        Objects.requireNonNull(timePickerDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    public void enddateDialog() {
        final Calendar calenderInstance = Calendar.getInstance();
        int hr = calenderInstance.get(Calendar.HOUR_OF_DAY);
        int min = calenderInstance.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener onTimeListner = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    String min = String.valueOf(minute);
                    String hour_count=String.valueOf(hourOfDay);
                    calenderInstance.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calenderInstance.set(Calendar.MINUTE, minute);
                    if (hourOfDay<hr)
                    {
                        calenderInstance.add(Calendar.DATE,1);
                    }
                    if (String.valueOf(hourOfDay).length()==1)
                    {
                        hour_count="0" + hourOfDay;
                    }
                    int day_months = calenderInstance.get(Calendar.DAY_OF_MONTH);
                    int month = calenderInstance.get(Calendar.MONTH);
                    int year = calenderInstance.get(Calendar.YEAR);
                    if (String.valueOf(minute).length() == 1) {
                        min = "0" + minute;
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                    Date date = new Date(day_months, month, day_months);
                    String dateformate = sdf.format(date);
                    String enddatetime=year + "-" + dateformate + " " + hour_count + ":" +min + ":" + "00";
                    //endtime_txtview.setText(year + "-" + dateformate + " " + String.valueOf(hourOfDay) + ":" + String.valueOf(min) + ":" + "00");
                    /*try {

                        if (!EndTimeET.getText().toString().isEmpty())
                        {
                            boolean timestatus=checktimings(StartTimeET.getText().toString(),EndTimeET.getText().toString());
                            if (timestatus==true)
                            {
                                StartTimeET.setText(year+"-"+dateformate+" "+String.valueOf(hourOfDay)+":"+String.valueOf(min)+":"+"00");
                            }
                            else
                            {   StartTimeET.setText(previoustartT);
                                *//*StartTimeET.setError("");*//*
                                Dialog();
                            }
                        }

                    }catch (Exception e)
                    {

                    }

*/
                    try {
                        if (!enddatetime.equals("")){
                            boolean timestatus=checkTimings(starttime_txtview.getText().toString(),enddatetime);
                            if (timestatus==true)
                            {
                                endtime_txtview.setText(year+"-"+dateformate+" "+hour_count+":"+min+":"+"00");
                            }
                            else
                            {
                                unequality();
                               // Toast.makeText(ScheduleShift.this,"End time must be greater than start time",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch (Exception e)
                    {

                    }
                }

                Log.d("timee", String.valueOf(hourOfDay) + String.valueOf(minute));
            }

        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(ScheduleShift.this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                onTimeListner, hr, min, false);
        timePickerDialog.setTitle("End Time");
        Objects.requireNonNull(timePickerDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
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

    public void nonSelection() {

        new iOSDialogBuilder(ScheduleShift.this)
                .setSubtitle("Please select shift type")
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
                /* .setNegativeListener("No", new iOSDialogClickListener() {
                     @Override
                     public void onClick(iOSDialog dialog) {
                         dialog.dismiss();
                     }
                 })*/
                .build().show();
        //new ProxyImpl().updateDarTable();
    }

    public void unequality() {

        new iOSDialogBuilder(ScheduleShift.this)
                .setSubtitle("End time must be later than the start time.")
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
                /* .setNegativeListener("No", new iOSDialogClickListener() {
                     @Override
                     public void onClick(iOSDialog dialog) {
                         dialog.dismiss();
                     }
                 })*/
                .build().show();
        //new ProxyImpl().updateDarTable();
    }

    public void enptyNoteDialog() {

        new iOSDialogBuilder(ScheduleShift.this)
                .setSubtitle("Please enter note")
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
                /* .setNegativeListener("No", new iOSDialogClickListener() {
                     @Override
                     public void onClick(iOSDialog dialog) {
                         dialog.dismiss();
                     }
                 })*/
                .build().show();
        //new ProxyImpl().updateDarTable();
    }


    public void InsertMileage() throws IOException {
        String vehilceNumber= TPApplication.getInstance().getVehicleNumberString();
        progressDialog = ProgressDialog.show(ScheduleShift.this, "", "Please wait...");
        MileageJson_rpc jsonRpc = new MileageJson_rpc();
        List<Mileage> aList = new ArrayList<>();
        ParamMileage param = new ParamMileage();

        Mileage details = new Mileage();
        details.setUserId(TPApp.userId);
        details.setStartMileage("0000");
        details.setDeviceid(TPApp.custId);
        details.setVehicleNumber(vehilceNumber);
        details.setVehicleId(TPApp.getVehicleid());
        //details.setActionId("1");
        aList.add(details);
        param.setCustId(TPApp.custId);
        param.setDetails(aList);
        param.setIsOverride("Y");
        jsonRpc.setJsonrpc("2.o");
        jsonRpc.setMethod("insertMileage");
        jsonRpc.setId("82F85DB43CBF6");
        jsonRpc.setParams(param);
        System.out.println("RequestObj**" + new Gson().toJson(jsonRpc));

        if (isNetworkConnected()) {

            //  Mileage.insertMileage(details);
            ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
            api.insertMileage(jsonRpc).enqueue(new Callback<DarInsertMieageRequest>() {
                @Override
                public void onResponse(Call<DarInsertMieageRequest> call, Response<DarInsertMieageRequest> response) {
                    log.info("RequestObj*" + new Gson().toJson(jsonRpc).toString());
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();

                    }

                    try {
                        if (response.isSuccessful() && response.code() == 200 && Objects.requireNonNull(response.body()).getResult().getResult()) {



                            // editor.putString("coulmunId", response.body().getResult().getAppId().get(0).toString());
                            preference.putString("coulmunId", response.body().getResult().getAppId().get(0).toString());
                            if (!response.body().getResult().getEndMileage().get(0).isEmpty()) {
                                TPApp.setMileage("0000");
                                preference.putString("EndMileage", response.body().getResult().getEndMileage().get(0));
                            } else {
                                preference.putString("EndMileage", "");
                                TPApp.setMileage("0000");
                            }

                            TPApp.setShiftStartTime(starttime_txtview.getText().toString());
                            TPApp.setShiftEndTime(endtime_txtview.getText().toString());
                            TPApp.setShiftType(shiftType);
                            Intent i = new Intent(ScheduleShift.this,DarAssignmentActivity.class);
                            startActivity(i);

                        }  else {
                            FailedDialog();
                            //Toast.makeText(DarVehicleListAndTask.this, "Please Insert Right Mileage", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception exc)
                    {
                        exc.printStackTrace();
                        log.error(exc.getMessage());
                        FailedDialog();
                    }

                }

                @Override
                public void onFailure(Call<DarInsertMieageRequest> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.d("Error", t.getMessage());
                    log.error(t.getMessage());
                    FailedDialog();
                }
            });

        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            CheckConnectivityDialog();
           /* //   Mileage.insertMileage(details);
            Intent intent = new Intent();
            intent.setClass(this, HomeActivity.class);
            startActivity(intent);
            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();*/

        }
    }

    public void CheckConnectivityDialog() {

        new iOSDialogBuilder(ScheduleShift.this)
                .setSubtitle("Please check your Internet Connection")
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
                /* .setNegativeListener("No", new iOSDialogClickListener() {
                     @Override
                     public void onClick(iOSDialog dialog) {
                         dialog.dismiss();
                     }
                 })*/
                .build().show();
        //new ProxyImpl().updateDarTable();
    }

    public void FailedDialog() {

        new iOSDialogBuilder(ScheduleShift.this)
                .setSubtitle("Service not responding.Please try again.")
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
                /* .setNegativeListener("No", new iOSDialogClickListener() {
                     @Override
                     public void onClick(iOSDialog dialog) {
                         dialog.dismiss();
                     }
                 })*/
                .build().show();
        //new ProxyImpl().updateDarTable();
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void bindDataAtLoadingTime() throws Exception {

    }

    @Override
    public void handleVoiceInput(String text) throws Exception {

    }

    @Override
    public void handleVoiceMode(boolean voiceMode) {

    }

    @Override
    public void handleNetworkStatus(boolean connected, boolean isFastConnection) {

    }
}