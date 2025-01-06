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
import com.ticketpro.util.Preference;

import java.util.ArrayList;
import java.util.List;

public class DisinfectingListView extends BaseActivityImpl {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<DarDisinfectingElements> mModelList;
    private Button back,next;
    Preference preference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disinfecting_list_view);
        setLogger(DisinfectingListView.class.getName());
        preference=Preference.getInstance(DisinfectingListView.this);
        mRecyclerView = (RecyclerView) findViewById(R.id.euip_list);
        LinearLayoutManager manager = new LinearLayoutManager(DisinfectingListView.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        back = findViewById(R.id.violation_back_btn);
        next=  findViewById(R.id.violation_next_btn);

        mModelList = DarDisinfectingElements._getDarDistList();
        final ArrayList<DarDisinfectingElements> itemList = TPApp.getItemList();
        if ((mModelList.size()>0 )&& (itemList!=null &&itemList.size()>0)){
            for (int i = 0; i < mModelList.size(); i++) {
                final DarDisinfectingElements darDisinfectingElements = mModelList.get(i);
                for (int j = 0; j < itemList.size(); j++) {
                    final DarDisinfectingElements darDisinfectingElements1 = itemList.get(j);
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
            ArrayList<DarDisinfectingElements> mList = new ArrayList<>();
            if (mModelList.size()>0){
                for (int i = 0; i < mModelList.size(); i++) {
                    final DarDisinfectingElements darDisinfectingElements = mModelList.get(i);
                    if (darDisinfectingElements.isSelected()){
                        mList.add(darDisinfectingElements);
                    }
                }
                if (mList.size()>0)
                    preference.putString("validateDisinfact","Y");
                else
                    preference.putString("validateDisinfact","N");


                TPApp.setItemList(null);
                TPApp.setItemList(mList);

            }
            setResult(RESULT_OK);
            finish();
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<DarDisinfectingElements> mList = new ArrayList<>();
                if (mModelList.size()>0){
                    for (int i = 0; i < mModelList.size(); i++) {
                        final DarDisinfectingElements darDisinfectingElements = mModelList.get(i);
                        if (darDisinfectingElements.isSelected()){
                            mList.add(darDisinfectingElements);
                        }
                    }
                    if(mList.size()>0)
                    {
                        TPApp.setItemList(null);
                        TPApp.setItemList(mList);
                        preference.putString("validateDisinfact","Y");
                        setResult(RESULT_OK);
                        Intent intent= new Intent();
                        intent.setClass(DisinfectingListView.this,VDRListView.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        NoSelectionDisinfecting();
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

        public RecyclerViewAdapter(List<DarDisinfectingElements> modelList) {
            mModelList = modelList;
        }

        @Override
        public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dar_equipment_row, parent, false);
            return new RecyclerViewAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerViewAdapter.MyViewHolder holder, int position) {
            final DarDisinfectingElements model = mModelList.get(position);
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
        ArrayList<DarDisinfectingElements> mList = new ArrayList<>();
        if (mModelList.size()>0){
            for (int i = 0; i < mModelList.size(); i++) {
                final DarDisinfectingElements darDisinfectingElements = mModelList.get(i);
                if (darDisinfectingElements.isSelected()){
                    mList.add(darDisinfectingElements);
                }
            }
            TPApp.setItemList(null);
            TPApp.setItemList(mList);
        }
        setResult(RESULT_OK);
        finish();
    }

    public void NoSelectionDisinfecting() {

        new iOSDialogBuilder(DisinfectingListView.this)
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