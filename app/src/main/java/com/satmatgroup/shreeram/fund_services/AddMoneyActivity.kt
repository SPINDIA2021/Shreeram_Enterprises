package com.satmatgroup.shreeram.fund_services

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.activities_upi.PaymentSuccess
import com.satmatgroup.shreeram.activities_upi.Singleton2
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import com.satmatgroup.shreeram.utils.toast
import kotlinx.android.synthetic.main.activity_add_money.*
import kotlinx.android.synthetic.main.activity_add_money.view.*
import org.json.JSONObject
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;


class AddMoneyActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener ,PaymentStatusListener {

    var TAG = javaClass.simpleName
    private var singleton: Singleton2? = null
    val GETUPIAPI = "GETUPIAPI"
    val UPDATE_BALANCE = "UPDATE_BALANCE"
   // var upi_id: String? = null
    var upiid: String = "8959525051@ybl"
    var minimumamount: String? = null

    lateinit var userModel: UserModel
    lateinit var transactionId: String
    lateinit var responseCode: String
    lateinit var approvalRefNo: String
    lateinit var txnRef: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.status_bar, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_add_money)

        // on below line we are getting date and then we are setting this date as transaction id.
        // on below line we are getting date and then we are setting this date as transaction id.
        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault())
        val transcId: String = df.format(c)
        cvProceedAddMoneyBtn.setOnClickListener {
            // on below line we are getting data from our edit text.
            // on below line we are getting data from our edit text.
            val gson = Gson()
            val json = AppPrefs.getStringPref(AppConstants.USER_MODEL, this)
            userModel = gson.fromJson(json, UserModel::class.java)


            val amount: String = et_ammount.getText().toString()+".00"
            val upi: String = upiid!!
            val name: String = userModel.cus_name
            val desc: String = "Payment"
            // on below line we are validating our text field.
            // on below line we are validating our text field.
            if (TextUtils.isEmpty(amount) && TextUtils.isEmpty(upi) && TextUtils.isEmpty(name) && TextUtils.isEmpty(
                    desc
                )
            ) {
                Toast.makeText(
                    this@AddMoneyActivity,
                    "Please enter amount..",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // if the edit text is not empty then
                // we are calling method to make payment.
                makePayment(amount, upi, name, desc, transcId)
            }
        }

        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }


        //toolbar
        val toolbar =
            findViewById<Toolbar>(R.id.custToolbar)
        val ivBackBtn =
            toolbar.findViewById<ImageView>(R.id.ivBackBtn)
        ivBackBtn.setOnClickListener { onBackPressed() }

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)

        getUpi(
            userModel.cus_id, AppPrefs.getStringPref("deviceId", this).toString(),
            AppPrefs.getStringPref("deviceName", this).toString(),
            userModel.cus_pin,
            userModel.cus_pass,
            userModel.cus_mobile, userModel.cus_type
        )

        getBalanceApi(userModel.cus_mobile)
