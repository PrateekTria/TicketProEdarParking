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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
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
import com.ticketpro.model.TicketPicture;
import com.ticketpro.model.TicketResponse;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.HomeActivity;
import com.ticketpro.parking.activity.PhotosActivity;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.activity.TakePictureActivity;
import com.ticketpro.parking.activity.ViewImageActivity;
import com.ticketpro.parking.activity.WriteTicketActivity;
import com.ticketpro.parking.api.WriteTicketNetworkCalls;
import com.ticketpro.parking.dar.model.Admin;
import com.ticketpro.parking.dar.model.DarAdminDropdown;
import com.ticketpro.parking.dar.model.DarAssignmentLocationJob;
import com.ticketpro.parking.dar.model.DarDutyReportJob;
import com.ticketpro.parking.dar.model.DarServiceRequestDropDown;
import com.ticketpro.parking.dar.model.DarTaskReportResponse;
import com.ticketpro.parking.dar.model.DarTicketResponse;
import com.ticketpro.parking.dar.model.DarTicketResult;
import com.ticketpro.parking.dar.model.FieldContactDetails;
import com.ticketpro.parking.dar.model.ServiceRequestModel;
import com.ticketpro.parking.dar.model.TaskReportModel;
import com.ticketpro.parking.dar.model.params.ParamAdmin;
import com.ticketpro.parking.dar.model.params.ParamServiceRequest;
import com.ticketpro.parking.dar.model.params.ParamTaskReport;
import com.ticketpro.parking.dar.model.request.AdminJson_rpc;
import com.ticketpro.parking.dar.model.request.ServiceRequestJson_rpc;
import com.ticketpro.parking.dar.model.request.TaskReport_rpc;
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

