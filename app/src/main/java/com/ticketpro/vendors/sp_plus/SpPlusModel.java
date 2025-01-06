package com.ticketpro.vendors.sp_plus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.util.DateUtil;
import com.ticketpro.vendors.ParkingExpireInfo;

import java.util.Date;

public class SpPlusModel {
    @SerializedName("licensePlate")
    @Expose
    private String licensePlate;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("accountStatus")
    @Expose
    private String accountStatus;
    @SerializedName("startAt")
    @Expose
    private String startAt;
    @SerializedName("stopAt")
    @Expose
    private String stopAt;
    @SerializedName("parkerType")
    @Expose
    private String parkerType;

    private Date startDateLocal;
    private Date endDateLocal;

    public Date getStartDateLocal() {
        return startDateLocal;
    }

    public void setStartDateLocal(Date startDateLocal) {
        this.startDateLocal = startDateLocal;
    }

    public Date getEndDateLocal() {
        return endDateLocal;
    }

    public void setEndDateLocal(Date endDateLocal) {
        this.endDateLocal = endDateLocal;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getStopAt() {
        return stopAt;
    }

    public void setStopAt(String stopAt) {
        this.stopAt = stopAt;
    }

    public String getParkerType() {
        return parkerType;
    }

    public void setParkerType(String parkerType) {
        this.parkerType = parkerType;
    }

    public ParkingExpireInfo getExpireInfo(){
        endDateLocal = DateUtil.spPlusTime(getStopAt());
        ParkingExpireInfo parkingExpireInfo=new ParkingExpireInfo();
        if (endDateLocal==null)
            return null;
        String expireStr="";
        long diffMinutes,diffHours,diffDays;
        long expiredDiff = new Date().getTime() - endDateLocal.getTime();
        if(expiredDiff > 0){
            diffMinutes = expiredDiff / (60 * 1000) % 60;
            diffHours = expiredDiff / (60 * 60 * 1000) % 24;
            diffDays = expiredDiff / (24 * 60 * 60 * 1000);
            if(diffDays>=1){
                expireStr=diffDays+" d "+diffHours +" h ago";

            }else if(diffHours>=1){
                expireStr=diffHours+" h "+diffMinutes +" m ago";

            }else{
                expireStr=diffMinutes +" m ago";
            }

            parkingExpireInfo.setExpired(true);

        }else{
            expiredDiff=Math.abs(expiredDiff);
            diffMinutes = expiredDiff / (60 * 1000) % 60;
            diffHours = expiredDiff / (60 * 60 * 1000) % 24;
            diffDays = expiredDiff / (24 * 60 * 60 * 1000);

            if(diffDays>=1){
                expireStr=/*"in "+*/diffDays+" d "+diffHours+" h";

            }else if(diffHours>=1){
                expireStr=/*"in "+*/diffHours+" h "+diffMinutes+" m";

            }else{
                expireStr=/*"in "+*/diffMinutes +" m";
            }

            parkingExpireInfo.setExpired(false);
        }

        parkingExpireInfo.setExpireMsg(expireStr);
        parkingExpireInfo.setDiffDays((int)diffDays);
        parkingExpireInfo.setDiffHrs((int)diffHours);
        parkingExpireInfo.setDiffMinutes((int)diffMinutes);
        parkingExpireInfo.setDiffSeconds((int)diffMinutes * 60);

        return parkingExpireInfo;
    }

}
