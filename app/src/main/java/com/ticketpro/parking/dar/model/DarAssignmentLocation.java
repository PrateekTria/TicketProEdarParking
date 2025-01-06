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

@Entity(tableName = "dar_assignment_location")
public class DarAssignmentLocation {
    @PrimaryKey
    @ColumnInfo(name = "id_assg_loc")
    @SerializedName("id_assg_loc")
    @Expose
    private int idAssgLoc;

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

    @ColumnInfo(name = "assignments_name")
    @SerializedName("assignments_name")
    @Expose
    private String assignmentsName;

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

    public static ArrayList<DarAssignmentLocation> getAssignmentLocation(int idAsg) {
        return (ArrayList<DarAssignmentLocation>) ParkingDatabase.getInstance(TPApplication.getInstance()).darAssignmentLocationDao().getDarAssignmentLocationByTitle(idAsg).subscribeOn(Schedulers.io()).blockingGet();

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

    public int getIdAssgLoc() {
        return idAssgLoc;
    }

    public void setIdAssgLoc(int idAssgLoc) {
        this.idAssgLoc = idAssgLoc;
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

    public String getAssignmentsName() {
        return assignmentsName;
    }

    public void setAssignmentsName(String assignmentsName) {
        this.assignmentsName = assignmentsName;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }


    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).darAssignmentLocationDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).darAssignmentLocationDao().removeById(id);
    }

    public static void insertAssignment(DarAssignmentLocation darAssignmentLocation) {
        new InsertDarAssignmentLocation(darAssignmentLocation).execute();
    }

    private static class InsertDarAssignmentLocation extends AsyncTask<Void,Void,Void> {
        private DarAssignmentLocation darAssignmentLocation;
        public InsertDarAssignmentLocation(DarAssignmentLocation darAssignmentLocation) {
            this.darAssignmentLocation = darAssignmentLocation;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).darAssignmentLocationDao().insertDarAssignmentLocation(darAssignmentLocation);
            return null;
        }
    }
}
