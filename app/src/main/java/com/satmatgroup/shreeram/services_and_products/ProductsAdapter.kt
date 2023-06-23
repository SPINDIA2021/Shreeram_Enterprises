package com.satmatgroup.shreeram.services_and_products

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.dmt.SelectDmtModel
import java.util.ArrayList

class ProductsAdapter (
    context: Context?,
    productsModelList: List<ProductsModel>,
    mListener: ProductsAdapter.ListAdapterListener
): RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    private var productsModelList: List<ProductsModel>
    private val mInflater: LayoutInflater
    private val mListener: ProductsAdapter.ListAdapterListener
    var mContext: Context? = null

    interface ListAdapterListener {
        // create an interface
        fun onClickAtOKButton(productsModel: ProductsModel?) // create callback function
    }

    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_products, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ProductsAdapter.ViewHolder, position: Int) {

        val productsModel: ProductsModel = productsModelList[position]

        holder.tvProduct.setText(productsModel.pro_name)
        holder.tvPrice.setText("₹"+productsModel.pro_amount)
        holder.tvStock.setText("Stock Available: "+productsModel.pro_quantity)

        Glide.with(mContext!!)
            .load(productsModel.pro_image)
            .into(holder.ivProduct)

        holder.itemView.setOnClickListener(View.OnClickListener {

            val bundle = Bundle()
            bundle.putString("ProductId",productsModel.product_id)
            bundle.putString("ProductName",productsModel.pro_name)
            bundle.putString("ProductAmount",productsModel.pro_amount)
            bundle.putString("ProductImage",productsModel.pro_image)
            bundle.putString("ProductQuantity",productsModel.pro_quantity)
            bundle.putString("AddedDate",productsModel.added_date)

            val intent = Intent(mContext, BuyOrBookActivity::class.java)
            intent.putExtras(bundle)
            mContext!!.startActivity(intent)

        })
       
    }

    override fun getItemCount(): Int {
        return productsModelList.size
    }

    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        //var tvSelectDmt: TextView = itemView.findViewById(R.id.tvSelectDmt)
        var tvProduct: TextView = itemView.findViewById(R.id.tvProduct)
        var tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        var ivProduct: ImageView = itemView.findViewById(R.id.ivProduct)
        var tvStock: TextView = itemView.findViewById(R.id.tvStock)
    }

    init {
        mInflater = LayoutInflater.from(context)
        this.productsModelList = productsModelList
        this.mListener = mListener
    }

    fun filterList(filterdNames: ArrayList<ProductsModel>) {
        productsModelList = filterdNames
        notifyDataSetChanged()
    }
}