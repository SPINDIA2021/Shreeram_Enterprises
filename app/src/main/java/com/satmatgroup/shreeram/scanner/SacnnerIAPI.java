package com.satmatgroup.shreeram.scanner;

import com.satmatgroup.shreeram.network.PanCardFormResonse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface SacnnerIAPI {

    // String BASE_URL="https://api.mewarpe.com/";
    String BASE_URL="https://shreeramenterprises.org.in/";


    @Multipart
    // @POST("api/upi_payout")
    @POST("Upi/paytransfer_api")
    Call<PanCardFormResonse> saveScan(/*@Part("orderId") RequestBody orderId,*/
            @Part("amount") RequestBody amount,
            @Part("vpa") RequestBody vpa,
            @Part("name") RequestBody name,
            @Part("mon_no") RequestBody mon_no,
            @Part("cus_id") RequestBody cus_id/*,
                                   @Part("member_id") RequestBody member_id,
                                   @Part("password") RequestBody password*/);


    @Multipart
    @POST("Upi/paytransfer_api")
    Call<ScannerFalseResponse> saveFalseScan(/*@Part("orderId") RequestBody orderId,*/
            @Part("amount") RequestBody amount,
            @Part("vpa") RequestBody vpa,
            @Part("name") RequestBody name,
            @Part("mon_no") RequestBody mon_no,
            @Part("cus_id") RequestBody cus_id/*,
                                   @Part("member_id") RequestBody member_id,
                                   @Part("password") RequestBody password*/);
}
/*
public interface SacnnerIAPI {

    String BASE_URL="https://aeps.shreeramenterprises.org.in/";


    @Multipart
    @POST("paytransfer_api.php")
    Call<ScannerResponse> saveScan(@Part("vpa") RequestBody vpa,
                                   @Part("name") RequestBody name,
                                   @Part("amount") RequestBody amount,
                                   @Part("mon_no") RequestBody mon_no);


    @Multipart
    @POST("paytransfer_api.php")
    Call<ScannerFalseResponse> saveFalseScan(@Part("vpa") RequestBody vpa,
                                             @Part("name") RequestBody name,
                                             @Part("amount") RequestBody amount,
                                             @Part("mon_no") RequestBody mon_no);
}
*/
