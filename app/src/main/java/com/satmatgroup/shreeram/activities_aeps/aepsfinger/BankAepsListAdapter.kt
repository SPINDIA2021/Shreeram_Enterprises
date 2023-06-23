package com.satmatgroup.shreeram.activities_aeps.aepsfinger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R

class BankAepsListAdapter(
    context: Context?,
    recievedMoneyHistoryModelList: List<AepsBankModel>,
    mListener: ListAdapterListener
) : RecyclerView.Adapter<BankAepsListAdapter.ViewHolder>() {
    private var recievedMoneyHistoryModelList: List<AepsBankModel>
    private val mInflater: LayoutInflater
    private val mListener: ListAdapterListener
    var mContext: Context? = null

    interface ListAdapterListener {
        // create an interface
        fun onClickAtOKButton(mobileRechargeModal: AepsBankModel?) // create callback function
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_banks, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val userListModel: AepsBankModel = recievedMoneyHistoryModelList[position]
        holder.tvBankName.setText(userListModel.bankName)


        /*   holder.tvDescription.setText("Sender :"+recievedMoneyHistoryModel.getSender());
        holder.tvAmount.setText("" +mContext.getResources().getString(R.string.Rs)+" " + recievedMoneyHistoryModel.getAmount());
        holder.tvTime.setText(recievedMoneyHistoryModel.getDatetime());
        holder.tvTransactionId.setText("Transaction ID :"+recievedMoneyHistoryModel.getId());
        holder.tvBankName.setVisibility(View.GONE);


*/
        holder.itemView.setOnClickListener { mListener.onClickAtOKButton(userListModel) }


    }

    override fun getItemCount(): Int {
        return recievedMoneyHistoryModelList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvBankName: TextView = itemView.findViewById(R.id.tvBankName)

    }

    // RecyclerView recyclerView;
    init {
        mInflater = LayoutInflater.from(context)
        this.recievedMoneyHistoryModelList = recievedMoneyHistoryModelList
        this.mListener = mListener // receive mListener from Fragment (or Activity)
    }

    fun updateList( list: List<AepsBankModel>) {
        recievedMoneyHistoryModelList = list
        notifyDataSetChanged()
    }
}
