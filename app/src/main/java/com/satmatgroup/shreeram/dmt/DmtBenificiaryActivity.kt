package com.satmatgroup.shreeram.dmt

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import com.satmatgroup.shreeram.utils.toast
import kotlinx.android.synthetic.main.activity_dmt_benificiary.*
import kotlinx.android.synthetic.main.activity_dmt_benificiary.custToolbar
import kotlinx.android.synthetic.main.activity_dmt_benificiary.rvRecipeintList
import kotlinx.android.synthetic.main.activity_dmt_benificiary.view.*
import kotlinx.android.synthetic.main.activity_login.progress_bar
import kotlinx.android.synthetic.main.layout_addbeneficiary_dialog.*
import kotlinx.android.synthetic.main.layout_addbeneficiary_dialog.btnAddBeneficiary
import kotlinx.android.synthetic.main.layout_addbeneficiary_dialog.etBeneficiaryBank
import kotlinx.android.synthetic.main.layout_addbeneficiary_dialog.etBeneficiaryMobile
import kotlinx.android.synthetic.main.layout_addbeneficiary_dialog.etBeneficiaryName
import kotlinx.android.synthetic.main.layout_addbeneficiary_dialog.etBenifAccnNo
import kotlinx.android.synthetic.main.layout_addbeneficiary_dialog.etBenifIFSCNo
import kotlinx.android.synthetic.main.layout_addbeneficiary_dialog.etConfirmAccount
import kotlinx.android.synthetic.main.layout_addbeneficiary_dialog.ivAccNumberConfirm
import org.json.JSONObject

class DmtBenificiaryActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener {
    lateinit var dmtMobile: String
    lateinit var dialog: Dialog
    private val ADD_BENEFICIARY = "ADD_BENEFICIARY"
    private val RECIPIENT_LIST: String = "RECIPIENT_LIST"

    lateinit var recipientsAdapter: RecipientsAdapter
    var recipientsArray = ArrayList<RecipientModel>()

    lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dmt_benificiary)

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)

        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }

        var bundle = intent.extras
        dmtMobile = bundle!!.getString("dmtMobile").toString()

        btnAddBenif.setOnClickListener {
            val intent = Intent(this,AddBeneficiaryActivity::class.java)
            startActivity(intent)
            //showBenifDialog(this, dmtMobile)
        }

        recipientList(dmtMobile)

        rvRecipeintList.apply {
            layoutManager = LinearLayoutManager(this@DmtBenificiaryActivity)
            recipientsAdapter = RecipientsAdapter(context, recipientsArray)
            rvRecipeintList.adapter = recipientsAdapter
        }

    }

    fun showBenifDialog(
        mContext: Context,
        dmtMobile: String
    ) {
        dialog = Dialog(mContext, android.R.style.Theme_Translucent_NoTitleBar)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_addbeneficiary_dialog)
        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window!!.attributes = wlp
        dialog.window!!.setLayout(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT
        )
        dialog.etConfirmAccount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (dialog.etBenifAccnNo.text.toString().equals(dialog.etConfirmAccount.text.toString())) {
                    dialog.ivAccNumberConfirm.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_check_circle_24))
                } else {
                    dialog.ivAccNumberConfirm.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_cancel))
                }
            }

        })

        dialog.btnAddBeneficiary.setOnClickListener {
            if (dialog.etBeneficiaryName.text.toString().isEmpty()) {
                dialog.etBeneficiaryName.requestFocus()
                dialog.etBeneficiaryName.setError("Invalid Recipient Name")
            } else if (!AppCommonMethods.checkForMobile(dialog.etBeneficiaryMobile)) {
                dialog.etBeneficiaryMobile.requestFocus()
                dialog.etBeneficiaryMobile.setError("Invalid Recipient Mobile")
            } else if (dialog.etBenifAccnNo.text.toString().isEmpty()) {
                dialog.etBenifAccnNo.requestFocus()
                dialog.etBenifAccnNo.setError("Invalid Bank Account")

            } else if (dialog.etConfirmAccount.text.toString().isEmpty()) {
                dialog.etConfirmAccount.requestFocus()
                dialog.etConfirmAccount.setError("Invalid Bank Account")

            } else if (dialog.etBenifIFSCNo.text.toString().isEmpty()) {

                dialog.etBenifIFSCNo.requestFocus()
                dialog.etBenifIFSCNo.setError("Invalid Bank IFSC")

            } else if (dialog.etBeneficiaryBank.text.toString().isEmpty()) {

                dialog.etBeneficiaryBank.requestFocus()
                dialog.etBeneficiaryBank.setError("Invalid Bank Name")

            } else {
                var deviceId =
                    Settings.System.getString(contentResolver, Settings.Secure.ANDROID_ID)

                Log.e("DEVICE ID", "" + deviceId)

                var deviceNameDet = getDeviceName().toString()

/*                addBeneficiary(
                    dmtMobile,
                    dialog.etBeneficiaryName.text.toString(),
                    dialog.etBenifIFSCNo.text.toString(),
                    dialog.etBeneficiaryBank.text.toString(),
                    dialog.etConfirmAccount.text.toString(),
                    deviceId,
                    deviceNameDet,
                    userModel.cus_id
                )*/
            }
        }


        dialog.ivCancelDialog.setOnClickListener {
            dialog.dismiss()
        }


        dialog.show()

    }

/*
    private fun addBeneficiary(
        dmt_user_id: String,
        payeeName: String,
        IFSC: String,
        bankName: String,
        accountNo: String,
        deviceId: String,
        deviceName: String,
        cus_id: String

    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, ADD_BENEFICIARY, this)
            mAPIcall.addBeneficiaryAccount(
                dmt_user_id,
                payeeName,
                IFSC,
                bankName,
                accountNo,
                deviceId,
                deviceName,
                cus_id
            )
        } else {
            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }
*/


    fun recipientList(dmt_user_id: String) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, RECIPIENT_LIST, this)
            mAPIcall.viewBenificiary(dmt_user_id)
        } else {
            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(RECIPIENT_LIST)) {
            recipientsArray.clear()

            Log.e("RECIPIENT_LIST", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                progress_bar.visibility = View.INVISIBLE
                val mess =jsonObject.getJSONObject("message")
                val status1 = mess.getString("status")
                val cast = mess.getJSONArray("api_msg")

                if(status1.equals("TXN")) {
                    for (i in 0 until cast.length()) {
                        val notifyObjJson = cast.getJSONObject(i)
                        val fundRecieveHistoryModel = Gson()
                            .fromJson(notifyObjJson.toString(), RecipientModel::class.java)
                        recipientsArray.add(fundRecieveHistoryModel)
                    }

                }
                rvRecipeintList.adapter!!.notifyDataSetChanged()

            } else {

                progress_bar.visibility = View.INVISIBLE

            }
        }
        if (flag.equals(ADD_BENEFICIARY)) {
            Log.e("ADD_BENEFICIARY", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val result = jsonObject.getString(AppConstants.RESULT)

            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                progress_bar.visibility = View.INVISIBLE
                toast("Beneficiary Added Successfully")
                dialog.dismiss()
                recipientList(dmtMobile)


            } else {
                progress_bar.visibility = View.INVISIBLE
                toast("Beneficiary Adding Failed")


            }
        }

    }

    fun getDeviceName(): String? {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            model
        } else {
            manufacturer.toString() + " " + model
        }
    }

    override fun onResume() {
        super.onResume()
        recipientList(dmtMobile)

    }

}