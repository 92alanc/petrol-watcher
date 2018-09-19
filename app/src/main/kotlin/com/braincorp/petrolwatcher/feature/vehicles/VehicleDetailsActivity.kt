package com.braincorp.petrolwatcher.feature.vehicles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.vehicles.contract.VehicleDetailsActivityContract
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.braincorp.petrolwatcher.feature.vehicles.presenter.VehicleDetailsActivityPresenter
import com.braincorp.petrolwatcher.utils.startConsumptionActivity
import com.braincorp.petrolwatcher.utils.startVehicleListActivity
import kotlinx.android.synthetic.main.activity_vehicle_details.*
import kotlinx.android.synthetic.main.content_vehicle_details.*

/**
 * The activity where vehicle details are shown and edited
 */
class VehicleDetailsActivity : AppCompatActivity(), View.OnClickListener,
                               VehicleDetailsActivityContract.View {

    companion object {
        private const val KEY_VEHICLE = "vehicle"
        private const val KEY_EDIT_MODE = "edit_mode"

        fun getIntent(context: Context, vehicle: Vehicle): Intent {
            return Intent(context, VehicleDetailsActivity::class.java)
                    .putExtra(KEY_VEHICLE, vehicle)
        }
    }

    override lateinit var presenter: VehicleDetailsActivityContract.Presenter

    // region private fields
    private var editMode = false
    private lateinit var vehicle: Vehicle
    // endregion

    // region activity functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_details)
        setupToolbar()
        fab.setOnClickListener(this)
        bt_calculate.setOnClickListener(this)
        vehicle = intent.getParcelableExtra(KEY_VEHICLE) ?: Vehicle()
        presenter = VehicleDetailsActivityPresenter(view = this)
        fillReadOnlyFields()
        fillCalculatedValues()
        savedInstanceState?.let {
            restoreInstanceState(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_vehicle_details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.item_delete)
            delete()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (editMode)
            showReadOnlyFields()
        else
            super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (outState == null) return

        with (outState) {
            putParcelable(KEY_VEHICLE, vehicle)
            putBoolean(KEY_EDIT_MODE, editMode)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            restoreInstanceState(it)
        }
    }
    // endregion

    // region view functions
    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab -> handleFabClick()
            R.id.bt_calculate -> startConsumptionActivity(vehicle)
        }
    }
    // endregion

    // region contract functions
    /**
     * Shows the vehicle list
     */
    override fun showVehicleList() {
        startVehicleListActivity(finishCurrent = true)
    }

    /**
     * Shows an invalid vehicle dialogue
     */
    override fun showInvalidVehicleDialogue() {
        AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(R.string.invalid_vehicle_data)
                .setIcon(R.drawable.ic_error)
                .setNeutralButton(R.string.ok, null)
                .show()
    }
    // endregion

    // region private functions
    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        with (savedInstanceState) {
            if (containsKey(KEY_EDIT_MODE))
                editMode = getBoolean(KEY_EDIT_MODE)

            if (containsKey(KEY_VEHICLE))
                vehicle = getParcelable(KEY_VEHICLE)

            if (editMode) {
                showEditableFields()
                fillEditableFields()
            } else {
                showReadOnlyFields()
                fillReadOnlyFields()
            }
        }
    }

    private fun handleFabClick() {
        if (editMode) {
            updateVehicleData()
            presenter.saveVehicle(vehicle)
        } else {
            editMode = true
            fab.setImageResource(R.drawable.ic_save)
            showEditableFields()
            fillEditableFields()
        }
    }

    private fun updateVehicleData() {
        val year = edt_year.text.toString()
        val manufacturer = edt_manufacturer.text.toString()
        val model = edt_model.text.toString()
        val trimLevel = edt_trim_level.text.toString()
        val capacity = edt_capacity.text.toString()
        val avgConsumptionCity = edt_avg_consumption_city.text.toString()
        val avgConsumptionMotorway = edt_avg_consumption_motorway.text.toString()

        year.let {
            if (it.isNotBlank()) vehicle.year = it.toInt()
            else -1
        }

        vehicle.manufacturer = manufacturer
        vehicle.model = model
        vehicle.details.trimLevel = trimLevel

        capacity.let {
            if (it.isNotBlank()) vehicle.details.fuelCapacity = it.toInt()
            else -1
        }

        avgConsumptionCity.let {
            if (it.isNotBlank()) vehicle.details.avgConsumptionCity = it.toFloat()
            else -1f
        }

        avgConsumptionMotorway.let {
            if (it.isNotBlank()) vehicle.details.avgConsumptionMotorway = it.toFloat()
            else -1f
        }
    }

    private fun showEditableFields() {
        label_factory_data.visibility = GONE
        group_read_only_fields.visibility = GONE
        group_editable_fields.visibility = VISIBLE
    }

    private fun showReadOnlyFields() {
        group_editable_fields.visibility = GONE
        label_factory_data.visibility = VISIBLE
        group_read_only_fields.visibility = VISIBLE
    }

    private fun fillReadOnlyFields() {
        val name = "${vehicle.manufacturer} ${vehicle.model} ${vehicle.details.trimLevel}"
        txt_name.text = name
        txt_year.text = getString(R.string.year_format, vehicle.year)
        txt_fuel_capacity.text = getString(R.string.capacity_format,
                                           vehicle.details.fuelCapacity.toString())
        txt_avg_consumption_city.text = getString(R.string.avg_consumption_city_format,
                                                  vehicle.details.avgConsumptionCity.toString())
        txt_avg_consumption_motorway.text = getString(R.string.avg_consumption_motorway_format,
                                                  vehicle.details.avgConsumptionMotorway.toString())
    }

    private fun fillEditableFields() {
        edt_year.setText(vehicle.year.toString())
        edt_manufacturer.setText(vehicle.manufacturer)
        edt_model.setText(vehicle.model)
        edt_trim_level.setText(vehicle.details.trimLevel)
        edt_capacity.setText(vehicle.details.fuelCapacity.toString())
        edt_avg_consumption_city.setText(vehicle.details.avgConsumptionCity.toString())
        edt_avg_consumption_motorway.setText(vehicle.details.avgConsumptionMotorway.toString())
    }

    private fun fillCalculatedValues() {
        val avgConsumptionCity = if (vehicle.calculatedValues.avgConsumptionCity > 0f)
            vehicle.calculatedValues.avgConsumptionCity.toString()
        else
            "?"

        val avgConsumptionMotorway = if (vehicle.calculatedValues.avgConsumptionMotorway > 0f)
            vehicle.calculatedValues.avgConsumptionMotorway.toString()
        else
            "?"

        txt_calculated_avg_consumption_city.text = getString(R.string.avg_consumption_city_format,
                                                             avgConsumptionCity)
        txt_calculated_avg_consumption_motorway.text = getString(R.string.avg_consumption_motorway_format,
                                                                 avgConsumptionMotorway)
    }

    private fun delete() {
        AlertDialog.Builder(this)
                .setTitle(R.string.delete_vehicle)
                .setMessage(R.string.delete_vehicle_confirmation)
                .setIcon(R.drawable.ic_warning)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes) { _, _ ->
                    presenter.deleteVehicle(vehicle)
                }.show()
    }
    // endregion

}