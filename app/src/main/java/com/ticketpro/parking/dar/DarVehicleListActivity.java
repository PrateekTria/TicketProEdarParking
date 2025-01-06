package com.ticketpro.parking.dar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.dar.model.DarVehicleList;
import com.ticketpro.parking.dar.model.DarVehicleTask;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class DarVehicleListActivity extends BaseActivityImpl {
    private ListView mListView;
    private TextView mItemName;
    SearchView searchView;
  ArrayList<String> listItem= new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dar_vehicle_list2);
        searchView=findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

        mListView=findViewById(R.id.vehicleList);

        /*searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText);
                return false;
            }
        });*/

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });

        Button _btnBack = findViewById(R.id.violation_back_btn);
        _btnBack.setOnClickListener(v -> finish());

        //String[] listItem = getResources().getStringArray(R.array.car_list);
        final ArrayList<DarVehicleList> darVehicleLists = DarVehicleList._getDarVehicleList(TPApp.custId);

       if (darVehicleLists.size()>0) {
           ArrayList<String> aList = new ArrayList<>();
           for (int i = 0; i < darVehicleLists.size(); i++) {
               String s = darVehicleLists.get(i).getVehName();
               aList.add(s);
               listItem.add(s);
           }
        /*   String listItem[] = aList.toArray(new String[darVehicleLists.size()]);*/

           final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listItem) {
               @Override
               public View getView(int position, View convertView, ViewGroup parent) {
                   View view = super.getView(position, convertView, parent);
                   TextView textView = (TextView) view.findViewById(android.R.id.text1);
                   /*YOUR CHOICE OF COLOR*/
                   textView.setTextColor(Color.BLACK);
                   textView.setPadding(15,0,0,0);
                   return view;
               }
           };
           mListView.setAdapter(adapter);
           mListView.setOnItemClickListener((adapterView, view, position, l) -> {
               final String itemAtPosition = (String) mListView.getItemAtPosition(position);
               Intent i = new Intent();
               i.putExtra("VEHICLE", itemAtPosition);
               i.setClass(DarVehicleListActivity.this, DarVehicleListAndTask.class);
               startActivity(i);
               //finish();
           });
       }

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

    public class VehicleViewAdapter extends ArrayAdapter<DarVehicleTask> {

        Context context;

        public VehicleViewAdapter(@NonNull Context context, ArrayList<DarVehicleTask> arrayList) {
            super(context, 0, arrayList);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View currentItemView = convertView;

            if (currentItemView == null) {
                currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_dar_vehicle_row, parent, false);
            }

            DarVehicleTask currentNumberPosition = getItem(position);

            //ImageView numbersImage = currentItemView.findViewById(R.id.imageView);
            assert currentNumberPosition != null;
            //numbersImage.setImageResource(currentNumberPosition.getNumbersImageId());

            TextView textView1 = currentItemView.findViewById(R.id.veh_itemName);
            RelativeLayout layout = currentItemView.findViewById(R.id.main_write_ticket_option);
            textView1.setText(currentNumberPosition.getVehTaskName());

            return currentItemView;
        }

    }
    private void filter(String text) {

        ArrayList<String> filteredlist = new ArrayList<String>();


        for (String item : listItem) {

            if (item.toLowerCase().contains(text.toLowerCase())) {

                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {

            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {

            listItem=filteredlist;
        }
    }
}