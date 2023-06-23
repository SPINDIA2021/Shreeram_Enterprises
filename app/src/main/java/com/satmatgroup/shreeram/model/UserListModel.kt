package com.satmatgroup.shreeram.model

import java.io.Serializable

data class UserListModel(
    val clbal: String?,
    val cus_id: String,
    val cus_mobile: String,
    val cus_name: String
) : Serializable