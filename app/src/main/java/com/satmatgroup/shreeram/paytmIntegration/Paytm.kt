package com.satmatgroup.shreeram.paytmIntegration

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.util.*

class Paytm(
    @field:SerializedName("MID") var mId: String,
    @field:SerializedName(
        "PAYTM_MERCHANT_KEY"
    ) var pAYTM_MERCHANT_KEY: String,
    channelId: String,
    txnAmount: String,
    website: String,
    callBackUrl: String,
    industryTypeId: String
) {

    @SerializedName("ORDER_ID")
    var orderId: String

    @SerializedName("CUST_ID")
    var custId: String

    @SerializedName("CHANNEL_ID")
    var channelId: String

    @SerializedName("TXN_AMOUNT")
    var txnAmount: String

    @SerializedName("WEBSITE")
    var website: String

    @SerializedName("CALLBACK_URL")
    var callBackUrl: String

    @SerializedName("INDUSTRY_TYPE_ID")
    var industryTypeId: String

    @SerializedName("REFID")
    var rEFID: String

    fun getmId(): String {
        return mId
    }

    /*
  * The following method we are using to generate a random string everytime
  * As we need a unique customer id and order id everytime
  * For real scenario you can implement it with your own application logic
  * */
    private fun generateString(): String {
        val uuid = UUID.randomUUID().toString()
        return uuid.replace("-".toRegex(), "")
    }

    init {
        orderId = generateString()
        custId = generateString()
        this.channelId = channelId
        this.txnAmount = txnAmount
        this.website = website
        this.callBackUrl = callBackUrl
        this.industryTypeId = industryTypeId
        rEFID = generateString()
        Log.d("orderId", orderId)
        Log.d("customerId", custId)
    }
}