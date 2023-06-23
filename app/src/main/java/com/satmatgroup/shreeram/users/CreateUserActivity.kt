package com.satmatgroup.shreeram.users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import com.satmatgroup.shreeram.utils.toast
import kotlinx.android.synthetic.main.activity_create_user.*
import kotlinx.android.synthetic.main.activity_create_user.view.*
import org.json.JSONObject

class CreateUserActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener {
    lateinit var userModel: UserModel
    private val REGISTER_USER: String = "REGISTER_USER"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        //Toolbar
        custToolbar.ivBackBtn.setOnClickListener { onBackPressed() }

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)


        cvAddUser.setOnClickListener {

            if (etRegName.text.isNullOrEmpty()) {

                etRegName.requestFocus()
                etRegName.error = "Enter Name"
            } else if (!AppCommonMethods.checkForEmail(etRegEmail)) {

                etRegEmail.requestFocus()
                etRegEmail.error = "Please Enter Valid Email"
            } else if (!AppCommonMethods.checkForMobile(etMobileNo)) {

                etMobileNo.requestFocus()
                etMobileNo.error = "Please Enter Valid Mobile"
            } else if (etRegPassword.text.toString().isEmpty()) {
                etRegPassword.requestFocus()
                etRegPassword.error = "Please Enter Valid Password"
            } else {

                if (userModel.cus_type.equals("distributor")) {

                    createRetailer(
                        etMobileNo.text.toString(),
                        userModel.cus_id,
                        etRegName.text.toString(),
                        etRegPassword.text.toString(),
                        etRegEmail.text.toString(),
                        AppPrefs.getStringPref("deviceId", this).toString(),
                        AppPrefs.getStringPref("deviceName", this).toString(),
                        userModel.cus_pin,
                        userModel.cus_pass,
                        userModel.cus_mobile,
                        userModel.cus_type
                    )

                } else if (userModel.cus_type.equals("master")) {

                    createDistributor(
                        etMobileNo.text.toString(),
                        userModel.cus_id,
                        etRegName.text.toString(),
                        etRegPassword.text.toString(),
                        etRegEmail.text.toString(),
                        AppPrefs.getStringPref("deviceId", this).toString(),

                        AppPrefs.getStringPref("deviceName", this).toString(),
                        userModel.cus_pin,
                        userModel.cus_pass,
                        userModel.cus_mobile,
                        userModel.cus_type

                    )

                }
            }


        }


    }

    //API CALL FUNCTION DEFINITION
    private fun createRetailer(
        newMob: String, dis_id: String, newName: String,
        newPass: String, newEmail: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE


        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, REGISTER_USER, this)
            mAPIcall.createUserApi(
                newMob, dis_id, newName, newPass, newEmail, deviceId, deviceName, pin,
                pass, cus_mobile, cus_type
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createDistributor(
        newMob: String, dis_id: String, newName: String,
        newPass: String, newEmail: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE


        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, REGISTER_USER, this)
            mAPIcall.createDistributorApi(
                newMob, dis_id, newName, newPass, newEmail, deviceId, deviceName, pin,
                pass, cus_mobile, cus_type
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {


        if (flag.equals(REGISTER_USER)) {
            Log.e("REGISTER_USER", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {
                progress_bar.visibility = View.INVISIBLE

                /*   val cast = jsonObject.getJSONArray("result")
                   for (i in 0 until cast.length()) {
                       val notifyObjJson = cast.getJSONObject(i)
                       val user_id = notifyObjJson.getString("user_id")
                       Log.e("user_id", user_id)
                       var userModel = Gson()
                           .fromJson(notifyObjJson.toString(), UserModel::class.java)
                   }
                   val gson = Gson()
                   val json = gson.toJson(userModel)
                   AppPrefs.putStringPref("userModel", json, this)
                   AppPrefs.putBooleanPref(AppConstants.IS_LOGIN, true, this)
                   AppPrefs.putStringPref("user_id", userModel.cust_id, this)
   */
                val response = jsonObject.getString("result")

                toast(response)
                val intent = Intent(this, MyUsersActivity::class.java)
                startActivity(intent)
                finish()


            } else {
                progress_bar.visibility = View.INVISIBLE
                val response = jsonObject.getString("result")
                toast(response)

            }
        }

    }
}