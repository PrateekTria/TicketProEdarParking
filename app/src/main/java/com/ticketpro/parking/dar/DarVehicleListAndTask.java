package com.ticketpro.parking.dar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGenerator;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.HomeActivity;
import com.ticketpro.parking.dar.model.DarDisinfectingElements;
import com.ticketpro.parking.dar.model.DarInsertMieageRequest;
import com.ticketpro.parking.dar.model.DarVdrElements;
import com.ticketpro.parking.dar.model.Mileage;
import com.ticketpro.parking.dar.model.params.ParamMileage;
import com.ticketpro.parking.dar.model.request.MileageJson_rpc;
import com.ticketpro.util.Preference;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DarVehicleListAndTask extends BaseActivityImpl implements Validator.ValidationListener {

    private static final int REQUEST_DISINFECTING = 1;
    private static final int REQUEST_VDR = 2;
    private TextView mVehicle;
    private CheckBox mDisinfectingChk;
    private CheckBox mVDRChk;
    private ListView mLvDisinfecting;
    private ListView mLvVdr;
    private Button mBtnVdr;
    private Button mBtnDisinfecting;
    private Button ValidaeBtn;
    private Button BtnNextInvisible;
    private Button next;
    private Button extra_btn;
    @NotEmpty
    private EditText mMileage;
    private DisinfectingViewAdapter disinfectingViewAdapter;
    private VDRViewAdapter vdrViewAdapter;
    private ArrayList<DarDisinfectingElements> itemList;
    private ArrayList<DarVdrElements> vdrItemList;
    private String vh, vhId;
    private StringBuilder vdr = new StringBuilder();
    private StringBuilder dis = new StringBuilder();
    private Validator validator;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Preference preference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dar_vehicle_list);
        setLogger(DarVehicleListAndTask.class.getName());
        TPApp.setItemList(null);
        TPApp.setItemVdr(null);
        Intent ii = getIntent();
        vh = ii.getStringExtra("VEHICLE");
        vhId = ii.getStringExtra("VEHICLEID");
        validator = new Validator(this);
        validator.setValidationListener(this);
        extra_btn=findViewById(R.id.btn_ext);
        Button b = findViewById(R.id.violation_back_btn);
        mMileage = findViewById(R.id.mileage);
        mMileage.requestFocus();
        mDisinfectingChk = findViewById(R.id.vc_checkboxDisinfecting);
        mVDRChk = findViewById(R.id.vc_checkboxVdr);
        mBtnDisinfecting = findViewById(R.id.btn_disinfecting);
        mBtnVdr = findViewById(R.id.btn_vdr);
        mVehicle = findViewById(R.id.txtVehicle);
        mVehicle.setText("VEHICLE# " + vh);
        mLvDisinfecting = findViewById(R.id.lvDisinfecting);
        mLvVdr = findViewById(R.id.lvVdr);
        ValidaeBtn = findViewById(R.id.validate_btn);
        BtnNextInvisible = findViewById(R.id.btnNext_invisible);
        preference = Preference.getInstance(DarVehicleListAndTask.this);
        preference.removeObject("AssignId");
        preference.putString("AssSubId", "0");
        preference.putString("validateMileage","N");
        preference.putString("validateDisinfact","N");
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        b.setOnClickListener(v -> {
            TPApp.setItemVdr(null);
            TPApp.setItemList(null);
            finish();
        });


        next = findViewById(R.id.btnNext);
        next.setVisibility(View.GONE);
        BtnNextInvisible.setVisibility(View.VISIBLE);


        next.setOnClickListener(v -> {
            //(TPApp.getItemList() != null && TPApp.getItemList().size() > 0) &&
            if ( (TPApp.getItemVdr() != null && TPApp.getItemVdr().size() > 0)) {
                validator.validate();
            } else {
                Toast.makeText(getInstance(), "Select VDR ", Toast.LENGTH_SHORT).show();
            }


        });
        extra_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(DarVehicleListAndTask.this,ScheduleShift.class);
                startActivity(i);
            }
        });

        ValidaeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if (!mMileage.getText().toString().equals("") && !mMileage.getText().toString().equals("0")  && mMileage.getText().toString().charAt(0)!=0)
                    InsertMileage();
                else*/
                if (mMileage.getText().toString()== null || mMileage.getText().toString().isEmpty())
                {
                    mMileage.setError("Please enter starting mileage");
                }
                else {
                    if (mMileage.getText().toString().charAt(0) == '0')
                        mMileage.setError("Please enter valid starting mileage");
                    else if (mMileage.getText().toString().equals("0"))
                        mMileage.setError("Please enter valid starting mileage");
                    else if (!mMileage.getText().toString().equals("") && !mMileage.getText().toString().equals("0")) {
                        try {
                            InsertMileage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else
                        mMileage.setError("Please enter starting mileage");
                }


            }
        });


        mDisinfectingChk.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Intent i = new Intent();
                i.setClass(DarVehicleListAndTask.this, DisinfectingListView.class);
                startActivityForResult(i, REQUEST_DISINFECTING);
            } else {
                TPApp.setItemList(null);
                mLvDisinfecting.setVisibility(View.GONE);
            }
        });


        mVDRChk.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Intent i = new Intent();
                i.setClass(DarVehicleListAndTask.this, VDRListView.class);
                startActivityForResult(i, REQUEST_VDR);
            } else {
                TPApp.setItemVdr(null);
                mLvVdr.setVisibility(View.GONE);
            }
        });


        mBtnDisinfecting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TPApp.setItemList(null);
                Intent i = new Intent();
                i.setClass(DarVehicleListAndTask.this, DisinfectingListView.class);
                startActivityForResult(i, REQUEST_DISINFECTING);
            }
        });


        mBtnVdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TPApp.setItemVdr(null);
                Intent i = new Intent();
                i.setClass(DarVehicleListAndTask.this, VDRListView.class);
                startActivityForResult(i, REQUEST_VDR);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_DISINFECTING && resultCode == RESULT_OK) {
                if (TPApp.getItemList() != null) {
                    itemList = TPApp.getItemList();
                    if (itemList.size() > 0) {
                        mBtnDisinfecting.setText("  DISINFECTING" + "(" + itemList.size() + ")");
                        for (int i = 0; i < itemList.size(); i++) {
                            dis.append(itemList.get(i).getId()).append(",");
                        }
                        if (dis.length() >= 1) {
                            dis.deleteCharAt(dis.length() - 1);
                        }
                    } else {
                        mBtnDisinfecting.setText("  DISINFECTING");
                    }
                } else {
                    mDisinfectingChk.setChecked(false);
                    mBtnDisinfecting.setText("  DISINFECTING");
                }
            }
            if (requestCode == REQUEST_DISINFECTING || requestCode == REQUEST_VDR && resultCode == RESULT_OK) {
                if (TPApp.getItemVdr() != null) {
                    vdrItemList = TPApp.getItemVdr();
                    if (vdrItemList.size() > 0) {
                        mBtnVdr.setText("  VDR" + "(" + vdrItemList.size() + ")");

                        for (int i = 0; i < vdrItemList.size(); i++) {
                            vdr.append(vdrItemList.get(i).getId()).append(",");
                        }
                        if (vdr.length() >= 1) {
                            vdr.deleteCharAt(vdr.length() - 1);
                        }
                    } else {
                        mBtnVdr.setText("  VDR");
                    }
                } else {
                    mVDRChk.setChecked(false);
                    mBtnVdr.setText("  VDR");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        TPApp.setVdr(vdr.toString());
        TPApp.setDisinfecting(dis.toString());
        TPApp.setMileage(mMileage.getText().toString());
        TPApp.setVehicleType(vh);
        TPApp.setVehicleid(vhId);
        Intent i = new Intent();
        //i.setClass(DarVehicleListAndTask.this, DarAssignment.class);
        i.setClass(DarVehicleListAndTask.this, ScheduleShift.class);
        startActivity(i);
        finish();
        ///  InsertMileage();

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

    public class DisinfectingViewAdapter extends ArrayAdapter<DarDisinfectingElements> {

        Context context;

        public DisinfectingViewAdapter(@NonNull Context context, ArrayList<DarDisinfectingElements> arrayList) {
            super(context, 0, arrayList);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View currentItemView = convertView;

            if (currentItemView == null) {
                currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_dar_vehicle_row, parent, false);
            }
            DarDisinfectingElements currentNumberPosition = getItem(position);
            //ImageView numbersImage = currentItemView.findViewById(R.id.imageView);
            assert currentNumberPosition != null;
            //numbersImage.setImageResource(currentNumberPosition.getNumbersImageId());
            TextView textView1 = currentItemView.findViewById(R.id.veh_itemName);
            RelativeLayout layout = currentItemView.findViewById(R.id.main_write_ticket_option);
            textView1.setText(currentNumberPosition.getName());

            return currentItemView;
        }

    }


    public class VDRViewAdapter extends ArrayAdapter<DarVdrElements> {
        Context context;

        public VDRViewAdapter(@NonNull Context context, ArrayList<DarVdrElements> arrayList) {
            super(context, 0, arrayList);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View currentItemView = convertView;

            if (currentItemView == null) {
                currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_dar_vehicle_row, parent, false);
            }

            DarVdrElements currentNumberPosition = getItem(position);

            //ImageView numbersImage = currentItemView.findViewById(R.id.imageView);
            assert currentNumberPosition != null;
            //numbersImage.setImageResource(currentNumberPosition.getNumbersImageId());

            TextView textView1 = currentItemView.findViewById(R.id.veh_itemName);
            RelativeLayout layout = currentItemView.findViewById(R.id.main_write_ticket_option);
            textView1.setText(currentNumberPosition.getName());

            return currentItemView;
        }

    }

    public void InsertMileage() throws IOException {
        progressDialog = ProgressDialog.show(DarVehicleListAndTask.this, "", "Validating...");
        MileageJson_rpc jsonRpc = new MileageJson_rpc();
        List<Mileage> aList = new ArrayList<>();
        ParamMileage param = new ParamMileage();

        Mileage details = new Mileage();
        details.setUserId(TPApp.userId);
        details.setStartMileage(mMileage.getText().toString());
        details.setDeviceid(TPApp.custId);
        details.setVehicleNumber(vh);
        details.setVehicleId(vhId);
        //details.setActionId("1");
        aList.add(details);
        param.setCustId(TPApp.custId);
        param.setDetails(aList);
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
                                mMileage.setEnabled(false);
                                preference.putString("validateMileage","Y");
                                log.info("Response" + response.body().getResult().toString() );
                               // editor.putString("coulmunId", response.body().getResult().getAppId().get(0).toString());
                                preference.putString("coulmunId", response.body().getResult().getAppId().get(0).toString());
                                if (!response.body().getResult().getEndMileage().get(0).isEmpty()) {
                                    TPApp.setMileage(mMileage.getText().toString());
                                    preference.putString("EndMileage", response.body().getResult().getEndMileage().get(0));
                                } else {
                                    preference.putString("EndMileage", "");
                                    TPApp.setMileage("0000");
                                }

                                editor.apply();
                                next.setVisibility(View.VISIBLE);
                                BtnNextInvisible.setVisibility(View.GONE);
                                ValidaeBtn.setVisibility(View.GONE);


                            } else if (response.isSuccessful() && response.code() == 200 && response.body().getResult().getResult() == false && response.body().getResult().getEndMileage()==null) {
                                // Dialog(response.body().getResult().getEndMileage().get(0));
                                NoPriorMileageDialog();
                                log.info("Response" + response.body().getResult().toString());

                            } else if (response.isSuccessful() && response.code() == 200 && response.body().getResult().getResult() == false) {
                                Dialog(response.body().getResult().getEndMileage().get(0));
                                log.info("Response" + response.body().getResult().toString() );
                            } else {
                                Dialog(preference.getString("EndMileage"));
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


    public void OverRideInsertMileage() throws IOException {
        progressDialog = ProgressDialog.show(DarVehicleListAndTask.this, "", "Validating...");
        MileageJson_rpc jsonRpc = new MileageJson_rpc();
        List<Mileage> aList = new ArrayList<>();
        ParamMileage param = new ParamMileage();

        Mileage details = new Mileage();
        details.setUserId(TPApp.userId);
        details.setStartMileage(mMileage.getText().toString());
        details.setDeviceid(TPApp.custId);
        details.setVehicleNumber(vh);
        details.setVehicleId(vhId);
        //details.setActionId("1");
        aList.add(details);
        param.setCustId(TPApp.custId);
        param.setIsOverride("Y");
        param.setDetails(aList);
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
                                 mMileage.setEnabled(false);
                                 preference.putString("validateMileage","Y");
                                 log.info("Response" + response.body().getResult().toString());
                                // editor.putString("coulmunId", response.body().getResult().getAppId().get(0).toString());
                                 preference.putString("coulmunId", response.body().getResult().getAppId().get(0).toString());
                                 if (!response.body().getResult().getEndMileage().get(0).isEmpty()) {
                                     TPApp.setMileage(mMileage.getText().toString());
                                     preference.putString("EndMileage", response.body().getResult().getEndMileage().get(0));
                                 } else {
                                     preference.putString("EndMileage", "");
                                     TPApp.setMileage("0000");
                                 }

                                 editor.apply();
                                 next.setVisibility(View.VISIBLE);
                                 BtnNextInvisible.setVisibility(View.GONE);
                                 ValidaeBtn.setVisibility(View.GONE);


                             } else {
                                 FailedDialog();
                                 log.info("Response" + response.body().getResult().toString());
                                 // Dialog(preference.getString("EndMileage"));
                                 //Toast.makeText(DarVehicleListAndTask.this, "Please Insert Right Mileage", Toast.LENGTH_SHORT).show();
                             }
                         }
                         catch (Exception ex)
                         {
                             ex.printStackTrace();
                             log.error(ex.getMessage());
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

        } /*else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            //   Mileage.insertMileage(details);
            Intent intent = new Intent();
            intent.setClass(this, HomeActivity.class);
            startActivity(intent);
            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();

        }*/

        else{
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
             CheckConnectivityDialog();
        }
    }


    public void Dialog(String EndMileage) {

        new iOSDialogBuilder(DarVehicleListAndTask.this)
                .setSubtitle("Invalid or incorrect mileage \n Ending mileage:" + " " + EndMileage
               +""+ "\n"+"\n  Override ?")
                .setPositiveListener("Yes", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                               /* Intent intent = new Intent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                                intent.setClass(SignCheck.this, DarEquipmentNew.class);
                                startActivity(intent);*/
                        dialog.dismiss();
                        try {
                            OverRideInsertMileage();
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
    }

    public void FailedDialog() {

        new iOSDialogBuilder(DarVehicleListAndTask.this)
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

    public void NoPriorMileageDialog() {

        new iOSDialogBuilder(DarVehicleListAndTask.this)
                .setSubtitle("No mileage record for this Vehicle"+"\n"+"Override ?")
                .setPositiveListener("Yes", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                               /* Intent intent = new Intent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                //intent.putExtra(TPConstant.EXTRA_END_SHIFT, true);
                                intent.setClass(SignCheck.this, DarEquipmentNew.class);
                                startActivity(intent);*/
                        dialog.dismiss();
                        try {
                            OverRideInsertMileage();
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
    }

    public void CheckConnectivityDialog() {

        new iOSDialogBuilder(DarVehicleListAndTask.this)
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

/* Intent i = new Intent();
            //i.setClass(DarVehicleListAndTask.this, DarAssignment.class);
            i.setClass(DarVehicleListAndTask.this, DarAssignmentActivity.class);
            startActivity(i);
            finish();*/
            /*TPApp.setVdr(vdr.toString());
            TPApp.setDisinfecting(dis.toString());
            TPApp.setMillage(mMileage.getText().toString());
            TPApp.setVehicleType(vh);

            Intent i = new Intent();
            i.setClass(DarVehicleListAndTask.this, DarAssignment.class);
            startActivity(i);*/

      /*  disinfectingViewAdapter = new DisinfectingViewAdapter(this, itemList);
                        mLvDisinfecting.setAdapter(disinfectingViewAdapter);
                        mLvDisinfecting.setVisibility(View.VISIBLE);*/


                       /* vdrViewAdapter = new VDRViewAdapter(this, vdrItemList);
                        mLvVdr.setAdapter(vdrViewAdapter);
                        mLvVdr.setVisibility(View.VISIBLE);
*/

           /*   final DarTicketResult result = response.body().getResult();
                            final List<Integer> appId = result.getAppId();
                            if (appId != null && appId.size() > 0) {
                                for (int i = 0; i < appId.size(); i++) {
                                    try {
                                        FieldContactDetails.removeById(appId.get(i).intValue());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            Toast.makeText(getInstance(), "Record save successfully", Toast.LENGTH_LONG).show();*/
                           /* Intent i = new Intent();
                            //i.setClass(DarVehicleListAndTask.this, DarAssignment.class);
                            i.setClass(DarVehicleListAndTask.this, DarAssignmentActivity.class);
                            startActivity(i);*/
}
