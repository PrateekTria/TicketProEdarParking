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
import android.widget.TextView;

import com.ticketpro.parking.R;
import com.ticketpro.parking.dar.model.DarVehicleTask;
import com.ticketpro.parking.dar.model.Temp;

import java.util.List;
import java.util.Map;
public class DarVehicleListAdapter extends BaseExpandableListAdapter {
    final Vibrator vibrator;

    private EquipmentSelectedList list;
    private final DarVehicleListAndTask context;
    private Map<DarVehicleTask, List<Temp>> equipmentCollections;
    private List<DarVehicleTask> groupList;
    public DarVehicleListAdapter(DarVehicleListAndTask darAssignment, List<DarVehicleTask> groupList, Map<DarVehicleTask, List<Temp>> assignmentCollections) {
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
        DarVehicleTask assignments = groupList.get(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.dar_assignment_group_item, null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.laptop);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(assignments.getVehTaskName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Temp child = (Temp) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();
        DarVehicleTask assignments = groupList.get(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dar_equipment_child_row, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.laptop);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.child_check);
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
        item.setText(child.getName());
        cb.setChecked(child.isSelected());

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final VibrationEffect vibrationEffect4;

                // this type of vibration requires API 29
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {

                    // create vibrator effect with the constant EFFECT_TICK
                    vibrationEffect4 = VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK);

                    // it is safe to cancel other vibrations currently taking place
                    vibrator.cancel();

                    vibrator.vibrate(vibrationEffect4);
                }

                List<Temp> darChildEquipments = equipmentCollections.get(groupList.get(groupPosition));
                if (child.isSelected()) {
                    darChildEquipments.get(childPosition).setSelected(false);
                } else {
                    for (int i = 0; i < darChildEquipments.size(); i++) {
                        Temp dc = darChildEquipments.get(i);
                        if (dc.isSelected()){
                            dc.setSelected(false);
                        }
                    }
                    darChildEquipments.get(childPosition).setSelected(true);
                }
                DarVehicleListAdapter.this.notifyDataSetChanged();
               /* if (list != null) {
                    list.push(equipmentCollections);
                }*/
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
