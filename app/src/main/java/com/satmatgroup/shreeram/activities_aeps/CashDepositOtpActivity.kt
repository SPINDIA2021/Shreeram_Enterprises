package com.satmatgroup.shreeram.activities_aeps

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.AepsBankModel
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.BankAepsListAdapter
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.MantraDeviceActivity
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import com.satmatgroup.shreeram.utils.toast
import kotlinx.android.synthetic.main.activity_aeps_transaction.*
import kotlinx.android.synthetic.main.activity_aeps_transaction.custToolbar
import kotlinx.android.synthetic.main.activity_aeps_transaction.progress_bar
import kotlinx.android.synthetic.main.activity_aeps_transaction.tvSelectBank
import kotlinx.android.synthetic.main.activity_aeps_transaction.view.*
import kotlinx.android.synthetic.main.activity_aeps_transaction.view.ivBackBtn
import kotlinx.android.synthetic.main.activity_buy_or_book.*
import kotlinx.android.synthetic.main.activity_cash_deposit_otp.*
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.layout_dialog_confirmotp.*
import kotlinx.android.synthetic.main.layout_list_bottomsheet_banklist.view.*
import org.json.JSONObject

class CashDepositOtpActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener,
    BankAepsListAdapter.ListAdapterListener {

    var bottomSheetDialogUsers: BottomSheetDialog? = null
    lateinit var bankListAdapter: BankAepsListAdapter

    var bankListModelArrayList = ArrayList<AepsBankModel>()
    private val AEPS_BANKS: String = "AEPS_BANKS"
    private val CASH_DEPOSIT: String = "CASH_DEPOSIT"
    private val VALIDATE_CASH_DEPOSIT: String = "VALIDATE_CASH_DEPOSIT"
    lateinit var userModel: UserModel
    lateinit var dialog: Dialog
    lateinit var fingpayTransactionId: String
    lateinit var cdPkId: String

    lateinit var nationalBankIdenticationNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_deposit_otp)

        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)

        tvSelectBankCashDeposit.setOnClickListener {
            bankListAeps()
        }

        btnSubmitCash.setOnClickListener {


             if (!AppCommonMethods.checkForMobile(etCashDepositMobile)) {
                etCashDepositMobile.requestFocus()
                etCashDepositMobile.error = "Invalid Mobile"

            } else if (tvSelectBankCashDeposit.text.toString().isNullOrEmpty()) {

                tvSelectBankCashDeposit.requestFocus()
                tvSelectBankCashDeposit.error = "Please Select Bank"
            } else if (etBankAcc.text.toString().isNullOrEmpty()) {

                 etBankAcc.requestFocus()
                 etBankAcc.error = "Please Enter Bank Account Number"
             } else if (etCashDepositAmount.text.toString().isNullOrEmpty()) {

                 etCashDepositAmount.requestFocus()
                 etCashDepositAmount.error = "Please Enter Bank Account Number"
             } else {

                cashDepositWithOtp(etCashDepositMobile.text.toString(),
                    etBankAcc.text.toString(),
                    nationalBankIdenticationNumber,
                    etCashDepositAmount.text.toString(),
                    userModel.cus_id)


            }
        }
    }

    fun bankListAeps(
    ) {
        progress_bar_cash.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, AEPS_BANKS, this)
            mAPIcall.bankListAeps()
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cashDepositWithOtp(
        cdmobileNumber: String,
        cdAcctNumber: String,
        cdnationalBankIdenticationNumber: String,
        cdtransactionAmount: String,
        cus_id: String
    ) {
        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                this,
                CASH_DEPOSIT,
                this
            )
            mAPIcall.cashDepositWithOtp(
                cdmobileNumber,
                cdAcctNumber,
                cdnationalBankIdenticationNumber,
                cdtransactionAmount,
                cus_id
            )
            progress_bar_cash.visibility = View.VISIBLE
        } else {
            Toast.makeText(
                this,
                "Internet Error",
                Toast.LENGTH_SHORT
            )
        }
    }

    private fun validateCashDepositWithOtp(
        cdnationalBankIdenticationNumber: String,
        cdmobileNumber: String,
        cdtransactionAmount: String,
        cdAcctNumber: String,
        fingpayTransactionId: String,
        cdPkId: String,
        otp: String,
        cus_id: String
    ) {
        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                this,
                VALIDATE_CASH_DEPOSIT,
                this
            )
            mAPIcall.validateCashDepositOtp(
                cdnationalBankIdenticationNumber,
                cdmobileNumber,
                cdtransactionAmount,
                cdAcctNumber,
                fingpayTransactionId,
                cdPkId,
                otp,
                cus_id
            )
            progress_bar_cash.visibility = View.VISIBLE
        } else {
            Toast.makeText(
                this,
                "Internet Error",
                Toast.LENGTH_SHORT
            )
        }
    }

    private fun ShowBottomSheetBankList() {
        val view: View = layoutInflater.inflate(R.layout.layout_list_bottomsheet_banklist, null)
        view.etSearchMobName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable?) {

                filter(s.toString());

            }
        })


        view.rvBankList.apply {

            layoutManager = LinearLayoutManager(this@CashDepositOtpActivity)
            bankListAdapter = BankAepsListAdapter(
                context, bankListModelArrayList, this@CashDepositOtpActivity
            )
            view.rvBankList.adapter = bankListAdapter
        }
        bottomSheetDialogUsers = BottomSheetDialog(this)
        bottomSheetDialogUsers!!.setContentView(view)

        val bottomSheetBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        bottomSheetDialogUsers!!.show()
    }

    override fun onClickAtOKButton(mobileRechargeModal: AepsBankModel?) {

        if (mobileRechargeModal != null) {
            tvSelectBankCashDeposit.setText(mobileRechargeModal.bankName)
            nationalBankIdenticationNumber = mobileRechargeModal.iinno
        }

        bottomSheetDialogUsers!!.dismiss()
    }

    fun filter(text: String) {
        val temp: MutableList<AepsBankModel> = ArrayList()
        for (d in bankListModelArrayList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.bankName.contains(text, ignoreCase = true)) {

                temp.add(d)
            }
        }
        //update recyclerview
        bankListAdapter.updateList(temp)
    }

    fun confirmOtp(fingpayTransactionId: String, cdPkId: String) {
        dialog = Dialog(this, R.style.ThemeOverlay_MaterialComponents_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_dialog_confirmotp)

        dialog.etOtp.requestFocus()
        dialog.tvDialogCancel.setOnClickListener {
            dialog.dismiss()
        }


        dialog.getWindow()!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        dialog.tvConfirmOtp.setOnClickListener {
            if (dialog.etOtp.text.toString().isNullOrEmpty()) {
                dialog.etOtp.requestFocus()
                dialog.etOtp.setError("Please Enter Valid OTP")
            } else {
                validateCashDepositWithOtp(nationalBankIdenticationNumber,
                    etCashDepositMobile.text.toString(),
                    etCashDepositAmount.text.toString(),
                    etBankAcc.text.toString(),
                    fingpayTransactionId,
                    cdPkId,
                    dialog.etOtp.text.toString(),
                    userModel.cus_id
                )

                dialog.dismiss()

            }

        }

        dialog.show()
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(AEPS_BANKS)) {
            bankListModelArrayList.clear()
            Log.e("AEPS_BANKS", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                progress_bar_cash.visibility = View.INVISIBLE

                val cast = jsonObject.getJSONArray("result")

                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val aeps_bank_id = notifyObjJson.getString("aeps_bank_id")
                    Log.e("aeps_bank_id ", aeps_bank_id)
                    val bankListModel = Gson()
                        .fromJson(
                            notifyObjJson.toString(),
                            AepsBankModel::class.java
                        )


                    bankListModelArrayList.add(bankListModel)
                }

                ShowBottomSheetBankList()

            } else {
                progress_bar_cash.visibility = View.INVISIBLE
            }
        }

        if(flag.equals(CASH_DEPOSIT)) {
            Log.e("CASH_DEPOSIT", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val message = jsonObject.getString("message")
            Log.e(AppConstants.STATUS, status)


            if (status.contains("true")) {

                progress_bar_cash.visibility = View.INVISIBLE
                val cast = jsonObject.getJSONObject("result")
                fingpayTransactionId = cast.getString("fingpayTransactionId")
                cdPkId = cast.getString("cdPkId")

                confirmOtp(fingpayTransactionId, cdPkId)

                toast(message)

            } else {
                toast(message)
                progress_bar_cash.visibility = View.INVISIBLE
            }
        }

        if(flag.equals(VALIDATE_CASH_DEPOSIT)) {
            Log.e("VALIDATE_CASH_DEPOSIT", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val message = jsonObject.getString("message")
            Log.e(AppConstants.STATUS, status)
            //val result = JSONObject("result")

            if (status.contains("true")) {

                toast(message)
                progress_bar_cash.visibility = View.INVISIBLE

            } else {
                toast(message)
                progress_bar_cash.visibility = View.INVISIBLE
            }
        }
    }
}