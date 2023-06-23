package com.satmatgroup.shreeram.payout

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.dmt.BankListAdapter
import com.satmatgroup.shreeram.dmt.BankListModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import org.json.JSONObject
import java.util.ArrayList

class UserPayoutBankAdapter(
    context: Context?,
    rechargeHistoryModalList: ArrayList<UserPayoutBankModel>,
    mListener: ListAdapterListener
) :  RecyclerView.Adapter<UserPayoutBankAdapter.ViewHolder>() {

    private var rechargeHistoryModalList: List<UserPayoutBankModel>
    private val mInflater: LayoutInflater
    private val mListener: UserPayoutBankAdapter.ListAdapterListener
    private var mContext: Context? = null
    val CHECK_STATUS = "CHECK_STATUS"
    var context = context

    interface ListAdapterListener {
        // create an interface
        fun onClickAtOKButton(mobileRechargeModal: UserPayoutBankModel?) // create callback function
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserPayoutBankAdapter.ViewHolder {
        mContext = parent.context
        //user_type = AppPrefs.getStringPref("user_type", mContext).toString()
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_payout_bank, parent, false)
        return UserPayoutBankAdapter.ViewHolder(listItem, mContext!!)
    }

    override fun onBindViewHolder(holder: UserPayoutBankAdapter.ViewHolder, position: Int) {
        val rechargeHistoryModal: UserPayoutBankModel =
            rechargeHistoryModalList[position]

        holder.tvBankNamePayout.setText(rechargeHistoryModal.bankName+" - "+rechargeHistoryModal.bankAccount)
        holder.itemView.setOnClickListener { mListener.onClickAtOKButton(rechargeHistoryModal) }
    }

    override fun getItemCount(): Int {
        return rechargeHistoryModalList.size
    }

    class ViewHolder(itemView: View,context: Context) : RecyclerView.ViewHolder(itemView) {
        // var ivStatus: ImageView
        var context = context

        var tvBankNamePayout: TextView

        //var btnCheckStatus: Button

        init {
            tvBankNamePayout = itemView.findViewById(R.id.tvBankNamePayout)

            //btnCheckStatus = itemView.findViewById(R.id.btnCheckStatus)
        }
    }

    // RecyclerView recyclerView;
    init {
        mInflater = LayoutInflater.from(context)
        this.rechargeHistoryModalList = rechargeHistoryModalList
        this.mListener = mListener // receive mListener from Fragment (or Activity)
    }
}