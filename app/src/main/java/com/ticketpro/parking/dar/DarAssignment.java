package com.ticketpro.parking.dar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.BaseActivityImpl;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.Assignments;
import com.ticketpro.parking.dar.model.DarAssignmentLocation;
import com.ticketpro.parking.service.EDarServiceJobIntentTask;
import com.ticketpro.util.DateUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
//Not active
public class DarAssignment extends BaseActivityImpl {
    private List<Assignments> groupList;
    private List<DarAssignmentLocation> childList;
    private Map<Assignments, List<DarAssignmentLocation>> assignmentCollection;
    private ExpandableListView expListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dar_assignment);
        TPApplication.getInstance().setDarStartTimeAssignment(DateUtil.getCurrentDateTime1());
        createGroupList();
        createCollection();

        expListView = (ExpandableListView) findViewById(R.id.el_assignment);
        final DarAssignmentAdapter expListAdapter = new DarAssignmentAdapter(
                this, groupList, assignmentCollection);
        expListView.setAdapter(expListAdapter);

        expListView.expandGroup(0);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = 0;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousItem )
                    expListView.collapseGroup(previousItem );
                previousItem = groupPosition;
            }
        });

        Button b  = findViewById(R.id.violation_back_btn);
        b.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(DarAssignment.this, EDarServiceJobIntentTask.class);
            serviceIntent.putExtra("START", TPApplication.getInstance().getDarStartTimeAssignment());
            serviceIntent.putExtra("END", DateUtil.getCurrentDateTime1());
            serviceIntent.putExtra("ID","");
            serviceIntent.putExtra("WITCH","One");
            EDarServiceJobIntentTask.enqueueWork(DarAssignment.this, serviceIntent);
            finish();
        });
    }

    public void expendView(int position){
        expListView.expandGroup(position);
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

    private void createGroupList() {
        groupList = new ArrayList<Assignments>();
        groupList = Assignments._getEquipmentList(TPApp.custId);

    }

    private void createCollection() {
        assignmentCollection = new LinkedHashMap<>();
        for (Assignments location : groupList) {
            loadChild(location.getIdAsg());
            assignmentCollection.put(location, childList);
        }
    }

    private void loadChild(int idAsg) {
        childList = new ArrayList<>();
        for (DarAssignmentLocation model : DarAssignmentLocation.getAssignmentLocation(idAsg))
            childList.add(model);
    }

}
