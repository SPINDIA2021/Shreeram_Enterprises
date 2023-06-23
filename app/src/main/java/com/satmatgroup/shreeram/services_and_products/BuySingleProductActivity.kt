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
import com.satmatgroup.shreeram.adapters_recyclerview.SliderAdapter
import com.satmatgroup.shreeram.model.BannerModel
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import com.satmatgroup.shreeram.utils.toast
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.activity_buy_or_book.*
import kotlinx.android.synthetic.main.activity_buy_or_book.progress_bar
import kotlinx.android.synthetic.main.activity_buy_products.*
import kotlinx.android.synthetic.main.activity_buy_single_product.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class BuySingleProductActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener {

    lateinit var userModel: UserModel
    lateinit var sliderBannerModel: SliderBannerModel
    lateinit var product_service_id: String
    var sliderBannerModelArrayList: ArrayList<SliderBannerModel>? = null
    lateinit var offer_name: String
    lateinit var regular_price: String
    lateinit var sale_price: String
    lateinit var off: String
    lateinit var banner: String
    lateinit var sliders: String
    lateinit var video: String
    lateinit var description: String
    lateinit var added_date: String

    var Price :Int = 0
    var totalPrice :Int = 0
    var product_quantity = 0


    var GET_SINGLE_PRODUCTS: String = "GET_SINGLE_PRODUCTS"
    var BUY_PRODUCT_SERVICE: String = "BUY_PRODUCT_SERVICE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_single_product)



        val gson = Gson()
        val json = AppPrefs.getStringPref(AppConstants.USER_MODEL, this)
        userModel = gson.fromJson(json, UserModel::class.java)

        val bundle: Bundle? = intent.extras
        product_service_id = bundle!!.getString("product_service_id").toString()

        getSingleProductsandServices(product_service_id)

        product_quantity = tv_product_quantity_single.text.toString().toInt()

        ivRemove.setOnClickListener {
            if (product_quantity == 0) {
            } else {
                product_quantity -= 1
                Price =  sale_price.toInt() * product_quantity
                totalPrice =  Price
                tv_product_quantity_single.setText(product_quantity.toString())
                tvSingleProductPrice.setText("₹"+totalPrice)
            }
        }

        ivAdd.setOnClickListener {
            product_quantity += 1
            Price =  sale_price.toInt() * product_quantity
            totalPrice = Price
            Log.e("totalprice",totalPrice.toString())
            tv_product_quantity_single.setText(product_quantity.toString())
            tvSingleProductPrice.setText("₹"+totalPrice)
        }

        btnPay.setOnClickListener {
            if(etAddressProduct.text.toString().isNullOrEmpty()) {
                etAddressProduct.requestFocus()
                etAddressProduct.error = "Invalid Address"
            }
            else {
                showDialogOffer(
                    product_quantity.toString(),
                    totalPrice.toString(),
                    offer_name,
                    etAddressProduct.text.toString()
                )
            }
        }


       /* val adapter = SliderAdapter( bannerModelArrayList!!,this)
        sliderViewProduct.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_RTL)
        sliderViewProduct.setSliderAdapter(adapter)
        sliderViewProduct.setScrollTimeInSec(3)

        sliderViewProduct.setAutoCycle(true)
        sliderViewProduct.startAutoCycle()*/
    }

    private fun showDialogOffer(
        quantity: String,
        totalPrice: String,
        offer_name: String,
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
        tvProdName.setText(offer_name)

        tv_pay.setOnClickListener {
            buyProductandService(userModel.cus_id,product_service_id,offer_name,quantity,totalPrice,address)
            dialog.dismiss()
        }
        tv_no.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun buyProductandService(
        cus_id: String,
        product_service_id: String,
        offer_name: String,
        quantity: String,
        sale_price: String,
        address: String
    ) {
        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                this,
                BUY_PRODUCT_SERVICE,
                this
            )
            mAPIcall.buyProductandService(
                cus_id,
                product_service_id,
                offer_name,
                quantity,
                sale_price,
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
        if (flag.equals(GET_SINGLE_PRODUCTS)) {
            sliderBannerModelArrayList = ArrayList()
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
                    offer_name = notifyObjJson.getString("offer_name")
                    regular_price = notifyObjJson.getString("regular_price")
                    sale_price = notifyObjJson.getString("sale_price")
                    off = notifyObjJson.getString("off")
                    banner = notifyObjJson.getString("banner")
                    sliders = notifyObjJson.getString("sliders")
                    video = notifyObjJson.getString("video")
                    description = notifyObjJson.getString("description")
                    added_date = notifyObjJson.getString("added_date")
                }

                tv_productNameSingle.setText(offer_name)

                videoView.setVideoPath(video)
                videoView.start()

                val imgs: ArrayList<String> = sliders.split(",") as ArrayList<String>
                /*imgs.forEach {
                    Log.e("Images", imgs)
                }*/

                for(i in 0 until imgs.size) {
                    sliderBannerModelArrayList!!.add(SliderBannerModel(imgs[i]))
                }
/*                for (e in imgs) {
                   //Log.e("Images",e)
                    sliderBannerModelArrayList!!.add(SliderBannerModel(e))
                    //sliderBannerModelArrayList!!.add(imgs)
                }*/

                val adapter = SliderBannerAdapter( sliderBannerModelArrayList!!,this)
                sliderViewProduct.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_RTL)
                sliderViewProduct.setSliderAdapter(adapter)
                sliderViewProduct.setScrollTimeInSec(3)

                sliderViewProduct.setAutoCycle(true)
                sliderViewProduct.startAutoCycle()

                tvSingleProductPrice.setText(sale_price)
                dateSingle.setText(added_date)

                Glide.with(this)
                    .load(banner)
                    .into(ivProductImgSingle)

                //rvProducts.adapter!!.notifyDataSetChanged()

            } else {

                progress_bar.visibility = View.INVISIBLE
            }


        }
        if (flag.equals(BUY_PRODUCT_SERVICE)) {
            Log.e("BUY_PRODUCT_SERVICE", result)
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
    }
}