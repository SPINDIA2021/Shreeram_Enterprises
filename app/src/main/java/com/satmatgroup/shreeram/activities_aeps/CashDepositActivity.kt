package com.satmatgroup.shreeram.activities_aeps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.satmatgroup.shreeram.R

class CashDepositActivity : AppCompatActivity() {

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
    var aadhar_no: String = ""
    var bankName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_deposit)


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
            aadhar_no = bundle.getString("aadhar_no").toString()
            bankName = bundle.getString("bankName").toString()
        }


    }
}