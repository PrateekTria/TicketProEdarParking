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

@Entity(tableName = "dar_22500_disposition_dropdown")
public class Dar22500DisposionDropDownElement {
    @PrimaryKey()
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
    private int syncDataId;
    @Ignore
    @SerializedName("primary_key")
    @Expose
    private int primaryKey;

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

    public static ArrayList<Dar22500DisposionDropDownElement> getElement(int custId){
        return (ArrayList<Dar22500DisposionDropDownElement>) ParkingDatabase.getInstance(TPApplication.getInstance()).dar22500DisposionDropDownElementDao().getDar22500DisposionDropDownElement(custId).subscribeOn(Schedulers.io()).blockingGet();
    }

    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).dar22500DisposionDropDownElementDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).darOffsiteDropdownElementsDao().removeById(id);
    }

    public static void insertDar22500DisposionDropDownElement(Dar22500DisposionDropDownElement dar22) {
        new InsertDar22500DisposionDropDownElement(dar22).execute();
    }

    private static class InsertDar22500DisposionDropDownElement extends AsyncTask<Void,Void,Void> {
        private Dar22500DisposionDropDownElement darAssignmentLocation;
        public InsertDar22500DisposionDropDownElement(Dar22500DisposionDropDownElement darAssignmentLocation) {
            this.darAssignmentLocation = darAssignmentLocation;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).dar22500DisposionDropDownElementDao().insertDar22500DisposionDropDownElement(darAssignmentLocation);
            return null;
        }
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
}
