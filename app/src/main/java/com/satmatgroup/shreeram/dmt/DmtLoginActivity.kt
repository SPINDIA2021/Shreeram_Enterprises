package com.satmatgroup.shreeram.dmt

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import com.satmatgroup.shreeram.utils.toast
import kotlinx.android.synthetic.main.activity_dmt_login.*
import kotlinx.android.synthetic.main.activity_dmt_login.custToolbar
import kotlinx.android.synthetic.main.activity_dmt_login.view.*
import kotlinx.android.synthetic.main.activity_login.progress_bar
import kotlinx.android.synthetic.main.activity_specific_recharge_history.*
import kotlinx.android.synthetic.main.layout_dialog_confirmotp.*
import org.json.JSONObject
import java.util.*

class DmtLoginActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener {
    lateinit var dialog: Dialog
    private val LOGIN_DMT = "LOGIN_DMT"
    private val SEND_OTP = "SEND_OTP"
    private val REGISTER_DMT = "REGISTER_DMT"
    lateinit var dmtUserModel: DmtUserModel
    lateinit var userModel: UserModel
    var otp: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dmt_login)
        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)
        btnDmtLogin.setOnClickListener {

            if (!AppCommonMethods.checkForMobile(etDmtMobile)) {
                etDmtMobile.requestFocus()
                etDmtMobile.error = "Invalid Mobile"
            } else {

                val deviceId =
                    Settings.System.getString(contentResolver, Settings.Secure.ANDROID_ID)

                Log.e("DEVICE ID", "" + deviceId)

                var deviceNameDet = getDeviceName().toString()
                loginDmt(etDmtMobile.text.toString())

            }
        }

        etDmtRegDOB.setOnClickListener {
            TodatePicker()
        }

        btnDmtRegister.setOnClickListener {

            if (etDmtRegFName.text.toString().isNullOrEmpty()) {
                etDmtRegFName.requestFocus()
                etDmtRegFName.error = "Invalid First Name"
            } else if (etDmtRegLName.text.toString().isNullOrEmpty()) {
                etDmtRegLName.requestFocus()
                etDmtRegLName.error = "Invalid Last Name"
            } else if (!AppCommonMethods.checkForMobile(etDmtRegMobileNo)) {
                etDmtRegMobileNo.requestFocus()
                etDmtRegMobileNo.error = "Invalid Mobile"
            } else if (etDmtRegDOB.text.toString().isNullOrEmpty()) {
                etDmtRegDOB.requestFocus()
                etDmtRegDOB.error = "Invalid DOB"
            } else if (etDmtRegPincode.text.toString().isNullOrEmpty()) {
                etDmtRegPincode.requestFocus()
                etDmtRegPincode.error = "Invalid Pincode"
            } else if (etDmtRegAddress.text.toString().isNullOrEmpty()) {
                etDmtRegAddress.requestFocus()
                etDmtRegAddress.error = "Invalid Address"
            } else if (!AppCommonMethods.checkForPan(etDmtRegPan)) {
                etDmtRegPan.requestFocus()
                etDmtRegPan.error = "Invalid Pan"
            } else if (etDmtRegAadhaar.text.toString().isNullOrEmpty()) {
                etDmtRegAadhaar.requestFocus()
                etDmtRegAadhaar.error = "Invalid Aadhar"
            } else {

                var deviceId =
                    Settings.System.getString(contentResolver, Settings.Secure.ANDROID_ID)

                Log.e("DEVICE ID", "" + deviceId)

                var deviceNameDet = getDeviceName().toString()
                val r = Random()
                otp = java.lang.String.format("%04d", r.nextInt(9999))
                Log.d("OTP", otp)
                resgisterDmt(
                    etDmtRegMobileNo.text.toString(), etDmtRegFName.text.toString(), etDmtRegLName.text.toString(),
                    etDmtRegDOB.text.toString(), etDmtRegPincode.text.toString(), etDmtRegAddress.text.toString(),
                    etDmtRegPan.text.toString(), etDmtRegAadhaar.text.toString()
                )
            }
        }
    }

    //API CALL FUNCTION DEFINITION
    private fun loginDmt(
        mobile: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, LOGIN_DMT, this)
            mAPIcall.loginDmt(mobile)
        } else {
            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resgisterDmt(
        mobile: String,
        fname: String,
        lname: String,
        dob: String,
        pincode: String,
        address: String,
        pan: String,
        aadhar: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, REGISTER_DMT, this)
            mAPIcall.registerDmt(mobile, fname, lname, dob, pincode, address, pan, aadhar)
        } else {
            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifySenderOtp(
        mobile: String,
        otp: String

    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, SEND_OTP, this)
            mAPIcall.verifySenderOtp(mobile, otp)
        } else {
            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }


    fun confirmOtp(mobile: String) {
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
            if (dialog.etOtp.text.toString().isNullOrEmpty()) {
                dialog.etOtp.requestFocus()
                dialog.etOtp.setError("Please Enter Valid OTP")
            } else {

                verifySenderOtp(
                    mobile,
                    dialog.etOtp.text.toString()
                )
                dialog.dismiss()

            }

        }
        dialog.show()
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(LOGIN_DMT)) {
            Log.e("LOGIN_DMT", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val result = jsonObject.getString(AppConstants.RESULT)
            Log.e(AppConstants.STATUS, status.toString())
            if (status.contains("true")) {
                progress_bar.visibility = View.GONE

                val cast = jsonObject.getJSONObject("message")
                val status = cast.getString("status")
                if (status.equals("TXN")) {

                    val api_msg = cast.getString("api_msg")

                    if(api_msg.contains("Customer Not found") || api_msg.contains("Customer mobile not verify. Please register")) {

                        progress_bar.visibility = View.GONE
                        cvDmtLogin.visibility = GONE
                        cvDmtRegister.visibility = VISIBLE
                        Toast.makeText(this, api_msg, Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        AppPrefs.putStringPref("dmtMobile", etDmtMobile.text.toString(),this)
                        val bundle = Bundle()
                        bundle.putString("dmtMobile", etDmtMobile.text.toString())
                        val intent = Intent(this, DmtBenificiaryActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }

                } /*else {

                    AppPrefs.putStringPref("dmtMobile", etDmtMobile.text.toString(),this)
                    val bundle = Bundle()
                    bundle.putString("dmtMobile", etDmtMobile.text.toString())
                    val intent = Intent(this, DmtBenificiaryActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)

                }*/

            } else if(status.contains("false")) {

                progress_bar.visibility = View.GONE
                cvDmtLogin.visibility = GONE
                cvDmtRegister.visibility = VISIBLE

                toast("User Not Registered")
            }
        }
        if (flag.equals(REGISTER_DMT)) {
            Log.e("REGISTER_DMT", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val result = jsonObject.getString(AppConstants.RESULT)
            Log.e(AppConstants.STATUS, status.toString())
            if (status.contains("true")) {
                progress_bar.visibility = View.GONE

                val cast = jsonObject.getJSONObject("message")
                val status = cast.getString("status")
                if (status.equals("ERR")) {
                    progress_bar.visibility = View.GONE
                    cvDmtLogin.visibility = GONE
                    cvDmtRegister.visibility = VISIBLE
                    Toast.makeText(this, cast.getString("api_msg"), Toast.LENGTH_SHORT)
                        .show()

                    /*   verifySenderOtp(etDmtRegMobileNo.text.toString(),

                       )*/
                } else if(status.equals("TXN")){


                    confirmOtp(etDmtRegMobileNo.text.toString())
                    Toast.makeText(this, cast.getString("api_msg"), Toast.LENGTH_SHORT).show()

/*                    val bundle = Bundle()
                    //bundle.putSerializable("dmtUserModel", dmtUserModel)

                    val intent = Intent(this, DmtBenificiaryActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)*/

                }


                /*        val r = Random()
                        var otp = java.lang.String.format("%04d", r.nextInt(9999))
                        Log.d("OTP", otp)
                        confirmOtp(otp, etDmtMobile.text.toString(), "login")*/

            } else {
                progress_bar.visibility = View.GONE
                cvDmtLogin.visibility = GONE
                cvDmtRegister.visibility = VISIBLE

                Toast.makeText(this, result, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        if (flag.equals(SEND_OTP)) {
            Log.e("SEND_OTP", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val result = jsonObject.getString(AppConstants.RESULT)
            Log.e(AppConstants.STATUS, status.toString())
            if (status.contains("true")) {
                progress_bar.visibility = View.GONE

                val cast = jsonObject.getJSONObject("message")
                val status = cast.getString("status")
                if (status.equals("SUCCESS")) {

                    cvDmtLogin.visibility = VISIBLE
                    cvDmtRegister.visibility = GONE
                    etDmtRegFName.setText("")
                    etDmtRegMobileNo.setText("")
                    Toast.makeText(this, "" + cast.getString("api_msg"), Toast.LENGTH_SHORT)
                        .show()
                } else {

                    cvDmtLogin.visibility = GONE
                    cvDmtRegister.visibility = VISIBLE

                    Toast.makeText(this, "" + cast.getString("api_msg"), Toast.LENGTH_SHORT)
                        .show()
                }


                /*   var deviceId =
                       Settings.System.getString(contentResolver, Settings.Secure.ANDROID_ID)

                   Log.e("DEVICE ID", "" + deviceId)

                   var deviceNameDet = getDeviceName().toString()*/


            } else {
                progress_bar.visibility = View.GONE
                cvDmtLogin.visibility = GONE
                cvDmtRegister.visibility = VISIBLE

                Toast.makeText(this, result, Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    fun getDeviceName(): String? {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            model
        } else {
            manufacturer.toString() + " " + model
        }
    }

    fun TodatePicker() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var dpd =
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val mmMonth = mMonth + 1
                val date = "$mDay/$mmMonth/$mYear"
                etDmtRegDOB.setText(date)

            }, year, month, day)
        dpd.show()
    }
}