package com.satmatgroup.shreeram.navigation_activities

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import kotlinx.android.synthetic.main.activity_support.*
import kotlinx.android.synthetic.main.activity_support.view.*
import org.json.JSONObject

class SupportActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener {
    val CONTACTUS = "CONTACTUS"

    lateinit var userModel : UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_support)
        //Toolbar
        custToolbar.ivBackBtn.setOnClickListener { onBackPressed() }
        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)


        contactUs(userModel.cus_id,AppPrefs.getStringPref("deviceId", this).toString(),
            AppPrefs.getStringPref("deviceName",this ).toString(),
            userModel.cus_pin,
            userModel.cus_pass,
            userModel.cus_mobile,userModel.cus_type)


    }


    fun contactUs(
        cus_id : String,
        deviceId: String,
        deviceName: String,
        pin: String,
        pass: String,
        cus_mobile: String,
        cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, CONTACTUS, this)
            mAPIcall.contactUsApi(cus_id ,deviceId,deviceName, pin, pass, cus_mobile, cus_type)
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {


        if (flag.equals(CONTACTUS)) {
            Log.e("CONTACTUS", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {
                progress_bar.visibility = View.GONE

                val cast = jsonObject.getJSONArray("result")
                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val contat_id = notifyObjJson.getString("supportid")

                    tvContactNumber.setText(notifyObjJson.getString("mobile"))
                    tvContactEmail.setText(notifyObjJson.getString("email"))
                    tvWhatsAppNumber.setText(notifyObjJson.getString("watsappnum"))
//                    tvContactWebsite.setText(notifyObjJson.getString("website"))
                    Log.e("contat_id", contat_id)



                }

                tvContactNumber.setOnClickListener {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:" + tvContactNumber.text.toString())
                    startActivity(intent)

                }
                tvWhatsAppNumber.setOnClickListener {
                    val contact =
                        "+91 " + tvWhatsAppNumber.text.toString() // use country code with your phone number

                    val url = "https://api.whatsapp.com/send?phone=$contact"
                    try {
                        val pm = packageManager
                        pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        startActivity(i)
                    } catch (e: PackageManager.NameNotFoundException) {
                        Toast.makeText(
                            this@SupportActivity,
                            "Whatsapp app not installed in your phone",
                            Toast.LENGTH_SHORT
                        ).show()
                        e.printStackTrace()
                    }
                }

            } else {
                progress_bar.visibility = View.GONE


            }
        }

    }
}