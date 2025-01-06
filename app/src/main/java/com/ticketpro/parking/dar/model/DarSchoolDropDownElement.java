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

@Entity(tableName = "dar_school_drop_down")
public class DarSchoolDropDownElement {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private Integer id;
    @ColumnInfo(name = "dd_item")
    @SerializedName("dd_item")
    @Expose
    private String ddItem;
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

    @ColumnInfo(name = "district")
    @SerializedName("district")
    @Expose
    private String district;

    @ColumnInfo(name = "street")
    @SerializedName("street")
    @Expose
    private String street;

    @ColumnInfo(name = "city")
    @SerializedName("city")
    @Expose
    private String city;

    @ColumnInfo(name = "schooltype")
    @SerializedName("schooltype")
    @Expose
    private String schooltype;

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
        ParkingDatabase.getInstance(TPApplication.getInstance()).darSchoolDropDownElementDao().removeAll();
    }

    public static void removeById(Integer id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).darSchoolDropDownElementDao().removeById(id);
    }

    public static void insertDarSchoolDropDownElement(DarSchoolDropDownElement darSchoolDropDownElement) {
        new InsertEquipments(darSchoolDropDownElement).execute();
    }
    public static ArrayList<DarSchoolDropDownElement> _getDarSchoolDropDownElementList(int custId){
        return (ArrayList<DarSchoolDropDownElement>) ParkingDatabase.getInstance(TPApplication.getInstance()).darSchoolDropDownElementDao().getDarSchoolDropDown(custId).subscribeOn(Schedulers.io()).blockingGet();
    }

    public static ArrayList<DarSchoolDropDownElement> _getDarSchoolDropDowndetailss(int Id){
        return (ArrayList<DarSchoolDropDownElement>) ParkingDatabase.getInstance(TPApplication.getInstance()).darSchoolDropDownElementDao().getschooldropdowndetails(Id).subscribeOn(Schedulers.io()).blockingGet();
    }

    private static class InsertEquipments extends AsyncTask<Void,Void,Void> {
        private DarSchoolDropDownElement darSchoolDropDownElement;
        public InsertEquipments(DarSchoolDropDownElement equipments) {
            this.darSchoolDropDownElement = equipments;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).darSchoolDropDownElementDao().insertDarSchoolDropDown(darSchoolDropDownElement);
            return null;
        }
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDdItem() {
        return ddItem;
    }

    public void setDdItem(String ddItem) {
        this.ddItem = ddItem;
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
    public String getDistrict ()
    {
        return  district;
    }
    public  void setDistrict(String district)
    {
        this.district=district;
    }
    public String getStreet()
    {
        return  street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity()
   {
       return  city;
   }
    public void setCity(String city) {
        this.city = city;
    }

    public  String getSchooltype()
    {
        return schooltype;
    }

    public void setSchooltype(String schooltype) {
        this.schooltype = schooltype;
    }
}
