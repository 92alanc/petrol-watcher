package com.braincorp.petrolwatcher.feature.vehicles

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.braincorp.petrolwatcher.R
import kotlinx.android.synthetic.main.activity_vehicle_details.*
import kotlinx.android.synthetic.main.content_vehicle_details.*

/**
 * The activity where vehicle details are
 * shown and edited
 */
class VehicleDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_details)
        setupToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_vehicle_details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.isChecked = true

        when (item?.itemId) {
            R.id.item_auto_input -> setupAutoInput()
            R.id.item_manual_input -> setupManualInput()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupAutoInput() {
        group_manual_input.visibility = GONE
        group_auto_input.visibility = VISIBLE
    }

    private fun setupManualInput() {
        group_auto_input.visibility = GONE
        group_manual_input.visibility = VISIBLE
    }

}