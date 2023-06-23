package com.satmatgroup.shreeram.activities_bbps

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.activities_bbps.complaints.ComplaintStatusActivity
import com.satmatgroup.shreeram.activities_bbps.complaints.RaiseComplaintActivity
import com.satmatgroup.shreeram.activities_bbps.services_fragments.BbpsElectricityFragment
import com.satmatgroup.shreeram.activities_bbps.services_fragments.BbpsMobilePostpaidFragment
import com.satmatgroup.shreeram.activities_bbps.services_fragments.BbpsdthFragment
import kotlinx.android.synthetic.main.activity_bbps_main.*
import kotlinx.android.synthetic.main.activity_bbps_main.view.*


class BbpsMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.status_bar, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_bbps_main)
        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }


        rl_electricity.setOnClickListener {

            val fragment = BbpsElectricityFragment()
            loadFragment(fragment)

        }
        rl_dthRecharge.setOnClickListener {

            val fragment = BbpsdthFragment()
            loadFragment(fragment)

        }
        rl_mobileRecharge.setOnClickListener {

            val fragment = BbpsMobilePostpaidFragment()
            loadFragment(fragment)

        }
        tvRaiseComplaint.setOnClickListener {
            val intent = Intent(this, RaiseComplaintActivity::class.java)
            startActivity(intent)
        }
        tvViewComplaint.setOnClickListener {

            val intent = Intent(this, ComplaintStatusActivity::class.java)
            startActivity(intent)

        }

    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fp_layout, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }
}