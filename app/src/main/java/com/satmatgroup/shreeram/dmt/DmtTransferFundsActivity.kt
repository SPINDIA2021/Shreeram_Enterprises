package com.satmatgroup.shreeram.dmt

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import com.satmatgroup.shreeram.utils.toast
import kotlinx.android.synthetic.main.activity_dmt_transfer_funds.*
import kotlinx.android.synthetic.main.activity_dmt_transfer_funds.view.*
import kotlinx.android.synthetic.main.layout_dialog_confirmpin.*
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit

class DmtTransferFundsActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener , TextToSpeech.OnInitListener{

    lateinit var recipientModel: RecipientModel
    private val GET_CHARGE: String = "GET_CHARGE"
    private val DMT_FUNDTRANSFER: String = "DMT_FUNDTRANSFER"
    private val CONFIRMPIN_API: String = "CONFIRMPIN_API"
    lateinit var dialog: Dialog
    var timer: Timer?=null
    lateinit var userModel: UserModel
    lateinit var charge: String
    lateinit var apiname: String

    private var tts: TextToSpeech? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dmt_transfer_funds)

        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }

        val bundle = intent.extras

        if (bundle != null) {
            recipientModel = bundle.getSerializable("recipientModel") as RecipientModel
        }
        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)

        apiname = AppPrefs.getStringPref("DmtName",this).toString()
        Log.e("apiname",apiname)
        tts = TextToSpeech(this, this)
        tvRecipientName.text = recipientModel.benename

        btnTransferFunds.setOnClickListener {

            if (etdmtTransferFundsAmount.text.toString().isEmpty()) {
                etdmtTransferFundsAmount.requestFocus()
                etdmtTransferFundsAmount.setError("Invalid amount")
            } else if (etdmtTransferFundsAmount.text.toString().toInt() > 5000) {
                etdmtTransferFundsAmount.requestFocus()
                etdmtTransferFundsAmount.setError("Amount should be in between 0-5000")
            } else if ((etdmtTransferFundsAmount.text.toString().toInt() <= 0)) {
                etdmtTransferFundsAmount.requestFocus()
                etdmtTransferFundsAmount.setError("Amount should be in between 0-5000")
            } else {
                showConfirmPaymentDialog()

                //getCharge(etdmtTransferFundsAmount.text.toString())
            }
        }


    }
