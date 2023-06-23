package com.satmatgroup.shreeram.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.authentication.LoginActivity
import com.satmatgroup.shreeram.utils.AppConstants.Companion.MOBILE_REGEX
import com.satmatgroup.shreeram.utils.AppConstants.Companion.PANCARD_REGEX
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class AppCommonMethods : AppConstants {
    private val mIsLogOn = true
    private val TAG = "Commonmethods"
    private lateinit var mContext: Context

    constructor() {}
    constructor(context: Context?) {
        if (context != null) {
            mContext = context
        }
    }


    fun LOG(type: Int, tag: String?, message: String) {
        if (mIsLogOn) {
            when (type) {
                0 -> Log.d(tag, message)
                1 -> Log.e(tag, message)
                2 -> Log.i(tag, message)
                3 -> Log.v(tag, message)
            }
        }
    }

    /**
     * **public void showMessage**
     *
     * This function is used to show message dialog to user. It will b removed once user click on ok
     *
     * @param message - message to be displaed
     * @return instance on alertdialog
     */
    fun showMessage(message: String?): AlertDialog? {
        try {
            val alertDialogBuilder =
                AlertDialog.Builder(mContext)
            val alertDialog = alertDialogBuilder.create()
            alertDialogBuilder.setTitle(mContext.getString(R.string.app_name))
            alertDialogBuilder.setMessage(message)
            alertDialogBuilder.setCancelable(false)
            alertDialogBuilder.setNegativeButton(
                "ok"
            ) { dialog, id -> dialog.dismiss() }
            if (!(mContext as AppCompatActivity?)!!.isFinishing) {
                alertDialog.show()
            }
            return alertDialog
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getTimeAgo(currentFormat: String?, value: String?): String {
        try {
            val format = SimpleDateFormat(currentFormat)
            val past = format.parse(value)
            val now = Date()
            val seconds =
                TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
            val minutes =
                TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
            val hours =
                TimeUnit.MILLISECONDS.toHours(now.time - past.time)
            val days =
                TimeUnit.MILLISECONDS.toDays(now.time - past.time)
            //
//          System.out.println(TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime()) + " milliseconds ago");
//          System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
//          System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
//          System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");
            return if (seconds < 60) {
                "$seconds seconds ago"
            } else if (minutes < 60) {
                "$minutes minutes ago"
            } else if (hours < 24) {
                "$hours hours ago"
            } else {
                "$days days ago"
            }
        } catch (j: Exception) {
            j.printStackTrace()
        }
        return ""
    }


    /**
     * hide keyboard
     *
     * @param view
     */
    fun hideKeyBoard(view: View) {
        val imm =
            mContext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * Show keyboard explicitly
     *
     * @param view
     */
    fun showKeyBoard(view: View?) {
        val imm =
            mContext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun convertDateFormat(
        currentDateFormat: String?,
        expectedDateFormat: String?,
        currentDate: String?
    ): String? {
        try {
            val read = SimpleDateFormat(currentDateFormat)
            val write = SimpleDateFormat(expectedDateFormat)
            val str: String
            str = write.format(read.parse(currentDate))
            return str
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    fun convertTimeFormat(
        currentTimeFormat: String?,
        expectedTimeFormat: String?,
        time: String?
    ): String? {
        if (time == null) {
            return ""
        }
        var newTime: String? = null
        val actual = SimpleDateFormat(currentTimeFormat)
        val target = SimpleDateFormat(expectedTimeFormat)
        val date: Date
        try {
            date = actual.parse(time)
            newTime = target.format(date)
            return newTime
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    val isNetworkAvailable: Boolean
        get() {
            val cm =
                mContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            if (!(networkInfo != null && networkInfo.isConnected)) {
            }
            return networkInfo != null && networkInfo.isConnected
        }

    /**
     * This function used to get UNIX time stamp
     *
     * @return
     */
    val timeStamp: String
        get() = (System.currentTimeMillis() / 1000).toString()

    /**
     * This function is used to convert unix time stamp to expected date format
     *
     * @param timestamp      - unix timestamp to convert
     * @param expectedFormat - date format which we expects
     * @return -
     */
    fun convertTimestampToDate(
        timestamp: String,
        expectedFormat: String?
    ): String {
        val date =
            Date(timestamp.toLong() * 1000L) // *1000 is to convert seconds to milliseconds
        //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
        val sdf = SimpleDateFormat(expectedFormat)
        //        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
        val formattedDate = sdf.format(date)
        Log.e(TAG, "$timestamp UNIX timestamp converted = $formattedDate")
        return formattedDate
    }

    fun getAmount(pieces: Int, denomination: Int): Int {
        return pieces * denomination
    }

    companion object {
        private var mContext: Context? = null


        fun onSNACK(view: View, message: String) {
            //Snackbar(view)
            val snackbar = Snackbar.make(
                view, message,
                Snackbar.LENGTH_LONG
            )
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(Color.WHITE)
            snackbar.setTextColor(Color.BLACK)
            snackbar.show()
        }

        fun getDiffYears(first: String?, last: String?): Int {
            return try {
                val a = getCalendar(
                    stringToDate(last)
                )
                val b = getCalendar(
                    stringToDate(first)
                )
                var diff = b[Calendar.YEAR] - a[Calendar.YEAR]
                if (a[Calendar.MONTH] > b[Calendar.MONTH] ||
                    (a[Calendar.MONTH] == b[Calendar.MONTH]
                            && a[Calendar.DATE] > b[Calendar.DATE])
                ) {
                    diff--
                }
                diff
            } catch (e: Exception) {
                e.printStackTrace()
                0
            }
        }

        fun isFirstDateBefore(first: String?, last: String?): Boolean {
            return try {
                val a = getCalendar(
                    stringToDate(last)
                )
                val b = getCalendar(
                    stringToDate(first)
                )
                if (a.before(b)) {
                    true
                } else {
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

        fun getCalendar(date: Date?): Calendar {
            val cal = Calendar.getInstance(Locale.US)
            cal.time = date
            return cal
        }

        fun convertDateFormat(
            currentDateFormat: String?,
            expectedDateFormat: String?,
            currentDate: String?
        ): String? {
            try {
                val read = SimpleDateFormat(currentDateFormat)
                val write = SimpleDateFormat(expectedDateFormat)
                val str: String
                str = write.format(read.parse(currentDate))
                return str
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }


        fun stringToDate(strDate: String?): Date? {
            return try {
                SimpleDateFormat("dd/MM/yyyy").parse(strDate)
            } catch (e: ParseException) {
                e.printStackTrace()
                null
            }
        }

        fun convertTimeFormat1(
            currentTimeFormat: String?,
            expectedTimeFormat: String?,
            time: String?
        ): String? {
            if (time == null) {
                return ""
            }
            var newTime: String? = null
            val actual = SimpleDateFormat(currentTimeFormat)
            val target = SimpleDateFormat(expectedTimeFormat)
            val date: Date
            try {
                date = actual.parse(time)
                newTime = target.format(date)
                return newTime
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * This function is used to convert unix time stamp to expected date format
         *
         * @param expectedFormat - date format which we expects
         * @return -
         */
        fun getDate(expectedFormat: String?): String {
            val timestamp = System.currentTimeMillis()
            val date =
                Date(timestamp) // *1000 is to convert seconds to milliseconds
            //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
            val sdf = SimpleDateFormat(expectedFormat)
            //        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
            // Log.e(TAG, timestamp + " UNIX timestamp converted = " + formattedDate);
            return sdf.format(date)
        }// the format of your date
        //      SimpleDateFormat sdf = new SimpleDateFormat(expectedFormat);
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom

        // *1000 is to convert seconds to milliseconds
        val date: String
            get() {
                val timestamp = System.currentTimeMillis()
                val date =
                    Date(timestamp) // *1000 is to convert seconds to milliseconds
                val sdf =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss z") // the format of your date
                //      SimpleDateFormat sdf = new SimpleDateFormat(expectedFormat);
                //        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
                val formattedDate = sdf.format(date)
                Log.e(
                    "AppCommonMethods",
                    "$timestamp UNIX timestamp converted = $formattedDate"
                )
                return formattedDate
            }


        fun showSnackBar(paramView: View?, paramString: String?) {
            Snackbar.make(paramView!!, paramString!!, 1).show()
        }

        fun showToast(
            mContext: Context?,
            paramString: String?
        ) {
            val toast = Toast.makeText(mContext, paramString, Toast.LENGTH_SHORT)
            toast.show()

        }


        //CHECK FOR EMAIL
        fun checkForEmail(editText: EditText): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(editText.text).matches();
        }

        //CHECK FOR EMAIL
        fun checkForMobile(editText: EditText): Boolean {
            return Pattern.matches(MOBILE_REGEX, editText.text); }

        //CHECK FOR PAN CARD
        fun checkForPan(editText: EditText): Boolean {
            return Pattern.matches(PANCARD_REGEX, editText.text); }

        //GET DEVICE ID
        fun getDeviceId(context: Context): String {
            return Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }

        //GET DEVICE NAME
        fun getDeviceName(): String {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                model
            } else {
                "$manufacturer $model"
            }
        }

        //LOGOUT ON TOKEN EXPIRED
         fun logoutOnExpiredDialog(context: Context) {
            val builder1 =
                androidx.appcompat.app.AlertDialog.Builder(context)
            builder1.setTitle(context.getString(R.string.error_session_expired))
            builder1.setMessage(context.getString(R.string.error_please_login_again))
            builder1.setCancelable(false)
            builder1.setPositiveButton(
                context.getString(R.string.btn_okay)
            ) { dialog, _ ->

                AppPrefs.putStringPref(AppConstants.USER_MODEL, "", context)
                AppPrefs.putBooleanPref(AppConstants.IS_LOGIN, false, context)
                AppPrefs.putStringPref(AppConstants.TOKEN, "", context)
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
                dialog.cancel()

            }
            val alert11 = builder1.create()
            alert11.show()
        }


        fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
            val formatter = SimpleDateFormat(format, locale)
            return formatter.format(this)
        }

        fun getCurrentDateTime(): Date {
            return Calendar.getInstance().time
        }




    }



}