package com.satmatgroup.shreeram.scanner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {


    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("cyrusOrderId")
    @Expose
    private String cyrusOrderId;
    @SerializedName("cyrus_id")
    @Expose
    private String cyrusId;
    @SerializedName("opening_bal")
    @Expose
    private String openingBal;
    @SerializedName("locked_amt")
    @Expose
    private String lockedAmt;
    @SerializedName("charged_amt")
    @Expose
    private String chargedAmt;
    @SerializedName("rrn")
    @Expose
    private String rrn;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCyrusOrderId() {
        return cyrusOrderId;
    }

    public void setCyrusOrderId(String cyrusOrderId) {
        this.cyrusOrderId = cyrusOrderId;
    }

    public String getCyrusId() {
        return cyrusId;
    }

    public void setCyrusId(String cyrusId) {
        this.cyrusId = cyrusId;
    }

    public String getOpeningBal() {
        return openingBal;
    }

    public void setOpeningBal(String openingBal) {
        this.openingBal = openingBal;
    }

    public String getLockedAmt() {
        return lockedAmt;
    }

    public void setLockedAmt(String lockedAmt) {
        this.lockedAmt = lockedAmt;
    }

    public String getChargedAmt() {
        return chargedAmt;
    }

    public void setChargedAmt(String chargedAmt) {
        this.chargedAmt = chargedAmt;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }
}
