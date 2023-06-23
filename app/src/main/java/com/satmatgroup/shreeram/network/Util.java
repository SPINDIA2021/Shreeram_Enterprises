package com.satmatgroup.shreeram.network;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.satmatgroup.shreeram.R;


import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;


/**
 * Created by Admin on 2/9/2016.
 */
public class Util {


    public static String getDateFormatted(String dateStr) {
        String formattedDate = "";
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatDate = new SimpleDateFormat("MMM dd, yyyy");

        Date date = null;
        try {
            date = format1.parse(dateStr);
            formattedDate = formatDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }



    public static void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            isLoadingVisible = false;
        }
    }
    private static boolean isLoadingVisible;
    private static ProgressDialog mProgressDialog;
    public static void showProgress(Context mContext) {

        if (!isLoadingVisible) {
            isLoadingVisible = true;
            if (mContext == null) {
                return;
            }
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setTitle(mContext.getString(R.string.app_name));
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Drawable drawable = new ProgressBar(mContext).getIndeterminateDrawable().mutate();
                drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.app_color), PorterDuff.Mode.SRC_IN);
                mProgressDialog.setIndeterminateDrawable(drawable);
            }
            try {
                mProgressDialog.show();
            } catch (Exception e) {

            }
        }

    }



    public static void requestAllRuntimePermissions(Activity activity) {
        String[] permissions = getAllPermissions(activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null && permissions.length > 0) {
            activity.requestPermissions(permissions, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    public static String convertTo24Hour(String time) {
        try {
            SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");

            SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");

            return date24Format.format(date12Format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

    }
    private static String[] getAllPermissions(final Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            return pi.requestedPermissions;
        } catch (Exception e) {
        }
        return null;
    }

    public static String address = "";
    public static String description = "";
    public static String areaUnit = "";
    public static String latitude = "";
    public static String longitude = "";
    public static SharedPreferences prefs;
    // public static String userType = "";
    public static int SelectedPropertyType = -1;
    public static String ListOrNew = "";
    public static int priceUnitPosition;


    public static DecimalFormat decimalf = new DecimalFormat("0.##");

    public static String ToPriceShortString(double price) {

        if (price / 100 < 999) {
            String p = String.valueOf(price);
            p.replace(" ", "");
            p.replace(".00", "");
            p.replace(".0", "");
            return decimalf.format(new BigDecimal(p.replace(".00", "")));
        } else if (price / 100000 < 99) {
            double priceDiv = price / 100000;
            String prc = String.format("%.2f", priceDiv);
            String p = String.valueOf(prc);
            p.replace(" ", "");
            p.replace(".00", "");
            p.replace(".0", "");
            return decimalf.format(new BigDecimal(p.replace(".00", ""))) + "L";
        } else {
            double priceDiv = price / 10000000;
            String prc = String.format("%.2f", priceDiv);
            String p = String.valueOf(prc);
            p.replace(" ", "");
            p.replace(".00", "");
            p.replace(".0", "");
            return decimalf.format(new BigDecimal(p.replace(".00", ""))) + "Cr";
        }
    }

    public static String FurnishedStatus(String s) {
        String replacedString = "";
        if (s.equals("1")) {
            replacedString = "Furnished";
        } else if (s.equals("2")) {
            replacedString = "Semi Furnished";
        } else if (s.equals("3")) {
            replacedString = "Unfurnished";
        }
        return replacedString;
    }


    public static String Status(String s) {
        String replacedString = "";
        if (s.equals("1")) {
            replacedString = "Approved";
        } else if (s.equals("2")) {
            replacedString = "Rejected";
        } else if (s.equals("3")) {
            replacedString = "Requested";
        } else if (s.equals("0")) {
            replacedString = "None";
        }
        return replacedString;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void RequestReadWritePermission(Activity activity) {
        activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void RequestReadWriteAndCameraPermission(Activity activity) {
        activity.requestPermissions(new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_READ_WRITE);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestLocationEnabke(Activity activity) {
        activity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        }, REQUEST_CAMERA_READ_WRITE);
    }

    public static ArrayList<String> getImageBytesforServer_pod() {
        return ImageBytesforServer_pod;
    }

    public static void setImageBytesforServer_pod(ArrayList<String> imageBytesforServer_pod) {
        ImageBytesforServer_pod = imageBytesforServer_pod;
    }

    public static ArrayList<String> ImageBytesforServer_pod = new ArrayList<String>();


    public static String getGoogleApiKey() {
        return GOOGLE_API_KEY;
    }

    public static void setGoogleApiKey(String googleApiKey) {
        GOOGLE_API_KEY = googleApiKey;
    }

    public static int[] getColorfulColor() {
        return COLORFUL_COLOR;
    }

    public static int[] getColorfulColorAdmin() {
        return COLORFUL_COLOR_ADMIN;
    }

    public static String getEmailPattern() {
        return EMAIL_PATTERN;
    }

    public static void setEmailPattern(String emailPattern) {
        EMAIL_PATTERN = emailPattern;
    }

    public static boolean isDebug() {
        return IS_DEBUG;
    }

    public static String getBase64key() {
        return base64key;
    }

    public static void setBase64key(String base64key) {
        Util.base64key = base64key;
    }

    public static int getRequestCamera() {
        return REQUEST_CAMERA;
    }

    public static int getRequestCodeAskPermissions() {
        return REQUEST_CODE_ASK_PERMISSIONS;
    }

    public static int getRequestCameraReadWrite() {
        return REQUEST_CAMERA_READ_WRITE;
    }


    public static String GOOGLE_API_KEY = "AIzaSyCF0nytDTzDrJ6ICHaAVBf4Z3aP4fveSuo"; //browser key

    public static final int[] COLORFUL_COLOR = {
            Color.rgb(226, 173, 89), Color.rgb(60, 141, 188), Color.rgb(176, 224, 230),
            Color.rgb(72, 179, 155), Color.rgb(102, 102, 102)};

    public static final int[] COLORFUL_COLOR_ADMIN = {
            Color.rgb(255, 0, 0), Color.rgb(255, 160, 122), Color.rgb(226, 173, 89), Color.rgb(60, 141, 188), Color.rgb(176, 224, 230),
            Color.rgb(72, 179, 155), Color.rgb(102, 102, 102)};


    public static String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static String MOBILE_PATTERN="[0-9]{10}";
    private static final boolean IS_DEBUG = true;
    public static String base64key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlapY14SRxSBQ5UdvyuFgsk6amTNxMporxqzte2rtZ00Fcxx6Q6ijrqbXZodHZHTNAteNtM/TuYLM1B9kK29Jud7paIuMh6MRPE7vE1aETnzHqAzA7cRHW6Qg4V72yphlcypLuJ2/aPV+K4oTKkg7KEHf7ldjV7ocWzmV0s9mV1dw6DqZMdLThjTasGILVBfSb5EKbQbJ4PU1hn0wxJp8ZrhDI5Y91EvOwvr81iM0X12ECMVE57WOtuYdKeTssxP42c8VnoCxirHvlpQEiKGv4viwHBfCc7nsrIZEkM/ol5waWk+ZjoZovxRGS2tf0x7Jmn4QTr1Ao2iypBo7X9UJgwIDAQAB";
    public static void showToast(Context mContext, String msg) {

        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    public static void hideKeyBorad(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @SuppressLint("MissingPermission")
    public static boolean isOnline(Context context) {

        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected();
    }

    public static void showAlertBox(Context context, String msg, DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(context).setTitle(context.getResources().getString(R.string.app_name)).setMessage(msg).setPositiveButton("OK", okListener).show().setCancelable(false);
    }


    public static void showLog(String logMessage) {
        if (IS_DEBUG) {
            Log.d("GRABGEINE LOG :", logMessage);
        }
    }


    public static final int REQUEST_CAMERA = 123;
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 12345;
    public static final int REQUEST_CAMERA_READ_WRITE = 1234;



    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            return true;
        }

        return false;
    }

    public static boolean isMobileDataAvailable(Context context) {
        boolean mobileDataEnabled = false; // Assume disabled
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean) method.invoke(cm);
            return true;

        } catch (Exception e) {

        }
        return false;
    }

    @SuppressLint("DefaultLocale")
    public static String getIpWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        @SuppressLint("MissingPermission") WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();

        return String.format("%d.%d.%d.%d",
                (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));
    }

    public static String getIpMobileData(Context context) {
        String ipaddress = null;
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        ipaddress = inetAddress.getHostAddress().toString();
                        Log.e("ip address", "" + ipaddress);
                        return ipaddress;
                    }
                }
            }
        } catch (SocketException ex) {
            //Log.e("Socket exception in GetIP Address of Utilities", ex.toString());
        }
        return ipaddress;
    }


    public static boolean getListViewSize(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 300 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;

        } else {
            return false;
        }
    }


    public static void checkEmpty(EditText editText, String message)
    {
        editText.requestFocus();
        editText.setError(message);
    }

    public static void matchPassword(EditText editText, String message)
    {
        editText.requestFocus();
        editText.setError(message);
    }

    public static void checkMobileValidation(EditText editText, String message)
    {
        editText.requestFocus();
        editText.setError(message);
    }


    public static void checkEmailValidation(EditText editText, String message)
    {
        editText.requestFocus();
        editText.setError(message);
    }


    public static void checkPasswordLenth(EditText editText, String message)
    {
        editText.requestFocus();
        editText.setError(message);
    }

    public static void checkEmptySnack(View v, String message) {

        Snackbar snackbar1 = Snackbar.make(v, message, Snackbar.LENGTH_SHORT);
        View view = snackbar1.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        params.topMargin=60;
        view.setLayoutParams(params);
        snackbar1.show();

    }





}
