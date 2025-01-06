package com.ticketpro.parking.dar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.DarDeviceSearchActivity;
import com.ticketpro.parking.dar.model.DarVehicleList;
import com.ticketpro.util.Preference;

import java.util.ArrayList;

public class DarVehiclelistNewActivity extends BaseActivityImpl {
    SearchView searchView;
    ArrayList<String> listItem= new ArrayList<>();
    ArrayList<String> listId=new ArrayList<>();
    ArrayList<String> vehiclelist= new ArrayList<>();
    DarVehicleListNewAdapter adapter;
    RecyclerView Rcvehiclelist;
    Preference preference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dar_vehiclelist_new);
        searchView= findViewById(R.id.searchView1);
        preference = Preference.getInstance(DarVehiclelistNewActivity.this);
        preference.putString("coulmunId","");
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.setQueryHint("Search Vehicle");
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
     /*   searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });*/
        Rcvehiclelist= findViewById(R.id.vehiclelist_recyclerview);
        getActionBar().hide();

        final ArrayList<DarVehicleList> darVehicleLists = DarVehicleList._getDarVehicleList(TPApp.custId);

        if (darVehicleLists.size()>0) {
            ArrayList<String> aList = new ArrayList<>();
            for (int i = 0; i < darVehicleLists.size(); i++) {
                String s = darVehicleLists.get(i).getVehName();
                String ids=String.valueOf(darVehicleLists.get(i).getVehId());
                aList.add(s);
                listItem.add(s);
                vehiclelist.add(s);
                listId.add(ids);
            }
        }
        buildrecyle();

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

    }

    private void filter(String text) {

        ArrayList<String> filteredlist = new ArrayList<String>();


        for (String item : listItem) {

            if (item.toLowerCase().contains(text.toLowerCase())) {

                filteredlist.add(item);
            }
        }
       /* if (filteredlist.isEmpty()) {

           *//* Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();*//*
        } else {

          // adapter.filterList(filteredlist);
        }*/
        adapter.filterList(filteredlist);
    }

    @Override
    public void onClick(View view) {

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
    public  void buildrecyle()
    {
        adapter = new DarVehicleListNewAdapter(DarVehiclelistNewActivity .this, listItem,listId,vehiclelist);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        Rcvehiclelist.setHasFixedSize(true);
        Rcvehiclelist.setLayoutManager(manager);
        Rcvehiclelist.setAdapter(adapter);
    }


}