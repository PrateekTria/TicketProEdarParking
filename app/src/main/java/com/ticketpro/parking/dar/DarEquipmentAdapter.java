package com.ticketpro.parking.dar;

import android.content.Context;
import android.graphics.Typeface;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.ticketpro.parking.R;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dar.model.DarChildEquipments;
import com.ticketpro.parking.dar.model.Equipments;

import java.util.List;
import java.util.Map;

public class DarEquipmentAdapter extends BaseExpandableListAdapter {
    final Vibrator vibrator;

    private EquipmentSelectedList list;
    private final DarEquipmentNew context;
    private Map<Equipments, List<DarChildEquipments>> equipmentCollections;
    private List<Equipments> groupList;
    public DarEquipmentAdapter(DarEquipmentNew darAssignment, List<Equipments> groupList, Map<Equipments,
            List<DarChildEquipments>> assignmentCollections, EquipmentSelectedList list) {
        this.list = list;
        this.context = darAssignment;
        this.equipmentCollections = assignmentCollections;
        this.groupList = groupList;

       vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return equipmentCollections.get(groupList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return equipmentCollections.get(groupList.get(groupPosition)).get(childPosition);

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
        Equipments assignments = groupList.get(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.dar_assignment_group_item, null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.groupView);
        TextView itemChild = (TextView) convertView.findViewById(R.id.childView);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(assignments.getItems());
        if (assignments.getItems().equals("Handheld")){
            itemChild.setText(TPApplication.getInstance().deviceName);
        }else {

            itemChild.setText(assignments.getChildItems());
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        DarChildEquipments child = (DarChildEquipments) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();
        Equipments assignments = groupList.get(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dar_equipment_child_row, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.laptop);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.child_check);
        LinearLayout childLayout = (LinearLayout) convertView.findViewById(R.id.child_layout);
       /* cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            List<DarChildEquipments> darChildEquipments = equipmentCollections.get(groupList.get(groupPosition));
            if (isChecked) {
                darChildEquipments.get(childPosition).setSelected(true);
            } else {
                darChildEquipments.get(childPosition).setSelected(false);
            }
            notifyDataSetChanged();
            if(list!=null){
                list.push(equipmentCollections);
            }
        });*/
        item.setText(child.getItems());
        cb.setChecked(child.isSelected());
        childLayout.setBackground(child.isSelected() ? AppCompatResources.getDrawable(context,R.drawable.dar_plain) : AppCompatResources.getDrawable(context,R.drawable.black_border));

        item.setOnClickListener(v -> {
            final VibrationEffect vibrationEffect4;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                vibrationEffect4 = VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK);
                vibrator.cancel();
                vibrator.vibrate(vibrationEffect4);
            }
            List<DarChildEquipments> darChildEquipments = equipmentCollections.get(groupList.get(groupPosition));
            if (child.isSelected()) {
                darChildEquipments.get(childPosition).setSelected(false);
            } else {
                for (int i = 0; i < darChildEquipments.size(); i++) {
                    DarChildEquipments dc = darChildEquipments.get(i);
                    if (dc.isSelected()){
                        dc.setSelected(false);
                    }
                }
                groupList.get(groupPosition).setChildItems(darChildEquipments.get(childPosition).getItems());
                darChildEquipments.get(childPosition).setSelected(true);
            }
            ((DarEquipmentNew)context).collapseAll();
            DarEquipmentAdapter.this.notifyDataSetChanged();
            if (list != null) {
                list.push(equipmentCollections);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
