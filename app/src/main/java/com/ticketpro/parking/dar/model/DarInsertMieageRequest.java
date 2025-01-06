package com.ticketpro.parking.dar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DarInsertMieageRequest {
    @SerializedName("result")
    @Expose
    private DarinsertMileageRequestResult result;
    @SerializedName("id")
    @Expose
    private String id;

    public DarinsertMileageRequestResult getResult() {
        return result;
    }

    public void setResult(DarinsertMileageRequestResult result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}