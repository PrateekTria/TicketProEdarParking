package com.ticketpro.parking.dar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DarTicketResult {
    @SerializedName("app_id")
    @Expose
    private List<Integer> appId = null;
    @SerializedName("result")
    @Expose
    private Boolean result;

    public List<Integer> getAppId() {
        return appId;
    }

    public void setAppId(List<Integer> appId) {
        this.appId = appId;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
