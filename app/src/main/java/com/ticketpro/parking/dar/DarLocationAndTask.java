package com.ticketpro.parking.dar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.gson.Gson;
import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGenerator;
import com.ticketpro.model.DutyReport;
import com.ticketpro.model.Ticket;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.activity.WriteTicketActivity;
import com.ticketpro.parking.dar.model.AssignmentLocationReportModel;
import com.ticketpro.parking.dar.model.DarAssignmentLocationJob;
import com.ticketpro.parking.dar.model.DarAssignmentLocationReportResponse;
import com.ticketpro.parking.dar.model.DarDutyReportJob;
import com.ticketpro.parking.dar.model.DarDutyResponseR;
import com.ticketpro.parking.dar.model.DarLocationActivityTask;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.params.ParamAssignmentLocationReport;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.params.ParamsDutyRequestResponse;
import com.ticketpro.parking.dar.model.request.AssignmentLocationReport_rpc;
import com.ticketpro.parking.dar.model.request.DarDutyRequestJson_rpc;
import com.ticketpro.parking.dar.model.request.TaskReport_rpc;
import com.ticketpro.parking.service.EDarServiceJobIntent;
import com.ticketpro.util.DateUtil;
import com.ticketpro.util.Preference;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DarLocationAndTask extends BaseActivityImpl {

    private ListView lv_assignment;
    private ArrayList<DarLocationActivityTask> listItem;
    private int id_assg;
    private int id_assg_loc;
    private int idDuty;
    private String dutyName;
    private int taskId;
    private String mName;
    public ProgressDialog progressDialog;
    private String items;
    String LocationIndex = "";
    int assignmentLocationId;
    String aa = "0";

    /*  private List<Duty> childList;*/
    Logger log;
    DarAssignmentLocationJob darAssignmentLocationJob = new DarAssignmentLocationJob();
    DarDutyReportJob darDutyReportJob = new DarDutyReportJob();
    private ArrayList<DarLocationActivityTask> darLocationActivityTasks;
    Preference preference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dar_location_and_task);
        setLogger(DarLocationAndTask.class.getName());
        lv_assignment = findViewById(R.id.lv_assignment);

        TPApplication.getInstance().setDarStartTimeLocation(DateUtil.getCurrentDateTime1());
        preference = Preference.getInstance(DarLocationAndTask.this);

        Intent intent = getIntent();
        log = Logger.getLogger("DarLocationAndTask");

       /* if (!intent.getStringExtra("location_name").equals("") && !intent.getStringExtra("location_name").isEmpty())
        LocationIndex=intent.getStringExtra("location_name");*/

        id_assg = intent.getIntExtra("id_assg", 0);
        if (intent.getStringExtra("id_assg_loc") != null) {
            aa = intent.getStringExtra("id_assg_loc");
        }

        if (!aa.equals(" ") || !aa.isEmpty() || !aa.equals(" ")) {

            assignmentLocationId = Integer.parseInt(aa);
            /*try {
                loadChild(assignmentLocationId);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }

        idDuty = intent.getIntExtra("id_duty", 0);
        id_assg_loc = idDuty;


        String sName = intent.getStringExtra("name");
        if (sName != null && !TextUtils.isEmpty(sName)) {
            mName = sName.replaceAll(" ", "");
        }
        dutyName = intent.getStringExtra("duty_name");


      /*if (!LocationIndex.isEmpty() ||!LocationIndex.equals("") )
      {
          id_assg_loc=childList.get(Integer.valueOf(LocationIndex)).getId();
      }
*/

        Button b = findViewById(R.id.violation_back_btn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isNetworkConnected()) {
                        callDutyReportAPI(TPApplication.getInstance().getDardutyId(), DarLocationAndTask.this, TPApplication.getInstance().getDarParkingdutyreportId());
                        callAssignLocationAPI("", DateUtil.getCurrentDateTime1(), String.valueOf(id_assg), TPApplication.getInstance().getAssignReportId(), TPApplication.getInstance().getAssLocRecId(), DarLocationAndTask.this);


                    } else {
                        noInterneTT();
                        log.info("Duty report not performed due to no Internet connection");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // callAssignLocationAPI("", DateUtil.getCurrentDateTime1(), String.valueOf(id_assg), TPApplication.getInstance().getAssignReportId(), TPApplication.getInstance().getAssLocRecId(), DarLocationAndTask.this);


                //finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            Intent serviceIntent = new Intent(DarLocationAndTask.this, EDarServiceJobIntent.class);
            if (isNetworkConnected()) {
                EDarServiceJobIntent.enqueueWork(DarLocationAndTask.this, serviceIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listItem != null) {
            listItem.clear();
        }
        listItem = DarLocationActivityTask.getDarLocationActivityTask(id_assg);
        _loadInitialData(listItem);

    }

    private void _loadInitialData(ArrayList<DarLocationActivityTask> listItem) {
        if (listItem != null && listItem.size() > 0) {
            DarLocationTaskViewAdapter equipmentArrayAdapter = new DarLocationTaskViewAdapter(this, listItem);
            lv_assignment.setAdapter(equipmentArrayAdapter);
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

    public class DarLocationTaskViewAdapter extends ArrayAdapter<DarLocationActivityTask> {

        // invoke the suitable constructor of the ArrayAdapter class
        public DarLocationTaskViewAdapter(@NonNull Context context, ArrayList<DarLocationActivityTask> arrayList) {

            // pass the context and arrayList for the super
            // constructor of the ArrayAdapter class
            super(context, 0, arrayList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View currentItemView = convertView;

            if (currentItemView == null) {
                currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_dar_location_and_task_row, parent, false);
            }

            DarLocationActivityTask currentNumberPosition = getItem(position);

            //ImageView numbersImage = currentItemView.findViewById(R.id.imageView);
            assert currentNumberPosition != null;
            //numbersImage.setImageResource(currentNumberPosition.getNumbersImageId());

            TextView textView1 = currentItemView.findViewById(R.id.txt_equipment_name);
            RelativeLayout layout = currentItemView.findViewById(R.id.main_write_ticket_option);
            textView1.setText(currentNumberPosition.getItems());

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        String location_task = textView1.getText().toString().trim();
                        if (location_task != null && !TextUtils.isEmpty(location_task)) {
                            items = location_task.replaceAll(" ", "");
                        }
                        try {

                            tasklogin(id_assg_loc, currentNumberPosition.getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error(e.getMessage());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error(e);
                    }

                   /* Intent i = new Intent();
                    i.setClass(DarLocationAndTask.this, OffsiteNonEnforcement.class);
                    startActivity(i);*/
                }
            });
            return currentItemView;
        }
    }

    @Override
    public void onBackPressed() {

        try {
            if (isNetworkConnected()) {
                callDutyReportAPI(TPApplication.getInstance().getDardutyId(), DarLocationAndTask.this, TPApplication.getInstance().getDarParkingdutyreportId());
                callAssignLocationAPI("", DateUtil.getCurrentDateTime1(), String.valueOf(id_assg), TPApplication.getInstance().getAssignReportId(), TPApplication.getInstance().getAssLocRecId(), DarLocationAndTask.this);


            } else {
                noInterneTT();
                log.info("Duty report not performed due to no Internet connection");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void callTaskReport(String startTime, String endTime, String id, String TaskRecordId, int Id_Task) throws IOException {
        progressDialog = ProgressDialog.show(DarLocationAndTask.this, "", "Please wait....");
        progressDialog.setCancelable(false);
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
        model.setAssignmentId(Integer.valueOf(id_assg));
        model.setLocationId(String.valueOf(idDuty));
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
                    if (response.isSuccessful() && response.code() == 200) {
                        final List<Integer> appId = response.body().getResult().getAppId();
                        try {
                            if (response.body().getResult().getDarTaskReportId().get(0) != null) {
                                log.info("Task login" + DateUtil.getCurrentDateTime1());
                                TPApplication.getInstance().setDarTaskReoprtId(response.body().getResult().getDarTaskReportId().get(0));
                                funtaskreport(Id_Task);
                            }
                        } catch (Exception e) {
                            FailedtDialog();
                            log.error(e.getMessage());
                        }
                        if (appId != null && appId.size() > 0) {
                            for (int i = 0; i < appId.size(); i++) {
                                try {
                                    TaskReportModel.removeById(appId.get(i));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    log.error(e);

                                }
                            }
                        }
                    } else {
                        FailedtDialog();
                        log.error("Api Failure");
                    }
                }

                @Override
                public void onFailure(Call<DarTaskReportResponse> call, Throwable t) {
                    Log.d("Dar Report", t.getMessage());
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    log.error("t.getMessage()");
                    FailedtDialog();
                }
            });
        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            noInterneTT();
        }

    }


    public void tasklogin(int locaId, int Id_Task) throws IOException {

        callTaskReport(DateUtil.getCurrentDateTime1(), "", String.valueOf(locaId), "", Id_Task);

    }

    public void callAssignLocationAPI(String startTime, String endTime, String id, String AssignReoprtId, String AssignLocationReportId, Context context) throws IOException {
        TPApplication.getInstance().setAssLocRecId(null);
        progressDialog = ProgressDialog.show(context, "", "Please wait...");
        progressDialog.setCancelable(false);
        AssignmentLocationReport_rpc rpc = new AssignmentLocationReport_rpc();
        ParamAssignmentLocationReport param = new ParamAssignmentLocationReport();
        List<AssignmentLocationReportModel> aList = new ArrayList<>();
        AssignmentLocationReportModel model = new AssignmentLocationReportModel();
        //
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
        AssignmentLocationReportModel.insertFieldContactDetails(model);
        if (isNetworkConnected()) {

            ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
            api.insertAssignmentLocationReport(rpc).enqueue(new Callback<DarAssignmentLocationReportResponse>() {
                @Override
                public void onResponse(Call<DarAssignmentLocationReportResponse> call, Response<DarAssignmentLocationReportResponse> response) {
                    if (response.isSuccessful() && response.code() == 200) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        List<String> assign_report_idd = null;
                        final List<Integer> appId = response.body().getResult().getAppId();

                        try {
                            if (response.body().getResult().getAssignmentReportId().get(0) != null) {
                                log.info("duty Logout" + DateUtil.getCurrentDateTime1());
                                TPApplication.getInstance().setAssLocRecId(response.body().getResult().getAssignmentReportId().get(0));
                                finish();

                            }

                        } catch (Exception e) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            e.printStackTrace();
                            log.error(e.getMessage());
                        }


                        if (appId != null && appId.size() > 0) {
                            for (int i = 0; i < appId.size(); i++) {
                                try {
                                    AssignmentLocationReportModel.removeById(appId.get(i));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    log.error(e.getMessage());
                                }
                            }
                        }

                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        FailedtDialog();
                        log.error("Api Failure");
                    }
                }

                @Override
                public void onFailure(Call<DarAssignmentLocationReportResponse> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.d("Dar Report", t.getMessage());
                    log.error(t.getMessage());
                    FailedtDialog();
                    log.error("Api Failure");
                }
            });

        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            noInterneTT();
            log.info("No internet");
        }

    }

    /*  private void loadChild(int idAsg) throws Exception {
     *//* childList = new ArrayList<>();
        for (Duty model : DarAssignmentLocation.getAssignmentLocation(idAsg))
            childList.add(model);*//*
        childList = new ArrayList<>();
        for (Duty model : Duty.getDutiesList(idAsg))
            childList.add(model);
    }
*/
    public void funtaskreport(int currentNumberPosition) {
        try {
            if (items.equalsIgnoreCase("22500")) {
                Intent i = new Intent();
                i.putExtra("ASG_ID", id_assg);
                i.putExtra("LOCATION_ID", id_assg_loc);
                i.putExtra("TASK_ID", currentNumberPosition);
                i.setClass(DarLocationAndTask.this, TaskForm22500.class);
                startActivity(i);
            } else if (items.equalsIgnoreCase("FieldContact")) {
                Intent i = new Intent();
                i.putExtra("ASG_ID", id_assg);
                i.putExtra("LOCATION_ID", id_assg_loc);
                i.putExtra("TASK_ID", currentNumberPosition);
                i.setClass(DarLocationAndTask.this, FieldContract.class);
                startActivity(i);
            } else if (items.equalsIgnoreCase("10-7/10-7B")) {
                Intent i = new Intent();
                i.putExtra("ASG_ID", id_assg);
                i.putExtra("LOCATION_ID", id_assg_loc);
                i.putExtra("TASK_ID", currentNumberPosition);
                i.setClass(DarLocationAndTask.this, BreakLunch.class);
                startActivity(i);
            } else if (items.equalsIgnoreCase("Admin")) {
                Intent i = new Intent();
                i.putExtra("ASG_ID", id_assg);
                i.putExtra("LOCATION_ID", id_assg_loc);
                i.putExtra("TASK_ID", currentNumberPosition);
                i.setClass(DarLocationAndTask.this, AdministrativeForm.class);
                startActivity(i);
            } else if (items.equalsIgnoreCase("SignCheck")) {
                Intent i = new Intent();
                i.putExtra("ASG_ID", id_assg);
                i.putExtra("LOCATION_ID", id_assg_loc);
                i.putExtra("TASK_ID", currentNumberPosition);
                i.setClass(DarLocationAndTask.this, SignCheck.class);
                startActivity(i);
            } else if (items.equalsIgnoreCase("PPZ")) {
                Intent i = new Intent();
                i.putExtra("ASG_ID", id_assg);
                i.putExtra("LOCATION_ID", id_assg_loc);
                i.putExtra("TASK_ID", currentNumberPosition);
                i.setClass(DarLocationAndTask.this, DarPPZ.class);
                startActivity(i);
            } else if (items.equalsIgnoreCase("Off-StreetPatrol")) {
                Ticket ticket = TPApp.createNewTicket();
                ticket.setDutyId(idDuty);
                TPApp.setDutyName(dutyName);
                TPApp.setDutyId(idDuty);
                TPApp.setActiveTicket(ticket);
                Intent i = new Intent();
                i.putExtra("ASG_ID", id_assg);
                i.putExtra("LOCATION_ID", id_assg_loc);
                i.putExtra("TASK_ID", currentNumberPosition);
                i.setClass(DarLocationAndTask.this, OffStreetPatrol.class);
                startActivity(i);
            } else if (items.equalsIgnoreCase("ServiceRequest")) {
                Ticket ticket = TPApp.createNewTicket();
                ticket.setDutyId(idDuty);
                TPApp.setDutyName(dutyName);
                TPApp.setDutyId(idDuty);
                TPApp.setActiveTicket(ticket);
                Intent i = new Intent();
                i.putExtra("ASG_ID", id_assg);
                i.putExtra("LOCATION_ID", id_assg_loc);
                i.putExtra("TASK_ID", currentNumberPosition);
                i.setClass(DarLocationAndTask.this, ServiceRequest.class);
                startActivity(i);
            } else if (items.equalsIgnoreCase("VehMaintenance")) {
                Intent i = new Intent();
                i.putExtra("ASG_ID", id_assg);
                i.putExtra("LOCATION_ID", id_assg_loc);
                i.putExtra("TASK_ID", currentNumberPosition);
                i.setClass(DarLocationAndTask.this, VehicleMaintainance.class);
                startActivity(i);
            } else if (items.equalsIgnoreCase("Enforcement")) {
                Ticket ticket = TPApp.createNewTicket();
                ticket.setDutyId(idDuty);
                TPApp.setDutyName(dutyName);
                TPApp.setDutyId(idDuty);
                TPApp.setActiveTicket(ticket);
                Intent i = new Intent();
                i.setClass(DarLocationAndTask.this, WriteTicketActivity.class);
                i.putExtra("ASG_ID", id_assg);
                i.putExtra("LOCATION_ID", id_assg_loc);
                i.putExtra("TASK_ID", currentNumberPosition);
                startActivity(i);
            } else if (items.equalsIgnoreCase("School")) {
                Intent i = new Intent();
                i.putExtra("ASG_ID", id_assg);
                i.putExtra("LOCATION_ID", id_assg_loc);
                i.putExtra("TASK_ID", currentNumberPosition);
                i.setClass(DarLocationAndTask.this, SchoolPatrol.class);
                startActivity(i);
            } else {
                log.error("String not found");
            }

        } catch (Exception e) {

        }
    }

    public void FailedtDialog() {

        new iOSDialogBuilder(DarLocationAndTask.this)
                .setSubtitle("Service not responding.Please try again.")
                .setPositiveListener("OK", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {

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

    }

 


    public void callDutyReportAPI(int duty_id, Context context, String duty_report_id) throws IOException {
        DarDutyRequestJson_rpc rpc = new DarDutyRequestJson_rpc();
        ParamsDutyRequestResponse param = new ParamsDutyRequestResponse();
        List<DutyReport> aList = new ArrayList<>();
        DutyReport model = new DutyReport();
        model.setCustId(TPApplication.getInstance().custId);
        model.setDateIn(TPApplication.getInstance().getDatein());
        model.setDateOut(new Date());
        model.setDeviceId(TPApplication.getInstance().deviceId);
        model.setDutyId(TPApplication.getInstance().getDardutyId());
        model.setDuty_report_id(duty_report_id);
        model.setReportId(0);
        model.setDate_string(TPApplication.getInstance().dutyInTime);
        model.setUserId(TPApplication.getInstance().userId);
        aList.add(model);
        param.setChalkId(0);
        param.setDutyReports(aList);
        rpc.setParams(param);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("updateDutyReports");
        System.out.println("RequestObj1**" + new Gson().toJson(rpc));
        
        if (isNetworkConnected()) {
            DutyReport.insertDutyReport(model);
            try {
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertDutyReport(rpc).enqueue(new Callback<DarDutyResponseR>() {
                    @Override
                    public void onResponse(Call<DarDutyResponseR> call, Response<DarDutyResponseR> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getResult().getResult() == true) {

                            }

                        }
                    }
                    @Override
                    public void onFailure(Call<DarDutyResponseR> call, Throwable t) {
                        Log.d("updateDutyReports--->>>>", t.getMessage());

                    }
                });
            } catch (Exception e) {
                log.error("updateDutyReports-->>"+e.getMessage());
            }
        }

    }


    public void noInterneTT() {

        new iOSDialogBuilder(DarLocationAndTask.this)
                .setSubtitle("Please check your internet connectivity")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();

                    }
                })
                .build().show();

    }



     /* List<String> assign_report_idassign_report_id=response.body().getResult().getAssignmentReportId();
                            assign_report_idd=assign_report_idassign_report_id;*/

     /* if (assign_report_idd.size()>0)
                    {
                        for (int j=0;j<assign_report_idd.size();j++)
                        {
                            String as=assign_report_idd.get(j);
                            TPApplication.getInstance().setAssLocRecId(as);
                            Log.d("entLocationReportId",assign_report_idd.get(j));
                        }

                    }*/

     /* Intent serviceIntent = new Intent(DarLocationAndTask.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START",DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("END","");
        serviceIntent.putExtra("ID",String.valueOf(locaId));
        serviceIntent.putExtra("WITCH","Three");
        serviceIntent.putExtra("TaskRecordId","0");
        EDarServiceJobIntentTask.enqueueWork(DarLocationAndTask.this, serviceIntent);*/

     /* Intent serviceIntent = new Intent(DarLocationAndTask.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID", String.valueOf(id_assg));
        serviceIntent.putExtra("WITCH", "Two");
        EDarServiceJobIntentTask.enqueueWork(DarLocationAndTask.this, serviceIntent);*/
       /* Intent serviceIntent = new Intent(DarLocationAndTask.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", "");
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID", String.valueOf(id_assg));
        serviceIntent.putExtra("assignment_report_id",TPApplication.getInstance().getAssignReportId());
        serviceIntent.putExtra("WITCH", "Two");
        serviceIntent.putExtra("assignment_location_report_id",TPApplication.getInstance().getAssignmentLocationReportid());
        EDarServiceJobIntentTask.enqueueWork(DarLocationAndTask.this, serviceIntent);*/

        /* Intent serviceIntent = new Intent(DarLocationAndTask.this, EDarServiceJobIntentTask.class);
                serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                serviceIntent.putExtra("ID", String.valueOf(id_assg));
                serviceIntent.putExtra("WITCH", "Two");
                EDarServiceJobIntentTask.enqueueWork(DarLocationAndTask.this, serviceIntent);
                finish();*/
              /*  Intent serviceIntent = new Intent(DarLocationAndTask.this, EDarServiceJobIntentTask.class);
                serviceIntent.putExtra("START", "");
                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                serviceIntent.putExtra("ID", String.valueOf(id_assg));
                serviceIntent.putExtra("assignment_report_id",TPApplication.getInstance().getAssignReportId());
                serviceIntent.putExtra("assignment_location_report_id",TPApplication.getInstance().getAssignmentLocationReportid());
                serviceIntent.putExtra("WITCH", "Two");
                EDarServiceJobIntentTask.enqueueWork(DarLocationAndTask.this, serviceIntent);*/
}


