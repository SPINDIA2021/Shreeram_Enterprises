package com.satmatgroup.shreeram.dmt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppConstants
import kotlinx.android.synthetic.main.activity_dmt_login.*
import kotlinx.android.synthetic.main.activity_login.progress_bar
import org.json.JSONObject

class DmtOtpActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener {
    private val SEND_OTP = "SEND_OTP"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dmt_otp)









    }

/*    private fun verifySenderOtp(
        mobile: String,
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, SEND_OTP, this)
            mAPIcall.verifySenderOtp(mobile)
        } else {
            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }*/

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
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


                    etDmtRegMobileNo.setText("")
                    Toast.makeText(this, "" + cast.getString("api_msg"), Toast.LENGTH_SHORT)
                        .show()
                } else {

                    cvDmtLogin.visibility = View.GONE
                    cvDmtRegister.visibility = View.VISIBLE

                    Toast.makeText(this, "" + cast.getString("api_msg"), Toast.LENGTH_SHORT)
                        .show()
                }


                /*   var deviceId =
                       Settings.System.getString(contentResolver, Settings.Secure.ANDROID_ID)

                   Log.e("DEVICE ID", "" + deviceId)

                   var deviceNameDet = getDeviceName().toString()*/


            } else {
                progress_bar.visibility = View.GONE
                cvDmtLogin.visibility = View.GONE
                cvDmtRegister.visibility = View.VISIBLE

                Toast.makeText(this, result, Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

}