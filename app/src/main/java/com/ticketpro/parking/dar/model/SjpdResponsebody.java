package com.ticketpro.parking.dar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SjpdResponsebody {

    @SerializedName("app_id")
    @Expose
    private List<String> appId;
    @SerializedName("dar_call_signin_id")
    @Expose
    private List<String> darCallSigninId;
    @SerializedName("result")
    @Expose
    private Boolean result;

    public List<String> getAppId() {
        return appId;
    }

    public void setAppId(List<String> appId) {
        this.appId = appId;
    }

    public List<String> getDarCallSigninId() {
        return darCallSigninId;
    }

    public void setDarCallSigninId(List<String> darCallSigninId) {
        this.darCallSigninId = darCallSigninId;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

}

