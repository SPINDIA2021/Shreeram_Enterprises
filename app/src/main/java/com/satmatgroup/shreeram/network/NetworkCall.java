package com.satmatgroup.shreeram.network;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.satmatgroup.shreeram.R;
import com.satmatgroup.shreeram.authentication.LoginActivity;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkCall {
    private final Context context;
    private Dialog mDialog;
    private int requestTag;
    private ServiceCallBack serviceCallBack;

    private final Callback<BaseResponse> callback = new Callback<BaseResponse>() {

        @Override
        public void onResponse(Call<BaseResponse> call, retrofit2.Response<BaseResponse> response) {
            Util.showLog(response.toString());//response.errorBody().source().readByteString()
            dismissLoading();

             if (response.raw().code() == 401||response!=null&&response.body()!=null&& !response.body().isResponseStatus() ) {
                Util.showAlertBox(context, response.body().getResponseMessage(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Preferences.clearAll();//clear all data
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                });
            }
            else
            {
                serviceCallBack.onSuccess(requestTag, response);
            }
        }

        @Override
        public void onFailure(@NotNull Call call, Throwable t) {
            dismissLoading();
            Util.showLog(t.getMessage());
            Log.v("MobPrep","Api Error: "+t.getMessage());
            Util.showAlertBox(context, context.getString(R.string.technical_error), null);
            serviceCallBack.onFail(requestTag,t);
        }
    };

    public ServiceCallBack getServiceCallBack() {
        return serviceCallBack;
    }

    public void setServiceCallBack(ServiceCallBack serviceCallBack) {
        this.serviceCallBack = serviceCallBack;
    }

    public int getRequestTag() {
        return requestTag;
    }

    public Callback requestCallback() {
        return callback;
    }

    public void setRequestTag(int requestType) {
        this.requestTag = requestType;
    }

    public NetworkCall(Context context) {
        this.context = context;
    }

    public IApi getRetrofit(boolean isShowLoading, final boolean pass) {

        if (Util.isOnline(context)) {
            final CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

            System.setProperty("http.keepAlive", "false");
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.connectTimeout(10, TimeUnit.MINUTES).readTimeout(10, TimeUnit.MINUTES);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder builder = original.newBuilder();

                    //    if(pass)
                    builder.addHeader("Accept", "application/json");
                    builder.addHeader("Content-Type", "application/json");

                    /*if (!TextUtils.isEmpty(Preferences.getString(Preferences.AUTH_TOKEN))) {
                        builder.header("Authorization", Preferences.getString(Preferences.AUTH_TOKEN));
                    }*/
                    Request request = builder.method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient client = httpClient.build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(IApi.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            if (isShowLoading && context instanceof AppCompatActivity)
                showLoading();
            // prepare call in Retrofit 2.0

            IApi api = retrofit.create(IApi.class);
//        Call<BaseResponce> call = api.callService(json);
            //asynchronous call
//        call.enqueue(this);
            return api;
        } else {
            Util.showAlertBox(context, "Please Check internet connection", null);
            return null;
        }
    }

    private void showLoading() {
        try {
            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDialog = null;

                    if(mDialog != null)
                        mDialog.dismiss();
                    if (mDialog == null) {

                        mDialog = new Dialog(context);
                        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        mDialog.setContentView(R.layout.progress_dialog);
                        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.show();
                    }
                }
            });

        } catch (Exception e) {
            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDialog = null;
                    if (mDialog == null) {

                        mDialog = new Dialog(context);
                        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        mDialog.setContentView(R.layout.progress_dialog);
                        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.show();
                    }
                }
            });
        }
    }

    public void dismissLoading() {
        try {
            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mDialog != null) {
                        mDialog.cancel();
                        mDialog.dismiss();
                    }
                }
            });

        } catch (Exception e) {
            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mDialog != null) {
                        mDialog.cancel();
                        mDialog.dismiss();
                    }
                }
            });
            e.printStackTrace();
        }
    }
}
