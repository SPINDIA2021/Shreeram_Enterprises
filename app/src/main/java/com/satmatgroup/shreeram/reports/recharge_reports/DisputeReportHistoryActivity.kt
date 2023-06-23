package com.satmatgroup.shreeram.reports.recharge_reports

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.a.DisputeHistoryAdapter
import com.satmatgroup.shreeram.model.DisputeHistoryModel
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import kotlinx.android.synthetic.main.activity_dispute_report_history.*
import kotlinx.android.synthetic.main.activity_dispute_report_history.view.*
import org.json.JSONObject

class DisputeReportHistoryActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener,
    PopupMenu.OnMenuItemClickListener {

    lateinit var disputeHistoryAdapter: DisputeHistoryAdapter
    var disputeHistoryModelArrayList = ArrayList<DisputeHistoryModel>()
    private val DISPUTE_REPORT: String = "DISPUTE_REPORT"


    lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.black, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_dispute_report_history)
        //Toolbar
        custToolbar.ivBackBtn.setOnClickListener { onBackPressed() }

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)

        disputeHistory(
            userModel.cus_id, AppPrefs.getStringPref("deviceId", this).toString(),
            AppPrefs.getStringPref("deviceName", this).toString(),
            userModel.cus_pin,
            userModel.cus_pass,
            userModel.cus_mobile, userModel.cus_type


        )

        custToolbar.ivMore.setOnClickListener {
            val popup = PopupMenu(this@DisputeReportHistoryActivity, ivMore)
            popup.setOnMenuItemClickListener(this@DisputeReportHistoryActivity)
            popup.inflate(R.menu.menu_main)
            popup.show()
        }
        rvDisputeHistory.apply {

            layoutManager = LinearLayoutManager(this@DisputeReportHistoryActivity)
            disputeHistoryAdapter = DisputeHistoryAdapter(
                context, disputeHistoryModelArrayList
            )
            rvDisputeHistory.adapter = disputeHistoryAdapter
        }

    }

    private fun disputeHistory(
        cus_id: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, DISPUTE_REPORT, this)
            mAPIcall.disputeHistory(
                cus_id, deviceId, deviceName, pin,
                pass, cus_mobile, cus_type
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(DISPUTE_REPORT)) {
            Log.e("DISPUTE_REPORT", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                progress_bar.visibility = View.INVISIBLE

                val cast = jsonObject.getJSONArray("result")

                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val t_id = notifyObjJson.getString("t_id")
                    Log.e("t_id ", t_id)
                    val rechargeHistoryModal = Gson().fromJson(
                            notifyObjJson.toString(),
                            DisputeHistoryModel::class.java
                        )


                    disputeHistoryModelArrayList.add(rechargeHistoryModal)
                }

                rvDisputeHistory.adapter!!.notifyDataSetChanged()


            } else {
                progress_bar.visibility = View.INVISIBLE


            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.action_refresh -> {
                disputeHistory(
                    userModel.cus_id, AppPrefs.getStringPref("deviceId", this).toString(),
                    AppPrefs.getStringPref("deviceName", this).toString(),
                    userModel.cus_pin,
                    userModel.cus_pass,
                    userModel.cus_mobile, userModel.cus_type


                )
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}