/*
 if (mName.equals("Downtown")) {
         if (items.equalsIgnoreCase("22500")) {
         Intent i = new Intent();
         i.putExtra("ASG_ID", id_assg);
         i.putExtra("ASG_ID", id_assg);
         i.putExtra("LOCATION_ID", id_assg_loc);
         i.putExtra("TASK_ID", currentNumberPosition);

         i.setClass(DarLocationAndTask.this, TaskForm22500.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("FieldContact")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, FieldContract.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("10-7/10-7B")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, BreakLunch.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("Admin")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, AdministrativeForm.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("SignCheck")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, SignCheck.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("PPZ")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, DarPPZ.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("Off-StreetPatrol")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, OffStreetPatrol.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("ServiceRequest")) {
        Ticket ticket = TPApp.createNewTicket();
        ticket.setDutyId(idDuty);
        TPApp.setDutyName(dutyName);
        TPApp.setDutyId(idDuty);
        TPApp.setActiveTicket(ticket);
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, ServiceRequest.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("VehMaintenance")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, VehicleMaintainance.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("Enforcement")) {
        Ticket ticket = TPApp.createNewTicket();
        ticket.setDutyId(idDuty);
        TPApp.setDutyName(dutyName);
        TPApp.setDutyId(idDuty);
        TPApp.setActiveTicket(ticket);
        Intent i = new Intent();
        i.setClass(DarLocationAndTask.this, Enforcement.class);
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        startActivity(i);
        }
        else {
        log.error("String not found");
        }
        } else if (mName.equals("Exterior/SafetyPatrol")) {
        if (items.equalsIgnoreCase("22500")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, TaskForm22500.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("FieldContact")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, FieldContract.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("10-7/10-7B")) {
        Intent i = new Intent();
        i.setClass(DarLocationAndTask.this, BreakLunch.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("Admin")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, AdministrativeForm.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("School")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, SchoolPatrol.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("ServiceRequest")) {
        Ticket ticket = TPApp.createNewTicket();
        ticket.setDutyId(idDuty);
        TPApp.setDutyName(dutyName);
        TPApp.setDutyId(idDuty);
        TPApp.setActiveTicket(ticket);
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, ServiceRequest.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("VehMaintenance")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, VehicleMaintainance.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("Enforcement")) {
        Ticket ticket = TPApp.createNewTicket();
        ticket.setDutyId(idDuty);
        TPApp.setDutyName(dutyName);
        TPApp.setDutyId(idDuty);
        TPApp.setActiveTicket(ticket);
        Intent i = new Intent();
        i.setClass(DarLocationAndTask.this, Enforcement.class);
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        startActivity(i);
        }
        else {
        log.error("String not found");
        }
        } else if (mName.equals("VehicleAbatement")) {
        if (items.equalsIgnoreCase("FieldContact")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, FieldContract.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("10-7/10-7B")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, BreakLunch.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("Admin")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, AdministrativeForm.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("ServiceRequest")) {
        Ticket ticket = TPApp.createNewTicket();
        ticket.setDutyId(idDuty);
        TPApp.setDutyName(dutyName);
        TPApp.setDutyId(idDuty);
        TPApp.setActiveTicket(ticket);
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, ServiceRequest.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("VehMaintenance")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, VehicleMaintainance.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("Enforcement")) {
        Ticket ticket = TPApp.createNewTicket();
        ticket.setDutyId(idDuty);
        TPApp.setDutyName(dutyName);
        TPApp.setDutyId(idDuty);
        TPApp.setActiveTicket(ticket);
        Intent i = new Intent();
        i.setClass(DarLocationAndTask.this, Enforcement.class);
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        startActivity(i);
        } else if (items.equalsIgnoreCase("22500")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, TaskForm22500.class);
        startActivity(i);
        }
        else {
        log.error("String not found");
        }

        } else if (mName.equals("TowSupport")) {
        if (items.equalsIgnoreCase("FieldContact")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, FieldContract.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("10-7/10-7B")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, BreakLunch.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("Admin")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, AdministrativeForm.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("ServiceRequest")) {
        Ticket ticket = TPApp.createNewTicket();
        ticket.setDutyId(idDuty);
        TPApp.setDutyName(dutyName);
        TPApp.setDutyId(idDuty);
        TPApp.setActiveTicket(ticket);
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, ServiceRequest.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("22500")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, TaskForm22500.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("Enforcement")) {
        Ticket ticket = TPApp.createNewTicket();
        ticket.setDutyId(idDuty);
        TPApp.setDutyName(dutyName);
        TPApp.setDutyId(idDuty);
        TPApp.setActiveTicket(ticket);
        Intent i = new Intent();
        i.setClass(DarLocationAndTask.this, Enforcement.class);
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        startActivity(i);
        } else if (items.equalsIgnoreCase("VehMaintenance")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, VehicleMaintainance.class);
        startActivity(i);
        }
        else {
        log.error("String not found");
        }

        }

        else if(mName.equals("StreetSweep"))
        {
        if (items.equalsIgnoreCase("22500")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, TaskForm22500.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("FieldContact")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, FieldContract.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("10-7/10-7B")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, BreakLunch.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("Admin")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, AdministrativeForm.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("SignCheck")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, SignCheck.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("PPZ")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, DarPPZ.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("Off-StreetPatrol")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, OffStreetPatrol.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("ServiceRequest")) {
        Ticket ticket = TPApp.createNewTicket();
        ticket.setDutyId(idDuty);
        TPApp.setDutyName(dutyName);
        TPApp.setDutyId(idDuty);
        TPApp.setActiveTicket(ticket);
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, ServiceRequest.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("VehMaintenance")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, VehicleMaintainance.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("Enforcement")) {
        Ticket ticket = TPApp.createNewTicket();
        ticket.setDutyId(idDuty);
        TPApp.setDutyName(dutyName);
        TPApp.setDutyId(idDuty);
        TPApp.setActiveTicket(ticket);
        Intent i = new Intent();
        i.setClass(DarLocationAndTask.this, Enforcement.class);
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        startActivity(i);
        }
        else {
        log.error("String not found");
        }
        }
        else if (mName.equals("BikePatrol")) {
        if (items.equalsIgnoreCase("22500")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, TaskForm22500.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("FieldContact")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, FieldContract.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("10-7/10-7B")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, BreakLunch.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("Admin")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, AdministrativeForm.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("SignCheck")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, SignCheck.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("PPZ")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, DarPPZ.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("Off-StreetPatrol")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, OffStreetPatrol.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("ServiceRequest")) {
        Ticket ticket = TPApp.createNewTicket();
        ticket.setDutyId(idDuty);
        TPApp.setDutyName(dutyName);
        TPApp.setDutyId(idDuty);
        TPApp.setActiveTicket(ticket);
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, ServiceRequest.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("VehMaintenance")) {
        Intent i = new Intent();
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        i.setClass(DarLocationAndTask.this, VehicleMaintainance.class);
        startActivity(i);
        } else if (items.equalsIgnoreCase("Enforcement")) {
        Ticket ticket = TPApp.createNewTicket();
        ticket.setDutyId(idDuty);
        TPApp.setDutyName(dutyName);
        TPApp.setDutyId(idDuty);
        TPApp.setActiveTicket(ticket);
        Intent i = new Intent();
        i.setClass(DarLocationAndTask.this, Enforcement.class);
        i.putExtra("ASG_ID", id_assg);
        i.putExtra("LOCATION_ID", id_assg_loc);
        i.putExtra("TASK_ID", currentNumberPosition);
        startActivity(i);
        }
        else {
        log.error("String not found");
        }
        }


        else {
        log.error("String not found+1");
        }

*/


//      darAssignmentLocationJob.callAssignLocationAPI("",DateUtil.getCurrentDateTime1(),String.valueOf(id_assg),TPApplication.getInstance().getAssignReportId(),TPApplication.getInstance().getAssLocRecId(),DarLocationAndTask.this);
