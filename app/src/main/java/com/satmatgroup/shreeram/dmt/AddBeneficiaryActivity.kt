package com.satmatgroup.shreeram.dmt

import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import com.satmatgroup.shreeram.utils.toast
import kotlinx.android.synthetic.main.activity_add_beneficiary.*
import kotlinx.android.synthetic.main.activity_add_beneficiary.view.*
import kotlinx.android.synthetic.main.layout_list_bottomsheet.view.*
import kotlinx.android.synthetic.main.layout_list_bottomsheet_banklist.*
import kotlinx.android.synthetic.main.layout_list_bottomsheet_banklist.view.*
import kotlinx.android.synthetic.main.layout_list_bottomsheet_users.view.etSearchMobName
import org.json.JSONObject

class AddBeneficiaryActivity : AppCompatActivity(), BankListAdapter.ListAdapterListener,
    AppApiCalls.OnAPICallCompleteListener {
    var bottomSheetDialogUsers: BottomSheetDialog? = null
    lateinit var bankListAdapter: BankListAdapter

    var bankListModelArrayList = ArrayList<BankListModel>()
    private val RECIPIENT_LIST: String = "RECIPIENT_LIST"
    private val ADD_BENEFICIARY = "ADD_BENEFICIARY"
    private val VERIFY_ACCOUNT = "VERIFY_ACCOUNT"

    lateinit var bank_code: String

    lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_beneficiary)

        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)

        etBeneficiaryBank.setOnClickListener {
            bankList()
        }

        etMobileNumber.setText(AppPrefs.getStringPref("dmtMobile",this).toString())

        tvVerifyAccountBtn.setOnClickListener {
            if (etBeneficiaryBank.text.toString().isEmpty()) {

                etBeneficiaryBank.requestFocus()
                etBeneficiaryBank.setError("Invalid Bank Name")

            } else if (etBenefAadharNo.text.toString()
                    .isNullOrEmpty()
            ) {

                etBenefAadharNo.requestFocus()
                etBenefAadharNo.error = "Invalid Aadhar"
            } else if (etBenefPanNo.text.toString().isEmpty()) {
                etBenefPanNo.requestFocus()
                etBenefPanNo.error = "Invalid Pan"

            } else if (etBenifAccnNo.text.toString().isEmpty()) {
                etBenifAccnNo.requestFocus()
                etBenifAccnNo.setError("Invalid Bank Account")

            } else if (etConfirmAccount.text.toString().isEmpty()) {
                etConfirmAccount.requestFocus()
                etConfirmAccount.setError("Invalid Bank Account")

            } else if (!AppCommonMethods.checkForMobile(etBeneficiaryMobile)) {
                etBeneficiaryMobile.requestFocus()
                etBeneficiaryMobile.setError("Invalid Mobile Number")

            } else {

                verifyBankAccount(
                    etBenefAadharNo.text.toString(),
                    etBenefPanNo.text.toString(), etBeneficiaryMobile.text.toString(),
                    etConfirmAccount.text.toString(), bank_code
                )

            }
        }

        btnAddBeneficiary.setOnClickListener {
            if (etBeneficiaryName.text.toString().isEmpty()) {
                etBeneficiaryName.requestFocus()
                etBeneficiaryName.setError("Invalid Beneficiary Name")
            } else if (etBenifAccnNo.text.toString().isEmpty()) {
                etBenifAccnNo.requestFocus()
                etBenifAccnNo.setError("Invalid Bank Account")

            } else if (etMobileNumber.text.toString().isEmpty()) {
                etMobileNumber.requestFocus()
                etMobileNumber.setError("Invalid Bank Account")

            } else if (etConfirmAccount.text.toString().isEmpty()) {
                etConfirmAccount.requestFocus()
                etConfirmAccount.setError("Invalid Bank Account")

            } else if (etBenifIFSCNo.text.toString().isEmpty()) {

                etBenifIFSCNo.requestFocus()
                etBenifIFSCNo.setError("Invalid Bank IFSC")

            } else if (etBeneficiaryBank.text.toString().isEmpty()) {

                etBeneficiaryBank.requestFocus()
                etBeneficiaryBank.setError("Invalid Bank Name")

            } else {

                addBeneficiary(
                    etBeneficiaryMobile.text.toString(),
                    etMobileNumber.text.toString(),
                    etConfirmAccount.text.toString(),
                    etBenifIFSCNo.text.toString(),
                    etBeneficiaryName.text.toString(),
                    bank_code
                )
            }
        }

        etConfirmAccount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {


            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (etBenifAccnNo.text.toString()
                        .equals(etConfirmAccount.text.toString())
                ) {
                    ivAccNumberConfirm.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_check_circle_24))
                } else {


                    ivAccNumberConfirm.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_cancel))


                }
            }

        })
    }


    fun bankList(
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, RECIPIENT_LIST, this)
            mAPIcall.bankListDmt()
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
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

            layoutManager = LinearLayoutManager(this@AddBeneficiaryActivity)
            bankListAdapter = BankListAdapter(
                context, bankListModelArrayList, this@AddBeneficiaryActivity
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

    override fun onClickAtOKButton(bankListModel: BankListModel?) {

        if (bankListModel != null) {
            etBeneficiaryBank.setText(bankListModel.bankname)
            bank_code = bankListModel.bank_id
        }
        bottomSheetDialogUsers?.dismiss()


    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {


        if (flag.equals(RECIPIENT_LIST)) {
            bankListModelArrayList.clear()

            Log.e("RECIPIENT_LIST", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                progress_bar.visibility = View.INVISIBLE

                val cast = jsonObject.getJSONArray("message")

                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)

                    val bankListModel = Gson()
                        .fromJson(
                            notifyObjJson.toString(),
                            BankListModel::class.java
                        )


                    bankListModelArrayList.add(bankListModel)
                }

                ShowBottomSheetBankList()

            } else {
                progress_bar.visibility = View.INVISIBLE


            }
        }
        if (flag.equals(ADD_BENEFICIARY)) {
            Log.e("ADD_BENEFICIARY", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
         //   val result = jsonObject.getString(AppConstants.RESULT)

            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {
                progress_bar.visibility = View.INVISIBLE
                val cast = jsonObject.getJSONObject("message")
                val status1 = cast.getString("status")

                if(status1.contains("ERR")) {
                    Toast.makeText(this, cast.getString("api_msg"), Toast.LENGTH_SHORT).show()
                }
                else if(status1.contains("TXN")) {
                    Toast.makeText(this, cast.getString("api_msg"), Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            } else {
                progress_bar.visibility = View.INVISIBLE
            }
        }

        if (flag.equals(VERIFY_ACCOUNT)) {
            Log.e("VERIFY_ACCOUNT", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
         //   val result = jsonObject.getString(AppConstants.RESULT)

            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                progress_bar.visibility = View.INVISIBLE
//                toast("Beneficiary Added Successfully")
                onBackPressed()

            } else {
                progress_bar.visibility = View.INVISIBLE
//                toast("Beneficiary Adding Failed")


            }
        }


    }

    fun filter(text: String) {
        val temp: MutableList<BankListModel> = ArrayList()
        for (d in bankListModelArrayList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.bankname.contains(text, ignoreCase = true)) {
                temp.add(d)
            }
        }
        //update recyclerview
        bankListAdapter.updateList(temp)
    }


    private fun addBeneficiary(
        dmtMobile: String,
        cusNumber: String,
        bank_acct: String,
        ifsc: String,
        name: String,
        bankname: String

    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, ADD_BENEFICIARY, this)
            mAPIcall.addBeneficiaryAccount(
                dmtMobile,
                cusNumber,
                bank_acct,
                ifsc,
                name,
                bankname
            )
        } else {
            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyBankAccount(
        adhar_number: String,
        pan_number: String,
        mobile_number: String,
        account_number: String,
        bank_code: String,

        ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, VERIFY_ACCOUNT, this)
            mAPIcall.verifBenefAccount(
                adhar_number,
                pan_number,
                mobile_number,
                account_number,
                bank_code
            )
        } else {
            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }
}