package com.satmatgroup.shreeram.dmt

import java.io.Serializable

data class RecipientModel(
    val STS_CODE: String,
    val BENEFICIARYID: String,
    val beneaccno: String,
    val benemobile: String,
    val benename: String,
    val bankid: String,
    val banknameUnique: String,
    val IFSC: String,
    val URL: String
) : Serializable