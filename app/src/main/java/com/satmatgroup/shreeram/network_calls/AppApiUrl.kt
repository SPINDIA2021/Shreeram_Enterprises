package com.satmatgroup.shreeram.network_calls

import com.satmatgroup.shreeram.utils.AppConstants


object AppApiUrl : AppConstants {
    //Production Environment
    var BASE_URL = "http://shreeramenterprises.org.in"
   // var BASE_URL = "http://aeps.shreeramenterprises.org.in/"
    var OFFERS_URL = BASE_URL

    var IMAGE_URL = "http://shreeramenterprises.org.in"

    //API URLS


    //GENERAL APP USER URLS

    //url =/applogin/
    val LOGIN_BY_PASSWORD: String = "$BASE_URL/applogin/userLogin"
    val REGISTER_USER: String = "$BASE_URL/applogin/registerenquiry"
    val REGISTER: String = "$BASE_URL/applogin/register_user"
    val GETFORGOTOTP: String = BASE_URL + "/applogin/getpassotp"
    val FORGOT_PASS: String = BASE_URL + "/applogin/forgetPass"
    val NOTE: String = BASE_URL+"/applogin/note"

    // url =/appapi/
    val GET_AEPS_BALANCE: String = "$BASE_URL/appapi/aepsBalance"

    val GET_DASHBOARD: String = "$BASE_URL/appapi/dashboard"
    val GET_BALANCE: String = "$BASE_URL/appapi/walletBalance"
    val GET_PROFILE: String = "$BASE_URL/appapi/profile"
    val GET_OPERATORS: String = "$BASE_URL/appapi/getMobileOperators"
    val GET_ELECTIRCITY_OPERATORS: String = "$BASE_URL/appapi/getOperators"
    val CHECK_IF_SAME_RECHARGE: String = "$BASE_URL/appapi/checkIfSameRecharge"
    val VERIFY_PIN: String = "$BASE_URL/appapi/verifyPin"
    val RECHARGE_HISTORY: String = "$BASE_URL/appapi/rechargeHistoryFromTo"

    val CIRCLE: String = "$BASE_URL/appapi/circles"


    //*********************Do Not Copy*********************************//
    val CHANGE_PIN: String = BASE_URL + "/appapi/changepin"
    val FUND_CREDIT: String = BASE_URL + "/appapi/viewcreditwallet"
    val FUND_DEBIT: String = BASE_URL + "/appapi/viewdebitwallet"
    val CHECKSAME_FUNDTRANSFER: String = BASE_URL + "/appapi/checkifsamefundtransfer"
    val LEDGER_REPORT: String = BASE_URL + "/appapi/ledgerfromto"
    val COMMISION_REPORT_URL: String = BASE_URL + "/appapi/getcommslab"
    val BROWSE_PLANS = OFFERS_URL + "/appapi/roffer"
    val BROWSE_PLANS_DTH = OFFERS_URL + "/appapi/Dthinfo"
    val GETBILLDETAILS = OFFERS_URL + "/appapi/ElectricityInfo"
    val DISSPUTE_HISTORY: String = BASE_URL + "/appapi/disputehistory"
    val NEWUSER_URL: String = BASE_URL + "/appapi/create_retailer_api"
    val NEW_DISTRIBUTOR_URL: String = BASE_URL + "/appapi/create_distributor_api"
    val USER_LIST: String = BASE_URL + "/appapi/user_list"
    val GET_USER_ID: String = BASE_URL + "/appapi/getcusid"
    val FUND_TRANSFER: String = BASE_URL + "/appapi/direct_credit"
    val RAISE_DISPUTE: String = BASE_URL + "/appapi/submitdispute"
    val FUND_REQUEST_URL: String = BASE_URL + "/appapi/fundreq"
    val GET_SUPPORT: String = BASE_URL + "/appapi/support"
    val USER_DAYBOOK: String = BASE_URL + "/appapi/userdaybook"
    val UPDATE_WALLET: String = BASE_URL + "/appapi/add_wallet_balance"
    val GET_UPIDETAILS: String = BASE_URL + "/appapi/getupidetails"
    val LOGOUT_USER: String = BASE_URL + "/appapi/userlogout"
    val GETPINOTP: String = BASE_URL + "/appapi/getpinotp"
    val FORGETPIN: String = BASE_URL + "/appapi/forgetpin"

    val FUND_MYREQUEST: String = BASE_URL + "/appapi/viewmyfundreq"

    val USER_SEARCH: String = BASE_URL + "/appapi/user_list_byname_or_mobile"

    val CHANGE_PASWORD: String = BASE_URL + "/appapi/changepassword"

    val RECHARGE_HISTORY_BY_MOBILE: String = BASE_URL + "appapi/rechargehistorybymobile"

    val RECHARGE_HISTORY_BY_DATE: String = BASE_URL + "appapi/rechargehistorybydate"

    //DMT APIS
    val DMT_LOGIN: String =
        BASE_URL + "/dmt/verifySender"

