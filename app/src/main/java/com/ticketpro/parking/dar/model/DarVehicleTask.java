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

@Entity(tableName = "dar_vehicle_task")
public class DarVehicleTask {
    @PrimaryKey
    @ColumnInfo(name = "veh_task_id")
    @SerializedName("veh_task_id")
    @Expose
    private Integer vehTaskId;

    @ColumnInfo(name = "veh_task_name")
    @SerializedName("veh_task_name")
    @Expose
    private String vehTaskName;

    @ColumnInfo(name = "userid")
    @SerializedName("userid")
    @Expose
    private Integer userid;

    @ColumnInfo(name = "custid")
    @SerializedName("custid")
    @Expose
    private Integer custid;

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

    public Integer getVehTaskId() {
        return vehTaskId;
    }

    public void setVehTaskId(Integer vehTaskId) {
        this.vehTaskId = vehTaskId;
    }

    public String getVehTaskName() {
        return vehTaskName;
    }

    public void setVehTaskName(String vehTaskName) {
        this.vehTaskName = vehTaskName;
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

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).darVehicleTaskDao().removeById(id);
    }


    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).darVehicleTaskDao().removeAll();
    }

    public static void insertDarVehicleTask(DarVehicleTask  darVehicleTask) {
        new InsertDarVehicleTask(darVehicleTask).execute();
    }
    public static ArrayList<DarVehicleTask> _getDarVehicleTasktList(int userid){
        return (ArrayList<DarVehicleTask>) ParkingDatabase.getInstance(TPApplication.getInstance()).darVehicleTaskDao().getDarVehicleTask(userid).subscribeOn(Schedulers.io()).blockingGet();
    }

    private static class InsertDarVehicleTask extends AsyncTask<Void,Void,Void> {
        private DarVehicleTask equipments;
        public InsertDarVehicleTask(DarVehicleTask equipments) {
            this.equipments = equipments;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).darVehicleTaskDao().insertDarVehicleTask(equipments);
            return null;
        }
    }
}