/*
    private fun startTimer(){
        timer = Timer(10000);
        timer?.start()
    }

    private fun updateTimer(){
        if(timer!=null) {
            val miliis = timer?.millisUntilFinished + TimeUnit.SECONDS.toMillis(5)
            //Here you need to maintain single instance for previous
            timer?.cancel()
            timer = Timer(miliis);
            timer?.start()
        }else{
            startTimer()
        }
    }*/


    private fun getCharge(
        amount: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, GET_CHARGE, this)
            mAPIcall.getCharge(amount)
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dmtFundTransfer(
        CustomerMobile: String,
        beneficiaryAccount: String,
        beneficiaryIFSC: String,
        amount: String,
        cus_id: String,
        benename: String,
        bankname: String,
        beneficiaryid: String,
        benemobile: String,
        dmtapiname: String

    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, DMT_FUNDTRANSFER, this)
            mAPIcall.dmtTransactionApi(
                CustomerMobile, beneficiaryAccount, beneficiaryIFSC, amount, cus_id, benename, bankname, beneficiaryid, benemobile, dmtapiname
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    fun countdown(){
        object : CountDownTimer(180000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                tvTimer.setText("Please Wait For: "+(millisUntilFinished / 1000).toString()+" Seconds")
            }
            override fun onFinish() {
                // do something after countdown is done ie. enable button, change color

                tvTimer.setText("Done")
                val intent = Intent(this@DmtTransferFundsActivity,ViewDmtTransactionActivity::class.java)
                startActivity(intent)
            }
        }.start()
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(GET_CHARGE)) {
            Log.e("GET_CHARGE", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {
                progress_bar.visibility = View.INVISIBLE
                charge = jsonObject.getString("result")
            } else {
                progress_bar.visibility = View.INVISIBLE
            }
        }
        if (flag.equals(DMT_FUNDTRANSFER)) {
            Log.e("DMT_FUNDTRANSFER", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {
                progress_bar.visibility = View.INVISIBLE
                toast(jsonObject.getString(AppConstants.RESULT))
                tts!!.speak(jsonObject.getString(AppConstants.RESULT), TextToSpeech.QUEUE_FLUSH, null,"")
                etdmtTransferFundsAmount.setText("")
                etAadharCardNumber.setText("")
                etPanCardNumber.setText("")

                charge = ""

                if(apiname.contains("SUPER")) {
                    btnTransferFunds.visibility = View.GONE
                    tvTimer.visibility = View.VISIBLE
                    Log.e("Timer","Timer")
                    countdown()
                }
                else {
                    val intent = Intent(this, ViewDmtTransactionActivity::class.java)
                    startActivity(intent)
                }


            } else {
                progress_bar.visibility = View.INVISIBLE
                toast(jsonObject.getString(AppConstants.RESULT))
                etdmtTransferFundsAmount.setText("")
                charge = ""

            }
        }
        if (flag.equals(CONFIRMPIN_API)) {
            Log.e("CONFIRMPIN_API", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val messageCode = jsonObject.getString(AppConstants.MESSAGE)
            Log.e(AppConstants.STATUS, status)
            Log.e(AppConstants.MESSAGE, messageCode);
            if (status.contains("true")) {
                progress_bar.visibility = View.INVISIBLE

                dmtFundTransfer(
                    AppPrefs.getStringPref("dmtMobile", this).toString(),
                    recipientModel.beneaccno,
                    recipientModel.IFSC,
                    etdmtTransferFundsAmount.text.toString(),
                    userModel.cus_id, recipientModel.benename,
                    recipientModel.banknameUnique,
                    recipientModel.BENEFICIARYID,
                    recipientModel.benemobile,apiname
                )
            //    dialog.dismiss()
            } else {
                progress_bar.visibility = View.INVISIBLE
                toast(messageCode)
              //  dialog.dismiss()
            }
        }


    }

    fun confirmPin() {
        dialog = Dialog(this, R.style.ThemeOverlay_MaterialComponents_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_dialog_confirmpin)

        dialog.etPin.requestFocus()
        dialog.tvDialogCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.etPin.setText(AppPrefs.getStringPref("AppPassword",this).toString())


        dialog.getWindow()!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        dialog.tvConfirmPin.setOnClickListener {
            if (dialog.etPin.text.toString().isEmpty()) {
                dialog.etPin.requestFocus()
                dialog.etPin.setError("Please Enter Pin")
            } else {
                confirmPinApi(
                    userModel.cus_mobile,
                    dialog.etPin.text.toString(),
                    AppPrefs.getStringPref("deviceId", this).toString(),
                    AppPrefs.getStringPref("deviceName", this).toString(),
                    userModel.cus_pass,
                    userModel.cus_mobile,
                    userModel.cus_type
                )
                dialog.dismiss()

            }

        }
        dialog.show()
    }

    private fun showConfirmPaymentDialog() {
        val builder1 =
            AlertDialog.Builder(this)
        builder1.setTitle("Please Verify..")
        builder1.setMessage(
            "Do you want to Proceed Fund Transfer for\n" +
                    "Account Number : ${recipientModel.beneaccno}\n" +
                    "Bank Name : ${recipientModel.banknameUnique}\n" +
                    "IFSC : ${recipientModel.IFSC}\n" +
                    "Amount : ${etdmtTransferFundsAmount.text}\n"
            //     "Services Charge: ${charge}"
        )
        builder1.setCancelable(true)
        builder1.setPositiveButton(
            "OK"
        ) { dialog, id ->
            confirmPinApi(
                userModel.cus_mobile,
                AppPrefs.getStringPref("AppPassword",this).toString(),
                AppPrefs.getStringPref("deviceId", this).toString(),
                AppPrefs.getStringPref("deviceName", this).toString(),
                userModel.cus_pass,
                userModel.cus_mobile,
                userModel.cus_type
            )
            // confirmPin()

            dialog.cancel()
        }
        builder1.setNegativeButton(
            "CANCEL"
        ) { dialog, id ->
            dialog.cancel()
        }
        val alert11 = builder1.create()
        alert11.show()
    }

    private fun confirmPinApi(
        mobile: String,
        pin: String,
        deviceId: String,
        deviceName: String,
        pass: String,
        cus_mobile: String,
        cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, CONFIRMPIN_API, this)
            mAPIcall.verifyPin(
                mobile, pin
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                //   tts!!.speak("Text to speech", TextToSpeech.QUEUE_FLUSH, null,"")

                Log.e("TTS","buttonSpeak!!.isEnabled = true")
                // buttonSpeak!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }
}