package com.ticketpro.parking.dar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import androidx.annotation.RequiresApi;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.gson.Gson;
import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGenerator;
import com.ticketpro.gpslibrary.ADLocation;
import com.ticketpro.gpslibrary.MyTracker;
import com.ticketpro.model.Duty;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.HomeActivity;
import com.ticketpro.parking.activity.MainActivity;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.AssignmentLocationReportModel;
import com.ticketpro.parking.dar.model.AssignmentReportModel;
import com.ticketpro.parking.dar.model.Assignments;
import com.ticketpro.parking.dar.model.DarAssignmentLocation;
import com.ticketpro.parking.dar.model.DarAssignmentLocationJob;
import com.ticketpro.parking.dar.model.DarAssignmentLocationReportResponse;
import com.ticketpro.parking.dar.model.DarAssignmentreportResponse;
import com.ticketpro.parking.dar.model.Mileage;
import com.ticketpro.parking.dar.model.SJPDModel;
import com.ticketpro.parking.dar.model.SJPDResonse;
import com.ticketpro.parking.dar.model.params.ParamAssignmentLocationReport;
import com.ticketpro.parking.dar.model.params.ParamAssignmentReport;
import com.ticketpro.parking.dar.model.params.ParamSjpd;
import com.ticketpro.parking.dar.model.params.ParamsEndMileage;
import com.ticketpro.parking.dar.model.request.AssignmentLocationReport_rpc;
import com.ticketpro.parking.dar.model.request.AssignmentReport_rpc;
import com.ticketpro.parking.dar.model.request.EndMileageJson_rpc;
import com.ticketpro.parking.dar.model.request.SJPDJson_rpc;
import com.ticketpro.parking.dar.model.response.EndMileageResponse;
import com.ticketpro.util.AssignmentIdInterface;
import com.ticketpro.util.DarAssignmentIdLogout;
import com.ticketpro.util.DarAssignmentLocationIdInterface;
import com.ticketpro.util.DateUtil;
import com.ticketpro.util.Preference;
import com.ticketpro.util.TPUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Active file
public class DarAssignmentActivity extends BaseActivityImpl implements AssignmentIdInterface, DarAssignmentLocationIdInterface, DarAssignmentIdLogout, MyTracker.ADLocationListener {
    private List<Assignments> groupList;
    private List<Duty> childList;
    private List<Duty> childDetails;
    private Map<Assignments, List<Duty>> assignmentCollection;
    private ExpandableListView expListView;
    private Preference preference;
    private String inTime = null;
    public ProgressDialog progressDialog;
    int assin_id = 0;
    String AssignId = "0";
    int AssignmentId_API = 0;
    MilageDialog milageDialog;
    DarAssignmentLocationJob darAssignmentLocationJob;
    DarAssignmentActivityAdapter expListAdapter;
    int AssignmentSelectionId = 500;
    private List<DarAssignmentLocation> locationDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dar_assignment);
        setLogger(DarAssignmentActivity.class.getName());


        preference = Preference.getInstance(DarAssignmentActivity.this);
        preference.putString("assignmentApiHits", "N");
        progressDialog = new ProgressDialog(DarAssignmentActivity.this);
        progressDialog.setMessage("Please wait....");
        TPApplication.getInstance().setDarStartTimeAssignment(DateUtil.getCurrentDateTime1());
        preference.putString("SJPD_RecordId", "");
        __openSjpdDialog();



        try {
            createGroupList();
            createCollection();
        } catch (Exception e) {
            e.printStackTrace();
        }


        expListView = (ExpandableListView) findViewById(R.id.el_assignment);
        expListAdapter = new DarAssignmentActivityAdapter(
                this, groupList, assignmentCollection, this, this, this);
        expListView.setAdapter(expListAdapter);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = 0;
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousItem)
                    expListView.collapseGroup(previousItem);
                previousItem = groupPosition;


            }
        });


        Button b = findViewById(R.id.violation_back_btn);
        b.setOnClickListener(v -> {
            new iOSDialogBuilder(DarAssignmentActivity.this)
                    .setSubtitle("SJPD Radio logoff \n Performed?")
                    .setPositiveListener("Yes", new iOSDialogClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(iOSDialog dialog) {

                            dialog.dismiss();
                            String Id_colunn = preference.getString("coulmunId");

                            String Start_mileage = TPApp.getMileage();

                            if (AssignmentSelectionId != 500) {
                                try {
                                    AssignmentLogout("", DateUtil.getCurrentDateTime1(), String.valueOf(assin_id), TPApplication.getInstance().getAssignReportId(), Id_colunn, Start_mileage);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    _sjpdlogoutwithoutSelectingAssignment(Id_colunn, Start_mileage);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            //_sjpdlogout(Id_colunn, Start_mileage);
                            /*MilageDialog milageDialog = new MilageDialog();

                            if (AssignmentSelectionId != 500) {
                                if (isNetworkConnected()) {
                                    milageDialog.EndMilageDialogShift(DarAssignmentActivity.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.deviceId), "Yes", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                                } else {
                                    milageDialog.EndMilageDialogShift(DarAssignmentActivity.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.deviceId), "No", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                                }

                            } else {
                                MileageDialog(Start_mileage, Id_colunn);
                            }*/
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



        inTime = preference.getString("IN_DATE");
        //this function call for SJPD
        try {
            if (inTime != null && !TextUtils.isEmpty(inTime) && DateUtil.isCurrentDateBig(DateUtil.getCurrentDateTime1(), inTime)) {
                preference.putString("IN_DATE", DateUtil.getCurrentDateTime1());
                //  __openSjpdDialog();
            } else {
                if (inTime == null) {
                    //    __openSjpdDialog();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        findLoc();
    }

    private void __openSjpdDialog() {
        new iOSDialogBuilder(DarAssignmentActivity.this)
                .setSubtitle("SJPD Radio logon \n Performed? ")
                .setPositiveListener("Yes", dialog -> {
                    preference.putString("IN_DATE", DateUtil.getCurrentDateTime1());
                    dialog.dismiss();
                    try {
                        dialog.dismiss();
                        _sjpdInsert();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).setNegativeListener("No", dialog -> {
                    dialog.dismiss();
                    finish();
                })
                .build().show();
    }


    private void createGroupList() {
        groupList = new ArrayList<Assignments>();
        groupList = Assignments._getEquipmentList(TPApp.custId);

    }

    private void createCollection() throws Exception {
        assignmentCollection = new LinkedHashMap<>();
        for (Assignments location : groupList) {
            loadChild(location.getIdAsg());
            assignmentCollection.put(location, childList);
        }
    }

    private void loadChild(int idAsg) throws Exception {
        childList = new ArrayList<>();
        for (Duty model : Duty.getDutiesList(idAsg))
            childList.add(model);
    }

    private void loadChilddeatils(int idAsg) throws Exception {
        childDetails = new ArrayList<>();
        for (Duty model : Duty.getDutiesList(idAsg))
            childDetails.add(model);
    }

    @Override
    public void onClick(View v) {

    }

    public void expendView(int position) {
        expListView.expandGroup(position);
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

    public void _sjpdInsert() throws IOException {
        String mileageIndexId ="";
         mileageIndexId = preference.getString("coulmunId");
        //  progressDialog = ProgressDialog.show(DarAssignmentActivity.this, "", "Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        SJPDJson_rpc sjpdJson_rpc = new SJPDJson_rpc();
        ParamSjpd paramSjpd = new ParamSjpd();
        ArrayList<SJPDModel> sjpdModels = new ArrayList<>();
        SJPDModel sjpdModel = new SJPDModel();
        sjpdModel.setLogin(DateUtil.getCurrentDateTime1());
        sjpdModel.setLogout("");
        sjpdModel.setUserId(String.valueOf(TPApplication.getInstance().userId));
        sjpdModel.setAppId("10");
        sjpdModel.setDisinfecting(TPApp.getDisinfecting());
        sjpdModel.setVdr(TPApp.getVdr());
        sjpdModel.setDar_call_signin_id("");
        sjpdModel.setNotes(TPApp.getOvertime_Note());
        sjpdModel.setShiftEndTime(TPApp.getShiftEndTime());
        sjpdModel.setShiftStTime(TPApp.getShiftStartTime());
        sjpdModel.setShiftType(TPApp.getShiftType());
        sjpdModel.setEquipmentId(TPApp.getEquipment());
        sjpdModel.setSubEquipmentId(TPApp.getEquipmentChild());
        sjpdModel.setActionId("5");// harcoded for api calling
        sjpdModel.setLocationId("5");// harcoded for api calling
        sjpdModel.setAssignmentId("5");// harcoded for api calling
        sjpdModel.setDarTaskReportId("1119");// harcoded for api calling
        sjpdModel.setTaskId("5");// harcoded for api calling
        sjpdModel.setMileage("2145");// harcoded for api calling
        /*if (!preference.getString("coulmunId").isEmpty() || !preference.getString("coulmunId").equals(""))
            sjpdModel.setMileageId(preference.getString("coulmunId"));
        else
            sjpdModel.setMileageId("0");*/

        sjpdModel.setMileageId(TextUtils.isEmpty(mileageIndexId) ? "" : mileageIndexId);
        sjpdModels.add(sjpdModel);
        paramSjpd.setCustId(TPApplication.getInstance().custId);
        paramSjpd.setDeviceId(TPApplication.getInstance().deviceId);
        paramSjpd.setDetails(sjpdModels);
        sjpdJson_rpc.setJsonrpc("2.0");
        sjpdJson_rpc.setId("82F85DB43CBF6");
        sjpdJson_rpc.setMethod("sjpdFormInsert");
        sjpdJson_rpc.setParams(paramSjpd);
        System.out.println("RequestObj**" + new Gson().toJson(sjpdJson_rpc));
        if (isNetworkConnected()) {
            try {
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertSJPD(sjpdJson_rpc).enqueue(new Callback<SJPDResonse>() {
                    @Override
                    public void onResponse(Call<SJPDResonse> call, Response<SJPDResonse> response) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        log.info("RequestObj**" + new Gson().toJson(sjpdJson_rpc).toString());
                        if (response.isSuccessful() && response.code()==200){
                            if (response.body().getResult() != null) {
                                if (response.body().getResult().getResult()) {
                                    log.info("SJPD Logon Performed" + " " + DateUtil.getCurrentDateTime1());
                                    log.info("Response" + " " + response.body().getResult().toString());
                                    preference.putString("SJPD_RecordId", response.body().getResult().getDarCallSigninId().get(0));
                                } else {
                                    log.info("Response" + " " + "No getting result");
                                    serviceNotResponding();
                                }
                            } else {
                                log.info("Result" + " " + "not Found");
                                serviceNotResponding();
                            }
                        }else{
                            log.info("Service not responding");
                            serviceNotResponding();
                        }
                    }

                    @Override
                    public void onFailure(Call<SJPDResonse> call, Throwable t) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        log.error("DarAssignment " + t.getMessage());
                        log.info("Api Failure");
                        serviceNotResponding();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                serviceNotResponding();
            }

        } else {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();

            }
            NoInternet();
            log.error("No Internet");
        }


    }


    public void _sjpdlogout(String Id_coulmn, String StartMileage) throws IOException {
        //  progressDialog = ProgressDialog.show(DarAssignmentActivity.this, "", "Please wait...");
      /*  progressDialog.show();
        progressDialog.setCancelable(false);*/
        String SJPDIndexId = " ";
        SJPDIndexId = preference.getString("SJPD_RecordId");
        SJPDJson_rpc sjpdJson_rpc = new SJPDJson_rpc();
        ParamSjpd paramSjpd = new ParamSjpd();
        ArrayList<SJPDModel> sjpdModels = new ArrayList<>();
        SJPDModel sjpdModel = new SJPDModel();
        sjpdModel.setLogin("");
        sjpdModel.setLogout(DateUtil.getCurrentDateTime1());
//        sjpdModel.setLogout("");
        sjpdModel.setUserId(String.valueOf(TPApplication.getInstance().userId));
        sjpdModel.setAppId("10");

        if (!SJPDIndexId.equals(" "))
            sjpdModel.setDar_call_signin_id(SJPDIndexId);
        else
            sjpdModel.setDar_call_signin_id("");

        sjpdModels.add(sjpdModel);
        paramSjpd.setCustId(TPApplication.getInstance().custId);
        paramSjpd.setDeviceId(TPApplication.getInstance().deviceId);
        paramSjpd.setDetails(sjpdModels);
        sjpdJson_rpc.setJsonrpc("2.0");
        sjpdJson_rpc.setId("82F85DB43CBF6");
        sjpdJson_rpc.setMethod("sjpdFormInsert");
        sjpdJson_rpc.setParams(paramSjpd);
        System.out.println("RequestObj112**" + new Gson().toJson(sjpdJson_rpc));
        if (isNetworkConnected()) {
            try {
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertSJPD(sjpdJson_rpc).enqueue(new Callback<SJPDResonse>() {
                    @Override
                    public void onResponse(Call<SJPDResonse> call, Response<SJPDResonse> response) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        log.info("RequestObj**" + new Gson().toJson(sjpdJson_rpc).toString());
                        if (response.isSuccessful() && response.code()==200) {
                            if (response.body().getResult() != null) {

                                if (response.body().getResult().getResult()) {
                                    log.info("Response" + " " + response.body().getResult().getResult().toString());
                                    log.info("SJPD Logout Performed" + " " + DateUtil.getCurrentDateTime1());
                                    try {
                                        if (TPApplication.getInstance().getVehicleString().equals("NV"))
                                        {
                                            Intent intent= new Intent(DarAssignmentActivity.this,HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            LogoutandsubmitEndMileage(StartMileage, Id_coulmn);
                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    log.info(response.body().getResult() + "not getting result");
                                    Failed();
                                }

                            } else {
                                log.info("Response" + " " + response.body().toString());
                                Failed();
                                log.info("Failure");
                            }
                            //  preference.putString("SJPD_RecordId", response.body().getResult().getDarCallSigninId().get(0));
                        }
                        else{
                            log.info("Service not responding");
                            serviceNotResponding();
                        }
                    }

                    @Override
                    public void onFailure(Call<SJPDResonse> call, Throwable t) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        log.error("DarAssignment " + t.getMessage());
                        log.info("Api Failure");
                        Failed();

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                Failed();
            }

        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            NoInterneTT();
            log.error("No Internert");
        }


    }

    public void _sjpdlogoutwithoutSelectingAssignment(String Id_coulmn, String StartMileage) throws IOException {
        //  progressDialog = ProgressDialog.show(DarAssignmentActivity.this, "", "Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String SJPDIndexId = " ";
        SJPDIndexId = preference.getString("SJPD_RecordId");
        SJPDJson_rpc sjpdJson_rpc = new SJPDJson_rpc();
        ParamSjpd paramSjpd = new ParamSjpd();
        ArrayList<SJPDModel> sjpdModels = new ArrayList<>();
        SJPDModel sjpdModel = new SJPDModel();
        sjpdModel.setLogin("");
        sjpdModel.setLogout(DateUtil.getCurrentDateTime1());
//        sjpdModel.setLogout("");
        sjpdModel.setUserId(String.valueOf(TPApplication.getInstance().userId));
        sjpdModel.setAppId("10");

        if (!SJPDIndexId.equals(" "))
            sjpdModel.setDar_call_signin_id(SJPDIndexId);
        else
            sjpdModel.setDar_call_signin_id("");

        sjpdModels.add(sjpdModel);
        paramSjpd.setCustId(TPApplication.getInstance().custId);
        paramSjpd.setDeviceId(TPApplication.getInstance().deviceId);
        paramSjpd.setDetails(sjpdModels);
        sjpdJson_rpc.setJsonrpc("2.0");
        sjpdJson_rpc.setId("82F85DB43CBF6");
        sjpdJson_rpc.setMethod("sjpdFormInsert");
        sjpdJson_rpc.setParams(paramSjpd);
        System.out.println("RequestObj112**" + new Gson().toJson(sjpdJson_rpc));
        if (isNetworkConnected()) {
            try {
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertSJPD(sjpdJson_rpc).enqueue(new Callback<SJPDResonse>() {
                    @Override
                    public void onResponse(Call<SJPDResonse> call, Response<SJPDResonse> response) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        log.info("RequestObj**" + new Gson().toJson(sjpdJson_rpc).toString());
                        if (response.isSuccessful() && response.code()==200) {
                            if (response.body().getResult() != null) {

                                if (response.body().getResult().getResult()) {
                                    log.info("Response" + " " + response.body().getResult().toString());
                                    log.info("SJPD Logout Performed" + " " + DateUtil.getCurrentDateTime1());
                                    try {
                                        if (TPApplication.getInstance().getVehicleString().trim().equals("NV"))
                                        {
                                            Intent intent= new Intent(DarAssignmentActivity.this,HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            LogoutandsubmitEndMileage(StartMileage, Id_coulmn);
                                        }

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    log.info(response.body().getResult() + "not getting result");
                                    Failed();
                                }

                            } else {
                                Failed();
                                log.info("Failure");
                            }
                            //  preference.putString("SJPD_RecordId", response.body().getResult().getDarCallSigninId().get(0));
                        }else{
                            log.info("Service not responding");
                            serviceNotResponding();
                        }
                    }

                    @Override
                    public void onFailure(Call<SJPDResonse> call, Throwable t) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        log.error("DarAssignment " + t.getMessage());
                        log.info("Api Failure");
                        Failed();

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                Failed();
            }

        } else {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();

            }
            NoInterneTT();
            log.error("No Internert");
        }


    }


    public void collapseAll(int position) {
        expListView.collapseGroup(position);
       /* int count =expListAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            expListView.collapseGroup(i);
        }*/

    }

    @Override
    public void getid(int p) {
        AssignmentSelectionId = p;
        if (p != 0) {
            LoginTime(p);
        }

        if (assin_id != 0 && assin_id != p) {
            preference.putString("AssignId", String.valueOf(p));
            // _sjpdInsert1(inTime,DateUtil.getCurrentDateTime1(),assin_id);
            assin_id = p;
        } else {
            preference.putString("AssignId", String.valueOf(p));
            assin_id = p;
        }

        try {
            loadChilddeatils(p);
            loadlocation(p);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        new iOSDialogBuilder(DarAssignmentActivity.this)
                .setSubtitle("SJPD Radio logoff \n Performed?")
                .setPositiveListener("Yes", new iOSDialogClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                        String Id_colunn = preference.getString("coulmunId");
                        String Start_mileage = TPApp.getMileage();

                        if (AssignmentSelectionId != 500) {
                            try {
                                AssignmentLogout("", DateUtil.getCurrentDateTime1(), String.valueOf(assin_id), TPApplication.getInstance().getAssignReportId(), Id_colunn, Start_mileage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                _sjpdlogoutwithoutSelectingAssignment(Id_colunn, Start_mileage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }     // _sjpdlogout(Id_colunn, Start_mileage);

                       /* dialog.dismiss();
                        String Id_colunn = preference.getString("coulmunId");
                        String Start_mileage = TPApp.getMileage();

                        MilageDialog milageDialog = new MilageDialog();
                        _sjpdlogout();
                        if (AssignmentSelectionId != 500) {
                            if (isNetworkConnected()) {
                                milageDialog.EndMilageDialogShift(DarAssignmentActivity.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.deviceId), "Yes", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                            } else {
                                milageDialog.EndMilageDialogShift(DarAssignmentActivity.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.deviceId), "No", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
                            }

                        } else {
                            MileageDialog(Start_mileage, Id_colunn);
                        }*/
                    }
                })
                .setNegativeListener("No", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build().show();

    }

    public void LoginTime(int assin_idd) {
        if (assin_idd != 0) {
            AssignId = String.valueOf(assin_id);
        } else {
            AssignId = "0";
        }
    }

    @Override
    public void locationId(int i, int position) {
        if (i != 0) {
            TPApplication.getInstance().setDatein(new Date());
            TPApplication.getInstance().setDutyInTime(DateUtil.getCurrentDate());
            int dutylocation_id = childDetails.get(position).getId();
            try {
                AssignmentLocation(i, dutylocation_id, position);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void logouttime(int x) {
        if (x == 10000) {
            //Logout();
        }
    }


    public void AssignmentLocation(int assignmentindexid, int dutylocation_id, int locationIndex) throws IOException {

        darAssignmentLocationJob = new DarAssignmentLocationJob();
        if (dutylocation_id != 0) {
            TPApplication.getInstance().setDardutyId(dutylocation_id);
        }
//
//            darAssignmentLocationJob.callAssignLocationAPI(DateUtil.getCurrentDateTime1(),"",String.valueOf(x),TPApplication.getInstance().getAssignReportId(),"",DarAssignmentActivity.this);*/
        //String AssignementReportId = preference.getString("assignmentreportId");

        if (AssignmentId_API != assin_id && AssignmentId_API == 0) {
            callAssignmentReport(DateUtil.getCurrentDateTime1(), "", String.valueOf(assin_id), "", String.valueOf(dutylocation_id));

        } else if (AssignmentId_API != assin_id && AssignmentId_API != 0) {
            /*Logoutassignment();*/
            callAssignmentReportLogout("", DateUtil.getCurrentDateTime1(), String.valueOf(assin_id), TPApplication.getInstance().getAssignReportId());
            callAssignmentReport(DateUtil.getCurrentDateTime1(), "", String.valueOf(assin_id), "", String.valueOf(dutylocation_id));

        } else {

            AssignLocationAPI(DateUtil.getCurrentDateTime1(), "", String.valueOf(assignmentindexid), TPApplication.getInstance().getAssignReportId(), "", String.valueOf(dutylocation_id), DarAssignmentActivity.this);
        }

    }


    public void callAssignmentReport(String startTime, String endTime, String id, String assignReportId, String AssignmentReportId) throws IOException {
        progressDialog.show();
        progressDialog.setCancelable(false);
        //   progressDialog = ProgressDialog.show(DarAssignmentActivity.this, "", "Please wait....");
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
        preference.putString("assignmentreportId", "0");
        System.out.println("RequestObj**" + new Gson().toJson(rpc));
        AssignmentReportModel.insertFieldContactDetails(model);
        ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
        if (isNetworkConnected()) {
            api.insertAssignmentReport(rpc).enqueue(new Callback<DarAssignmentreportResponse>() {
                @Override
                public void onResponse(Call<DarAssignmentreportResponse> call, Response<DarAssignmentreportResponse> response) {
                    log.info("RequestObj**" + new Gson().toJson(rpc).toString());
                    /*if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

*/
                    if (response.isSuccessful() && response.code() == 200){
                        if (response.body().getResult() != null) {
                            if (response.body().getResult().getResult()) {
                                preference.putString("AssSubId", "1");
                                preference.putString("assignmentApiHits", "Y");
                                final List<Integer> appId = response.body().getResult().getAppId();
                                final List<String> assignmentReportId = response.body().getResult().getAssignmentReportId();

                                if (appId != null && appId.size() > 0) {
                                    for (int i = 0; i < appId.size(); i++) {
                                        try {
                                            AssignmentReportModel.removeById(appId.get(i));

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            log.error(e.getMessage().toString());
                                        }
                                    }
                                }
                                if (assignmentReportId != null && assignmentReportId.size() > 0) {
                                    for (int j = 0; j < assignmentReportId.size(); j++) {
                                        log.info("Response" + " " + response.body().getResult().toString());
                                        TPApplication.getInstance().setAssignReportId(assignmentReportId.get(j));
                                        Log.d("assignmentReportId", assignmentReportId.get(j));
                                        preference.putString("assignmentreportId", assignmentReportId.get(j));
                                        AssignmentId_API = Integer.valueOf(id);
                                        log.info("Assignment login id-" + assignmentReportId.get(j) + " " + DateUtil.getCurrentDateTime1());
                                        try {
                                            callAssignLocationAPI(DateUtil.getCurrentDateTime1(), "", String.valueOf(id), TPApplication.getInstance().getAssignReportId(), "", String.valueOf(AssignmentReportId), DarAssignmentActivity.this);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                } else {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    Dialog();
                                    log.error("incorrect response");

                                }
                            } else {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                Dialog();
                                log.info("Response" + " " + response.body().getResult().toString());
                            }
                        }else
                        {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Dialog();
                            log.error("API Failure");
                        }
                    }else
                    {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Dialog();
                        log.error("API Failure");
                    }
                }

                @Override
                public void onFailure(Call<DarAssignmentreportResponse> call, Throwable t) {
                    Log.d("Dar Report", t.getMessage());
                    log.error(t.getMessage());
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Dialog();
                    log.error("API Failure");
                }
            });

        } else {
            //Toast.makeText(DarAssignmentActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            log.error("No Internet");
            NoInternetDialog();
        }

    }


    private void loadlocation(int idAsg) {
        locationDetails = new ArrayList<>();
        for (DarAssignmentLocation model : DarAssignmentLocation.getAssignmentLocation(idAsg))
            locationDetails.add(model);
    }


    public void callAssignLocationAPI(String startTime, String endTime, String id, String AssignReoprtId, String AssignLocationReportId, String AssignmentLocationId, Context context) throws IOException {
        // progressDialog.show();
        // progressDialog = ProgressDialog.show(context, "", "Please wait...");
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
                    if (response.isSuccessful() && response.code()==200) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        log.info("RequestObj**" + new Gson().toJson(rpc).toString());
                        List<String> assign_report_idd = null;
                        final List<Integer> appId = response.body().getResult().getAppId();

                        try {
                            if (response.body().getResult().getAssignmentReportId().get(0) != null) {
                                log.info("Response"+" "+response.body().getResult().toString());
                                TPApplication.getInstance().setAssLocRecId(response.body().getResult().getAssignmentReportId().get(0));
                                log.info("duty logged on"+" "+DateUtil.getCurrentDateTime1());
                                Intent i = new Intent();
                                i.putExtra("id_assg", TPApplication.getInstance().act_assignmentid);
                                i.putExtra("id_assg_loc", String.valueOf(TPApplication.getInstance().act_assignmentid));
                                i.putExtra("location_name", TPApplication.getInstance().getAct_assignmentlocationpos());
                                i.putExtra("id_duty", TPApplication.getInstance().getAct_dutylocationid());
                                i.putExtra("name", TPApplication.getInstance().getAct_assignmentname());
                                i.putExtra("duty_name", TPApplication.getInstance().getAct_dutylocationname());
                                i.setClass(DarAssignmentActivity.this, DarLocationAndTask.class);
                                startActivity(i);
                           /* List<String> assign_report_idassign_report_id=response.body().getResult().getAssignmentReportId();
                            assign_report_idd=assign_report_idassign_report_id;*/
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
                        // Dialog();
                    }
                }

                @Override
                public void onFailure(Call<DarAssignmentLocationReportResponse> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.d("Dar Report", t.getMessage());
                    log.error(t.getMessage());
                    Dialog();

                }
            });
        } else {
            log.error("No Internet_in Location");
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            NoInternetDialog();
            log.error("No Internet");
        }

    }


    public void MileageDialog(String end_mileage, String mileage_index) {

        Dialog EndDialog = new Dialog(this, R.style.Theme_Dialog);
        EndDialog.setTitle("End Mileage?");
        View customlayout = EndDialog.getLayoutInflater().inflate(R.layout.endmilagedialoglayouy, null);
        EndDialog.setContentView(customlayout);
        EndDialog.setCancelable(true);
        Button btn_submit = EndDialog.findViewById(R.id.endmilage_submit);
        Button btn_back = EndDialog.findViewById(R.id.endmilage_back);
        EditText EndMileage = EndDialog.findViewById(R.id.end_mileage);
        btn_submit.setOnClickListener(view -> {
            String EndMileageValue = EndMileage.getText().toString();

            if (EndMileageValue.isEmpty() || EndMileageValue.equals("")) {
                EndMileage.setError("Enter End Mileage");
            } else if (Integer.parseInt(end_mileage) > Integer.parseInt(EndMileageValue)) {
                EndMileage.setError("Enter Correct End  Mileage");

            } else {
                EndDialog.dismiss();
                try {
                    saveEndMileage(EndMileageValue, 2, mileage_index);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            // ;

        });
        EndDialog.show();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EndDialog.dismiss();
            }
        });

    }

    public void saveEndMileage(String endMileage, int i, String Mileage_index) throws IOException {
        progressDialog = ProgressDialog.show(this, "", "Please wait");
        EndMileageJson_rpc jsonRpc = new EndMileageJson_rpc();
        List<Mileage> aList = new ArrayList<>();
        ParamsEndMileage param = new ParamsEndMileage();
        Mileage details = new Mileage();
        details.setUserId(TPApp.deviceId);
        details.setDeviceid(TPApp.custId);
        details.setEndMileage(endMileage);
        details.setVehicleId(TPApp.getVehicleid());
        details.setVehicleNumber(TPApp.getVehicleType());
        //details.setActionId("1");
        aList.add(details);
        param.setCustId(Integer.parseInt(Mileage_index));
        param.setDetails(aList);
        jsonRpc.setJsonrpc("2.o");
        jsonRpc.setMethod("updateMileage");
        jsonRpc.setId("82F85DB43CBF6");
        jsonRpc.setParams(param);
        System.out.println("RequestObj**" + new Gson().toJson(jsonRpc));

        if (isNetworkConnected()) {
            try {
                //     Mileage.insertMileage(details);
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertEndMileage(jsonRpc).enqueue(new Callback<EndMileageResponse>() {
                    @Override
                    public void onResponse(Call<EndMileageResponse> call, Response<EndMileageResponse> response) {
                        Log.d("ResponseBody", response.body().getResult().toString());
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        if (response.code() == 200 && response.body().getResult().getResult() == true) {
                            Intent intent = new Intent();
                            intent.setClass(DarAssignmentActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onFailure(Call<EndMileageResponse> call, Throwable t) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Log.d("Error", t.getMessage());
                        log.error("Mileage Failure");
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

            //Mileage.insertMileage(details);
        /*    Intent intent = new Intent();
            intent.setClass(context, HomeActivity.class);
            context.startActivity(intent);
            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();*/

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
                    if (response.isSuccessful() && response.code()==200) {
                        preference.putString("AssSubId", "1");
                        log.info("Assignment Logout performed"+" "+DateUtil.getCurrentDateTime1());
                        log.info("RequestObj**" + new Gson().toJson(rpc));
                        final List<Integer> appId = response.body().getResult().getAppId();
                        final List<String> assignmentReportId = response.body().getResult().getAssignmentReportId();

                      /*  if (appId != null && appId.size() > 0) {
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
                        //    Dialog();
                    }
                }

                @Override
                public void onFailure(Call<DarAssignmentreportResponse> call, Throwable t) {
                    Log.d("Dar Report", t.getMessage());
                    log.error(t.getMessage());
                }
            });

        } else {
            log.error("No Internet");
        }

    }

    public void Dialog() {

        new iOSDialogBuilder(DarAssignmentActivity.this)
                .setSubtitle("Please try again")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build().show();
    }

    public void NoInternetDialog() {

        new iOSDialogBuilder(DarAssignmentActivity.this)
                .setSubtitle("Please check your internet connectivity")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .build().show();

    }

    public void NoInternet() {

        new iOSDialogBuilder(DarAssignmentActivity.this)
                .setSubtitle("Please check your internet connectivity")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .build().show();

    }


    public void NoInterneTT() {

        new iOSDialogBuilder(DarAssignmentActivity.this)
                .setSubtitle("Please check your internet connectivity")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();

                    }
                })
                .build().show();

    }

    public void serviceNotResponding() {

        new iOSDialogBuilder(DarAssignmentActivity.this)
                .setSubtitle("Service not responding. Please try again.")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                        finish();

                    }
                })
                .build().show();

    }


    public void Failed() {

        new iOSDialogBuilder(DarAssignmentActivity.this)
                .setSubtitle("Service not responding.Please try again.")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();

                    }
                })
                .build().show();

    }

    public void AssignLocationAPI(String startTime, String endTime, String id, String AssignReoprtId, String AssignLocationReportId, String AssignmentLocationId, Context context) throws IOException {
        progressDialog.show();
        // progressDialog = ProgressDialog.show(context, "", "Please wait...");
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
                    if (response.isSuccessful() && response.code()==200) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        List<String> assign_report_idd = null;
                        final List<Integer> appId = response.body().getResult().getAppId();

                        try {
                            if (response.body().getResult().getAssignmentReportId().get(0) != null) {
                                TPApplication.getInstance().setAssLocRecId(response.body().getResult().getAssignmentReportId().get(0));
                                log.info("duty Logout performed"+" "+DateUtil.getCurrentDateTime1());
                                Intent i = new Intent();
                                i.putExtra("id_assg", TPApplication.getInstance().act_assignmentid);
                                i.putExtra("id_assg_loc", String.valueOf(TPApplication.getInstance().act_assignmentid));
                                i.putExtra("location_name", TPApplication.getInstance().getAct_assignmentlocationpos());
                                i.putExtra("id_duty", TPApplication.getInstance().getAct_dutylocationid());
                                i.putExtra("name", TPApplication.getInstance().getAct_assignmentname());
                                i.putExtra("duty_name", TPApplication.getInstance().getAct_dutylocationname());
                                i.setClass(DarAssignmentActivity.this, DarLocationAndTask.class);
                                startActivity(i);
                           /* List<String> assign_report_idassign_report_id=response.body().getResult().getAssignmentReportId();
                            assign_report_idd=assign_report_idassign_report_id;*/
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
                        Failed();
                        log.error("API failure");
                    }
                }

                @Override
                public void onFailure(Call<DarAssignmentLocationReportResponse> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.d("Dar Report", t.getMessage());
                    log.error(t.getMessage());
                    Failed();
                    log.error("API failure");

                }
            });
        } else {

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            log.error("No Internet");
            NoInternetDialog();
        }
    }



    public void LogoutandsubmitEndMileage(String Start_mileage, String Id_colunn) throws IOException {
        MilageDialog milageDialog = new MilageDialog();

        if (AssignmentSelectionId != 500) {
            if (isNetworkConnected()) {
                milageDialog.EndMilageDialogShift(DarAssignmentActivity.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.deviceId), "Yes", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
            } else {
                milageDialog.EndMilageDialogShift(DarAssignmentActivity.this, String.valueOf(TPApp.custId), String.valueOf(TPApp.deviceId), "No", Id_colunn, Start_mileage, inTime, preference.getString("AssignId"), TPApp.getVehicleid(), TPApp.getVehicleType());
            }

        } else {
            MileageDialog(Start_mileage, Id_colunn);
        }
    }


    public void AssignmentLogout(String startTime, String endTime, String id, String assignReportId, String Id_coulumn, String StartMileage) throws IOException {
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

                    if (response.isSuccessful() && response.code()==200) {
                        preference.putString("AssSubId", "1");
                        log.info("Assignment Logout performed"+" "+DateUtil.getCurrentDateTime1());
                        if (response.body().getResult() != null) {
                            if (response.body().getResult().getResult() != null) {
                                try {
                                    _sjpdlogout(Id_coulumn, StartMileage);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                Failed();
                                log.info(response.body().getResult());
                            }
                        } else {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Failed();
                            log.info("response not getting");
                        }

                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Failed();
                        //  Failed();  Dialog();
                    }
                }

                @Override
                public void onFailure(Call<DarAssignmentreportResponse> call, Throwable t) {
                    Log.d("Dar Report", t.getMessage());
                    log.error(t.getMessage());
                    Failed();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            });

        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            log.error("No Internet");
            NoInterneTT();
        }

    }
    private void findLoc() {
        new MyTracker(DarAssignmentActivity.this, this).track();
        // new GetLocation1(MainActivity.this, this).track();
    }
    @Override
    public void whereIAM(ADLocation loc) {
        if (loc!=null) {
             String latitude = String.valueOf(loc.lat);
             String longitude = String.valueOf(loc.longi);
             //System.out.println("==========>"+latitude+"  "+ longitude);
             Log.d("Curent Lat Long: ","==========>"+latitude+"  "+ longitude);
            preference.putString("LAT", latitude);
            preference.putString("LONGI", longitude);
            //String[] split = loc.address.split(",");

        }


    }

     /* int Lid = 0;
        Lid = locationDetails.get(locationIndex).getIdAssgLoc();*/

    //  finish();
                          /*  if (inTime!=null){

                                _sjpdInsert(inTime,DateUtil.getCurrentDateTime1());

                            }else {

                                Intent serviceIntent = new Intent(DarAssignmentActivity.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeAssignment());
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                if (assin_id !=0){
                                    serviceIntent.putExtra("ID",String.valueOf(assin_id));
                                }
                                else {
                                    serviceIntent.putExtra("ID","");
                                }
                                serviceIntent.putExtra("WITCH","One");
                                EDarServiceJobIntentTask.enqueueWork(DarAssignmentActivity.this, serviceIntent);
                               // finish();
                            }
*/
    /* *//* Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                            intent.setClass(AdministrativeForm.this, HomeActivity.class);
                            startActivity(intent);*//*
                            dialog.dismiss();
                            String  Id_colunn=  sharedPreferences.getString("coulmunId","");
                            String  Start_mileage= TPApp.getMileage();

                            MilageDialog milageDialog= new MilageDialog();

                            if (isNetworkConnected())
                            {
                                milageDialog.EndMilageDialogShift(AdministrativeForm.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.deviceId),"Yes",Id_colunn,Start_mileage,inTime);
                            }
                            else
                            {
                                milageDialog.EndMilageDialogShift(AdministrativeForm.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.deviceId),"No",Id_colunn,Start_mileage,inTime);
                            }*/

      /* Intent serviceIntent = new Intent(DarAssignmentActivity.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
        serviceIntent.putExtra("END", "");
        serviceIntent.putExtra("ID", String.valueOf(x));
        serviceIntent.putExtra("assignment_report_id",TPApplication.getInstance().getAssignReportId());
        serviceIntent.putExtra("WITCH", "Two");
        EDarServiceJobIntentTask.enqueueWork(DarAssignmentActivity.this, serviceIntent);*/
/*if (AssignementReportId.equals("0")  || AssignementReportId.equals("") )
            {
                callAssignLocationAPI(DateUtil.getCurrentDateTime1(), "", String.valueOf(x), TPApplication.getInstance().getAssignReportId()+1, "", String.valueOf(Lid), DarAssignmentActivity.this);

            }
            else {*/
       /*if (AssignmentId_API!=assin_id && AssignmentId_API==0)
       {
           callAssignmentReport(DateUtil.getCurrentDateTime1(), "", String.valueOf(assin_id), "", String.valueOf(Lid));
       }
        if (AssignmentId_API!=assin_id && AssignmentId_API!=0)
        {

            Logoutassignment();
            callAssignmentReport(DateUtil.getCurrentDateTime1(), "", String.valueOf(assin_id), "", String.valueOf(Lid));
        }*/

   /* public  void AssignmentLocationLogout(int y)
    {
        Intent serviceIntent = new Intent(DarAssignmentActivity.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", "");
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID", String.valueOf(y));
        serviceIntent.putExtra("assignment_report_id",TPApplication.getInstance().getAssignReportId());
        serviceIntent.putExtra("WITCH", "Two");
        EDarServiceJobIntentTask.enqueueWork(DarAssignmentActivity.this, serviceIntent);
    }*/

      /* Intent serviceIntent = new Intent(DarAssignmentActivity.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("END","");
        serviceIntent.putExtra("assignment_report_id","");
        if (assin_idd !=0){
            serviceIntent.putExtra("ID",String.valueOf(assin_idd));
        }
        else {
            serviceIntent.putExtra("ID","");
        }
        serviceIntent.putExtra("WITCH","One");
        EDarServiceJobIntentTask.enqueueWork(DarAssignmentActivity.this, serviceIntent);*/

     /* if (assign_report_idd.size()>0)
                    {
                        for (int j=0;j<assign_report_idd.size();j++)
                        {
                            String as=assign_report_idd.get(j);
                            TPApplication.getInstance().setAssLocRecId(as);
                            Log.d("entLocationReportId",assign_report_idd.get(j));
                        }

                    }*/

/*
    public void _sjpdInsert1(String loginTime, String logOutTime, int id) {
        */
    /* progressDialog = ProgressDialog.show(DarAssignmentActivity.this, "", "Saving...");*//*

        SJPDJson_rpc sjpdJson_rpc = new SJPDJson_rpc();
        ParamSjpd paramSjpd = new ParamSjpd();
        ArrayList<SJPDModel> sjpdModels = new ArrayList<>();

        SJPDModel sjpdModel = new SJPDModel();
        sjpdModel.setLogin(loginTime);
        sjpdModel.setLogout(logOutTime);
        sjpdModel.setUserId(String.valueOf(TPApplication.getInstance().userId));

        sjpdModels.add(sjpdModel);

        paramSjpd.setCustId(TPApplication.getInstance().userId);
        paramSjpd.setDetails(sjpdModels);

        sjpdJson_rpc.setJsonrpc("2.0");
        sjpdJson_rpc.setId("82F85DB43CBF6");
        sjpdJson_rpc.setMethod("sjpdFormInsert");
        sjpdJson_rpc.setParams(paramSjpd);
        System.out.println("RequestObj**" + new Gson().toJson(sjpdJson_rpc));
        if (isNetworkConnected()) {
            try {
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertSJPD(sjpdJson_rpc).enqueue(new Callback<DarTicketResponse>() {
                    @Override
                    public void onResponse(Call<DarTicketResponse> call, Response<DarTicketResponse> response) {
                        */
/*if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }*//*

                        if (response.isSuccessful() && response.code() == 200) {
                            Log.d("DarAssignment", String.valueOf(response.raw()));
                            Intent serviceIntent = new Intent(DarAssignmentActivity.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", "");
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            if (!TPApplication.getInstance().getAssignReportId().isEmpty())
                                serviceIntent.putExtra("assignment_report_id", TPApplication.getInstance().getAssignReportId());
                            else
                                serviceIntent.putExtra("assignment_report_id", "");
                            serviceIntent.putExtra("ID", "");
                            serviceIntent.putExtra("WITCH", "One");
                            EDarServiceJobIntentTask.enqueueWork(DarAssignmentActivity.this, serviceIntent);
                            */
    /* finish();*//*

                        }
                    }

                    @Override
                    public void onFailure(Call<DarTicketResponse> call, Throwable t) {

                        log.error("DarAssignment " + t.getMessage());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }

        } else {
            //Toast.makeText(DarAssignmentActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }


    }

    public void Logoutassignment() {
        Intent serviceIntent = new Intent(DarAssignmentActivity.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", "");
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        if (!TPApplication.getInstance().getAssignReportId().isEmpty())
            serviceIntent.putExtra("assignment_report_id", TPApplication.getInstance().getAssignReportId());
        else
            serviceIntent.putExtra("assignment_report_id", "");
        serviceIntent.putExtra("ID", "");
        serviceIntent.putExtra("WITCH", "One");
        EDarServiceJobIntentTask.enqueueWork(DarAssignmentActivity.this, serviceIntent);
    }



    public void Logout() {
        Intent serviceIntent = new Intent(DarAssignmentActivity.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", "");
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        if (!TPApplication.getInstance().getAssignReportId().isEmpty())
            serviceIntent.putExtra("assignment_report_id", TPApplication.getInstance().getAssignReportId());
        else
            serviceIntent.putExtra("assignment_report_id", "");
        serviceIntent.putExtra("ID", "");
        serviceIntent.putExtra("WITCH", "One");
        EDarServiceJobIntentTask.enqueueWork(DarAssignmentActivity.this, serviceIntent);
    }
*/


}
