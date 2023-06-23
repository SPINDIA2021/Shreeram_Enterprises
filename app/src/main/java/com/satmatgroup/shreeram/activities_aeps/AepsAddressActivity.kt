package com.satmatgroup.shreeram.activities_aeps

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import kotlinx.android.synthetic.main.activity_aeps_address.*
import kotlinx.android.synthetic.main.activity_aeps_address.custToolbar
import kotlinx.android.synthetic.main.activity_aeps_address.view.*
import kotlinx.android.synthetic.main.layout_list_bottomsheet.view.*
import org.json.JSONObject
import java.util.ArrayList

class AepsAddressActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener,
    StateListAdapter.ListAdapterListener {

    lateinit var merchant_name: String
    lateinit var merchant_mobile: String
    lateinit var merchant_email: String
    lateinit var company_legal_name: String
    lateinit var company_marketing_name: String
    lateinit var company_branch: String

    lateinit var latitude: String
    lateinit var longitude: String
    lateinit var stateId: String

    lateinit var stateListAdapter: StateListAdapter
    var stateListModelArrayList = ArrayList<StateListModel>()
    lateinit var stateListModelList: List<StateListModel>

    private val STATE: String = "STATE"

    //lateinit var stateListAdapter: StateListAdapter
    //var stateListModelArrayList = ArrayList<StateListModel>()
    //lateinit var stateListModelList: List<StateListModel>

    var bottomSheetDialog: BottomSheetDialog? = null
    var bottomSheetDialogOffers: BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aeps_address)

        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }

        state()

        tvSelectState.setOnClickListener {
            ShowBottomSheet()
        }

        val bundle = intent.extras
        if (bundle != null) {
            merchant_name = bundle.getString("merchant_name").toString()
            merchant_mobile = bundle.getString("merchant_mobile").toString()
            merchant_email = bundle.getString("merchant_email").toString()
            company_legal_name = bundle.getString("company_legal_name").toString()
            company_marketing_name = bundle.getString("company_marketing_name").toString()
            company_branch = bundle.getString("company_branch").toString()

            latitude = bundle.getString("latitude").toString()
            longitude = bundle.getString("longitude").toString()

        }


        btnAddAddress.setOnClickListener {


            if (etMerchantAddress.text.toString().isNullOrEmpty()) {
                etMerchantAddress.requestFocus()
                etMerchantAddress.setError("Invalid Merchant Address")
            } else if (etDistrict.text.toString().isNullOrEmpty()) {

                etDistrict.requestFocus()
                etDistrict.setError("Invalid District")
            } else if (etCity.text.toString().isNullOrEmpty()) {
                etCity.requestFocus()
                etCity.setError("Invalid City")

            } else if (tvSelectState.text.toString().isNullOrEmpty()) {

                tvSelectState.requestFocus()
                tvSelectState.setError("Invalid State")
            } else if (etPinCode.text.toString().isNullOrEmpty()) {
                etPinCode.requestFocus()
                etPinCode.setError("Invalid Pin Code")
            } else {

                val bundle = Bundle()
                bundle.putString("merchant_name", merchant_name)
                bundle.putString("merchant_mobile", merchant_mobile)
                bundle.putString("merchant_email", merchant_email)
                bundle.putString("company_legal_name", company_legal_name)
                bundle.putString("company_marketing_name", company_marketing_name)
                bundle.putString("company_branch", company_branch)
                bundle.putString("merchant_address", etMerchantAddress.text.toString())
                bundle.putString("district", etDistrict.text.toString())
                bundle.putString("city", etCity.text.toString())
                bundle.putString("state", tvSelectState.text.toString())
                bundle.putString("pincode", etPinCode.text.toString())

                bundle.putString("latitude", latitude)
                bundle.putString("longitude", longitude)
                val intent = Intent(this, AepsBankDetailsActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)


            }
        }
    }

    private fun state() {
        //progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, STATE, this)
            mAPIcall.stateList()
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun ShowBottomSheet() {
        val view: View = layoutInflater.inflate(R.layout.layout_list_bottomsheet, null)
        view.rvspinner.apply {

            layoutManager = LinearLayoutManager(this@AepsAddressActivity)
            stateListAdapter = StateListAdapter(
                context, stateListModelArrayList, this@AepsAddressActivity
            )
            view.rvspinner.adapter = stateListAdapter
        }

        bottomSheetDialog = BottomSheetDialog(this, R.style.SheetDialog)
        bottomSheetDialog!!.setContentView(view)
        val bottomSheetBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.peekHeight = 600
        bottomSheetDialog!!.show()
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(STATE)) {
            stateListModelArrayList.clear()
            Log.e("AEPS_BANKS", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {

                //progress_bar_state.visibility = View.INVISIBLE

                val cast = jsonObject.getJSONArray("result")
                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val stateModel = Gson()
                        .fromJson(
                            notifyObjJson.toString(),
                            StateListModel::class.java
                        )
                    stateListModelArrayList.add(stateModel)
                }
                ShowBottomSheet()
            } else {
                //progress_bar_state.visibility = View.INVISIBLE
            }
        }
    }

    override fun onClickAtOKButton(StateListModel: StateListModel?) {
        if (StateListModel != null) {
            tvSelectState.setText(StateListModel.state)
            stateId = StateListModel.stateId.toString()
            bottomSheetDialog!!.dismiss()
        }
    }
}