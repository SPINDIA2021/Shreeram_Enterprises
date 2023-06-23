package com.satmatgroup.shreeram.activities_aeps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.activities_aeps.aepshistory.AepsHistoryModel
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.utils.AppPrefs
import java.util.*

class AepsCommissionHistoryAdapter(
    context: Context?,
    rechargeHistoryModalList: ArrayList<AepsHistoryModel>
) :
    RecyclerView.Adapter<AepsCommissionHistoryAdapter.ViewHolder>() {
    private var rechargeHistoryModalList: List<AepsHistoryModel>
    private val mInflater: LayoutInflater
    private var mContext: Context? = null


    lateinit var userModel : UserModel
    var user_type: String = ""
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mContext = parent.context
        user_type = AppPrefs.getStringPref("user_type", mContext).toString()
        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", mContext)
        userModel = gson.fromJson(json, UserModel::class.java)
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_aeps_commision, parent, false)
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
        holder.tvTransactionType.text =
            "Transaction Type : " + rechargeHistoryModal.transactionType.toUpperCase()

/*
        if (rechargeHistoryModal.status.equals("SUCCESS")) {
            holder.tvAmount.setBackgroundColor(mContext!!.resources.getColor(R.color.material_green_700))

        } else if (rechargeHistoryModal.status.equals("PENDING")) {

            holder.tvAmount.setBackgroundColor(mContext!!.resources.getColor(R.color.amber))

        } else {
            holder.tvAmount.setBackgroundColor(mContext!!.resources.getColor(R.color.material_red_500))

        }*/
        holder.tvAmount.text = "Amount : ₹ " + rechargeHistoryModal.amount
        if (userModel.cus_type.equals("retailer")){
            holder.tvCommissionRecieved.text ="Commission Recvd :₹"+rechargeHistoryModal.retailer_commission

        }else if (userModel.cus_type.equals("master")){
            holder.tvCommissionRecieved.text ="Commission Recvd :₹"+rechargeHistoryModal.master_commission


        }else{

            holder.tvCommissionRecieved.text ="Commission Recvd :₹"+rechargeHistoryModal.distributor_commission

        }

        holder.tvUserType.setText(rechargeHistoryModal.status.toUpperCase())


    }

    override fun getItemCount(): Int {
        return rechargeHistoryModalList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var ivStatus: ImageView
        var tvTxnId: TextView
        var tvDate: TextView
        var tvAmount: TextView

        var tvTransactionType: TextView
       var tvCommissionRecieved : TextView
       var tvUserType : TextView

        init {

            tvTxnId = itemView.findViewById(R.id.tvTxnId)
            tvDate = itemView.findViewById(R.id.tvDate)
            tvAmount = itemView.findViewById(R.id.tvAmount)
            tvTransactionType = itemView.findViewById(R.id.tvTxnType)
            tvCommissionRecieved = itemView.findViewById(R.id.tvCommissionReceived)
            tvUserType = itemView.findViewById(R.id.tvStatusType)


        }
    }

    fun filterList(filterdNames: ArrayList<AepsHistoryModel>) {
        rechargeHistoryModalList = filterdNames
        notifyDataSetChanged()
    }

    companion object {
        const val imgUrl = " http:// edigitalvillage.in/assets/operator_img/"
    }

    // RecyclerView recyclerView;
    init {
        mInflater = LayoutInflater.from(context)
        this.rechargeHistoryModalList = rechargeHistoryModalList
    }
}