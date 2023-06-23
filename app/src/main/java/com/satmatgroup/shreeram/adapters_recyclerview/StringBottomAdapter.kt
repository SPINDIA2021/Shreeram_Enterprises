package com.satmatgroup.shreeram.adapters_recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.StringModel


class StringBottomAdapter(
    context: Context?,
    recievedMoneyHistoryModelList: List<StringModel>,
    mListener: ListAdapterListener
) : RecyclerView.Adapter<StringBottomAdapter.ViewHolder>() {
    private val recievedMoneyHistoryModelList: List<StringModel>
    private val mInflater: LayoutInflater
    private val mListener: ListAdapterListener
    var mContext: Context? = null

    interface ListAdapterListener {
        // create an interface
        fun onClickAtOKButton(mobileRechargeModal: StringModel) // create callback function
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_custombottomadapter, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val mobileRechargeModal: StringModel = recievedMoneyHistoryModelList[position]
        holder.tvString.setText(mobileRechargeModal.ListName)
        /*   holder.tvDescription.setText("Sender :"+recievedMoneyHistoryModel.getSender());
        holder.tvAmount.setText("" +mContext.getResources().getString(R.string.Rs)+" " + recievedMoneyHistoryModel.getAmount());
        holder.tvTime.setText(recievedMoneyHistoryModel.getDatetime());
        holder.tvTransactionId.setText("Transaction ID :"+recievedMoneyHistoryModel.getId());
        holder.tvBankName.setVisibility(View.GONE);


*/holder.itemView.setOnClickListener { mListener.onClickAtOKButton(mobileRechargeModal) }


    }

    override fun getItemCount(): Int {
        return recievedMoneyHistoryModelList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvString: TextView

        init {
            tvString = itemView.findViewById(R.id.tvString)
        }
    }

    // RecyclerView recyclerView;
    init {
        mInflater = LayoutInflater.from(context)
        this.recievedMoneyHistoryModelList = recievedMoneyHistoryModelList
        this.mListener = mListener // receive mListener from Fragment (or Activity)
    }
}
