package com.satmatgroup.shreeram.user_profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.MantraDeviceActivity
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.utils.AppPrefs
import kotlinx.android.synthetic.main.activity_kyc_verification.*

class KycVerificationActivity : AppCompatActivity() {

    lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kyc_verification)

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)


        btnVerifyKYC.setOnClickListener {
            if(etRequestRemarks.text.toString().isNullOrEmpty()) {
                etRequestRemarks.requestFocus()
                etRequestRemarks.setError("Invalid Remarks")
            } else if(etAdhaarNumber.text.toString().isNullOrEmpty()) {
                etAdhaarNumber.requestFocus()
                etAdhaarNumber.setError("Invalid Aadhaar Number")
            } else if(etAdhaarNumber.text.toString().length < 12) {
                etAdhaarNumber.requestFocus()
                etAdhaarNumber.setError("Invalid Aadhaar Number")
            } else if(etPanNumber.text.toString().isNullOrEmpty()) {
                etPanNumber.requestFocus()
                etPanNumber.setError("Invalid Pan Number")
            } else {

                val bundle = Bundle()
                bundle.putString("flag","ekyc")
                bundle.putString("requestremarks",etRequestRemarks.text.toString())
                bundle.putString("aadharnumberkyc",etAdhaarNumber.text.toString())
                bundle.putString("pannumberkyc",etPanNumber.text.toString())
                bundle.putString("kyccusid",userModel.cus_id)

                val intent = Intent(this, MantraDeviceActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }
}