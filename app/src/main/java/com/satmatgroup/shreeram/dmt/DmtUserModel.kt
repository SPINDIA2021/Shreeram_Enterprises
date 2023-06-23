package com.satmatgroup.shreeram.dmt

import java.io.Serializable

data class DmtUserModel(
    val dmt_user_id: String,
    val registered_date: String,
    val user_mobile: String,
    val user_name: String
) : Serializable