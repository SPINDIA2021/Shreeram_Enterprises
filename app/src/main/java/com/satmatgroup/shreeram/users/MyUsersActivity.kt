package com.satmatgroup.shreeram.users

import android.Manifest
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.adapters_recyclerview.UserListActivityAdapter
import com.satmatgroup.shreeram.fund_services.TransferFundsActivity
import com.satmatgroup.shreeram.model.UserListModel
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppConstants.Companion.AADHAAR
import com.satmatgroup.shreeram.utils.AppConstants.Companion.PAN
import com.satmatgroup.shreeram.utils.AppConstants.Companion.PROFILE
import com.satmatgroup.shreeram.utils.AppPrefs
import com.satmatgroup.shreeram.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_all_recharge_reports.*
import kotlinx.android.synthetic.main.activity_my_users.*
import kotlinx.android.synthetic.main.activity_my_users.mSwipeRefresh
import kotlinx.android.synthetic.main.activity_my_users.progress_bar
import kotlinx.android.synthetic.main.layout_dialog_add_user.*
import kotlinx.android.synthetic.main.layout_dialog_change_password.*
import kotlinx.android.synthetic.main.layout_dialog_change_password.custToolbarDialog
import kotlinx.android.synthetic.main.layout_dialog_change_password.view.*
import kotlinx.android.synthetic.main.layout_dialog_change_password.view.ivClosePasswordDialog
import org.json.JSONObject
import java.io.IOException


