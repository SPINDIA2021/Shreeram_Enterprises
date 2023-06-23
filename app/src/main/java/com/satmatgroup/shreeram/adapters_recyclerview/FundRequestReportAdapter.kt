package com.satmatgroup.shreeram.adapters_recyclerview

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.FundRequestModel
import com.satmatgroup.shreeram.utils.AppPrefs
import java.util.*

class FundRequestReportAdapter(
    context: Context?,
    rechargeHistoryModalList: ArrayList<FundRequestModel>
) :
    RecyclerView.Adapter<FundRequestReportAdapter.ViewHolder>() {
    private var rechargeHistoryModalList: List<FundRequestModel>
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
            layoutInflater.inflate(R.layout.layout_list_fundrequestreport, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val rechargeHistoryModal: FundRequestModel =
            rechargeHistoryModalList[position]
        holder.tvTxnId.setText(rechargeHistoryModal.req_id)
         holder.tvDate.text = rechargeHistoryModal.req_date

        if (rechargeHistoryModal.req_status.equals("0")) {

            holder.tvStatus.setText("DECLINED")
            holder.tvStatus.setTextColor(Color.RED)

        } else if (rechargeHistoryModal.req_status.equals("1")) {

            holder.tvStatus.setText("ACCEPTED")
            holder.tvStatus.setTextColor(Color.GREEN)

        }else{

            holder.tvStatus.setText("PENDING")
            holder.tvStatus.setTextColor(Color.YELLOW)

        }

        holder.tvAmount.text = "₹ " + rechargeHistoryModal.pay_amount
        holder.tvReqTo.text =rechargeHistoryModal.request_from


    }

    override fun getItemCount(): Int {
        return rechargeHistoryModalList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var ivStatus: ImageView
        var tvTxnId: TextView
        var tvDate: TextView
        var tvReqTo: TextView
        var tvAmount: TextView
        var tvStatus: TextView


        init {
            tvTxnId = itemView.findViewById(R.id.tvReqId)
            tvDate = itemView.findViewById(R.id.tvDate)
            tvReqTo = itemView.findViewById(R.id.tvReqTo)
            tvAmount = itemView.findViewById(R.id.tvAmount)
            tvStatus = itemView.findViewById(R.id.tvStatus)


//            ivStatus = itemView.findViewById(R.id.ivStatus)
        }
    }

    fun filterList(filterdNames: ArrayList<FundRequestModel>) {
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