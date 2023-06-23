package com.satmatgroup.shreeram.network_calls

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.main_controller.VolleySingleton
import com.satmatgroup.shreeram.network_calls.AppApiUrl.AEPSCOMMISSION_HISTORY
import com.satmatgroup.shreeram.network_calls.AppApiUrl.AEPSPAYOUT_HISTORY
import com.satmatgroup.shreeram.network_calls.AppApiUrl.AEPS_BANK_LIST
import com.satmatgroup.shreeram.network_calls.AppApiUrl.AEPS_COMMISIONSLAB_URL
import com.satmatgroup.shreeram.network_calls.AppApiUrl.AEPS_HISTORY
import com.satmatgroup.shreeram.network_calls.AppApiUrl.AEPS_PAYOUT
import com.satmatgroup.shreeram.network_calls.AppApiUrl.AEPS_TRANSACTION
import com.satmatgroup.shreeram.network_calls.AppApiUrl.BROWSE_PLANS
import com.satmatgroup.shreeram.network_calls.AppApiUrl.BROWSE_PLANS_DTH
import com.satmatgroup.shreeram.network_calls.AppApiUrl.BUY_PRODUCT
import com.satmatgroup.shreeram.network_calls.AppApiUrl.BUY_PRODUCT_AND_SERVICE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.BUY_SERVICE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.CASH_DEPOSIT_WITH_OTP
import com.satmatgroup.shreeram.network_calls.AppApiUrl.CHANGE_PASWORD
import com.satmatgroup.shreeram.network_calls.AppApiUrl.CHANGE_PIN
import com.satmatgroup.shreeram.network_calls.AppApiUrl.CHECKSAME_FUNDTRANSFER
import com.satmatgroup.shreeram.network_calls.AppApiUrl.CHECK_IF_SAME_RECHARGE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.CHECK_KYC_STATUS
import com.satmatgroup.shreeram.network_calls.AppApiUrl.CHECK_STATUS
import com.satmatgroup.shreeram.network_calls.AppApiUrl.CIRCLE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.COMMISION_REPORT_URL
import com.satmatgroup.shreeram.network_calls.AppApiUrl.DELETE_RECIPIENT
import com.satmatgroup.shreeram.network_calls.AppApiUrl.DISSPUTE_HISTORY
import com.satmatgroup.shreeram.network_calls.AppApiUrl.DMT_ADD_BENFICIARY
import com.satmatgroup.shreeram.network_calls.AppApiUrl.DMT_BANK_LIST
import com.satmatgroup.shreeram.network_calls.AppApiUrl.DMT_COMMISIONSLAB_URL
import com.satmatgroup.shreeram.network_calls.AppApiUrl.DMT_HISTORY
import com.satmatgroup.shreeram.network_calls.AppApiUrl.DMT_LOGIN
import com.satmatgroup.shreeram.network_calls.AppApiUrl.DMT_REGISTER
import com.satmatgroup.shreeram.network_calls.AppApiUrl.DMT_SENDOTP
import com.satmatgroup.shreeram.network_calls.AppApiUrl.DMT_TRANSACTION
import com.satmatgroup.shreeram.network_calls.AppApiUrl.DMT_VIEW_BENIFICIARY
import com.satmatgroup.shreeram.network_calls.AppApiUrl.DUMMY_PID
import com.satmatgroup.shreeram.network_calls.AppApiUrl.EKYC
import com.satmatgroup.shreeram.network_calls.AppApiUrl.FORGETPIN
import com.satmatgroup.shreeram.network_calls.AppApiUrl.FORGOT_PASS
import com.satmatgroup.shreeram.network_calls.AppApiUrl.FUND_CREDIT
import com.satmatgroup.shreeram.network_calls.AppApiUrl.FUND_DEBIT
import com.satmatgroup.shreeram.network_calls.AppApiUrl.FUND_MYREQUEST
import com.satmatgroup.shreeram.network_calls.AppApiUrl.FUND_REQUEST_URL
import com.satmatgroup.shreeram.network_calls.AppApiUrl.FUND_TRANSFER
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GETBILLDETAILS
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GETFORGOTOTP
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GETPINOTP
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_AEPS_BALANCE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_AEPS_CHARGE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_BALANCE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_CHARGE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_DASHBOARD
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_DMT_SERVICE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_ELECTIRCITY_OPERATORS
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_OPERATORS
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_PAYOUT_DETAILS
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_PRODUCTS
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_PRODUCT_AND_SERVICE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_PROFILE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_SERVICE_AMOUNT
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_SERVICE_STATUS
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_SINGLE_PRODUCT_AND_SERVICE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_SUPPORT
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_UPIDETAILS
import com.satmatgroup.shreeram.network_calls.AppApiUrl.GET_USER_ID
import com.satmatgroup.shreeram.network_calls.AppApiUrl.KYC_ONBOARDING
import com.satmatgroup.shreeram.network_calls.AppApiUrl.LEDGER_REPORT
import com.satmatgroup.shreeram.network_calls.AppApiUrl.LOGIN_BY_PASSWORD
import com.satmatgroup.shreeram.network_calls.AppApiUrl.LOGOUT_USER
import com.satmatgroup.shreeram.network_calls.AppApiUrl.MICRO_ATM_LOGIN
import com.satmatgroup.shreeram.network_calls.AppApiUrl.MICRO_ATM_TRANSACTION
import com.satmatgroup.shreeram.network_calls.AppApiUrl.NEWUSER_URL
import com.satmatgroup.shreeram.network_calls.AppApiUrl.NEW_DISTRIBUTOR_URL
import com.satmatgroup.shreeram.network_calls.AppApiUrl.NOTE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.OFFER_POPUP
import com.satmatgroup.shreeram.network_calls.AppApiUrl.PRODUCT_SERVICES_HISTORY
import com.satmatgroup.shreeram.network_calls.AppApiUrl.RAISE_DISPUTE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.RECHARGE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.RECHARGE_HISTORY
import com.satmatgroup.shreeram.network_calls.AppApiUrl.RECHARGE_HISTORY_BY_DATE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.RECHARGE_HISTORY_BY_MOBILE
import com.satmatgroup.shreeram.network_calls.AppApiUrl.REGISTER
import com.satmatgroup.shreeram.network_calls.AppApiUrl.RESEND_EKYC_OTP
import com.satmatgroup.shreeram.network_calls.AppApiUrl.STATE_LIST
import com.satmatgroup.shreeram.network_calls.AppApiUrl.UPDATE_WALLET
import com.satmatgroup.shreeram.network_calls.AppApiUrl.USER_DAYBOOK
import com.satmatgroup.shreeram.network_calls.AppApiUrl.USER_LIST
import com.satmatgroup.shreeram.network_calls.AppApiUrl.USER_PAYOUT_BANK
import com.satmatgroup.shreeram.network_calls.AppApiUrl.USER_SEARCH
import com.satmatgroup.shreeram.network_calls.AppApiUrl.VALIDATE_CASH_DEPOSIT_OTP
import com.satmatgroup.shreeram.network_calls.AppApiUrl.VALIDATE_EKYC_OTP
import com.satmatgroup.shreeram.network_calls.AppApiUrl.VERIFY_BANK
import com.satmatgroup.shreeram.network_calls.AppApiUrl.VERIFY_PIN
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppConstants.Companion.AADHAR_NUM
import com.satmatgroup.shreeram.utils.AppConstants.Companion.ADDRESS
import com.satmatgroup.shreeram.utils.AppConstants.Companion.AMOUNT
import com.satmatgroup.shreeram.utils.AppConstants.Companion.API_KEY
import com.satmatgroup.shreeram.utils.AppConstants.Companion.BASIC
import com.satmatgroup.shreeram.utils.AppConstants.Companion.BASIC_TOKEN
import com.satmatgroup.shreeram.utils.AppConstants.Companion.CUS_ID
import com.satmatgroup.shreeram.utils.AppConstants.Companion.CUS_MOBILE_API
import com.satmatgroup.shreeram.utils.AppConstants.Companion.CUS_TYPE
import com.satmatgroup.shreeram.utils.AppConstants.Companion.DATE
import com.satmatgroup.shreeram.utils.AppConstants.Companion.DEVICE_ID
import com.satmatgroup.shreeram.utils.AppConstants.Companion.DEVICE_NAME
import com.satmatgroup.shreeram.utils.AppConstants.Companion.EMAIL
import com.satmatgroup.shreeram.utils.AppConstants.Companion.FORM_URL_ENCODED
import com.satmatgroup.shreeram.utils.AppConstants.Companion.FROM_DATE
import com.satmatgroup.shreeram.utils.AppConstants.Companion.MOBILE
import com.satmatgroup.shreeram.utils.AppConstants.Companion.MOBILE_RECHARGE
import com.satmatgroup.shreeram.utils.AppConstants.Companion.OPERATOR
import com.satmatgroup.shreeram.utils.AppConstants.Companion.OPERATOR_TYPE
import com.satmatgroup.shreeram.utils.AppConstants.Companion.PASSWORD
import com.satmatgroup.shreeram.utils.AppConstants.Companion.PIN
import com.satmatgroup.shreeram.utils.AppConstants.Companion.REC_MOBILE
import com.satmatgroup.shreeram.utils.AppConstants.Companion.TOKEN
import com.satmatgroup.shreeram.utils.AppConstants.Companion.TO_DATE
import com.satmatgroup.shreeram.utils.AppConstants.Companion.USER_NAME
import com.satmatgroup.shreeram.utils.AppPrefs
import com.satmatgroup.shreeram.utils.AppPrefs.putStringPref
import org.json.JSONException
import java.util.*


