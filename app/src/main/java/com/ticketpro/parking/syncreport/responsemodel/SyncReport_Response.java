package com.ticketpro.parking.syncreport.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SyncReport_Response {
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("id")
    @Expose
    private String id;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
