package com.braincorp.petrolwatcher.feature.vehicles

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.vehicles.api.VehicleApi
import com.braincorp.petrolwatcher.feature.vehicles.contract.VehicleDetailsActivityContract
import com.braincorp.petrolwatcher.feature.vehicles.presenter.VehicleDetailsActivityPresenter
import com.braincorp.petrolwatcher.utils.GenericSpinnerAdapter
import kotlinx.android.synthetic.main.activity_vehicle_details.*
import kotlinx.android.synthetic.main.content_vehicle_details.*

/**
 * The activity where vehicle details are
 * shown and edited
 */
class VehicleDetailsActivity : AppCompatActivity(), VehicleDetailsActivityContract.View {

    override lateinit var presenter: VehicleDetailsActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_details)
        setupToolbar()
        presenter = VehicleDetailsActivityPresenter(VehicleApi.getApi(), view = this)
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

    /**
     * Sets the year range
     *
     * @param range the range
     */
    override fun setYearRange(range: IntRange) {
        spn_year.adapter = null
        val adapter = GenericSpinnerAdapter(this, range.toList())
        spn_year.adapter = adapter
    }

    /**
     * Sets the manufacturer list
     *
     * @param manufacturers the list
     */
    override fun setManufacturerList(manufacturers: List<String>) {
        spn_manufacturer.adapter = null
        val adapter = GenericSpinnerAdapter(this, manufacturers)
        spn_manufacturer.adapter = adapter
    }

    /**
     * Sets the models list
     *
     * @param models the list
     */
    override fun setModelsList(models: List<String>) {
        spn_name.adapter = null
        val adapter = GenericSpinnerAdapter(this, models)
        spn_name.adapter = adapter
    }

    /**
     * Sets the trim level list
     *
     * @param trimLevels the list
     */
    override fun setTrimLevelList(trimLevels: List<String>) {
        spn_trim_level.adapter = null
        val adapter = GenericSpinnerAdapter(this, trimLevels)
        spn_trim_level.adapter = adapter
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