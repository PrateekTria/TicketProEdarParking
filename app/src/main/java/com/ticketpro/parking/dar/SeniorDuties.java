package com.ticketpro.parking.dar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.gson.Gson;
import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGenerator;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.HomeActivity;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.DarSeniorDutiesElements;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.DarTicketResult;
import com.ticketpro.parking.dar.model.FieldContactDetails;
import com.ticketpro.parking.dar.model.SeniorDutiesModel;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.params.ParamSeniorDuties;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.request.SeniorDutiesJSON_rpc;
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

public class SeniorDuties extends BaseActivityImpl implements SeniorDutiesList{
    private Button _btnBack;
    private Button _mEndShift;
    private Button _mLogout;
    private Button _mSave;
    private int assignmentId;
    private ArrayList<DarSeniorDutiesElements> _mList = new ArrayList<>();
    private RecyclerView mRecyclerView;

    private ProgressDialog progressDialog;
    SearchView searchView;
    SeniorDutyAdapter adapter;
    private ArrayList<DarSeniorDutiesElements> mItemsList = new ArrayList<>();
    private String inTime = null;
    private Preference preference;
    DarSJPDlogoutJob darSJPDlogoutJob= new DarSJPDlogoutJob();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_duties);
        setLogger(SeniorDuties.class.getName());
        Intent intent = new Intent();
        assignmentId = intent.getIntExtra("ASSIGNMENT",0);
        _btnBack = findViewById(R.id.bc_btnBack);
        //_btnBack.setOnClickListener(v -> finish());
        _mEndShift = findViewById(R.id.bc_btnEndShift);
        _mLogout = findViewById(R.id.bc_btn_logout);
        searchView=findViewById(R.id.search_view);
        _mSave = findViewById(R.id.bc_btn_save);

        preference = Preference.getInstance(SeniorDuties.this);
        inTime = preference.getString("IN_DATE");

        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setQueryHint("Search Duty");
        _btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent serviceIntent = new Intent(SeniorDuties.this, EDarServiceJobIntentTask.class);
                serviceIntent.putExtra("START", "");
                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                serviceIntent.putExtra("ID", assignmentId);
                serviceIntent.putExtra("WITCH", "Three");
                serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                EDarServiceJobIntentTask.enqueueWork(SeniorDuties.this, serviceIntent);
                finish();*/

                try {
                    callTaskReport("",DateUtil.getCurrentDateTime1(),String.valueOf(assignmentId),TPApplication.getInstance().getDarTaskReoprtId(),"6");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        _mEndShift.setOnClickListener(v -> {
           /* Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntent.class);
            if (isNetworkConnected()) {
                EDarServiceJobIntent.enqueueWork(TaskForm22500.this, serviceIntent);
            }*/

            new iOSDialogBuilder(SeniorDuties.this)
                    .setSubtitle("SJPD Radio logoff \n Performed?")
                    .setPositiveListener("Yes", new iOSDialogClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(iOSDialog dialog) {

                            darSJPDlogoutJob._sjpdlogout(SeniorDuties.this);
                            Intent serviceIntent = new Intent(SeniorDuties.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", "");
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", assignmentId);
                            serviceIntent.putExtra("WITCH", "Three");
                            serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                            EDarServiceJobIntentTask.enqueueWork(SeniorDuties.this, serviceIntent);
                            TPApplication.getInstance().setDarStartTimeTask(null);
                          /*  Intent serviceIntent1 = new Intent(OffsiteNonEnforcement.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(id_assg_loc));
                            serviceIntent.putExtra("WITCH", "Two");
                            EDarServiceJobIntentTask.enqueueWork(OffsiteNonEnforcement.this, serviceIntent1);*/

                            //  dialog.dismiss();

                            dialog.dismiss();
                            String  Id_colunn=preference.getString("coulmunId");
                            String  Start_mileage= TPApp.getMileage();

                            MilageDialog milageDialog= new MilageDialog();

                            try {
                                if (isNetworkConnected())
                                {
                                    milageDialog.EndMilageDialogShift(SeniorDuties.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                }
                                else
                                {
                                    milageDialog.EndMilageDialogShift(SeniorDuties.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
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
        _mLogout.setOnClickListener(v -> {
            try {




                new iOSDialogBuilder(SeniorDuties.this)
                        .setSubtitle("SJPD Radio logoff \n Performed?")
                        .setPositiveListener("Yes", new iOSDialogClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(iOSDialog dialog) {

                                darSJPDlogoutJob._sjpdlogout(SeniorDuties.this);
                                Intent serviceIntent = new Intent(SeniorDuties.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", "");
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", assignmentId);
                                serviceIntent.putExtra("WITCH", "Three");
                                serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
                                EDarServiceJobIntentTask.enqueueWork(SeniorDuties.this, serviceIntent);

                                dialog.dismiss();
                                //finish();

                                String  Id_colunn=preference.getString("coulmunId");
                                String  Start_mileage= TPApp.getMileage();

                                MilageDialog milageDialog= new MilageDialog();

                                try {
                                    if (isNetworkConnected())
                                    {
                                        milageDialog.EndMilageDialogLogout(SeniorDuties.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                    }
                                    else
                                    {
                                        milageDialog.EndMilageDialogLogout(SeniorDuties.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
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
        mRecyclerView = findViewById(R.id.nonf_list);
        LinearLayoutManager manager = new LinearLayoutManager(SeniorDuties.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);

        _mList = DarSeniorDutiesElements._getList(TPApp.custId);

        if (_mList.size()>0) {
            searchView.setVisibility(View.VISIBLE);
            adapter = new SeniorDutyAdapter(_mList, this,SeniorDuties.this);
            mRecyclerView.setAdapter(adapter);
        }else {
             searchView.setVisibility(View.GONE);
        }

        _mSave.setOnClickListener(v -> {
            StringBuilder sb = new StringBuilder();
            if (mItemsList.size()>0){
                for (int i = 0; i < mItemsList.size(); i++) {
                    final DarSeniorDutiesElements ds = mItemsList.get(i);
                    if (ds.isSelected()) {
                        sb.append(ds.getId()).append(",");
                    }
                }
                if (sb.length()>2) {
                    sb.deleteCharAt(sb.length() - 1).toString();
                    try {
                        __save(sb);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getInstance(), "Select duties", Toast.LENGTH_SHORT).show();
                }
            }
            //__save();
        });
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

    }

    private void __save(StringBuilder sb) throws IOException {
        progressDialog = ProgressDialog.show(SeniorDuties.this, "", "Inserting...");

        SeniorDutiesJSON_rpc rpc = new SeniorDutiesJSON_rpc();
        ParamSeniorDuties param = new ParamSeniorDuties();
        List<SeniorDutiesModel> aList = new ArrayList<>();
        SeniorDutiesModel model = new SeniorDutiesModel();

        model.setSeniorDutyVal(sb.toString());
        if (!preference.getString("coulmunId").isEmpty() || !preference.getString("coulmunId").equals(""))
            model.setMileageId(preference.getString("coulmunId"));
        else
            model.setMileageId("0");

        if (TPApplication.getInstance().getDarTaskReoprtId() != null) {
            model.setDarTaskReportId(TPApplication.getInstance().getDarTaskReoprtId());
        } else {
            model.setDarTaskReportId("");
        }
        model.setNonInforcementDdElementId("6");
        model.setEquipmentId(TPApp.getEquipment());
        model.setSubEquipmentId(TPApp.getEquipmentChild());
        model.setVehicle(TPApp.getVehicleType());
        model.setMileage(TPApp.getMileage());
        model.setDisinfecting(TPApp.getDisinfecting());
        model.setVdr(TPApp.getVdr());
        model.setAssignmentId(5);
        model.setDeviceid(String.valueOf(TPApp.deviceId));
        model.setAppId((int) SeniorDutiesModel.getNextPrimaryId());
        model.setUserId(String.valueOf(TPApp.userId));
        model.setDatetime(DateUtil.getCurrentDateTime1());
        aList.add(model);
        param.setDetails(aList);
        param.setCustId(TPApp.custId);
        rpc.setParams(param);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("OffSiteNonInforcementSeniorDutyFormInsert");
        System.out.println("RequestObj**"+new Gson().toJson(rpc));

        if (isNetworkConnected()){
            try {
                SeniorDutiesModel.insertSeniorDutiesModel(model);
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertSeniorDuties(rpc).enqueue(new Callback<DarTicketResponse>() {
                    @Override
                    public void onResponse(Call<DarTicketResponse> call, Response<DarTicketResponse> response) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            searchView.setQuery("", false);
                            searchView.clearFocus();
                        }
                        if (response.code() == 200 && response.isSuccessful()) {
                            final DarTicketResult result = response.body().getResult();
                            final List<Integer> appId = result.getAppId();
                            if (appId!=null && appId.size()>0) {
                                for (int i = 0; i < appId.size(); i++) {
                                    try {
                                        FieldContactDetails.removeById(appId.get(i).intValue());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
                            _mList = DarSeniorDutiesElements._getList(TPApp.custId);
                            if (_mList.size()>0) {
                                adapter = new SeniorDutyAdapter(_mList, getInstance(),SeniorDuties.this);
                                mRecyclerView.setAdapter(adapter);
                            }
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
        }else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            try {
                SeniorDutiesModel.insertSeniorDutiesModel(model);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
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
    public void pushElement(ArrayList<DarSeniorDutiesElements> myItems) {

        if (myItems!=null && myItems.size()>0){
            mItemsList = myItems;
        }
    }

    private void filter(String text) {

        ArrayList<DarSeniorDutiesElements> filteredlist = new ArrayList<DarSeniorDutiesElements>();


        for (DarSeniorDutiesElements item : _mList) {

            if (item.getMenuName().toLowerCase().contains(text.toLowerCase())) {

                filteredlist.add(item);
            }
        }
        /* if (filteredlist.isEmpty()) {

         *//* Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();*//*
        } else {

          // adapter.filterList(filteredlist);
        }*/
        adapter.filterList(filteredlist);
    }

    @Override
    public void onBackPressed() {
        try {
            callTaskReport("",DateUtil.getCurrentDateTime1(),String.valueOf(assignmentId),TPApplication.getInstance().getDarTaskReoprtId(),"6");
        } catch (IOException e) {
            e.printStackTrace();
        }

      /*  Intent serviceIntent = new Intent(SeniorDuties.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", "");
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID", assignmentId);
        serviceIntent.putExtra("WITCH", "Three");
        serviceIntent.putExtra("TaskRecordId", TPApplication.getInstance().getDarTaskReoprtId());
        EDarServiceJobIntentTask.enqueueWork(SeniorDuties.this, serviceIntent);
        finish();
        super.onBackPressed();*/
    }



    public  void callTaskReport(String startTime, String endTime, String id,String TaskRecordId,String taskid) throws IOException {
        progressDialog = ProgressDialog.show(SeniorDuties.this, "", "Please Wait...");
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
                }
            });

        }
        else{
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            log.error("No Internet");
        }
    }
}