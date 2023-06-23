package com.satmatgroup.shreeram.a

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.DisputeHistoryModel
import com.satmatgroup.shreeram.utils.AppPrefs
import java.util.*

class DisputeHistoryAdapter(
    context: Context?,
    rechargeHistoryModalList: ArrayList<DisputeHistoryModel>
) :
    RecyclerView.Adapter<DisputeHistoryAdapter.ViewHolder>() {
    private var rechargeHistoryModalList: List<DisputeHistoryModel>
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
            layoutInflater.inflate(R.layout.layout_list_disputehistory, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val rechargeHistoryModal: DisputeHistoryModel =
            rechargeHistoryModalList[position]

        holder.tvTicketId.text = rechargeHistoryModal.t_id
        holder.tvDate.text = rechargeHistoryModal.ticket_date
        holder.tvIssueRaised.text = rechargeHistoryModal.issue
        holder.tvAdminMsg.text = rechargeHistoryModal.message
        holder.tvMobile.text = "Mobile : " + rechargeHistoryModal.mobileno
        holder.tvRecId.text = rechargeHistoryModal.rec_id
        holder.tvDisputeStatus.text = rechargeHistoryModal.dispute_status


    }

    override fun getItemCount(): Int {
        return rechargeHistoryModalList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var ivStatus: ImageView
        var tvTicketId: TextView
        var tvRecId: TextView
        var tvDate: TextView
        var tvIssueRaised: TextView
        var tvAdminMsg: TextView
        var tvMobile: TextView
        var tvDisputeStatus: TextView


        init {
            tvTicketId = itemView.findViewById(R.id.tvTicketId)
            tvRecId = itemView.findViewById(R.id.tvRecId)
            tvDate = itemView.findViewById(R.id.tvDate)
            tvIssueRaised = itemView.findViewById(R.id.tvIssueRaised)
            tvAdminMsg = itemView.findViewById(R.id.tvAdminMsg)
            tvMobile = itemView.findViewById(R.id.tvMobile)
            tvDisputeStatus = itemView.findViewById(R.id.tvDisputeStatus)

//            ivStatus = itemView.findViewById(R.id.ivStatus)
        }
    }

    fun filterList(filterdNames: ArrayList<DisputeHistoryModel>) {
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