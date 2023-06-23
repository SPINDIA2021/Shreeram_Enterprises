package com.satmatgroup.shreeram.category;

import android.content.Context;

import androidx.annotation.NonNull;


import com.myapp.onlysratchapp.category.CategoryResponse;
import com.satmatgroup.shreeram.data.DataSource;
import com.satmatgroup.shreeram.data.remote.RemoteDataSource;
import com.satmatgroup.shreeram.network.BaseResponse;
import com.satmatgroup.shreeram.network.IApi;
import com.satmatgroup.shreeram.network.NetworkCall;
import com.satmatgroup.shreeram.network.ServiceCallBack;
import com.satmatgroup.shreeram.network.Util;

import java.util.ArrayList;

import retrofit2.Response;


public class CategoryPresenter implements CategoryContract.Presenter, ServiceCallBack {
    private final DataSource loginDataSource;
    private final CategoryContract.View mLoginView;
    private Context context;


    public CategoryPresenter(@NonNull RemoteDataSource listDataSource, CategoryContract.View loginFragment) {
        loginDataSource = listDataSource;
        mLoginView = loginFragment;
        mLoginView.setPresenter(this);

    }


    @Override
    public void onSuccess(int tag, Response<BaseResponse> baseResponse) {
        if (tag == IApi.COMMON_TAG) {
            BaseResponse response = baseResponse.body();
            if (response != null) {
                if (response.isResponseStatus() == true) {
                    //Toast.makeText(context, response.getResponseMessage(), Toast.LENGTH_LONG).show();

                    ArrayList<CategoryResponse> userData = (ArrayList<CategoryResponse>) response.getResponsePacket();


                    mLoginView.categoryResponse(userData);


                } else {

                    Util.showAlertBox(context, response.getResponseMessage(), null);
                }

            }

        }


        if (tag == IApi.COMMON_TAG1) {
            BaseResponse response = baseResponse.body();
            if (response != null) {
                if (response.isResponseStatus() == true) {
                    //Toast.makeText(context, response.getResponseMessage(), Toast.LENGTH_LONG).show();

                    String userData = (String) response.getResponsePacket();


                    mLoginView.retryResponse(userData);


                } else {

                    Util.showAlertBox(context, response.getResponseMessage(), null);
                }

            }

        }

    }


    @Override
    public void onFail(int requestTag, Throwable t) {

    }




    @Override
    public void getCategory( Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.getCategory(this, networkCall);
    }

    @Override
    public void saveRetry(String txnid, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.saveRetry(txnid,this, networkCall);
    }


}
