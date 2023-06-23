package com.satmatgroup.shreeram.user_profile

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.activities_aeps.AepsOnboardActivity
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.navigation_activities.SupportActivity
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import com.satmatgroup.shreeram.utils.toast
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.view.*
import kotlinx.android.synthetic.main.layout_dialog_change_password.*
import kotlinx.android.synthetic.main.layout_dialog_change_password.custToolbarDialog
import kotlinx.android.synthetic.main.layout_dialog_change_password.view.*
import kotlinx.android.synthetic.main.layout_dialog_change_pin.*
import kotlinx.android.synthetic.main.layout_dialog_change_pin.view.ivClosePinDialog
import kotlinx.android.synthetic.main.layout_dialog_confirmotp.*
import kotlinx.android.synthetic.main.layout_dialog_confirmpin.tvDialogCancel
import org.json.JSONObject
import java.util.*

class ProfileActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener {
    lateinit var dialog: Dialog

    private val CHANGEPASSWORD_API: String = "CHANGEPASSWORD_API"
    private val CHANGEPIN_API: String = "CHANGEPIN_API"

    lateinit var userModel: UserModel
    lateinit var newaepskyc_status: String

    private val SEND_OTP: String = "SEND_OTP"
    private val FORGET_PIN: String = "FORGET_PIN"
    lateinit var otp: String
    var deviceId: String = ""
    var deviceNameDet: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.status_bar, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_profile)


        val gson = Gson()
        val json = AppPrefs.getStringPref(AppConstants.USER_MODEL, this)
        userModel = gson.fromJson(json, UserModel::class.java)
        getProfileApi(userModel.cus_mobile)

        deviceId = AppCommonMethods.getDeviceId(this)
        deviceNameDet = AppCommonMethods.getDeviceName()

        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }


        tvKycNotVerified.setOnClickListener {
            val intent = Intent(this, AepsOnboardActivity::class.java)
            startActivity(intent)
        }

        rl_changepassword.setOnClickListener {

            changePasswordDialog()
        }
        rl_changepin.setOnClickListener {
            changePinDialog()
        }

        ll_gotoSupport.setOnClickListener {
            val intent = Intent(this, SupportActivity::class.java)
            startActivity(intent)
        }


        rl_forgotpin.setOnClickListener {
            val gson = Gson()
            val json = AppPrefs.getStringPref(AppConstants.USER_MODEL, this)
            userModel = gson.fromJson(json, UserModel::class.java)
            val r = Random()
            otp = java.lang.String.format("%06d", r.nextInt(999999))
            Log.d("OTP", otp)
            sendSmsforPin(
                userModel.cus_mobile, otp, AppPrefs.getStringPref("deviceId", this).toString(),
                AppPrefs.getStringPref("deviceName", this).toString(),
                userModel.cus_pin,
                userModel.cus_pass,
                userModel.cus_mobile, userModel.cus_type
            )
        }
  }


    fun sendSmsforPin(
        mobile: String, otp: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, SEND_OTP, this)
            mAPIcall.getpinotp(mobile, otp, deviceId, deviceName, pin, pass, cus_mobile, cus_type)
        } else {

            toast("No Internet Connection")

        }
    }

    fun forgetpin(
        cus_id: String,
        deviceId: String,
        deviceName: String,
        cus_type: String

    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, FORGET_PIN, this)
            mAPIcall.forgetpin(cus_id, deviceId, deviceName, cus_type)
        } else {

            toast("No Internet Connection")
        }
    }

    private fun changePasswordDialog() {
        dialog = Dialog(this, R.style.Widget_MaterialComponents_MaterialCalendar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_dialog_change_password)
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.custToolbarDialog.ivClosePasswordDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.etConfirmNewPassword.setText("")
        dialog.etCurrentPassword.setText("")
        dialog.etNewPassword.setText("")


        dialog.btnChangePassword.setOnClickListener {
            val gson = Gson()
            val json = AppPrefs.getStringPref(AppConstants.USER_MODEL, this)
            userModel = gson.fromJson(json, UserModel::class.java)
            if (dialog.etCurrentPassword.text.toString().isEmpty()) {

                dialog.etCurrentPassword.requestFocus()
                dialog.etCurrentPassword.error = "Invalid Password"
            } else if (dialog.etNewPassword.text.toString().isEmpty()) {
                dialog.etNewPassword.requestFocus()
                dialog.etNewPassword.error = "Invalid Password"
            } else if (dialog.etConfirmNewPassword.text.toString().isEmpty()) {
                dialog.etConfirmNewPassword.requestFocus()
                dialog.etConfirmNewPassword.error = "Invalid Password"
            } else if (dialog.etNewPassword.text.toString() != etConfirmNewPassword.text.toString()) {
                toast("New Password doesn't match")
            } else {

                //Code after validation
                changePassword(
                    userModel.cus_id.toString(),
                    dialog.etCurrentPassword.text.toString(),
                    dialog.etConfirmNewPassword.text.toString(),
                    AppPrefs.getStringPref("deviceId", this).toString(),
                    AppPrefs.getStringPref("deviceName", this).toString(),
                    userModel.cus_pin, userModel.cus_pass, userModel.cus_mobile,
                    userModel.cus_type

                )
            }
        }

        dialog.show()
    }

    private fun changePinDialog() {
        dialog = Dialog(this, R.style.Widget_MaterialComponents_MaterialCalendar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_dialog_change_pin)
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.custToolbarDialog.ivClosePinDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.btnChangePin.setOnClickListener {
            val gson = Gson()
            val json = AppPrefs.getStringPref(AppConstants.USER_MODEL, this)
            userModel = gson.fromJson(json, UserModel::class.java)

            if (dialog.etCurrentPin.text.toString().isEmpty()) {

                dialog.etCurrentPin.requestFocus()
                dialog.etCurrentPin.error = "Invalid Pin"
            } else if (etNewPin.text.toString().isEmpty()) {
                dialog.etNewPin.requestFocus()
                dialog.etNewPin.error = "Invalid Pin"
            } else if (dialog.etConfirmNewPin.text.toString().isEmpty()) {
                dialog.etConfirmNewPin.requestFocus()
                dialog.etConfirmNewPin.error = "Invalid Pin"
            } else if (dialog.etNewPin.text.toString() != dialog.etConfirmNewPin.text.toString()) {
                toast("New Pin doesn't match")
            } else {

                //Code after validation
                changePin(
                    userModel.cus_id,
                    dialog.etCurrentPin.text.toString(),
                    dialog.etConfirmNewPin.text.toString(),
                    AppPrefs.getStringPref("deviceId", this).toString(),
                    AppPrefs.getStringPref("deviceName", this).toString(),
                    userModel.cus_pin, userModel.cus_pass, userModel.cus_mobile, userModel.cus_type
                )
            }
        }


        dialog.show()
    }


    //API CALL FUNCTION DEFINITION
    private fun getProfileApi(
        cus_id: String
    ) {
        progress_bar.visibility = View.VISIBLE
        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                this,
                AppConstants.PROFILE_API,
                this
            )
            mAPIcall.getProfile(cus_id)

        } else {
            toast(getString(R.string.error_internet))
        }
    }


    //API CALL FUNCTION DEFINITION
    private fun changePassword(
        cus_id: String, current_pass: String, new_pass: String,
        deviceId: String, deviceName: String, pin: String, pass: String,
        cus_mobile: String, cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, CHANGEPASSWORD_API, this)
            mAPIcall.changePassword(
                cus_id,
                current_pass,
                new_pass,
                deviceId,
                deviceName,
                pin,
                pass,
                cus_mobile,
                cus_type
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    //API CALL FUNCTION DEFINITION
    private fun changePin(
        cus_id: String, current_pin: String, new_pin: String,
        deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, CHANGEPIN_API, this)
            mAPIcall.changePin(
                cus_id, current_pin, new_pin, deviceId, deviceName,
                pin, pass, cus_mobile, cus_type
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(AppConstants.PROFILE_API)) {
            progress_bar.visibility = View.GONE
            Log.e(AppConstants.PROFILE_API, result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val messageCode = jsonObject.getString(AppConstants.MESSAGE)

            //   val token = jsonObject.getString(AppConstants.TOKEN)
            Log.e(AppConstants.STATUS, status)
            Log.e(AppConstants.MESSAGE, messageCode)
            if (status.contains(AppConstants.TRUE)) {


                val users = jsonObject.getJSONArray(AppConstants.RESULT)

                    for (i in 0 until users.length()) {
                    val notifyObjJson = users.getJSONObject(i)
                    newaepskyc_status = notifyObjJson.getString("newaepskyc_status")
                        Log.e("kyc",newaepskyc_status)
                    userModel = Gson()
                        .fromJson(notifyObjJson.toString(), UserModel::class.java)
                }

                if(newaepskyc_status.equals("not-done")) {
                    tvKycNotVerified.visibility = View.VISIBLE
                    tvKycVerified.visibility = View.GONE
                } else {
                    tvKycNotVerified.visibility = View.GONE
                    tvKycVerified.visibility = View.VISIBLE
                }

                tvProfileUserName.text = userModel.cus_name
                tvProfileCustType.text = userModel.cus_type
                tvProfileEmail.text = userModel.cus_email
                tvProfileMobileNumber.text = jsonObject.getString(AppConstants.CUS_MOBILE)
                /*     Glide.with(this)
                         .load(userModel.profile_img)
                         .into(ivProfileImage)
     */

            } else {
                if (messageCode.equals(getString(R.string.error_expired_token))) {
                    AppCommonMethods.logoutOnExpiredDialog(this)

                } else {
                    toast(messageCode.trim())
                }
            }
        }
        if (flag.equals(CHANGEPASSWORD_API)) {
            Log.e("CHANGEPASSWORD_API", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status.toString())
            //Log.e(AppConstants.MESSAGE_CODE, messageCode);
            if (status.contains("true")) {
                progress_bar.visibility = View.GONE

                val cast = jsonObject.getJSONArray("result")
                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val cust_id = notifyObjJson.getString("cus_id")
                    Log.e("id", cust_id)
                    userModel = Gson()
                        .fromJson(notifyObjJson.toString(), UserModel::class.java)
                }
                val gson = Gson()
                val json = gson.toJson(userModel)
                AppPrefs.putStringPref("userModel", json, this)
                Toast.makeText(this, "Password Changed Successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else if (status.contains("false")) {

                progress_bar.visibility = View.GONE

                //JSONObject locationId = cast.getJSONObject(0);
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()

            }
        }
        if (flag.equals(CHANGEPIN_API)) {
            Log.e("CHANGEPIN_API", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status.toString())
            //Log.e(AppConstants.MESSAGE_CODE, messageCode);
            if (status.contains("true")) {
                val cast = jsonObject.getJSONArray("result")
                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val cust_id = notifyObjJson.getString("cus_id")
                    Log.e("id", cust_id)
                    userModel = Gson()
                        .fromJson(notifyObjJson.toString(), UserModel::class.java)
                }
                val gson = Gson()
                val json = gson.toJson(userModel)
                AppPrefs.putStringPref("userModel", json, this)
                Toast.makeText(this, "Pin Changed Successfully", Toast.LENGTH_SHORT).show()
                dialog.etCurrentPin.setText("")
                dialog.etNewPin.setText("")
                dialog.etConfirmNewPin.setText("")
                dialog.dismiss()
            } else if (status.contains("false")) {


                //JSONObject locationId = cast.getJSONObject(0);
                Toast.makeText(this, "Invalid Mobile or Pin", Toast.LENGTH_SHORT).show()
                dialog.etCurrentPin.setText("")
                dialog.etNewPin.setText("")
                dialog.etConfirmNewPin.setText("")
            }
        }

        if (flag.equals(SEND_OTP)) {
            Log.e("SEND_OTP", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            //Log.e(AppConstants.MESSAGE_CODE, messageCode);
            if (status.contains("true")) {
                progress_bar.visibility = View.INVISIBLE
                confirmOtp(otp)
            } else {

                progress_bar.visibility = View.INVISIBLE

            }
        }
        if (flag.equals(FORGET_PIN)) {
            Log.e("FORGET_PIN", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val messageCode = jsonObject.getString(AppConstants.MESSAGE)
            Log.e(AppConstants.STATUS, status)
            Log.e(AppConstants.MESSAGE, messageCode);
            if (status.contains("true")) {
                progress_bar.visibility = View.INVISIBLE
                showForgotpin(messageCode)
            } else {
                progress_bar.visibility = View.INVISIBLE
                Toast.makeText(
                    this,
                    "Oops! Something Went Wrong Please Try Again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }

    fun confirmOtp(otp: String) {
        dialog = Dialog(this, R.style.ThemeOverlay_MaterialComponents_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_dialog_confirmotp)

        dialog.etOtp.requestFocus()
        dialog.tvDialogCancel.setOnClickListener {
            dialog.dismiss()
        }


        dialog.getWindow()!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        dialog.tvConfirmOtp.setOnClickListener {
            if (!dialog.etOtp.text.toString().equals(otp)) {
                dialog.etOtp.requestFocus()
                dialog.etOtp.setError("Please Enter Valid OTP")
            } else {
                forgetpin(userModel.cus_id, deviceId, deviceNameDet, userModel.cus_type)
                dialog.dismiss()

            }

        }


        dialog.show()
    }

    private fun showForgotpin(msg: String) {
        val builder1 =
            AlertDialog.Builder(this)
        builder1.setTitle("Success!")
        builder1.setMessage(msg)
        builder1.setCancelable(true)
        builder1.setPositiveButton(
            "OK"
        ) { dialog, id ->


            dialog.cancel()
        }

        val alert11 = builder1.create()
        alert11.show()
    }

}