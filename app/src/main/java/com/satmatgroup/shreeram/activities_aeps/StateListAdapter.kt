package com.satmatgroup.shreeram.activities_aeps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R

class StateListAdapter(
    context: Context?,
    stateListModelList: List<StateListModel>,
    mListener: ListAdapterListener
) : RecyclerView.Adapter<StateListAdapter.ViewHolder>()  {


    private val stateListModelList: List<StateListModel>
    private val mInflater: LayoutInflater
    private val mListener: ListAdapterListener
    var mContext: Context? = null

    interface ListAdapterListener {
        // create an interface
        fun onClickAtOKButton(StateListModel: StateListModel?) // create callback function
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvOperatorName: TextView

        init {
            tvOperatorName = itemView.findViewById(R.id.tvOperatorName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateListAdapter.ViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_operators, parent, false)
        return ViewHolder(listItem)

    }

    override fun onBindViewHolder(holder: StateListAdapter.ViewHolder, position: Int) {
        val StateListModel: StateListModel = stateListModelList[position]

        holder.tvOperatorName.setText(StateListModel.state)
        holder.itemView.setOnClickListener { mListener.onClickAtOKButton(StateListModel) }

    }

    override fun getItemCount(): Int {
        return stateListModelList.size
    }

    init {
        mInflater = LayoutInflater.from(context)
        this.stateListModelList = stateListModelList
        this.mListener = mListener // receive mListener from Fragment (or Activity)
    }

}