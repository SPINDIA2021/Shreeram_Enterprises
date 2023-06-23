package com.satmatgroup.shreeram.activities_aeps

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.paysprint.onboardinglib.activities.HostActivity
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.user_profile.KycVerificationActivity
import com.satmatgroup.shreeram.utils.*
import kotlinx.android.synthetic.main.activity_aeps_kyc_details.*
import kotlinx.android.synthetic.main.activity_aeps_kyc_details.custToolbar
import kotlinx.android.synthetic.main.activity_aeps_kyc_details.view.*
import kotlinx.android.synthetic.main.layout_dialog_kyc_otp.etOtp
import kotlinx.android.synthetic.main.layout_dialog_kyc_otp.*
import org.json.JSONObject
import java.io.IOException
import java.util.*

class AepsKycDetailsActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener {

    private val GALLERY = 1
    private var CAMERA = 2
    private val PERMISSION_REQUEST_CODE = 200
    var fromImageView: String = ""

    lateinit var encoded_adhaar_front_img: String
    lateinit var encoded_adhaar_back_img: String
    lateinit var encoded_pancard_img: String

    lateinit var merchant_name : String
    lateinit var merchant_mobile : String
    lateinit var merchant_email : String
    lateinit var company_legal_name : String
    lateinit var company_marketing_name : String
    lateinit var company_branch : String
    lateinit var merchant_address : String
    lateinit var district : String
    lateinit var city : String
    lateinit var state : String
    lateinit var pincode : String
    lateinit var bank_name : String
    lateinit var bank_accnt_number : String
    lateinit var bank_ifsc : String
    lateinit var bank_branch_name : String
    lateinit var bank_accnt_holder_name : String
    lateinit var userModel: UserModel
    lateinit var dialog: Dialog
    private lateinit var otp: String
    lateinit var latitude: String
    lateinit var longitude: String
    private val KYC_ONBOARD: String = "KYC_ONBOARD"
    private val VALIDATE_OTP: String = "VALIDATE_OTP"
    private val RESEND_OTP: String = "RESEND_OTP"