//        tvUserNameNumber.setText("Transferring money to:\n${userModel.cus_name} (${userModel.cus_mobile}) ")


        singleton = Singleton2.getInstance()
        //   submit = this.findViewById(R.id.btn_submit);
        //et_upi = findViewById<EditText>(R.id.et_upi)

        rlGooglePay.setOnClickListener(View.OnClickListener {
            if (appInstalledOrNot("com.google.android.apps.nbu.paisa.user", "GOOGLE PAY")) {
                Startupipayment("com.google.android.apps.nbu.paisa.user")
            }
        })
        rl_PhonePe.setOnClickListener(View.OnClickListener {
            if (appInstalledOrNot("com.phonepe.app", "PHONE PE")) {
                Startupipayment("com.phonepe.app")
            }
        })
        rl_Paytm.setOnClickListener(View.OnClickListener {
            if (appInstalledOrNot("net.one97.paytm", "PAYTM")) {
                Startupipayment("net.one97.paytm")
            }
        })
        rl_AmazonPay.setOnClickListener(View.OnClickListener {
            if (appInstalledOrNot("in.amazon.mShop.android.shopping", "AMAZON PAY")) {
                Startupipayment("in.amazon.mShop.android.shopping")
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            if (data != null) {
                // Get Response from activity intent
                val response = data.getStringExtra("response")
                if (response == null) {
                    callbackTransactionCancelled()
                    Log.d(TAG, "Response is null")
                } else {
                    try {
                        // Get transactions details from response.
                        val transactionDetails =
                            getTransactionDetails(response)
                        if (transactionDetails.status!!.toLowerCase() == "success") {

                            addFundsApi(
                                userModel.cus_id, et_ammount.text.toString(), "UPI Payment",
                                transactionDetails.getTransactionId().toString(),
                                transactionDetails.getTransactionRefId().toString(),
                                AppPrefs.getStringPref("deviceId", this).toString(),
                                AppPrefs.getStringPref("deviceName", this).toString(),
                                userModel.cus_pin,
                                userModel.cus_pass,
                                userModel.cus_mobile, userModel.cus_type
                            )
                            transactionId = transactionDetails.getTransactionId().toString()
                            txnRef = transactionDetails.getTransactionRefId().toString()
                            responseCode = transactionDetails.getResponseCode().toString()
                            approvalRefNo = transactionDetails.getApprovalRefNo().toString()
                            Log.e("transactionId", transactionId)
                            Log.e("responseCode ", responseCode)
                            Log.e("approvalRefNo", approvalRefNo)
                            Log.e("txnRef       ", txnRef)

                        } else if (transactionDetails.status!!.toLowerCase(Locale.getDefault()) == "submitted") {
                            val intent = Intent(
                                this@AddMoneyActivity,
                                PaymentSuccess::class.java
                            )
                            intent.putExtra("status", 2)
                            intent.putExtra("amount", et_ammount.text.toString())
                            intent.putExtra("transactionId", transactionDetails.getTransactionId())
                            intent.putExtra("responseCode", transactionDetails.getResponseCode())
                            intent.putExtra("approvalRefNo", transactionDetails.getApprovalRefNo())
                            intent.putExtra("txnRef", transactionDetails.getTransactionRefId())
                            startActivity(intent)
                        } else {
                            val intent = Intent(
                                this@AddMoneyActivity,
                                PaymentSuccess::class.java
                            )
                            intent.putExtra("status", 0)
                            intent.putExtra("amount", et_ammount.text.toString())
                            intent.putExtra("transactionId", transactionDetails.getTransactionId())
                            intent.putExtra("responseCode", transactionDetails.getResponseCode())
                            intent.putExtra("approvalRefNo", transactionDetails.getApprovalRefNo())
                            intent.putExtra("txnRef", transactionDetails.getTransactionRefId())
                            startActivity(intent)
                        }
                    } catch (e: Exception) {
                        callbackTransactionCancelled()
                        callbackTransactionFailed()
                    }
                }
            } else {
                Log.e(TAG, "Intent Data is null. User cancelled")
                callbackTransactionCancelled()
            }
        }
    }


    private fun appInstalledOrNot(uri: String, flag: String): Boolean {
        val pm = packageManager
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {

            showAppDialog(flag)
            //   Toast.makeText(this@AddMoneyActivity, "App not found", Toast.LENGTH_LONG).show()
        }
        return false
    }

    private fun Startupipayment(packagename: String) {
        if (!et_ammount.text.toString().isEmpty()) {

            if (et_ammount.text.toString().toDouble() >= minimumamount!!.toDouble()) {
                var ammount = "0.00"
                val df = DecimalFormat("#.00")
                ammount = df.format(java.lang.Double.valueOf(et_ammount.text.toString()))

                // Set Parameters for UPI

                val payUri = Uri.Builder()
                payUri.scheme("upi").authority("pay")
                payUri.appendQueryParameter("pa", upiid.toString())
                payUri.appendQueryParameter("pn", "Test Name")
                payUri.appendQueryParameter(
                    "tid",
                    "TRNID" + System.currentTimeMillis().toString()
                )
                payUri.appendQueryParameter(
                    "tr",
                    "RTRNID" + System.currentTimeMillis().toString()
                )
                payUri.appendQueryParameter("tn", "This is a short description.")
                payUri.appendQueryParameter("am", ammount)
                payUri.appendQueryParameter("cu", "INR")

                //Build URI
                val uri = payUri.build()

                // Set Data Intent
                val paymentIntent = Intent(Intent.ACTION_VIEW)
                paymentIntent.data = uri
                paymentIntent.setPackage(packagename)
                startActivityForResult(paymentIntent, 100)
            } else {
                et_ammount.requestFocus()
                et_ammount.error = "Amount should be Greater than $minimumamount"
            }


        } else {

            Toast.makeText(
                this@AddMoneyActivity,
                "Please check UPI ID or Amount.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun getQueryString(url: String): Map<String, String> {
        val params = url.split("&".toRegex()).toTypedArray()
        val map: MutableMap<String, String> =
            HashMap()
        for (param in params) {
            val name = param.split("=".toRegex()).toTypedArray()[0]
            val value = param.split("=".toRegex()).toTypedArray()[1]
            map[name] = value
        }
        return map
    }

    // Make TransactionDetails object from response string
    private fun getTransactionDetails(response: String): TransactionDetails {
        val map = getQueryString(response)
        val transactionId = map["txnId"]
        val responseCode = map["responseCode"]
        val approvalRefNo = map["ApprovalRefNo"]
        val status = map["Status"]
        val transactionRefId = map["txnRef"]
        return TransactionDetails(
            transactionId,
            responseCode,
            approvalRefNo,
            status,
            transactionRefId
        )
    }

    // Checks whether listener is registered
    private fun isListenerRegistered(): Boolean {
        return Singleton2.getInstance()
            ?.isListenerRegistered()!!
    }

    private fun callbackOnAppNotFound() {
        Log.e("AppNotFound", "No UPI app found on device.")
        if (isListenerRegistered()) {
            singleton!!.listener!!.onAppNotFound()
        }
        finish()
    }

    private fun callbackTransactionSuccess() {
        if (isListenerRegistered()) {
            singleton!!.listener!!.onTransactionSuccess()
        }
    }

    private fun callbackTransactionSubmitted() {
        if (isListenerRegistered()) {
            singleton!!.listener!!.onTransactionSubmitted()
        }
    }

    private fun callbackTransactionFailed() {
        if (isListenerRegistered()) {
            singleton!!.listener!!.onTransactionFailed()
        }
    }

    private fun callbackTransactionCancelled() {
        if (isListenerRegistered()) {
            singleton!!.listener!!.onTransactionCancelled()
        }
    }


    //Api functions
    fun getUpi(
        cus_id: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, GETUPIAPI, this)
            mAPIcall.getUpi(cus_id, deviceId, deviceName, pin, pass, cus_mobile, cus_type)
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getBalanceApi(cus_id: String) {
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

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(GETUPIAPI)) {
            Log.e("GETUPIAPI", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {
                progress_bar.visibility = View.GONE

                val cast = jsonObject.getJSONArray("result")
                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                   // upi_id = notifyObjJson.getString("upi_id")
                    upiid="8959525051"
                    minimumamount = notifyObjJson.getString("minimun_amount")
                    tvMinimumAmount.text =
                        "( Minimum Amount - ₹ " + notifyObjJson.getString("minimun_amount") + ")"

                    Log.e("upi_id", upiid!!)


                }


            } else {
                progress_bar.visibility = View.GONE


            }
        }
        if (flag.equals(UPDATE_BALANCE)) {
            Log.e("UPDATE_BALANCE", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {
                progress_bar.visibility = View.GONE

                val intent = Intent(
                    this@AddMoneyActivity,
                    PaymentSuccess::class.java
                )
                intent.putExtra("status", 1)
                intent.putExtra("amount", et_ammount.text.toString())
                intent.putExtra("transactionId", transactionId)
                intent.putExtra("responseCode", responseCode)
                intent.putExtra("approvalRefNo", approvalRefNo)
                intent.putExtra("txnRef", txnRef)
                startActivity(intent)


            } else {
                progress_bar.visibility = View.GONE


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


    private fun showAppDialog(flag: String?) {
        var uri: String
        val builder1 =
            AlertDialog.Builder(this)
        builder1.setTitle("Attention!")
        builder1.setMessage("$flag App is not installed.\nDo you want to install the app?")
        builder1.setCancelable(true)
        builder1.setPositiveButton(
            "INSTALL"
        ) { dialog, id ->


            if (flag.equals("PAYTM")) {
                uri = "market://details?id=net.one97.paytm"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(uri)
                startActivity(intent)
                dialog.cancel()
            } else if (flag.equals("GOOGLE PAY")) {
                uri = "market://details?id=com.google.android.apps.nbu.paisa.user"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(uri)
                startActivity(intent)
                dialog.cancel()

            } else if (flag.equals("PHONE PE")) {
                uri = "market://details?id=com.phonepe.app"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(uri)
                startActivity(intent)
                dialog.cancel()
            } else if (flag.equals("AMAZON PAY")) {
                uri = "market://details?id=in.amazon.mShop.android.shopping"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(uri)
                startActivity(intent)
                dialog.cancel()
            }

        }
        builder1.setNegativeButton(
            "CANCEL"
        ) { dialog, id -> dialog.cancel() }

        val alert11 = builder1.create()
        alert11.show()
    }

    private fun addFundsApi(
        cus_id: String,
        amount: String,
        bank: String,
        transactionId: String,
        txnRef: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, UPDATE_BALANCE, this)
            mAPIcall.addFundsApi(
                cus_id, amount, bank, transactionId, txnRef, deviceId, deviceName, pin,
                pass, cus_mobile, cus_type
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun makePayment(
        amount: String,
        upi: String,
        name: String,
        desc: String,
        transactionId: String
    ) {
        // on below line we are calling an easy payment method and passing
        // all parameters to it such as upi id,name, description and others.
        val easyUpiPayment = EasyUpiPayment.Builder()
            .with(this) // on below line we are adding upi id.
            .setPayeeVpa("8959525051@ybl") // on below line we are setting name to which we are making oayment.
            .setPayeeName(name) // on below line we are passing transaction id.
            .setTransactionId(transactionId) // on below line we are passing transaction ref id.
            .setTransactionRefId(transactionId) // on below line we are adding description to payment.
            .setDescription(desc) // on below line we are passing amount which is being paid.
            .setAmount(amount) // on below line we are calling a build method to build this ui.
            .build()
        // on below line we are calling a start
        // payment method to start a payment.
        easyUpiPayment.startPayment()
        // on below line we are calling a set payment
        // status listener method to call other payment methods.
        easyUpiPayment.setPaymentStatusListener(this)
    }

    override fun onTransactionCompleted(transactionDetails: TransactionDetails) {
        // on below line we are getting details about transaction when completed.
       /* val transcDetails: String = transactionDetails.getStatus().toString()
            .toString() + "\n" + "Transaction ID : " + transactionDetails.getTransactionId()*/
        try {
            // Get transactions details from response.

            if (transactionDetails.status!!.toLowerCase() == "success") {

                addFundsApi(
                    userModel.cus_id, et_ammount.text.toString(), "UPI Payment",
                    transactionDetails.getTransactionId().toString(),
                    transactionDetails.getTransactionRefId().toString(),
                    AppPrefs.getStringPref("deviceId", this).toString(),
                    AppPrefs.getStringPref("deviceName", this).toString(),
                    userModel.cus_pin,
                    userModel.cus_pass,
                    userModel.cus_mobile, userModel.cus_type
                )
                transactionId = transactionDetails.getTransactionId().toString()
                txnRef = transactionDetails.getTransactionRefId().toString()
                responseCode = transactionDetails.getResponseCode().toString()
                approvalRefNo = transactionDetails.getApprovalRefNo().toString()
                Log.e("transactionId", transactionId)
                Log.e("responseCode ", responseCode)
                Log.e("approvalRefNo", approvalRefNo)
                Log.e("txnRef       ", txnRef)

            } else if (transactionDetails.status!!.toLowerCase(Locale.getDefault()) == "submitted") {
                val intent = Intent(
                    this@AddMoneyActivity,
                    PaymentSuccess::class.java
                )
                intent.putExtra("status", 2)
                intent.putExtra("amount", et_ammount.text.toString())
                intent.putExtra("transactionId", transactionDetails.getTransactionId())
                intent.putExtra("responseCode", transactionDetails.getResponseCode())
                intent.putExtra("approvalRefNo", transactionDetails.getApprovalRefNo())
                intent.putExtra("txnRef", transactionDetails.getTransactionRefId())
                startActivity(intent)
            } else {
                val intent = Intent(
                    this@AddMoneyActivity,
                    PaymentSuccess::class.java
                )
                intent.putExtra("status", 0)
                intent.putExtra("amount", et_ammount.text.toString())
                intent.putExtra("transactionId", transactionDetails.getTransactionId())
                intent.putExtra("responseCode", transactionDetails.getResponseCode())
                intent.putExtra("approvalRefNo", transactionDetails.getApprovalRefNo())
                intent.putExtra("txnRef", transactionDetails.getTransactionRefId())
                startActivity(intent)
            }
        } catch (e: Exception) {
            callbackTransactionCancelled()
            callbackTransactionFailed()
        }

    }

    override fun onTransactionSuccess() {
        // this method is called when transaction is successful and we are displaying a toast message.
        Toast.makeText(this, "Transaction successfully completed..", Toast.LENGTH_SHORT).show()
    }

    override fun onTransactionSubmitted() {
        // this method is called when transaction is done
        // but it may be successful or failure.
        Log.e("TAG", "TRANSACTION SUBMIT")
    }

    override fun onTransactionFailed() {
        // this method is called when transaction is failure.
        Toast.makeText(this, "Failed to complete transaction", Toast.LENGTH_SHORT).show()
    }

    override fun onTransactionCancelled() {
        // this method is called when transaction is cancelled.
        Toast.makeText(this, "Transaction cancelled..", Toast.LENGTH_SHORT).show()
    }

    override fun onAppNotFound() {
        // this method is called when the users device is not having any app installed for making payment.
        Toast.makeText(this, "No app found for making transaction..", Toast.LENGTH_SHORT).show()
    }
}