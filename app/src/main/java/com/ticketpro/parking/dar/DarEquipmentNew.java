package com.ticketpro.parking.dar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.LoginSelectUserActivity;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.activity.handlers.NotUsedListner;
import com.ticketpro.parking.dar.model.DarChildEquipments;
import com.ticketpro.parking.dar.model.DarEquipmentNewAdapter;
import com.ticketpro.parking.dar.model.Equipments;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DarEquipmentNew extends BaseActivityImpl implements EquipmentSelectedList, NotUsedListner {

    private List<Equipments> groupList;
    private List<DarChildEquipments> equipmentschildlist;
    private List<DarChildEquipments> childList = new ArrayList<>();
    private List<String> subequimentlist;
    private Map<Equipments, List<DarChildEquipments>> equipmentCollection;
    private ExpandableListView expListView;
    private DarEquipmentAdapter expListAdapter;
    private Button back, vehicle;
    private TextView deviceName;
    private String deviceId = "deviceid";

    private int value = 1;
    private int child_posi;
    private RecyclerView recyclerView;

    ArrayList<String> subequimentPosList;
    ArrayList<String> selectedchildlist = new ArrayList<>();
    Boolean checked = true;

    private DarSearchDeviceAdapter darSearchDeviceAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dar_equipment_new);
        setLogger(DarEquipmentNew.class.getName());
        cretechildlist();
        createGroupList();
        createCollection();


        try {
            value = getIntent().getIntExtra("value", 0);
            if (value == 0) {
                value = 1;
            } else {
                value = 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // log.error(e.getMessage());
        }

        back = findViewById(R.id.violation_back_btn);
        deviceName = findViewById(R.id.deviceName);
        recyclerView = findViewById(R.id.recycler_view_list);

        try {
            deviceName.setText(TPApplication.getInstance().getDeviceInfo().getDevice());
        } catch (Exception e) {
            e.printStackTrace();
            //  log.error(e.getMessage());
        }
        if (value == 1) {
            subequimentlist = new ArrayList<String>();
            subequimentlist.add(0, "");
            subequimentlist.add(1, "");
            subequimentlist.add(2, "");
            subequimentlist.add(3, "");
            subequimentlist.add(4, "");
            subequimentlist.add(5, "");
            subequimentlist.add(6, "");
            subequimentlist.add(7, "");
            subequimentlist.add(8, "");
            subequimentlist.add(9, "");

            subequimentPosList = new ArrayList<String>();
            subequimentPosList.add(0, "");
            subequimentPosList.add(1, "");
            subequimentPosList.add(2, "");
            subequimentPosList.add(3, "");
            subequimentPosList.add(4, "");
            subequimentPosList.add(5, "");
            subequimentPosList.add(6, "");
            subequimentPosList.add(7, "");
            subequimentPosList.add(8, "");
            subequimentPosList.add(9, "");


        } else {
            try {
                deviceId = getIntent().getStringExtra("DeviceId");
                child_posi = getIntent().getIntExtra("position_id", 0);
                subequimentlist = getIntent().getStringArrayListExtra("childarraylist");
                subequimentPosList = getIntent().getStringArrayListExtra("positionchildarray");
                groupList.get(child_posi).setChildItems(deviceId);
            } catch (Exception e1) {
                e1.printStackTrace();
                log.error(e1.getMessage());
            }
        }

        vehicle = findViewById(R.id.eq_btnVehicle);
        back.setOnClickListener(v -> {
            TPApplication.getInstance().setEquipment(null);
            TPApplication.getInstance().setEquipmentChild(null);
            Intent intent = new Intent(DarEquipmentNew.this, LoginSelectUserActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        DarEquipmentNewAdapter adapter = new DarEquipmentNewAdapter(this, groupList, (ArrayList<String>) subequimentlist, value, subequimentPosList,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        expListView = findViewById(R.id.eq_exp_list);

        vehicle.setOnClickListener(v -> {
            selectedchildlist.clear();
            StringBuilder strEquipment = new StringBuilder();
            StringBuilder strEquipmentChild = new StringBuilder();
            TPApplication.getInstance().setEquipment(null);
            TPApplication.getInstance().setEquipmentChild(null);

            for (int m = 0; m < subequimentlist.size() ; m++) {
                if (subequimentlist.get(m).equals("")) {

                } else {
                    strEquipment.append(groupList.get(m).getId()).append(",");

                }
            }
            for (int k = 0; k < subequimentPosList.size() ; k++) {
                if (subequimentPosList.get(k).equals("")) {
                } else {
                    strEquipmentChild.append(subequimentPosList.get(k)).append(",");

                    selectedchildlist.add(subequimentPosList.get(k));
                }
            }


            strEquipment.append(TPApplication.getInstance().getDeviceInfo().getDeviceId()).append(",");
            if (strEquipment.length() > 1) {
                TPApplication.getInstance().setEquipment(strEquipment.deleteCharAt(strEquipment.length() - 1).toString());

            }
            if (strEquipmentChild.length() > 0) {
                TPApplication.getInstance().setEquipmentChild(strEquipmentChild.deleteCharAt(strEquipmentChild.length() - 1).toString());
            }
            /*if (checked == true) {
                Intent i = new Intent();
                i.setClass(DarEquipmentNew.this, DarVehiclelistNewActivity.class);
                startActivity(i);
            }*/
            if (groupList.size() == selectedchildlist.size()) {
                Intent i = new Intent();
                i.setClass(DarEquipmentNew.this, DarVehiclelistNewActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(DarEquipmentNew.this, "Please select all equipments", Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void collapseAll() {
       /* int count = expListAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            expListView.collapseGroup(i);
        }*/
    }

    private void createGroupList() {
        equipmentschildlist = new ArrayList<>();
        groupList = new ArrayList<Equipments>();
        groupList = Equipments._getEquipmentList(TPApp.custId);
    }

    private void cretechildlist() {
        subequimentlist = new ArrayList<>();
        subequimentlist.clear();
    }

    private void createCollection() {
        equipmentCollection = new LinkedHashMap<>();

        for (Equipments equipments : groupList) {
            loadChild(equipments.getId());
      /*      for (int i=0;i<childList.size()-1;i++)
            {
                Search_childlist.add(childList.get(equipments.getId()));
                Log.d("child_", Search_childlist.toString());
            }*/

            equipmentCollection.put(equipments, childList);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            //  Toast.makeText(this, data.getStringExtra("DeviceId").toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadChild(int idAsg) {
        /* childList = new ArrayList<>();*/
        for (DarChildEquipments model : DarChildEquipments._getEquipmentList(idAsg))
            childList.add(model);
        /* Search_childlist=childList;*/


     /*   for(int i=0;i<childList.size();i++){

           Search_childlist.add(childList.get(i));

            Log.d("child_", childList.get(i).getItems());
        }
*/
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

    @Override
    public void push(Map<Equipments, List<DarChildEquipments>> aList) {
        if (aList != null && !aList.isEmpty()) {
            equipmentCollection = aList;
        }
    }

    public void startCommentActivity(Intent i) {
        startActivityForResult(i, 100);
    }
   /* public  void dc(ArrayList<String> arrayList)
    {
        darSearchDeviceAdapter.filterList(arrayList);
    }
*/

    @Override
    public void onBackPressed() {
        TPApplication.getInstance().setEquipment(null);
        TPApplication.getInstance().setEquipmentChild(null);
        Intent intent = new Intent(DarEquipmentNew.this, LoginSelectUserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBtnClick(int position, String value, String posvalue) {
        subequimentlist.set(position,value);
        subequimentPosList.set(position,value);

    }



    /*    expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                editor.putInt("group_id",groupPosition);
                editor.commit();


                if(groupPosition != previousItem )
                    expListView.collapseGroup(previousItem );
                previousItem = groupPosition;
            }
        });*/

/*
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                Intent intent= new Intent();
                intent.putParcelableArrayListExtra("List", (ArrayList<? extends Parcelable>) equipmentschildlist);
                intent.setClass(getApplicationContext(),DarDeviceSearchActivity.class);
                startActivity(intent);
                return false;
            }
        });*/
        /*expListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            if(expListView.isGroupExpanded(groupPosition)){
                expListView.collapseGroup(groupPosition);
            }else{
                expListView.expandGroup(groupPosition);
            }
            //expListView.collapseGroup(groupPosition);

            return false;
        });
       *//* expListAdapter = new DarEquipmentAdapter(this,groupList,Search_childlist, equipmentCollection,DarEquipmentNew.this,deviceId,itemClickListener);
        expListView.setAdapter(expListAdapter);*//*
        if (groupList.size()>0 && !equipmentCollection.isEmpty()){

            expListView.expandGroup(1);
        }*/


      /*for (Map.Entry<Equipments, List<DarChildEquipments>> entry : equipmentCollection.entrySet()) {
                Equipments key = entry.getKey();
                List<DarChildEquipments> tab = entry.getValue();
                for (DarChildEquipments dc:tab) {
                    if (dc.isSelected()){
                        System.out.println("key is ====>"+key.getId()+" value "+dc.getChildId());

                        strEquipmentChild.append(dc.getChildId()).append(",");
                    }
                }
            }*/

     /*private void createGroupchildList( int i) {
           position_listt=String.valueOf(i);
           int k = 0;


        equipmentschildlist = new ArrayList<DarChildEquipments>();
         equipmentschildlist.clear();
        Search_childlist.clear();
        equipmentschildlist = DarChildEquipments._getEquipmentchild(k);

        try {
            for (int j=0;j<equipmentschildlist.size()-1;j++)
            {
                 Search_childlist.add(equipmentschildlist.get(j).getItems());
            }
            Log.d("Size",String.valueOf(equipmentschildlist.size()));
        } catch (Exception E)
        {
        }
       }*/

}