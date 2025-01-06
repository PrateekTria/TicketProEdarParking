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

@Entity(tableName = "dar_location_task")
public class DarLocationActivityTask {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private int id;

    @ColumnInfo(name = "id_assg")
    @SerializedName("id_assg")
    @Expose
    private int idAssgLoc;
    @ColumnInfo(name = "custid")
    @SerializedName("custid")
    @Expose
    private int custId;

    @ColumnInfo(name = "userid")
    @SerializedName("userid")
    @Expose
    private int userid;

    @ColumnInfo(name = "items")
    @SerializedName("items")
    @Expose
    private String items;

    @ColumnInfo(name = "type")
    @SerializedName("type")
    @Expose
    private String type;

    @ColumnInfo(name = "order_number")
    @SerializedName("order_number")
    @Expose
    private int orderNumber;

    @Ignore
    @SerializedName("sync_data_id")
    @Expose
    private int syncDataId;
    @Ignore
    @SerializedName("primary_key")
    @Expose
    private int primaryKey;

    public static ArrayList<DarLocationActivityTask> getDarLocationActivityTask(int idAsg) {
        return (ArrayList<DarLocationActivityTask>) ParkingDatabase.getInstance(TPApplication.getInstance()).darLocationActivityTaskDao().getDarLocationActivityTask(idAsg).subscribeOn(Schedulers.io()).blockingGet();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAssgLoc() {
        return idAssgLoc;
    }

    public void setIdAssgLoc(int idAssgLoc) {
        this.idAssgLoc = idAssgLoc;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
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

    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).darAssignmentLocationDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).darAssignmentLocationDao().removeById(id);
    }

    public static void insertDarLocationActivityTask(DarLocationActivityTask darLocationActivityTask) {
        new InsertDarLocationActivityTask(darLocationActivityTask).execute();
    }

    private static class InsertDarLocationActivityTask extends AsyncTask<Void,Void,Void> {
        private DarLocationActivityTask darAssignmentLocation;
        public InsertDarLocationActivityTask(DarLocationActivityTask darAssignmentLocation) {
            this.darAssignmentLocation = darAssignmentLocation;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).darLocationActivityTaskDao().insertDarLocationActivityTask(darAssignmentLocation);
            return null;
        }
    }
}
