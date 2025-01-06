package com.ticketpro.parking.dar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ticketpro.parking.R;
import com.ticketpro.parking.dar.model.Assignments;
import com.ticketpro.parking.dar.model.DarAssignmentLocation;

import java.util.List;
import java.util.Map;

public class DarAssignmentAdapter extends BaseExpandableListAdapter {

    private DarAssignment context;
    private Map<Assignments, List<DarAssignmentLocation>> assignmentCollections;
    private List<Assignments> groupList;
    public DarAssignmentAdapter(DarAssignment darAssignment, List<Assignments> groupList, Map<Assignments, List<DarAssignmentLocation>> assignmentCollections) {

        this.context = darAssignment;
        this.assignmentCollections = assignmentCollections;
        this.groupList = groupList;
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
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getText().equals("Offsite / Non-Enforcement")) {
                    Intent i = new Intent();
                    i.putExtra("id_assg_loc", assignments.getIdAsg());
                    i.setClass(context, OffsiteNonEnforcement.class);
                    context.startActivity(i);
                }else {
                    context.expendView(groupPosition);
                }

            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        DarAssignmentLocation child = (DarAssignmentLocation) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();
        Assignments assignments = groupList.get(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dar_assignment_group_child_item, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.laptop);
        item.setText(child.getItems());

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("id_assg", assignments.getIdAsg());
                i.putExtra("id_assg_loc", child.getIdAsg());
                i.putExtra("name",assignments.getItems());
                i.setClass(context, DarLocationAndTask.class);
                context.startActivity(i);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
