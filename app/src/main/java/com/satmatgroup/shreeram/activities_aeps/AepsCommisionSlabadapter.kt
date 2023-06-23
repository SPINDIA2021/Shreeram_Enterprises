package com.satmatgroup.shreeram.activities_aeps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.utils.AppPrefs
import java.util.*

class AepsCommisionSlabadapter(
    context: Context?,
    rechargeHistoryModalList: ArrayList<AepsCommissionSlabModel>
) :
    RecyclerView.Adapter<AepsCommisionSlabadapter.ViewHolder>() {
    private var rechargeHistoryModalList: List<AepsCommissionSlabModel>
    private val mInflater: LayoutInflater
    private var mContext: Context? = null
    lateinit var userModel: UserModel

    var user_type: String = ""
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mContext = parent.context
        user_type = AppPrefs.getStringPref("user_type", mContext).toString()
        val layoutInflater = LayoutInflater.from(parent.context)

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", mContext)
        userModel = gson.fromJson(json, UserModel::class.java)

        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_commisionslab_dmt, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val rechargeHistoryModal: AepsCommissionSlabModel =
            rechargeHistoryModalList[position]


        holder.tvSlab.text = ""+rechargeHistoryModal.amount_min_range+"-"+rechargeHistoryModal.amount_max_range
        holder.tvType.text = ""+rechargeHistoryModal.member_type



        if(userModel.cus_type.equals("retailer")) {
            holder.tvCommission.text = ""+rechargeHistoryModal.retailer_comm
        } else if (userModel.cus_type.equals("distributor")) {
            holder.tvCommission.text = ""+rechargeHistoryModal.distributor_comm
        } else if (userModel.cus_type.equals("master")) {
            holder.tvCommission.text = ""+rechargeHistoryModal.master_comm
        }

    }

    override fun getItemCount(): Int {
        return rechargeHistoryModalList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var ivStatus: ImageView
        var tvSlab: TextView
        var tvCommission: TextView
        var tvType: TextView



        init {
            tvSlab = itemView.findViewById(R.id.tvSlab)
            tvCommission = itemView.findViewById(R.id.tvCommission)
            tvType = itemView.findViewById(R.id.tvType)


//            ivStatus = itemView.findViewById(R.id.ivStatus)
        }
    }

    fun filterList(filterdNames: ArrayList<AepsCommissionSlabModel>) {
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