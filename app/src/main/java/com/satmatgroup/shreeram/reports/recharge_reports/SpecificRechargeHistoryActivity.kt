package com.satmatgroup.shreeram.reports.recharge_reports

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.myapp.onlysratchapp.category.CategoryResponse
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.adapters_recyclerview.RecentRechargesAdapter
import com.satmatgroup.shreeram.category.CategoryContract
import com.satmatgroup.shreeram.category.CategoryPresenter
import com.satmatgroup.shreeram.model.RecentRechargeHistoryModal
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network.Injection
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.*
import kotlinx.android.synthetic.main.activity_specific_recharge_history.*
import kotlinx.android.synthetic.main.activity_specific_recharge_history.custToolbar
import kotlinx.android.synthetic.main.activity_specific_recharge_history.progress_bar
import kotlinx.android.synthetic.main.activity_specific_recharge_history.rvRechargeHistory
import kotlinx.android.synthetic.main.activity_specific_recharge_history.view.*
import org.json.JSONObject
import java.util.*

class SpecificRechargeHistoryActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener, CategoryContract.View {
    private var presenter: CategoryContract.Presenter? = null
    lateinit var recentRechargesAdapter: RecentRechargesAdapter
    var recentRechargeHistoryModalArrayList = ArrayList<RecentRechargeHistoryModal>()
    private val RECHARGE_HISTORY: String = "RECHARGE_HISTORY"
    lateinit var userModel: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.status_bar, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_specific_recharge_history)


        CategoryPresenter(Injection.provideLoginRepository(this), this)


        //Toolbar
        custToolbar.ivBackBtn.setOnClickListener { onBackPressed() }
        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)



        tvSearchBtn.setOnClickListener {
            if (!etSearch.getText().toString().isEmpty()
                && etDateSearch.getText().toString().isEmpty()
            ) {
                hideKeyboard()
                recentRechargeHistoryModalArrayList =
                    java.util.ArrayList()
                rechargeHistoryByMobile(
                    userModel.cus_id,
                    etSearch.getText().toString(),
                    AppPrefs.getStringPref("deviceId", this).toString(),
                    AppPrefs.getStringPref("deviceName", this).toString(),
                    userModel.cus_pin,
                    userModel.cus_pass,
                    userModel.cus_mobile, userModel.cus_type
                )
                //filterNumber(etSearch.getText().toString());
            }
            if (!etDateSearch.getText().toString().isEmpty()
                && etSearch.getText().toString().isEmpty()) {
                recentRechargeHistoryModalArrayList = java.util.ArrayList()
                etSearch.setText("")
                rechargeHistoryByDate(
                    userModel.cus_id,
                    AppCommonMethods.convertDateFormat("dd/MM/yyyy",
                        "yyyy-MM-dd", etDateSearch.text.toString()).toString(),
                    AppPrefs.getStringPref("deviceId", this).toString(),
                    AppPrefs.getStringPref("deviceName", this).toString(),
                    userModel.cus_pin,
                    userModel.cus_pass,
                    userModel.cus_mobile, userModel.cus_type
                )

                //filterDate(etDateSearch.getText().toString());
            }
        }

        etDateSearch.setOnClickListener {
            etSearch.setText("")
            TodatePicker() }

    }

    private fun rechargeHistoryByMobile(
        cus_id: String, mobile: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {
        etDateSearch.setText("")
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, RECHARGE_HISTORY, this)
            mAPIcall.rechargeHistoryByMobile(
                cus_id, mobile, deviceId, deviceName, pin,
                pass, cus_mobile, cus_type
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun rechargeHistoryByDate(
        cus_id: String, date: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {
        etSearch.setText("")
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, RECHARGE_HISTORY, this)
            mAPIcall.rechargeHistoryByDate(
                cus_id, date, deviceId, deviceName, pin,
                pass, cus_mobile, cus_type
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(RECHARGE_HISTORY)) {
            recentRechargeHistoryModalArrayList.clear()
            Log.e("RECHARGE_HISTORY", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                progress_bar.visibility = View.INVISIBLE

                val cast = jsonObject.getJSONArray("result")

                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val recid = notifyObjJson.getString("recid")
                    Log.e("recid ", recid)
                    val rechargeHistoryModal = Gson()
                        .fromJson(
                            notifyObjJson.toString(),
                            RecentRechargeHistoryModal::class.java
                        )


                    recentRechargeHistoryModalArrayList.add(rechargeHistoryModal)
                }

                rvRechargeHistory.apply {

                    layoutManager = LinearLayoutManager(this@SpecificRechargeHistoryActivity)
                    recentRechargesAdapter = RecentRechargesAdapter(
                        context, recentRechargeHistoryModalArrayList, retryClick
                    )
                    rvRechargeHistory.adapter = recentRechargesAdapter
                }


            } else {
                recentRechargeHistoryModalArrayList.clear()
                rvRechargeHistory.apply {

                    layoutManager = LinearLayoutManager(this@SpecificRechargeHistoryActivity)
                    recentRechargesAdapter = RecentRechargesAdapter(
                        context, recentRechargeHistoryModalArrayList,retryClick
                    )
                    rvRechargeHistory.adapter = recentRechargesAdapter
                }
                progress_bar.visibility = View.INVISIBLE


            }
        }

    }

    fun TodatePicker() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var dpd =
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val mmMonth = mMonth + 1
                val date = "$mDay/$mmMonth/$mYear"
                etDateSearch.setText(date)

            }, year, month, day)
        dpd.show()
    }

    var retryClick = View.OnClickListener { v ->
        val i = v.tag as Int

        var txnId=recentRechargeHistoryModalArrayList.get(i).requestertxnid
        presenter!!.saveRetry(txnId, this)
    }


    override fun setPresenter(presenter: CategoryContract.Presenter?) {
        this.presenter = presenter
    }

    override fun categoryResponse(categoryResponse: ArrayList<CategoryResponse>?) {
        TODO("Not yet implemented")
    }

    override fun retryResponse(retryResponse: String?) {
        showSuccessOrFailedDialog("Recharge Status",retryResponse!!)
    }


    private fun showSuccessOrFailedDialog(title: String, message: String) {
        val builder1 =
            AlertDialog.Builder(this)
        builder1.setTitle("" + title)
        builder1.setMessage("" + message)
        builder1.setCancelable(false)
        builder1.setPositiveButton(
            "OK"
        ) { dialog, id ->
            dialog.dismiss()
        }
        /*
        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });*/
        val alert11 = builder1.create()
        alert11.show()
    }


}