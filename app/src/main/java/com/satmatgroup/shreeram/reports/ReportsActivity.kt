package com.satmatgroup.shreeram.reports

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.satmatgroup.shreeram.R
import kotlinx.android.synthetic.main.activity_reports.*
import kotlinx.android.synthetic.main.activity_reports.view.*


class ReportsActivity : AppCompatActivity() {
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

        val adapter = ReportsTabAdapter(supportFragmentManager)

        val generalReportsFragment: GeneralReportsFragment =
            GeneralReportsFragment.newInstance("General")

        val aepsReportsFragment: AepsReportsFragment =
            AepsReportsFragment.newInstance("Aeps")

        adapter.addFragment(generalReportsFragment, "GENERAL")
        adapter.addFragment(aepsReportsFragment, "AEPS/DMT")
        viewPager!!.adapter = adapter

        tabLayout!!.setupWithViewPager(viewPager)

    }

}