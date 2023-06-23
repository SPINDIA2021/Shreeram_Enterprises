package com.satmatgroup.shreeram.aeps_activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.activities_aeps.aepshistory.AepsHistoryModel
import com.satmatgroup.shreeram.utils.AppPrefs
import java.util.*

class AepsHistoryAdapter(
    context: Context?,
    rechargeHistoryModalList: ArrayList<AepsHistoryModel>
) :
    RecyclerView.Adapter<AepsHistoryAdapter.ViewHolder>() {
    private var rechargeHistoryModalList: List<AepsHistoryModel>
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
            layoutInflater.inflate(R.layout.layout_list_aeps_history, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val rechargeHistoryModal: AepsHistoryModel =
            rechargeHistoryModalList[position]
        holder.tvTxnId.text = rechargeHistoryModal.aeps_id
        holder.tvDate.text = rechargeHistoryModal.aeps_date_time
        try{
            holder.tvTransactionType.text =
                "Transaction Type : " + rechargeHistoryModal.transactionType.toUpperCase()
        }catch (e: Exception){
            holder.tvTransactionType.text =
                "Transaction Type : Not Available"
        }


        holder.tvStatus_ref.text = "Ref ID : " + rechargeHistoryModal.transaction_ref_id
        holder.btnCheckStatus.visibility = GONE

        if (rechargeHistoryModal.status!=null)
        {
            if (rechargeHistoryModal.status.equals("SUCCESS")) {
                holder.tvStatus.text = rechargeHistoryModal.status.toUpperCase()
                holder.tvStatus.setTextColor(mContext!!.resources.getColor(R.color.material_green_700))
                holder.btnCheckStatus.visibility = GONE

            } else if (rechargeHistoryModal.status.equals("PENDING")) {

                holder.tvStatus.text = rechargeHistoryModal.status.toUpperCase()
                holder.tvStatus.setTextColor(mContext!!.resources.getColor(R.color.amber))
                holder.btnCheckStatus.visibility = GONE

            } else {
                holder.tvStatus.text = rechargeHistoryModal.status.toUpperCase()
                holder.tvStatus.setTextColor(mContext!!.resources.getColor(R.color.material_red_500))
                holder.btnCheckStatus.visibility = GONE

            }
        }else{
            holder.tvStatus.text = "Not Available"
            holder.tvStatus.setTextColor(mContext!!.resources.getColor(R.color.material_red_500))
            holder.btnCheckStatus.visibility = GONE
        }

        holder.tvAmount.text = "₹ " + rechargeHistoryModal.amount


    }

    override fun getItemCount(): Int {
        return rechargeHistoryModalList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var ivStatus: ImageView
        var tvTxnId: TextView
        var tvDate: TextView
        var tvAmount: TextView
        var tvStatus: TextView
        var tvStatus_ref: TextView
        var tvTransactionType: TextView
        var btnCheckStatus: Button

        init {
            tvTxnId = itemView.findViewById(R.id.tvTxnId)
            tvDate = itemView.findViewById(R.id.tvDate)
            tvAmount = itemView.findViewById(R.id.tvRecAmnt)
            tvStatus = itemView.findViewById(R.id.tvStatus)
            tvStatus_ref = itemView.findViewById(R.id.tvRefId)
            tvTransactionType = itemView.findViewById(R.id.tvTransactionType)
            btnCheckStatus = itemView.findViewById(R.id.btnCheckStatus)
        }
    }

    fun filterList(filterdNames: ArrayList<AepsHistoryModel>) {
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