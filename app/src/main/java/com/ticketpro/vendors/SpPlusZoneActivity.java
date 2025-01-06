package com.ticketpro.vendors;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ticketpro.model.Vendor;
import com.ticketpro.model.VendorItem;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.ZoneListActivity;
import com.ticketpro.util.Preference;
import com.ticketpro.util.TPConstant;

import java.util.ArrayList;

public class SpPlusZoneActivity extends BaseActivityImpl {

    private Preference preference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_plus_zone);

        preference = Preference.getInstance(SpPlusZoneActivity.this);
        try {
            Vendor spPlus = Vendor.getVendorByName("SP_PLUS");
            ArrayList<VendorItem> vendorItems = VendorItem.getVendorSamtrans(spPlus.getVendorId());
            if (vendorItems.size()>0) {
                ArrayList<String> vendorItemArrayList = new ArrayList<>();
                for (VendorItem item : vendorItems) {
                    vendorItemArrayList.add(item.getItemName());
                }

                final String[] objects;
                objects = vendorItemArrayList.toArray(new String[vendorItemArrayList.size()]);
                final AlertDialog.Builder builder = new AlertDialog.Builder(SpPlusZoneActivity.this);
                builder.setTitle("Select Zone");
                builder.setItems(objects, (dialog, which) -> {
                    String object = objects[which];
                    preference.putString(TPConstant.CURRENT_SP_PLUS_ZONE, vendorItems.get(which).getItemCode());
                    String itemCode = vendorItems.get(which).getItemCode();
                    String itemCodeName = vendorItems.get(which).getItemName();
                    //__lookupSpPlusPlate(itemCode, plate);
                    final Intent intent = new Intent();
                    intent.setClass(this, SpPlusZoneInfoActivity.class);
                    intent.putExtra("ZoneCode",itemCode);
                    intent.putExtra("ZoneName",itemCodeName);
                    startActivity(intent);
                    dialog.dismiss();
                    this.finish();
                });

                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.show();
                builder.setCancelable(true);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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