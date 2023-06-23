package com.satmatgroup.shreeram.fund_services

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.adapters_recyclerview.UserListAdapter
import com.satmatgroup.shreeram.model.UserListModel
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.*
import kotlinx.android.synthetic.main.activity_transfer_funds.*
import kotlinx.android.synthetic.main.activity_transfer_funds.view.*
import kotlinx.android.synthetic.main.layout_list_bottomsheet.view.*
import kotlinx.android.synthetic.main.layout_list_bottomsheet.view.rvspinner
import kotlinx.android.synthetic.main.layout_list_bottomsheet_users.view.*
import org.json.JSONObject
import java.util.*

class TransferFundsActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener,
    UserListAdapter.ListAdapterListener {
    val FUND_TRANSFER = "FUND_TRANSFER"
    val GET_USER_ID = "GET_USER_ID"
    val GET_USERLIST = "GET_USERLIST"
    val SAME_FUNDTRANSFER = "SAME_FUNDTRANSFER"
    var bottomSheetDialogUsers: BottomSheetDialog? = null
    lateinit var userListAdapter: UserListAdapter
    var userListModelArrayList = ArrayList<UserListModel>()

    var user_id: String = ""

    lateinit var userListModel: UserListModel

    lateinit var userModel: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.status_bar, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_transfer_funds)

        //Toolbar
        custToolbar.ivBackBtn.setOnClickListener { onBackPressed() }
        val bundle = intent.extras

        if (bundle != null) {
            userListModel = bundle.getSerializable("userListModel") as UserListModel
            tvCustomerName.setText(userListModel.cus_name)
            tvUserBalance.setText(userListModel.clbal)
            etCustMobileNumber.setText(userListModel.cus_mobile.trim())

            user_id = userListModel.cus_id
        } else {

            tvCustomerName.setText("")
            tvUserBalance.setText("")
            etCustMobileNumber.setText("")
            user_id = ""
        }


        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)



        rlUserName.setOnClickListener {
            getUserList(
                userModel.cus_id,
                AppPrefs.getStringPref(" deviceId", this@TransferFundsActivity).toString(),
                AppPrefs.getStringPref("deviceName", this@TransferFundsActivity).toString(),
                userModel.cus_pin,
                userModel.cus_pass,
                userModel.cus_mobile,
                userModel.cus_type
            )

        }



        btnTransferFunds.setOnClickListener {
            if (tvCustomerName.text.toString().isEmpty()) {

                tvCustomerName.requestFocus()
                tvCustomerName.error = "Please Select Customer"

            } else if (etTransferFundsAmount.text.toString().isEmpty()) {
                etTransferFundsAmount.requestFocus()
                etTransferFundsAmount.error = "Invalid Amount"

            } else {
                checkIfSameFundTransfer(
                    userModel.cus_id,
                    user_id,
                    etTransferFundsAmount.text.toString(),
                    AppPrefs.getStringPref("deviceId", this).toString(),
                    AppPrefs.getStringPref("deviceName", this).toString(),
                    userModel.cus_pin,
                    userModel.cus_pass,
                    userModel.cus_mobile,
                    userModel.cus_type

                )
            }
        }

        btnGetDetails.setOnClickListener {
            hideKeyboard()
            if (!AppCommonMethods.checkForMobile(etCustMobileNumber)) {

                etCustMobileNumber.requestFocus();
                etCustMobileNumber.setError("Invalid Number")
            } else {

                getUserId(
                    etCustMobileNumber.text.toString(),
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


    private fun checkIfSameFundTransfer(
        cus_id: String, user_list_id: String, amount: String,
        deviceId: String, deviceName: String,
        pin: String, pass: String, cus_mobile: String, cus_type: String

    ) {

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, SAME_FUNDTRANSFER, this)
            mAPIcall.checkIfSameFundTransfer(
                cus_id, user_list_id, amount, deviceId, deviceName,
                pin, pass, cus_mobile, cus_type
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUserId(
        mobile: String,
        deviceId: String, deviceName: String,
        pin: String, pass: String, cus_mobile: String, cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, GET_USER_ID, this)
            mAPIcall.getUserId(mobile, deviceId, deviceName, pin, pass, cus_mobile, cus_type)
        } else {

            toast("No Internet Connection")
        }
    }

    private fun getUserList(
        dis_cus_id: String,
        deviceId: String, deviceName: String,
        pin: String, pass: String, cus_mobile: String,
        cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, GET_USERLIST, this)
            mAPIcall.getUserList(dis_cus_id, deviceId, deviceName, pin, pass, cus_mobile, cus_type)
        } else {

            toast("No Internet Connection")
        }
    }

    private fun fundTransfer(
        dis_id: String, cus_id: String, amount_string: String,
        deviceId: String, deviceName: String,
        pin: String, pass: String, cus_mobile: String, cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, FUND_TRANSFER, this)
            mAPIcall.fundTransferApi(
                dis_id,
                cus_id,
                amount_string,
                deviceId,
                deviceName,
                pin,
                pass,
                cus_mobile, cus_type
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun searchUser(
        dis_cus_id: String, mobileorname: String,
        deviceId: String, deviceName: String,
        pin: String, pass: String, cus_mobile: String, cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, GET_USERLIST, this)
            mAPIcall.searcUser(
                dis_cus_id,
                mobileorname,
                deviceId,
                deviceName,
                pin,
                pass,
                cus_mobile, cus_type
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(GET_USERLIST)) {
            userListModelArrayList.clear()
            Log.e("GET_USERLIST", result)
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
                    val userListModel = Gson()
                        .fromJson(
                            notifyObjJson.toString(),
                            UserListModel::class.java
                        )


                    userListModelArrayList.add(userListModel)
                }

                ShowBottomSheetUserList()

            } else {
                progress_bar.visibility = View.INVISIBLE


            }
        }
        if (flag.equals(SAME_FUNDTRANSFER)) {
            Log.e("SAME_FUNDTRANSFER", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            //Log.e(AppConstants.MESSAGE_CODE, messageCode);
            if (status.contains("true")) {
                progress_bar.visibility = View.INVISIBLE

                val response = jsonObject.getString("result")

                showFailedRechargeDialog(response)
            } else {
                progress_bar.visibility = View.INVISIBLE

                fundTransfer(
                    userModel.cus_id, user_id, etTransferFundsAmount.getText().toString(),
                    AppPrefs.getStringPref("deviceId", this).toString(),
                    AppPrefs.getStringPref("deviceName", this).toString(),
                    userModel.cus_pin,
                    userModel.cus_pass,
                    userModel.cus_mobile,
                    userModel.cus_type
                )
            }
        }

        if (flag.equals(FUND_TRANSFER)) {
            Log.e("FUND_TRANSFER", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            //Log.e(AppConstants.MESSAGE_CODE, messageCode);
            if (status.contains("true")) {

                /*Toast.makeText(mContext, "Your Recharge request has been Submitted Successfully", Toast.LENGTH_SHORT)
                        .show();*/
                val response = jsonObject.getString("result")
                progress_bar.visibility = View.INVISIBLE

                showSuccessRechargeDialog(response)
                //walletBalance(cus_id);
            } else {
                progress_bar.visibility = View.INVISIBLE

                /* Toast.makeText(mContext, "Unable to Recharge", Toast.LENGTH_SHORT)
                        .show();*/
                val response = jsonObject.getString("result")

                showFailedRechargeDialog(response)
            }
        }

        if (flag.equals(
                GET_USER_ID
            )
        ) {
            progress_bar.visibility = View.INVISIBLE

            Log.e("GET_USER_ID", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            //Log.e(AppConstants.MESSAGE_CODE, messageCode);
            if (status.contains("true")) {
                val cast = jsonObject.getJSONArray("result")
                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    user_id = notifyObjJson.getString("cus_id")
                    Log.e("userID ", user_id)
                    progress_bar.visibility = View.INVISIBLE

                    tvUserBalance.setText(notifyObjJson.getString("clbal"))
                    tvCustomerName.setText(notifyObjJson.getString("cus_name"))

                    //walletBalance(userID)
                }
            } else if (status.contains("false")) {
                progress_bar.visibility = View.INVISIBLE

                toast("Invalid Customer")
            }
        }

    }


    private fun ShowBottomSheetUserList() {
        val view: View = layoutInflater.inflate(R.layout.layout_list_bottomsheet_users, null)
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
                if (s.toString().length == 0) {
                    bottomSheetDialogUsers!!.dismiss()

                    getUserList(
                        userModel.cus_id,
                        AppPrefs.getStringPref(" deviceId", this@TransferFundsActivity).toString(),
                        AppPrefs.getStringPref("deviceName", this@TransferFundsActivity).toString(),
                        userModel.cus_pin,
                        userModel.cus_pass,
                        userModel.cus_mobile,
                        userModel.cus_type
                    )
                    hideKeyboard()


                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        view.ivSearchBtn.setOnClickListener {
            bottomSheetDialogUsers!!.dismiss()

            if (view.etSearchMobName.text.isEmpty()) {

                view.etSearchMobName.requestFocus()
                view.etSearchMobName.setError("Please enter mobile or name")

            } else {

                searchUser(
                    userModel.cus_id, view.etSearchMobName.text.toString(),
                    AppPrefs.getStringPref("deviceId", this).toString(),
                    AppPrefs.getStringPref("deviceName", this).toString(),
                    userModel.cus_pin,
                    userModel.cus_pass,
                    userModel.cus_mobile, userModel.cus_type
                )
            }
        }



        view.rvspinner.apply {

            layoutManager = LinearLayoutManager(this@TransferFundsActivity)
            userListAdapter = UserListAdapter(
                context, userListModelArrayList, this@TransferFundsActivity
            )
            view.rvspinner.adapter = userListAdapter
        }
        bottomSheetDialogUsers = BottomSheetDialog(this, R.style.SheetDialog)
        bottomSheetDialogUsers!!.setContentView(view)
        val bottomSheetBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.peekHeight = 800
        bottomSheetDialogUsers!!.show()
    }

    private fun showSuccessRechargeDialog(response: String) {
        val builder1 =
            AlertDialog.Builder(this)
        builder1.setTitle("Request Status")
        builder1.setMessage("" + response)
        builder1.setCancelable(false)
        builder1.setPositiveButton(
            "OK"
        ) { dialog, id ->
            tvUserBalance.setText("")
            tvCustomerName.setText("")
            //ivMobileOperatorImg.setImageResource(R.drawable.ic_network_check_black_24dp)
            etTransferFundsAmount.setText("")

            /*

               showScratchCard()*/
            dialog.cancel()
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

    private fun showFailedRechargeDialog(response: String) {
        val builder1 =
            AlertDialog.Builder(this)
        builder1.setTitle("Attention!")
        builder1.setMessage(response)
        builder1.setCancelable(true)
        builder1.setPositiveButton(
            "OK"
        ) { dialog, id ->
            tvUserBalance.setText("")
            tvCustomerName.setText("")
            //ivMobileOperatorImg.setImageResource(R.drawable.ic_network_check_black_24dp)
            etTransferFundsAmount.setText("")

            dialog.cancel()
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


    override fun onClickAtOKButton(mobileRechargeModal: UserListModel?) {

        if (mobileRechargeModal != null) {
            tvCustomerName.setText(mobileRechargeModal.cus_name)
            if (mobileRechargeModal.clbal.equals("null") || mobileRechargeModal.clbal == null) {
                tvUserBalance.setText("0")

            } else {
                tvUserBalance.setText(mobileRechargeModal.clbal)

            }
            etCustMobileNumber.setText(mobileRechargeModal.cus_mobile)

            user_id = mobileRechargeModal.cus_id
            bottomSheetDialogUsers!!.dismiss()
        }

    }
}