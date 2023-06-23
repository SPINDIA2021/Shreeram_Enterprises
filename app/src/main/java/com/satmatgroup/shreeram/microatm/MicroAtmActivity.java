package com.satmatgroup.shreeram.microatm;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.fingpay.microatmsdk.HistoryScreen;
import com.fingpay.microatmsdk.MicroAtmLoginScreen;
import com.fingpay.microatmsdk.data.MiniStatementModel;
import com.fingpay.microatmsdk.utils.Constants;
import com.satmatgroup.shreeram.R;
import com.satmatgroup.shreeram.model.UserModel;
import com.satmatgroup.shreeram.network_calls.AppApiCalls;
import com.satmatgroup.shreeram.utils.AppCommonMethods;
import com.satmatgroup.shreeram.utils.AppConstants;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MicroAtmActivity extends Activity implements AppApiCalls.OnAPICallCompleteListener {
    private Context context = MicroAtmActivity.this;
    public static final String MICRO_ATM_TRANSACTION = "MICRO_ATM_TRANSACTION";

    private EditText merchIdEt, passwordEt, mobileEt, amountEt, remarksEt;
    private RadioGroup radioGroup;
    private Button fingPayBtn, historyBtn;
    private TextView respTv;

    private String latitude;
    private String longitude;

    String url = "https://edigitalvillage.net/index.php/aeps/submitMicroAtmResponse";
    boolean status;
    String response;
    double transAmount;
    double balAmount;
    String bankRrn;
    String transType;
    int type;
    String cardNum;
    String bankName;
    String cardType;
    String terminalId;
    String fpId;
    String transId;


    String merchantId;
    String password;
    String mobile;
    String amount;
    String remarks;


    LinearLayout ll_fields, ll_balance_inquiry, ll_cash_withdrawal;


    private static final int CODE = 1;

    public static final String SUPER_MERCHANT_ID = "927";

    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };
    private SharedPreferences permissionStatus;

    UserModel userModel;

    String cus_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micro_atm);


        Bundle bundle = getIntent().getExtras();
        cus_id = bundle.getString("cus_id");

        merchantId = bundle.getString("aeps_merchantLoginId");
        password = bundle.getString("aeps_merchantLoginPin");

        latitude = bundle.getString("latitude");
        longitude = bundle.getString("longitude");


        ll_fields = findViewById(R.id.ll_fields);
        ll_balance_inquiry = findViewById(R.id.ll_balance_inquiry);

        ll_balance_inquiry.setOnClickListener(listener);
        ll_cash_withdrawal = findViewById(R.id.ll_cash_withdrawal);

        ll_cash_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                ll_fields.setVisibility(View.VISIBLE);
            }
        });


/*        ll_balance_inquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imei = "1234";
                Intent intent = new Intent(MainActivity.this, MicroAtmLoginScreen.class);
                intent.putExtra(Constants.TYPE, Constants.BALANCE_ENQUIRY);
                intent.putExtra(Constants.MICROATM_MANUFACTURER, Constants.MoreFun);
                intent.putExtra(Constants.IMEI, imei);
                startActivityForResult(intent, CODE);
                String s = "fingpay" + String.valueOf(new Date().getTime());
                intent.putExtra(Constants.TXN_ID, s);
                Utils.logD(s);

                double lat = 17.4442015, lng = 78.4808421;  // get current location and send these values
                intent.putExtra(Constants.LATITUDE, lat);
                intent.putExtra(Constants.LONGITUDE, lng);
                ll_fields.setVisibility(View.GONE);
                //transactionDetails();
            }
        });*/


        permissionStatus = getSharedPreferences("microatm_sample", 0);


//        merchIdEt = findViewById(R.id.et_merch_id);
//        passwordEt = findViewById(R.id.et_merch_pin);
        mobileEt = findViewById(R.id.et_mobile);
        amountEt = findViewById(R.id.et_amount);
        remarksEt = findViewById(R.id.et_remarks);

        //radioGroup = findViewById(R.id.rg_type);

        fingPayBtn = findViewById(R.id.btn_fingpay);
        fingPayBtn.setOnClickListener(listener);


        historyBtn = findViewById(R.id.btn_history);
        historyBtn.setOnClickListener(listener);


        respTv = findViewById(R.id.tv_transaction);

        checkPermissions();

    }


    //RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
