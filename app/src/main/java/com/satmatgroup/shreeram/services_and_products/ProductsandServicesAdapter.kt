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
import java.util.ArrayList

class ProductsandServicesAdapter (
    context: Context?,
    productsandServicesModelList: List<ProductsandServicesModel>,
    mListener: ProductsandServicesAdapter.ListAdapterListener
): RecyclerView.Adapter<ProductsandServicesAdapter.ViewHolder>() {
    private var productsandServicesModelList: List<ProductsandServicesModel>
    private val mInflater: LayoutInflater
    private val mListener: ProductsandServicesAdapter.ListAdapterListener
    var mContext: Context? = null

    interface ListAdapterListener {
        // create an interface
        fun onClickAtOKButton(productsandServicesModel: ProductsandServicesModel?) // create callback function
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_products, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ProductsandServicesAdapter.ViewHolder, position: Int) {

        val productsandServicesModel: ProductsandServicesModel = productsandServicesModelList[position]

        holder.tvProduct.setText(productsandServicesModel.offer_name)
        holder.tvPrice.setText("₹"+productsandServicesModel.sale_price)
        //holder.tvStock.setText("Stock Available: "+productsandServicesModel.)

        Glide.with(mContext!!)
            .load(productsandServicesModel.banner)
            .into(holder.ivProduct)

        holder.itemView.setOnClickListener(View.OnClickListener {

            val bundle = Bundle()

            bundle.putString("product_service_id",productsandServicesModel.product_service_id)

            val intent = Intent(mContext, BuySingleProductActivity::class.java)
            intent.putExtras(bundle)
            mContext!!.startActivity(intent)

        })

    }

    override fun getItemCount(): Int {
        return productsandServicesModelList.size
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
        this.productsandServicesModelList = productsandServicesModelList
        this.mListener = mListener
    }

    fun filterList(filterdNames: ArrayList<ProductsandServicesModel>) {
        productsandServicesModelList = filterdNames
        notifyDataSetChanged()
    }


}