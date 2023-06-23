package com.satmatgroup.shreeram.scanner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScannerResponse {

    @SerializedName("statuscode")
    @Expose
    private String statuscode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
