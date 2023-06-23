package com.satmatgroup.shreeram.reports

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.activities_aeps.AepsCommissionActivity
import com.satmatgroup.shreeram.activities_aeps.AepsCommissionSlabActivity
import com.satmatgroup.shreeram.activities_aeps.aepshistory.AepsHistoryActivity
import com.satmatgroup.shreeram.dmt.DmtCommissionSlabActivity
import com.satmatgroup.shreeram.dmt.ViewDmtTransactionActivity
import com.satmatgroup.shreeram.payout.PayoutHistoryActivity
import kotlinx.android.synthetic.main.fragment_aeps_reports.view.*


/**
 * A simple [Fragment] subclass.
 */
class AepsReportsFragment : Fragment() {


    lateinit var root: View

    companion object {
        fun newInstance(message: String): AepsReportsFragment {

            val f = AepsReportsFragment()

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
        root = inflater.inflate(R.layout.fragment_aeps_reports, container, false)

        initView(root)


        return root
    }


    fun initView(root: View) {

        root.ll_dmt_history.setOnClickListener {
            val intent = Intent(requireContext(), ViewDmtTransactionActivity::class.java)
            startActivity(intent)
        }

        root.ll_payouthistory.setOnClickListener {
            val intent = Intent(requireContext(), PayoutHistoryActivity::class.java)
            startActivity(intent)
        }
        root.ll_aeps_commissionslab.setOnClickListener {
            val intent = Intent(requireContext(), AepsCommissionSlabActivity::class.java)
            startActivity(intent)
        }
        root.ll_aeps_history.setOnClickListener {
            val intent = Intent(requireContext(), AepsHistoryActivity::class.java)
            startActivity(intent)
        }
        root.ll_dmt_commission_slab.setOnClickListener {
            val intent = Intent(requireContext(), DmtCommissionSlabActivity::class.java)
            startActivity(intent)
        }
        root.ll_aeps_comm_history.setOnClickListener {
            val intent = Intent(requireContext(), AepsCommissionActivity::class.java)
            startActivity(intent)
        }
    }
}
