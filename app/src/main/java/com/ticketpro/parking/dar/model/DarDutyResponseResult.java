package com.ticketpro.parking.dar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DarDutyResponseResult {

    @SerializedName("result")
    @Expose
    private Boolean result;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

}
