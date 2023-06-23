package com.satmatgroup.shreeram.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BaseResponse<T> {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private T data;



    public T getResponsePacket() {
        return data;
    }

    public void setResponsePacket(T responsePacket) {
        this.data = responsePacket;
    }

    public boolean isResponseStatus() {
        return status;
    }

    public void setResponseStatus(boolean responseStatus) {
        this.status = responseStatus;
    }

    public BaseResponse<T> withResponseStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public String getResponseMessage() {
        return message;
    }

    public void setResponseMessage(String message) {
        this.message = message;
    }

    public BaseResponse<T> withResponseMessage(String message) {
        this.message = message;
        return this;
    }


    public BaseResponse<T> withResponsePacket(T data) {
        this.data = data;
        return this;
    }
}

