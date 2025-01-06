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

@Entity(tableName = "dar_assignment")
public class Assignments {
    @PrimaryKey
    @ColumnInfo(name = "id_asg")
    @SerializedName("id_asg")
    @Expose
    private int idAsg;

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

    public int getIdAsg() {
        return idAsg;
    }

    public void setIdAsg(int idAsg) {
        this.idAsg = idAsg;
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

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }


    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).assignmentsDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).assignmentsDao().removeById(id);
    }
    public static ArrayList<Assignments> _getEquipmentList(int custId){
        return (ArrayList<Assignments>) ParkingDatabase.getInstance(TPApplication.getInstance()).assignmentsDao().getAssignment(custId).subscribeOn(Schedulers.io()).blockingGet();
    }

    public static void insertAssignment(Assignments assignments) {
        new InsertAssignment(assignments).execute();
    }

    private static class InsertAssignment extends AsyncTask<Void,Void,Void> {
        private Assignments assignments;
        public InsertAssignment(Assignments assignments) {
            this.assignments = assignments;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).assignmentsDao().insertAssignments(assignments);
            return null;
        }
    }
}
