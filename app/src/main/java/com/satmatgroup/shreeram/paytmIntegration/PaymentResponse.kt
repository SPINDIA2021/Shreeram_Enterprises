package com.`in`.spindiabrand.paytmIntegration

class PaymentResponse {
    var Bundle: ArrayList<BundleData>? = null

    inner class BundleData {
        var sTATUS: String? = null
        var cHECKSUMHASH: String? = null
        var bANKNAME: String? = null
        var oRDERID: String? = null
        var tXNAMOUNT: String? = null
        var tXNDATE: String? = null
        var mID: String? = null
        var tXNID: String? = null
        var rESPCODE: String? = null
        var pAYMENTMODE: String? = null
        var bANKTXNID: String? = null
        var cURRENCY: String? = null
        var gATEWAYNAME: String? = null
        var rESPMSG: String? = null
    }
}