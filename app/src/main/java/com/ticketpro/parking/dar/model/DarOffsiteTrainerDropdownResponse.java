package com.ticketpro.parking.dar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DarOffsiteTrainerDropdownResponse {
    @SerializedName("result")
    @Expose
    private List<DarOffsiteTrainerDropdown> result = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;

    public List<DarOffsiteTrainerDropdown> getResult() {
        return result;
    }

    public void setResult(List<DarOffsiteTrainerDropdown> result) {
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

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }
}
