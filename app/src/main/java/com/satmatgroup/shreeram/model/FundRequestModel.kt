package com.satmatgroup.shreeram.model

data class FundRequestModel(
    val cus_id: String,
    val cus_mobile: String,
    val cus_name: String,
    val cus_type: String,
    val pay_amount: String,
    val pay_bank: String,
    val ref_no: String,
    val req_date: String,
    val req_id: String,
    val req_status: String,
    val request_from: String,
    val res_date: String
)