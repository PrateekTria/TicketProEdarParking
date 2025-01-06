package com.ticketpro.parking.dar.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.DarAuthorityResponse;

import java.util.List;

public class DarAuthorityResult {

    @SerializedName("result")
    @Expose
    private List<DarAuthorityResponse> result = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;

    public List<DarAuthorityResponse> getResult() {
        return result;
    }

    public void setResult(List<DarAuthorityResponse> result) {
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
