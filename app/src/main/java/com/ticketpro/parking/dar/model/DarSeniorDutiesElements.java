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

@Entity(tableName = "dar_senior_duties_dropdown")
public class DarSeniorDutiesElements {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private Integer id;

    @ColumnInfo(name = "menu_name")
    @SerializedName("menu_name")
    @Expose
    private String menuName;
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
    private Integer syncDataId;
    @Ignore
    @SerializedName("primary_key")
    @Expose
    private Integer primaryKey;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Integer getSyncDataId() {
        return syncDataId;
    }

    public void setSyncDataId(Integer syncDataId) {
        this.syncDataId = syncDataId;
    }

    public Integer getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Integer primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
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

    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).darSeniorDutiesElementsDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).darSeniorDutiesElementsDao().removeById(id);
    }


    public static void insertDarSeniorDutiesElements(DarSeniorDutiesElements  darSeniorDutiesElements) {
        new InsertDarSeniorDutiesElements(darSeniorDutiesElements).execute();
    }
    public static ArrayList<DarSeniorDutiesElements> _getList(int custId){
        return (ArrayList<DarSeniorDutiesElements>) ParkingDatabase.getInstance(TPApplication.getInstance()).darSeniorDutiesElementsDao().getDarSeniorDutiesElements(custId).subscribeOn(Schedulers.io()).blockingGet();
    }

    private static class InsertDarSeniorDutiesElements extends AsyncTask<Void,Void,Void> {
        private DarSeniorDutiesElements equipments;
        public InsertDarSeniorDutiesElements(DarSeniorDutiesElements equipments) {
            this.equipments = equipments;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).darSeniorDutiesElementsDao().insertDarSeniorDutiesElements(equipments);
            return null;
        }
    }
}