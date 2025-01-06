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

@Entity(tableName = "dar_break_and_lunch_dropdown")
public class DarBreakAndLunchDropDown {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private Integer id;
    @ColumnInfo(name = "dd_item")
    @SerializedName("dd_item")
    @Expose
    private String ddItem;
    @ColumnInfo(name = "userid")
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @ColumnInfo(name = "custid")
    @SerializedName("custid")
    @Expose
    private Integer custid;
    @ColumnInfo(name = "is_active")
    @SerializedName("is_active")
    @Expose
    private Integer isActive;
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

    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).darBreakAndLunchDropDownDao().removeAll();
    }

    public static void removeById(Integer id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).darBreakAndLunchDropDownDao().removeById(id);
    }

    public static void insertDarBreakAndLunchDropDown(DarBreakAndLunchDropDown darFieldContactDropdown) {
        new InsertDarBreakAndLunchDropDown(darFieldContactDropdown).execute();
    }
    public static ArrayList<DarBreakAndLunchDropDown> _getDarFieldContactDropdownList(int custId){
        return (ArrayList<DarBreakAndLunchDropDown>) ParkingDatabase.getInstance(TPApplication.getInstance()).darBreakAndLunchDropDownDao().getList(custId).subscribeOn(Schedulers.io()).blockingGet();
    }

    private static class InsertDarBreakAndLunchDropDown extends AsyncTask<Void,Void,Void> {
        private DarBreakAndLunchDropDown equipments;
        public InsertDarBreakAndLunchDropDown(DarBreakAndLunchDropDown equipments) {
            this.equipments = equipments;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).darBreakAndLunchDropDownDao().insertDarBreakAndLunchDropDown(equipments);
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDdItem() {
        return ddItem;
    }

    public void setDdItem(String ddItem) {
        this.ddItem = ddItem;
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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
