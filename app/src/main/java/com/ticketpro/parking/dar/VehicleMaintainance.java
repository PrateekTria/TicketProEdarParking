package com.ticketpro.parking.dar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.DarAssignmentLocationJob;
import com.ticketpro.parking.dar.model.DarDutyReportJob;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.DarTicketResult;
import com.ticketpro.parking.dar.model.SignCheckModel;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.VehMaintenanceModel;
import com.ticketpro.parking.dar.model.params.ParamSignCheck;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.params.ParamsVehMaintenance;
import com.ticketpro.parking.dar.model.request.SignCheckJson_rpc;
import com.ticketpro.parking.dar.model.request.TaskReport_rpc;
import com.ticketpro.parking.dar.model.request.VehMaintenanceJson_rpc;
import com.ticketpro.parking.service.EDarServiceJobIntentTask;
import com.ticketpro.util.DateUtil;
import com.ticketpro.util.Preference;
import com.ticketpro.util.TPUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleMaintainance extends BaseActivityImpl implements MyTracker.ADLocationListener, Validator.ValidationListener {
    @NotEmpty
    private EditText _note;
    @NotEmpty
    private EditText _location;
    private Button  _btnGPS;
    private Button _btnBack, _btnSave, _btnEndShift, _btnLogOut;
    private Validator validator;
    private ProgressDialog progressDialog;
    private RadioGroup rG_SeniorAdvised;
    private RadioGroup rG_VehicleChange;
    private RadioButton rB_Yes_SA,rB_No_SA,rB_Yes_VC,rB_No_VC;
    String  SeniorAdvised=" ";
    String  VehicleChange=" ";
    private int asgId = 0;
    private int locId = 0;
    private int taskId = 0;
    private Preference preference;
    private String inTime = null;
    DarAssignmentLocationJob darAssignmentLocationJob= new DarAssignmentLocationJob();
    DarDutyReportJob darDutyReportJob= new DarDutyReportJob();
    DarSJPDlogoutJob darSJPDlogoutJob= new DarSJPDlogoutJob();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_maintainance);
        setLogger(VehicleMaintainance.class.getName());
        validator = new Validator(this);
        validator.setValidationListener(this);
        Intent it = getIntent();
        asgId = it.getIntExtra("ASG_ID",0);
        locId = it.getIntExtra("LOCATION_ID",0);
        taskId = it.getIntExtra("TASK_ID",0);
        preference = Preference.getInstance(VehicleMaintainance.this);
        
        inTime = preference.getString("IN_DATE");
        _location = findViewById(R.id.vm_etLocation);
        _btnGPS = findViewById(R.id.vm_btn_gps);
        _btnSave = findViewById(R.id.vm_btn_save);
        _btnEndShift = findViewById(R.id.vm_btnEndShift);
        _btnLogOut = findViewById(R.id.vm_btn_logout);
        _btnBack = findViewById(R.id.vm_btnBack);
        rG_SeniorAdvised=findViewById(R.id.rg_sn_sa);
        rG_VehicleChange=findViewById(R.id.rg_vm_vc);
        rB_Yes_SA=findViewById(R.id.rb_vm_saP);
        rB_No_SA=findViewById(R.id.rb_vm_saN);
        rB_Yes_VC=findViewById(R.id.rb_vc_vmP);
        rB_No_VC=findViewById(R.id.rb_vc_vmN);
        _note=findViewById(R.id.veh_maintain_note);
       /* _note.setImeOptions(EditorInfo.IME_ACTION_DONE);
        _note.setRawInputType(InputType.TYPE_CLASS_TEXT);
        _note.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        _note.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});*/
        _btnGPS.setOnClickListener(v -> findLoc());
        _btnSave.setOnClickListener(v-> validator.validate());
        rG_SeniorAdvised.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.rb_vm_saP)
                {
                    SeniorAdvised="Yes";
                } else if (i==R.id.rb_vm_saN) {
                    SeniorAdvised="No";
                }
                else {
                    SeniorAdvised="No";
                }


            }
        });
        rG_VehicleChange.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.rb_vc_vmP)
                {
                    VehicleChange="Yes";
                } else if (i==R.id.rb_vc_vmN) {
                    VehicleChange="No";
                }
                else {
                    VehicleChange="No";
                }


            }
        });

        _btnBack.setOnClickListener(v -> {
            try {
                callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
            } catch (IOException e) {
                e.printStackTrace();
            }

          /*  Intent serviceIntent = new Intent(VehicleMaintainance.this, EDarServiceJobIntentTask.class);
            serviceIntent.putExtra("START", "");
            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
            serviceIntent.putExtra("ID",String.valueOf(locId));
            serviceIntent.putExtra("WITCH","Three");
            serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
            EDarServiceJobIntentTask.enqueueWork(VehicleMaintainance.this, serviceIntent);
            TPApplication.getInstance().setDarStartTimeTask(null);

            finish();*/
        });
        _btnEndShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new iOSDialogBuilder(VehicleMaintainance.this)
                        .setSubtitle("SJPD Radio logout \n Performed?")
                        .setPositiveListener("Yes", new iOSDialogClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(iOSDialog dialog) {
                                darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(),VehicleMaintainance.this,TPApplication.getInstance().getDarParkingdutyreportId());
                                darAssignmentLocationJob.callAssignLocationAPI("",DateUtil.getCurrentDateTime1(),String.valueOf(asgId),TPApplication.getInstance().getAssignReportId(),TPApplication.getInstance().getAssLocRecId(),VehicleMaintainance.this);
                                darSJPDlogoutJob._sjpdlogout(VehicleMaintainance.this);
                                Intent serviceIntent = new Intent(VehicleMaintainance.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", "");
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID",String.valueOf(locId));
                                serviceIntent.putExtra("WITCH","Three");
                                serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
                                EDarServiceJobIntentTask.enqueueWork(VehicleMaintainance.this, serviceIntent);

                                TPApplication.getInstance().setDarStartTimeTask(null);
                               /* Intent serviceIntent1 = new Intent(VehicleMaintainance.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", String.valueOf(asgId));
                                serviceIntent.putExtra("WITCH", "Two");
                                EDarServiceJobIntentTask.enqueueWork(VehicleMaintainance.this, serviceIntent1);*/

                                dialog.dismiss();

                                String  Id_colunn=" ";
                                if (!preference.getString("coulmunId").isEmpty())
                                {
                                     Id_colunn=preference.getString("coulmunId");
                                }

                                 String  Start_mileage= TPApp.getMileage();


                                 MilageDialog milageDialog= new MilageDialog();

                                try {
                                    if (isNetworkConnected())
                                    {
                                        milageDialog.EndMilageDialogShift(VehicleMaintainance.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                    }
                                    else
                                    {
                                        milageDialog.EndMilageDialogShift(VehicleMaintainance.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //   dialog.dismiss();

                           /* Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                            intent.setClass(SignCheck.this, HomeActivity.class);
                            startActivity(intent);*/


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
        });

         _btnLogOut.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 new iOSDialogBuilder(VehicleMaintainance.this)
                         .setSubtitle("SJPD Radio logout \n Performed?")
                         .setPositiveListener("Yes", new iOSDialogClickListener() {
                             @RequiresApi(api = Build.VERSION_CODES.M)
                             @Override
                             public void onClick(iOSDialog dialog) {
                                 darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(),VehicleMaintainance.this,TPApplication.getInstance().getDarParkingdutyreportId());
                                 darAssignmentLocationJob.callAssignLocationAPI("",DateUtil.getCurrentDateTime1(),String.valueOf(asgId),TPApplication.getInstance().getAssignReportId(),TPApplication.getInstance().getAssLocRecId(),VehicleMaintainance.this);
                                 darSJPDlogoutJob._sjpdlogout(VehicleMaintainance.this);

                                 Intent serviceIntent = new Intent(VehicleMaintainance.this, EDarServiceJobIntentTask.class);
                                 serviceIntent.putExtra("START", "");
                                 serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                 serviceIntent.putExtra("ID",String.valueOf(locId));
                                 serviceIntent.putExtra("WITCH","Three");
                                 serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
                                 EDarServiceJobIntentTask.enqueueWork(VehicleMaintainance.this, serviceIntent);

                                 TPApplication.getInstance().setDarStartTimeTask(null);
                              /*   Intent serviceIntent1 = new Intent(VehicleMaintainance.this, EDarServiceJobIntentTask.class);
                                 serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                                 serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                 serviceIntent.putExtra("ID", String.valueOf(asgId));
                                 serviceIntent.putExtra("WITCH", "Two");
                                 EDarServiceJobIntentTask.enqueueWork(VehicleMaintainance.this, serviceIntent1);*/


                                 dialog.dismiss();

                                 String  Id_colunn=" ";
                                 if (!preference.getString("coulmunId").isEmpty())
                                 {
                                     Id_colunn=preference.getString("coulmunId");
                                 }
                                 String  Start_mileage= TPApp.getMileage();


                                 MilageDialog milageDialog= new MilageDialog();

                                 try {
                                     if (isNetworkConnected())
                                     {
                                         milageDialog.EndMilageDialogLogout(VehicleMaintainance.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                     }
                                     else
                                     {
                                         milageDialog.EndMilageDialogLogout(VehicleMaintainance.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                     }
                                 } catch (IOException e) {
                                     e.printStackTrace();
                                 }
                                 //   dialog.dismiss();

                           /* Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                            intent.setClass(SignCheck.this, HomeActivity.class);
                            startActivity(intent);*/


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
         });
    }

    private void findLoc() {
        new GetLocation1(VehicleMaintainance.this, this).track();
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
        _location.setText(TPUtility.getFullAddress(gpsAddress));
    }

    @Override
    public void onValidationSucceeded() {
        if (SeniorAdvised.isEmpty() || SeniorAdvised.equals(" ")|| VehicleChange.isEmpty() || VehicleChange.equals(" "))
        {
            Dialog();
         //   Toast.makeText(VehicleMaintainance.this, "Please Select Below ", Toast.LENGTH_SHORT).show();
        }else {
            try {
                _save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
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
    public void _save() throws IOException {
        int x=0;
        progressDialog = ProgressDialog.show(VehicleMaintainance.this, "", "Inserting...");
        VehMaintenanceJson_rpc rpc = new VehMaintenanceJson_rpc();
        ParamsVehMaintenance paramsVehMaintenance = new ParamsVehMaintenance();
        List<VehMaintenanceModel> aList = new ArrayList<>();
        VehMaintenanceModel model = new VehMaintenanceModel();

        model.setLocationId(_location.getText().toString());
       /* model.setSigncheckDdElemId(String.valueOf(id));
        model.setPermitNumber(_mPermit.getText().toString());
        model.setEnforceableInput(ForceableType);*/
        model.setUserId(String.valueOf(TPApp.userId));
        model.setDeviceid(String.valueOf(TPApp.deviceId));
        model.setNotes(_note.getText().toString());
        model.setEquipmentId(TPApp.getEquipment());
        model.setSubEquipmentId(TPApp.getEquipmentChild());
        model.setVehicle(TPApp.getVehicleType());
        model.setMileage(TPApp.getMileage());
        model.setTaskDate(DateUtil.getCurrentDateTime1());
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
        model.setDisinfecting(TPApp.getDisinfecting());
        model.setSeniorAdvised(SeniorAdvised);
        model.setVehicleChanged(VehicleChange);
        model.setVdr(TPApp.getVdr());
        model.setAssignmentId(asgId);
        model.setLocationId(String.valueOf(locId));
        model.setTaskId(String.valueOf(taskId));
        model.setActionId("1");
        /* model.setForceable(ForceableType);*/
        model.setAppId((int) VehMaintenanceModel.getNextPrimaryId());
        aList.add(model);
        paramsVehMaintenance.setDetails(aList);
        paramsVehMaintenance.setCustId(TPApp.custId);
        rpc.setParams(paramsVehMaintenance);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("darVehicleMaintenanceDetailInsert");
         System.out.println("RequestObj**"+new Gson().toJson(rpc));
         log.info(new Gson().toJson(rpc));
     //   VehMaintenanceModel.insertVehMaintenanceModel(model);

        if (isNetworkConnected()){
            try {

                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                api.insertvehmaintenace(rpc).enqueue(new Callback<DarTicketResponse>() {
                    @Override
                    public void onResponse(Call<DarTicketResponse> call, Response<DarTicketResponse> response) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (response.code() == 200 && response.isSuccessful()) {

                            if (response.body().getResult()!= null)
                            {
                               if (response.body().getResult().getResult()== true)
                               {
                                   Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
                           /* _spinnerText.setHint("Select Activity");
                            _mPermit.setText("");
                            _mEnf.setText("");*/
                                   _location.setText("");
                                   _note.setText("");
                                   rG_VehicleChange.clearCheck();
                                   rG_SeniorAdvised.clearCheck();
                               }else {
                                   Failed();
                                   log.error("response incorrect");
                               }
                            }else {
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
            try {
               // SignCheckModel.insertSignCheckModel(model);
                VehMaintenanceModel.insertVehMaintenanceModel(model);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
           /* _spinnerText.setHint("Select Activity");
            _mPermit.setText("");
            _mEnf.setText("");*/
            _location.setText("");
            _note.setText("");
            rG_VehicleChange.clearCheck();
            rG_SeniorAdvised.clearCheck();
          /*  RG_Enf.clearCheck();*/
        }

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

    public  void Dialog(){

        new iOSDialogBuilder(VehicleMaintainance.this)
                .setSubtitle("Please Checked required items!")
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

     /*   Intent serviceIntent = new Intent(VehicleMaintainance.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START", "");
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID",String.valueOf(locId));
        serviceIntent.putExtra("WITCH","Three");
        serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
        EDarServiceJobIntentTask.enqueueWork(VehicleMaintainance.this, serviceIntent);
        TPApplication.getInstance().setDarStartTimeTask(null);

        finish();
        super.onBackPressed();*/
    }


    public void callTaskReport(String startTime, String endTime, String id, String TaskRecordId, String taskid) throws IOException {
        progressDialog = ProgressDialog.show(VehicleMaintainance.this, "", "Please Wait...");
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

        new iOSDialogBuilder(VehicleMaintainance.this)
                .setSubtitle("Please try again")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();

                    }
                })
                .build().show();

    }

    /* *//* final DarTicketResult result = response.body().getResult();
                            final List<Integer> appId = result.getAppId();
                            if (appId != null && appId.size()>0) {
                                for (int i = 0; i < appId.size(); i++) {
                                    try {
                                        VehMaintenanceModel.removeById(appId.get(i).intValue());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }*//*
                            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
                           *//* _spinnerText.setHint("Select Activity");
                            _mPermit.setText("");
                            _mEnf.setText("");*//*
                            _location.setText("");
                            _note.setText("");
                            rG_VehicleChange.clearCheck();
                            rG_SeniorAdvised.clearCheck();
                          *//*  RG_Enf.clearCheck();*/
}
