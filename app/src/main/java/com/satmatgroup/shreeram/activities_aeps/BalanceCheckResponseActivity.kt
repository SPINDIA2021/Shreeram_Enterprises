package com.satmatgroup.shreeram.activities_aeps

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.satmatgroup.shreeram.R
import kotlinx.android.synthetic.main.activity_balance_check_response.*
import kotlinx.android.synthetic.main.activity_balance_check_response.tvMobileNumber
import kotlinx.android.synthetic.main.activity_balance_check_response.tvOutletName
import kotlinx.android.synthetic.main.activity_balance_check_response.tvPrintReceipt
import kotlinx.android.synthetic.main.activity_balance_check_response.tvSuccessMessage

class BalanceCheckResponseActivity : AppCompatActivity() {

    var aepsmessage: String = ""
    var terminalId: String = ""
    var requestTransactionTime: String = ""
    var transactionAmount: String = ""
    var transactionStatus: String = ""
    var balanceAmount: String = ""
    var bankRRN: String = ""
    var transactionType: String = ""
    var fpTransactionId: String = ""
    var merchantTransactionId: String = ""
    var outletname: String = ""
    var outletmobile: String = ""
    var url: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance_check_response)


        val bundle = intent.extras


        if (bundle != null) {
            aepsmessage = bundle.getString("aepsmessage").toString()
            terminalId = bundle.getString("terminalId").toString()
            requestTransactionTime = bundle.getString("requestTransactionTime").toString()
            transactionAmount = bundle.getString("transactionAmount").toString()
            transactionStatus = bundle.getString("transactionStatus").toString()
            balanceAmount = bundle.getString("balanceAmount").toString()
            bankRRN = bundle.getString("bankRRN").toString()
            transactionType = bundle.getString("transactionType").toString()
            fpTransactionId = bundle.getString("fpTransactionId").toString()
            merchantTransactionId = bundle.getString("merchantTransactionId").toString()
            outletname = bundle.getString("outletname").toString()
            outletmobile = bundle.getString("outletmobile").toString()
            url = bundle.getString("url").toString()
        }

        tvSuccessMessage.text = aepsmessage
        tvTerminalID.text = terminalId
        tvBankRRNNo.text = bankRRN
        tvDate.text = requestTransactionTime
        tvOutletName.text = terminalId
        tvMobileNumber.text = terminalId
        tvBalanceAmount.text = resources.getString(R.string.Rupee) + "" + balanceAmount
        tvOutletName.text = outletname
        tvMobileNumber.text = outletmobile
        tvPrintReceipt.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("url", url)
            val intent = Intent(this, InvoiceViewActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }

    }
}