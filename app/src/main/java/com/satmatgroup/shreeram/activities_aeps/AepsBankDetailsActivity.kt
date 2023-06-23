package com.satmatgroup.shreeram.activities_aeps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.satmatgroup.shreeram.R
import kotlinx.android.synthetic.main.activity_aeps_bank_details.*
import kotlinx.android.synthetic.main.activity_aeps_bank_details.view.*

class AepsBankDetailsActivity : AppCompatActivity() {


    lateinit var merchant_name : String
    lateinit var merchant_mobile : String
    lateinit var merchant_email : String
    lateinit var company_legal_name : String
    lateinit var company_marketing_name : String
    lateinit var company_branch : String
    lateinit var merchant_address : String
    lateinit var district : String
    lateinit var city : String
    lateinit var state : String
    lateinit var pincode : String

    lateinit var latitude: String
    lateinit var longitude: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aeps_bank_details)


        custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }

        val bundle = intent.extras
        if (bundle != null) {
            merchant_name = bundle.getString("merchant_name").toString()
            merchant_mobile = bundle.getString("merchant_mobile").toString()
            merchant_email = bundle.getString("merchant_email").toString()
            company_legal_name = bundle.getString("company_legal_name").toString()
            company_marketing_name = bundle.getString("company_marketing_name").toString()
            company_branch = bundle.getString("company_branch").toString()
            merchant_address = bundle.getString("merchant_address").toString()
            district = bundle.getString("district").toString()
            city = bundle.getString("city").toString()
            state = bundle.getString("state").toString()
            pincode = bundle.getString("pincode").toString()

            latitude = bundle.getString("latitude").toString()
            longitude = bundle.getString("longitude").toString()
        }


        btnProceed.setOnClickListener {

            if (etBankName.text.toString().isNullOrEmpty()){
                etBankName.requestFocus()
                etBankName.setError("Invalid Bank Name")

            }else if (etBankAccountNumber.text.toString().isNullOrEmpty()){

                etBankAccountNumber.requestFocus()
                etBankAccountNumber.setError("Invalid Bank Account Number")
            }else if (etBankIfscCode.text.toString().isNullOrEmpty()){
                etBankIfscCode.requestFocus()
                etBankIfscCode.setError("Invalid IFSC")

            }else if (etBankBranchName.text.toString().isNullOrEmpty()){

                etBankBranchName.requestFocus()
                etBankBranchName.setError("Invalid Branch Name")
            }else if (etAccountHolderName.text.toString().isNullOrEmpty()){
                etAccountHolderName.requestFocus()
                etAccountHolderName.setError("Invalid Account Holder Name")

            }else{

                val bundle = Bundle()
                bundle.putString("merchant_name", merchant_name)
                bundle.putString("merchant_mobile", merchant_mobile)
                bundle.putString("merchant_email", merchant_email)
                bundle.putString("company_legal_name", company_legal_name)
                bundle.putString("company_marketing_name", company_marketing_name)
                bundle.putString("company_branch", company_branch)
                bundle.putString("merchant_address",merchant_address)
                bundle.putString("district", district)
                bundle.putString("city", city)
                bundle.putString("state", state)
                bundle.putString("pincode", pincode)
                bundle.putString("bank_name", etBankName.text.toString())
                bundle.putString("bank_accnt_number", etBankAccountNumber.text.toString())
                bundle.putString("bank_ifsc", etBankIfscCode.text.toString())
                bundle.putString("bank_branch_name", etBankBranchName.text.toString())
                bundle.putString("bank_accnt_holder_name", etAccountHolderName.text.toString())

                bundle.putString("latitude", latitude)
                bundle.putString("longitude", longitude)
                val intent = Intent(this,AepsKycDetailsActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)



            }
        }
    }
}