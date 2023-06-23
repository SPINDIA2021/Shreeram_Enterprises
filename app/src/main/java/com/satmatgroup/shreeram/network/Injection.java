/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.satmatgroup.shreeram.network;

import android.content.Context;

import com.satmatgroup.shreeram.data.DataSource;
import com.satmatgroup.shreeram.data.remote.RemoteDataSource;


/**
 * Enables injection of mock implementations for
 * {@link } at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static RemoteDataSource provideLoginRepository(Context context) {
        return RemoteDataSource.getInstance();
    }

    public static RemoteDataSource provideregisterRepository(Context context) {
        return RemoteDataSource.getInstance();
    }
    public static RemoteDataSource providedocprofileRepository(Context context) {
        return RemoteDataSource.getInstance();
    }

    public static RemoteDataSource providemyapplistRepository(Context context) {
        return RemoteDataSource.getInstance();
    }

    public static RemoteDataSource providelogoutRepository(Context context) {
        return RemoteDataSource.getInstance();
    }

    public static RemoteDataSource providesaveAppRepository(Context context) {
        return RemoteDataSource.getInstance();
    }

    public static RemoteDataSource providesuitesRepository(Context context) {
        return RemoteDataSource.getInstance();
    }
    public static RemoteDataSource providespinnerRepository(Context context) {
        return RemoteDataSource.getInstance();
    }

    public static RemoteDataSource providefeedbackRepository(Context context) {
        return RemoteDataSource.getInstance();
    }

    public static RemoteDataSource provideforgotpasswordRepository(Context context) {
        return RemoteDataSource.getInstance();
    }

    public static RemoteDataSource providecontactusRepository(Context context) {
        return RemoteDataSource.getInstance();
    }

    public static RemoteDataSource providehealthtipsitemRepository(Context context) {
        return RemoteDataSource.getInstance();
    }

    public static RemoteDataSource providehealthtoolsbmiRepository(Context context) {
        return RemoteDataSource.getInstance();
    }

    public static RemoteDataSource providelabreportsRepository(Context context) {
        return RemoteDataSource.getInstance();
    }

    public static RemoteDataSource provideRegisterRepository(Context context) {
        return RemoteDataSource.getInstance();
    }
    public static RemoteDataSource providechangepassrRepository(Context context) {
        return RemoteDataSource.getInstance();
    }

    public static DataSource providespecialistRepository(Context context) {
        return RemoteDataSource.getInstance();
    }

    public static DataSource provideyoutubeRepository(Context context) {
        return RemoteDataSource.getInstance();
    }
}
