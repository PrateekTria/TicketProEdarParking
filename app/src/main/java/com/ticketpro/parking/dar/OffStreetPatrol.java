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
import com.ticketpro.model.ChalkVehicle;
import com.ticketpro.model.Ticket;
import com.ticketpro.model.TicketResponse;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.ChalkVehicleActivity;
import com.ticketpro.parking.activity.HomeActivity;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.activity.WriteTicketActivity;
import com.ticketpro.parking.dar.model.DarAssignmentLocationJob;
import com.ticketpro.parking.dar.model.DarDutyReportJob;
import com.ticketpro.parking.dar.model.DarOffStreetPatrolDropDown;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.DarTicketResult;
import com.ticketpro.parking.dar.model.FieldContactDetails;
import com.ticketpro.parking.dar.model.OffStreetModel;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.params.ParamOffStreet;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.request.OffStreetJson_rpc;
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

public class OffStreetPatrol extends BaseActivityImpl implements Validator.ValidationListener{
    private Button _btnBack, _btnSave, _btnEndShift, _btnLogOut;
    private TextView _spinnerText;
    private int asgId = 0;
    private int locId = 0;
    private int taskId = 0;

    ArrayList<DarOffStreetPatrolDropDown> darOffStreetPatrolDropDowns;
    private Integer id;
    private ProgressDialog progressDialog;
    private Button _writeTicket;
    private Button _chalkVehicle;
    private Button _off_Back;
    SharedPreferences sharedPreferences;
    private Preference preference;
    private String inTime = null;
    DarAssignmentLocationJob darAssignmentLocationJob= new DarAssignmentLocationJob();
    DarDutyReportJob darDutyReportJob= new DarDutyReportJob();
    DarSJPDlogoutJob darSJPDlogoutJob = new DarSJPDlogoutJob();

    @NotEmpty
    private EditText mDistrict;
    @NotEmpty
    private  EditText _note;

