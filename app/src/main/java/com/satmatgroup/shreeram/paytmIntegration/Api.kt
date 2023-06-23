package com.satmatgroup.shreeram.paytmIntegration

import com.satmatgroup.shreeram.paytmIntegration.Checksum
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("generateChecksum.php")
    fun getChecksum(
        @Field("MID") mId: String?,
        @Field("PAYTM_MERCHANT_KEY") PAYTM_MERCHANT_KEY: String?,
        @Field("ORDER_ID") orderId: String?,
        @Field("CUST_ID") custId: String?,
        @Field("CHANNEL_ID") channelId: String?,
        @Field("TXN_AMOUNT") txnAmount: String?,
        @Field("WEBSITE") website: String?,
        @Field("CALLBACK_URL") callbackUrl: String?,
        @Field("INDUSTRY_TYPE_ID") industryTypeId: String?
    ): Call<Checksum?>?

    companion object {
        //this is the URL of the paytm folder that we added in the server
        //make sure you are using your ip else it will not work
        const val BASE_URL = "http://calllog.asmiglobalsoftwares.com/"
    }
}
