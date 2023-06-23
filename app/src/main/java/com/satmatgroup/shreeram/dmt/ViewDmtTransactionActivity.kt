package com.satmatgroup.shreeram.dmt

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import kotlinx.android.synthetic.main.activity_view_dmt_transaction.*
import kotlinx.android.synthetic.main.activity_view_dmt_transaction.view.*
import org.json.JSONObject

class ViewDmtTransactionActivity : AppCompatActivity(),
    AppApiCalls.OnAPICallCompleteListener, PopupMenu.OnMenuItemClickListener,
    SwipeRefreshLayout.OnRefreshListener {
    lateinit var dmtHistoryAdapter: DmtHistoryAdapter
    var dmtHistoryModelArrayList = ArrayList<DmtHistoryModel>()
    private val DMTHISTORY_REPORT: String = "DMTHISTORY_REPORT"
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
        setContentView(R.layout.activity_view_dmt_transaction)

        //Toolbar
        custToolbar.ivBackBtn.setOnClickListener { onBackPressed() }


        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)

        //dmtHistory(userModel.cus_id)


        mSwipeRefresh.setOnRefreshListener(this);

        mSwipeRefresh.post(Runnable {
            if (mSwipeRefresh != null) {
                mSwipeRefresh.setRefreshing(true)
            }
            dmtHistoryModelArrayList.clear()
            dmtHistory(userModel.cus_id)

            mSwipeRefresh.setRefreshing(false)

        })

    }

    fun dmtHistory(
        cus_id: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, DMTHISTORY_REPORT, this)
            mAPIcall.dmtHistory(cus_id)
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(DMTHISTORY_REPORT)) {
            dmtHistoryModelArrayList.clear()
            Log.e("DMTHISTORY_REPORT", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                progress_bar.visibility = View.INVISIBLE

                val cast = jsonObject.getJSONArray("message")

                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val dmt_transactions_id = notifyObjJson.getString("dmt_trnx_id")
                    Log.e("dmt_trnx_id ", dmt_transactions_id)
                    val aepsmodel = Gson().fromJson(notifyObjJson.toString(), DmtHistoryModel::class.java)
                    dmtHistoryModelArrayList.add(aepsmodel)
                }
                rvDmtHistory.apply {

                    layoutManager = LinearLayoutManager(this@ViewDmtTransactionActivity)
                    dmtHistoryAdapter =
                        DmtHistoryAdapter(
                            context, dmtHistoryModelArrayList,
                        )
                    rvDmtHistory.adapter = dmtHistoryAdapter
                }

                rvDmtHistory.adapter!!.notifyDataSetChanged()


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
                dmtHistory(
                    userModel.cus_id
                )
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRefresh() {
        dmtHistoryModelArrayList.clear()
        dmtHistory(userModel.cus_id)


        mSwipeRefresh.setRefreshing(false)

    }


}