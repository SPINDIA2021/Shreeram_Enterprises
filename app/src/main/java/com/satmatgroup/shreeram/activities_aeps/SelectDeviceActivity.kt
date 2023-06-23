package com.satmatgroup.shreeram.activities_aeps

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.satmatgroup.shreeram.R
import com.satmatgroup.shreeram.activities_aeps.aepsfinger.MantraDeviceActivity
import kotlinx.android.synthetic.main.activity_select_device.*

class SelectDeviceActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var list_of_items = arrayOf("Mantra MFS100", "Morpho")

    lateinit var selected_device: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_device)

        spinner!!.setOnItemSelectedListener(this)

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.setAdapter(aa)




        btnProceed.setOnClickListener {
            if (selected_device.isNullOrEmpty()) {

                Toast.makeText(this, "Please Select A Device", Toast.LENGTH_SHORT).show()
            } else if (selected_device.equals("Mantra MFS100")) {
                /*       val intent = Intent(this, AepsTransactionActivity::class.java)
                       startActivity(intent)*/
                val intent = Intent(this, MantraDeviceActivity::class.java)
                startActivity(intent)
            } else if (selected_device.equals("Morpho")) {


            }
        }


    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selected_device = list_of_items[position]

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}