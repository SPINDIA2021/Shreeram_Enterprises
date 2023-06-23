package com.satmatgroup.shreeram.authentication

import android.Manifest
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
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppConstants.Companion.REGISTER_API
import com.satmatgroup.shreeram.utils.ImageUtil
import com.satmatgroup.shreeram.utils.toast
import kotlinx.android.synthetic.main.activity_register_new.*
import kotlinx.android.synthetic.main.activity_register_new.view.*
import kotlinx.android.synthetic.main.layout_dialog_confirmotp.*
import org.json.JSONObject
import java.io.IOException

class RegisterActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener {

    private val GALLERY = 1
    private var CAMERA = 2
    private val PERMISSION_REQUEST_CODE = 200
    lateinit var fromImageView: String

    var encoded_adhaar_img: String? = ""
    var encoded_pancard_img: String? = ""
    var encoded_profile_img: String? = ""
    lateinit var dialog:Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.status_bar, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_register_new)

        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }


        ivProfileImageFinal.setOnClickListener {
            if (checkPermission()) {
                fromImageView = "profile"
                takePhotoFromCamera()
            } else {
                requestPermission()
            }
        }

        ivAddAadharCard.setOnClickListener {
            if (checkPermission()) {
                fromImageView = "aadhaar"
                showPictureDialog()
            } else {
                requestPermission()
            }
        }

        ivAddPanCard.setOnClickListener {
            if (checkPermission()) {
                fromImageView = "pan"
                showPictureDialog()
            } else {
                requestPermission()
            }
        }

        btnEnquire.setOnClickListener {
            if (etRegisterUserName.text.toString().isNullOrEmpty()) {
                etRegisterUserName.requestFocus()
                etRegisterUserName.setError(getString(R.string.error_invalid_name))
            } else
                if (!AppCommonMethods.checkForEmail(etRegisterUserEmail)) {

                    etRegisterUserAadhaarNumber.requestFocus()
                    etRegisterUserAadhaarNumber.setError(getString(R.string.error_invalid_email))
                } else if (!AppCommonMethods.checkForMobile(etRegisterUserMobileNumber)) {

                    etRegisterUserMobileNumber.requestFocus()
                    etRegisterUserMobileNumber.setError(getString(R.string.error_mobile_number))
                } else if (etRegisterUserAadhaarNumber.text.toString().length < 12) {

                    etRegisterUserAadhaarNumber.requestFocus()
                    etRegisterUserAadhaarNumber.setError(getString(R.string.invalid_aadhaar))
                } else if (etRegisterUserAddress.text.toString().isNullOrEmpty()) {

                    etRegisterUserAddress.requestFocus()
                    etRegisterUserAddress.setError(getString(R.string.error_invalid_address))
                }else {
                    registerApi(
                        etRegisterUserName.text.toString(),
                        etRegisterUserMobileNumber.text.toString(),
                        etRegisterUserEmail.text.toString(),
                        etRegisterUserAadhaarNumber.text.toString(),
                        etRegisterUserAddress.text.toString(),
                        AppCommonMethods.getDeviceId(this), AppCommonMethods.getDeviceName(),
                        etRegisterUserPanNumber.text.toString()
                    )
                }
        }
    }

    //API CALL FUNCTION DEFINITION
    private fun registerApi(
        username: String,
        mobile: String,
        email: String,
        aadhaar_number: String,
        address: String,
        deviceId: String,
        deviceName: String,
        pan_no: String
    ) {
        progress_bar.visibility = View.VISIBLE
        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                this,
                AppConstants.REGISTER_API,
                this
            )
            mAPIcall.register(
                username,
                mobile,
                email,
                aadhaar_number,
                address,
                deviceId,
                deviceName,
                pan_no
            )

        } else {
            toast(getString(R.string.error_internet))
        }
    }

    fun confirmOtp(mobile: String) {
        dialog = Dialog(this, R.style.ThemeOverlay_MaterialComponents_Dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_dialog_confirmotp)

        dialog.etOtp.requestFocus()
        dialog.tvDialogCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.getWindow()!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        dialog.tvConfirmOtp.setOnClickListener {
            if (dialog.etOtp.text.toString().isNullOrEmpty()) {
                dialog.etOtp.requestFocus()
                dialog.etOtp.setError("Please Enter Valid OTP")
            } else {
/*                verifySenderOtp(
                    mobile,
                    dialog.etOtp.text.toString()
                )*/
                dialog.dismiss()

            }

        }
        dialog.show()
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

                    if (fromImageView.equals("profile")) {
                        ivProfileImageFinal.setImageBitmap(bitmap)
                        encoded_profile_img = ImageUtil.convert(bitmap)


                    } else if (fromImageView.equals("aadhaar")) {
                        ivAddAadharCard.setImageBitmap(bitmap)
                        encoded_adhaar_img = ImageUtil.convert(bitmap)

                    } else if (fromImageView.equals("pan")) {
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

            if (fromImageView.equals("profile")) {
                ivProfileImageFinal.setImageBitmap(thumbnail)

            } else if (fromImageView.equals("aadhaar")) {
                ivAddAadharCard.setImageBitmap(thumbnail)

            } else if (fromImageView.equals("pan")) {
                ivAddPanCard.setImageBitmap(thumbnail)

            }
            //Toast.makeText(HomeActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
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
            this@RegisterActivity,
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
    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(REGISTER_API)) {
            Log.e(AppConstants.REGISTER_API, result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val messageCode = jsonObject.getString(AppConstants.MESSAGE)
            Log.e(AppConstants.STATUS, status)
            //  Log.e(MESSAGE, messageCode)
            if (status.contains(AppConstants.TRUE)) {
                progress_bar.visibility = View.INVISIBLE

                toast(messageCode)

                /*   val gson = Gson()
                   val json = gson.toJson(userModel)
                   AppPrefs.putStringPref("userModel", json, this)
                   AppPrefs.putStringPref("user_type", userModel.cus_type, this)
                   AppPrefs.putStringPref("user_id", userModel.cus_id, this)
                   AppPrefs.putBooleanPref(AppConstants.IS_LOGIN, true, this)
                   AppPrefs.putStringPref("deviceId",deviceId,this);
                   AppPrefs.putStringPref("deviceName",deviceNameDet,this)
   */
                val bundle = Bundle()
                onBackPressed()


            } else {
                progress_bar.visibility = View.INVISIBLE
                toast(messageCode)
            }
        }
    }
    //********************CAMERA METHODS*************************//
}