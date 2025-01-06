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

@Entity(tableName = "dar_OffSiteNonEnforcement_dropdown")
public class DarOffsiteDropdownElementsResult {
    @PrimaryKey
    @ColumnInfo(name = "dd_elementId")
    @SerializedName("dd_elementId")
    @Expose
    private Integer ddElementId;

    @ColumnInfo(name = "dd_elementName")
    @SerializedName("dd_elementName")
    @Expose
    private String ddElementName;

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

    @ColumnInfo(name = "activity_id")
    @SerializedName("activity_id")
    @Expose
    private Integer activityId;

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

    public Integer getDdElementId() {
        return ddElementId;
    }

    public void setDdElementId(Integer ddElementId) {
        this.ddElementId = ddElementId;
    }

    public String getDdElementName() {
        return ddElementName;
    }

    public void setDdElementName(String ddElementName) {
        this.ddElementName = ddElementName;
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

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
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

    public void getList(int custid) throws Exception{
        ParkingDatabase.getInstance(TPApplication.getInstance()).darOffsiteDropdownElementsDao().getDarOffsiteDropdownElements(custid);
    }

    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).darOffsiteDropdownElementsDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).darOffsiteDropdownElementsDao().removeById(id);
    }


    public static void insertDarOffsiteDropdownElementsResult(DarOffsiteDropdownElementsResult  darSeniorDutiesElements) {
        new InsertDarOffsiteDropdownElementsResult(darSeniorDutiesElements).execute();
    }
    public static ArrayList<DarOffsiteDropdownElementsResult> _getDarOffsiteDropdownElementsResultList(int custId){
        return (ArrayList<DarOffsiteDropdownElementsResult>) ParkingDatabase.getInstance(TPApplication.getInstance()).darOffsiteDropdownElementsDao().getDarOffsiteDropdownElements(custId).subscribeOn(Schedulers.io()).blockingGet();
    }

    private static class InsertDarOffsiteDropdownElementsResult extends AsyncTask<Void,Void,Void> {
        private DarOffsiteDropdownElementsResult darOffsiteDropdownElementsResult;
        public InsertDarOffsiteDropdownElementsResult(DarOffsiteDropdownElementsResult darOffsiteDropdownElementsResult) {
            this.darOffsiteDropdownElementsResult = darOffsiteDropdownElementsResult;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).darOffsiteDropdownElementsDao().insertDarOffsiteDropdownElements(darOffsiteDropdownElementsResult);
            return null;
        }
    }
}
