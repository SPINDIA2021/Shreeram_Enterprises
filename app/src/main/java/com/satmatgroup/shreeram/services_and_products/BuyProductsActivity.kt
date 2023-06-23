package com.satmatgroup.shreeram.services_and_products

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.dmt.SelectDmtAdapter
import com.satmatgroup.shreeram.dmt.SelectDmtModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.toast
import kotlinx.android.synthetic.main.activity_add_beneficiary.*
import kotlinx.android.synthetic.main.activity_buy_products.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progress_bar
import kotlinx.android.synthetic.main.activity_select_dmt.*
import org.json.JSONObject

class BuyProductsActivity : AppCompatActivity(), ProductsandServicesAdapter.ListAdapterListener,
    AppApiCalls.OnAPICallCompleteListener{

    val PRODUCTS: String = "PRODUCTS"
    val PRODUCTS_AND_SERVICES: String = "PRODUCTS_AND_SERVICES"
    lateinit var productAdapter: ProductsandServicesAdapter
    var productModelArrayList = ArrayList<ProductsandServicesModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_products)

        //getProduct()
        getProductandServices()

        rvProducts.apply {
            layoutManager = LinearLayoutManager(this@BuyProductsActivity)
            productAdapter = ProductsandServicesAdapter(context, productModelArrayList, this@BuyProductsActivity)
            rvProducts.adapter = productAdapter
        }

    }

    private fun getProduct(
    ) {
        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                this,
                PRODUCTS,
                this
            )
            mAPIcall.getProduct()
            progress_bar.visibility = View.VISIBLE
        } else {
            Toast.makeText(
                this,
                "Internet Error",
                Toast.LENGTH_SHORT
            )
        }
    }

    private fun getProductandServices(
    ) {
        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                this,
                PRODUCTS_AND_SERVICES,
                this
            )
            mAPIcall.getProductsandServices()
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
        if (flag.equals(PRODUCTS)) {
            //progress_bar_balance.visibility = GONE
            Log.e("PRODUCTS", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val message = jsonObject.getString(AppConstants.MESSAGE)

            Log.e(AppConstants.STATUS, status)

            if (status.contains("true")) {

                progress_bar.visibility = View.INVISIBLE

                val cast = jsonObject.getJSONArray("result")

                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    Log.e("Product Name",notifyObjJson.getString("pro_name"))
                    val productsModel = Gson()
                        .fromJson(
                            notifyObjJson.toString(),
                            ProductsModel::class.java
                        )
                    //productModelArrayList.add(productsModel)
                }

                rvProducts.adapter!!.notifyDataSetChanged()

            } else {

                progress_bar.visibility = View.INVISIBLE
            }


        }
        if (flag.equals(PRODUCTS_AND_SERVICES)) {
            //progress_bar_balance.visibility = GONE
            Log.e("PRODUCTS", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val message = jsonObject.getString(AppConstants.MESSAGE)

            Log.e(AppConstants.STATUS, status)

            if (status.contains("true")) {

                progress_bar.visibility = View.INVISIBLE

                val cast = jsonObject.getJSONArray("result")

                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    //Log.e("Product Name",notifyObjJson.getString("pro_name"))
                    val productsandServicesModel = Gson()
                        .fromJson(
                            notifyObjJson.toString(),
                            ProductsandServicesModel::class.java
                        )
                    productModelArrayList.add(productsandServicesModel)
                }

                rvProducts.adapter!!.notifyDataSetChanged()

            } else {

                progress_bar.visibility = View.INVISIBLE
            }


        }

    }


    override fun onClickAtOKButton(productsModel: ProductsandServicesModel?) {

    }
}