package com.satmatgroup.shreeram.adapters_recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.RecentRechargeHistoryModal
import com.satmatgroup.shreeram.utils.AppPrefs
import java.util.*

class CommisionReportAdapter(
    context: Context?,
    rechargeHistoryModalList: ArrayList<RecentRechargeHistoryModal>
) :
    RecyclerView.Adapter<CommisionReportAdapter.ViewHolder>() {
    private var rechargeHistoryModalList: List<RecentRechargeHistoryModal>
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
            layoutInflater.inflate(R.layout.layout_list_commision, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {


        val rechargeHistoryModal: RecentRechargeHistoryModal =
            rechargeHistoryModalList[position]
        holder.tvTxnId.setText(rechargeHistoryModal.recid)
        holder.tvDate.setText(rechargeHistoryModal.reqdate)
        holder.tvRecAmount.text =
            mContext!!.resources.getString(R.string.Rupee) + " " + rechargeHistoryModal.amount
        holder.tvBalance.text =
            mContext!!.resources.getString(R.string.Rupee) + " " + rechargeHistoryModal.txn_clbal
        holder.tvOperator.text = rechargeHistoryModal.operatorname
        holder.tvRecnNumber.text = """Recharge No:${rechargeHistoryModal.mobileno}"""
        holder.tvOpeningBalance.text =
            mContext!!.resources.getString(R.string.Rupee) + " " + rechargeHistoryModal.txn_opbal



        if (user_type.equals("retailer")) {

            if (rechargeHistoryModal.retailer.equals("")) {
                holder.tvCommisionRecvd.text =
                    mContext!!.resources.getString(R.string.Rupee) + "0"


            } else {
                holder.tvCommisionRecvd.text =
                    mContext!!.resources.getString(R.string.Rupee) + " " + rechargeHistoryModal.retailer


            }
        } else if (user_type.equals("distributor")) {
            if (rechargeHistoryModal.distributor.equals("")) {
                holder.tvCommisionRecvd.text =
                    mContext!!.resources.getString(R.string.Rupee) + "0"


            } else {
                holder.tvCommisionRecvd.text =
                    mContext!!.resources.getString(R.string.Rupee) + " " + rechargeHistoryModal.distributor


            }


        } else {
            if (rechargeHistoryModal.master.equals("")) {
                holder.tvCommisionRecvd.text =
                    mContext!!.resources.getString(R.string.Rupee) + "0"


            } else {
                holder.tvCommisionRecvd.text =
                    mContext!!.resources.getString(R.string.Rupee) + " " + rechargeHistoryModal.master


            }
        }


        // holder.ivStatus.setText(rechargeHistoryModal.getAmount());
        if (rechargeHistoryModal.status.toString().equals("FAILED")) {
            holder.tvRecnNumber.setBackgroundColor(mContext!!.resources.getColor(R.color.material_red_500))
            // holder.ivStatus.setImageResource(R.drawable.ic_failed)

        } else if (rechargeHistoryModal.status.toString().equals("SUCCESS")) {
            //holder.ivStatus.setImageResource(R.drawable.checked_right)
            holder.tvRecnNumber.setBackgroundColor(mContext!!.resources.getColor(R.color.material_green_700))

        } else if (rechargeHistoryModal.status.toString().equals("PENDING")) {

            holder.tvRecnNumber.setBackgroundColor(mContext!!.resources.getColor(R.color.amber))

        } else {

        }


        /* if (rechargeHistoryModal.){

            holder.ivOperator.setImageResource(R.drawable.jio);

        }*/
    }

    override fun getItemCount(): Int {
        return rechargeHistoryModalList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var ivStatus: ImageView
        var tvTxnId: TextView
        var tvDate: TextView
        var tvRecAmount: TextView
        var tvBalance: TextView
        var tvRecnNumber: TextView
        var tvOpeningBalance: TextView
        var tvCommisionRecvd: TextView
        var tvOperator: TextView

        init {
            tvTxnId = itemView.findViewById(R.id.tvTxnId)
            tvDate = itemView.findViewById(R.id.tvDate)
            tvRecAmount = itemView.findViewById(R.id.tvRecAmnt)
            tvBalance = itemView.findViewById(R.id.tvBalance)
            tvRecnNumber = itemView.findViewById(R.id.tvRecnNumber)
            tvOpeningBalance = itemView.findViewById(R.id.tvOpeningBal)
            tvCommisionRecvd = itemView.findViewById(R.id.tvCommisionRecvd)
            tvOperator = itemView.findViewById(R.id.tvOperator)
//            ivStatus = itemView.findViewById(R.id.ivStatus)
        }
    }

    fun filterList(filterdNames: ArrayList<RecentRechargeHistoryModal>) {
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