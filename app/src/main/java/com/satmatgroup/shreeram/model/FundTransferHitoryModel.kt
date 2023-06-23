package com.satmatgroup.shreeram.model

data class FundTransferHitoryModel(
    val cus_name: String,
    val to_cus_id: String,
    val to_cus_mobile: String,
    val to_cus_name: String,
    val txn_agentid: String,
    val txn_clbal: String,
    val txn_crdt: String,
    val txn_dbdt: String,
    val txn_id: String,
    val txn_opbal: String,
    val txn_time: String,
    val txn_type: String
)