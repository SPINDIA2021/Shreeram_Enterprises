package com.satmatgroup.shreeram.activities_aeps

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.AepsBankModel
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.BankAepsListAdapter
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.MantraDeviceActivity
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.reports.ReportsActivity
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import com.satmatgroup.shreeram.utils.toast
import kotlinx.android.synthetic.main.activity_aeps_transaction.*
import kotlinx.android.synthetic.main.activity_aeps_transaction.view.*
import kotlinx.android.synthetic.main.layout_list_bottomsheet_banklist.view.*
import org.json.JSONObject

class AepsTransactionActivity : AppCompatActivity(), BankAepsListAdapter.ListAdapterListener,
    AppApiCalls.OnAPICallCompleteListener, AdapterView.OnItemSelectedListener {

    var bottomSheetDialogUsers: BottomSheetDialog? = null
    lateinit var bankListAdapter: BankAepsListAdapter

    var bankListModelArrayList = ArrayList<AepsBankModel>()
    private val AEPS_BANKS: String = "AEPS_BANKS"
    private val AEPS_TRANSACTION: String = "AEPS_TRANSACTION"

    var pidData: String = ""
    var transactionType: String = ""
    var transaction: String = ""
    lateinit var userModel: UserModel
    var nationalBankIdenticationNumber: String=""
    var sendAmount = "0"
    var list_of_items = arrayOf("Mantra MFS100", "Morpho")
    lateinit var selected_device: String

    var latitudeLabel: String=""
    var longitudeLabel: String=""

    var intent1: Intent? = null
    var gpsStatus = false

    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aeps_transaction)
        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }

        checkGpsStatus()

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)

        val bundle = intent.extras

        if (bundle != null) {
            pidData = bundle.getString("pid").toString()
            transaction= bundle.getString("transaction").toString()
            transactionType= bundle.getString("transactionType").toString()
        }

        tvSelectBank.setOnClickListener {
            bankListAeps()
        }

        if(transactionType.equals("cashdeposit")) {
            textViewTransactionType.visibility = GONE
            cvRadioGroup.visibility = GONE
            ll_aepsamount.visibility = VISIBLE
        } else if( transactionType.equals("aadharpay")) {
            textViewTransactionType.visibility = GONE
            cvRadioGroup.visibility = GONE
            ll_aepsamount.visibility = VISIBLE
        }


        rbCashWithdrawal.setOnClickListener {
            transactionType = "cashwithdrawal"
            rbCashWithdrawal.isChecked = true
            if (rbCashWithdrawal.isChecked) {
                rbBalanceCheck.isChecked = false
                rbMiniStatement.isChecked = false
                rbAadharPayment.isChecked = false
                rbCashDeposit.isChecked = false
                ll_aepsamount.visibility = VISIBLE
            }
        }

        rbBalanceCheck.setOnClickListener {
            transactionType = "balancecheck"
            rbBalanceCheck.isChecked = true
            if (rbBalanceCheck.isChecked) {
                rbCashWithdrawal.isChecked = false
                rbMiniStatement.isChecked = false
                rbAadharPayment.isChecked = false
                rbCashDeposit.isChecked = false
                ll_aepsamount.visibility = GONE

            }
        }

        rbMiniStatement.setOnClickListener {
            transactionType = "ministatement"
            rbMiniStatement.isChecked = true
            if (rbMiniStatement.isChecked) {
                rbCashWithdrawal.isChecked = false
                rbBalanceCheck.isChecked = false
                rbAadharPayment.isChecked = false
                rbCashDeposit.isChecked = false
                ll_aepsamount.visibility = GONE

            }
        }

        rbCashDeposit.setOnClickListener {
            transactionType = "cashdeposit"
            rbCashDeposit.isChecked = true
            if (rbCashDeposit.isChecked) {
                rbCashWithdrawal.isChecked = false
                rbBalanceCheck.isChecked = false
                rbAadharPayment.isChecked = false
                rbMiniStatement.isChecked = false
                ll_aepsamount.visibility = VISIBLE
            }
        }

        rbAadharPayment.setOnClickListener {
            transactionType = "aadharpay"
            rbAadharPayment.isChecked = true
            if (rbAadharPayment.isChecked) {
                rbCashWithdrawal.isChecked = false
                rbBalanceCheck.isChecked = false
                rbMiniStatement.isChecked = false
                rbCashDeposit.isChecked = false
                ll_aepsamount.visibility = VISIBLE

            }
        }


        btnSubmit.setOnClickListener {

          /*  Log.e("transactionType",transactionType)
            if (etAepsAadharNo.text.toString().length < 12 || etAepsAadharNo.text.isNullOrEmpty()) {
                etAepsAadharNo.requestFocus()
                etAepsAadharNo.error = "Invalid Aadhar Number"

            } else if (!AppCommonMethods.checkForMobile(etAepsMobileNumber)) {
                etAepsMobileNumber.requestFocus()
                etAepsMobileNumber.error = "Invalid Mobile"

            } else if (tvSelectBank.text.toString().isNullOrEmpty()) {

                tvSelectBank.requestFocus()
                tvSelectBank.error = "Please Select Bank"
            } else if (transactionType.isNullOrEmpty()) {

                toast("Please Select Transaction Type")
            } else {

                if (transactionType.equals("ministatement") || transactionType.equals("balancecheck")) {
                    sendAmount = "0"

                        val bundle = Bundle()
                        bundle.putString("latitude",latitudeLabel);
                        bundle.putString("longitude",longitudeLabel);
                        bundle.putString("flag","aeps")
                        bundle.putString("cus_id", userModel.cus_id)
                        bundle.putString("aadhar_no", etAepsAadharNo.text.toString())
                        bundle.putString(
                            "nationalBankIdenticationNumber",
                            nationalBankIdenticationNumber
                        )
                        bundle.putString("mobile_no", etAepsMobileNumber.text.toString())
                        bundle.putString("transactionType", transactionType)
                        bundle.putString("sendAmount", sendAmount)


                        val intent = Intent(this, MantraDeviceActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)


                } else if (transactionType.equals("cashdeposit")) {
                    if (etAepsAmount.text.toString().isNullOrEmpty() || etAepsAmount.text.toString()
                            .toInt() <= 0
                    ) {

                        etAepsAmount.requestFocus()
                        etAepsAmount.error = "Invalid Amount"
                    } else {

                        *//*       val intent = Intent(this, AepsTransactionActivity::class.java)
                               startActivity(intent)*//*

                        val bundle = Bundle()
                        bundle.putString("latitude",latitudeLabel);
                        bundle.putString("longitude",longitudeLabel);
                        bundle.putString("flag","aeps")
                        bundle.putString("cus_id", userModel.cus_id)
                        bundle.putString("aadhar_no", etAepsAadharNo.text.toString())
                        bundle.putString(
                            "nationalBankIdenticationNumber",
                            nationalBankIdenticationNumber
                        )
                        bundle.putString("mobile_no", etAepsMobileNumber.text.toString())
                        bundle.putString("transactionType", transactionType)
                        bundle.putString("sendAmount", sendAmount)
                        bundle.putString("bankName", tvSelectBank.text.toString())

                        val intent = Intent(this, MantraDeviceActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }

                } else {
                    if (etAepsAmount.text.toString().isNullOrEmpty() || etAepsAmount.text.toString()
                            .toInt() <= 0
                    ) {

                        etAepsAmount.requestFocus()
                        etAepsAmount.error = "Invalid Amount"
                    } else {
                        sendAmount = etAepsAmount.text.toString()

                            *//*       val intent = Intent(this, AepsTransactionActivity::class.java)
                                   startActivity(intent)*//*

                            val bundle = Bundle()
                            bundle.putString("latitude",latitudeLabel);
                            bundle.putString("longitude",longitudeLabel);
                            bundle.putString("flag","aeps")
                            bundle.putString("cus_id", userModel.cus_id)
                            bundle.putString("aadhar_no", etAepsAadharNo.text.toString())
                            bundle.putString(
                                "nationalBankIdenticationNumber",
                                nationalBankIdenticationNumber
                            )
                            bundle.putString("mobile_no", etAepsMobileNumber.text.toString())
                            bundle.putString("transactionType", transactionType)
                            bundle.putString("sendAmount", sendAmount)
                            bundle.putString("bankName", tvSelectBank.text.toString())

                            val intent = Intent(this, MantraDeviceActivity::class.java)
                            intent.putExtras(bundle)
                            startActivity(intent)

                    }

                }

            }*/



            sendAmount=etAepsAmount.text.toString()
            val bundle = Bundle()
            bundle.putString("latitude",latitudeLabel);
            bundle.putString("longitude",longitudeLabel);
            bundle.putString("flag","aeps")
            bundle.putString("cus_id", userModel.cus_mobile)
            bundle.putString("aadhar_no", etAepsAadharNo.text.toString())
            bundle.putString(
                "nationalBankIdenticationNumber",
                nationalBankIdenticationNumber
            )
            bundle.putString("mobile_no", etAepsMobileNumber.text.toString())
            bundle.putString("transactionType", transactionType)
            bundle.putString("sendAmount", sendAmount)
            bundle.putString("bankName", tvSelectBank.text.toString())



            val intent = Intent(this, MantraDeviceActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        spinner!!.setOnItemSelectedListener(this)

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.setAdapter(aa)

    }

    private fun checkGpsStatus() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (gpsStatus) {

            getLocation()
        } else {
            gpsStatus()
            toast("GPS is Disabled")
        }
    }

    fun gpsStatus() {
        intent1 = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent1);
    }

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
            5000, 5f, locationListener)
    }

    //define the listener
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {

            Log.d("TAG", "onLocationChanged: "+"Latitude: " + location.latitude)
            Log.d("TAG", "onLocationChanged: "+"Longitude: " + location.longitude)

            latitudeLabel = location.latitude.toString()
            longitudeLabel = location.longitude.toString()
        }


        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selected_device = list_of_items[position]

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    fun bankListAeps(
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, AEPS_BANKS, this)
            mAPIcall.bankListAeps()
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

