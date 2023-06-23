package com.satmatgroup.shreeram.adapters_recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserListModel

class UserListAdapter(
    context: Context?,
    recievedMoneyHistoryModelList: List<UserListModel>,
    mListener: ListAdapterListener
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private val recievedMoneyHistoryModelList: List<UserListModel>
    private val mInflater: LayoutInflater
    private val mListener: ListAdapterListener
    var mContext: Context? = null

    interface ListAdapterListener {
        // create an interface
        fun onClickAtOKButton(mobileRechargeModal: UserListModel?) // create callback function
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_useractivity, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val userListModel: UserListModel = recievedMoneyHistoryModelList[position]
        holder.tvUserName.setText("User Name : " + userListModel.cus_name)
        holder.tvMobile.setText("Mobile : " + userListModel.cus_mobile)

        if (userListModel.clbal.equals("null")|| userListModel.clbal==null) {
            holder.tvBalance.setText("Balance  : ₹0")

        } else {
            holder.tvBalance.setText("Balance  : ₹" + userListModel.clbal)

        }

        /*   holder.tvDescription.setText("Sender :"+recievedMoneyHistoryModel.getSender());
        holder.tvAmount.setText("" +mContext.getResources().getString(R.string.Rs)+" " + recievedMoneyHistoryModel.getAmount());
        holder.tvTime.setText(recievedMoneyHistoryModel.getDatetime());
        holder.tvTransactionId.setText("Transaction ID :"+recievedMoneyHistoryModel.getId());
        holder.tvBankName.setVisibility(View.GONE);


*/holder.itemView.setOnClickListener { mListener.onClickAtOKButton(userListModel) }


    }

    override fun getItemCount(): Int {
        return recievedMoneyHistoryModelList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUserName: TextView
        var tvMobile: TextView
        var tvBalance: TextView

        init {
            tvUserName = itemView.findViewById(R.id.tvUserName)
            tvMobile = itemView.findViewById(R.id.tvUserMobile)
            tvBalance = itemView.findViewById(R.id.tvCustomerBalance)
        }
    }

    // RecyclerView recyclerView;
    init {
        mInflater = LayoutInflater.from(context)
        this.recievedMoneyHistoryModelList = recievedMoneyHistoryModelList
        this.mListener = mListener // receive mListener from Fragment (or Activity)
    }
}
