package com.satmatgroup.shreeram.services_and_products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import java.util.ArrayList


class ProductsHistoryAdapter (
    context: Context?,
    productsHistoryModelList: List<ProductsHistoryModel>,
    mListener: ProductsHistoryAdapter.ListAdapterListener
): RecyclerView.Adapter<ProductsHistoryAdapter.ViewHolder>() {
    private var productsHistoryModelList: List<ProductsHistoryModel>
    private val mInflater: LayoutInflater
    private val mListener: ListAdapterListener
    var mContext: Context? = null

    interface ListAdapterListener {
        // create an interface
        fun onClickAtOKButton(productsHistoryModel: ProductsHistoryModel?) // create callback function
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_purchase_history, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val productsHistoryModel: ProductsHistoryModel = productsHistoryModelList[position]

        if(productsHistoryModel.txn_type.equals("Service Buy")) {
            holder.rl_statusDetailsProduct.setBackgroundResource(R.drawable.bg_status_service)
        } else {
            holder.rl_statusDetailsProduct.setBackgroundResource(R.drawable.bg_status_product)
        }

        holder.tvTxnId.setText(productsHistoryModel.txn_id)
        holder.tvTransactionId.setText(productsHistoryModel.transaction_id)
        holder.tvProductType.setText(productsHistoryModel.txn_type)
        holder.tvProductHistoryName.setText(productsHistoryModel.txn_comment)
        holder.tvTxnDate.setText(productsHistoryModel.txn_date)
        holder.tvOpeningBalProd.setText("₹"+productsHistoryModel.txn_opbal)
        holder.tvAmountdebited.setText("₹"+productsHistoryModel.txn_dbdt)
        holder.tvClosingBalProd.setText("₹"+productsHistoryModel.txn_clbal)
        holder.tvRecAmount.setText("₹"+productsHistoryModel.txn_dbdt)

    }

    override fun getItemCount(): Int {
        return productsHistoryModelList.size
    }

    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        //var tvSelectDmt: TextView = itemView.findViewById(R.id.tvSelectDmt)
        var tvTxnId: TextView = itemView.findViewById(R.id.tvTxnId)
        var tvTransactionId: TextView = itemView.findViewById(R.id.tvTransactionId)
        var tvProductType: TextView = itemView.findViewById(R.id.tvProductType)
        var tvProductHistoryName: TextView = itemView.findViewById(R.id.tvProductHistoryName)
        var tvTxnDate: TextView = itemView.findViewById(R.id.tvTxnDate)
        var tvOpeningBalProd: TextView = itemView.findViewById(R.id.tvOpeningBalProd)
        var tvAmountdebited: TextView = itemView.findViewById(R.id.tvAmountdebited)
        var tvClosingBalProd: TextView = itemView.findViewById(R.id.tvClosingBalProd)
        var tvRecAmount: TextView = itemView.findViewById(R.id.tvRecAmount)
        var rl_statusDetailsProduct: RelativeLayout = itemView.findViewById(R.id.rl_statusDetailsProduct)
    }

    init {
        mInflater = LayoutInflater.from(context)
        this.productsHistoryModelList = productsHistoryModelList
        this.mListener = mListener
    }

    fun filterList(filterdNames: ArrayList<ProductsHistoryModel>) {
        productsHistoryModelList = filterdNames
        notifyDataSetChanged()
    }

}