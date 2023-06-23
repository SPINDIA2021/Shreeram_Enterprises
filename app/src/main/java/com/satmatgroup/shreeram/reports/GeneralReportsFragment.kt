package com.satmatgroup.shreeram.reports

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.reports.recharge_reports.*
import kotlinx.android.synthetic.main.fragment_general_reports.view.*

class GeneralReportsFragment : Fragment() {

    lateinit var root: View

    companion object {
        fun newInstance(message: String): GeneralReportsFragment {

            val f = GeneralReportsFragment()

            val bdl = Bundle(1)

            bdl.putString(AlarmClock.EXTRA_MESSAGE, message)

            f.setArguments(bdl)

            return f

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_general_reports, container, false)


        initView(root)



        return root

    }


    fun initView(root: View) {

        root.ll_allRecharges.setOnClickListener {
            val intent = Intent(requireContext(), AllRechargeReportsActivity::class.java)
            startActivity(intent)

        }
        root.ll_specific_report.setOnClickListener {
            val intent = Intent(requireContext(), SpecificRechargeHistoryActivity::class.java)
            startActivity(intent)
        }
        root.ll_commissionreport.setOnClickListener {
            val intent = Intent(requireContext(), CommisionReportActivity::class.java)
            startActivity(intent)
        }
        root.ll_commissionslab.setOnClickListener {
            val intent = Intent(requireContext(), CommissionSlabActivity::class.java)
            startActivity(intent)
        }
        root.ll_recieved_funds.setOnClickListener {
            val intent = Intent(requireContext(), FundRecieveReportActivity::class.java)
            startActivity(intent)
        }
        root.ll_debited_funds.setOnClickListener {
            val intent = Intent(requireContext(), FundTransferReportActivity::class.java)
            startActivity(intent)
        }
        root.ll_fund_request.setOnClickListener {
            val intent = Intent(requireContext(), FundRequestReportActivity::class.java)
            startActivity(intent)
        }
        root.ll_ledger.setOnClickListener {
            val intent = Intent(requireContext(), LedgerReportActivity::class.java)
            startActivity(intent)
        }
        root.ll_disputer_history.setOnClickListener {
            val intent = Intent(requireContext(), DisputeReportHistoryActivity::class.java)
            startActivity(intent)
        }

    }
}