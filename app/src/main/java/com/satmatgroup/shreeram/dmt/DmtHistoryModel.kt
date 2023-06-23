package com.satmatgroup.shreeram.dmt

data class DmtHistoryModel(
    val amount: String,
    val bank_name: String,
    val bene_code: String,
    val bene_name: String,
    val charge: String,
    val cus_id: String,
    val description: String,
    val dmt_trnx_id: String,
    val imps_ref_no: String,
    val mobile_no: String,
    val request_id: String,
    val status: String,
    val trans_id: String,
    val dmt_trnx_date: String,
    val apiname: String
)