    private val partnerKey = "UFMwMDEzNDE1NDljYzhmZTY3MTcxZjg1MDYwZjI1NTUxYmNiYTkyOQ=="
    private val partnerId = "PS001341"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aeps_kyc_details)
        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)

        if (!checkPermission()) {
            requestPermission()
        }

        val  bundle= intent.extras
        if (bundle != null) {
            merchant_name = bundle.getString("merchant_name").toString()
            merchant_mobile= bundle.getString("merchant_mobile").toString()
            merchant_email= bundle.getString("merchant_email").toString()
            company_legal_name= bundle.getString("company_legal_name").toString()
            company_marketing_name= bundle.getString("company_marketing_name").toString()
            company_branch = bundle.getString("company_branch").toString()
            merchant_address = bundle.getString("merchant_address").toString()
            district = bundle.getString("district").toString()
            city = bundle.getString("city").toString()
            state = bundle.getString("state").toString()
            pincode = bundle.getString("pincode").toString()
            bank_name = bundle.getString("bank_name").toString()
            bank_accnt_number  = bundle.getString("bank_accnt_number").toString()
            bank_ifsc = bundle.getString("bank_ifsc").toString()
            bank_branch_name = bundle.getString("bank_branch_name").toString()
            bank_accnt_holder_name= bundle.getString("bank_accnt_holder_name").toString()

        }

        Log.e("Mobile",merchant_mobile)



        ivAddFrontAadhar.setOnClickListener {
            if (checkPermission()) {
                fromImageView = "aadhaar_card"
                showPictureDialog()
            } else {
                requestPermission()
            }
        }
        ivCheque.setOnClickListener {
            if (checkPermission()) {
                fromImageView = "cheque"
                showPictureDialog()
            } else {
                requestPermission()
            }
        }
        ivAddPanCard.setOnClickListener {
            if (checkPermission()) {
                fromImageView = "pan_card"
                showPictureDialog()
            } else {
                requestPermission()
            }
        }

        btnKycverify.setOnClickListener {
            if (etPanNumber.text.toString().isNullOrEmpty()) {
                etPanNumber.requestFocus()
                etPanNumber.setError("Invalid PAN Number")

            } else if (etAadharNumber.text.toString().isNullOrEmpty()) {

                etAadharNumber.requestFocus()
                etAadharNumber.setError("Invalid Aadhaar Number")
            } else if (etGstNumber.text.toString().isNullOrEmpty()) {
                etGstNumber.requestFocus()
                etGstNumber.setError("Invalid GST Number")
            } else if (etCompanyShopPanNumber.text.toString().isNullOrEmpty()) {
                etCompanyShopPanNumber.requestFocus()
                etCompanyShopPanNumber.setError("Invalid Company/Shop PAN Number")
            } else if (encoded_adhaar_front_img.equals("")) {
                toast("No Aadhar Front Image Uploaded !")
            } else if (encoded_adhaar_back_img.equals("")) {
                toast("No Cancel Cheque Image Uploaded !")
            } else if (encoded_pancard_img.equals("")) {
                toast("No PAN Image Uploaded !")
            } else {
                Log.e("STATE ID",state)

                val intent = Intent(applicationContext, HostActivity::class.java)
                intent.putExtra("pId", partnerId)//partner Id provided in credential
                intent.putExtra("pApiKey", partnerKey)//JWT API Key provided in credential
                intent.putExtra("mCode", userModel.cus_id)//Merchant Code
                intent.putExtra("mobile", merchant_mobile)// merchant mobile number
                intent.putExtra("lat", latitude)
                intent.putExtra("lng", longitude)
                intent.putExtra("firm", company_legal_name)
                intent.putExtra("email", merchant_email)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent, 999)

           /*     onBoardKycApi(userModel.cus_id,latitude,longitude,merchant_name,merchant_mobile,
                    company_legal_name, company_marketing_name,merchant_email,pincode,city,district,
                    state,merchant_address,etPanNumber.text.toString(),etAadharNumber.text.toString(),
                    etGstNumber.text.toString(),etCompanyShopPanNumber.text.toString(),
                    bank_accnt_number,bank_ifsc,bank_name,bank_branch_name,bank_accnt_holder_name,
                    encoded_adhaar_back_img,encoded_pancard_img,encoded_adhaar_front_img)*/

//                toast("Send for KYC validation")
            }
        }
    }


    //API CALL FUNCTION DEFINITION
    private fun onBoardKycApi(
        cus_id: String,
        latitude: String,
        longitude: String,
        merchantName: String,
        merchantPhoneNumber: String,
        companyLegalName: String,
        companyMarketingName: String,
        emailId: String,
        merchantPinCode: String,
        merchantCityName: String,
        merchantDistrictName: String,
        merchantState: String,
        merchantAddress: String,
        userPan: String,
        aadhaarNumber: String,
        gstInNumber: String,
        companyOrShopPan: String,
        companyBankAccountNumber: String,
        bankIfscCode: String,
        companyBankName: String,
        bankBranchName: String,
        bankAccountName: String,
        cancellationCheckImages: String,
        shopAndPanImage: String,
        ekycDocuments: String
    ) {
        progress_bar_kyc.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, KYC_ONBOARD, this)
            mAPIcall.kycOnBoarding(
                cus_id, latitude, longitude, merchantName, merchantPhoneNumber,
                companyLegalName, companyMarketingName, emailId, merchantPinCode, merchantCityName,
                merchantDistrictName, merchantState, merchantAddress, userPan, aadhaarNumber,
                gstInNumber, companyOrShopPan, companyBankAccountNumber, bankIfscCode,
                companyBankName, bankBranchName, bankAccountName, cancellationCheckImages,
                shopAndPanImage, ekycDocuments
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateekycotp(
        otp: String,
        cus_id: String
    ) {
        progress_bar_kyc.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, VALIDATE_OTP, this)
            mAPIcall.validateekycotp(otp, cus_id)
        } else {
            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resendekycotp(
        cus_id: String
    ) {
        progress_bar_kyc.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, RESEND_OTP, this)
            mAPIcall.resendekycotp(cus_id)
        } else {
            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }


    //********************CAMERA METHODS*************************//
    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf(
            "Select photo from Gallery",
            "Capture photo from Camera"
        )
        pictureDialog.setItems(
            pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }


    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_CANCELED) {
            return
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI)
                    // Toast.makeText(HomeActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();

                    if (fromImageView.equals("aadhaar_card")) {
                        ivAddFrontAadhar.setImageBitmap(bitmap)
                        encoded_adhaar_front_img = ImageUtil.convert(bitmap)


                    } else if (fromImageView.equals("cheque")) {
                        ivCheque.setImageBitmap(bitmap)
                        encoded_adhaar_back_img = ImageUtil.convert(bitmap)

                    } else if (fromImageView.equals("pan_card")) {
                        ivAddPanCard.setImageBitmap(bitmap)
                        encoded_pancard_img = ImageUtil.convert(bitmap)

                    }
                    // encodedBlankCheque = saveImage(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                    //Toast.makeText(HomeActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            val thumbnail = data!!.extras!!["data"] as Bitmap?


            if (fromImageView.equals("aadhaar_card")) {
                ivAddFrontAadhar.setImageBitmap(thumbnail)
                encoded_adhaar_front_img = ImageUtil.convert(thumbnail!!)


            } else if (fromImageView.equals("cheque")) {
                ivCheque.setImageBitmap(thumbnail)
                encoded_adhaar_back_img = ImageUtil.convert(thumbnail!!)

            } else if (fromImageView.equals("pan_card")) {
                ivAddPanCard.setImageBitmap(thumbnail)
                encoded_pancard_img = ImageUtil.convert(thumbnail!!)

            }
            //Toast.makeText(HomeActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == 999) {
            if (resultCode == Activity.RESULT_OK) {
                val status = data?.getBooleanExtra("status", false)
                val response = data?.getIntExtra("response", 0)
                val message = data?.getStringExtra("message")
                val detailedResponse = "Status: $status,  " +
                        "Response: $response, " +
                        "Message: $message "

                val builder = android.app.AlertDialog.Builder(this@AepsKycDetailsActivity)
                builder.setTitle("Info")
                builder.setMessage(message)
                builder.setCancelable(false)
                builder.setPositiveButton("Ok"){dialogInterface, which ->
                    dialogInterface.dismiss()
                }
                builder.show();
            }
        }
    }

