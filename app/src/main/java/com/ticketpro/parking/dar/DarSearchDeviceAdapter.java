package com.ticketpro.parking.dar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ticketpro.model.EquipmentModel;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.DarDeviceSearchActivity;
import com.ticketpro.parking.activity.handlers.ItemClickListener;
import com.ticketpro.parking.dar.model.DarChildEquipments;

import java.util.ArrayList;
import java.util.List;

public class DarSearchDeviceAdapter extends RecyclerView.Adapter<DarSearchDeviceAdapter.ViewHolder> {
    private ArrayList<String> DeviceModelArrayList;
    private Context context;
    ArrayList<String> listchildlistpos;
    int l;
    ItemClickListener itemClickListener;
    ArrayList<String> EquipmentIdlist;
    public DarSearchDeviceAdapter(Context context, ArrayList<String> deviceModelArrayList, int l,ItemClickListener itemClickListener,ArrayList<String> listchildlistpos,ArrayList<String> EquipmentIdlist) {
        this.DeviceModelArrayList = deviceModelArrayList;
        this.context=context;
        this.l=l;
        this.itemClickListener=itemClickListener;
        this.listchildlistpos= listchildlistpos;
        this.EquipmentIdlist=EquipmentIdlist;
    }


    public void filterList(ArrayList<String> filterlist) {

        DeviceModelArrayList = filterlist;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DarSearchDeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DarSearchDeviceAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String model = DeviceModelArrayList.get(position);
        holder.device_textview.setText(DeviceModelArrayList.get(position));
        holder.device_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int j=0;
                holder.device_textview.setTextColor(Color.parseColor("#000fff"));
                for (int i=0;i<EquipmentIdlist.size();i++)
                {
                   if (EquipmentIdlist.get(i).equals(holder.device_textview.getText().toString())){
                       j=i;
                       break;
                   }
                }


                itemClickListener.onClick(l,holder.device_textview.getText().toString(),listchildlistpos.get(j));


                /*context.setResult(Activity.RESULT_OK,intent);*/
                /*    ((DarDeviceSearchActivity)context).setResult(100,intent);*/

            }
        });

    }



    @Override
    public int getItemCount() {

        return DeviceModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView device_textview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            device_textview = itemView.findViewById(R.id.device_child_tview);

        }
    }
}