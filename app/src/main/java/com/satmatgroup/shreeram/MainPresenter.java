package com.satmatgroup.shreeram;

import android.content.Context;

import androidx.annotation.NonNull;

import com.satmatgroup.shreeram.data.DataSource;
import com.satmatgroup.shreeram.data.remote.RemoteDataSource;
import com.satmatgroup.shreeram.network.BaseResponse;
import com.satmatgroup.shreeram.network.IApi;
import com.satmatgroup.shreeram.network.NetworkCall;
import com.satmatgroup.shreeram.network.ServiceCallBack;
import com.satmatgroup.shreeram.network.Util;

import retrofit2.Response;


public class MainPresenter implements com.satmatgroup.shreeram.MainContract.Presenter, ServiceCallBack {
    private final DataSource loginDataSource;
    private final com.satmatgroup.shreeram.MainContract.View mLoginView;
    private Context context;


    public MainPresenter(@NonNull RemoteDataSource listDataSource, com.satmatgroup.shreeram.MainContract.View loginFragment) {
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

                    String userData = (String) response.getResponseMessage();


                    mLoginView.panFormResponse(userData);


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
    public void getPanForm(String username, String password, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.getPanForm(username,password,this, networkCall);
    }
}