//    fun saveImage(myBitmap: Bitmap?): String {
//        val bytes = ByteArrayOutputStream()
//        myBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
//    //    encodedImage = ImageUtil.convert(myBitmap)
//        Log.d("encodedImage", encodedImage!!)
//        return encodedImage
//    }

    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            showPictureDialog()
            // Permission is not granted
            return false
        }
        return true
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this@AepsKycDetailsActivity,
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()

                // main logic
            } else {
                val PERMISSIONS = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) !=
                        PackageManager.PERMISSION_GRANTED
                    ) {
                        showMessageOKCancel(
                            "You need to allow access permissions"
                        ) { dialog, which ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermission()
                            }
                        }
                    }
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) !=
                        PackageManager.PERMISSION_GRANTED
                    ) {
                        showMessageOKCancel(
                            "You need to allow access permissions"
                        ) { dialog, which ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermission()
                            }
                        }
                    }
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        showMessageOKCancel(
                            "You need to allow access permissions"
                        ) { dialog, which ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermission()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {}
    //********************CAMERA METHODS*************************//

    fun confirmOtp() {
        dialog = Dialog(this, R.style.ThemeOverlay_MaterialComponents_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_dialog_kyc_otp)

        dialog.etOtp.requestFocus()
        dialog.tvDialogCancelKyc.setOnClickListener {
            dialog.dismiss()
        }

        dialog.getWindow()!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        dialog.tvDialogResend.setOnClickListener {
            resendekycotp(userModel.cus_id)
        }


        dialog.tvConfirmOtpKyc.setOnClickListener {
            if (dialog.etOtp.text.toString().isNullOrEmpty()) {
                dialog.etOtp.requestFocus()
                dialog.etOtp.setError("Please Enter Valid OTP")
            } else {

                validateekycotp(dialog.etOtp.text.toString(),userModel.cus_id)

                dialog.dismiss()
            }

        }
        dialog.show()
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(KYC_ONBOARD)) {
            Log.e("KYC_ONBOARD", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                val result = jsonObject.getJSONObject("result")
                val message = result.getString("message")
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                confirmOtp()

            } else {
                progress_bar_kyc.visibility = View.INVISIBLE
            }
        }

        if (flag.equals(VALIDATE_OTP)) {
            Log.e("VALIDATE_OTP", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val message = jsonObject.getString("message")
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                val  intent = Intent(this, KycVerificationActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                progress_bar_kyc.visibility = View.INVISIBLE
            }
        }
        if (flag.equals(RESEND_OTP)) {
            Log.e("RESEND_OTP", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val message = jsonObject.getString("message")
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                toast(message)

            } else {
                progress_bar_kyc.visibility = View.INVISIBLE
            }
        }
    }


}