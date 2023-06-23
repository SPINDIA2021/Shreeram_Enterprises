package com.satmatgroup.shreeram.data.remote;




import com.myapp.onlysratchapp.category.CategoryResponse;
import com.satmatgroup.shreeram.data.DataSource;
import com.satmatgroup.shreeram.network.BaseResponse;
import com.satmatgroup.shreeram.network.IApi;
import com.satmatgroup.shreeram.network.NetworkCall;
import com.satmatgroup.shreeram.network.ServiceCallBack;


import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class RemoteDataSource implements DataSource {
    private static RemoteDataSource INSTANCE;

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }




    @Override
    public void getCategory(ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{

            RequestBody for1 = RequestBody.create(MediaType.parse("text/plain"), "appcategory");
            Call<BaseResponse<ArrayList<CategoryResponse>>> responceCall = networkCall.getRetrofit(true, true).getCategory(for1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveRetry(String txnid, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{

            RequestBody txnid1 = RequestBody.create(MediaType.parse("text/plain"), txnid);


            Call<BaseResponse> responceCall = networkCall.getRetrofit(true, true).saveRetry(txnid1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG1);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void getPanForm(String username, String password, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{

            RequestBody username1 = RequestBody.create(MediaType.parse("text/plain"), username);
            RequestBody password1 = RequestBody.create(MediaType.parse("text/plain"), password);


            Call<BaseResponse> responceCall = networkCall.getRetrofit(true, true).getPanForm(username1,password1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


