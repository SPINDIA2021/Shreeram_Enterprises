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
import com.satmatgroup.shreeram.recharge_services.*
import kotlinx.android.synthetic.main.activity_bbps_main.*
import kotlinx.android.synthetic.main.activity_bbps_main.custToolbar
import kotlinx.android.synthetic.main.activity_bbps_main.rl_dthRecharge
import kotlinx.android.synthetic.main.activity_bbps_main.rl_electricity
import kotlinx.android.synthetic.main.activity_bbps_main.rl_landlineRecharge
import kotlinx.android.synthetic.main.activity_bbps_main.rl_mobileRecharge
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


        rl_landlineRecharge.setOnClickListener {

            val intent = Intent(this, LandLineRechargeActivity::class.java)
            startActivity(intent)
            /* val fragment = BbpsdthFragment()
             loadFragment(fragment)*/

        }

        rl_lpggas.setOnClickListener {
            val intent = Intent(this, LPGGasRechargeActivity::class.java)
            startActivity(intent)
        }

        rl_water.setOnClickListener {
            val intent = Intent(this, WaterRechargeActivity::class.java)
            startActivity(intent)
        }

        rl_electricity.setOnClickListener {

            val intent = Intent(this, ElectricityRechargeActivity::class.java)
            startActivity(intent)
            /*val fragment = BbpsElectricityFragment()
            loadFragment(fragment)*/

        }
        rl_dthRecharge.setOnClickListener {

            val intent = Intent(this, DthRechargeActivity::class.java)
            startActivity(intent)
           /* val fragment = BbpsdthFragment()
            loadFragment(fragment)*/

        }
        rl_mobileRecharge.setOnClickListener {

            val intent = Intent(this, MobilePrepaidActivity::class.java)
            startActivity(intent)
           /* val fragment = BbpsMobilePostpaidFragment()
            loadFragment(fragment)*/
        }


        rl_broadband.setOnClickListener {

            val intent = Intent(this, BroadbandRechargeActivity::class.java)
            startActivity(intent)
        }

        rl_insurance.setOnClickListener {

            val intent = Intent(this, InsurancePaymentActivity::class.java)
            startActivity(intent)
        }

        rl_pipedgas.setOnClickListener {

            val intent = Intent(this, PipedGasRechargeActivity::class.java)
            startActivity(intent)
        }

        rl_municipaltaxes.setOnClickListener {

            val intent = Intent(this, MunicipleTaxesPaymentActivity::class.java)
            startActivity(intent)
        }

        rl_loanpayment.setOnClickListener {
            val intent = Intent(this, LoanRePaymentActivity::class.java)
            startActivity(intent)
        }

        rl_cabletv.setOnClickListener {
            val intent = Intent(this, CableRechargeActivity::class.java)
            startActivity(intent)
        }

        rl_traffic_challan.setOnClickListener {
            val intent = Intent(this, TrafficChallanPaymentActivity::class.java)
            startActivity(intent)
        }

        rl_hospital.setOnClickListener {
            val intent = Intent(this, HospitalPaymentActivity::class.java)
            startActivity(intent)
        }

        rl_postpaid.setOnClickListener {
            val intent = Intent(this, MobilePostpaidActivity::class.java)
            startActivity(intent)
        }




      /*  tvRaiseComplaint.setOnClickListener {
            val intent = Intent(this, RaiseComplaintActivity::class.java)
            startActivity(intent)
        }
        tvViewComplaint.setOnClickListener {

            val intent = Intent(this, ComplaintStatusActivity::class.java)
            startActivity(intent)

        }*/

    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fp_layout, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }
}