/*    private fun aepsTransaction(
        cus_id: String,
        txtPidData: String,
        adhaarNumber: String,
        nationalBankIdenticationNumber: String,
        mobileNumber: String,
        type: String,
        transactionAmount: String


    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, AEPS_TRANSACTION, this)
            mAPIcall.aepsTransaction(
                cus_id,
                txtPidData,
                adhaarNumber,
                nationalBankIdenticationNumber,
                mobileNumber,
                type,
                transactionAmount
            )
        } else {
            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }*/


    private fun ShowBottomSheetBankList() {
        val view: View = layoutInflater.inflate(R.layout.layout_list_bottomsheet_banklist, null)
        view.etSearchMobName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString());
            }
        })




        view.rvBankList.apply {

            layoutManager = LinearLayoutManager(this@AepsTransactionActivity)
            bankListAdapter = BankAepsListAdapter(
                context, bankListModelArrayList, this@AepsTransactionActivity
            )
            view.rvBankList.adapter = bankListAdapter
        }
        bottomSheetDialogUsers = BottomSheetDialog(this)
        bottomSheetDialogUsers!!.setContentView(view)

        val bottomSheetBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        bottomSheetDialogUsers!!.show()
    }

    override fun onClickAtOKButton(mobileRechargeModal: AepsBankModel?) {

        if (mobileRechargeModal != null) {
            tvSelectBank.setText(mobileRechargeModal.bankName)
            nationalBankIdenticationNumber = mobileRechargeModal.iinno
        }

        bottomSheetDialogUsers!!.dismiss()
    }


    fun filter(text: String) {
        val temp: MutableList<AepsBankModel> = ArrayList()
        for (d in bankListModelArrayList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.bankName.contains(text, ignoreCase = true)) {

                temp.add(d)
            }
        }
        //update recyclerview
        bankListAdapter.updateList(temp)
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(AEPS_BANKS)) {
            bankListModelArrayList.clear()
            Log.e("AEPS_BANKS", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                progress_bar.visibility = View.INVISIBLE

                val cast = jsonObject.getJSONArray("result")

                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val aeps_bank_id = notifyObjJson.getString("aeps_bank_id")
                    Log.e("aeps_bank_id ", aeps_bank_id)
                    val bankListModel = Gson()
                        .fromJson(
                            notifyObjJson.toString(),
                            AepsBankModel::class.java
                        )


                    bankListModelArrayList.add(bankListModel)
                }

                ShowBottomSheetBankList()

            } else {
                progress_bar.visibility = View.INVISIBLE
            }
        }
        if (flag.equals(AEPS_TRANSACTION)) {
            Log.e("AEPS_TRANSACTION", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val result = jsonObject.getString(AppConstants.RESULT)

            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {
                progress_bar.visibility = View.INVISIBLE


                val aepsRes = jsonObject.getJSONObject("result")

                val aepsStatus = aepsRes.getString("status")
                val aepsMessage = aepsRes.getString("message")
                if (aepsStatus.equals("false")) {

                    toast(aepsMessage)
                } else {
                    toast(aepsMessage)

                    val intent = Intent(this, ReportsActivity::class.java)
                    startActivity(intent)
                }


            } else {
                progress_bar.visibility = View.INVISIBLE
//                toast("Beneficiary Adding Failed")


            }
        }

    }

}