package com.satmatgroup.shreeram.activities_aeps.aepsfinger

data class AepsBankModel(
    val aeps_bank_id: String,
    val bankName: String,
    val iinno: String,
    var selected: Boolean=false
)