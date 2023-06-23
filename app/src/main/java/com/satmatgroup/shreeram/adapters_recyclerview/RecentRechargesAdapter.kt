package com.satmatgroup.shreeram.adapters_recyclerview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.RecentRechargeHistoryModal
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiUrl.IMAGE_URL
import com.satmatgroup.shreeram.utils.AppPrefs
import java.util.*

class RecentRechargesAdapter(
    context: Context?,
    rechargeHistoryModalList: ArrayList<RecentRechargeHistoryModal>,
    retryClick:View.OnClickListener
) :
    RecyclerView.Adapter<RecentRechargesAdapter.ViewHolder>() {
    private var rechargeHistoryModalList: List<RecentRechargeHistoryModal>
    private val mInflater: LayoutInflater
    private var mContext: Context? = null
    private var retryClick: View.OnClickListener

    lateinit var userModel: UserModel
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mContext = parent.context
        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", mContext)
        userModel = gson.fromJson(json, UserModel::class.java)
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_recharge_history, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val rechargeHistoryModal: RecentRechargeHistoryModal =
            rechargeHistoryModalList[position]
        holder.tvTxnId.setText(rechargeHistoryModal.txn_id)
        holder.tvDate.setText(rechargeHistoryModal.reqdate)
        holder.tvOperator.setText(rechargeHistoryModal.operatorname)
        holder.tvRecAmount.text =
            mContext!!.resources.getString(R.string.Rupee) + " " + rechargeHistoryModal.amount
        holder.tvBalance.text =
            mContext!!.resources.getString(R.string.Rupee) + " " + rechargeHistoryModal.txn_clbal
        holder.tvOperator.text = rechargeHistoryModal.operatorname
        holder.tvStatus.setText(rechargeHistoryModal.status)
        holder.tvRecnNumber.text = rechargeHistoryModal.mobileno
        holder.tvOpeningBalance.text =
            mContext!!.resources.getString(R.string.Rupee) + " " + rechargeHistoryModal.txn_opbal


        Glide.with(mContext!!)
            .load(IMAGE_URL + rechargeHistoryModal.operator_image)
            .into(holder.ivOperator)

        holder.tvRefId.text = rechargeHistoryModal.statusdesc
        if (userModel.cus_type.equals("retailer")) {

            if (rechargeHistoryModal.retailer.equals("")) {
                holder.tvCommisionRecvd.text =
                    mContext!!.resources.getString(R.string.Rupee) + "0"


            } else {
                holder.tvCommisionRecvd.text =
                    mContext!!.resources.getString(R.string.Rupee) + " " + rechargeHistoryModal.retailer


            }
        } else if (userModel.cus_type.equals("distributor")) {
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
            holder.tvDisputedStatus.visibility = View.GONE
            holder.tvRetry.visibility=View.GONE
            holder.tvStatus.text = "FAILED"
            holder.rl_statusDetails.setBackgroundResource(R.drawable.bg_status_failed)
            // holder.ivStatus.setImageResource(R.drawable.ic_failed)
            if (rechargeHistoryModal.dispute_status.toString().equals("disputed")) {
                holder.tvDisputedStatus.visibility = View.GONE
                holder.tvRetry.visibility=View.GONE
                holder.tvDisputedStatus.visibility = View.VISIBLE
            } else {
                holder.tvDisputedStatus.visibility = View.GONE
                holder.tvRetry.visibility=View.GONE
            }
        } else if (rechargeHistoryModal.status.toString().equals("SUCCESS")) {
            holder.tvStatus.text = "SUCCESS"
            holder.rl_statusDetails.setBackgroundResource(R.drawable.bg_status_success)
            //holder.ivStatus.setImageResource(R.drawable.checked_right)
            holder.tvDisputedStatus.visibility = View.VISIBLE
            holder.tvRetry.visibility=View.GONE

            if (rechargeHistoryModal.dispute_status.equals("disputed")) {
                holder.tvDisputedStatus.visibility = View.VISIBLE
                holder.tvRetry.visibility=View.GONE
            } else {
                holder.tvDisputedStatus.visibility = View.GONE
                holder.tvRetry.visibility=View.GONE
            }
        } else if (rechargeHistoryModal.status.toString().equals("PENDING")) {
            holder.tvDisputedStatus.visibility = View.GONE
            holder.tvRetry.visibility=View.VISIBLE
            holder.tvStatus.text = "PENDING"
            holder.rl_statusDetails.setBackgroundResource(R.drawable.bg_status_pending)

            // holder.ivStatus.setImageResource(R.drawable.ic_pending)
            if (rechargeHistoryModal.dispute_status.equals("disputed")) {
                holder.tvDisputedStatus.visibility = View.VISIBLE
                holder.tvDisputedStatus.setText("DISPUTED")
            } else {
                holder.tvDisputedStatus.visibility = View.GONE
            }
        } else {
            holder.tvDisputedStatus.visibility = View.VISIBLE
            holder.tvRetry.visibility=View.GONE
        }
        holder.tvDisputedStatus.setOnClickListener {
            val bundle = Bundle()
            /*      val disputeIntent = Intent(mContext, RaiseDisputeActivity::class.java)
                  bundle.putSerializable("rechargeDetails", rechargeHistoryModal)
                  disputeIntent.putExtras(bundle)
                  mContext!!.startActivity(disputeIntent)*/
        }

        holder.tvRetry.setTag(position)
        holder.tvRetry.setOnClickListener(retryClick)
        /* if (rechargeHistoryModal.){

            holder.ivOperator.setImageResource(R.drawable.jio);

        }*/
    }

    override fun getItemCount(): Int {
        return rechargeHistoryModalList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivOperator: ImageView

        // var ivStatus: ImageView
        var tvTxnId: TextView
        var tvDate: TextView
        var tvRecAmount: TextView
        var tvBalance: TextView
        var tvStatus: TextView
        var tvRecnNumber: TextView
        var tvDisputedStatus: TextView
        var tvRetry:TextView
        var tvOpeningBalance: TextView
        var tvCommisionRecvd: TextView
        var tvOperator: TextView
        var tvRefId: TextView
        var rl_statusDetails: RelativeLayout

        init {
            tvTxnId = itemView.findViewById(R.id.tvTxnId)
            tvDate = itemView.findViewById(R.id.tvTxnDate)
            tvRecAmount = itemView.findViewById(R.id.tvRecAmount)
            tvBalance = itemView.findViewById(R.id.tvClosingBal)
            tvStatus = itemView.findViewById(R.id.tvRecStatus)
            tvRecnNumber = itemView.findViewById(R.id.tvRechargeMobileNumber)
            tvDisputedStatus = itemView.findViewById(R.id.tvDisputeBtn)
            tvRetry=itemView.findViewById(R.id.tvRetryBtn)
            ivOperator = itemView.findViewById(R.id.ivOperator)
            tvOpeningBalance = itemView.findViewById(R.id.tvOpeningBal)
            tvCommisionRecvd = itemView.findViewById(R.id.tvCommissionRcvd)
            tvOperator = itemView.findViewById(R.id.tvOperatorName)
            tvRefId = itemView.findViewById(R.id.tvRecId)
            rl_statusDetails = itemView.findViewById(R.id.rl_statusDetails)
        }
    }

    fun filterList(filterdNames: ArrayList<RecentRechargeHistoryModal>) {
        rechargeHistoryModalList = filterdNames
        notifyDataSetChanged()
    }

    companion object {
        const val imgUrl = "http://edigitalvillage.in/assets/operator_img/"
    }

    // RecyclerView recyclerView;
    init {
        mInflater = LayoutInflater.from(context)
        this.rechargeHistoryModalList = rechargeHistoryModalList
        this.retryClick = retryClick
    }
}