package com.satmatgroup.shreeram.reports.recharge_reports

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.satmatgroup.shreeram.model.FundRequestModel
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.adapters_recyclerview.FundRequestReportAdapter

import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import kotlinx.android.synthetic.main.activity_fund_request_report.*
import kotlinx.android.synthetic.main.activity_fund_request_report.view.*
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FundRequestReportActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener,
    AppApiCalls.OnAPICallCompleteListener {

    lateinit var fundRequestReportAdapter: FundRequestReportAdapter
    var fundRequestReportArraylist = ArrayList<FundRequestModel>()
    private val LEDGER_REPORT: String = "LEDGER_REPORT"
    lateinit var userModel: UserModel

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fund_request_report)



        //Toolbar
        custToolbar.ivBackBtn.setOnClickListener { onBackPressed() }


        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)
        val date = getCurrentDateTime()
        val dateInString = date.toString("dd/MM/yyyy")
        tvSelectFromDate.text = dateInString
        tvSelectToDate.text = dateInString
        fundRequestHistory(
            userModel.cus_id, AppCommonMethods.convertDateFormat(
                "dd/MM/yyyy", "yyyy-MM-dd", tvSelectFromDate.text.toString()
            ).toString(), AppCommonMethods.convertDateFormat(
                "dd/MM/yyyy", "yyyy-MM-dd", tvSelectToDate.text.toString()
            ).toString(),
            AppPrefs.getStringPref("deviceId", this).toString(),
            AppPrefs.getStringPref("deviceName", this).toString(),
            userModel.cus_pin,
            userModel.cus_pass,
            userModel.cus_mobile, userModel.cus_type


        )

        custToolbar.ivMore.setOnClickListener {
            val popup = PopupMenu(this@FundRequestReportActivity, ivMore)
            popup.setOnMenuItemClickListener(this@FundRequestReportActivity)
            popup.inflate(R.menu.menu_main)
            popup.show()
        }
        rvFundRequestReport.apply {

            layoutManager = LinearLayoutManager(this@FundRequestReportActivity)
            fundRequestReportAdapter = FundRequestReportAdapter(
                context, fundRequestReportArraylist
            )
            rvFundRequestReport.adapter = fundRequestReportAdapter
        }

        tvSelectFromDate.setOnClickListener {
            FromdatePicker()
        }
        tvSelectToDate.setOnClickListener {
            TodatePicker()
        }


        btnGo.setOnClickListener {
            compareTwoDates(tvSelectFromDate.text.toString(), tvSelectToDate.text.toString())
        }

    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun FromdatePicker() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var dpd =
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val mmMonth = mMonth + 1
                val date = "$mDay/$mmMonth/$mYear"
                tvSelectFromDate.text = date


            }, year, month, day)
        dpd.show()
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
                tvSelectToDate.text = date

            }, year, month, day)
        dpd.show()
    }

    private fun fundRequestHistory(
        cus_id: String, fromdate: String, todate: String,
        deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, LEDGER_REPORT, this)
            mAPIcall.fundRequestHistory(cus_id, fromdate, todate,deviceId, deviceName, pin,
                pass, cus_mobile, cus_type)
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(LEDGER_REPORT)) {
            fundRequestReportArraylist.clear()
            Log.e("RECHARGE_HISTORY", result)
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
                    val fundRequestModel = Gson()
                        .fromJson(
                            notifyObjJson.toString(),
                            FundRequestModel::class.java
                        )


                    fundRequestReportArraylist.add(fundRequestModel)
                }

                rvFundRequestReport.adapter!!.notifyDataSetChanged()


            } else {
                progress_bar.visibility = View.INVISIBLE


            }
        }
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }


    fun compareTwoDates(date: String, dateafter: String) {


        val dateFormat = SimpleDateFormat(
            "dd/MM/yyyy"
        )
        var convertedDate: Date? = Date()
        var convertedDate2 = Date()
        try {
            convertedDate = dateFormat.parse(date)
            convertedDate2 = dateFormat.parse(dateafter)
            if (convertedDate2.after(convertedDate) || convertedDate2.equals(convertedDate)) {
                fundRequestReportArraylist = ArrayList()
                fundRequestHistory(
                    userModel.cus_id, AppCommonMethods.convertDateFormat(
                        "dd/MM/yyyy", "yyyy-MM-dd", date
                    ).toString(), AppCommonMethods.convertDateFormat(
                        "dd/MM/yyyy", "yyyy-MM-dd", dateafter
                    ).toString(),
                    AppPrefs.getStringPref("deviceId", this).toString(),
                    AppPrefs.getStringPref("deviceName", this).toString(),
                    userModel.cus_pin,
                    userModel.cus_pass,
                    userModel.cus_mobile, userModel.cus_type
                )
                rvFundRequestReport.apply {

                    layoutManager = LinearLayoutManager(this@FundRequestReportActivity)
                    fundRequestReportAdapter = FundRequestReportAdapter(
                        context, fundRequestReportArraylist
                    )
                    rvFundRequestReport.adapter = fundRequestReportAdapter
                }

            } else {
                AppCommonMethods.onSNACK(rl_main, "Invalid date")
            }
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
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
                fundRequestHistory(
                    userModel.cus_id, AppCommonMethods.convertDateFormat(
                        "dd/MM/yyyy", "yyyy-MM-dd", tvSelectFromDate.text.toString()
                    ).toString(), AppCommonMethods.convertDateFormat(
                        "dd/MM/yyyy", "yyyy-MM-dd", tvSelectToDate.text.toString()
                    ).toString(),
                    AppPrefs.getStringPref("deviceId", this).toString(),
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