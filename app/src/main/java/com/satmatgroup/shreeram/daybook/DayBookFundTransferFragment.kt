package com.satmatgroup.shreeram.daybook

import android.graphics.Color
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserDayBookModel
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.network_calls.AppApiCalls
import com.satmatgroup.shreeram.utils.AppCommonMethods
import com.satmatgroup.shreeram.utils.AppConstants
import com.satmatgroup.shreeram.utils.AppPrefs
import kotlinx.android.synthetic.main.fragment_pie_chart_fund_transfer.*
import kotlinx.android.synthetic.main.fragment_pie_chart_fund_transfer.view.*
import kotlinx.android.synthetic.main.fragment_pie_chart_fund_transfer.view.progress_bar
import org.eazegraph.lib.models.PieModel
import org.json.JSONObject

class DayBookFundTransferFragment : Fragment(), AppApiCalls.OnAPICallCompleteListener {

    lateinit var root: View
    private val DAYBOOK: String = "DAYBOOK"
    lateinit var userModel: UserModel
    var commssionSlabModelArrayList: ArrayList<UserDayBookModel>? = null

    lateinit var userDayBook: UserDayBookModel


    companion object {
        fun newInstance(message: String): DayBookFundTransferFragment {

            val f = DayBookFundTransferFragment()

            val bdl = Bundle(1)

            bdl.putString(AlarmClock.EXTRA_MESSAGE, message)

            f.setArguments(bdl)

            return f

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_pie_chart_fund_transfer, container, false)

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", requireContext())
        userModel = gson.fromJson(json, UserModel::class.java)

        userDayBook(
            userModel.cus_id, AppPrefs.getStringPref("deviceId", requireContext()).toString(),
            AppPrefs.getStringPref("deviceName", requireContext()).toString(),
            userModel.cus_pin,
            userModel.cus_pass,
            userModel.cus_mobile, userModel.cus_type
        )
        return root

    }

    private fun userDayBook(
        cus_id: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {
        root.progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(requireContext()).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(requireContext(), DAYBOOK, this)
            mAPIcall.userDayBook(
                cus_id, deviceId, deviceName, pin,
                pass, cus_mobile, cus_type
            )
        } else {

            Toast.makeText(requireContext(), "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(
                DAYBOOK
            )
        ) {
            Log.e("DAYBOOK", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            //Log.e(AppConstants.MESSAGE_CODE, messageCode);
            if (status.contains("true")) {
                root.progress_bar.visibility = View.GONE

                val cast = jsonObject.getJSONArray("result")

                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val type = notifyObjJson.getString("type")
                    Log.e("type", type)
                    userDayBook = Gson()
                        .fromJson<UserDayBookModel>(
                            notifyObjJson.toString(),
                            UserDayBookModel::class.java
                        )

                    if (type.equals("FUND CREDITED")) {

                        root.tvFundCredited.text = "₹ " + userDayBook.bal
                        root.tvCountFundCredited.text = userDayBook.cnt

                    } else if (type.equals("FUND DEBITED")) {

                        root.tvFundDebited.text = "₹ " + userDayBook.bal
                        root.tvCountFundDebited.text = userDayBook.cnt


                    } else if (type.equals("OPENING BALANCE")) {
                        root.tvOpeningbalance.text = "₹ " + userDayBook.bal

                    } else if (type.equals("CLOSING BALANCE")) {
                        root.tvClosingBalance.text ="₹ " +  userDayBook.bal
                    }
                }
                setData()
            }
        }
    }


    private fun setData() {
        
        // Set the data and color to the pie chart
      /*  piechart.addPieSlice(
            PieModel(
                "Opening Balance", root.tvOpeningbalance.text.toString().toFloat(),
                Color.parseColor("#66BB6A")
            )
        )
        piechart.addPieSlice(
            PieModel(
                "Closing Balance", root.tvClosingBalance.text.toString().toFloat(),
                Color.parseColor("#FFA726")
            )
        )*/
        piechart.addPieSlice(
            PieModel(
                "Fund Credited", root.tvCountFundCredited.text.toString().toFloat(),
                Color.parseColor("#EF5350")
            )
        )
        piechart.addPieSlice(
            PieModel(
                "Fund Debited", root.tvCountFundDebited.text.toString().toFloat(),
                Color.parseColor("#C2185B")
            )
        )
        // To animate the pie chart
        piechart.startAnimation()
    }

}