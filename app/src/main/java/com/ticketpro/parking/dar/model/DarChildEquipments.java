package com.ticketpro.parking.dar.model;

import android.os.AsyncTask;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dao.ParkingDatabase;

import java.util.ArrayList;

import io.reactivex.schedulers.Schedulers;

@Entity(tableName = "dar_equipment_child")
public class DarChildEquipments {
    @PrimaryKey
    @ColumnInfo(name = "child_id")
    @SerializedName("child_id")
    @Expose
    private Integer childId;

    @ColumnInfo(name = "equipment_id")
    @SerializedName("equipment_id")
    @Expose
    private Integer equipmentId;

    @ColumnInfo(name = "userid")
    @SerializedName("userid")
    @Expose
    private Integer userid;

    @ColumnInfo(name = "custid")
    @SerializedName("custid")
    @Expose
    private Integer custid;

    @ColumnInfo(name = "items")
    @SerializedName("items")
    @Expose
    private String items;

    @ColumnInfo(name = "order_number")
    @SerializedName("order_number")
    @Expose
    private Integer orderNumber;

    @Ignore
    @SerializedName("sync_data_id")
    @Expose
    private int syncDataId;
    @Ignore
    @SerializedName("primary_key")
    @Expose
    private int primaryKey;

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).equipmentChaildDao().removeAll();
    }

    public static void removeById(Integer id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).equipmentChaildDao().removeById(id);
    }

    public static void insertEquipment(DarChildEquipments equipments) {
        new InsertEquipments(equipments).execute();
    }
    public static ArrayList<DarChildEquipments> _getEquipmentList(int custId){
        return (ArrayList<DarChildEquipments>) ParkingDatabase.getInstance(TPApplication.getInstance()).equipmentChaildDao().getEquipmentChildByTitle(custId).subscribeOn(Schedulers.io()).blockingGet();
    }
    /*public static ArrayList<DarChildEquipments> _getEquipmentListbyitem(int items){
        return (ArrayList<DarChildEquipments>) ParkingDatabase.getInstance(TPApplication.getInstance()).equipmentChaildDao().getEquipmentChildByitem(items).subscribeOn(Schedulers.io()).blockingGet();
    }*/

    private static class InsertEquipments extends AsyncTask<Void,Void,Void> {
        private DarChildEquipments equipments;
        public InsertEquipments(DarChildEquipments equipments) {
            this.equipments = equipments;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).equipmentChaildDao().insertEquipmentChild(equipments);
            return null;
        }
    }

    public int getSyncDataId() {
        return syncDataId;
    }

    public void setSyncDataId(int syncDataId) {
        this.syncDataId = syncDataId;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Integer getChildId() {
        return childId;
    }

    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getCustid() {
        return custid;
    }

    public void setCustid(Integer custid) {
        this.custid = custid;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }


}