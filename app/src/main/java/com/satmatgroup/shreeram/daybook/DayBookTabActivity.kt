package com.satmatgroup.shreeram.daybook

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.satmatgroup.shreeram.R
import kotlinx.android.synthetic.main.activity_reports.*
import kotlinx.android.synthetic.main.activity_reports.view.*


class DayBookTabActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.status_bar, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_reports)
        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }
        setupViewPager()
    }

    //Create View Pager
    private fun setupViewPager() {

        val adapter = DayBookTabAdapter(supportFragmentManager)

        var dayBookRechargesFragment: DayBookRechargesFragment =
            DayBookRechargesFragment.newInstance("Recharges")
        var dayBookFundTransferFragment: DayBookFundTransferFragment =
            DayBookFundTransferFragment.newInstance("Fund Transfer")

        adapter.addFragment(dayBookRechargesFragment, "Recharges")
        adapter.addFragment(dayBookFundTransferFragment, "Fund Transfer")
        viewPager!!.adapter = adapter

        tabLayout!!.setupWithViewPager(viewPager)

    }

}