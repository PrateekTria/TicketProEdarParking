package com.ticketpro.parking.dar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ticketpro.model.Duty;
import com.ticketpro.model.DutyReport;
import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.Assignments;
import com.ticketpro.parking.dar.model.DarAssignmentLocation;
import com.ticketpro.util.AssignmentIdInterface;
import com.ticketpro.util.DarAssignmentIdLogout;
import com.ticketpro.util.DarAssignmentLocationIdInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class DarAssignmentActivityAdapter extends BaseExpandableListAdapter {

    private DarAssignmentActivity context;
    private Map<Assignments, List<Duty>> assignmentCollections;
    private List<Assignments> groupList;
    int gposition=1000;
    AssignmentIdInterface assignmentIdInterface;
    DarAssignmentLocationIdInterface darAssignmentLocationIdInterface;
    DarAssignmentIdLogout darAssignmentIdLogout;

    public DarAssignmentActivityAdapter(DarAssignmentActivity darAssignment, List<Assignments> groupList, Map<Assignments, List<Duty>> assignmentCollections, AssignmentIdInterface assignmentIdInterface, DarAssignmentLocationIdInterface darAssignmentLocationIdInterface, DarAssignmentIdLogout darAssignmentIdLogout) {

        this.context = darAssignment;
        this.assignmentCollections = assignmentCollections;
        this.groupList = groupList;
        this.assignmentIdInterface=assignmentIdInterface;
        this.darAssignmentLocationIdInterface=darAssignmentLocationIdInterface;
        this.darAssignmentIdLogout=darAssignmentIdLogout;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return assignmentCollections.get(groupList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return assignmentCollections.get(groupList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //String laptopName = (String) getGroup(groupPosition);

        Assignments assignments = groupList.get(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.dar_assignment_row1,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.groupView);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(assignments.getItems());
        item.setOnClickListener(v -> {
            if (item.getText().equals("Offsite / Non-Enforcement")) {
              //  assignmentIdInterface.getid(assignments.getIdAsg());
                Intent i = new Intent();
                i.putExtra("id_assg_loc", assignments.getIdAsg());
                i.setClass(context, OffsiteNonEnforcement.class);
                context.startActivity(i);
            }else {
                if (gposition==1000)
                {

                    gposition=groupPosition;
                    context.expendView(groupPosition);
                   assignmentIdInterface.getid(assignments.getIdAsg());

                }
                else  if(gposition==groupPosition)
                {
                  ///  context.expendView(groupPosition);
                 context.collapseAll(gposition);
                 darAssignmentIdLogout.logouttime(10000);
                    gposition=1000;

                }
                else {

                    gposition=groupPosition;
                    context.expendView(groupPosition);
                    darAssignmentIdLogout.logouttime(10000);
                   assignmentIdInterface.getid(assignments.getIdAsg());

                }

            }

        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Duty child = (Duty) getChild(groupPosition, childPosition);

        LayoutInflater inflater = context.getLayoutInflater();
        Assignments assignments = groupList.get(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dar_assignment_group_child_item, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.laptop);
        item.setText(child.getTitle());

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                 //   assignmentIdInterface.getid(assignments.getIdAsg());

                   /* Intent i = new Intent();*/
                    DutyReport dutyReport = new DutyReport();
                    dutyReport.setDateIn(new Date());
                    dutyReport.setDutyId(child.getId());
                    dutyReport.setUserId(TPApplication.getInstance().getCurrentUserId());
                    dutyReport.setCustId(TPApplication.getInstance().getCustId());
                    dutyReport.setDeviceId(TPApplication.getInstance().getDeviceId());
                    dutyReport.setDuty_report_id(System.currentTimeMillis() + "_" + TPApplication.getInstance().deviceId);
                    TPApplication.getInstance().setActiveDutyReport(dutyReport);
                    TPApplication.getInstance().setDarParkingdutyreportId(System.currentTimeMillis() + "_" + TPApplication.getInstance().deviceId);

                    TPApplication.getInstance().setActiveDutyInfo(child);
                    TPApplication.getInstance().setAct_assignmentid(assignments.getIdAsg());
                    TPApplication.getInstance().setAct_assignmentlocationpos(String.valueOf(childPosition));
                    TPApplication.getInstance().setAct_dutylocationid(child.getId());
                    TPApplication.getInstance().setAct_assignmentname(assignments.getItems());
                    TPApplication.getInstance().setAct_dutylocationname(child.getTitle());



                    darAssignmentLocationIdInterface.locationId(Integer.parseInt(child.getIdAsg()),childPosition);
                    /*
                    i.putExtra("id_assg", assignments.getIdAsg());
                    i.putExtra("id_assg_loc", child.getIdAsg());
                    i.putExtra("location_name",String.valueOf(childPosition));
                    i.putExtra("id_duty", child.getId());
                    i.putExtra("name",assignments.getItems());
                    i.putExtra("duty_name",child.getTitle());
                    i.setClass(context, DarLocationAndTask.class);
                    context.startActivity(i);*/

                } catch (Exception e) {
                    e.printStackTrace();
                    context.log.info(e.getMessage());

                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}



               /* if (gposition==1000  )
                {
                    if(gposition==groupPosition)
                    {
                        context.collapseAll(gposition);
                        Log.d("position",gposition+"$"+groupPosition);
                    }
                    else {
                        gposition=groupPosition;
                        context.expendView(groupPosition);
                        Log.d("position",gposition+"1"+"$"+groupPosition);
                    }

                }
                else
                {
                    gposition=groupPosition;
                    context.expendView(groupPosition);
                    Log.d("position",gposition+"1"+"$"+groupPosition);
                }*/
