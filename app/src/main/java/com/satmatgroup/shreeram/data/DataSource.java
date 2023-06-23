package com.satmatgroup.shreeram.data;




import com.satmatgroup.shreeram.network.NetworkCall;
import com.satmatgroup.shreeram.network.ServiceCallBack;

import okhttp3.RequestBody;
import retrofit2.http.Part;


public interface DataSource {

    void getCategory(ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void saveRetry(String txnid, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void getPanForm(String username,String password, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);


}

