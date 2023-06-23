package com.satmatgroup.shreeram.activities_bbps.complaints

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.satmatgroup.shreeram.R
import kotlinx.android.synthetic.main.activity_raise_complaint.*
import kotlinx.android.synthetic.main.activity_raise_complaint.view.*

class RaiseComplaintActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.status_bar, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_raise_complaint)
        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }
        btnSubmit.setOnClickListener {
            showCustomerID()
        }

    }

    fun showCustomerID() {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Please Note")
        //set message for alert dialog
        builder.setMessage("Your Complaint ID - 656787")
        //performing positive action
        builder.setPositiveButton(getString(R.string.btn_okay)) { dialogInterface, which ->
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}