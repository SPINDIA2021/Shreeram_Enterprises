package com.satmatgroup.shreeram.activities_upi

class TransactionDetails {
    private var transactionId: String? = null
    private var responseCode: String? = null
    private var approvalRefNo: String? = null
     var status: String? = null
    private var transactionRefId: String? = null

    constructor(
        transactionId: String?,
        responseCode: String?,
        approvalRefNo: String?,
        status: String?,
        transactionRefId: String?
    ) {
        this.transactionId = transactionId
        this.responseCode = responseCode
        this.approvalRefNo = approvalRefNo
        this.status = status
        this.transactionRefId = transactionRefId
    }

    fun getTransactionId(): String? {
        return transactionId
    }

    fun getResponseCode(): String? {
        return responseCode
    }

    fun getApprovalRefNo(): String? {
        return approvalRefNo
    }



    fun getTransactionRefId(): String? {
        return transactionRefId
    }

    override fun toString(): String {
        return "transactionId:" + transactionId +
                ", responseCode:" + responseCode +
                ", transactionRefId:" + transactionRefId +
                ", approvalRefNo:" + approvalRefNo +
                ", status:" + status
    }
}