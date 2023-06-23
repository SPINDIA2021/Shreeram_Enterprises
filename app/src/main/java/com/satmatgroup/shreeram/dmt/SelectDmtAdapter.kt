package com.satmatgroup.shreeram.dmt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import java.util.ArrayList

class SelectDmtAdapter (
    context: Context?,
    selectDmtModelList: List<SelectDmtModel>,
    mListener: ListAdapterListener
): RecyclerView.Adapter<SelectDmtAdapter.ViewHolder>() {

    private var selectDmtModelList: List<SelectDmtModel>
    private val mInflater: LayoutInflater
    private val mListener: ListAdapterListener
    var mContext: Context? = null
    lateinit var dmtService: String

    interface ListAdapterListener {
        // create an interface
        fun onClickAtOKButton(mobileRechargeModal: SelectDmtModel?) // create callback function
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_dmt_service, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userListModel: SelectDmtModel = selectDmtModelList[position]

        holder.tvSelectDmt.setText(userListModel.name)

        holder.tvSelectDmt.setOnClickListener {
            //holder.tvSelectDmt.setBackgroundColor(R.drawable.a)
            val bundle = Bundle()

            bundle.putSerializable("SelectDmtModel", userListModel)
            val intent = Intent(mContext, DmtLoginActivity::class.java)
            intent.putExtras(bundle)
            mContext!!.startActivity(intent)
            mListener.onClickAtOKButton(userListModel)
        }

    }

    override fun getItemCount(): Int {
        return selectDmtModelList.size
    }

    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        var tvSelectDmt: TextView = itemView.findViewById(R.id.tvSelectDmt)

    }

    init {
        mInflater = LayoutInflater.from(context)
        this.selectDmtModelList = selectDmtModelList
        this.mListener = mListener
    }

    fun filterList(filterdNames: ArrayList<SelectDmtModel>) {
        selectDmtModelList = filterdNames
        notifyDataSetChanged()
    }
}