class AppApiCalls(
    private val mContext: Context,
    flag: String,
    listener: OnAPICallCompleteListener
) :
    AppConstants {
    private val TAG = "Demo_APICalls"
    private val mApiCallCompleteListener: OnAPICallCompleteListener
    private var mFlag = ""
    var mRetryPolicy: RetryPolicy = DefaultRetryPolicy(
        0,
        -1,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
    )

    //Api functions
    //Login
    fun login(mobile: String, password: String, deviceId: String, deviceName: String, otp: String) {

        val url: String = LOGIN_BY_PASSWORD
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d(mContext.getString(R.string.response), response)

                    }
                    // response
                    Log.d(mContext.getString(R.string.response), response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e(mContext.getString(R.string.error_api), error.toString())
                    }
                    Toast.makeText(
                        mContext,
                        mContext.getString(R.string.error_server_error),
                        Toast.LENGTH_SHORT
                    ).show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[MOBILE] = mobile
                    jsonObject[PASSWORD] = password
                    jsonObject[DEVICE_ID] = deviceId
                    jsonObject[DEVICE_NAME] = deviceName
                    jsonObject["otp"] = otp
                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    //Register
    fun register(
        username: String,
        mobile: String,
        email: String,
        aadhaar_number: String,
        address: String,
        deviceId: String,
        deviceName: String,
        pan_no: String
    ) {

        val url: String = REGISTER
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d(mContext.getString(R.string.response), response)

                    }
                    // response
                    Log.d(mContext.getString(R.string.response), response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e(mContext.getString(R.string.error_api), error.toString())
                    }
                    Toast.makeText(
                        mContext,
                        mContext.getString(R.string.error_server_error),
                        Toast.LENGTH_SHORT
                    ).show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[USER_NAME] = username
                    jsonObject[MOBILE] = mobile
                    jsonObject[EMAIL] = email
                    jsonObject[AADHAR_NUM] = aadhaar_number
                    jsonObject[ADDRESS] = address
                    jsonObject[DEVICE_ID] = deviceId
                    jsonObject[DEVICE_NAME] = deviceName
                    jsonObject["pan_no"] = pan_no

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    //Dashboard
    fun dashboard(
        cus_id: String
    ) {

        val url: String = GET_DASHBOARD
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d(mContext.getString(R.string.response), response)

                    }
                    // response
                    Log.d(mContext.getString(R.string.response), response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e(mContext.getString(R.string.error_api), error.toString())
                    }
                    Toast.makeText(
                        mContext,
                        mContext.getString(R.string.error_server_error),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[CUS_MOBILE_API] = cus_id
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    //Get Balance
    fun getBalance(
        cus_id: String
    ) {

        val url: String = GET_BALANCE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d(mContext.getString(R.string.response), response)

                    }
                    // response
                    Log.d(mContext.getString(R.string.response), response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e(mContext.getString(R.string.error_api), error.toString())
                    }
                    Toast.makeText(
                        mContext,
                        mContext.getString(R.string.error_server_error),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[CUS_MOBILE_API] = cus_id
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    //Get AEPS Balance
    fun getAepsBalance(
        cus_id: String
    ) {

        val url: String = GET_AEPS_BALANCE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d(mContext.getString(R.string.response), response)

                    }
                    // response
                    Log.d(mContext.getString(R.string.response), response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e(mContext.getString(R.string.error_api), error.toString())
                    }
                    Toast.makeText(
                        mContext,
                        mContext.getString(R.string.error_server_error),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[CUS_ID] = cus_id
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    //Get Profile
    fun getProfile(
        cus_id: String
    ) {

        val url: String = GET_PROFILE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d(mContext.getString(R.string.response), response)

                    }
                    // response
                    Log.d(mContext.getString(R.string.response), response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e(mContext.getString(R.string.error_api), error.toString())
                    }
                    Toast.makeText(
                        mContext,
                        mContext.getString(R.string.error_server_error),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[CUS_MOBILE_API] = cus_id
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    //Get Operators
    fun getOperators(
        operator_type: String
    ) {

        val url: String = GET_OPERATORS
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d(mContext.getString(R.string.response), response)

                    }
                    // response
                    Log.d(mContext.getString(R.string.response), response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e(mContext.getString(R.string.error_api), error.toString())
                    }
                    Toast.makeText(
                        mContext,
                        mContext.getString(R.string.error_server_error),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[OPERATOR_TYPE] = operator_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    //Get Electricity Operators
    fun getElectricityOperators(
        operator_type: String
    ) {

        val url: String = GET_ELECTIRCITY_OPERATORS
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d(mContext.getString(R.string.response), response)

                    }
                    // response
                    Log.d(mContext.getString(R.string.response), response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e(mContext.getString(R.string.error_api), error.toString())
                    }
                    Toast.makeText(
                        mContext,
                        mContext.getString(R.string.error_server_error),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[OPERATOR_TYPE] = operator_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    //Check if Same recharge
    fun checkIfSameRecharge(
        cus_id: String,
        rec_mobile: String,
        amount: String,
        operator: String
    ) {

        val url: String = CHECK_IF_SAME_RECHARGE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d(mContext.getString(R.string.response), response)

                    }
                    // response
                    Log.d(mContext.getString(R.string.response), response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e(mContext.getString(R.string.error_api), error.toString())
                    }
                    Toast.makeText(
                        mContext,
                        mContext.getString(R.string.error_server_error),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[CUS_MOBILE_API] = cus_id
                    jsonObject[OPERATOR] = operator
                    jsonObject[REC_MOBILE] = rec_mobile
                    jsonObject[AMOUNT] = amount
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    //Verify Pin
    fun verifyPin(
        cus_mobile: String,
        pin: String
    ) {

        val url: String = VERIFY_PIN
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d(mContext.getString(R.string.response), response)

                    }
                    // response
                    Log.d(mContext.getString(R.string.response), response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e(mContext.getString(R.string.error_api), error.toString())
                    }
                    Toast.makeText(
                        mContext,
                        mContext.getString(R.string.error_server_error),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[CUS_MOBILE_API] = cus_mobile
                    jsonObject[PIN] = pin
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    //Recharge Api
    fun rechargeApi(
        cus_id: String,
        rec_mobile: String,
        amount: String,
        operator: String,
        cus_type: String,
    ) {

        val url: String = RECHARGE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d(mContext.getString(R.string.response), response)

                    }
                    // response
                    Log.d(mContext.getString(R.string.response), response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e(mContext.getString(R.string.error_api), error.toString())
                    }
                    Toast.makeText(
                        mContext,
                        mContext.getString(R.string.error_server_error),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[CUS_ID] = cus_id
                    jsonObject[MOBILE_RECHARGE] = rec_mobile
                    jsonObject[AMOUNT] = amount
                    jsonObject[OPERATOR] = operator
                    jsonObject[CUS_TYPE] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun rechargeApiElectricity(
        cus_id: String,
        rec_mobile: String,
        amount: String,
        operator: String,
        cus_type: String,
        bill_unit: String,
        circle_code: String
    ) {

        val url: String = RECHARGE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d(mContext.getString(R.string.response), response)

                    }
                    // response
                    Log.d(mContext.getString(R.string.response), response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e(mContext.getString(R.string.error_api), error.toString())
                    }
                    Toast.makeText(
                        mContext,
                        mContext.getString(R.string.error_server_error),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[CUS_ID] = cus_id
                    jsonObject[MOBILE_RECHARGE] = rec_mobile
                    jsonObject[AMOUNT] = amount
                    jsonObject[OPERATOR] = operator
                    jsonObject[CUS_TYPE] = cus_type
                    jsonObject["bill_unit"] = bill_unit
                    jsonObject["circle_code"] = circle_code
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    //Recharge History
    fun rechargeHistoryFromTo(
        cus_mobile: String,
        fromDate: String,
        toDate: String,
    ) {

        val url: String = RECHARGE_HISTORY
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d(mContext.getString(R.string.response), response)

                    }
                    // response
                    Log.d(mContext.getString(R.string.response), response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e(mContext.getString(R.string.error_api), error.toString())
                    }
                    Toast.makeText(
                        mContext,
                        mContext.getString(R.string.error_server_error),
                        Toast.LENGTH_SHORT
                    ).show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[CUS_MOBILE_API] = cus_mobile
                    jsonObject[FROM_DATE] = fromDate
                    jsonObject[TO_DATE] = toDate
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()


                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    //Recharge History by date
    fun rechargeHistoryByDate(
        cus_mobile: String,
        date: String,
    ) {

        val url: String = RECHARGE_HISTORY
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d(mContext.getString(R.string.response), response)

                    }
                    // response
                    Log.d(mContext.getString(R.string.response), response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e(mContext.getString(R.string.error_api), error.toString())
                    }
                    Toast.makeText(
                        mContext,
                        mContext.getString(R.string.error_server_error),
                        Toast.LENGTH_SHORT
                    ).show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[CUS_MOBILE_API] = cus_mobile
                    jsonObject[DATE] = date
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()


                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    //****************************Do not copy from here***************************//


    fun contactUsApi(
        cus_id: String,
        deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = GET_SUPPORT
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()



                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun changePassword(
        cus_id: String,
        current_password: String,
        new_password: String,
        deviceId: String,
        deviceName: String,
        pin: String,
        pass: String,
        cus_mobile: String,
        cus_type: String
    ) {

        val url: String = CHANGE_PASWORD
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["password"] = current_password
                    jsonObject["newpassword"] = new_password
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun changePin(
        cus_id: String,
        current_pin: String,
        new_pin: String,
        deviceId: String,
        deviceName: String,
        pin: String,
        pass: String,
        cus_mobile: String,
        cus_type: String
    ) {

        val url: String = CHANGE_PIN
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["id"] = cus_id
                    jsonObject["curr_pin"] = current_pin
                    jsonObject["new_pin"] = new_pin
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun forgotPassword(mobile: String, deviceId: String, deviceName: String) {
        Log.e("MOBILE", mobile)

        val url: String = FORGOT_PASS
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["mobile"] = mobile
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun note(

    ) {

        val url: String = NOTE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()

                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun getUserId(
        cus_id: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = GET_USER_ID
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)
                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["mobile"] = cus_id
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun getUserList(
        dis_cus_id: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {
        Log.e("DISCUS", dis_cus_id)
        val url: String = USER_LIST
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["dis_cus_id"] = dis_cus_id
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun createUserApi(
        newMob: String, dis_id: String, newName: String,
        newPass: String, newEmail: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = NEWUSER_URL
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["mobile"] = newMob
                    jsonObject["dis_cus_id"] = dis_id
                    jsonObject["name"] = newName
                    jsonObject["password"] = newPass
                    jsonObject["email"] = newEmail
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun createDistributorApi(
        newMob: String, dis_id: String, newName: String,
        newPass: String, newEmail: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = NEW_DISTRIBUTOR_URL
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["mobile"] = newMob
                    jsonObject["mst_cus_id"] = dis_id
                    jsonObject["name"] = newName
                    jsonObject["password"] = newPass
                    jsonObject["email"] = newEmail
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun dthInfo(
        mobile: String, operator: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = BROWSE_PLANS_DTH
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["mobile"] = mobile
                    jsonObject["operator"] = operator
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun mobileOffers(
        mobile: String, operator: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String, circle_code: String
    ) {

        val url: String = BROWSE_PLANS
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["mobile"] = mobile
                    jsonObject["operator"] = operator
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject["circle_code"] = circle_code
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()

                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun getBillDetails(
        customerId: String, operator: String
    ) {

        val url: String = GETBILLDETAILS
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["customerId"] = customerId
                    jsonObject["operator"] = operator

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun commisionSlab(
        cus_id: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = COMMISION_REPORT_URL
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers = HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun disputeHistory(
        cus_id: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = DISSPUTE_HISTORY
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun searcUser(
        dis_cus_id: String, mobileorname: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = USER_SEARCH
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["dis_cus_id"] = dis_cus_id
                    jsonObject["mobileorname"] = mobileorname
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun fundRecieveHistory(
        cus_id: String,
        fromdate: String,
        todate: String,
        deviceId: String,
        deviceName: String,
        pin: String,
        pass: String,
        cus_mobile: String,
        cus_type: String
    ) {

        val url: String = FUND_CREDIT
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["fromdate"] = fromdate
                    jsonObject["todate"] = todate
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()

                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun fundTransferHistory(
        cus_id: String,
        fromdate: String,
        todate: String,
        deviceId: String,
        deviceName: String,
        pin: String,
        pass: String,
        cus_mobile: String,
        cus_type: String
    ) {

        val url: String = FUND_DEBIT
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["fromdate"] = fromdate
                    jsonObject["todate"] = todate
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"


                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun ledgerReportApi(
        cus_id: String,
        fromdate: String,
        todate: String,
        deviceId: String,
        deviceName: String,
        pin: String,
        pass: String,
        cus_mobile: String,
        cus_type: String
    ) {

        val url: String = LEDGER_REPORT
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["fromdate"] = fromdate
                    jsonObject["todate"] = todate
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun userDayBook(
        cus_id: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = USER_DAYBOOK
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    fun checkIfSameFundTransfer(
        cus_id: String,
        to_id: String,
        amount: String,
        deviceId: String,
        deviceName: String,
        pin: String,
        pass: String,
        cus_mobile: String,
        cus_type: String
    ) {

        val url: String = CHECKSAME_FUNDTRANSFER
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()

                    jsonObject["cus_id"] = cus_id
                    jsonObject["to_id"] = to_id
                    jsonObject["amount"] = amount
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()


                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun getpinotp(
        mobile: String, otp: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        Log.e("mobile", mobile)
        val url: String = GETPINOTP
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["mobile"] = mobile
                    jsonObject["otp"] = otp
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun getForgetPassOtp(
        mobile: String, otp: String
    ) {

        Log.e("mobile", mobile)
        val url: String = GETFORGOTOTP
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["mobile"] = mobile
                    jsonObject["otp"] = otp
                    // jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun forgetpin(
        cus_id: String,
        deviceId: String,
        deviceName: String, cus_type: String
    ) {

        val url: String = FORGETPIN
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["cus_type"] = cus_type
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun fundRequestHistory(
        cus_id: String,
        fromdate: String,
        todate: String,
        deviceId: String,
        deviceName: String,
        pin: String,
        pass: String,
        cus_mobile: String,
        cus_type: String
    ) {

        val url: String = FUND_MYREQUEST
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["fromdate"] = fromdate
                    jsonObject["todate"] = todate
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun fundRequestApi(
        cus_id: String,
        req_to: String,
        amount: String,
        bank: String,
        refrenceNumber: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = FUND_REQUEST_URL
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["req_to"] = req_to
                    jsonObject["amount"] = amount
                    jsonObject["bank"] = bank
                    jsonObject["ref_no"] = refrenceNumber
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun fundTransferApi(
        dis_id: String,
        cus_id: String,
        amount_string: String,
        deviceId: String,
        deviceName: String,
        pin: String,
        pass: String,
        cus_mobile: String,
        cus_type: String
    ) {

        val url: String = FUND_TRANSFER
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["dis_id"] = dis_id
                    jsonObject["c_id"] = cus_id
                    jsonObject["amount"] = amount_string
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    fun raiseDisputeApi(
        cus_id: String,
        recid: String,
        issue: String,
        subject: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = RAISE_DISPUTE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["recid"] = recid
                    jsonObject["issue"] = issue
                    jsonObject["subject"] = subject
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()


                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    fun getUpi(
        cus_id: String,
        deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = GET_UPIDETAILS
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> = HashMap()

                    jsonObject["cus_id"] = cus_id
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun addFundsApi(
        cus_id: String,
        amount: String,
        bank: String,
        transactionId: String,
        txnRef: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = UPDATE_WALLET
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> = HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["amount"] = amount
                    jsonObject["bank"] = bank
                    jsonObject["transaction_id"] = transactionId
                    jsonObject["transaction_ref"] = txnRef
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun userLogout(
        cus_id: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = LOGOUT_USER
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun rechargeHistory(
        cus_id: String,
        fromdate: String,
        todate: String,
        deviceId: String,
        deviceName: String,
        pin: String,
        pass: String,
        cus_mobile: String,
        cus_type: String
    ) {

        val url: String = RECHARGE_HISTORY
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["fromdate"] = fromdate
                    jsonObject["todate"] = todate
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()


                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    fun rechargeHistoryByMobile(
        cus_id: String, mobile: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = RECHARGE_HISTORY_BY_MOBILE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["mobile"] = mobile
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()

                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun rechargeHistoryByDate(
        cus_id: String, date: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = RECHARGE_HISTORY_BY_DATE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["date"] = date
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()

                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    //DMT APIS
    fun loginDmt(mobile: String) {
        Log.e("MOBILE", mobile)

        val url: String = DMT_LOGIN
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["mobile"] = mobile
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun getDmtService(
    ) {

        val url: String = GET_DMT_SERVICE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun addBeneficiaryAccount(
        dmtMobile: String,
        cusNumber: String,
        bank_acct: String,
        ifsc: String,
        name: String,
        bankname: String
    ) {
        Log.e("MOBILE", dmtMobile)

        val url: String = DMT_ADD_BENFICIARY
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["mob_no"] = dmtMobile
                    jsonObject["cust_mob"] = cusNumber
                    jsonObject["bank_acct"] = bank_acct
                    jsonObject["ifsc_code"] = ifsc
                    jsonObject["name"] = name
                    jsonObject["bankid"] = bankname
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun verifBenefAccount(
        adhar_number: String,
        pan_number: String,
        mobile_number: String,
        account_number: String,
        bank_code: String,
    ) {
        Log.e("MOBILE", adhar_number)

        val url: String = VERIFY_BANK
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["adhar_number"] = adhar_number
                    jsonObject["pan_number"] = pan_number
                    jsonObject["mobile_number"] = mobile_number
                    jsonObject["account_number"] = account_number
                    jsonObject["bank_code"] = bank_code
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    fun viewBenificiary(
        dmt_user_id: String
    ) {
        Log.e("MOBILE", dmt_user_id)

        val url: String = DMT_VIEW_BENIFICIARY
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["mobile"] = dmt_user_id
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    fun dummyPid(
        PidData: String, pidOptions : String
    ) {
        Log.e("MOBILE", PidData)

        val url: String = DUMMY_PID
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["PidData"] = PidData
                    jsonObject["PidOption"] = pidOptions
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun bankListDmt(
    ) {

        val url: String = DMT_BANK_LIST
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun registerDmt(
        mobile: String,
        fname: String,
        lname: String,
        dob: String,
        pincode: String,
        address: String,
        pan: String,
        aadhar: String
    ) {
        Log.e("MOBILE", mobile)

        val url: String = DMT_REGISTER
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["mobile"] = mobile
                    jsonObject["fname"] = fname
                    jsonObject["lname"] = lname
                    jsonObject["dob"] = dob
                    jsonObject["pincode"] = pincode
                    jsonObject["address"] = address
                    jsonObject["pan"] = pan
                    jsonObject["aadhar"] = aadhar
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun verifySenderOtp(
        mobile: String,
        otp: String
        ) {
        Log.e("MOBILE", mobile)

        val url: String = DMT_SENDOTP
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["mobile"] = mobile
                    jsonObject["otp"] = otp
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun dmtHistory(cus_id: String) {

        val url: String = DMT_HISTORY
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()
                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    fun deleteRecipient(bene_id: String) {

        val url: String = DELETE_RECIPIENT
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(
                            true,
                            mFlag,
                            response
                        )
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["beneficaryid"] = bene_id
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()
                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun checkStatus(unique_id: String) {

        val url: String = CHECK_STATUS
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(
                            true,
                            mFlag,
                            response
                        )
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["unique_id"] = unique_id
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()
                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }




    fun dmtTransactionApi(
        CustomerMobile: String,
        beneficiaryAccount: String,
        beneficiaryIFSC: String,
        amount: String,
        cus_id: String,
        benename: String,
        bankname: String,
        beneficiaryid: String,
        benemobile: String,
        dmtapiname: String
    ) {

        val url: String = DMT_TRANSACTION
        Log.e("Beneficiary Id",beneficiaryid)
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(
                            true,
                            mFlag,
                            response
                        )
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @kotlin.jvm.Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["CustomerMobile"] = CustomerMobile
                    jsonObject["beneficiaryAccount"] = beneficiaryAccount
                    jsonObject["beneficiaryIFSC"] = beneficiaryIFSC
                    jsonObject["amount"] = amount
                    jsonObject["cus_id"] = cus_id
                    jsonObject["benename"] = benename
                    jsonObject["bankname"] = bankname
                    jsonObject["beneficiaryid"] = beneficiaryid
                    jsonObject["benemobile"] = benemobile
                    jsonObject["dmtapiname"] = dmtapiname

                    return jsonObject
                }

                @kotlin.jvm.Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers["Content-Type"] = "application/x-www-form-urlencoded"
                    headers["X-API-KEY"] = "r9T4lmWq8x"
                    headers[mContext.getString(R.string.authorization)] = "Basic YWRtaW46MTIzNA=="
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun getCharge(amount: String) {

        val url: String = GET_CHARGE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(
                            true,
                            mFlag,
                            response
                        )
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["amount"] = amount
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()
                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun dmtcommisionSlab(
        cus_id: String
    ) {

        val url: String = DMT_COMMISIONSLAB_URL
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    //AEPS
    fun getAepsCharge(cus_id: String,amount: String) {

        val url: String = GET_AEPS_CHARGE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(
                            true,
                            mFlag,
                            response
                        )
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["amount"] = amount
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()
                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun aepsCommissionHistory(
        cus_id: String, date: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = AEPSCOMMISSION_HISTORY
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["date"] = date
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun aepsHistory(
        cus_id: String, date: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {

        val url: String = AEPS_HISTORY
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["date"] = date
                    jsonObject["deviceId"] = deviceId
                    jsonObject["deviceName"] = deviceName
                    jsonObject["pin"] = pin
                    jsonObject["pass"] = pass
                    jsonObject["cus_mobile"] = cus_mobile
                    jsonObject["cus_type"] = cus_type
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"

                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun aepscommisionSlab(
        cus_id: String
    ) {

        val url: String = AEPS_COMMISIONSLAB_URL
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun aepsPayoutHistory(cus_id: String) {

        val url: String = AEPSPAYOUT_HISTORY
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()
                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun aepsPayout(
        cus_id: String, bank_name: String, account_number: String,
        ifsc_code: String, account_holder_name: String, amount: String,
        charge: String, type: String, payout_bank_id: String
    ) {

        val url: String = AEPS_PAYOUT
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["bank_name"] = bank_name
                    jsonObject["account_number"] = account_number
                    jsonObject["ifsc_code"] = ifsc_code
                    jsonObject["account_holder_name"] = account_holder_name
                    jsonObject["amount"] = amount
                    jsonObject["charge"] = charge
                    jsonObject["type"] = type
                    jsonObject["type"] = type
                    jsonObject["payout_bank_id"] = payout_bank_id
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()
                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }

        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun aepsPayountAccountDetails(
        cus_id: String
    ) {

        val url: String = GET_PAYOUT_DETAILS
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()

                    jsonObject["cus_id"] = cus_id
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun userPayoutBank(
        cus_id: String
    ) {

        val url: String = USER_PAYOUT_BANK
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }


    fun aepsTransaction(
        cus_id: String,
        txtPidData: String,
        adhaarNumber: String,
        nationalBankIdenticationNumber: String,
        mobileNumber: String,
        type: String,
        transactionAmount: String,
        latitude: String,
        longitude: String
    ) {

        //   dummPid(result, pidOptionDummy);

        //open(result);
        //putStringPref(AppConstants.PIDDATA, txtPidData, mContext)
       /* Toast.makeText(mContext,"AEPS Req: cus_id"+ cus_id+
            " adhaarNumber: "+adhaarNumber+" nationalBankIdenticationNumber: "+nationalBankIdenticationNumber
            +" mobileNumber: "+mobileNumber+" type: "+type+" transactionAmount: "+transactionAmount+
            " latitude: "+latitude+" longitude: "+longitude, Toast.LENGTH_LONG)
            .show()*/
        Log.e("NAC", nationalBankIdenticationNumber)

        val url: String = AEPS_TRANSACTION
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext,"Toast AEPS1: "+ error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["txtPidData"] = txtPidData
                    jsonObject["adhaarNumber"] = adhaarNumber
                    jsonObject["nationalBankIdenticationNumber"] = nationalBankIdenticationNumber
                    jsonObject["mobileNumber"] = mobileNumber
                    jsonObject["type"] = type
                    jsonObject["transactionAmount"] = transactionAmount
                    jsonObject["latitude"] = latitude
                    jsonObject["longitude"] = longitude
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun bankListAeps(
    ) {

        val url: String = AEPS_BANK_LIST
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    //KYC
    fun kycOnBoarding(
        cus_id: String,
        latitude: String,
        longitude: String,
        merchantName: String,
        merchantPhoneNumber: String,
        companyLegalName: String,
        companyMarketingName: String,
        emailId: String,
        merchantPinCode: String,
        merchantCityName: String,
        merchantDistrictName: String,
        merchantState: String,
        merchantAddress: String,
        userPan: String,
        aadhaarNumber: String,
        gstInNumber: String,
        companyOrShopPan: String,
        companyBankAccountNumber: String,
        bankIfscCode: String,
        companyBankName: String,
        bankBranchName: String,
        bankAccountName: String,
        cancellationCheckImages: String,
        shopAndPanImage: String,
        ekycDocuments: String

    ) {

        val url: String = KYC_ONBOARDING
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(
                            true,
                            mFlag,
                            response
                        )
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> = HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["latitude"] = latitude
                    jsonObject["longitude"] = longitude
                    jsonObject["merchantName"] = merchantName
                    jsonObject["merchantPhoneNumber"] = merchantPhoneNumber
                    jsonObject["companyLegalName"] = companyLegalName
                    jsonObject["companyMarketingName"] = companyMarketingName
                    jsonObject["emailId"] = emailId
                    jsonObject["merchantPinCode"] = merchantPinCode
                    jsonObject["merchantCityName"] = merchantCityName
                    jsonObject["merchantDistrictName"] = merchantDistrictName
                    jsonObject["merchantState"] = merchantState
                    jsonObject["merchantAddress"] = merchantAddress
                    jsonObject["userPan"] = userPan
                    jsonObject["aadhaarNumber"] = aadhaarNumber
                    jsonObject["gstInNumber"] = gstInNumber
                    jsonObject["companyOrShopPan"] = companyOrShopPan
                    jsonObject["companyBankAccountNumber"] = companyBankAccountNumber
                    jsonObject["bankIfscCode"] = bankIfscCode
                    jsonObject["companyBankName"] = companyBankName
                    jsonObject["bankBranchName"] = bankBranchName
                    jsonObject["bankAccountName"] = bankAccountName
                    jsonObject["cancellationCheckImages"] = cancellationCheckImages
                    jsonObject["shopAndPanImage"] = shopAndPanImage
                    jsonObject["ekycDocuments"] = ekycDocuments

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.retryPolicy = mRetryPolicy
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun stateList() {
        val url: String = STATE_LIST
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)
                    }
                    // response
                    Log.d("Response", response)
                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun circle() {
        val url: String = CIRCLE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)
                    }
                    // response
                    Log.d("Response", response)
                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun eKyc(
        requestRemarks: String,
        userPan: String,
        aadhaarNumber: String,
        txtPidData: String,
        PidOptions: String,
        cus_id: String
    ) {

        val url: String = EKYC
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, "Toast KYC1: " +error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["requestRemarks"] = requestRemarks
                    jsonObject["userPan"] = userPan
                    jsonObject["aadhaarNumber"] = aadhaarNumber
                    jsonObject["txtPidData"] = txtPidData
                    jsonObject["PidOptions"] = PidOptions
                    jsonObject["cus_id"] = cus_id

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun validateekycotp(
        otp: String,
        cus_id: String
    ) {

        val url: String = VALIDATE_EKYC_OTP
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["otp"] = otp
                    jsonObject["cus_id"] = cus_id

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun resendekycotp(
        cus_id: String
    ) {

        val url: String = RESEND_EKYC_OTP
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()

                    jsonObject["cus_id"] = cus_id

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun checkKycStatus(
        cus_id: String
    ) {
        val url: String = CHECK_KYC_STATUS
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)
                    }
                    // response
                    Log.d("Response", response)
                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }



    //MICRO ATM

    fun microAtmTransaction(
        cus_id: String,
        status: String,
        response: String,
        transAmount: String,
        balAmount: String,
        bankRrn: String,
        transType: String,
        type: String,
        cardNum: String,
        bankName: String,
        cardType: String,
        terminalId: String,
        fpId: String,
        transId: String,
    ) {

        val url: String = MICRO_ATM_TRANSACTION
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["status"] = status
                    jsonObject["response"] = response
                    jsonObject["transAmount"] = transAmount
                    jsonObject["balAmount"] = balAmount
                    jsonObject["bankRrn"] = bankRrn
                    jsonObject["transType"] = transType
                    jsonObject["type"] = type
                    jsonObject["cardNum"] = cardNum
                    jsonObject["bankName"] = bankName
                    jsonObject["cardType"] = cardType
                    jsonObject["terminalId"] = terminalId
                    jsonObject["fpId"] = fpId
                    jsonObject["transId"] = transId
                    jsonObject["cus_id"] = cus_id
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }
    fun microAtmLogin(
        cus_id: String

    ) {

        val url: String = MICRO_ATM_LOGIN
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun getProduct(

    ) {

        val url: String = GET_PRODUCTS
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun getProductsandServices(

    ) {

        val url: String = GET_PRODUCT_AND_SERVICE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun getSingleProductandService(
        product_service_id: String
    ) {

        val url: String = GET_SINGLE_PRODUCT_AND_SERVICE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()

                    jsonObject["product_service_id"] = product_service_id
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun getServiceStatus(
        cus_id: String,
        service: String
    ) {

        val url: String = GET_SERVICE_STATUS
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["service"] = service

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun getServiceAmount(
        service: String
    ) {

        val url: String = GET_SERVICE_AMOUNT
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["service"] = service

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun buyService(
        cus_id: String,
        service: String,
        amount: String
    ) {

        val url: String = BUY_SERVICE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["service"] = service
                    jsonObject["amount"] = amount

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun buyProduct(
        cus_id: String,
        product_id: String,
        pro_name: String,
        quantity: String,
        amount: String,
        address: String
    ) {

        val url: String = BUY_PRODUCT
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["product_id"] = product_id
                    jsonObject["pro_name"] = pro_name
                    jsonObject["quantity"] = quantity
                    jsonObject["amount"] = amount
                    jsonObject["address"] = address

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun buyProductandService(
        cus_id: String,
        product_service_id: String,
        offer_name: String,
        quantity: String,
        sale_price: String,
        address: String
    ) {

        val url: String = BUY_PRODUCT_AND_SERVICE
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id
                    jsonObject["product_service_id"] = product_service_id
                    jsonObject["offer_name"] = offer_name
                    jsonObject["quantity"] = quantity
                    jsonObject["sale_price"] = sale_price
                    jsonObject["address"] = address

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun cashDepositWithOtp(
        cdmobileNumber: String,
        cdAcctNumber: String,
        cdnationalBankIdenticationNumber: String,
        cdtransactionAmount: String,
        cus_id: String
    ) {

        val url: String = CASH_DEPOSIT_WITH_OTP
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cdmobileNumber"] = cdmobileNumber
                    jsonObject["cdAcctNumber"] = cdAcctNumber
                    jsonObject["cdnationalBankIdenticationNumber"] = cdnationalBankIdenticationNumber
                    jsonObject["cdtransactionAmount"] = cdtransactionAmount
                    jsonObject["cus_id"] = cus_id
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun validateCashDepositOtp(
        cdnationalBankIdenticationNumber: String,
        cdmobileNumber: String,
        cdtransactionAmount: String,
        cdAcctNumber: String,
        fingpayTransactionId: String,
        cdPkId: String,
        otp: String,
        cus_id: String
    ) {

        val url: String = VALIDATE_CASH_DEPOSIT_OTP
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cdnationalBankIdenticationNumber"] = cdnationalBankIdenticationNumber
                    jsonObject["cdmobileNumber"] = cdmobileNumber
                    jsonObject["cdtransactionAmount"] = cdtransactionAmount
                    jsonObject["cdAcctNumber"] = cdAcctNumber
                    jsonObject["fingpayTransactionId"] = fingpayTransactionId
                    jsonObject["cdPkId"] = cdPkId
                    jsonObject["otp"] = otp
                    jsonObject["cus_id"] = cus_id
                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun productServicesHistory(
        cus_id: String
    ) {

        val url: String = PRODUCT_SERVICES_HISTORY
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()
                    jsonObject["cus_id"] = cus_id

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    fun offerPopup(
    ) {

        val url: String = OFFER_POPUP
        AppCommonMethods(mContext).LOG(0, TAG, url)
        val getRequest: StringRequest =
            object : StringRequest(
                Method.POST,
                url,
                Response.Listener { response ->
                    if (!(mContext as Activity).isFinishing) {
                        Log.d("Response", response)

                    }
                    // response
                    Log.d("Response", response)

                    try {
                        mApiCallCompleteListener.onAPICallCompleteListner(true, mFlag, response)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    if (!(mContext as Activity).isFinishing) {
                        // gotoNoInternet();
                        Log.e("Error of API", error.toString())
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                    Toast.makeText(mContext, "Oops! Something went wrong!", Toast.LENGTH_SHORT)
                        .show()
                    onErrorResponse(error)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val jsonObject: MutableMap<String, String> =
                        HashMap()

                    jsonObject[TOKEN] = AppPrefs.getStringPref(TOKEN, mContext).toString()

                    return jsonObject
                }

                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String>? {
                    val headers =
                        HashMap<String, String>()
                    headers[mContext.getString(R.string.content_type)] = FORM_URL_ENCODED
                    headers[mContext.getString(R.string.x_api_key)] = API_KEY
                    headers[mContext.getString(R.string.authorization)] = "$BASIC $BASIC_TOKEN"
                    return headers
                }
            }
        getRequest.setShouldCache(false)
        getRequest.setRetryPolicy(mRetryPolicy);
        VolleySingleton.instance?.addToRequestQueue(getRequest)
    }

    /**
     * This function is used to handle error in response
     *
     * @param error
     */
    private fun onErrorResponse(error: VolleyError) {
//        Crashlytics.logException(error);
        VolleyLog.d(TAG, "Error: " + error.message)
        val response = error.networkResponse
        if (response != null) {
            try {
                if (response.statusCode == 401) {
                    Log.d(TAG, "Error: " + String(response.data))
                    // new AppCommonMethods(mContext).showToast(mContext,"" + (new JSONObject(new String(response.data))).getString("message"));
                    //                   Log.d(TAG, "Error: " + error.getMessage() + " status code: " + error.networkResponse.statusCode+" "+(new JSONObject(new String(response.data))).getString("message"));
                } else {
                    Log.d(TAG, "Error: " + String(response.data))
                    // new AppCommonMethods(mContext).showToast(mContext, "" + (new JSONObject(new String(response.data))).getString("message"));
//                    Log.d(TAG, "Error: " + error.getMessage() + " status code: " + error.networkResponse.statusCode+" "+(new JSONObject(new String(response.data))).getString("message"));
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            VolleyLog.d(
                TAG,
                "Error: " + error.message + " status code: " + error.networkResponse.statusCode + " " + response.data
            )
        }
    }

    interface OnAPICallCompleteListener {
        @Throws(JSONException::class)
        fun onAPICallCompleteListner(
            item: Any?,
            flag: String?,
            result: String
        )
    }

    interface OnAPICallError {
        @Throws(JSONException::class)
        fun Error(item: Any?, flag: String?, error: VolleyError?)
    }

    init {
        mFlag = flag
        mApiCallCompleteListener = listener
        //            mAccess = NCLDatabaseAccess.getInstance(mContext);
    }
}