package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.braincorp.petrolwatcher.R
import kotlinx.android.synthetic.main.activity_vehicles.*

class VehiclesActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, VehiclesActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicles)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}