    val DMT_SENDOTP: String =
        BASE_URL + "/dmt/verifySenderOtp"

    val DMT_REGISTER: String =
        BASE_URL + "/dmt/registerSender"

    val DMT_ADD_BENFICIARY: String =
        BASE_URL + "/dmt/addBeneficiary"

    val DMT_VIEW_BENIFICIARY: String =
        BASE_URL + "/dmt/getBeneficiary"

    val DELETE_RECIPIENT: String =
        BASE_URL + "/dmt/deleteBeneficiary"

    val GET_CHARGE: String =
        BASE_URL + "/dmt/getCharge"

    val DMT_TRANSACTION: String =
        BASE_URL + "/dmt/moneyTransfer"

    val DMT_HISTORY: String =
        BASE_URL + "/dmt/getDmtHistory"

    val CHECK_STATUS: String =
        BASE_URL + "/dmt/checkStatus"

    val DMT_BANK_LIST: String =
        BASE_URL + "/dmt/getBankNames"

    val VERIFY_BANK: String =
        BASE_URL + "/dmt/accountValidation"

    val DMT_COMMISIONSLAB_URL: String =
        BASE_URL + "/dmt/dmt_commission_slab"

    val GET_DMT_SERVICE: String =
        BASE_URL + "/dmt/getDmtApiList"

    //AEPS
    val GET_AEPS_CHARGE: String =
        BASE_URL + "/aeps/getCharge"

    val AEPS_HISTORY: String =
        BASE_URL + "/aeps/aeps_history"

    val AEPS_COMMISIONSLAB_URL: String =
        BASE_URL + "/aeps/aeps_commission_slab"

    val AEPS_BANK_LIST: String =
        BASE_URL + "/aeps/getbank"

    val DUMMY_PID: String =
        BASE_URL + "/aeps/getPidData"

    val AEPS_TRANSACTION: String =
        BASE_URL + "/aeps/aepsapi"

    val AEPS_PAYOUT: String =
        BASE_URL + "/aeps/submitPayout"

    val GET_PAYOUT_DETAILS: String =
        BASE_URL + "/aeps/getPayoutDetails"

    val AEPSPAYOUT_HISTORY: String =
        BASE_URL + "/aeps/payoutHistory"

    val AEPSCOMMISSION_HISTORY: String =
        BASE_URL + "/aeps/aeps_history"

    val USER_PAYOUT_BANK: String =
        BASE_URL + "/aeps/userpayoutbank"

    //MICRO ATM
    val MICRO_ATM_TRANSACTION: String =
        BASE_URL + "/aeps/submitMicroAtmResponse"

    val MICRO_ATM_LOGIN: String =
        BASE_URL + "/aeps/microAtmDetails"
    //******************************************************************//

    // url =rechargeapi/recharge
    val RECHARGE: String = "$BASE_URL/rechargeapi/recharge"

    val GET_PRODUCTS: String =
        BASE_URL + "/appapi/getproduct"

    val GET_PRODUCT_AND_SERVICE: String =
        BASE_URL + "/appapi/getproductandservice"

    val GET_SINGLE_PRODUCT_AND_SERVICE: String =
            BASE_URL + "/appapi/getsingleproductandservice"

    val GET_SERVICE_STATUS: String =
        BASE_URL + "/appapi/getservicestatus"

    val GET_SERVICE_AMOUNT: String =
        BASE_URL + "/appapi/getserviceamount"

    val BUY_SERVICE: String =
        BASE_URL + "/appapi/buyservice"

    val BUY_PRODUCT: String =
        BASE_URL + "/appapi/buyproduct"

    val PRODUCT_SERVICES_HISTORY: String =
        BASE_URL + "/appapi/productserviceshistory"

    val OFFER_POPUP: String =
        BASE_URL + "/appapi/offerpopup"

    val CASH_DEPOSIT_WITH_OTP: String =
        BASE_URL + "/aeps/cashdepositwithotp"

    val VALIDATE_CASH_DEPOSIT_OTP: String =
        BASE_URL + "/aeps/validateCdOtp"

    val BUY_PRODUCT_AND_SERVICE: String =
            BASE_URL + "/appapi/buyproductandservice"

    val KYC_ONBOARDING: String =
        BASE_URL + "aeps/onboarding"

    val EKYC: String =
        BASE_URL + "/aeps/ekycsubmit"

    val VALIDATE_EKYC_OTP: String =
        BASE_URL + "/aeps/validateekycotp"

    val RESEND_EKYC_OTP: String =
        BASE_URL + "/aeps/resendekycotp"

    val STATE_LIST: String =
        BASE_URL + "/aeps/getaepsstate"

    val CHECK_KYC_STATUS: String =
        BASE_URL + "/aeps/checkkycstatus"

    // UPI Collection

    val DYNAMIC_QR_CODE: String =
        BASE_URL + "/Upi/upicollection_dynamicqr"

    val UPI_SEND_REQUEST: String =
        BASE_URL + "/Upi/upicollection_sendrequest"
}