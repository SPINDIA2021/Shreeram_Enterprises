package com.satmatgroup.shreeram.main_controller

import android.app.Application
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.android.gms.security.ProviderInstaller
import com.satmatgroup.shreeram.network.Preferences


class VolleySingleton : Application() {


    override fun onCreate() {
        super.onCreate()
         updateAndroidSecurityProvider()
        instance = this
        Preferences.init(this)
    }

    val requestQueue: RequestQueue? = null
        get() {
            if (field == null) {

                return Volley.newRequestQueue(applicationContext)

            }
            return field
        }


    fun <T> addToRequestQueue(request: Request<T>) {

        request.tag = TAG
        requestQueue?.add(request)
    }

    private fun updateAndroidSecurityProvider() {
        try {
            ProviderInstaller.installIfNeeded(this)
        } catch (e: Exception) {
            e.message
        }
    }

    companion object {

        private val TAG = VolleySingleton::class.java.simpleName
        @get:Synchronized
        var instance: VolleySingleton? = null
            private set
    }
}