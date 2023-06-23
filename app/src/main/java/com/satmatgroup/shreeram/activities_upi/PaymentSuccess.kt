package com.satmatgroup.shreeram.activities_upi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.satmatgroup.shreeram.NewMainActivity
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.utils.AppPrefs
import kotlinx.android.synthetic.main.activity_payment_success.*

class PaymentSuccess : AppCompatActivity() {

    lateinit var transactionId: String
    lateinit var responseCode: String
    lateinit var approvalRefNo: String
    lateinit var txnRef: String
    lateinit var amount: String
    val UPDATE_BALANCE = "UPDATE_BALANCE"
    lateinit var userModel: UserModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)

        ivBackBtn.setOnClickListener {
            val intent = Intent(this, NewMainActivity::class.java)
            startActivity(intent)
            finish()
        }

        tvRepeatBtn.setOnClickListener {
            onBackPressed()
        }
        transactionId = intent.extras!!.getString("transactionId").toString()
        responseCode = intent.extras!!.getString("responseCode").toString()
        approvalRefNo = intent.extras!!.getString("approvalRefNo").toString()
        txnRef = intent.extras!!.getString("txnRef").toString()
        amount = intent.extras!!.getString("amount").toString()

        Log.e("transactionId", transactionId)
        Log.e("responseCode ", responseCode)
        Log.e("approvalRefNo", approvalRefNo)
        Log.e("txnRef       ", txnRef)



        if (intent.extras!!.getInt("status") == 1) {
            tvMessage.text = "Payment Successful"
            tvAddedAmount.text = "₹" + amount
            tvTransactionId.text = transactionId
            tvTransactionRefId.text = txnRef
            rl_money.setBackgroundColor(resources.getColor(R.color.green))

            ivSucces.setImageResource(R.drawable.ic_success)

        } else if (intent.extras!!.getInt("status") == 2) {
            tvMessage.text = "Payment Pending"
            rl_money.setBackgroundColor(resources.getColor(R.color.amber))
            tvAddedAmount.text = "₹" + amount
            tvTransactionId.text = transactionId
            tvTransactionRefId.text = txnRef
            ivSucces.setImageResource(R.drawable.ic_pending)

        } else {
            tvMessage.text = "Payment Failed"
            tvAddedAmount.text = "₹" + amount
            rl_money.setBackgroundColor(resources.getColor(R.color.material_red_700))
            tvTransactionId.text = transactionId
            tvTransactionRefId.text = txnRef
            ivSucces.setImageResource(R.drawable.ic_failed)

        }

    }


}