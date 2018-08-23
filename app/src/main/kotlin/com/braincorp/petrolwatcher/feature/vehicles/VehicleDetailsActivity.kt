package com.braincorp.petrolwatcher.feature.vehicles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import kotlinx.android.synthetic.main.activity_vehicle_details.*
import kotlinx.android.synthetic.main.content_vehicle_details.*

class VehicleDetailsActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val KEY_VEHICLE = "vehicle"
        private const val KEY_EDIT_MODE = "edit_mode"
        private const val KEY_YEAR = "year"
        private const val KEY_MANUFACTURER = "manufacturer"
        private const val KEY_MODEL = "model"
        private const val KEY_TRIM_LEVEL = "trim_level"
        private const val KEY_CAPACITY = "capacity"
        private const val KEY_AVG_CONSUMPTION_CITY = "avg_consumption_city"
        private const val KEY_AVG_CONSUMPTION_MOTORWAY = "avg_consumption_motorway"

        fun getIntent(context: Context, vehicle: Vehicle): Intent {
            return Intent(context, VehicleDetailsActivity::class.java)
                    .putExtra(KEY_VEHICLE, vehicle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_details)
        setupToolbar()
        fab.setOnClickListener(this)
        bt_calculate.setOnClickListener(this)
        savedInstanceState?.let {
            restoreInstanceState(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (outState == null) return

        with (outState) {
            // TODO: put data
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            restoreInstanceState(it)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab -> TODO("edit or save")
            R.id.bt_calculate -> TODO("calculate consumption")
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        // TODO: retrieve data
    }

}