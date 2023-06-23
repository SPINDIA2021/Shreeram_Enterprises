package com.satmatgroup.shreeram.payout

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs

import org.json.JSONObject
import java.util.*

class AepsPayoutHistoryAdapter(
    context: Context?,
    rechargeHistoryModalList: ArrayList<PayoutHistoryModel>
) :
    RecyclerView.Adapter<AepsPayoutHistoryAdapter.ViewHolder>() {
    private var rechargeHistoryModalList: List<PayoutHistoryModel>
    private val mInflater: LayoutInflater
    private var mContext: Context? = null
    val CHECK_STATUS = "CHECK_STATUS"
    var context = context

    var user_type: String = ""
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mContext = parent.context
        user_type = AppPrefs.getStringPref("user_type", mContext).toString()
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_aepspayout_history, parent, false)
        return ViewHolder(listItem, mContext!!)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val rechargeHistoryModal: PayoutHistoryModel =
            rechargeHistoryModalList[position]
        holder.tvTxnId.text = rechargeHistoryModal.pay_req_id
        holder.tvDate.text = rechargeHistoryModal.request_date
        holder.tvAccountHolderName.text =
            "Name : " + rechargeHistoryModal.accountHolderName.toUpperCase()
        holder.tvBankAccountNo.text = "Account No :"+rechargeHistoryModal.bankAccount
        holder.tvBankIfsc.text = "IFSC :"+rechargeHistoryModal.bankIFSC
        holder.tvBankName.text ="Bank Name :"+ rechargeHistoryModal.bankName
        holder.tvCharge.text ="Charge : ₹"+ rechargeHistoryModal.charge
      //  holder.btnCheckStatus.visibility = GONE

        if (rechargeHistoryModal.status.equals("SUCCESS")) {
            holder.tvStatus.text = rechargeHistoryModal.status.toUpperCase()
            holder.tvStatus.setTextColor(mContext!!.resources.getColor(R.color.material_green_700))
      //      holder.btnCheckStatus.visibility = GONE

        } else if (rechargeHistoryModal.status.equals("PENDING")) {

            holder.tvStatus.text = rechargeHistoryModal.status.toUpperCase()
            holder.tvStatus.setTextColor(mContext!!.resources.getColor(R.color.amber))
          //  holder.btnCheckStatus.visibility = VISIBLE

        } else {
            holder.tvStatus.text = rechargeHistoryModal.status.toUpperCase()
            holder.tvStatus.setTextColor(mContext!!.resources.getColor(R.color.material_red_500))
//
        }
        holder.tvAmount.text = "₹ " + rechargeHistoryModal.amount

 /*       holder.btnCheckStatus.setOnClickListener {


           // holder.checkStatus(rechargeHistoryModal.transaction_ref_id,CHECK_STATUS,mContext!!)
        }
*/

    }

    override fun getItemCount(): Int {
        return rechargeHistoryModalList.size
    }

    class ViewHolder(itemView: View,context: Context) : RecyclerView.ViewHolder(itemView),
        AppApiCalls.OnAPICallCompleteListener {
        // var ivStatus: ImageView
        var context = context

        var tvTxnId: TextView
        var tvDate: TextView
        var tvAmount: TextView
        var tvStatus: TextView
        var tvAccountHolderName: TextView
        var tvBankName: TextView
        var tvBankAccountNo: TextView
        var tvBankIfsc: TextView
        var tvCharge: TextView
        //var btnCheckStatus: Button

        init {
            tvTxnId = itemView.findViewById(R.id.tvTxnId)
            tvDate = itemView.findViewById(R.id.tvDate)
            tvAmount = itemView.findViewById(R.id.tvRecAmnt)
            tvStatus = itemView.findViewById(R.id.tvStatus)
            tvAccountHolderName = itemView.findViewById(R.id.tvAccounHolderName)
            tvBankName = itemView.findViewById(R.id.tvBankName)
            tvBankAccountNo = itemView.findViewById(R.id.tvBankAccountNo)
            tvBankIfsc = itemView.findViewById(R.id.tvBankIfsc)
            tvCharge = itemView.findViewById(R.id.tvCharge)
            //btnCheckStatus = itemView.findViewById(R.id.btnCheckStatus)
        }


/*        fun checkStatus(
            dmt_user_id: String, CHECK_STATUS: String, context: Context
        ) {

            if (AppCommonMethods(context).isNetworkAvailable) {
                val mAPIcall =
                    AppApiCalls(context, CHECK_STATUS, this)
                mAPIcall.checkAepsPayoutStatus(dmt_user_id)
            } else {

                Toast.makeText(context, "Internet Error", Toast.LENGTH_SHORT).show()
            }
        }*/

        override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
            if (flag.equals("CHECK_STATUS")) {
                Log.e("CHECK_STATUS", result)
                val jsonObject = JSONObject(result)
                val status = jsonObject.getString(AppConstants.STATUS)
                Log.e(AppConstants.STATUS, status)
                if (status.contains("true")) {
                    Toast.makeText(context, jsonObject.getString("result"), Toast.LENGTH_SHORT)
                        .show()


                    (context as PayoutHistoryActivity).aepsPayoutHistory(
                        AppPrefs.getStringPref(
                            "user_id",
                            context
                        ).toString()
                    )

                } else {
                    Toast.makeText(context, jsonObject.getString("result"), Toast.LENGTH_SHORT)
                        .show()

                }
            }

        }

    }

    fun filterList(filterdNames: ArrayList<PayoutHistoryModel>) {
        rechargeHistoryModalList = filterdNames
        notifyDataSetChanged()
    }

    companion object {
        const val imgUrl = " http://omkarpay.in/assets/operator_img/"
    }

    // RecyclerView recyclerView;
    init {
        mInflater = LayoutInflater.from(context)
        this.rechargeHistoryModalList = rechargeHistoryModalList
    }
}