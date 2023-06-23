package com.satmatgroup.shreeram.adapters_recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserListModel
import com.satmatgroup.shreeram.utils.AppPrefs
import java.util.*

class UserListActivityAdapter(
    context: Context?,
    rechargeHistoryModalList: ArrayList<UserListModel>,
    mListener: ListAdapterListener

) :
    RecyclerView.Adapter<UserListActivityAdapter.ViewHolder>() {
    private var rechargeHistoryModalList: List<UserListModel>
    private val mInflater: LayoutInflater
    private var mContext: Context? = null
    private val mListener: ListAdapterListener

    var user_type: String = ""

    interface ListAdapterListener {
        // create an interface
        fun onClickAtOKButton(userListModel: UserListModel) // create callback function
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mContext = parent.context
        user_type = AppPrefs.getStringPref("user_type", mContext).toString()
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_useractivity, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val userListModel: UserListModel =
            rechargeHistoryModalList[position]
//
        holder.tvUserName.setText("User Name : " + userListModel.cus_name)
        holder.tvUserMobile.setText("Mobile : " + userListModel.cus_mobile)

        if (userListModel.clbal.equals("null") || userListModel.clbal == null) {
            holder.tvUserBalance.setText("Balance  : ₹0")
        } else {
            holder.tvUserBalance.setText("Balance  : ₹" + userListModel.clbal)

        }



        holder.btnSendFunds.setOnClickListener {
            mListener.onClickAtOKButton(userListModel)
        }


    }

    override fun getItemCount(): Int {
        return rechargeHistoryModalList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var ivStatus: ImageView
        var tvUserName: TextView
        var tvUserMobile: TextView
        var tvUserBalance: TextView
        var btnSendFunds: ImageView


        init {
            tvUserName = itemView.findViewById(R.id.tvUserName)
            tvUserMobile = itemView.findViewById(R.id.tvUserMobile)
            tvUserBalance = itemView.findViewById(R.id.tvCustomerBalance)
            btnSendFunds = itemView.findViewById(R.id.ivSendMoney)


//            ivStatus = itemView.findViewById(R.id.ivStatus)
        }
    }

    fun filterList(filterdNames: ArrayList<UserListModel>) {
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
        this.mListener = mListener // receive mListener from Fragment (or Activity)

    }
}