package com.satmatgroup.shreeram.model

data class LedgerReportModel(
    val cus_mobile: String,
    val cus_name: String,
    val cus_type: String,
    val txn_agentid: String,
    val txn_clbal: String,
    val txn_crdt: String,
    val txn_date: String,
    val txn_dbdt: String,
    val txn_id: String,
    val txn_opbal: String,
    val txn_time: String,
    val txn_type: String
)