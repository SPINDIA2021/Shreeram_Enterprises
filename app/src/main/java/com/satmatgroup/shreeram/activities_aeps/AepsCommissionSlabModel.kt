package com.satmatgroup.shreeram.activities_aeps

data class AepsCommissionSlabModel(
    val commission_scheme_aeps_id: String,
    val scheme_id: String,
    val slab_opcode: String,
    val slab: String,
    val type: String,
    val retailer_comm: String,
    val distributor_comm: String,
    val master_comm: String,
    val api_comm: String,
    val amount_min_range: String,
    val amount_max_range: String,
    val added_datetime: String,
    val aeps_comm_id: String,
    val member_type: String,
    val percent: String,
    val maximum_comm: String,
    val updated_time: String
)