class MyUsersActivity : AppCompatActivity(), UserListActivityAdapter.ListAdapterListener,
    AppApiCalls.OnAPICallCompleteListener, SwipeRefreshLayout.OnRefreshListener {
    lateinit var dialog: Dialog


    //Adding Image
    var encodedImage: String? = "empty"
    private val GALLERY = 1
    private var CAMERA = 2
    private val PERMISSION_REQUEST_CODE = 200
    lateinit var fromImageView: String

    lateinit var userModel : UserModel



    lateinit var userListActivityAdapter: UserListActivityAdapter
    var userListModelArrayList = ArrayList<UserListModel>()
    private val USER_LIST: String = "USER_LIST"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_users)

        ivAddUser.setOnClickListener {
            val intent = Intent(this,CreateUserActivity::class.java);
            startActivity(intent)
        }

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)

        ivSearchBtn.setOnClickListener {
            if (etSearchMobName.text.isEmpty()) {
                etSearchMobName.requestFocus()
                etSearchMobName.setError("Please enter mobile or name")

            } else {

                searchUser(userModel.cus_id, etSearchMobName.text.toString(), AppPrefs.getStringPref("deviceId", this).toString(),
                    AppPrefs.getStringPref("deviceName",this ).toString(),
                    userModel.cus_pin,
                    userModel.cus_pass,
                    userModel.cus_mobile,userModel.cus_type)
            }
        }
        etSearchMobName.addTextChangedListener(object : TextWatcher {
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
                if (s.toString().length == 0) {

                    userListHistory(userModel.cus_id,AppPrefs.getStringPref(" deviceId", this@MyUsersActivity).toString(),
                        AppPrefs.getStringPref("deviceName",this@MyUsersActivity ).toString(),
                        userModel.cus_pin,
                        userModel.cus_pass,
                        userModel.cus_mobile,userModel.cus_type)
                    hideKeyboard()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        mSwipeRefresh.setOnRefreshListener(this);

        mSwipeRefresh.post(Runnable {
            if (mSwipeRefresh != null) {
                mSwipeRefresh.setRefreshing(true)
            }
            userListModelArrayList.clear()

            mSwipeRefresh.setRefreshing(false)
            userListHistory(
                userModel.cus_id, AppPrefs.getStringPref("deviceId", this).toString(),
                AppPrefs.getStringPref("deviceName",this ).toString(),
                userModel.cus_pin,
                userModel.cus_pass,
                userModel.cus_mobile,userModel.cus_type


            )
        })

    }


    private fun userListHistory(
        cus_id: String,deviceId : String,deviceName: String,pin: String,pass: String,cus_mobile: String
        ,cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, USER_LIST, this)
            mAPIcall.getUserList(cus_id,deviceId,deviceName,pin,pass,cus_mobile,cus_type)
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun searchUser(
        dis_cus_id: String, mobileorname: String, deviceId: String, deviceName: String,
        pin: String,pass: String,cus_mobile: String,cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, USER_LIST, this)
            mAPIcall.searcUser(dis_cus_id, mobileorname,deviceId, deviceName,pin,
                pass,cus_mobile,cus_type)
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }



    private fun addUserDialog() {
        dialog = Dialog(this, R.style.Widget_MaterialComponents_MaterialCalendar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_dialog_add_user)
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.custToolbarDialog.ivClosePasswordDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.ivProfileImageFinal.setOnClickListener {
            if (checkPermission()) {

                fromImageView = PROFILE
                showPictureDialog()
            } else {
                requestPermission()
            }
        }

        dialog.ivAddAadharCard.setOnClickListener {
            if (checkPermission()) {

                fromImageView = AADHAAR
                showPictureDialog()
            } else {
                requestPermission()
            }
        }

        dialog.ivAddPanCard.setOnClickListener {
            if (checkPermission()) {

                fromImageView = PAN
                showPictureDialog()
            } else {
                requestPermission()
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

                    if (fromImageView.equals(PROFILE)) {
                        dialog.ivProfileImageFinal.setImageBitmap(bitmap)

                    } else if (fromImageView.equals(AADHAAR)) {
                        dialog.ivAddAadharCard.setImageBitmap(bitmap)

                    } else if (fromImageView.equals(PAN)) {
                        dialog.ivAddPanCard.setImageBitmap(bitmap)

                    }
                    // encodedBlankCheque = saveImage(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                    //Toast.makeText(HomeActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            val thumbnail = data!!.extras!!["data"] as Bitmap?

            dialog.ivProfileImageFinal.visibility = VISIBLE
            if (fromImageView.equals(PROFILE)) {
                dialog.ivProfileImageFinal.setImageBitmap(thumbnail)

            } else if (fromImageView.equals(AADHAAR)) {
                dialog.ivAddAadharCard.setImageBitmap(thumbnail)

            } else if (fromImageView.equals(PAN)) {
                dialog.ivAddPanCard.setImageBitmap(thumbnail)

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
            this@MyUsersActivity,
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
                Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_SHORT)
                    .show()

                // main logic
            } else {
                val PERMISSIONS = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT)
                    .show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) !=
                        PackageManager.PERMISSION_GRANTED
                    ) {
                        showMessageOKCancel(
                            getString(R.string.error_need_permission)
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
                            getString(R.string.error_need_permission)
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
                            getString(R.string.error_need_permission)
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
            .setPositiveButton(getString(R.string.btn_okay), okListener)
            .setNegativeButton(getString(R.string.cancel), null)
            .create()
            .show()
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {}


    //********************CAMERA METHODS*************************//



    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(USER_LIST)) {
            userListModelArrayList.clear()

            Log.e("DISPUTE_REPORT", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                progress_bar.visibility = View.INVISIBLE

                val cast = jsonObject.getJSONArray("result")

                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val cus_id = notifyObjJson.getString("cus_id")
                    Log.e("cus_id ", cus_id)
                    val userListModel = Gson()
                        .fromJson(
                            notifyObjJson.toString(),
                            UserListModel::class.java
                        )


                    userListModelArrayList.add(userListModel)
                }

                rvUserList.apply {

                    layoutManager = LinearLayoutManager(this@MyUsersActivity)
                    userListActivityAdapter = UserListActivityAdapter(
                        context, userListModelArrayList, this@MyUsersActivity
                    )
                    rvUserList.adapter = userListActivityAdapter
                }

            } else {
                progress_bar.visibility = View.INVISIBLE


            }
        }
    }

    override fun onClickAtOKButton(userListModel: UserListModel) {


        val bundle = Bundle()
        bundle.putSerializable("userListModel", userListModel)
        val intent = Intent(this, TransferFundsActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onRefresh() {

        mSwipeRefresh.setRefreshing(false)
        userListHistory(
            userModel.cus_id, AppPrefs.getStringPref("deviceId", this).toString(),
            AppPrefs.getStringPref("deviceName",this ).toString(),
            userModel.cus_pin,
            userModel.cus_pass,
            userModel.cus_mobile,userModel.cus_type


        )
    }

}