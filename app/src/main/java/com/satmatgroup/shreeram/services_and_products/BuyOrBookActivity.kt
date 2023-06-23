package com.satmatgroup.shreeram.services_and_products

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import com.satmatgroup.shreeram.utils.toast
import kotlinx.android.synthetic.main.activity_buy_or_book.*
import kotlinx.android.synthetic.main.activity_buy_or_book.progress_bar
import kotlinx.android.synthetic.main.activity_buy_products.*
import kotlinx.android.synthetic.main.activity_dmt_login.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class BuyOrBookActivity : AppCompatActivity(),  AppApiCalls.OnAPICallCompleteListener {

    lateinit var userModel: UserModel
    val BUYPRODUCT: String = "BUYPRODUCT"
    val GET_SINGLE_PRODUCTS: String = "GET_SINGLE_PRODUCTS"

    var Price :Int = 0
    var totalPrice :Int = 0
    var product_quantity = 0

    lateinit var productId: String
    lateinit var productName: String
    lateinit var productAmount: String
    lateinit var productImage: String
    lateinit var productStock: String
    lateinit var productAddedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_or_book)

        val gson = Gson()
        val json = AppPrefs.getStringPref(AppConstants.USER_MODEL, this)
        userModel = gson.fromJson(json, UserModel::class.java)

        val bundle: Bundle? = intent.extras
        productId = bundle!!.getString("ProductId").toString()
        productName = bundle!!.getString("ProductName").toString()
        productAmount = bundle!!.getString("ProductAmount").toString()
        productImage = bundle!!.getString("ProductImage").toString()
        productStock = bundle!!.getString("ProductQuantity").toString()
        productAddedDate = bundle!!.getString("AddedDate").toString()

        Glide.with(this)
            .load(productImage)
            .into(ivProductImg)

        tv_productName.setText(productName)
        tvProductPrice.setText(productAmount)
        product_quantity = 1

        tvProductStock.setText("Available Stock: "+productStock)

        //val price = tvProductPrice.text.toString().substring(1).toInt()
        //totalPrice = price

        tv_product_quantity.setText(product_quantity.toString())
        product_quantity = tv_product_quantity.text.toString().toInt()

        iv_remove.setOnClickListener {
            if (product_quantity == 0) {
            } else {
                product_quantity -= 1
                Price =  productAmount.toInt() * product_quantity
                totalPrice =  Price
                tv_product_quantity.setText(product_quantity.toString())
                tvProductPrice.setText("₹"+totalPrice)
            }
        }

        iv_add.setOnClickListener {
            product_quantity += 1
            Price =  productAmount.toInt() * product_quantity
            totalPrice = Price
            Log.e("totalprice",totalPrice.toString())
            tv_product_quantity.setText(product_quantity.toString())
            tvProductPrice.setText("₹"+totalPrice)
        }

        proceed.setOnClickListener {
            if(etAddress.text.toString().isNullOrEmpty()) {
                etAddress.requestFocus()
                etAddress.error = "Invalid Address"
            }
            else if(product_quantity > productStock.toInt()) {
                toast("Quantity must not be greater than "+productStock)
            } else {
                showDialogOffer(
                    product_quantity.toString(),
                    totalPrice.toString(),
                    productName,
                    etAddress.text.toString()
                )
            }
        }
    }


    private fun showDialogOffer(
        quantity: String,
        totalPrice: String,
        productName: String,
        address: String
    ) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.order_products_layout)

        val tvProdName = dialog.findViewById(R.id.tvProdName) as TextView
        val tvQuantity = dialog.findViewById(R.id.tvQuantity) as TextView
        val tvProductAmount = dialog.findViewById(R.id.tvProductAmount) as TextView
        val tv_pay = dialog.findViewById(R.id.tvPay) as TextView
        val tv_no = dialog.findViewById(R.id.tvNo) as TextView

        tvQuantity.setText(quantity)
        tvProductAmount.setText("₹"+totalPrice)
        tvProdName.setText(productName)

        tv_pay.setOnClickListener {
            buyProduct(userModel.cus_id,productId,productName,quantity,totalPrice,address)
            dialog.dismiss()
        }
        tv_no.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun buyProduct(
        cus_id: String,
        product_id: String,
        pro_name: String,
        quantity: String,
        amount: String,
        address: String
    ) {
        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                this,
                BUYPRODUCT,
                this
            )
            mAPIcall.buyProduct(
                cus_id,
                product_id,
                pro_name,
                quantity,
                amount,
                address
            )
            progress_bar.visibility = View.VISIBLE
        } else {
            Toast.makeText(
                this,
                "Internet Error",
                Toast.LENGTH_SHORT
            )
        }
    }

    private fun getSingleProductsandServices(
        product_service_id: String
    ) {
        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                this,
                GET_SINGLE_PRODUCTS,
                this
            )
            mAPIcall.getSingleProductandService(product_service_id)
            progress_bar.visibility = View.VISIBLE
        } else {
            Toast.makeText(
                this,
                "Internet Error",
                Toast.LENGTH_SHORT
            )
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(BUYPRODUCT)) {
            Log.e("BUYPRODUCT", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val result = jsonObject.getString("message")
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {
                progress_bar.visibility = View.INVISIBLE
                toast(result)
            } else {
                progress_bar.visibility = View.INVISIBLE
                toast(result)
            }
        }

        if (flag.equals(GET_SINGLE_PRODUCTS)) {
            //progress_bar_balance.visibility = GONE
            Log.e("PRODUCTS", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val message = jsonObject.getString(AppConstants.MESSAGE)

            Log.e(AppConstants.STATUS, status)

            if (status.contains("true")) {

                progress_bar.visibility = View.INVISIBLE

                val cast = jsonObject.getJSONArray("result")

                

                rvProducts.adapter!!.notifyDataSetChanged()

            } else {

                progress_bar.visibility = View.INVISIBLE
            }


        }
    }
}