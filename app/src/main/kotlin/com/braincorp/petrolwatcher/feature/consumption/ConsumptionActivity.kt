package com.braincorp.petrolwatcher.feature.consumption

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.consumption.contract.ConsumptionActivityContract
import com.braincorp.petrolwatcher.feature.consumption.model.RoadType
import com.braincorp.petrolwatcher.feature.consumption.model.TankState
import com.braincorp.petrolwatcher.feature.consumption.presenter.ConsumptionActivityPresenter
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.braincorp.petrolwatcher.ui.GenericSpinnerAdapter
import kotlinx.android.synthetic.main.activity_consumption.*

/**
 * The activity where fuel consumption is calculated
 */
class ConsumptionActivity : AppCompatActivity(),
        View.OnClickListener,
        ConsumptionActivityContract.View {

    companion object {
        const val KEY_DATA = "data"

        private const val KEY_VEHICLE = "vehicle"

        fun intent(context: Context, vehicle: Vehicle): Intent {
            return Intent(context, ConsumptionActivity::class.java)
                    .putExtra(KEY_VEHICLE, vehicle)
        }
    }

    override lateinit var presenter: ConsumptionActivityContract.Presenter

    private lateinit var vehicle: Vehicle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumption)
        presenter = ConsumptionActivityPresenter(view = this)
        bt_finish.setOnClickListener(this)
        if (savedInstanceState == null)
            vehicle = intent.getParcelableExtra(KEY_VEHICLE)
        else
            restoreInstanceState(savedInstanceState)
        populateViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_VEHICLE, vehicle)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            restoreInstanceState(it)
        }
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED)
        super.onBackPressed()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.bt_finish)
            calculateConsumption()
    }

    /**
     * Shows an error in the odometer (start) field
     */
    override fun showOdometerStartError() {
        edt_odometer_start.error = getString(R.string.field_should_not_be_blank)
    }

    /**
     * Shows an error in the odometer (end) field
     */
    override fun showOdometerEndError() {
        edt_odometer_end.error = getString(R.string.field_should_not_be_blank)
    }

    /**
     * Shows a fuel capacity error
     */
    override fun showFuelCapacityError() {
        AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(R.string.fuel_capacity_missing)
                .setIcon(R.drawable.ic_error)
                .setNeutralButton(R.string.ok) { _, _ ->
                    setResult(RESULT_CANCELED)
                    finish()
                }.show()
    }

    /**
     * Shows an invalid distance error
     */
    override fun showInvalidDistanceError() {
        AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(R.string.invalid_distance)
                .setIcon(R.drawable.ic_error)
                .setNeutralButton(R.string.ok, null)
                .show()
    }

    /**
     * Show an invalid tank state error
     */
    override fun showInvalidTankStateError() {
        AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(R.string.invalid_tank_state)
                .setIcon(R.drawable.ic_error)
                .setNeutralButton(R.string.ok, null)
                .show()
    }

    /**
     * Exports the calculated average consumption
     *
     * @param consumption the calculated average consumption
     */
    override fun exportConsumption(consumption: Float) {
        val roadTypeIndex = spn_road_type.selectedItemPosition
        val roadType = RoadType.values()[roadTypeIndex]
        when (roadType) {
            RoadType.URBAN_ROAD -> vehicle.calculatedValues.avgConsumptionCity = consumption
            RoadType.MOTORWAY -> vehicle.calculatedValues.avgConsumptionMotorway = consumption
        }

        val data = Intent().putExtra(KEY_DATA, vehicle)
        setResult(RESULT_OK, data)
        finish()
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        vehicle = savedInstanceState.getParcelable(KEY_VEHICLE)
        populateViews()
    }

    private fun calculateConsumption() {
        val odometerStart = edt_odometer_start.text.toString()
        val odometerEnd = edt_odometer_end.text.toString()
        val tankStateStartIndex = spn_tank_state_start.selectedItemPosition
        val tankStateEndIndex = spn_tank_state_end.selectedItemPosition

        val tankStateStart = TankState.values()[tankStateStartIndex]
        val tankStateEnd = TankState.values()[tankStateEndIndex]

        presenter.calculateConsumption(odometerStart, odometerEnd,
                tankStateStart, tankStateEnd,
                vehicle.details.fuelCapacity)
    }

    private fun populateViews() {
        val vehicleName = "${vehicle.manufacturer} ${vehicle.model} ${vehicle.details.trimLevel}"
        txt_selected_vehicle.text = vehicleName

        val tankStates = resources.getStringArray(R.array.tank_states).toList()
        val tankStateAdapter = GenericSpinnerAdapter(this, tankStates)
        spn_tank_state_start.adapter = tankStateAdapter
        spn_tank_state_end.adapter = tankStateAdapter

        val roadTypes = resources.getStringArray(R.array.road_types).toList()
        val roadTypeAdapter = GenericSpinnerAdapter(this, roadTypes)
        spn_road_type.adapter = roadTypeAdapter
    }

}