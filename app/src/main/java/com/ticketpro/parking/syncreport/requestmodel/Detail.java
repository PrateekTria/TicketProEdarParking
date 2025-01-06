package com.ticketpro.parking.syncreport.requestmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

    @SerializedName("duty_id")
    @Expose
    private Integer dutyId;
    @SerializedName("date_in_time")
    @Expose
    private String dateInTime;

    public Integer getDutyId() {
        return dutyId;
    }

    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
    }

    public String getDateInTime() {
        return dateInTime;
    }

    public void setDateInTime(String dateInTime) {
        this.dateInTime = dateInTime;
    }
}
