package com.satmatgroup.shreeram.dmt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import org.json.JSONObject
import java.util.*

class RecipientsAdapter(
    context: Context?,
    rechargeHistoryModalList: ArrayList<RecipientModel>,
) :
    RecyclerView.Adapter<RecipientsAdapter.ViewHolder>() {
    private var rechargeHistoryModalList: List<RecipientModel>
    private val mInflater: LayoutInflater
    private var mContext: Context? = null
    var DELETE_RECIPIENT = "DELETE_RECIPIENT"

    var user_type: String = ""
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mContext = parent.context
        user_type = AppPrefs.getStringPref("user_type", mContext).toString()
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_recipients, parent, false)
        return ViewHolder(listItem, parent.context)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val rechargeHistoryModal: RecipientModel =
            rechargeHistoryModalList[position]

        holder.tvReciName.text = rechargeHistoryModal.benename
        holder.tvReciBankName.text = rechargeHistoryModal.banknameUnique
        holder.tvReciBankIfsc.text = rechargeHistoryModal.IFSC
        holder.tvReciAccNo.text = rechargeHistoryModal.beneaccno

        holder.btnSendMoney.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("recipientModel", rechargeHistoryModal)
            val intent = Intent(mContext, DmtTransferFundsActivity::class.java)
            intent.putExtras(bundle)
            mContext!!.startActivity(intent)
        }
        holder.btnDeleteRecipient.setOnClickListener {
            holder.deleteFromWishList(
                rechargeHistoryModal.BENEFICIARYID, DELETE_RECIPIENT, mContext!!
            )

        }


    }

    override fun getItemCount(): Int {
        return rechargeHistoryModalList.size
    }

    class ViewHolder(itemView: View, context: Context) :
        RecyclerView.ViewHolder(itemView),
        AppApiCalls.OnAPICallCompleteListener {
        // var ivStatus: ImageView

        var context = context
        var tvReciName: TextView
        var tvReciBankName: TextView
        var tvReciBankIfsc: TextView
        var tvReciAccNo: TextView
        var btnDeleteRecipient: Button
        var btnSendMoney: Button

        init {
            tvReciName = itemView.findViewById(R.id.tvReciName)
            tvReciBankName = itemView.findViewById(R.id.tvReciBankName)
            tvReciBankIfsc = itemView.findViewById(R.id.tvReciIfsc)
            tvReciAccNo = itemView.findViewById(R.id.tvReciAccNo)
            btnDeleteRecipient = itemView.findViewById(R.id.btnDeleteRecipient)
            btnSendMoney = itemView.findViewById(R.id.btnSendMoneyRecipient)
//            ivStatus = itemView.findViewById(R.id.ivStatus)
        }

        fun deleteFromWishList(
            bene_id: String, DELET_RECIPIENT: String, context: Context
        ) {

            if (AppCommonMethods(context).isNetworkAvailable) {
                val mAPIcall =
                    AppApiCalls(context, DELET_RECIPIENT, this)
                mAPIcall.deleteRecipient(bene_id)
            } else {

                Toast.makeText(context, "Internet Error", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
            if (flag.equals("DELETE_RECIPIENT")) {
                Log.e("DELETE_RECIPIENT", result)
                val jsonObject = JSONObject(result)
                val status = jsonObject.getString(AppConstants.STATUS)
                Log.e(AppConstants.STATUS, status)
                val message = jsonObject.getJSONObject("message")
                if (status.contains("true")) {
                        val status1 = message.getString("status")
                    if(status1.equals("TXN")) {
                        (context as DmtBenificiaryActivity).recipientList(
                            AppPrefs.getStringPref(
                                "dmtMobile",
                                context
                            ).toString()
                        )
                        Toast.makeText(context, ""+message.getString("api_msg"), Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(context, ""+message.getString("api_msg"), Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(context, "Oops! Something went Wrong", Toast.LENGTH_SHORT).show()

                }
            }

        }

    }

    fun filterList(filterdNames: ArrayList<RecipientModel>) {
        rechargeHistoryModalList = filterdNames
        notifyDataSetChanged()
    }

    companion object {
        const val imgUrl = " http://paykrs.in/assets/operator_img/"
    }

    // RecyclerView recyclerView;
    init {
        mInflater = LayoutInflater.from(context)
        this.rechargeHistoryModalList = rechargeHistoryModalList
    }
}