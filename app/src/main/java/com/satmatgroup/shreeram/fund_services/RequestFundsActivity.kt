package com.satmatgroup.shreeram.fund_services

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserDayBookModel
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import com.satmatgroup.shreeram.utils.toast
import kotlinx.android.synthetic.main.activity_request_funds.*
import kotlinx.android.synthetic.main.activity_request_funds.view.*
import org.json.JSONObject

class RequestFundsActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener {

    private val FUNDREQUEST: String = "FUNDREQUEST"
    private val CHECKSAMEFUNDREQ: String = "CHECKSAMEFUNDREQ"
    lateinit var userModel: UserModel

    lateinit var userDayBook: UserDayBookModel

    var req_to: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.status_bar, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_request_funds)

        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }


        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)

        getBalanceApi(userModel.cus_mobile)

        if (userModel.cus_type.equals("distributor")) {
            tvRequestTo.setText("Request Funds to - " + "Master".toUpperCase())
            req_to = "master"

        } else if (userModel.cus_type.equals("retailer")) {
            tvRequestTo.setText("Request Funds to - " + "Distributor".toUpperCase())
            req_to = "distributor"

        }

        cvFundRequestBtn.setOnClickListener {
            if (req_to.isEmpty()) {


            } else if (etRequestAmount.text.toString().isEmpty()) {
                etRequestAmount.requestFocus()
                etRequestAmount.setError("Invalid Amount")

            } else {

                fundReqeuest(
                    userModel.cus_id,
                    req_to,
                    etRequestAmount.text.toString(),
                    "bank",
                    etRequestAmount.text.toString(),
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

    private fun fundReqeuest(
        cus_id: String,
        req_to: String,
        amount: String,
        bank: String,
        refrenceNumber: String,
        deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, FUNDREQUEST, this)
            mAPIcall.fundRequestApi(
                cus_id,
                req_to,
                amount,
                bank,
                refrenceNumber,
                deviceId,
                deviceName,
                pin,
                pass,
                cus_mobile,
                cus_type
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(FUNDREQUEST)) {
            Log.e("RECHARGE_API", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            //Log.e(AppConstants.MESSAGE_CODE, messageCode);
            if (status.contains("true")) {
                progress_bar.visibility = View.INVISIBLE

                showSuccessRechargeDialog()

            } else {
                progress_bar.visibility = View.INVISIBLE

                showFailedRechargeDialog()
            }
        }

        if (flag.equals(AppConstants.BALANCE_API)) {
            progress_bar.visibility = View.GONE
            Log.e(AppConstants.BALANCE_API, result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val messageCode = jsonObject.getString(AppConstants.MESSAGE)

            //   val token = jsonObject.getString(AppConstants.TOKEN)
            Log.e(AppConstants.STATUS, status)
            Log.e(AppConstants.MESSAGE, messageCode)
            if (status.contains(AppConstants.TRUE)) {

                tvWalletBalance.text =
                    "${getString(R.string.Rupee)} ${jsonObject.getString(AppConstants.WALLETBALANCE)}"
                /* tvAepsBalance.text =
                     "${getString(R.string.Rupee)} ${jsonObject.getString(AEPSBALANCE)}"*/

            } else {
                progress_bar.visibility = View.GONE
                if (messageCode.equals(getString(R.string.error_expired_token))) {
                    AppCommonMethods.logoutOnExpiredDialog(this)
                } else {
                    toast(messageCode.trim())
                }
            }
        }

    }


    private fun showSuccessRechargeDialog() {
        val builder1 =
            AlertDialog.Builder(this)
        builder1.setTitle("Request Status")
        builder1.setMessage("Fund Requested Successfully")
        builder1.setCancelable(false)
        builder1.setPositiveButton(
            "OK"
        ) { dialog, id ->
            etRequestAmount.setText("")
            //ivMobileOperatorImg.setImageResource(R.drawable.ic_network_check_black_24dp)

            /*
               showScratchCard()*/
            dialog.cancel()
            onBackPressed()
        }

        val alert11 = builder1.create()
        alert11.show()
    }

    private fun showFailedRechargeDialog() {
        val builder1 =
            AlertDialog.Builder(this)
        builder1.setTitle("Request Status")
        builder1.setMessage("Fund Requested Failed")
        builder1.setCancelable(false)
        builder1.setPositiveButton(
            "OK"
        ) { dialog, id ->
            etRequestAmount.setText("")
            //ivMobileOperatorImg.setImageResource(R.drawable.ic_network_check_black_24dp)

            /*

               showScratchCard()*/
            dialog.cancel()
        }
        val alert11 = builder1.create()
        alert11.show()
    }


    private fun getBalanceApi(
        cus_id: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                this,
                AppConstants.BALANCE_API,
                this
            )
            mAPIcall.getBalance(cus_id)

        } else {
            toast(getString(R.string.error_internet))
        }
    }

}