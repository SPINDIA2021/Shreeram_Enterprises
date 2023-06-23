package com.satmatgroup.shreeram.adapters_recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.CircleListModel


class CircleListAdapter(
    context: Context?,
    circleListModelList: List<CircleListModel>,
    mListener: ListAdapterListener
) : RecyclerView.Adapter<CircleListAdapter.ViewHolder>() {


    private val circleListModelList: List<CircleListModel>
    private val mInflater: LayoutInflater
    private val mListener: CircleListAdapter.ListAdapterListener
    var mContext: Context? = null

    interface ListAdapterListener {
        // create an interface
        fun onClickAtOKButton(CircleListModel: CircleListModel?) // create callback function
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvOperatorName: TextView

        init {
            tvOperatorName = itemView.findViewById(R.id.tvOperatorName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_operators, parent, false)
        return CircleListAdapter.ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val CircleListModel: CircleListModel = circleListModelList[position]

        holder.tvOperatorName.setText(CircleListModel.state)
        holder.itemView.setOnClickListener { mListener.onClickAtOKButton(CircleListModel) }
    }

    override fun getItemCount(): Int {
        return circleListModelList.size
    }

    init {
        mInflater = LayoutInflater.from(context)
        this.circleListModelList = circleListModelList
        this.mListener = mListener // receive mListener from Fragment (or Activity)
    }

}