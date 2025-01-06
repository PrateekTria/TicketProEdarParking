package com.ticketpro.parking.dar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dar22500DisposionDropDownElementResponse {

    @SerializedName("result")
    @Expose
    private List<Dar22500DisposionDropDownElement> result = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;

    public List<Dar22500DisposionDropDownElement> getResult() {
        return result;
    }

    public void setResult(List<Dar22500DisposionDropDownElement> result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }
}
