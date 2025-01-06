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

@Entity(tableName = "dar_vdr")
public class DarVdrElements {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private int id;

    @ColumnInfo(name = "veh_task_id")
    @SerializedName("veh_task_id")
    @Expose
    private int vehTaskId;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    private String name;
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
    private String isActive;
    @ColumnInfo(name = "order_number")
    @SerializedName("order_number")
    @Expose
    private Integer orderNumber;
    @ColumnInfo(name = "created_by")
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @ColumnInfo(name = "updated_by")
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).darVdrElementsDao().removeAll();
    }

    public static void removeById(Integer id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).darVdrElementsDao().removeById(id);
    }

    public static void insertDarVdrElementsList(DarVdrElements darVdrElements) {
        new InsertDarVdrElements(darVdrElements).execute();
    }

    public static ArrayList<DarVdrElements> _getDarVdrElementsList(int custId) {
        return (ArrayList<DarVdrElements>) ParkingDatabase.getInstance(TPApplication.getInstance()).darVdrElementsDao().getDarVdrElementsList(custId).subscribeOn(Schedulers.io()).blockingGet();
    }

    public static ArrayList<DarVdrElements> _getDarVdrList() {
        return (ArrayList<DarVdrElements>) ParkingDatabase.getInstance(TPApplication.getInstance()).darVdrElementsDao().getVdrList().subscribeOn(Schedulers.io()).blockingGet();
    }
    private static class InsertDarVdrElements extends AsyncTask<Void, Void, Void> {
        private DarVdrElements darVdrElements;

        public InsertDarVdrElements(DarVdrElements darVdrElements) {
            this.darVdrElements = darVdrElements;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).darVdrElementsDao().insertDarVdrElements(darVdrElements);
            return null;
        }
    }

    public int getVehTaskId() {
        return vehTaskId;
    }

    public void setVehTaskId(int vehTaskId) {
        this.vehTaskId = vehTaskId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
