package com.satmatgroup.shreeram.network;

import android.content.Context;
import android.content.SharedPreferences;


public class Preferences {
    public static final String AUTH_TOKEN = "Authorization";
    private static SharedPreferences sharedPref;
    private static final String PREF_NAME = "ScratchPreference";


    public static final String Name = "name";
    public static final String Mobile = "mobile";
    public static final String Inter = "Inter";
    public static final String Reward = "Reward";
    public static final String Banner = "Banner";
    public static final String DOCID = "docID";
    public static final String USER_NAME = "username";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String HeadId = "headid";
    public static final String ADDRESS = "address";
    public static final String PROOFWITHIMAGE = "proofwithimage";
    public static final String PROOFID = "proof";
    public static final String MOBILE = "mobile";
    public static final String FEEDBACK = "feedback";
    public static final String CONTRACT_FILE = "contract_file";
    public static final int HEADID = 0;
    public static final String CompName = "jnu";
    public static final String AuthPswd = "1269C48227AB2E13298623FEC51D88B0";
    public static final String FINGERDATA = "fingerdata";
    public static final String aadhar_no = "aadhar_no";
    public static final String nationalBankIdenticationNumber = "nationalBankIdenticationNumber";
    public static final String mobile_no = "mobile_no";
    public static final String transactionType = "transactionType";
    public static final String sendAmount = "sendAmount";
    public static final String latitude = "latitude";
    public static final String longitude = "longitude";
    public static final String CATEGORY_ID = "categoryId";
    public static final String LOCATION_ID = "locationId";
    public static final String CONS_DOC_ID = "ConsDocID";
    public static final String REG_NO = "regNo";
    public static final String VIDEOAPP_ID = "videoappId";
    public static final String VIDEOAPP_CERTIFICATE = "videoAppCertificate";
    public static final String TEMP_TOKEN = "temp_token";
    public static final String TEMP_CHANNEL = "temp_channel";

    public static void init(Context context) {
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void saveValue(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveValue(String key, long value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key, value);
        editor.apply();
    }


    public static void saveValue(String key, int value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void saveValue(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }



    public static void saveNoti(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getString(String key) {
        return sharedPref.getString(key, "");
    }

    public static int getInt(String key) {
        return sharedPref.getInt(key, 0);
    }

    public static int getDays(String key) {
        return sharedPref.getInt(key, 90);
    }

    public static long getLong(String key) {
        return sharedPref.getLong(key, 0L);
    }

    public static boolean getBoolean(String key) {
        return sharedPref.getBoolean(key, false);
    }

    public static boolean getNoti(String key) {
        return sharedPref.getBoolean(key, true);
    }

    public static void clearAll() {
        sharedPref.edit().clear().apply();
    }

    public static void clear(String key) {


    }


}
