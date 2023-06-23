package com.satmatgroup.shreeram.model

data class UserModel(
    val cus_id: String,
    val cus_mobile: String,
    val cus_name: String,
    val cus_pass: String,
    val cus_pin: String,
    val cus_email: String,
    val cus_type: String,
    val package_id: String,
    val profile_img: String
)