package com.satmatgroup.shreeram.utils

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.text.TextUtils

class AppUtils {


    object LocationConstants {
        const val SUCCESS_RESULT = 0
        const val FAILURE_RESULT = 1
        const val PACKAGE_NAME = "com.satmatgroup.laundrysalon"
        const val RECEIVER = "$PACKAGE_NAME.RECEIVER"
        const val RESULT_DATA_KEY = "$PACKAGE_NAME.RESULT_DATA_KEY"
        const val LOCATION_DATA_EXTRA =
            "$PACKAGE_NAME.LOCATION_DATA_EXTRA"
        const val LOCATION_DATA_AREA =
            "$PACKAGE_NAME.LOCATION_DATA_AREA"
        const val LOCATION_DATA_CITY =
            "$PACKAGE_NAME.LOCATION_DATA_CITY"
        const val LOCATION_DATA_STREET =
            "$PACKAGE_NAME.LOCATION_DATA_STREET"


        fun isLocationEnabled(context: Context): Boolean {
            var locationMode = 0
            val locationProviders: String
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    locationMode = Settings.Secure.getInt(
                        context.contentResolver,
                        Settings.Secure.LOCATION_MODE
                    )
                } catch (e: SettingNotFoundException) {
                    e.printStackTrace()
                }
                locationMode != Settings.Secure.LOCATION_MODE_OFF
            } else {
                locationProviders = Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED
                )
                !TextUtils.isEmpty(locationProviders)
            }
        }
    }


    fun hasLollipop(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }


}