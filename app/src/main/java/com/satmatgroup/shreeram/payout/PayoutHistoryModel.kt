package com.satmatgroup.shreeram.payout

data class PayoutHistoryModel(
    val accountHolderName: String,
    val amount: String,
    val bankAccount: String,
    val bankIFSC: String,
    val bankName: String,
    val charge: String,
    val cus_id: String,
    val pay_req_id: String,
    val request_date: String,
    val status: String
)