    private Validator validator;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off_street_patrol);
        setLogger(OffStreetPatrol.class.getName());
        validator = new Validator(this);
        validator.setValidationListener(this);
        _off_Back = findViewById(R.id.ofs_Back);
        mDistrict = findViewById(R.id.et_district);
        mDistrict.setFocusable(true);
        _writeTicket = findViewById(R.id.f22_write_ticket);
        _chalkVehicle = findViewById(R.id.f22_chalk);
        _note=findViewById(R.id.offstreetpatrol_notes);
        /*_note.setImeOptions(EditorInfo.IME_ACTION_DONE);
        _note.setRawInputType(InputType.TYPE_CLASS_TEXT);*/
        preference = Preference.getInstance(OffStreetPatrol.this);
        inTime = preference.getString("IN_DATE");
        sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
      /*  _note.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        _note.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});*/
        _off_Back.setOnClickListener(v -> {
            try {
                Logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
           /* Intent serviceIntent = new Intent(OffStreetPatrol.this, EDarServiceJobIntentTask.class);
            serviceIntent.putExtra("START","");
            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
            serviceIntent.putExtra("ID",String.valueOf(locId));
            serviceIntent.putExtra("WITCH","Three");
            serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
            EDarServiceJobIntentTask.enqueueWork(OffStreetPatrol.this, serviceIntent);
            TPApplication.getInstance().setDarStartTimeTask(null);

            finish();*/
        });
        Intent it = getIntent();
        asgId = it.getIntExtra("ASG_ID", 0);
        locId = it.getIntExtra("LOCATION_ID", 0);
        taskId = it.getIntExtra("TASK_ID", 0);
       /* _btnBack.setOnClickListener(v -> finish());*/
        _btnSave = findViewById(R.id.ofs_btn_save);
        _btnEndShift = findViewById(R.id.ofs_btnEndShift);
        _btnLogOut = findViewById(R.id.ofs_btn_logout);
        _spinnerText = findViewById(R.id.ofs_activity_spinner);

        _chalkVehicle.setOnClickListener(v -> {
            ChalkVehicle chalk = TPApp.createNewChalk();
            TPApp.setActiveChalk(chalk);
            Intent i = new Intent();
            i.setClass(OffStreetPatrol.this, ChalkVehicleActivity.class);
            startActivity(i);
        });
        _writeTicket.setOnClickListener(v -> {
            Ticket ticket = TPApp.createNewTicket();
            TPApp.setActiveTicket(ticket);
            Intent i = new Intent();
            i.setClass(OffStreetPatrol.this, WriteTicketActivity.class);
            startActivity(i);
        });

        _btnEndShift.setOnClickListener(v -> {

           /* Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntent.class);
            if (isNetworkConnected()) {
                EDarServiceJobIntent.enqueueWork(TaskForm22500.this, serviceIntent);
            }*/

            new iOSDialogBuilder(OffStreetPatrol.this)
                    .setSubtitle("SJPD Radio logoff \n Performed?")
                    .setPositiveListener("Yes", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {

                         /*   Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                            intent.setClass(OffStreetPatrol.this, HomeActivity.class);
                            startActivity(intent);*/
                            darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(),OffStreetPatrol.this,TPApplication.getInstance().getDarParkingdutyreportId());
                            darAssignmentLocationJob.callAssignLocationAPI("",DateUtil.getCurrentDateTime1(),String.valueOf(asgId),TPApplication.getInstance().getAssignReportId(),TPApplication.getInstance().getAssLocRecId(),OffStreetPatrol.this);
                            darSJPDlogoutJob._sjpdlogout(OffStreetPatrol.this);
                            Intent serviceIntent = new Intent(OffStreetPatrol.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START","");
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID",String.valueOf(locId));
                            serviceIntent.putExtra("WITCH","Three");
                            serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
                            EDarServiceJobIntentTask.enqueueWork(OffStreetPatrol.this, serviceIntent);

                            TPApplication.getInstance().setDarStartTimeTask(null);
                          /*  Intent serviceIntent1 = new Intent(OffStreetPatrol.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(asgId));
                            serviceIntent.putExtra("WITCH", "Two");
                            EDarServiceJobIntentTask.enqueueWork(OffStreetPatrol.this, serviceIntent1);*/

                            dialog.dismiss();
                            String  Id_colunn=preference.getString("coulmunId");
                            String  Start_mileage= TPApp.getMileage();

                            MilageDialog milageDialog= new MilageDialog();

                            try {
                                if (isNetworkConnected())
                                {
                                    milageDialog.EndMilageDialogShift(OffStreetPatrol.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                }
                                else
                                {
                                    milageDialog.EndMilageDialogShift(OffStreetPatrol.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
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



                new iOSDialogBuilder(OffStreetPatrol.this)
                        .setSubtitle("SJPD Radio logoff \n Performed?")
                        .setPositiveListener("Yes", new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                               /* Intent intent = new Intent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                                intent.setClass(OffStreetPatrol.this, DarEquipmentNew.class);
                                startActivity(intent);*/
                                darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(),OffStreetPatrol.this,TPApplication.getInstance().getDarParkingdutyreportId());
                                darAssignmentLocationJob.callAssignLocationAPI("",DateUtil.getCurrentDateTime1(),String.valueOf(asgId),TPApplication.getInstance().getAssignReportId(),TPApplication.getInstance().getAssLocRecId(),OffStreetPatrol.this);
                                darSJPDlogoutJob._sjpdlogout(OffStreetPatrol.this);

                                Intent serviceIntent = new Intent(OffStreetPatrol.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START","");
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID",String.valueOf(locId));
                                serviceIntent.putExtra("WITCH","Three");
                                serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
                                EDarServiceJobIntentTask.enqueueWork(OffStreetPatrol.this, serviceIntent);

                                TPApplication.getInstance().setDarStartTimeTask(null);
//                                Intent serviceIntent1 = new Intent(OffStreetPatrol.this, EDarServiceJobIntentTask.class);
//                                serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
//                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
//                                serviceIntent.putExtra("ID", String.valueOf(asgId));
//                                serviceIntent.putExtra("WITCH", "Two");
//                                EDarServiceJobIntentTask.enqueueWork(OffStreetPatrol.this, serviceIntent1);

                                dialog.dismiss();
                           //     finish();

                                String  Id_colunn=preference.getString("coulmunId");
                                String  Start_mileage= TPApp.getMileage();

                                MilageDialog milageDialog= new MilageDialog();

                                try {
                                    if (isNetworkConnected())
                                    {
                                        milageDialog.EndMilageDialogLogout(OffStreetPatrol.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                    }
                                    else
                                    {
                                        milageDialog.EndMilageDialogLogout(OffStreetPatrol.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
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
        darOffStreetPatrolDropDowns = DarOffStreetPatrolDropDown._getDarOffStreetPatrolDropDownList(TPApp.custId);
        _spinnerText.setOnClickListener(v -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(OffStreetPatrol.this);
            //builderSingle.setIcon(R.drawable.icon);
            builderSingle.setTitle("Select Activity");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(OffStreetPatrol.this, android.R.layout.select_dialog_singlechoice);
            for (int i = 0; i < darOffStreetPatrolDropDowns.size(); i++) {
                final DarOffStreetPatrolDropDown dropdown = darOffStreetPatrolDropDowns.get(i);
                String s = dropdown.getMenuName();
                arrayAdapter.add(s);
            }
            builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                _spinnerText.setText(arrayAdapter.getItem(which).toString());
                darOffStreetPatrolDropDowns.forEach((n) -> {
                    if (n.getMenuName().equals(arrayAdapter.getItem(which)))
                        id = n.getId();
                });
                dialog.dismiss();
            });
            builderSingle.show();
        });

        _btnSave.setOnClickListener(v -> validator.validate());
    }

    private void __saveDarPPZ() throws IOException {
        progressDialog = ProgressDialog.show(OffStreetPatrol.this, "", "Inserting...");

        OffStreetJson_rpc rpc = new OffStreetJson_rpc();
        ParamOffStreet paramPPZ = new ParamOffStreet();
        List<OffStreetModel> aList = new ArrayList<>();

        OffStreetModel model = new OffStreetModel();
        model.setOffStreetPatrolDdElemId(mDistrict.getText().toString());
        model.setUserId(String.valueOf(TPApp.userId));
        model.setDeviceid(String.valueOf(TPApp.deviceId));
        if (!preference.getString("coulmunId").isEmpty() || !preference.getString("coulmunId").equals(""))
            model.setMileageId(preference.getString("coulmunId"));
        else
            model.setMileageId("0");
        model.setEquipmentId(TPApp.getEquipment());
        model.setSubEquipmentId(TPApp.getEquipmentChild());
        model.setVehicle(TPApp.getVehicleType());
        model.setMileage(TPApp.getMileage());
        model.setDisinfecting(TPApp.getDisinfecting());
        model.setVdr(TPApp.getVdr());
        model.setTaskDate(DateUtil.getCurrentDateTime1());

        if (TPApplication.getInstance().getDarTaskReoprtId()!=null)
        {
            model.setDarTaskReportId(TPApplication.getInstance().getDarTaskReoprtId());
        }
        else
        {
            model.setDarTaskReportId("");
        }
        model.setNotes(_note.getText().toString());
        model.setAssignmentId(asgId);
        model.setLocationId(String.valueOf(locId));
        model.setTaskId(String.valueOf(taskId));
        model.setActionId("0");
        model.setAppId((int) OffStreetModel.getNextPrimaryId());
        aList.add(model);
        paramPPZ.setDetails(aList);
        paramPPZ.setCustId(TPApp.custId);
        rpc.setParams(paramPPZ);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("darOffStreetPatrolDetailInsert");
     /*   System.out.println("RequestObj**"+new Gson().toJson(rpc));*/
      //  OffStreetModel.insertOffStreetModel(model);
        log.info(new Gson().toJson(rpc));

        if (isNetworkConnected()){
            try {
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertOffStreetPatrol(rpc).enqueue(new Callback<DarTicketResponse>() {
                    @Override
                    public void onResponse(Call<DarTicketResponse> call, Response<DarTicketResponse> response) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        //System.out.println(new Gson().toJson(response.raw().request().body()));


                            if (response.code() == 200 && response.isSuccessful()) {

                                if (response.body().getResult() != null)
                                {
                                    if(response.body().getResult().getResult()== true)
                                    {
                                        mDistrict.setText("");
                                        _note.setText("");
                                        Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
                                    }else
                                    {
                                        Failed();
                                        log.error("response incorrect");
                                    }
                                }else {
                                    Failed();
                                    log.error("response incorrect");
                                }


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
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                e.printStackTrace();
                log.error(e.getMessage());
                Failed();
            }
        }else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            OffStreetModel.insertOffStreetModel(model);
            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
            _note.setText("");
            mDistrict.setText("");

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
            __saveDarPPZ();
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

   /*     Intent serviceIntent = new Intent(OffStreetPatrol.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START","");
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID",String.valueOf(locId));
        serviceIntent.putExtra("WITCH","Three");
        serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
        EDarServiceJobIntentTask.enqueueWork(OffStreetPatrol.this, serviceIntent);
        TPApplication.getInstance().setDarStartTimeTask(null);

        finish();
        super.onBackPressed();*/
    }

    public void Logout() throws IOException {
        callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");

      /*  Intent serviceIntent = new Intent(OffStreetPatrol.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START","");
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID",String.valueOf(locId));
        serviceIntent.putExtra("WITCH","Three");
        serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
        EDarServiceJobIntentTask.enqueueWork(OffStreetPatrol.this, serviceIntent);
        TPApplication.getInstance().setDarStartTimeTask(null);

        finish();*/
    }

    public void callTaskReport(String startTime, String endTime, String id, String TaskRecordId, String taskid) throws IOException {
        progressDialog = ProgressDialog.show(OffStreetPatrol.this, "", "Please Wait...");
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

        new iOSDialogBuilder(OffStreetPatrol.this)
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
                            if (appId !=null && appId.size()>0) {
                                for (int i = 0; i < appId.size(); i++) {
                                    try {
                                        OffStreetModel.removeById(appId.get(i).intValue());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }*/

}