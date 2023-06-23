package com.satmatgroup.shreeram.activities_aeps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.utils.AppPrefs
import java.util.*

class MinistatementAdapter(
    context: Context?,
    rechargeHistoryModalList: ArrayList< MiniStatementModel>
) :
    RecyclerView.Adapter<MinistatementAdapter.ViewHolder>() {
    private var rechargeHistoryModalList: List< MiniStatementModel>
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
            layoutInflater.inflate(R.layout.layout_list_ministatement, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val rechargeHistoryModal:  MiniStatementModel =
            rechargeHistoryModalList[position]

   //     holder.tvCommisionRecvd.setText("₹ " + rechargeHistoryModal.packcomm_comm)
        holder.tvTransDate.setText(""+rechargeHistoryModal.date)
        holder.tvTransType.setText(""+rechargeHistoryModal.txnType)
        holder.tvAmount.setText("₹"+rechargeHistoryModal.amount)
        holder.tvNarration.setText(""+rechargeHistoryModal.narration)


    }

    override fun getItemCount(): Int {
        return rechargeHistoryModalList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var ivStatus: ImageView
        var tvTransDate: TextView
        var tvTransType: TextView
        var tvAmount: TextView
        var tvNarration: TextView


        init {
            tvTransDate = itemView.findViewById(R.id.tvTransDate)
            tvTransType = itemView.findViewById(R.id.tvTransType)
            tvAmount = itemView.findViewById(R.id.tvAmount)
            tvNarration = itemView.findViewById(R.id.tvNarration)

//            ivStatus = itemView.findViewById(R.id.ivStatus)
        }
    }

    fun filterList(filterdNames: ArrayList< MiniStatementModel>) {
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