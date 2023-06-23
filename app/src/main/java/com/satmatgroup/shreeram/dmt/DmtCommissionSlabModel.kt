package com.satmatgroup.shreeram.dmt

data class DmtCommissionSlabModel(
    val commission_scheme_dmt_id: String,
    val scheme_id: String,
    val slab_opcode: String,
    val slab: String,
    val type: String,
    val retailer_comm: String,
    val distributor_comm: String,
    val master_comm: String,
    val api_comm: String,
    val added_datetime: String

)