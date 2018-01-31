package com.braincorp.petrolwatcher.activities

import android.os.Bundle
import com.braincorp.petrolwatcher.R
import kotlinx.android.synthetic.main.activity_vehicles.*

class VehiclesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicles)
        setSupportActionBar(toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

}