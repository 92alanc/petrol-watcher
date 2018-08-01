package com.braincorp.petrolwatcher.feature.vehicles

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.vehicles.api.VehicleApi
import com.braincorp.petrolwatcher.feature.vehicles.contract.VehicleDetailsActivityContract
import com.braincorp.petrolwatcher.feature.vehicles.presenter.VehicleDetailsActivityPresenter
import com.braincorp.petrolwatcher.utils.GenericSpinnerAdapter
import com.braincorp.petrolwatcher.utils.dependencyInjection
import com.braincorp.petrolwatcher.utils.toRange
import kotlinx.android.synthetic.main.activity_vehicle_details.*
import kotlinx.android.synthetic.main.content_vehicle_details.*

/**
 * The activity where vehicle details are
 * shown and edited
 */
class VehicleDetailsActivity : AppCompatActivity(), VehicleDetailsActivityContract.View,
                               AdapterView.OnItemSelectedListener {

    companion object {
        private const val KEY_YEAR_RANGE = "year_range"
    }

    override lateinit var presenter: VehicleDetailsActivityContract.Presenter

    private var yearRange = IntRange.EMPTY
    private var year = 0
    private var manufacturers = listOf<String>()
    private var manufacturer = ""
    private var models = listOf<String>()
    private var model = ""
    private var trimLevels = listOf<String>()
    private var trimLevel = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_details)
        setupToolbar()
        val baseUrl = dependencyInjection().getVehiclesApiBaseUrl()
        presenter = VehicleDetailsActivityPresenter(VehicleApi.getApi(baseUrl), view = this)
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
        else
            presenter.getYearRange()
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

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putIntArray(KEY_YEAR_RANGE, yearRange.toList().toIntArray())
        // TODO
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
    }

    override fun onItemSelected(spinner: AdapterView<*>?, view: View?, position: Int, p3: Long) {
        when (spinner?.id) {
            R.id.spn_year -> {
                year = yearRange.toList()[position]
                presenter.getManufacturers(year)
            }

            R.id.spn_manufacturer -> {
                manufacturer = manufacturers[position]
                presenter.getModels(year, manufacturer)
            }

            R.id.spn_name -> {
                model = models[position]
                presenter.getDetails(year, manufacturer, model)
            }

            R.id.spn_trim_level -> {
                trimLevel = trimLevels[position]
            }
        }
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) { }

    /**
     * Sets the year range
     *
     * @param range the range
     */
    override fun setYearRange(range: IntRange) {
        this.yearRange = range
        spn_year.adapter = null
        val adapter = GenericSpinnerAdapter(this, range.toList())
        spn_year.adapter = adapter
        spn_year.onItemSelectedListener = this
    }

    /**
     * Sets the manufacturer list
     *
     * @param manufacturers the list
     */
    override fun setManufacturerList(manufacturers: List<String>) {
        this.manufacturers = manufacturers
        spn_manufacturer.adapter = null
        val adapter = GenericSpinnerAdapter(this, manufacturers)
        spn_manufacturer.adapter = adapter
        spn_manufacturer.onItemSelectedListener = this
    }

    /**
     * Sets the models list
     *
     * @param models the list
     */
    override fun setModelsList(models: List<String>) {
        this.models = models
        spn_name.adapter = null
        val adapter = GenericSpinnerAdapter(this, models)
        spn_name.adapter = adapter
        spn_name.onItemSelectedListener = this
    }

    /**
     * Sets the trim level list
     *
     * @param trimLevels the list
     */
    override fun setTrimLevelList(trimLevels: List<String>) {
        this.trimLevels = trimLevels
        spn_trim_level.adapter = null
        val adapter = GenericSpinnerAdapter(this, trimLevels)
        spn_trim_level.adapter = adapter
        spn_trim_level.onItemSelectedListener = this
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupAutoInput() {
        group_manual_input.visibility = GONE
        group_auto_input.visibility = VISIBLE
        if (yearRange != IntRange.EMPTY)
            presenter.getYearRange()
    }

    private fun setupManualInput() {
        group_auto_input.visibility = GONE
        group_manual_input.visibility = VISIBLE
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        yearRange = savedInstanceState.getIntArray(KEY_YEAR_RANGE).toRange()
        // TODO
    }

}