package com.satmatgroup.shreeram.dmt

data class BankListModel(
    val id: String,
    val bank_id: String,
    val bankcode: String,
    val bankname: String,
    val masterifsc: String,
    val url: String
)