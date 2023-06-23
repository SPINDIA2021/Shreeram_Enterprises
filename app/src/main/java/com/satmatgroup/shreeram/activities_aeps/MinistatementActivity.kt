package com.satmatgroup.shreeram.activities_aeps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.model.UserModel
import com.satmatgroup.shreeram.utils.AppPrefs
import kotlinx.android.synthetic.main.activity_commission_slab.custToolbar
import kotlinx.android.synthetic.main.activity_ministatement.*
import kotlinx.android.synthetic.main.activity_ministatement.rvMiniStatement
import kotlinx.android.synthetic.main.activity_ministatement.view.*

class MinistatementActivity : AppCompatActivity() {
    lateinit var ministatementAdapter: MinistatementAdapter
    var miniStatementModelArrayList = ArrayList<MiniStatementModel>()
    lateinit var userModel: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ministatement)

        //Toolbar
        custToolbar.ivBackBtn.setOnClickListener { onBackPressed() }

        val gson = Gson()
        val json = AppPrefs.getStringPref("userModel", this)
        userModel = gson.fromJson(json, UserModel::class.java)


        val bundle = intent.extras
        if (bundle != null) {
            miniStatementModelArrayList = bundle.getParcelableArrayList<MiniStatementModel>("miniStatementModelArrayList") as ArrayList<MiniStatementModel>
        }

        rvMiniStatement.apply {

            layoutManager = LinearLayoutManager(this@MinistatementActivity)
            ministatementAdapter = MinistatementAdapter(
                context, miniStatementModelArrayList
            )
            rvMiniStatement.adapter = ministatementAdapter
        }
    }
}