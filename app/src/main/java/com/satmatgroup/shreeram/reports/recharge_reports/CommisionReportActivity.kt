package com.satmatgroup.shreeram.reports.recharge_reports

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.adapters_recyclerview.CommisionReportAdapter
import com.satmatgroup.shreeram.model.RecentRechargeHistoryModal
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import kotlinx.android.synthetic.main.activity_commision_report.*
import kotlinx.android.synthetic.main.activity_commision_report.btnGo
import kotlinx.android.synthetic.main.activity_commision_report.custToolbar
import kotlinx.android.synthetic.main.activity_commision_report.ivMore
import kotlinx.android.synthetic.main.activity_commision_report.progress_bar
import kotlinx.android.synthetic.main.activity_commision_report.rl_main
import kotlinx.android.synthetic.main.activity_commision_report.tvSelectFromDate
import kotlinx.android.synthetic.main.activity_commision_report.tvSelectToDate
import kotlinx.android.synthetic.main.activity_commision_report.view.*
import kotlinx.android.synthetic.main.activity_commision_report.view.ivBackBtn
import kotlinx.android.synthetic.main.activity_commision_report.view.ivMore
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CommisionReportActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener,
    PopupMenu.OnMenuItemClickListener {
    lateinit var recentRechargesAdapter: CommisionReportAdapter
    var recentRechargeHistoryModalArrayList = ArrayList<RecentRechargeHistoryModal>()
    private val COMMISSION_REPORT: String = "COMMISSION_REPORT"

    lateinit var userModel: UserModel

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.black, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_commision_report)
        //Toolbar
        custToolbar.ivBackBtn.setOnClickListener { onBackPressed() }


        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)
        val date = getCurrentDateTime()
        val dateInString = date.toString("dd/MM/yyyy")
        tvSelectFromDate.setText(dateInString)
        tvSelectToDate.setText(dateInString)
        commisionApi(
            userModel.cus_id, AppCommonMethods.convertDateFormat(
                "dd/MM/yyyy", "yyyy-MM-dd", tvSelectFromDate.text.toString()
            ).toString(), AppCommonMethods.convertDateFormat(
                "dd/MM/yyyy", "yyyy-MM-dd", tvSelectToDate.text.toString()
            ).toString(),AppPrefs.getStringPref("deviceId", this).toString(),
            AppPrefs.getStringPref("deviceName",this ).toString(),
            userModel.cus_pin,
            userModel.cus_pass,
            userModel.cus_mobile,userModel.cus_type


        )
        custToolbar.ivMore.setOnClickListener {
            val popup = PopupMenu(this@CommisionReportActivity, ivMore)
            popup.setOnMenuItemClickListener(this@CommisionReportActivity)
            popup.inflate(R.menu.menu_main)
            popup.show()
        }
        rvCommisionReport.apply {

            layoutManager = LinearLayoutManager(this@CommisionReportActivity)
            recentRechargesAdapter = CommisionReportAdapter(
                context, recentRechargeHistoryModalArrayList
            )
            rvCommisionReport.adapter = recentRechargesAdapter
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
                tvSelectFromDate.setText(date)


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
                tvSelectToDate.setText(date)

            }, year, month, day)
        dpd.show()
    }

    private fun commisionApi(
        cus_id: String, fromdate: String, todate: String, deviceId:String,deviceName :String,pin :String,
        pass : String,cus_mobile : String,cus_type :String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, COMMISSION_REPORT, this)
            mAPIcall.rechargeHistory(cus_id, fromdate, todate,deviceId ,deviceName,pin,
                pass,cus_mobile,cus_type)
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(COMMISSION_REPORT)) {
            Log.e("COMMISSION_REPORT", result)
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

                rvCommisionReport.adapter!!.notifyDataSetChanged()


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
            if (convertedDate2.after(convertedDate)|| convertedDate2.equals(convertedDate)) {
                recentRechargeHistoryModalArrayList = ArrayList()
                commisionApi(
                    userModel.cus_id, AppCommonMethods.convertDateFormat(
                        "dd/MM/yyyy", "yyyy-MM-dd", date
                    ).toString(), AppCommonMethods.convertDateFormat(
                        "dd/MM/yyyy", "yyyy-MM-dd", dateafter
                    ).toString(),
                    AppPrefs.getStringPref("deviceId", this).toString(),
                    AppPrefs.getStringPref("deviceName",this ).toString(),
                    userModel.cus_pin,
                    userModel.cus_pass,
                    userModel.cus_mobile,userModel.cus_type

                )
                rvCommisionReport.apply {

                    layoutManager = LinearLayoutManager(this@CommisionReportActivity)
                    recentRechargesAdapter = CommisionReportAdapter(
                        context, recentRechargeHistoryModalArrayList
                    )
                    rvCommisionReport.adapter = recentRechargesAdapter
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
                commisionApi(
                    userModel.cus_id, AppCommonMethods.convertDateFormat(
                        "dd/MM/yyyy", "yyyy-MM-dd", tvSelectFromDate.text.toString()
                    ).toString(), AppCommonMethods.convertDateFormat(
                        "dd/MM/yyyy", "yyyy-MM-dd", tvSelectToDate.text.toString()
                    ).toString(),
                    AppPrefs.getStringPref("deviceId", this).toString(),
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