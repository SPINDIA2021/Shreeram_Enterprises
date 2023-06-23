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
import com.satmatgroup.shreeram.adapters_recyclerview.CommisionSlabadapter
import com.satmatgroup.shreeram.model.CommisionSlabModel
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import kotlinx.android.synthetic.main.activity_commision_report.*
import kotlinx.android.synthetic.main.activity_commision_report.view.*
import kotlinx.android.synthetic.main.activity_commission_slab.*
import kotlinx.android.synthetic.main.activity_commission_slab.custToolbar
import kotlinx.android.synthetic.main.activity_commission_slab.ivMore
import kotlinx.android.synthetic.main.activity_commission_slab.progress_bar
import org.json.JSONObject

class CommissionSlabActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener,
    PopupMenu.OnMenuItemClickListener {

    lateinit var commisionSlabadapter: CommisionSlabadapter
    var commisionSlabModelArrayList = ArrayList<CommisionSlabModel>()
    private val COMMISION_SLAB: String = "COMMISION_SLAB"


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
        setContentView(R.layout.activity_commission_slab)
        //Toolbar
        custToolbar.ivBackBtn.setOnClickListener { onBackPressed() }

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)

        commisionApi(
            userModel.cus_id,AppPrefs.getStringPref("deviceId", this).toString(),
            AppPrefs.getStringPref("deviceName",this ).toString(),
            userModel.cus_pin,
            userModel.cus_pass,
            userModel.cus_mobile,userModel.cus_type
        )
        custToolbar.ivMore.setOnClickListener {
            val popup = PopupMenu(this@CommissionSlabActivity, ivMore)
            popup.setOnMenuItemClickListener(this@CommissionSlabActivity)
            popup.inflate(R.menu.menu_main)
            popup.show()
        }
        rvCommisionSlabReport.apply {

            layoutManager = LinearLayoutManager(this@CommissionSlabActivity)
            commisionSlabadapter = CommisionSlabadapter(
                context, commisionSlabModelArrayList
            )
            rvCommisionSlabReport.adapter = commisionSlabadapter
        }

    }

    private fun commisionApi(
        cus_id: String,deviceId:String,deviceName :String,pin :String,
        pass : String,cus_mobile : String,cus_type :String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, COMMISION_SLAB, this)
            mAPIcall.commisionSlab(cus_id,deviceId ,deviceName,pin,
                pass,cus_mobile,cus_type)
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(COMMISION_SLAB)) {
            Log.e("COMMISION_SLAB", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                progress_bar.visibility = View.INVISIBLE

                val cast = jsonObject.getJSONArray("result")

                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val cus_id = notifyObjJson.getString("cus_id")
                    Log.e("cus_id ", cus_id)
                    val rechargeHistoryModal = Gson()
                        .fromJson(
                            notifyObjJson.toString(),
                            CommisionSlabModel::class.java
                        )


                    commisionSlabModelArrayList.add(rechargeHistoryModal)
                }

                rvCommisionSlabReport.adapter!!.notifyDataSetChanged()


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
                commisionApi(userModel.cus_id,AppPrefs.getStringPref("deviceId", this).toString(),
                    AppPrefs.getStringPref("deviceName",this ).toString(),
                    userModel.cus_pin,
                    userModel.cus_pass,
                    userModel.cus_mobile,userModel.cus_type

                )

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}