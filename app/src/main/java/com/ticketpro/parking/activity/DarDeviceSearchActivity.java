package com.ticketpro.parking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.handlers.ItemClickListener;
import com.ticketpro.parking.dar.DarEquipmentNew;
import com.ticketpro.parking.dar.DarSearchDeviceAdapter;

import java.util.ArrayList;
import java.util.List;

public class DarDeviceSearchActivity extends AppCompatActivity implements ItemClickListener {

    private RecyclerView courseRV;
    private ArrayList<String> Search_childlist = new ArrayList<>();
    private DarSearchDeviceAdapter adapter;
    private int index;
    ItemClickListener itemClickListener;
    List<String> listchildlist;
    ArrayList<String> listchildlistpos;
    ArrayList<String> childidPos;
    ArrayList<String> EquimentIdList = new ArrayList<>();
    SearchView searchView;
    Button btnNotUse_found;
    LinearLayout dataFound_lyt;
    private TextView noDataText;

    int value;
    /*private ArrayList<EquipmentModel> DeviceModelArrayList;*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dar_device_search);
        //] getSupportActionBar().setTitle("Search");
        getSupportActionBar().setTitle("Search");
        getSupportActionBar().hide();
        courseRV = findViewById(R.id.SearchDeviceRecycler);
        btnNotUse_found = findViewById(R.id.not_use_btn_1);
        dataFound_lyt = findViewById(R.id.vehicle_data_found_lyt);
        noDataText = findViewById(R.id.no_data_found_txt);
        searchView = findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
       /* searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });*/
        listchildlist = new ArrayList<>();
        value = getIntent().getIntExtra("value", 0);
        Search_childlist = getIntent().getStringArrayListExtra("list");
        EquimentIdList = Search_childlist;

        if (EquimentIdList.size() == 0) {
            noDataText.setVisibility(View.VISIBLE);
        } else {
            noDataText.setVisibility(View.GONE);
        }

        index = getIntent().getIntExtra("position", 0);
        listchildlist = getIntent().getStringArrayListExtra("childarraylist");
        listchildlistpos = getIntent().getStringArrayListExtra("childarraylistpos");
        childidPos = getIntent().getStringArrayListExtra("positionchildarray");

        buildRecyclerView();
        btnNotUse_found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    listchildlist.set(index, "NU");
                    childidPos.set(index, "NU");
                } catch (Exception e) {
                    Log.i("exception", e.toString());


                }

                Intent intent = new Intent(getApplicationContext(), DarEquipmentNew.class);
                intent.putStringArrayListExtra("childarraylist", (ArrayList<String>) listchildlist);
                intent.putExtra("value", 2);
                intent.putStringArrayListExtra("positionchildarray", childidPos);
                startActivity(intent);
                finish();
            }
        });

      /*  itemClickListener= new ItemClickListener() {
            @Override
            public void onClick(int position, String value) {

            }
        };


    }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();


        inflater.inflate(R.menu.search_menu, menu);

/*
        MenuItem searchItem = menu.findItem(R.id.actionSearch);


        searchView = (SearchView) searchItem.getActionView();*/
        /* searchView.setIconified(false);*/


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {

        ArrayList<String> filteredlist = new ArrayList<String>();


        for (String item : Search_childlist) {

            if (item.toLowerCase().contains(text.toLowerCase())) {

                filteredlist.add(item);
            }
        }
        /*   if (filteredlist.isEmpty()) {

         *//* Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();*//*
        } else {


        }*/
        adapter.filterList(filteredlist);
    }


    private void buildRecyclerView() {
        adapter = new DarSearchDeviceAdapter(DarDeviceSearchActivity.this, Search_childlist, index, this, listchildlistpos, EquimentIdList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRV.setHasFixedSize(true);
        courseRV.setLayoutManager(manager);
        courseRV.setAdapter(adapter);
    }

    @Override
    public void onClick(int position, String value, String posvalue) {
        try {
            listchildlist.set(position, value);
            childidPos.set(position, posvalue);
        } catch (Exception e) {
            Log.i("exception", e.toString());
        }

        Intent intent = new Intent(getApplicationContext(), DarEquipmentNew.class);
        intent.putStringArrayListExtra("childarraylist", (ArrayList<String>) listchildlist);
        intent.putExtra("value", 2);
        intent.putStringArrayListExtra("positionchildarray", childidPos);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), DarEquipmentNew.class);
        intent.putStringArrayListExtra("childarraylist", (ArrayList<String>) listchildlist);
        intent.putExtra("value", 2);
        intent.putStringArrayListExtra("positionchildarray", childidPos);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }


    /* @Override
    public void onClick(int position, String value) {
        try {

            listchildlist.set(position,value);


           *//*  int x=DarChildEquipments._getEquipmentListbyitem("7142");*//*



     *//*  if (listchildlist.get(position).equals(""))
            {
                *//**//*  listchildlist.add(position,value);*//**//*


            }*//*

        }catch (Exception e)
        {
          Log.i("exception",e.toString());
        }

        Intent intent= new Intent(getApplicationContext(), DarEquipmentNew.class);
        intent.putStringArrayListExtra("childarraylist", (ArrayList<String>) listchildlist);
        intent.putExtra("value",2);
        startActivity(intent);
        finish();

    }*/
}