package com.satmatgroup.shreeram.activities_aeps.aepshistory

import java.io.Serializable

data class AepsHistoryModel(
    val aadhar_number: String,
    val aeps_bank_id: String,
    val aeps_date_time: String,
    val aeps_id: String,
    val amount: String,
    val apiclid: String,
    val device: String,
    val distributor_commission: String,
    val dmt_dispute_status: String,
    val master_commission: String,
    val pidData: String,
    val retailer_commission: String,
    val status: String,
    val transactionType: String,
    val transaction_ref_id: String,
    val txn_ip: String,
    val utr: String
) : Serializable