package com.satmatgroup.shreeram.adapters_recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.CommisionSlabModel
import com.satmatgroup.shreeram.utils.AppPrefs
import java.util.*

class CommisionSlabadapter(
    context: Context?,
    rechargeHistoryModalList: ArrayList<CommisionSlabModel>
) :
    RecyclerView.Adapter<CommisionSlabadapter.ViewHolder>() {
    private var rechargeHistoryModalList: List<CommisionSlabModel>
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
            layoutInflater.inflate(R.layout.layout_list_commisionslab, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val rechargeHistoryModal: CommisionSlabModel =
            rechargeHistoryModalList[position]

        holder.tvCommisionRecvd.setText("₹ " + rechargeHistoryModal.packcomm_comm)
        holder.tvOpertorName.setText(rechargeHistoryModal.operatorname)

    }

    override fun getItemCount(): Int {
        return rechargeHistoryModalList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var ivStatus: ImageView
        var tvOpertorName: TextView
        var tvCommisionRecvd: TextView


        init {
            tvOpertorName = itemView.findViewById(R.id.tvOperatorName)
            tvCommisionRecvd = itemView.findViewById(R.id.tvCommisionRecvd)

//            ivStatus = itemView.findViewById(R.id.ivStatus)
        }
    }

    fun filterList(filterdNames: ArrayList<CommisionSlabModel>) {
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