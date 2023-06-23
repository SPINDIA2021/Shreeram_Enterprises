package com.satmatgroup.shreeram.reports.recharge_reports

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.*
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.adapters_recyclerview.StringBottomAdapter
import com.satmatgroup.shreeram.model.RecentRechargeHistoryModal
import com.satmatgroup.shreeram.model.StringModel
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import com.satmatgroup.shreeram.utils.toast
import kotlinx.android.synthetic.main.activity_raise_dispute.*
import kotlinx.android.synthetic.main.activity_raise_dispute.view.*
import kotlinx.android.synthetic.main.layout_list_bottomsheet.view.*
import org.json.JSONObject
import java.util.ArrayList

class RaiseDisputeActivity : AppCompatActivity(), StringBottomAdapter.ListAdapterListener,
    AppApiCalls.OnAPICallCompleteListener {

    lateinit var userModel: UserModel

    lateinit var stringAdapter: StringBottomAdapter
    var stringModelArrayList = ArrayList<StringModel>()

    var bottomSheetDialog: BottomSheetDialog? = null

    private val RAISEDISPUTE: String = "RAISEDISPUTE"

    lateinit var recentRechargeHistoryModal: RecentRechargeHistoryModal


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.black, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_raise_dispute)


        //Toolbar
        custToolbar.ivBackBtn.setOnClickListener { onBackPressed() }

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)


        val bundle = intent.extras
        if (bundle != null) {
            recentRechargeHistoryModal =
                bundle.getSerializable("rechargeDetails") as RecentRechargeHistoryModal
        }


        stringModelArrayList.add(StringModel("Recharge Pending"))
        stringModelArrayList.add(StringModel("Recharge Successful but Service not received"))
        stringModelArrayList.add(StringModel("Refund/Payment issue"))
        stringModelArrayList.add(StringModel("Other"))


        ll_issue.setOnClickListener {
            ShowBottomSheet()
        }

        btnRaiseDispute.setOnClickListener {
            if (tvIssue.text.toString().isEmpty()) {

                toast("Please Select an Issue")

            } else if (etSubject.text.toString().isEmpty()) {

                etSubject.requestFocus()
                etSubject.error = "Please Mention Subject"
            } else {

                raiseDispute(
                    userModel.cus_id,
                    recentRechargeHistoryModal.recid.toString(),
                    tvIssue.text.toString(),
                    etSubject.text.toString(),
                    AppPrefs.getStringPref("deviceId", this).toString(),
                    AppPrefs.getStringPref("deviceName", this).toString(),
                    userModel.cus_pin,
                    userModel.cus_pass,
                    userModel.cus_mobile,
                    userModel.cus_type
                )
            }
        }


    }

    private fun ShowBottomSheet() {
        val view: View = layoutInflater.inflate(R.layout.layout_list_bottomsheet, null)
        view.rvspinner.apply {

            layoutManager = LinearLayoutManager(this@RaiseDisputeActivity)
            stringAdapter = StringBottomAdapter(
                context, stringModelArrayList, this@RaiseDisputeActivity
            )
            view.rvspinner.adapter = stringAdapter
        }

        bottomSheetDialog = BottomSheetDialog(this, R.style.SheetDialog)
        bottomSheetDialog!!.setContentView(view)
        val bottomSheetBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.peekHeight = 600
        bottomSheetDialog!!.show()
    }

    override fun onClickAtOKButton(mobileRechargeModal: StringModel) {

        tvIssue.setText(mobileRechargeModal.ListName)
        bottomSheetDialog!!.dismiss()

    }


    private fun raiseDispute(
        cus_id: String,
        recid: String,
        issue: String,
        subject: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, RAISEDISPUTE, this)
            mAPIcall.raiseDisputeApi(
                cus_id,
                recid,
                issue,
                subject,
                deviceId, deviceName, pin,
                pass, cus_mobile, cus_type
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(RAISEDISPUTE)) {
            e("RAISEDISPUTE", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            e(AppConstants.STATUS, status)
            if (status.contains("true")) {
                progress_bar.visibility = View.INVISIBLE
                toast("Dispute Raised Successfully")
                tvIssue.setText("")
                etSubject.setText("")

            } else {
                progress_bar.visibility = View.INVISIBLE
                toast("Failed to raise dispute")
            }
        }

    }
}

