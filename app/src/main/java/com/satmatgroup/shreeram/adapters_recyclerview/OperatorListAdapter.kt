package com.satmatgroup.shreeram.adapters_recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.OperatorsModel
import com.satmatgroup.shreeram.network_calls.AppApiUrl.IMAGE_URL
import de.hdodenhof.circleimageview.CircleImageView

class OperatorListAdapter(
    context: Context?,
    recievedMoneyHistoryModelList: List<OperatorsModel>,
    mListener: ListAdapterListener
) : RecyclerView.Adapter<OperatorListAdapter.ViewHolder>() {
    private val recievedMoneyHistoryModelList: List<OperatorsModel>
    private val mInflater: LayoutInflater
    private val mListener: ListAdapterListener
    var mContext: Context? = null

    interface ListAdapterListener {
        // create an interface
        fun onClickAtOKButton(operatorsModel: OperatorsModel?) // create callback function
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_operators, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val operatorsModel: OperatorsModel = recievedMoneyHistoryModelList[position]
        holder.tvOperatorName.setText(operatorsModel.operatorname)
        holder.itemView.setOnClickListener { mListener.onClickAtOKButton(operatorsModel) }

        Glide.with(mContext!!)
            .load(IMAGE_URL + operatorsModel.operator_image)
            .into(holder.ivOperator)

    }

    override fun getItemCount(): Int {
        return recievedMoneyHistoryModelList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivOperator: CircleImageView
        var tvOperatorName: TextView

        init {
            tvOperatorName = itemView.findViewById(R.id.tvOperatorName)
            ivOperator = itemView.findViewById(R.id.ivOperator)
        }
    }

    // RecyclerView recyclerView;
    init {
        mInflater = LayoutInflater.from(context)
        this.recievedMoneyHistoryModelList = recievedMoneyHistoryModelList
        this.mListener = mListener // receive mListener from Fragment (or Activity)
    }
}
