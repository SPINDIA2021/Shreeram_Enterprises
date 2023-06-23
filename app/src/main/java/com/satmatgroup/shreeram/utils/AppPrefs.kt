package com.satmatgroup.shreeram.utils

import android.content.Context
import androidx.preference.PreferenceManager


object AppPrefs : AppConstants {
    // Global Variables
    //    temp Global Varible to load data while API are not integrated
    //
    fun putStringPref(
        key: String?,
        value: String?,
        context: Context?
    ) {
        val prefs = PreferenceManager
            .getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun getStringPref(key: String?, context: Context?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(key, "")
    }

    fun putBooleanPref(
        key: String?,
        value: Boolean,
        context: Context?
    ) {
        val prefs = PreferenceManager
            .getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun getBooleanPref(key: String?, context: Context?): Boolean {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(context)
        return preferences.getBoolean(key, false)
    }

    fun putIntegerPref(
        key: String?,
        value: Int,
        context: Context?
    ) {
        val prefs = PreferenceManager
            .getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putInt(key, value)
        editor.commit()
    }

    fun getIntPref(key: String?, context: Context?): Int {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(context)
        return preferences.getInt(key, 0)
    }
}