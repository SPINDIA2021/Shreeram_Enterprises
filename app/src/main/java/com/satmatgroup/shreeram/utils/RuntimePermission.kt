package com.satmatgroup.shreeram.utils

import android.R
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.SparseIntArray
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

abstract class RuntimePermissionActivity : AppCompatActivity() {
    private var mErrorString: SparseIntArray? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mErrorString = SparseIntArray()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var permissionCheck = PackageManager.PERMISSION_GRANTED
        for (permission in grantResults) {
            permissionCheck = permissionCheck + permission
        }
        if (grantResults.size > 0 && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            onPermissionsGranted(requestCode)
        } else {

        }
    }

    fun requestAppPermissions(
        requestedPermissions: Array<String?>,
        stringId: Int, requestCode: Int
    ) {
        mErrorString?.put(requestCode, stringId)
        var permissionCheck = PackageManager.PERMISSION_GRANTED
        var shouldShowRequestPermissionRationale = false
        for (permission in requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(
                this,
                permission!!
            )
            shouldShowRequestPermissionRationale =
                shouldShowRequestPermissionRationale || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    permission
                )
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale) {
                Snackbar.make(
                    findViewById(R.id.content), stringId,
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(
                    "GRANT"
                ) {
                    ActivityCompat.requestPermissions(
                        this@RuntimePermissionActivity,
                        requestedPermissions,
                        requestCode
                    )
                }.show()
            } else {
                ActivityCompat.requestPermissions(this, requestedPermissions, requestCode)
            }
        } else {
            onPermissionsGranted(requestCode)
        }
    }

    abstract fun onPermissionsGranted(requestCode: Int)
}