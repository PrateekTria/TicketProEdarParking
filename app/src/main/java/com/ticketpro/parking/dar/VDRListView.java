package com.ticketpro.parking.dar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.dar.model.DarDisinfectingElements;
import com.ticketpro.parking.dar.model.DarVdrElements;
import com.ticketpro.util.Preference;

import java.util.ArrayList;
import java.util.List;

public class VDRListView extends BaseActivityImpl {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<DarVdrElements> mModelList;
    private Button back;
    private Button next;
    Preference preference;
    private StringBuilder vdr = new StringBuilder();
    private StringBuilder dis = new StringBuilder();
    private ArrayList<DarDisinfectingElements> itemList1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vdrlist_view);
        setLogger(VDRListView.class.getName());
        mRecyclerView = (RecyclerView) findViewById(R.id.euip_list);
        preference=Preference.getInstance(VDRListView.this);
        LinearLayoutManager manager = new LinearLayoutManager(VDRListView.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        back = findViewById(R.id.violation_back_btn);
        next=findViewById(R.id.vdr_next_btn);

        mModelList = DarVdrElements._getDarVdrList();
        final ArrayList<DarVdrElements> itemList = TPApp.getItemVdr();
        if ((mModelList.size()>0 )&& (itemList!=null &&itemList.size()>0)){
            for (int i = 0; i < mModelList.size(); i++) {
                final DarVdrElements darDisinfectingElements = mModelList.get(i);
                for (int j = 0; j < itemList.size(); j++) {
                    final DarVdrElements darDisinfectingElements1 = itemList.get(j);
                    if (darDisinfectingElements.getName().equals(darDisinfectingElements1.getName())){
                        mModelList.set(i,darDisinfectingElements1);
                    }
                }
            }
        }



        if (mModelList.size()>0){
            mAdapter = new RecyclerViewAdapter(mModelList);
            mRecyclerView.setAdapter(mAdapter);
        }

        back.setOnClickListener(v -> {
            Intent data = new Intent();
            ArrayList<DarVdrElements> mList = new ArrayList<>();

                if (mModelList.size() > 0) {
                    for (int i = 0; i < mModelList.size(); i++) {
                        final DarVdrElements darDisinfectingElements = mModelList.get(i);
                        if (darDisinfectingElements.isSelected()) {
                            mList.add(darDisinfectingElements);
                        }
                    }
                    if (mModelList.size()>0)
                    {
                        TPApp.setItemVdr(null);
                        TPApp.setItemVdr(mList);
                        setResult(RESULT_OK);
                        finish();

                    }
                    else {
                        NoSelection();
                    }

                }


        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent data = new Intent();
                ArrayList<DarVdrElements> mList = new ArrayList<>();

                if (mModelList.size()>0){
                    for (int i = 0; i < mModelList.size(); i++) {
                        final DarVdrElements darDisinfectingElements = mModelList.get(i);
                        if (darDisinfectingElements.isSelected()){
                            mList.add(darDisinfectingElements);
                        }
                    }
                    if (mList.size()>0)
                    {
                        if (preference.getString("validateMileage").equals("N"))
                        {
                           NoSelectionMileage();
                        }
                        /*else if(preference.getString("validateDisinfact").equals("N")){
                           NoSelectionDisinfecting();
                        }*/
                        else {
                            TPApp.setItemVdr(null);
                            TPApp.setItemVdr(mList);
                            if (TPApp.getItemList() != null) {
                            itemList1 = TPApp.getItemList();

                                for (int i = 0; i < itemList1.size(); i++) {
                                    dis.append(itemList1.get(i).getId()).append(",");
                                }
                            }
                            for (int i = 0; i < mList.size(); i++) {
                                vdr.append(mList.get(i).getId()).append(",");
                            }
                            TPApp.setVdr(vdr.toString());
                            TPApp.setDisinfecting(dis.toString());
                            Intent i = new Intent();
                            //i.setClass(DarVehicleListAndTask.this, DarAssignment.class);
                            i.setClass(VDRListView.this, ScheduleShift.class);
                            startActivity(i);
                            finish();
                        }

                    }
                    else {
                        NoSelection();
                    }

                }

            }

        });

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

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

        public RecyclerViewAdapter(List<DarVdrElements> modelList) {
            mModelList = modelList;
        }

        @Override
        public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dar_equipment_row, parent, false);
            return new RecyclerViewAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerViewAdapter.MyViewHolder holder, int position) {
            final DarVdrElements model = mModelList.get(position);
            holder.textView.setText(model.getName());
            holder.mLayout.setBackground(model.isSelected() ? getDrawable(R.drawable.dar_plain) : getDrawable(R.drawable.black_border));
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    model.setSelected(!model.isSelected());
                    holder.mLayout.setBackground(model.isSelected() ? getDrawable(R.drawable.dar_plain) : getDrawable(R.drawable.black_border));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mModelList == null ? 0 : mModelList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private View view;
            private TextView textView;
            private LinearLayout mLayout;

            private MyViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                textView = (TextView) itemView.findViewById(R.id.txt_equipment_name);
                mLayout = itemView.findViewById(R.id.topLayout);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        ArrayList<DarVdrElements> mList = new ArrayList<>();
        if (mModelList.size()>0){
            for (int i = 0; i < mModelList.size(); i++) {
                final DarVdrElements darDisinfectingElements = mModelList.get(i);
                if (darDisinfectingElements.isSelected()){
                    mList.add(darDisinfectingElements);
                }
            }
            TPApp.setItemVdr(null);
            TPApp.setItemVdr(mList);
        }
        setResult(RESULT_OK);
        finish();
    }

    public void NoSelection() {

        new iOSDialogBuilder(VDRListView.this)
                .setSubtitle("Please select VDR list")
                .setPositiveListener("OK", new iOSDialogClickListener() {
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
                /*.setNegativeListener("No", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })*/
                .build().show();
        //new ProxyImpl().updateDarTable();
    }

    public void NoSelectionMileage() {

        new iOSDialogBuilder(VDRListView.this)
                .setSubtitle("Please validate start mileage")
                .setPositiveListener("OK", new iOSDialogClickListener() {
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
                /*.setNegativeListener("No", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })*/
                .build().show();
        //new ProxyImpl().updateDarTable();
    }
    public void NoSelectionDisinfecting() {

        new iOSDialogBuilder(VDRListView.this)
                .setSubtitle("Please select Disinfecting list")
                .setPositiveListener("OK", new iOSDialogClickListener() {
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
                /*.setNegativeListener("No", new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {
                        dialog.dismiss();
                    }
                })*/
                .build().show();
        //new ProxyImpl().updateDarTable();
    }
}