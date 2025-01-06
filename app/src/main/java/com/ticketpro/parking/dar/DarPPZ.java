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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.gson.Gson;
import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGenerator;
import com.ticketpro.model.TicketResponse;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.HomeActivity;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.DarAssignmentLocationJob;
import com.ticketpro.parking.dar.model.DarDutyReportJob;
import com.ticketpro.parking.dar.model.DarPPZDropdown;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.DarTicketResult;
import com.ticketpro.parking.dar.model.FieldContactDetails;
import com.ticketpro.parking.dar.model.PPZModel;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.params.ParamPPZ;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.request.PPZJson_rpc;
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

public class DarPPZ extends BaseActivityImpl {
    private Button _btnBack, _btnSave, _btnEndShift, _btnLogOut;
    private TextView _spinnerText;
    private int asgId = 0;
    private int locId = 0;
    private int taskId = 0;
    ArrayList<DarPPZDropdown> darPPZDropdowns;
    private Integer id = 0;
    private ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    private Preference preference;
    private String inTime = null;
    DarAssignmentLocationJob darAssignmentLocationJob= new DarAssignmentLocationJob();
    DarDutyReportJob darDutyReportJob= new DarDutyReportJob();
    DarSJPDlogoutJob darSJPDlogoutJob =new DarSJPDlogoutJob();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dar_ppz);
        setLogger(DarPPZ.class.getName());
        TPApplication.getInstance().setDarStartTimeTask(DateUtil.getCurrentDateTime1());
        Intent it = getIntent();
        asgId = it.getIntExtra("ASG_ID", 0);
        locId = it.getIntExtra("LOCATION_ID", 0);
        taskId = it.getIntExtra("TASK_ID", 0);
        preference = Preference.getInstance(DarPPZ.this);
        inTime = preference.getString("IN_DATE");
        sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        _btnBack = findViewById(R.id.ppz_btnBack);
        _btnBack.setOnClickListener(v -> {
            try {
                callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
            } catch (IOException e) {
                e.printStackTrace();
            }

           /* Intent serviceIntent = new Intent(DarPPZ.this, EDarServiceJobIntentTask.class);
            serviceIntent.putExtra("START","");
            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
            serviceIntent.putExtra("ID",String.valueOf(locId));
            serviceIntent.putExtra("WITCH","Three");
            serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
            EDarServiceJobIntentTask.enqueueWork(DarPPZ.this, serviceIntent);
            TPApplication.getInstance().setDarStartTimeTask(null);

            finish();*/
        });
        _btnSave = findViewById(R.id.ppz_btn_save);
        _btnEndShift = findViewById(R.id.ppz_btnEndShift);
        _btnLogOut = findViewById(R.id.ppz_btn_logout);
        _spinnerText = findViewById(R.id.ppz_activity_spinner);
        darPPZDropdowns = DarPPZDropdown._getDarSchoolDropDownElementList(TPApp.custId);
        _spinnerText.setOnClickListener(v -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(DarPPZ.this);
            //builderSingle.setIcon(R.drawable.icon);
            builderSingle.setTitle("Select Activity");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(DarPPZ.this, android.R.layout.select_dialog_singlechoice);
            for (int i = 0; i < darPPZDropdowns.size(); i++) {
                final DarPPZDropdown dropdown = darPPZDropdowns.get(i);
                String s = dropdown.getMenuName();
                arrayAdapter.add(s);
            }
            builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                _spinnerText.setText(arrayAdapter.getItem(which).toString());
                darPPZDropdowns.forEach((n) -> {
                    if (n.getMenuName().equals(arrayAdapter.getItem(which)))
                        id = n.getId();
                });
                dialog.dismiss();
            });
            builderSingle.show();
        });
        _btnEndShift.setOnClickListener(v -> {
           /* Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntent.class);
            if (isNetworkConnected()) {
                EDarServiceJobIntent.enqueueWork(TaskForm22500.this, serviceIntent);
            }*/


            new iOSDialogBuilder(DarPPZ.this)
                    .setSubtitle("SJPD Radio logoff \n Performed?")
                    .setPositiveListener("Yes", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {

                           /* Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                            intent.setClass(DarPPZ.this, HomeActivity.class);
                            startActivity(intent);*/
                            darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(),DarPPZ.this,TPApplication.getInstance().getDarParkingdutyreportId());
                            darAssignmentLocationJob.callAssignLocationAPI("",DateUtil.getCurrentDateTime1(),String.valueOf(asgId),TPApplication.getInstance().getAssignReportId(),TPApplication.getInstance().getAssLocRecId(),DarPPZ.this);
                            darSJPDlogoutJob._sjpdlogout(DarPPZ.this);

                            Intent serviceIntent = new Intent(DarPPZ.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START","");
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID",String.valueOf(locId));
                            serviceIntent.putExtra("WITCH","Three");
                            serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
                            EDarServiceJobIntentTask.enqueueWork(DarPPZ.this, serviceIntent);

                            TPApplication.getInstance().setDarStartTimeTask(null);
                           /* Intent serviceIntent1 = new Intent(DarPPZ.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(asgId));
                            serviceIntent.putExtra("WITCH", "Two");
                            EDarServiceJobIntentTask.enqueueWork(DarPPZ.this, serviceIntent1);*/

                            dialog.dismiss();

                            String  Id_colunn=preference.getString("coulmunId");
                            String  Start_mileage= TPApp.getMileage();

                            MilageDialog milageDialog= new MilageDialog();

                            try {
                                if (isNetworkConnected())
                                {
                                    milageDialog.EndMilageDialogShift(DarPPZ.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                }
                                else
                                {
                                    milageDialog.EndMilageDialogShift(DarPPZ.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
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
        _btnLogOut.setOnClickListener(v -> {
            try {





                new iOSDialogBuilder(DarPPZ.this)
                        .setSubtitle("SJPD Radio logoff \n Performed?")
                        .setPositiveListener("Yes", new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                               /* Intent intent = new Intent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                                intent.setClass(DarPPZ.this, DarEquipmentNew.class);
                                startActivity(intent);*/
                                darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(),DarPPZ.this,TPApplication.getInstance().getDarParkingdutyreportId());
                                darAssignmentLocationJob.callAssignLocationAPI("",DateUtil.getCurrentDateTime1(),String.valueOf(asgId),TPApplication.getInstance().getAssignReportId(),TPApplication.getInstance().getAssLocRecId(),DarPPZ.this);
                                darSJPDlogoutJob._sjpdlogout(DarPPZ.this);

                                Intent serviceIntent = new Intent(DarPPZ.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START","");
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID",String.valueOf(locId));
                                serviceIntent.putExtra("WITCH","Three");
                                serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
                                EDarServiceJobIntentTask.enqueueWork(DarPPZ.this, serviceIntent);

                                TPApplication.getInstance().setDarStartTimeTask(null);
                               /* Intent serviceIntent1 = new Intent(DarPPZ.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", String.valueOf(asgId));
                                serviceIntent.putExtra("WITCH", "Two");
                                EDarServiceJobIntentTask.enqueueWork(DarPPZ.this, serviceIntent1);*/

                                dialog.dismiss();
                               // finish();
                                String  Id_colunn=preference.getString("coulmunId");
                                String  Start_mileage= TPApp.getMileage();

                                MilageDialog milageDialog= new MilageDialog();

                                try {
                                    if (isNetworkConnected())
                                    {
                                        milageDialog.EndMilageDialogLogout(DarPPZ.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                    }
                                    else
                                    {
                                        milageDialog.EndMilageDialogLogout(DarPPZ.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
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
        _btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id > 0) {
                    try {
                        __saveDarPPZ();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getInstance(), "Select Activity", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void __saveDarPPZ() throws IOException {
        progressDialog = ProgressDialog.show(DarPPZ.this, "", "Inserting...");

        PPZJson_rpc rpc = new PPZJson_rpc();
        ParamPPZ paramPPZ = new ParamPPZ();
        List<PPZModel> aList = new ArrayList<>();

        PPZModel model = new PPZModel();
        model.setDdItem(String.valueOf(id));
        model.setUserId(String.valueOf(TPApp.userId));
        model.setDeviceid(String.valueOf(TPApp.deviceId));

        model.setEquipmentId(TPApp.getEquipment());
        model.setSubEquipmentId(TPApp.getEquipmentChild());
        model.setVehicle(TPApp.getVehicleType());
        model.setMileage(TPApp.getMileage());
        model.setDisinfecting(TPApp.getDisinfecting());
        model.setVdr(TPApp.getVdr());

        if (!preference.getString("coulmunId").isEmpty() || !preference.getString("coulmunId").equals(""))
            model.setMileageId(preference.getString("coulmunId"));
        else
            model.setMileageId("0");

        if (TPApplication.getInstance().getDarTaskReoprtId()!=null)
        {
            model.setDarTaskReportId(TPApplication.getInstance().getDarTaskReoprtId());
        }
        else
        {
            model.setDarTaskReportId("");
        }
        model.setAssignmentId(String.valueOf(asgId));
        model.setLocationId(String.valueOf(locId));
        model.setTaskId(String.valueOf(taskId));
        model.setActionId("0");
        model.setAppId((int) PPZModel.getNextPrimaryId());
        aList.add(model);
        paramPPZ.setDetails(aList);
        paramPPZ.setCustId(TPApp.custId);
        rpc.setParams(paramPPZ);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("darPpzDetailInsert");

       // log.info(new Gson().toJson(rpc));

        if (isNetworkConnected()) {
            try {
                PPZModel.insertPPZModel(model);
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertPPZ(rpc).enqueue(new Callback<DarTicketResponse>() {
                    @Override
                    public void onResponse(Call<DarTicketResponse> call, Response<DarTicketResponse> response) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        if (response.code() == 200 && response.isSuccessful()) {
                            final DarTicketResult result = response.body().getResult();
                            final List<Integer> appId = result.getAppId();
                            if (appId!=null && appId.size()>0) {
                                for (int i = 0; i < appId.size(); i++) {
                                    try {
                                        PPZModel.removeById(appId.get(i).intValue());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<DarTicketResponse> call, Throwable t) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            PPZModel.insertPPZModel(model);
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
    public void onBackPressed() {
        try {
            callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
        } catch (IOException e) {
            e.printStackTrace();
        }

      /*  Intent serviceIntent = new Intent(DarPPZ.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START","");
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID",String.valueOf(locId));
        serviceIntent.putExtra("WITCH","Three");
        serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
        EDarServiceJobIntentTask.enqueueWork(DarPPZ.this, serviceIntent);
        TPApplication.getInstance().setDarStartTimeTask(null);

        finish();
        super.onBackPressed();*/
    }


    public void callTaskReport(String startTime, String endTime, String id, String TaskRecordId, String taskid) throws IOException {
        progressDialog = ProgressDialog.show(DarPPZ.this, "", "Please Wait...");
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
                    if (response.isSuccessful()) {
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

        new iOSDialogBuilder(DarPPZ.this)
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