package com.ticketpro.parking.dar.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.DarDeviceSearchActivity;
import com.ticketpro.parking.activity.handlers.NotUsedListner;
import com.ticketpro.parking.dar.DarSearchDeviceAdapter;

import java.util.ArrayList;
import java.util.List;

public class DarEquipmentNewAdapter extends RecyclerView.Adapter<DarEquipmentNewAdapter.ViewHolder>{
    Context context;
    private List<Equipments> groupList;
    private ArrayList<String> Search_childlist;
    DarSearchDeviceAdapter adapter;
    List<String> subequimentList;
    NotUsedListner notUsedListner;
    ArrayList<String> subequimentPosList;
    int value;
    ArrayList<String> childlistpos;

    public DarEquipmentNewAdapter(Context context, List<Equipments> groupList, ArrayList<String> subequimentList, int value, ArrayList<String> childlistpos, NotUsedListner notUsedListner) {
        this.context=context;
        this.groupList=groupList;
        this.subequimentList=subequimentList;
        this.value=value;
        this.childlistpos= childlistpos;
        this.notUsedListner=notUsedListner;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.dar_rquipment_new_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int i) {
        holder.item.setText(groupList.get(i).getItems());

        try {

            if (subequimentList.get(i).equals(""))
            {
                holder.itemchild.setText("");
            }
            else
            {
                holder.itemchild.setText(subequimentList.get(i));
            }
        }catch (Exception e)
        {

        }


        holder.EaLinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatechildList(groupList.get(i).getId());
                if (!Search_childlist.isEmpty())
                {

                    Intent intent= new Intent(context.getApplicationContext(), DarDeviceSearchActivity.class);
                    intent.putExtra("list",Search_childlist);
                    intent.putExtra("position",i);
                    intent.putStringArrayListExtra("childarraylist", (ArrayList<String>) subequimentList);
                    intent.putStringArrayListExtra("childarraylistpos",subequimentPosList);
                    intent.putStringArrayListExtra("positionchildarray",childlistpos);
                    intent.putExtra("value",value);
                    context.startActivity(intent);
                    ((Activity)context).finish();

                }

               /* Intent intent= new Intent(context.getApplicationContext(), DarDeviceSearchActivity.class);
                intent.putExtra("list",Search_childlist);
                intent.putExtra("position",i);
                intent.putStringArrayListExtra("childarraylist", (ArrayList<String>) subequimentList);
                intent.putStringArrayListExtra("childarraylistpos",subequimentPosList);
                intent.putStringArrayListExtra("positionchildarray",childlistpos);
                intent.putExtra("value",value);
                context.startActivity(intent);
                ((Activity)context).finish();*/
            }
        });

        holder.NotUSED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notUsedListner.onBtnClick(i,"NU","NU");
                notifyDataSetChanged();
               
            }
        });



    }


    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item, itemchild;
        public Button NotUSED;
        private LinearLayout EaLinearlayout;
        public DarSearchDeviceAdapter adapter;
        public ViewHolder(View itemView) {
            super(itemView);

            this.item = (TextView) itemView.findViewById(R.id.groupView);
            this.itemchild = (TextView) itemView.findViewById(R.id.childView);
            this.EaLinearlayout=(LinearLayout) itemView.findViewById(R.id.EaLinearlayout);
            this.NotUSED=(Button)itemView.findViewById(R.id.notUsed);


        }
    }



    public  void CreatechildList( int i)
    {

        List<DarChildEquipments> equipmentschildlist= new ArrayList<>();
        Search_childlist=new ArrayList<>();
        subequimentPosList= new ArrayList<>();
        equipmentschildlist.clear();
        equipmentschildlist = DarChildEquipments._getEquipmentList(i);
        try {
            for (int j=0;j<equipmentschildlist.size();j++)
            {
                Search_childlist.add(equipmentschildlist.get(j).getItems());
                subequimentPosList.add(String.valueOf(equipmentschildlist.get(j).getChildId()));
            }

        } catch (Exception E)
        {

        }

    }


}