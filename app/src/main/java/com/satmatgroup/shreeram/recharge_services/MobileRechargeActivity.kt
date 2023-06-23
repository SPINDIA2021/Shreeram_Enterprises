package com.satmatgroup.shreeram.recharge_services

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.recharge_services.mobile_recviewpager.MobilePostpaidFragment
import com.satmatgroup.shreeram.recharge_services.mobile_recviewpager.MobilePrepaidFragment
import com.satmatgroup.shreeram.recharge_services.mobile_recviewpager.OrderHistoryTabAdapter
import kotlinx.android.synthetic.main.activity_mobile_recharge.*
import kotlinx.android.synthetic.main.activity_mobile_recharge.view.*

class MobileRechargeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.status_bar, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_mobile_recharge)
        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }

        setupViewPager()
    }

    //Create View Pager
    private fun setupViewPager() {

        val adapter = OrderHistoryTabAdapter(supportFragmentManager)

        var mobilePrepaidFragment: MobilePrepaidFragment =
            MobilePrepaidFragment.newInstance("Prepaid")


        var mobilePostpaidFragment: MobilePostpaidFragment =
            MobilePostpaidFragment.newInstance("Postpaid")

        adapter.addFragment(mobilePrepaidFragment, "PREPAID")
        adapter.addFragment(mobilePostpaidFragment, "POSTPAID")
        viewPager!!.adapter = adapter

        tabLayout!!.setupWithViewPager(viewPager)

    }

}