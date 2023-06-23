package com.satmatgroup.shreeram;


import android.content.Context;

import com.satmatgroup.shreeram.network.BaseView;


public interface MainContract {
    interface View extends BaseView<Presenter> {

        void panFormResponse(String panCardFormResonse);
    }

    interface Presenter {


        void getPanForm( String username, String password, Context context);

    }
}