/*    private void transactionDetails() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");

                            if (status.equals("true")) {

                                Toast.makeText(MicroAtmActivity.this, "Data Uploaded", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MicroAtmActivity.this, "Error in uploading", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MicroAtmActivity.this, "my error :" + error, Toast.LENGTH_LONG).show();
                        Log.e("My error", "" + error);

                    }
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                final HashMap<String, String> params = new HashMap<String, String>();
                params.put("status", String.valueOf(status));
                params.put("response", response);
                params.put("transAmount", String.valueOf(transAmount));
                params.put("balAmount", String.valueOf(balAmount));
                params.put("bankRrn", bankRrn);
                params.put("transType", transType);
                params.put("type", String.valueOf(type));
                params.put("cardNum", cardNum);
                params.put("bankName", bankName);
                params.put("cardType", cardType);
                params.put("terminalId", terminalId);
                params.put("fpId", fpId);
                params.put("transId", transId);
                params.put("cus_id", "59");
                params.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MTE4MTMyNDUsImV4cCI6MTYxMTgzMTI0NSwiaXNzIjoiaHR0cHM6XC9cL3Byb2ZpdHBheS5jby5pblwvIiwiZGF0YSI6eyJjdXNfaWQiOm51bGwsImN1c19uYW1lIjpudWxsLCJjdXNfbW9iaWxlIjoiOTg3NjU0MzIxMiIsImN1c19wYXNzd29yZCI6Ijk4NzQ1NiJ9fQ.wa1b2Z1O2bQHLlu_zOLQ8AVszFd6l4Kr3zM1i3hWg9M");
                return params;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("X-API-KEY", "QAnrR*\"&<}q=3x.qY|Kbf@:a:lEFxF");
                headers.put("Authorization", "Basic cHJvZml0cGF5OnByb2ZpdHBheUA0MzIx");
                return headers;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }*/


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String imei = getImei();
            //String imei = "1234";
            switch (view.getId()) {
                case R.id.btn_fingpay:

                    mobile = mobileEt.getText().toString().trim();
                    amount = amountEt.getText().toString().trim();
                    remarks = remarksEt.getText().toString().trim();

                    if (isValidString(merchantId)) {
                        if (isValidString(password)) {
                            Utils.dissmissKeyboard(merchIdEt);
                            Intent intent = new Intent(MicroAtmActivity.this, MicroAtmLoginScreen.class);
                            intent.putExtra(Constants.MERCHANT_USERID, merchantId);
                            // this MERCHANT_USERID be given by FingPay depending on the merchant, only that value need to sent from App to SDK

                            intent.putExtra(Constants.MERCHANT_PASSWORD, password);
                            // this MERCHANT_PASSWORD be given by FingPay depending on the merchant, only that value need to sent from App to SDK

                            intent.putExtra(Constants.AMOUNT, amount);
                            intent.putExtra(Constants.REMARKS, remarks);


                            intent.putExtra(Constants.MOBILE_NUMBER, mobile);
                            // send a valid 10 digit mobile number from the app to SDK

                            intent.putExtra(Constants.AMOUNT_EDITABLE, false);
                            // send true if Amount can be edited in the SDK or send false then Amount cant be edited in the SDK

//                            double val = Math.random();
//                            String txn = "Fp" + val;
//                            txn = txn.replaceAll(".", "");
//                            txn = txn.substring(0,20);

//                            Log.d("Test", "Tx :" +txn);
//                            intent.putExtra(Constants.TXN_ID, txn);
                            String s = "fingpay" + String.valueOf(new Date().getTime());
                            intent.putExtra(Constants.TXN_ID, s);
                            Utils.logD(s);
                            // some dummy value is given in TXN_ID for now but some proper value should come from App to SDK

                            intent.putExtra(Constants.SUPER_MERCHANTID, SUPER_MERCHANT_ID);
                            // this SUPER_MERCHANT_ID be given by FingPay to you, only that value need to sent from App to SDK

                            intent.putExtra(Constants.IMEI, imei);

                            double lat = 17.4442015, lng = 78.4808421;  // get current location and send these values
                            intent.putExtra(Constants.LATITUDE, lat);
                            intent.putExtra(Constants.LONGITUDE, lng);
                            //intent.putExtra(Constants.TYPE, Constants.CASH_WITHDRAWAL);
                            /*int id = radioGroup.getCheckedRadioButtonId();
                            switch (id) {
                                case R.id.rb_cw:
                                    intent.putExtra(Constants.TYPE, Constants.CASH_WITHDRAWAL);
                                    break;

                                case R.id.rb_cd:
                                    intent.putExtra(Constants.TYPE, Constants.CASH_DEPOSIT);
                                    break;

                                case R.id.rb_be:
                                    intent.putExtra(Constants.TYPE, Constants.BALANCE_ENQUIRY);
                                    break;

                                case R.id.rb_ms:
                                    intent.putExtra(Constants.TYPE, Constants.MINI_STATEMENT);
                                    break;

                                case R.id.rb_rp:
                                    intent.putExtra(Constants.TYPE, Constants.PIN_RESET);
                                    break;

                                case R.id.rb_cp:
                                    intent.putExtra(Constants.TYPE, Constants.CHANGE_PIN);
                                    break;

                                case R.id.rb_ca:
                                    intent.putExtra(Constants.TYPE, Constants.CARD_ACTIVATION);
                                    break;
                            }
*/
                            intent.putExtra(Constants.MICROATM_MANUFACTURER, Constants.MoreFun);

                            startActivityForResult(intent, CODE);
                        } else {
                            Toast.makeText(context, "Please enter the password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Please enter the merchant id", Toast.LENGTH_SHORT).show();
                    }
                    break;


                case R.id.btn_history:

                    Utils.dissmissKeyboard(merchIdEt);
                    Intent intent = new Intent(MicroAtmActivity.this, HistoryScreen.class);
                    intent.putExtra(Constants.MERCHANT_USERID, merchantId);
                    // this MERCHANT_USERID be given by FingPay depending on the merchant, only that value need to sent from App to SDK

                    intent.putExtra(Constants.MERCHANT_PASSWORD, password);
                    // this MERCHANT_PASSWORD be given by FingPay depending on the merchant, only that value need to sent from App to SDK

                    intent.putExtra(Constants.SUPER_MERCHANTID, SUPER_MERCHANT_ID);
                    // this SUPER_MERCHANT_ID be given by FingPay to you, only that value need to sent from App to SDK

                    intent.putExtra(Constants.IMEI, imei);

                    startActivity(intent);

                    break;


                case R.id.ll_balance_inquiry:

                    Log.d("MERCHANTID",merchantId);
                    Log.d("PASSWORD",password);



                    if (isValidString(merchantId)) {
                        if (isValidString(password)) {
                            Utils.dissmissKeyboard(merchIdEt);
                            intent = new Intent(MicroAtmActivity.this, MicroAtmLoginScreen.class);
                            intent.putExtra(Constants.MERCHANT_USERID, merchantId);
                            // this MERCHANT_USERID be given by FingPay depending on the merchant, only that value need to sent from App to SDK

                            intent.putExtra(Constants.MERCHANT_PASSWORD, password);
                            // this MERCHANT_PASSWORD be given by FingPay depending on the merchant, only that value need to sent from App to SDK

                            intent.putExtra(Constants.AMOUNT, amount);
                            intent.putExtra(Constants.REMARKS, remarks);


                            intent.putExtra(Constants.MOBILE_NUMBER, mobile);
                            // send a valid 10 digit mobile number from the app to SDK

                            intent.putExtra(Constants.AMOUNT_EDITABLE, false);
                            // send true if Amount can be edited in the SDK or send false then Amount cant be edited in the SDK

                            double val = Math.random();
                            String txn = "Fp" + val;
                            txn = txn.replaceAll(".", "");
                            Log.d("Test", "Tx :" + txn);
                            intent.putExtra(Constants.TXN_ID, txn);
                            String s = "fingpay" + String.valueOf(new Date().getTime());
                            intent.putExtra(Constants.TXN_ID, s);
                            Utils.logD(s);
                            // some dummy value is given in TXN_ID for now but some proper value should come from App to SDK

                            intent.putExtra(Constants.SUPER_MERCHANTID, SUPER_MERCHANT_ID);
                            // this SUPER_MERCHANT_ID be given by FingPay to you, only that value need to sent from App to SDK

                            intent.putExtra(Constants.IMEI, imei);

                            double lat = 17.4442015, lng = 78.4808421;  // get current location and send these values
                            intent.putExtra(Constants.LATITUDE, lat);
                            intent.putExtra(Constants.LONGITUDE, lng);
                            intent.putExtra(Constants.TYPE, Constants.BALANCE_ENQUIRY);
                            //Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();

                            intent.putExtra(Constants.MICROATM_MANUFACTURER, Constants.MoreFun);

                            startActivityForResult(intent, CODE);
                        } else {
                            Toast.makeText(context, "Please enter the password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Please enter the merchant id", Toast.LENGTH_SHORT).show();
                    }
                    break;

                default:
                    break;
            }
        }
    };

    public static boolean isValidString(String str) {
        if (str != null) {
            str = str.trim();
            if (str.length() > 0)
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CODE) {
            Toast.makeText(context, "res" + data.getExtras().toString(), Toast.LENGTH_SHORT).show();
            Utils.logD(data.getExtras().toString());
            status = data.getBooleanExtra(Constants.TRANS_STATUS, false);
            response = data.getStringExtra(Constants.MESSAGE);
            transAmount = data.getDoubleExtra(Constants.TRANS_AMOUNT, 0);
            balAmount = data.getDoubleExtra(Constants.BALANCE_AMOUNT, 0);
            bankRrn = data.getStringExtra(Constants.RRN);
            transType = data.getStringExtra(Constants.TRANS_TYPE);
            type = data.getIntExtra(Constants.TYPE, Constants.CASH_WITHDRAWAL);
            cardNum = data.getStringExtra(Constants.CARD_NUM);
            bankName = data.getStringExtra(Constants.BANK_NAME);
            cardType = data.getStringExtra(Constants.CARD_TYPE);
            terminalId = data.getStringExtra(Constants.TERMINAL_ID);
            fpId = data.getStringExtra(Constants.FP_TRANS_ID);
            Log.e("FPID", fpId);
            transId = data.getStringExtra(Constants.TXN_ID);
            Log.e("TransId", transId);

            if (type == Constants.MINI_STATEMENT) {
                List<MiniStatementModel> l = data.getParcelableArrayListExtra(Constants.LIST);
                if (Utils.isValidArrayList((ArrayList<?>) l)) {
                    Utils.logD(l.toString());
                }
            }

            if (isValidString(response)) {
                if (!isValidString(bankRrn))
                    bankRrn = "";
                if (!isValidString(transType))
                    transType = "";

                String s = "Status :" + status + "\n" + "Message : " + response + "\n"
                        + "Trans Amount : " + transAmount + "\n" + "Balance Amount : " + balAmount + "\n"
                        + "Bank RRN : " + bankRrn + "\n" + "Trand Type : " + transType + "\n"
                        + "Type : " + type + "\n" + "Card Num :" + cardNum + "\n" + "CardType :" + cardType + "\n" + "Bank Name :" + bankName + "\n" + "Terminal Id :" + terminalId;

                microAtmTransaction(cus_id, String.valueOf(status), response, String.valueOf(transAmount), String.valueOf(balAmount), bankRrn, transType,
                        String.valueOf(type), cardNum, bankName, cardType, terminalId, fpId, transId);
                Utils.logD("micro atm result is:" + s);
                respTv.setText(s);
                respTv.setVisibility(View.VISIBLE);

            }
        } else if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(MicroAtmActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
            }
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(context, "cancelled", Toast.LENGTH_SHORT).show();
            respTv.setText("");
        }
    }

    public String getImei() {
        String imei = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            imei = telephonyManager.getDeviceId();

        } catch (Exception e) {
            Utils.logE(e.toString());
            if (!isValidString(imei))
                imei = Settings.Secure.getString(context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            Utils.logD("IMEI: " + imei);

        }
        return imei;
    }


    private List<String> getUngrantedPermissions() {
        List<String> permissions = new ArrayList<>();

        for (String s : permissionsRequired) {
            if (ContextCompat.checkSelfPermission(context, s) != PackageManager.PERMISSION_GRANTED)
                permissions.add(s);
        }

        return permissions;
    }

    private void checkPermissions() {
        List<String> permissions = getUngrantedPermissions();
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(MicroAtmActivity.this,
                    permissions.toArray(new String[permissions.size()]),
                    PERMISSION_CALLBACK_CONSTANT);

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], true);
            editor.commit();
        } else {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {

            } else if (Utils.isValidArrayList((ArrayList<?>) getUngrantedPermissions())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MicroAtmActivity.this);
                builder.setTitle(getString(R.string.need_permissions));
                builder.setMessage(getString(R.string.device_permission));
                builder.setPositiveButton(getString(R.string.grant), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(MicroAtmActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton(getString(R.string.cancel_btn), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getBaseContext(), getString(R.string.unable_toget_permission), Toast.LENGTH_LONG).show();
            }
        }
    }


    private void microAtmTransaction(String cus_id, String status, String response,
                                     String transAmount, String balAmount,
                                     String bankRrn, String transType, String type,
                                     String cardNum, String bankName, String cardType,
                                     String terminalId, String fpId, String transId) {
        if (new AppCommonMethods(this).isNetworkAvailable()) {


            AppApiCalls mAPIcall = new AppApiCalls(this, MICRO_ATM_TRANSACTION, this);
            mAPIcall.microAtmTransaction(cus_id, status, response,
                    transAmount, balAmount,
                    bankRrn, transType, type,
                    cardNum, bankName, cardType,
                    terminalId, fpId, transId);
        } else {
            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAPICallCompleteListner(@Nullable Object item, @Nullable String flag, @NotNull String result) throws JSONException {
        if (flag.equals(MICRO_ATM_TRANSACTION)) {
            Log.e(" MICRO_ATM_TRANSACTION", result);
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString(AppConstants.STATUS);

            Log.e(AppConstants.STATUS, status);
            if (status.contains("true")) {


              //  JSONObject aepsRes = jsonObject.getJSONObject("result");
//
//                String aepsStatus = aepsRes.getString("status");
//                String aepsMessage = aepsRes.getString("message");

                if (status.equals("true")) {

                    Toast.makeText(MicroAtmActivity.this, "Data Uploaded", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MicroAtmActivity.this, "Error in uploading", Toast.LENGTH_SHORT).show();
                }


            } else {
//                toast("Beneficiary Adding Failed")


            }
        }

    }
}



