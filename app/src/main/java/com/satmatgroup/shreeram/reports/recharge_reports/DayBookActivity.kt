package com.satmatgroup.shreeram.reports.recharge_reports

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserDayBookModel
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import kotlinx.android.synthetic.main.activity_day_book.*
import kotlinx.android.synthetic.main.activity_day_book.view.*
import org.json.JSONObject
import kotlin.collections.ArrayList

class DayBookActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener {
    private val DAYBOOK: String = "DAYBOOK"
    lateinit var userModel: UserModel
    var commssionSlabModelArrayList: ArrayList<UserDayBookModel>? = null

    lateinit var userDayBook : UserDayBookModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.black, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_day_book)

        //Toolbar
        custToolbar.ivBackBtn.setOnClickListener { onBackPressed() }

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)

        userDayBook(userModel.cus_id,AppPrefs.getStringPref("deviceId", this).toString(),
            AppPrefs.getStringPref("deviceName",this ).toString(),
            userModel.cus_pin,
            userModel.cus_pass,
            userModel.cus_mobile,userModel.cus_type
        )
    }

    private fun userDayBook(
        cus_id: String,deviceId:String,deviceName :String,pin :String,
        pass : String,cus_mobile : String,cus_type :String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, DAYBOOK, this)
            mAPIcall.userDayBook(cus_id,deviceId ,deviceName,pin,
                pass,cus_mobile,cus_type)
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(
                DAYBOOK
            )
        ) {
            Log.e("DAYBOOK", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            //Log.e(AppConstants.MESSAGE_CODE, messageCode);
            if (status.contains("true")) {
                progress_bar.visibility = View.GONE

                val cast = jsonObject.getJSONArray("result")
                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val type = notifyObjJson.getString("type")
                    Log.e("type", type)
                    userDayBook = Gson()
                        .fromJson<UserDayBookModel>(
                            notifyObjJson.toString(),
                            UserDayBookModel::class.java)


                    if (type.equals("PENDING RECHARGES")){
                        tvPendingRecBalance.text ="Balance :\n₹ "+userDayBook.bal
                        tvPendingCount.text ="Count : "+userDayBook.cnt

                    }else if (type.equals("SUCCESS RECHARGES")){
                        tvSuccessRecBalance.text ="Balance :\n₹ "+userDayBook.bal
                        tvSuccessCount.text ="Count :" +userDayBook.cnt


                    }else if (type.equals("FAILED RECHARGES")){
                        tvFailedRecBalance.text ="Balance :\n₹ "+userDayBook.bal
                        tvFailedCount.text ="Count : "+userDayBook.cnt



                    }else if (type.equals("FUND CREDITED")){

                        tvFundCreditBalance.text ="Balance : ₹ "+userDayBook.bal
                        tvFundCreditCount.text ="Count : "+userDayBook.cnt


                    }else if (type.equals("FUND DEBITED")){
                        tvFundDebitBalance.text ="Balance : ₹ "+userDayBook.bal
                        tvFundDebitCount.text ="Count : "+userDayBook.cnt



                    }else if (type.equals("OPENING BALANCE")){

                        tvOpeningBalance.text ="Balance : ₹ "+userDayBook.bal


                    }else if (type.equals("CLOSING BALANCE")){

                        tvClosingBalance.text ="Balance : ₹ "+userDayBook.bal


                    }
                }





            }
        }
    }

}