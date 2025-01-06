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

@Entity(tableName = "dar_vehicle_list")
public class DarVehicleList {
    @PrimaryKey()
    @ColumnInfo(name = "veh_id")
    @SerializedName("veh_id")
    @Expose
    private Integer vehId;
    @ColumnInfo(name = "veh_name")
    @SerializedName("veh_name")
    @Expose
    private String vehName;

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
    @ColumnInfo(name = "Model")
    @SerializedName("Model")
    @Expose
    private String model;
    @ColumnInfo(name = "type")
    @SerializedName("type")
    @Expose
    private String type;

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

    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).equipmentDao().removeAll();
    }

    public static void removeById(Integer id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).equipmentDao().removeById(id);
    }

    public static void insertDarVehicleList(DarVehicleList equipments) {
        new InsertEquipments(equipments).execute();
    }
    public static ArrayList<DarVehicleList> _getDarVehicleList(int custId){
        return (ArrayList<DarVehicleList>) ParkingDatabase.getInstance(TPApplication.getInstance()).darVehicleListDao().getDarVehicleList(custId).subscribeOn(Schedulers.io()).blockingGet();
    }

    public static String getVehicleType(int  vehId) {
        String VehicleType = ParkingDatabase.getInstance(TPApplication.getInstance()).darVehicleListDao().getTypeByVehicleId(vehId);
        return VehicleType;
    }




    private static class InsertEquipments extends AsyncTask<Void,Void,Void> {
        private DarVehicleList equipments;
        public InsertEquipments(DarVehicleList equipments) {
            this.equipments = equipments;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).darVehicleListDao().insertDarVehicleList(equipments);
            return null;
        }
    }

    public Integer getVehId() {
        return vehId;
    }

    public void setVehId(Integer vehId) {
        this.vehId = vehId;
    }

    public String getVehName() {
        return vehName;
    }

    public void setVehName(String vehName) {
        this.vehName = vehName;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
