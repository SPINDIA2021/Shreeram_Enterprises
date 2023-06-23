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
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.toast
import kotlinx.android.synthetic.main.activity_add_beneficiary.view.*
import kotlinx.android.synthetic.main.activity_aeps_onboard.*
import kotlinx.android.synthetic.main.activity_aeps_onboard.custToolbar

class AepsOnboardActivity : AppCompatActivity() {

    var latitudeLabel: String=""
    var longitudeLabel: String=""

    var intent1: Intent? = null
    var gpsStatus = false

    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aeps_onboard)
        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }


        checkGpsStatus()

        btnNextUserDetails.setOnClickListener {

            if (etMerchantName.text.toString().isNullOrEmpty()) {
                etMerchantName.requestFocus()
                etMerchantName.setError("Invalid Name")

            } else if (!AppCommonMethods.checkForMobile(etMerchantMobile)) {

                etMerchantMobile.requestFocus()
                etMerchantMobile.setError("Invalid Mobile")
            } else if (!AppCommonMethods.checkForEmail(etMerchantEmail)) {
                etMerchantEmail.requestFocus()
                etMerchantEmail.setError("Invalid Email")

            } else if (etCompanyLegalName.text.toString().isNullOrEmpty()) {
                etCompanyLegalName.requestFocus()
                etCompanyLegalName.setError("Invalid Company Name")

            } else if (etCompanyMarketingName.text.toString().isNullOrEmpty()) {

                etCompanyMarketingName.requestFocus()
                etCompanyMarketingName.setError("Invalid Company Marketing Name")
            } else if (etCompanyBranchName.text.toString().isNullOrEmpty()) {
                etCompanyBranchName.requestFocus()
                etCompanyBranchName.setError("Invalid Branch Name")
            } else {

                val bundle = Bundle()
                bundle.putString("merchant_name", etMerchantName.text.toString())
                bundle.putString("merchant_mobile", etMerchantMobile.text.toString())
                bundle.putString("merchant_email", etMerchantEmail.text.toString())
                bundle.putString("company_legal_name", etCompanyLegalName.text.toString())
                bundle.putString("company_marketing_name", etCompanyMarketingName.text.toString())
                bundle.putString("company_branch", etCompanyBranchName.text.toString())
                bundle.putString("latitude",latitudeLabel)
                bundle.putString("longitude",longitudeLabel)
                val intent = Intent(this, AepsAddressActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)

            }
        }
    }

    override fun onStart() {
        super.onStart()
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
}