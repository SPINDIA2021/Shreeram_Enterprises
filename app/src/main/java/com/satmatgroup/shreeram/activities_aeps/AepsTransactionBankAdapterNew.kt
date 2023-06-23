package com.satmatgroup.shreeram.aeps_activities

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.AepsBankModel
import com.satmatgroup.shreeram.activities_aeps.aepshistory.AepsHistoryModel
import com.satmatgroup.shreeram.utils.AppPrefs
import java.util.*

class AepsTransactionBankAdapterNew(
    context: Context?,
    rechargeHistoryModalList: ArrayList<AepsBankModel>,
    bankClick: View.OnClickListener?
) :
    RecyclerView.Adapter<AepsTransactionBankAdapterNew.ViewHolder>() {
    private var rechargeHistoryModalList: List<AepsBankModel>
    private val mInflater: LayoutInflater
    private var mContext: Context? = null
    private var banlClick: View.OnClickListener? = null

    var user_type: String = ""
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mContext = parent.context
        user_type = AppPrefs.getStringPref("user_type", mContext).toString()
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.row_bank, parent, false)
        return ViewHolder(listItem)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val rechargeHistoryModal: AepsBankModel =
            rechargeHistoryModalList[position]
        holder.tvBankName.text = rechargeHistoryModal.bankName


        if (rechargeHistoryModal.bankName.equals("State Bank\nof India"))
        {
            holder.imgBank.setImageDrawable(mContext!!.resources.getDrawable(R.drawable.sbi))
        }else if (rechargeHistoryModal.bankName.equals("Punjab National\nBank"))
        {
            holder.imgBank.setImageDrawable(mContext!!.resources.getDrawable(R.drawable.pnb))
        }else if (rechargeHistoryModal.bankName.equals("Bank of Baroda\nPlus")){
            holder.imgBank.setImageDrawable(mContext!!.resources.getDrawable(R.drawable.baroda))
        }



        if (rechargeHistoryModal.selected)
        {
            holder.layBankback.setBackgroundResource(R.drawable.circle_withshadowblue)
            // holder.imgBank.setColorFilter(mContext!!.getColor(R.color.white))
        }else
        { holder.layBankback.setBackgroundResource(R.drawable.circle_withshadow)
            //   holder.imgBank.setColorFilter(mContext!!.getColor(R.color.black))

        }

        holder.layBank.setTag(position)
        holder.layBank.setOnClickListener(banlClick)

    }

    override fun getItemCount(): Int {
        return rechargeHistoryModalList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // var ivStatus: ImageView
        var tvBankName: TextView
        var layBank: LinearLayout
        var layBankback: RelativeLayout
        var  imgBank:ImageView


        init {
            tvBankName = itemView.findViewById(R.id.text_bank)
            layBank = itemView.findViewById(R.id.lay_main)
            layBankback = itemView.findViewById(R.id.lay_bankback)
            imgBank = itemView.findViewById(R.id.img_bank)

        }
    }




    // RecyclerView recyclerView;
    init {
        mInflater = LayoutInflater.from(context)
        this.rechargeHistoryModalList = rechargeHistoryModalList
        this.banlClick = bankClick
    }
}