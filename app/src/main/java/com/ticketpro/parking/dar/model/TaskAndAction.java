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

@Entity(tableName = "dar_task_action")
public class TaskAndAction {
    @PrimaryKey
    @ColumnInfo(name = "action_id")
    @SerializedName("action_id")
    @Expose
    private int actionId;

    @ColumnInfo(name = "activity_id")
    @SerializedName("activity_id")
    @Expose
    private int activityId;


    @ColumnInfo(name = "id_assg_loc")
    @SerializedName("id_assg_loc")
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

    @ColumnInfo(name = "action_name")
    @SerializedName("action_name")
    @Expose
    private String actionName;

    @ColumnInfo(name = "order_number")
    @SerializedName("order_number")
    @Expose
    private int orderNumber;

    @ColumnInfo(name = "is_active")
    @SerializedName("is_active")
    @Expose
    private String isActive;

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

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
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

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public int getIdAssgLoc() {
        return idAssgLoc;
    }

    public void setIdAssgLoc(int idAssgLoc) {
        this.idAssgLoc = idAssgLoc;
    }

    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).equipmentDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).equipmentDao().removeById(id);
    }

    public static void insertTaskAndAction(TaskAndAction taskAndAction) {
        new InsertTaskAndAction(taskAndAction).execute();
    }

    private static class InsertTaskAndAction extends AsyncTask<Void,Void,Void> {
        private TaskAndAction taskAndAction;
        public InsertTaskAndAction(TaskAndAction taskAndAction) {
            this.taskAndAction = taskAndAction;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).taskAndActionDao().insertTaskAndAction(taskAndAction);
            return null;
        }
    }
}
