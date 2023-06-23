package com.satmatgroup.shreeram.model

import java.io.Serializable


class RecentRechargeHistoryModal : Serializable {
    var operator_image: String? = null
    var amount: String? = null
    var retailer: String? = null
    var reqdate: String? = null
    var mobileno: String? = null
    var statusdesc: String? = null
    var distributor: String? = null
    var dispute_status: String? = null
    var operator: String? = null
    var master: String? = null
    var txn_id: String? = null
    var cus_name: String? = null
    var txn_clbal: String? = null
    var recmedium: String? = null
    var recid: String? = null
    var txn_opbal: String? = null
    var status: String? = null
    var operatorname: String? = null
    var requestertxnid: String?= null

    override fun toString(): String {
        return "ClassPojo [operator_image = $operator_image, amount = $amount, retailer = $retailer, reqdate = $reqdate, mobileno = $mobileno, statusdesc = $statusdesc, distributor = $distributor, dispute_status = $dispute_status, operator = $operator, master = $master, txn_id = $txn_id, cus_name = $cus_name, txn_clbal = $txn_clbal, recmedium = $recmedium, recid = $recid, txn_opbal = $txn_opbal, status = $status, operatorname = $operatorname, requestertxnid = $requestertxnid]"
    }
}

