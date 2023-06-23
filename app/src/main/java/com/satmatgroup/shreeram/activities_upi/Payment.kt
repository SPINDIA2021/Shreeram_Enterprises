package com.satmatgroup.shreeram.activities_upi

import java.io.Serializable

class Payment : Serializable {

    private var vpa: String? = null
    private var name: String? = null
    private var payeeMerchantCode: String? = null
    private var txnId: String? = null
    private var txnRefId: String? = null
    private var description: String? = null
    private var amount: String? = null
    private var defaultPackage: String? = null
    private val currency = "INR"

    fun getVpa(): String? {
        return vpa
    }

    fun setVpa(vpa: String?) {
        this.vpa = vpa
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getPayeeMerchantCode(): String? {
        return payeeMerchantCode
    }

    fun setPayeeMerchantCode(payeeMerchantCode: String?) {
        this.payeeMerchantCode = payeeMerchantCode
    }

    fun getTxnId(): String? {
        return txnId
    }

    fun setTxnId(txnId: String?) {
        this.txnId = txnId
    }

    fun getTxnRefId(): String? {
        return txnRefId
    }

    fun setTxnRefId(txnRefId: String?) {
        this.txnRefId = txnRefId
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getAmount(): String? {
        return amount
    }

    fun setAmount(amount: String?) {
        this.amount = amount
    }

    fun getCurrency(): String? {
        return currency
    }

    fun getDefaultPackage(): String? {
        return defaultPackage
    }

    fun setDefaultPackage(defaultPackage: String?) {
        this.defaultPackage = defaultPackage
    }

}