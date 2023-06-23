package com.satmatgroup.shreeram.payout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import kotlinx.android.synthetic.main.activity_payout_history.*
import kotlinx.android.synthetic.main.activity_payout_history.rvDisputeHistory
import kotlinx.android.synthetic.main.activity_payout_history.view.*
import org.json.JSONObject

class PayoutHistoryActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener,
    AppApiCalls.OnAPICallCompleteListener {

    lateinit var aepsHistoryAdapter: AepsPayoutHistoryAdapter
    var aepsHistoryModelArrayList = ArrayList<PayoutHistoryModel>()
    private val AEPS_PAYOUT_HISTORY: String = "AEPS_PAYOUT_HISTORY"
    lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payout_history)


        //Toolbar
        custToolbar.ivBackBtn.setOnClickListener { onBackPressed() }


        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)

        aepsPayoutHistory(
            userModel.cus_id
        )
        custToolbar.ivMore.setOnClickListener {
            val popup = PopupMenu(this@PayoutHistoryActivity, ivMore)
            popup.setOnMenuItemClickListener(this@PayoutHistoryActivity)
            popup.inflate(R.menu.menu_main)
            popup.show()
        }
        rvDisputeHistory.apply {

            layoutManager = LinearLayoutManager(this@PayoutHistoryActivity)
            aepsHistoryAdapter = AepsPayoutHistoryAdapter(
                context, aepsHistoryModelArrayList
            )
            rvDisputeHistory.adapter = aepsHistoryAdapter
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
                aepsPayoutHistory(
                    userModel.cus_id
                )
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun aepsPayoutHistory(
        cus_id: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, AEPS_PAYOUT_HISTORY, this)
            mAPIcall.aepsPayoutHistory(
                cus_id
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(AEPS_PAYOUT_HISTORY)) {
            aepsHistoryModelArrayList.clear()
            Log.e("AEPS_PAYOUT_HISTORY", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                progress_bar.visibility = View.INVISIBLE


                val cast = jsonObject.getJSONArray("result")

                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val pay_req_id = notifyObjJson.getString("pay_req_id")
                    Log.e("pay_req_id ", pay_req_id)
                    val aepsmodel = Gson()
                        .fromJson(
                            notifyObjJson.toString(),
                            PayoutHistoryModel::class.java
                        )


                    aepsHistoryModelArrayList.add(aepsmodel)
                }

                rvDisputeHistory.adapter!!.notifyDataSetChanged()


            } else {
                progress_bar.visibility = View.INVISIBLE


            }
        }

    }
}