package com.ticketpro.parking.dar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.Equipments;

import java.util.ArrayList;
import java.util.List;

public class DarEquipment extends BaseActivityImpl {
    private ListView mEquipmentList;
    private ArrayList<Equipments> equipmentsArrayList;
    private Button back,next,vehicle;
   // private EquipmentViewAdapter equipmentArrayAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Equipments> mModelList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dar_equipment);
        __intiView();
        _loadInitialData();
    }

    private void __intiView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.euip_list);
        LinearLayoutManager manager = new LinearLayoutManager(DarEquipment.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        back = findViewById(R.id.violation_back_btn);
        next = findViewById(R.id.eq_btnNext);
        vehicle = findViewById(R.id.eq_btnVehicle);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        next.setOnClickListener(v -> {
           if (mModelList!=null && mModelList.size()==0){
               return;
           }else {
               StringBuilder sb = new StringBuilder();
               if (mModelList != null && mModelList.size() > 0) {
                   for (Equipments eq : mModelList) {
                       if (eq.isSelected()) {
                           sb.append(eq.getId()).append(",");
                       }
                   }
                   TPApplication.getInstance().setEquipment(sb.deleteCharAt(sb.length() - 1).toString());
                   Intent i = new Intent();
                   i.setClass(DarEquipment.this, DarAssignmentActivity.class);
                   startActivity(i);
                   //finish();
               }

           }
        });
        vehicle.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setClass(DarEquipment.this, DarVehicleListActivity.class);
            startActivity(i);
        });
    }

    private void _loadInitialData() {
        equipmentsArrayList = Equipments._getEquipmentList(TPApp.getCustId());
        mAdapter = new RecyclerViewAdapter(equipmentsArrayList);
        mRecyclerView.setAdapter(mAdapter);


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

    /*public class EquipmentViewAdapter extends ArrayAdapter<Equipments> {
        private SparseBooleanArray mSelectedItemsIds;

        public EquipmentViewAdapter(@NonNull Context context, ArrayList<Equipments> arrayList) {
            super(context, 0, arrayList);
            mSelectedItemsIds = new  SparseBooleanArray();

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View currentItemView = convertView;

            if (currentItemView == null) {
                currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.dar_equipment_row, parent, false);
            }

            Equipments currentNumberPosition = getItem(position);

            //ImageView numbersImage = currentItemView.findViewById(R.id.imageView);
            assert currentNumberPosition != null;
            //numbersImage.setImageResource(currentNumberPosition.getNumbersImageId());

            TextView textView1 = currentItemView.findViewById(R.id.txt_equipment_name);
            RelativeLayout layout = currentItemView.findViewById(R.id.main_write_ticket_option);
            textView1.setText(currentNumberPosition.getItems());
            *//*layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*//*
            return currentItemView;
        }
        public void  toggleSelection(int position) {

            selectView(position, !mSelectedItemsIds.get(position));

        }
        public void selectView(int position, boolean value) {

            if (value)

                mSelectedItemsIds.put(position,  value);

            else

                mSelectedItemsIds.delete(position);

            notifyDataSetChanged();

        }
        public int  getSelectedCount() {

            return mSelectedItemsIds.size();

        }

        public  SparseBooleanArray getSelectedIds() {

            return mSelectedItemsIds;

        }
    }
*/
    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {



        public RecyclerViewAdapter(List<Equipments> modelList) {
            mModelList = modelList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dar_equipment_row, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Equipments model = mModelList.get(position);
            holder.textView.setText(model.getItems());
            holder.mLayout.setBackground(model.isSelected() ? getDrawable(R.drawable.dar_plain) : getDrawable(R.drawable.black_border));
            holder.mLayout.setOnClickListener(view -> {
                model.setSelected(!model.isSelected());
                holder.mLayout.setBackground(model.isSelected() ? getDrawable(R.drawable.dar_plain) : getDrawable(R.drawable.black_border));
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
}