public class ServiceRequest  extends BaseActivityImpl implements MyTracker.ADLocationListener, Validator.ValidationListener{
    private Button _mSave;
    private Button _mLogout;
    private Button _mEndShift;
    private TextView mSpinner;
    private int asgId = 0;
    private int locId = 0;
    private int taskId = 0;
    private ProgressDialog progressDialog;
    private Integer id = 0;
    private Button btnTakeImage;
    private Validator validator;
    @NotEmpty
    private EditText mLocation;
    private ImageView mPictureView;
    final int REQUEST_CODE = 101;
    private Button _btnBack;
    private Button mGpsbutton;
    private LinearLayout mLayout;
    @NotEmpty
    private EditText _note;
    String imagePath;
    SharedPreferences sharedPreferences;
    private Preference preference;
    private String inTime = null;
    DarAssignmentLocationJob darAssignmentLocationJob= new DarAssignmentLocationJob();
    DarDutyReportJob darDutyReportJob= new DarDutyReportJob();
    DarSJPDlogoutJob darSJPDlogoutJob= new DarSJPDlogoutJob();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);
        setLogger(ServiceRequest.class.getName());
        preference = Preference.getInstance(ServiceRequest.this);
        TPApplication.getInstance().setDarStartTimeTask(DateUtil.getCurrentDateTime1());
        inTime = preference.getString("IN_DATE");
        validator = new Validator(this);
        validator.setValidationListener(this);
        sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Intent it = getIntent();
        asgId = it.getIntExtra("ASG_ID", 0);
        locId = it.getIntExtra("LOCATION_ID", 0);
        taskId = it.getIntExtra("TASK_ID", 0);
        _mSave = findViewById(R.id.sr_btn_save);
        _mEndShift = findViewById(R.id.sr_btnEndShift);
        _mLogout = findViewById(R.id.sr_btn_logout);
        _btnBack = findViewById(R.id.sr_btnBack);
        mLocation = findViewById(R.id.srv_location);
        mGpsbutton = findViewById(R.id.srv_gpsbtn);
        mPictureView = findViewById(R.id.srv_image);
        btnTakeImage = findViewById(R.id.srv_take_btn);
        mLayout = findViewById(R.id.srv_top_layout);
        _note=findViewById(R.id.servicerequest_note);
        /*_note.setImeOptions(EditorInfo.IME_ACTION_DONE);
        _note.setRawInputType(InputType.TYPE_CLASS_TEXT);
        _note.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        _note.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});*/
        //mLayout.setVisibility(View.GONE);
        mGpsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findLoc();
            }
        });

     /*  mPictureView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent= new Intent();
               intent.putExtra("imagepath",imagePath);
               intent.setClass(ServiceRequest.this, ViewImageActivity.class);
               startActivity(intent);

           }
       });*/
        btnTakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(ServiceRequest.this, TakePictureActivity.class);
                i.putExtra("CitationNumber", 000000000);
                i.putExtra("ServiceRequest", true);
                startActivityForResult(i, REQUEST_CODE);
                mPictureView.setVisibility(View.VISIBLE);
            }
        });

        _btnBack.setOnClickListener(v -> {

            try {
                callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
            } catch (IOException e) {
                e.printStackTrace();
            }

           /* Intent serviceIntent = new Intent(ServiceRequest.this, EDarServiceJobIntentTask.class);
            serviceIntent.putExtra("START","");
            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
            serviceIntent.putExtra("ID",String.valueOf(locId));
            serviceIntent.putExtra("WITCH","Three");
            serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
            EDarServiceJobIntentTask.enqueueWork(ServiceRequest.this, serviceIntent);
            TPApplication.getInstance().setDarStartTimeTask(null);

            finish();*/
        });

        _mEndShift.setOnClickListener(v -> {


           /* Intent serviceIntent = new Intent(TaskForm22500.this, EDarServiceJobIntent.class);
            if (isNetworkConnected()) {
                EDarServiceJobIntent.enqueueWork(TaskForm22500.this, serviceIntent);
            }*/

            new iOSDialogBuilder(ServiceRequest.this)
                    .setSubtitle("SJPD Radio logoff \n Performed?")
                    .setPositiveListener("Yes", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {

                          /*  Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                            intent.setClass(ServiceRequest.this, HomeActivity.class);
                            startActivity(intent);*/
                            darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(),ServiceRequest.this,TPApplication.getInstance().getDarParkingdutyreportId());
                            darAssignmentLocationJob.callAssignLocationAPI("",DateUtil.getCurrentDateTime1(),String.valueOf(asgId),TPApplication.getInstance().getAssignReportId(),TPApplication.getInstance().getAssLocRecId(),ServiceRequest.this);
                            darSJPDlogoutJob._sjpdlogout(ServiceRequest.this);

                            Intent serviceIntent = new Intent(ServiceRequest.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START","");
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID",String.valueOf(locId));
                            serviceIntent.putExtra("WITCH","Three");
                            serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
                            EDarServiceJobIntentTask.enqueueWork(ServiceRequest.this, serviceIntent);

                            TPApplication.getInstance().setDarStartTimeTask(null);
                         /*   Intent serviceIntent1 = new Intent(ServiceRequest.this, EDarServiceJobIntentTask.class);
                            serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                            serviceIntent.putExtra("ID", String.valueOf(asgId));
                            serviceIntent.putExtra("WITCH", "Two");
                            EDarServiceJobIntentTask.enqueueWork(ServiceRequest.this, serviceIntent1);*/

                            dialog.dismiss();
                            String  Id_colunn=preference.getString("coulmunId");
                            String  Start_mileage= TPApp.getMileage();

                            MilageDialog milageDialog= new MilageDialog();

                            try {
                                if (isNetworkConnected())
                                {
                                    milageDialog.EndMilageDialogShift(ServiceRequest.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                }
                                else
                                {
                                    milageDialog.EndMilageDialogShift(ServiceRequest.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
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





                new iOSDialogBuilder(ServiceRequest.this)
                        .setSubtitle("SJPD Radio logoff \n Performed?")
                        .setPositiveListener("Yes", new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {
                               /* Intent intent = new Intent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                                intent.setClass(ServiceRequest.this, DarEquipmentNew.class);
                                startActivity(intent);*/
                                darDutyReportJob.callDutyReportAPI(TPApplication.getInstance().getDardutyId(),ServiceRequest.this,TPApplication.getInstance().getDarParkingdutyreportId());
                                darAssignmentLocationJob.callAssignLocationAPI("",DateUtil.getCurrentDateTime1(),String.valueOf(asgId),TPApplication.getInstance().getAssignReportId(),TPApplication.getInstance().getAssLocRecId(),ServiceRequest.this);
                                darSJPDlogoutJob._sjpdlogout(ServiceRequest.this);

                                Intent serviceIntent = new Intent(ServiceRequest.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START","");
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID",String.valueOf(locId));
                                serviceIntent.putExtra("WITCH","Three");
                                serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
                                EDarServiceJobIntentTask.enqueueWork(ServiceRequest.this, serviceIntent);

                                TPApplication.getInstance().setDarStartTimeTask(null);
                               /* Intent serviceIntent1 = new Intent(ServiceRequest.this, EDarServiceJobIntentTask.class);
                                serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeLocation());
                                serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
                                serviceIntent.putExtra("ID", String.valueOf(asgId));
                                serviceIntent.putExtra("WITCH", "Two");
                                EDarServiceJobIntentTask.enqueueWork(ServiceRequest.this, serviceIntent1);*/

                                dialog.dismiss();
                               // finish();

                                String  Id_colunn=preference.getString("coulmunId");
                                String  Start_mileage= TPApp.getMileage();

                                MilageDialog milageDialog= new MilageDialog();

                                try {
                                    if (isNetworkConnected())
                                    {
                                        milageDialog.EndMilageDialogLogout(ServiceRequest.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"Yes",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
                                    }
                                    else
                                    {
                                        milageDialog.EndMilageDialogLogout(ServiceRequest.this,String.valueOf(TPApp.custId),String.valueOf(TPApp.userId),"No",Id_colunn,Start_mileage,inTime,preference.getString("AssignId"),TPApp.getVehicleid(),TPApp.getVehicleType());
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
        mSpinner = findViewById(R.id.sr_activity_spinner);
        final ArrayList<DarServiceRequestDropDown> darAdminDropdowns = DarServiceRequestDropDown._getDarSchoolDropDownElementList(TPApp.custId);
        mSpinner.setOnClickListener(v -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(ServiceRequest.this);
            //builderSingle.setIcon(R.drawable.icon);
            builderSingle.setTitle("Select Activity");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ServiceRequest.this, android.R.layout.select_dialog_singlechoice);
            for (int i = 0; i < darAdminDropdowns.size(); i++) {
                final DarServiceRequestDropDown darAdminDropdown = darAdminDropdowns.get(i);
                String s = darAdminDropdown.getDdItem();
                if (s != null) {
                    arrayAdapter.add(s);
                }
            }
            builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                mSpinner.setText(arrayAdapter.getItem(which));
                mLayout.setVisibility(View.VISIBLE);
                darAdminDropdowns.forEach((n) -> {
                    if (n.getDdItem().equals(arrayAdapter.getItem(which)))
                        id = n.getId();
                    System.out.println("ID IS================>" + id);
                });
                dialog.dismiss();
            });
            builderSingle.show();
        });
        _mSave.setOnClickListener(v -> {

            if (id > 0 ) {
                validator.validate();
            } else {
                Toast.makeText(getInstance(), "Select Activity", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE: {

                     imagePath = data.getStringExtra("PicturePath");
                    int pictureIndex = data.getIntExtra("PictureIndex", 0);
                    if (imagePath != null) {
                        //activeTicket.getTicketPictures().get(pictureIndex).setImagePath(imagePath);
                        displayPictures(imagePath);
                    }
                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + requestCode);
            }
        }
    }

    private void displayPictures(String imagePath) {

        Glide.with(ServiceRequest.this)
                .load(imagePath)
                .into(mPictureView);
    }

    private void _saveDetails() throws IOException {
        progressDialog = ProgressDialog.show(ServiceRequest.this, "", "Inserting...");
        final ArrayList<TicketPicture> ticketPictures = TPApp.getActiveTicket().getTicketPictures();
        final ArrayList<String> picturePath = new ArrayList<>();
        TicketPicture ticketPicture = null;
        if (ticketPictures.size()>0) {
            for (TicketPicture tp : ticketPictures) {
                ticketPicture = tp;
                final String imagePath = ticketPicture.getImagePath();
                picturePath.add(imagePath);
                String filename = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                ticketPicture.setImageName(filename);
            }
        }
        ServiceRequestJson_rpc rpc = new ServiceRequestJson_rpc();
        ParamServiceRequest paramAdmin = new ParamServiceRequest();
        List<ServiceRequestModel> aList = new ArrayList<>();
        ServiceRequestModel model = new ServiceRequestModel();
        model.setServiceRequestDdElemId(String.valueOf(id));
        model.setEquipmentId(TPApp.getEquipment());
        model.setSubEquipmentId(TPApp.getEquipmentChild());
        model.setVehicle(TPApp.getVehicleType());
        model.setMileage(TPApp.getMileage());
        model.setDisinfecting(TPApp.getDisinfecting());
        model.setVdr(TPApp.getVdr());
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
        model.setNotes(_note.getText().toString());
        model.setAssignmentId(asgId);
        model.setLocationId(String.valueOf(locId));
        model.setTaskId(String.valueOf(taskId));
        //model.setActionId("0");
        model.setUserId(String.valueOf(TPApp.userId));
        model.setDeviceid(String.valueOf(TPApp.deviceId));
        model.setAppId((int) ServiceRequestModel.getNextPrimaryId());
        model.setLocation(mLocation.getText().toString());
        if (ticketPicture!=null) {
            model.setImageResolution(ticketPicture.getImageResolution());
            model.setImageSize(ticketPicture.getImageSize());
            //model.setImagePath(ticketPicture.getImagePath());
            model.setImageName(ticketPicture.getImageName());
        }
        aList.add(model);
        paramAdmin.setDetails(aList);
        paramAdmin.setCustId(TPApp.custId);
        rpc.setParams(paramAdmin);
        rpc.setJsonrpc("2.0");
        rpc.setId("82F85DB43CBF6");
        rpc.setMethod("darServiceRequestInsert");

        System.out.println("RequestObj**"+new Gson().toJson(rpc));
         log.info(new Gson().toJson(rpc).toString());

        if (isNetworkConnected()) {
            try {

                WriteTicketNetworkCalls.syncUploadImages(picturePath);
              //  ServiceRequestModel.insertServiceRequestModel(model);
                ApiRequest api = ServiceGenerator.createRx2Service(ApiRequest.class);
                Log.d("API",api.toString());
                api.insertServiceRequest(rpc).enqueue(new Callback<DarTicketResponse>() {
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
                                    mPictureView.setImageResource(R.drawable.image_not_found);
                                    _note.setText("");
                                    mLocation.setText("");
                                    mPictureView.setVisibility(View.GONE);
                                    Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();

                                }
                                else
                                {
                                    Failed();
                                    log.error("result false");
                                }
                            }else
                            {
                                Failed();
                                log.error("response incorrect");
                            }

                        }
                        else
                        {
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
        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            try {
                ServiceRequestModel.insertServiceRequestModel(model);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
            _note.setText("");
            mLocation.setText("");
            mPictureView.setVisibility(View.GONE);
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
        new GetLocation1(ServiceRequest.this, this).track();
    }

    @Override
    public void onBackPressed() {

        try {
            callTaskReport("", DateUtil.getCurrentDateTime1(), String.valueOf(asgId), TPApplication.getInstance().getDarTaskReoprtId(), "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Intent serviceIntent = new Intent(ServiceRequest.this, EDarServiceJobIntentTask.class);
        serviceIntent.putExtra("START","");
        serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
        serviceIntent.putExtra("ID",String.valueOf(locId));
        serviceIntent.putExtra("WITCH","Three");
        serviceIntent.putExtra("TaskRecordId",TPApplication.getInstance().getDarTaskReoprtId());
        EDarServiceJobIntentTask.enqueueWork(ServiceRequest.this, serviceIntent);
        TPApplication.getInstance().setDarStartTimeTask(null);

        finish();
        super.onBackPressed();*/
    }


    public void callTaskReport(String startTime, String endTime, String id, String TaskRecordId, String taskid) throws IOException {
        progressDialog = ProgressDialog.show(ServiceRequest.this, "", "Please Wait...");
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

        new iOSDialogBuilder(ServiceRequest.this)
                .setSubtitle("Please try again")
                .setPositiveListener("Ok", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();

                    }
                })
                .build().show();

    }

    @Override
    public void onValidationSucceeded() {
        try {
            _saveDetails();
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

    ///     log.info(response.body().getResult().getAppId().toString()+response.body().getResult().getResult());

                         /*   final DarTicketResult result = response.body().getResult();

                            final List<Integer> appId = result.getAppId();
                            if (appId != null && appId.size()>0) {
                                for (int i = 0; i < appId.size(); i++) {
                                    log.info(appId.get(0).toString());
                                    try {
                                        ServiceRequestModel.removeById(appId.get(i).intValue());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        log.error(e.getMessage());
                                    }
                                }
                            }else
                            {
                                log.error("null app id");
                            }*/
                           /* mPictureView.setImageResource(R.drawable.image_not_found);
                            _note.setText("");
                            mLocation.setText("");
                            mPictureView.setVisibility(View.GONE);
                            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();
*/
}