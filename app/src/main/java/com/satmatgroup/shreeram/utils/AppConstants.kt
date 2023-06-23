package com.satmatgroup.shreeram.utils

interface AppConstants {
    companion object {
        const val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        const val IS_LOGIN = "isLogin"
        const val MOBILE_REGEX =
            "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$"
        const val PANCARD_REGEX = "[A-Z]{3}[ABCFGHLJPTF]{1}[A-Z]{1}[0-9]{4}[A-Z]{1}"
        const val ONLY_ALPHABETS = "^[\\p{L} .'-]+$";
        const val REMOVE_SPECIAL_CHARS = "[^0-9a-zA-Z]+"
        const val PASSWORD_EIGHT_TWENTY = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,20})"

        const val PIDDATA = "PidData"
        //MODEL FOR SHARED PREFS

        const val USER_MODEL = "userModel"
        const val USER_ID = "user_id"
        //API FLAGS
        const val LOGIN_API = "LOGIN_API"
        const val REGISTER_API = "REGISTER_API"
        const val DASHBOARD_API = "DASHBOARD_API"
        const val BALANCE_API = "BALANCE_API"
        const val AEPS_BALANCE_API = "AEPS_BALANCE_API"
        const val PROFILE_API = "PROFILE_API"
        const val OPERATOR_API = "OPERATOR_API"
        const val ELECTRICITY_OPERATOR_API = "ELECTRICITY_OPERATOR_API"
        const val CHECK_SAME_RECHARGE_API = "CHECK_SAME_RECHARGE_API"
        const val VERFY_PIN_API = "VERFY_PIN_API"
        const val RECHARGE_API = "RECHARGE_API"
        const val REC_HIST_API = "REC_HIST_API"
        const val REC_HISTBYDATE_API = "REC_HISTBYDATE_API"

        //HEADER CONSTANTS
        const val FORM_URL_ENCODED = "application/x-www-form-urlencoded"
        const val API_KEY = "QAnrR*\"&<}q=3x.qY|Kbf@:a:lEFxF"
        const val BASIC = "Basic"
        const val BASIC_TOKEN = "cHJvZml0cGF5OnByb2ZpdHBheUA0MzIx"

        //CONSTANTS
        const val PROFILE = "PROFILE"
        const val AADHAAR = "AADHAAR"
        const val PAN = "PAN"
        const val OPERATOR_MOBILE = "mobile"
        const val OPERATOR_POSTPAID = "postpaid"
        const val OPERATOR_DTH = "dth"

        //API RESPONSE CONSTANTS
        const val WALLETBALANCE = "walletBalance"
        const val AEPSBALANCE = "aepsBalance"
        const val NEWS = "news"
        const val BANNER = "banner"
        const val CUS_DATA = "cusData"
        const val CUS_MOBILE = "mobile"

        //API CONSTANTS
        const val STATUS = "status"
        const val RESULT = "result"
        const val MESSAGE = "message"
        const val MESS = "mess"
        const val TOKEN = "token"
        const val TRUE = "true"
        const val FALSE = "false"
        const val USER_NAME = "user_name"
        const val MOBILE = "user_mobile"
        const val EMAIL = "email"
        const val ADDRESS = "user_address"
        const val AADHAR_NUM = "aadhaar_number"
        const val ENCODED_AADHAAR_IMG = "aadhaar_img"
        const val ENCODED_PANCARD_IMG = "pancard_img"
        const val ENCODED_PROFILE_IMG = "profile_img"
        const val PASSWORD = "user_password"
        const val DEVICE_NAME = "deviceName"
        const val DEVICE_ID = "deviceId"

        const val CUS_MOBILE_API = "cus_mobile"
        const val CUS_ID = "cus_id"
        const val OPERATOR_TYPE = "operator_type"
        const val AMOUNT = "amount"
        const val REC_MOBILE = "recmobile"
        const val OPERATOR = "operator"
        const val PIN = "pin"
        const val MOBILE_RECHARGE = "mobile"
        const val CUS_TYPE = "cus_type"
        const val FROM_DATE = "fromDate"
        const val TO_DATE = "toDate"
        const val DATE = "date"


    }
}
