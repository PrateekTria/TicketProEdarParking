package com.ticketpro.vendors;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ticketpro.api.ApiRequest;
import com.ticketpro.api.ServiceGeneratorSpPlus;
import com.ticketpro.model.Feature;
import com.ticketpro.model.UserSetting;
import com.ticketpro.model.VendorService;
import com.ticketpro.model.VendorServiceConfig;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.bl.CommonBLProcessor;
import com.ticketpro.util.StringUtil;
import com.ticketpro.util.TPUtility;
import com.ticketpro.vendors.sp_plus.SpPlusModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class SpPlusZoneInfoActivity extends BaseActivityImpl {

    private ProgressDialog progressDialog;
    private TableLayout tableLayout;
    private ArrayList<SpPlusModel> parkings;
    private ArrayList<SpPlusModel> filteredItems = new ArrayList<>();
    private final int ASC_ORDER = 1;
    private final int DESC_ORDER = 2;
    private int sortBy = 3;
    private int sortOrder = 1;

    private Handler lookupHandler;
    private String zoneName;
    private String zoneCode;
    private EditText searchEditText;

    private CheckBox expiredCheckbox;
    private Button pageSizeButton;
    private int pageSize = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sp_plus_zone_info);
            setActiveScreen(this);


            setLogger(SpPlusZoneInfoActivity.class.getName());
            setBLProcessor(new CommonBLProcessor((TPApplication) getApplicationContext()));
            tableLayout = (TableLayout) findViewById(R.id.logs_4_table_view);

            UserSetting userSettings = TPApp.getUserSettings();
            if (userSettings != null) {
                CachedMap.cachedDuration = userSettings.getCacheExpiry();
            }

            pageSizeButton = (Button) findViewById(R.id.pageSizeBtn);
            pageSizeButton.setVisibility(View.VISIBLE);

            expiredCheckbox = (CheckBox) findViewById(R.id.expiredChk);
            expiredCheckbox.setVisibility(View.VISIBLE);

            expiredCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // loadData(true);
                }
            });

            searchEditText = (EditText) findViewById(R.id.searchText);
            searchEditText.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String searchText = searchEditText.getText().toString();

                    filteredItems.clear();

                    if (searchText.length() == 0) {
                        filteredItems.addAll(parkings);
                    } else {
                        searchText = searchText.toLowerCase();
                        for (SpPlusModel parking : parkings) {
                            String plateNumber = parking.getLicensePlate();

                            if (!StringUtil.isEmpty(plateNumber)) {
                                if (plateNumber.toLowerCase().startsWith(searchText)) {
                                    filteredItems.add(parking);
                                }
                            }
                        }
                    }

                    initDatagrid(filteredItems);
                }
            });


            zoneName = getIntent().getStringExtra("ZoneName");
            zoneCode = getIntent().getStringExtra("ZoneCode");

            if (zoneName != null) {
                ((TextView) findViewById(R.id.zone_info_text)).setText(zoneName + " (" + zoneCode + ")");
            }

            // bindDataAtLoadingTime();

            _initState(zoneCode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void _initState(String zoneCode) throws IOException {
        if (isNetworkConnected()) {
            progressDialog = new ProgressDialog(SpPlusZoneInfoActivity.this);
            progressDialog.setMessage("Loading SP Plus...");
            progressDialog.setCancelable(false);
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            progressDialog.show();
            if (!(TPApp.enableSpPlus && Feature.isFeatureAllowed(Feature.PARK_SP_PLUS))) {
                return;
            }
            final VendorServiceConfig config = VendorService.getServiceConfigSpPlus(VendorService.SP_PLUS, TPApp.deviceId, "/");
            Map<String, String> paramsMap = config.getParamsMap();
            String uName = paramsMap.get("vendor-name");
            String api_key = paramsMap.get("x-api-key");
            String URL = config.getServiceURL();

            String finalUrl = URL + "/" + zoneCode;


            ApiRequest apiRequest = ServiceGeneratorSpPlus.createService(ApiRequest.class);
            apiRequest.spPlusPlate(finalUrl, api_key, uName).enqueue(new retrofit2.Callback<List<SpPlusModel>>() {
                @Override
                public void onResponse(Call<List<SpPlusModel>> call, Response<List<SpPlusModel>> response) {

                    if (response.isSuccessful() && response.code() == 200 && response.body().size() > 0) {
                        initDatagrid((ArrayList<SpPlusModel>) response.body());
                        progressDialog.dismiss();
                    }else {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<List<SpPlusModel>> call, Throwable t) {
                    progressDialog.dismiss();
                    System.out.println(t.getMessage());
                }
            });
        }


    }

    private void initDatagrid(final ArrayList<SpPlusModel> parkings) {
        try {
            if (parkings == null) {
                return;
            }
            try {
                tableLayout.removeAllViews();
            } catch (Exception e) {
                e.printStackTrace();
            }
            LayoutInflater inflater = LayoutInflater.from(this);

            if (pageSize > 0) {
                pageSizeButton.setText("Show " + pageSize);
            } else {
                pageSizeButton.setText("Show All");
            }

            //adding Header
            View headerRow = inflater.inflate(R.layout.table_row_parking, null);

            TextView plateColumn = ((TextView) headerRow.findViewById(R.id.tr_header1));
            plateColumn.setText("LPN");
            plateColumn.setClickable(true);
            plateColumn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    // Collections.sort(parkings,new ParkMobileParkingRight.PlateComparator());

                    //Update Sorting Order
                    if (sortBy != 1) {
                        sortOrder = ASC_ORDER;
                    } else if (sortOrder == ASC_ORDER) {
                        sortOrder = DESC_ORDER;
                        Collections.reverse(parkings);
                    } else {
                        sortOrder = ASC_ORDER;
                    }

                    sortBy = 1;
                    initDatagrid(parkings);
                }
            });

            TextView infoColumn = ((TextView) headerRow.findViewById(R.id.tr_header2));
            infoColumn.setText("Space");
           /* infoColumn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Collections.sort(parkings,new ParkMobileParkingRight.SpaceComparator());

                    //Update Sorting Order
                    if(sortBy!=2){
                        sortOrder=ASC_ORDER;
                    }
                    else if(sortOrder==ASC_ORDER){
                        sortOrder=DESC_ORDER;
                        Collections.reverse(parkings);
                    }
                    else{
                        sortOrder=ASC_ORDER;
                    }

                    sortBy=2;
                    initDatagrid(parkings);
                }
            });
*/
            TextView statusColumn = ((TextView) headerRow.findViewById(R.id.tr_header3));
            statusColumn.setText("Expire");
            statusColumn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //Collections.sort(parkings,new ParkMobileParkingRight.ExpireComparator());

                    //Update Sorting Order
                    if (sortBy != 3) {
                        sortOrder = ASC_ORDER;
                    } else if (sortOrder == ASC_ORDER) {
                        sortOrder = DESC_ORDER;
                        Collections.reverse(parkings);
                    } else {
                        sortOrder = ASC_ORDER;
                    }

                    sortBy = 3;
                    initDatagrid(parkings);
                }
            });

            switch (sortBy) {
                case 1:
                    if (sortOrder == ASC_ORDER)
                        plateColumn.setText("LPN \u25BC");
                    else
                        plateColumn.setText("LPN \u25B2");
                    break;

                case 2:
                    if (sortOrder == ASC_ORDER)
                        infoColumn.setText("Space \u25BC");
                    else
                        infoColumn.setText("Space \u25B2");
                    break;

                case 3:
                    if (sortOrder == ASC_ORDER)
                        statusColumn.setText("Expire \u25BC");
                    else
                        statusColumn.setText("Expire \u25B2");
                    break;
            }

            tableLayout.addView(headerRow);

            int index = 0;
            for (SpPlusModel item : parkings) {
                View tableRow = inflater.inflate(R.layout.table_row_parking, null);

                ImageView statusIcon = ((ImageView) tableRow.findViewById(R.id.tr_status_img));
                statusIcon.setBackgroundResource(R.drawable.color_green);

                ImageView actionIcon = ((ImageView) tableRow.findViewById(R.id.tr_action));
                actionIcon.setBackgroundResource(R.drawable.info);
                actionIcon.setVisibility(View.GONE);
                actionIcon.setTag(R.id.ListIndex, index);
                actionIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = ((Integer) v.getTag(R.id.ListIndex)).intValue();
                        SpPlusModel parking = parkings.get(index);
                        displayParkingInfoMsg(parking,index);
                    }
                });

                tableRow.setTag(R.id.ListIndex, index);
                tableRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = ((Integer) v.getTag(R.id.ListIndex)).intValue();
                        SpPlusModel parking = parkings.get(index);
                        displayParkingInfoMsg(parking, index);
                    }
                });

                ParkingExpireInfo expireInfo = item.getExpireInfo();
                if (item.getStopAt() != null && !item.getStopAt().isEmpty()) {
                    if (expireInfo.isExpired()) {
                        statusIcon.setBackgroundResource(R.drawable.color_red);
                        //((TextView)tableRow.findViewById(R.id.tr_header1)).setTextColor(android.graphics.Color.RED);
                        //((TextView)tableRow.findViewById(R.id.tr_header2)).setTextColor(android.graphics.Color.RED);
                        //((TextView)tableRow.findViewById(R.id.tr_header3)).setTextColor(android.graphics.Color.RED);
                    } else if (expireInfo.getDiffMinutes() <= 3 && expireInfo.getDiffHrs() == 0 && expireInfo.getDiffDays() == 0) {
                        statusIcon.setBackgroundResource(R.drawable.color_yellow);
                    }
                }
                ((TextView) tableRow.findViewById(R.id.tr_header1)).setText(item.getLicensePlate());
                ((TextView) tableRow.findViewById(R.id.tr_header2)).setText(item.getParkerType());
                if (expireInfo==null){
                    ((TextView) tableRow.findViewById(R.id.tr_header3)).setText("");

                }else {

                    ((TextView) tableRow.findViewById(R.id.tr_header3)).setText(expireInfo.getExpireMsg());
                }

                if ((index % 2) == 0) {
                    tableRow.setBackgroundResource(R.drawable.tablerow_even);
                } else {
                    tableRow.setBackgroundResource(R.drawable.tablerow_odd);
                }

                if (!expiredCheckbox.isChecked()) {
                    tableLayout.addView(tableRow);
                    index++;
                }

                if (pageSize > 0 && index >= pageSize) {
                    break;
                }
            }

        } catch (Exception e) {
            log.error(TPUtility.getPrintStackTrace(e));
        }
    }

    private void displayParkingInfoMsg(SpPlusModel spPlusModel, int index) {
        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_view, null);
        TextView headerTV = view.findViewById(R.id.headerTV);
        TextView valueTV = view.findViewById(R.id.valueTV);

        StringBuilder keys = new StringBuilder();
        StringBuilder values = new StringBuilder();

        keys.append("Plate" + "\n");
        keys.append("Status" + "\n");
        keys.append("Start time" + "\n");
        keys.append("Stop time" + "\n");
        keys.append("Expire" + "\n");
        keys.append("ParkerType" + "\n");

        headerTV.setText(keys.toString());


        values.append(" : ").append(StringUtil.getDisplayString(spPlusModel.getLicensePlate())).append("\n");
        String accountStatus = spPlusModel.getAccountStatus();
        if (accountStatus != null) {

            values.append(" : ").append(StringUtil.getDisplayString(accountStatus)).append("\n");
        } else {

            values.append(" : ").append(StringUtil.getDisplayString("")).append("\n");
        }
        values.append(" : ").append(StringUtil.getDisplayString(spPlusModel.getStartAt())).append("\n");
        values.append(" : ").append(StringUtil.getDisplayString(spPlusModel.getStopAt())).append("\n");

        ParkingExpireInfo expireInfo = spPlusModel.getExpireInfo();

        if (spPlusModel.getStopAt() != null && !spPlusModel.getStopAt().isEmpty()) {
            values.append(" : ").append(StringUtil.getDisplayString(expireInfo.getExpireMsg())).append("\n");
        } else {
            values.append(" : ").append("").append("\n");
        }
        values.append(" : ").append(StringUtil.getDisplayString(spPlusModel.getParkerType()));

        valueTV.setText(values.toString());

        AlertDialog.Builder dialog = new AlertDialog.Builder(SpPlusZoneInfoActivity.this);
        dialog.setCancelable(false);

        dialog.setView(view);
        dialog.setTitle("SpPlus Parking Info");
        dialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

       /* dialog.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/

        TPUtility.applyButtonStyles(dialog.show());
    }

    public void searchAction(View view) {
        if (searchEditText.getVisibility() == View.GONE) {
            searchEditText.setVisibility(View.VISIBLE);
            TPUtility.showSoftKeyboard(this, searchEditText);
        } else {
            searchEditText.setVisibility(View.GONE);
        }
    }

    public void pageSizeAction(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Results Limit");

        final CharSequence[] choiceList = {"Show 20 Results",
                "Show 50 Results",
                "Show 100 Results",
                "Show All"};

        builder.setItems(choiceList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                pageSize = 20;
                                break;

                            case 1:
                                pageSize = 50;
                                break;

                            case 2:
                                pageSize = 100;
                                break;

                            case 3:
                                pageSize = -1;
                                break;
                        }

                        initDatagrid(parkings);
                    }
                })
                .setCancelable(true)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void refreshAction(View view) throws IOException {
        if (zoneName!=null) {
            _initState(zoneCode);
        }
    }

    @Override
    public void onClick(View v) {

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