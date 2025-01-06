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

import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.activity.handlers.ItemClickListener;
import com.ticketpro.parking.dar.model.DarVehicleList;

import java.util.ArrayList;

public class DarVehicleListNewAdapter extends RecyclerView.Adapter<DarVehicleListNewAdapter.ViewHolder> {
    private ArrayList<String> DeviceModelArrayList;
    private Context context;
    private  ArrayList<String> listId;
    private  ArrayList<String> vehiclelist;
    String Veh_Type="";

    public DarVehicleListNewAdapter(Context context,ArrayList<String> VehicleModelArrayList ,ArrayList<String> listId,ArrayList<String> vehicleList ) {
        this.DeviceModelArrayList = VehicleModelArrayList;
        this.context=context;
        this.listId=listId;
        this.vehiclelist=vehicleList;

    }


    public void filterList(ArrayList<String> filterlist) {

        DeviceModelArrayList = filterlist;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DarVehicleListNewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vechile_new_row_adapter, parent, false);
        return new DarVehicleListNewAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DarVehicleListNewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.device_textview.setText(DeviceModelArrayList.get(position));
        holder.device_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vehicleId=" ";
                final String vehicleNumber = (String) DeviceModelArrayList.get(position);

                try {
                    for (int i=0;i<vehiclelist.size();i++)
                    {
                        if (vehiclelist.get(i)==vehicleNumber)
                        {
                            vehicleId=listId.get(i);
                        }
                    }
                    TPApplication.getInstance().setVehicleNumberString(vehicleNumber);
                    TPApplication.getInstance().setVehicleid(vehicleId);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    Veh_Type= DarVehicleList.getVehicleType(Integer.valueOf(vehicleId));

                }catch (Exception e)
                {
                  e.printStackTrace();
                }

                if(Veh_Type != null && Veh_Type.trim().equals("NV"))
                {
                        TPApplication.getInstance().setVehicleString(Veh_Type.trim());
                        Intent i = new Intent();
                        i.putExtra("VEHICLE", vehicleNumber.replaceAll("\\s", ""));
                        i.putExtra("VEHICLEID", vehicleId);
                        i.setClass(context, ScheduleShift.class);
                        context.startActivity(i);
                        ((Activity) context).finish();

                }else{
                    TPApplication.getInstance().setVehicleString("");
                    Intent i = new Intent();
                    i.putExtra("VEHICLE", vehicleNumber.replaceAll("\\s", ""));
                    i.putExtra("VEHICLEID",vehicleId);
                    i.setClass(context, DarVehicleListAndTask.class);
                    context.startActivity(i);
                    ((Activity)context).finish();
                }

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