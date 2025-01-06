package com.ticketpro.parking.dar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.handlers.Schoolitemlistner;

import java.util.ArrayList;

public class DarSchoolListAdapter extends RecyclerView.Adapter<DarSchoolListAdapter.ViewHolder> {
    private ArrayList<String> DeviceModelArrayList;
    private Context context;
    private Schoolitemlistner schoolitemlistner;
    private  ArrayList<String> SchoolId;
    private Dialog alertDialog;
    ArrayList<String> SchoolList_ItemList;

    public DarSchoolListAdapter(Context context, ArrayList<String> VehicleModelArrayList, Schoolitemlistner  sc, ArrayList<String> SchoolId, Dialog alertDialog,ArrayList<String> SchoolList_ItemList) {
        this.DeviceModelArrayList = VehicleModelArrayList;
        this.context=context;
        this.schoolitemlistner=sc;
        this.SchoolId=SchoolId;
        this.alertDialog=alertDialog;
        this.SchoolList_ItemList=SchoolList_ItemList;

    }


    public void filterList(ArrayList<String> filterlist) {

        DeviceModelArrayList = filterlist;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DarSchoolListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_schoollist_adapter, parent, false);
        return new DarSchoolListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DarSchoolListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.device_textview.setText(DeviceModelArrayList.get(position));
        holder.device_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int j=1000;
                String s= DeviceModelArrayList.get(position);
                final String itemAtPosition = (String) DeviceModelArrayList.get(position);
                for (int i=0; i<SchoolList_ItemList.size();i++)
                {
                    if(SchoolList_ItemList.get(i).equals(s))
                         j=i;

                }
                String id=SchoolId.get(j);
                schoolitemlistner.onget(id,holder.device_textview.getText().toString());
                alertDialog.dismiss();
             /*   ((Activity)context).finish();*/

              /*  Intent i = new Intent();
                i.putExtra("VEHICLE", itemAtPosition);
                i.setClass(context, DarVehicleListAndTask.class);
                context.startActivity(i);
                ((Activity)context).finish();*/
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

            device_textview = itemView.findViewById(R.id.veh_itemname);

        }
    }
}
