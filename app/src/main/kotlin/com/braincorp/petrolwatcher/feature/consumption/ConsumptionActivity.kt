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
import com.braincorp.petrolwatcher.feature.consumption.model.TankLevel
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
        private const val KEY_DISPLAYED_INFO = "displayed_info"

        fun intent(context: Context, vehicle: Vehicle): Intent {
            return Intent(context, ConsumptionActivity::class.java)
                    .putExtra(KEY_VEHICLE, vehicle)
        }
    }

    override lateinit var presenter: ConsumptionActivityContract.Presenter

    private lateinit var vehicle: Vehicle

    private var displayedInfo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumption)
        presenter = ConsumptionActivityPresenter(view = this)
        bt_finish.setOnClickListener(this)
        if (savedInstanceState == null)
            vehicle = intent.getParcelableExtra(KEY_VEHICLE)
        else
            restoreInstanceState(savedInstanceState)

        if (!displayedInfo)
            displayInfo()
        populateViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putParcelable(KEY_VEHICLE, vehicle)
            putBoolean(KEY_DISPLAYED_INFO, displayedInfo)
        }
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
     * Show an invalid tank level error
     */
    override fun showInvalidTankLevelError() {
        AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(R.string.invalid_tank_level)
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
        with(savedInstanceState) {
            vehicle = getParcelable(KEY_VEHICLE)!!
            displayedInfo = getBoolean(KEY_DISPLAYED_INFO)
        }
        populateViews()
    }

    private fun displayInfo() {
        AlertDialog.Builder(this)
                .setTitle(R.string.info)
                .setMessage(R.string.info_consumption)
                .setIcon(R.drawable.ic_info)
                .setNeutralButton(R.string.ok, null)
                .show()
        displayedInfo = true
    }

    private fun calculateConsumption() {
        val odometerStart = edt_odometer_start.text.toString()
        val odometerEnd = edt_odometer_end.text.toString()
        val tankLevelStartIndex = spn_tank_level_start.selectedItemPosition
        val tankLevelEndIndex = spn_tank_level_end.selectedItemPosition

        val tankLevelStart = TankLevel.values()[tankLevelStartIndex]
        val tankLevelEnd = TankLevel.values()[tankLevelEndIndex]

        presenter.calculateConsumption(odometerStart, odometerEnd,
                tankLevelStart, tankLevelEnd,
                vehicle.details.fuelCapacity)
    }

    private fun populateViews() {
        val vehicleName = "${vehicle.manufacturer} ${vehicle.model} ${vehicle.details.trimLevel}"
        txt_selected_vehicle.text = vehicleName

        val tankLevels = resources.getStringArray(R.array.tank_levels).toList()
        val tankLevelAdapter = GenericSpinnerAdapter(this, tankLevels)
        spn_tank_level_start.adapter = tankLevelAdapter
        spn_tank_level_end.adapter = tankLevelAdapter

        val roadTypes = resources.getStringArray(R.array.road_types).toList()
        val roadTypeAdapter = GenericSpinnerAdapter(this, roadTypes)
        spn_road_type.adapter = roadTypeAdapter
    }

}