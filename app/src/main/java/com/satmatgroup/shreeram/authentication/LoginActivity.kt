package com.satmatgroup.shreeram.authentication

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.satmatgroup.shreeram.NewMainActivity
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppConstants.Companion.LOGIN_API
import com.satmatgroup.shreeram.utils.AppConstants.Companion.MESSAGE
import com.satmatgroup.shreeram.utils.AppConstants.Companion.RESULT
import com.satmatgroup.shreeram.utils.AppConstants.Companion.TOKEN
import com.satmatgroup.shreeram.utils.AppConstants.Companion.TRUE
import com.satmatgroup.shreeram.utils.AppConstants.Companion.USER_MODEL
import com.satmatgroup.shreeram.utils.AppPrefs
import com.satmatgroup.shreeram.utils.toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_dialog_confirmotp.*
import org.json.JSONObject
import java.util.*

class LoginActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener {
    var dialogBuilder: AlertDialog? = null
    var editText: EditText? = null
    private lateinit var otp: String
    lateinit var dialog: Dialog

    private var NOTE: String = "NOTE"

    lateinit var userModel: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.status_bar, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_login_new)

        btnLogin.setOnClickListener {
            if (etLoginMobile.text.toString().isNullOrEmpty()) {
                etLoginMobile.requestFocus()
                etLoginMobile.error = getString(R.string.error_mobile_number)
            } else if (etLoginPassword.text.toString().isNullOrEmpty()) {
                etLoginPassword.requestFocus()
                etLoginPassword.error = getString(R.string.error_password)
            } else {
                val r = Random()
                otp = java.lang.String.format("%06d", r.nextInt(999999))
                Log.d("OTP", otp)
                AppPrefs.putStringPref("AppPassword", etLoginPassword.text.toString(), this)
                loginApi(
                    etLoginMobile.text.toString(), etLoginPassword.text.toString(),
                    AppCommonMethods.getDeviceId(this),
                    AppCommonMethods.getDeviceName(),
                    otp
                )
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
            }

        }

        note()

        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }

        tvRegisterHere.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    //API CALL FUNCTION DEFINITION
    private fun loginApi(
        mobile: String, password: String, deviceId: String, deviceNameDet: String,
        otp: String
    ) {
        progress_bar.visibility = View.VISIBLE
        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                this,
                LOGIN_API,
                this
            )
            mAPIcall.login(mobile, password, deviceId, deviceNameDet, otp)

        } else {
            toast(getString(R.string.error_internet))
        }
    }

    private fun note(

    ) {
        progress_bar.visibility = View.VISIBLE
        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                this,
                NOTE,
                this
            )
            mAPIcall.note()

        } else {
            toast(getString(R.string.error_internet))
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(LOGIN_API)) {
            progress_bar.visibility = View.GONE
            Log.e(LOGIN_API, result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val messageCode = jsonObject.getString(MESSAGE)
            Log.e(AppConstants.STATUS, status)
            Log.e(MESSAGE, messageCode)
            if (status.contains(TRUE)) {
                val token = jsonObject.getString(TOKEN)
                val cast = jsonObject.getJSONArray(RESULT)
                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    userModel = Gson()
                        .fromJson(notifyObjJson.toString(), UserModel::class.java)
                }
                val gson = Gson()
                val json = gson.toJson(userModel)
                AppPrefs.putStringPref(USER_MODEL, json, this)
                AppPrefs.putBooleanPref(AppConstants.IS_LOGIN, true, this)
                AppPrefs.putStringPref(TOKEN, token, this)
                AppPrefs.putStringPref(AppConstants.USER_ID, etLoginMobile.text.toString(), this)
                AppPrefs.putStringPref("deviceId", AppCommonMethods.getDeviceId(this), this)
                AppPrefs.putStringPref("deviceName", AppCommonMethods.getDeviceName(), this)
                val intent = Intent(this, NewMainActivity::class.java)
                startActivity(intent)
                finish()

                //confirmOtp(otp, userModel, token)


            } else {
                toast(messageCode.trim())
            }
        }

        if (flag.equals(NOTE)) {
            progress_bar.visibility = View.GONE
            Log.e(NOTE, result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val messageCode = jsonObject.getJSONArray("result")
            Log.e(AppConstants.STATUS, status)
            if (status.contains(TRUE)) {
                for (i in 0 until messageCode.length()) {
                    val notifyObjJson = messageCode.getJSONObject(i)
                    tvNote.text = notifyObjJson.getString("note")
                }
            } else {
               progress_bar.visibility = View.INVISIBLE
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        finishAffinity()
    }

/*
    private fun showforgotPassDialog() {
        dialogBuilder = AlertDialog.Builder(this).create()
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.forget_password_dialog, null)

        val btnOk =
            dialogView.findViewById<Button>(R.id.btnForgotOk)
        val btnCancel =
            dialogView.findViewById<Button>(R.id.btnForgotCancel)

        dialogView.etForgotPassMobile.requestFocus()
        btnOk.setOnClickListener {
            forgetPassword(
                dialogView.etForgotPassMobile.getText().toString(),
                AppCommonMethods.getDeviceId(this), AppCommonMethods.getDeviceName())
            //                dialogBuilder.dismiss();

            // forgetPassword(editText.getText().toString());
        }
        btnCancel.setOnClickListener { // DO SOMETHINGS
            dialogBuilder!!.dismiss()
        }
        dialogBuilder!!.setOnShowListener(DialogInterface.OnShowListener {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(
                editText,
                InputMethodManager.SHOW_IMPLICIT
            )
        })
        dialogBuilder!!.setView(dialogView)
        dialogBuilder!!.show()
    }
*/

    fun confirmOtp(otp: String, userModel: UserModel, token: String) {
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
                val gson = Gson()
                val json = gson.toJson(userModel)
                AppPrefs.putStringPref(USER_MODEL, json, this)
                AppPrefs.putBooleanPref(AppConstants.IS_LOGIN, true, this)
                AppPrefs.putStringPref(TOKEN, token, this)
                AppPrefs.putStringPref("deviceId", AppCommonMethods.getDeviceId(this), this)
                AppPrefs.putStringPref("deviceName", AppCommonMethods.getDeviceName(), this)
                val intent = Intent(this, NewMainActivity::class.java)
                startActivity(intent)
                finish()
                dialog.dismiss()
            }
        }
        dialog.show()
    }
}