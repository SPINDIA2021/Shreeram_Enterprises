package com.satmatgroup.shreeram.adapters_recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.FundTransferHitoryModel
import com.satmatgroup.shreeram.utils.AppPrefs
import java.util.*

class FundTransferReportAdapter(
    context: Context?,
    rechargeHistoryModalList: ArrayList<FundTransferHitoryModel>
) :
    RecyclerView.Adapter<FundTransferReportAdapter.ViewHolder>() {
    private var rechargeHistoryModalList: List<FundTransferHitoryModel>
    private val mInflater: LayoutInflater
    private var mContext: Context? = null

    var user_type: String = ""
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mContext = parent.context
        user_type = AppPrefs.getStringPref("user_type", mContext).toString()
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_fundtransfer, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val rechargeHistoryModal: FundTransferHitoryModel =
            rechargeHistoryModalList[position]
        holder.tvTxnId.setText(rechargeHistoryModal.txn_id)
        holder.tvTxnType.text = "Transaction type : " + rechargeHistoryModal.txn_type
         holder.tvDate.text = rechargeHistoryModal.txn_time
        holder.tvRecievedFrom.text = rechargeHistoryModal.to_cus_name

        if (rechargeHistoryModal.to_cus_mobile.equals("0")) {

            holder.tvRecievedMobile.visibility = GONE


        } else {
            holder.tvRecievedMobile.text = "Mobile : " + rechargeHistoryModal.to_cus_mobile


        }

        holder.tvBalance.text = "₹ " + rechargeHistoryModal.txn_clbal
        holder.tvCreditAmnt.text = "₹ " + rechargeHistoryModal.txn_crdt
        holder.tvDebitAmnt.text = "₹ " + rechargeHistoryModal.txn_dbdt
        holder.tvOpeningBalance.text = "₹ " + rechargeHistoryModal.txn_opbal

    }

    override fun getItemCount(): Int {
        return rechargeHistoryModalList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var ivStatus: ImageView
        var tvTxnId: TextView
        var tvDate: TextView
        var tvCreditAmnt: TextView
        var tvDebitAmnt: TextView
        var tvTxnType: TextView
        var tvOpeningBalance: TextView
        var tvBalance: TextView
        var tvRecievedFrom: TextView
        var tvRecievedMobile: TextView

        init {
            tvTxnId = itemView.findViewById(R.id.tvTxnId)
            tvDate = itemView.findViewById(R.id.tvDate)
            tvCreditAmnt = itemView.findViewById(R.id.tvCreditAmnt)
            tvBalance = itemView.findViewById(R.id.tvBalance)
            tvDebitAmnt = itemView.findViewById(R.id.tvDebitAmnt)
            tvOpeningBalance = itemView.findViewById(R.id.tvOpeningBal)
            tvTxnType = itemView.findViewById(R.id.tvTxnType)
            tvRecievedFrom = itemView.findViewById(R.id.tvSentTo)
            tvRecievedMobile = itemView.findViewById(R.id.tvSentMobile)
//            ivStatus = itemView.findViewById(R.id.ivStatus)
        }
    }

    fun filterList(filterdNames: ArrayList<FundTransferHitoryModel>) {
        rechargeHistoryModalList = filterdNames
        notifyDataSetChanged()
    }

    companion object {
        const val imgUrl = " http://edigitalvillage.in/assets/operator_img/"
    }

    // RecyclerView recyclerView;
    init {
        mInflater = LayoutInflater.from(context)
        this.rechargeHistoryModalList = rechargeHistoryModalList
    }
}