package com.satmatgroup.shreeram.dmt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import kotlinx.android.synthetic.main.activity_add_beneficiary.*
import kotlinx.android.synthetic.main.activity_add_beneficiary.progress_bar
import kotlinx.android.synthetic.main.activity_dmt_benificiary.*
import kotlinx.android.synthetic.main.activity_dmt_benificiary.rvRecipeintList
import kotlinx.android.synthetic.main.activity_dmt_benificiary.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_select_dmt.*
import org.json.JSONObject

class SelectDmtActivity : AppCompatActivity(), SelectDmtAdapter.ListAdapterListener,
    AppApiCalls.OnAPICallCompleteListener {

    lateinit var selectDmtAdapter: SelectDmtAdapter
    lateinit var dmtService: String

    private val DMT_SERVICE: String = "DMT_SERVICE"
    var bankListModelArrayList = ArrayList<SelectDmtModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_dmt)

        dmtService()

        rvSelectDmtService.apply {
            layoutManager = LinearLayoutManager(this@SelectDmtActivity)
            selectDmtAdapter = SelectDmtAdapter(context, bankListModelArrayList, this@SelectDmtActivity)
            rvSelectDmtService.adapter = selectDmtAdapter
        }

    }

    fun dmtService(
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, DMT_SERVICE, this)
            mAPIcall.getDmtService()
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(DMT_SERVICE)) {
            bankListModelArrayList.clear()

            Log.e("DMT_SERVICE", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                progress_bar.visibility = View.INVISIBLE

                val cast = jsonObject.getJSONArray("message")

                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    Log.e("TAG",notifyObjJson.getString("name"))
                    val selectDmtModel = Gson()
                        .fromJson(
                            notifyObjJson.toString(),
                            SelectDmtModel::class.java
                        )
                    bankListModelArrayList.add(selectDmtModel)
                }

                rvSelectDmtService.adapter!!.notifyDataSetChanged()

            } else {
                tvFalse.visibility = View.VISIBLE
                progress_bar.visibility = View.INVISIBLE
            }
        }
    }

    override fun onClickAtOKButton(selectDmtModel: SelectDmtModel?) {
        if (selectDmtModel != null) {
            dmtService = selectDmtModel.name
        }
        Log.e("Name",dmtService)
        AppPrefs.putStringPref("DmtName", dmtService, this)
    }
}