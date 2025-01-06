package com.ticketpro.parking.dar;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.ticketpro.parking.R;
import com.ticketpro.parking.dar.model.DarSeniorDutiesElements;

import java.util.ArrayList;

public class SeniorDutyAdapter extends RecyclerView.Adapter<SeniorDutyAdapter.ViewHolder> {
    private Context context;
    private SeniorDutiesList list;
    private ArrayList<DarSeniorDutiesElements> myItems = new ArrayList<>();
    public SeniorDutyAdapter(ArrayList<DarSeniorDutiesElements> getItems, Context context,SeniorDutiesList list){
        try {
            this.context = context;
            this.list = list;
            myItems = getItems;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void filterList(ArrayList<DarSeniorDutiesElements> filterlist) {

        myItems = filterlist;

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public CheckBox cbSelect;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.nonf_txt_senior);
            cbSelect = (CheckBox) v.findViewById(R.id.nonf_check);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_offsite_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final DarSeniorDutiesElements object = myItems.get(position);
        holder.name.setText(object.getMenuName());
        holder.cbSelect.setOnCheckedChangeListener(null);
        holder.cbSelect.setChecked(object.isSelected());

        holder.name.setBackground(object.isSelected() ? AppCompatResources.getDrawable(context,R.drawable.dar_plain) :
                AppCompatResources.getDrawable(context,R.drawable.black_border));

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setSelected(!object.isSelected());
                holder.name.setBackground(object.isSelected() ? AppCompatResources.getDrawable(context,R.drawable.dar_plain) : AppCompatResources.getDrawable(context,R.drawable.black_border));

                list.pushElement(myItems);
            }
        });
       /* holder.cbSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            object.setSelected(isChecked);
            holder.mLayout.setBackground(model.isSelected() ? getDrawable(R.drawable.dar_plain) : getDrawable(R.drawable.black_border));

            list.pushElement(myItems);
        });*/


    }

    @Override
    public int getItemCount() {
        return myItems == null ? 0 : myItems.size();
    }
}