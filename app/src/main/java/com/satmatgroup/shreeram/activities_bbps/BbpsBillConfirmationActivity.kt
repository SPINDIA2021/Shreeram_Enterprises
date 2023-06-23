package com.satmatgroup.shreeram.activities_bbps

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.satmatgroup.shreeram.R
import kotlinx.android.synthetic.main.activity_bbps_bill_confirmation.*
import kotlinx.android.synthetic.main.activity_bbps_bill_confirmation.view.*

class BbpsBillConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.status_bar, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_bbps_bill_confirmation)

        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }

        btnPayBill.setOnClickListener {

            val intent = Intent(this, BbpsBillTransactionActivity::class.java)
            startActivity(intent)
        }
    }
}