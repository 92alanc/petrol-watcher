package com.braincorp.petrolwatcher.feature.costplanning

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.consumption.model.RoadType
import com.braincorp.petrolwatcher.feature.consumption.model.TankLevel
import com.braincorp.petrolwatcher.feature.costplanning.contract.CostPlanningActivityContract
import com.braincorp.petrolwatcher.feature.costplanning.presenter.CostPlanningActivityPresenter
import com.braincorp.petrolwatcher.feature.stations.model.Fuel
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.braincorp.petrolwatcher.ui.GenericSpinnerAdapter
import com.braincorp.petrolwatcher.utils.formatPriceAsCurrency
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_cost_planning.*
import kotlinx.android.synthetic.main.content_cost_planning.*
import java.math.BigDecimal
import java.util.*

/**
 * The activity for cost planning
 */
class CostPlanningActivity : AppCompatActivity(),
        CostPlanningActivityContract.View,
        View.OnClickListener {

    private companion object {
        const val KEY_ORIGIN = "origin"
        const val KEY_DESTINATION = "destination"
        const val KEY_SELECTED_FUEL_TYPE = "selected_fuel_type"
        const val KEY_SELECTED_FUEL_QUALITY = "selected_fuel_quality"
        const val KEY_SELECTED_ROAD_TYPE = "selected_road_type"
        const val KEY_SELECTED_VEHICLE = "selected_vehicle"
        const val KEY_SELECTED_TANK_LEVEL = "selected_tank_level"
        const val KEY_VEHICLES = "vehicles"
        const val KEY_COST = "cost"
        const val KEY_FUEL_AMOUNT = "fuel_amount"
        const val KEY_LOCALE = "locale"
        const val TAG = "PETROL_WATCHER"
    }

    // region Properties and fields
    override lateinit var presenter: CostPlanningActivityContract.Presenter

    private lateinit var placeAutocompleteOrigin: PlaceAutocompleteFragment
    private lateinit var placeAutocompleteDestination: PlaceAutocompleteFragment

    private var origin: Place? = null
    private var destination: Place? = null
    private var originAddress: String? = null
    private var destinationAddress: String? = null
    private var selectedFuelType: Fuel.Type? = null
    private var selectedFuelQuality: Fuel.Quality? = null
    private var selectedRoadType: RoadType? = null
    private var selectedVehicle: Vehicle? = null
    private var selectedTankLevel: TankLevel? = null
    private var cost: BigDecimal? = null
    private var fuelAmount: Int = 0
    private var vehicles = ArrayList<Vehicle>()
    private var locale = Locale.getDefault()
    // endregion

    // region Activity functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cost_planning)
        presenter = CostPlanningActivityPresenter(view = this)
        bindPlaceAutocompleteFragments()
        if (savedInstanceState != null) restoreInstanceState(savedInstanceState)
        else presenter.fetchVehicles()
        populateSpinners()
        fab.setOnClickListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let {
            it.putString(KEY_ORIGIN, origin?.address?.toString())
            it.putString(KEY_DESTINATION, destination?.address?.toString())
            it.putParcelable(KEY_SELECTED_VEHICLE, selectedVehicle)
            it.putSerializable(KEY_SELECTED_TANK_LEVEL, selectedTankLevel)
            it.putSerializable(KEY_SELECTED_FUEL_TYPE, selectedFuelType)
            it.putSerializable(KEY_SELECTED_FUEL_QUALITY, selectedFuelQuality)
            it.putSerializable(KEY_SELECTED_ROAD_TYPE, selectedRoadType)
            it.putParcelableArrayList(KEY_VEHICLES, vehicles)
            it.putSerializable(KEY_COST, cost)
            it.putInt(KEY_FUEL_AMOUNT, fuelAmount)
            it.putString(KEY_LOCALE, locale.toLanguageTag())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let(::restoreInstanceState)
    }
    // endregion

    // region View functions
    /**
     * Updates the vehicle spinner
     *
     * @param vehicles the vehicles
     */
    override fun updateVehicles(vehicles: ArrayList<Vehicle>) {
        this.vehicles = vehicles
        populateVehicleSpinner()
    }

    /**
     * Updates the estimated cost and fuel amount
     *
     * @param cost the estimated cost
     * @param fuelAmount the estimated fuel amount, in litres
     */
    override fun updateEstimatedCostAndFuelAmount(cost: BigDecimal, fuelAmount: Int) {
        this.cost = cost
        this.fuelAmount = fuelAmount
        showResult()
    }
    // endregion

    // region Listeners
    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab -> getResult()
        }
    }
    // endregion

    // region Private functions
    @Suppress("DEPRECATION")
    // This warning has been suppressed because casting a support fragment
    // to a PlaceAutoCompleteFragment doesn't work
    private fun bindPlaceAutocompleteFragments() {
        placeAutocompleteOrigin = fragmentManager.findFragmentById(R.id.edt_origin)
                as PlaceAutocompleteFragment
        with(placeAutocompleteOrigin) {
            setHint(getString(R.string.origin))
            setOnPlaceSelectedListener(object: PlaceSelectionListener {
                override fun onPlaceSelected(place: Place?) {
                    origin = place
                    originAddress = origin?.address?.toString()
                }

                override fun onError(error: Status?) {
                    Log.e(TAG, error?.statusMessage)
                }
            })
        }

        placeAutocompleteDestination = fragmentManager.findFragmentById(R.id.edt_destination)
                as PlaceAutocompleteFragment
        with(placeAutocompleteDestination) {
            setHint(getString(R.string.destination))
            setOnPlaceSelectedListener(object: PlaceSelectionListener {
                override fun onPlaceSelected(place: Place?) {
                    destination = place
                    destinationAddress = destination?.address?.toString()
                }

                override fun onError(error: Status?) {
                    Log.e(TAG, error?.statusMessage)
                }
            })
        }
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        with(savedInstanceState) {
            originAddress = getString(KEY_ORIGIN)
            destinationAddress = getString(KEY_DESTINATION)
            selectedVehicle = getParcelable(KEY_SELECTED_VEHICLE)
            getSerializable(KEY_SELECTED_TANK_LEVEL)?.let {
                selectedTankLevel = it as TankLevel
            }
            getSerializable(KEY_SELECTED_FUEL_TYPE)?.let {
                selectedFuelType = it as Fuel.Type
            }
            getSerializable(KEY_SELECTED_FUEL_QUALITY)?.let {
                selectedFuelQuality = it as Fuel.Quality
            }
            getSerializable(KEY_SELECTED_ROAD_TYPE)?.let {
                selectedRoadType = it as RoadType
            }
            vehicles = getParcelableArrayList(KEY_VEHICLES)!!
            getSerializable(KEY_COST)?.let {
                cost = it as BigDecimal
            }
            fuelAmount = getInt(KEY_FUEL_AMOUNT)
            locale = Locale.forLanguageTag(getString(KEY_LOCALE))
        }

        placeAutocompleteOrigin.setText(originAddress)
        placeAutocompleteDestination.setText(destinationAddress)
        updateVehicles(vehicles)
        spn_vehicle.setSelection(vehicles.indexOf(selectedVehicle))
        spn_tank_level.setSelection(TankLevel.values().indexOf(selectedTankLevel))

        if (cost != null && fuelAmount > 0) showResult()
    }

    private fun populateSpinners() {
        populateVehicleSpinner()
        populateTankLevelSpinner()
        populateFuelTypeSpinner()
        populateFuelQualitySpinner()
        populateRoadTypeSpinner()
    }

    private fun populateVehicleSpinner() {
        val vehicleNames = vehicles.map {
            "${it.manufacturer} ${it.model} ${it.details.trimLevel}"
        }
        val adapter = GenericSpinnerAdapter(this, vehicleNames)
        spn_vehicle.adapter = adapter
        spn_vehicle.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(spinner: AdapterView<*>?) { }

            override fun onItemSelected(spinner: AdapterView<*>?,
                                        itemView: View?,
                                        position: Int,
                                        id: Long) {
                selectedVehicle = vehicles[position]
            }
        }
    }

    private fun populateTankLevelSpinner() {
        val tankLevels = resources.getStringArray(R.array.tank_levels).toList()
        val adapter = GenericSpinnerAdapter(this, tankLevels)
        spn_tank_level.adapter = adapter
        spn_tank_level.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(spinner: AdapterView<*>?) { }

            override fun onItemSelected(spinner: AdapterView<*>?,
                                        itemView: View?,
                                        position: Int,
                                        id: Long) {
                selectedTankLevel = TankLevel.values()[position]
            }
        }
    }

    private fun populateFuelTypeSpinner() {
        val fuelTypes = Fuel.Type.values().map { getString(it.stringRes) }
        val adapter = GenericSpinnerAdapter(this, fuelTypes)
        spn_fuel_type.adapter = adapter
        spn_fuel_type.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(spinner: AdapterView<*>?) { }

            override fun onItemSelected(spinner: AdapterView<*>?, itemView: View?, position: Int, p3: Long) {
                selectedFuelType = Fuel.Type.values()[position]
            }
        }
    }

    private fun populateFuelQualitySpinner() {
        val fuelQualities = Fuel.Quality.values().map { getString(it.stringRes) }
        val adapter = GenericSpinnerAdapter(this, fuelQualities)
        spn_fuel_quality.adapter = adapter
        spn_fuel_quality.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(spinner: AdapterView<*>?) { }

            override fun onItemSelected(spinner: AdapterView<*>?, itemView: View?, position: Int, p3: Long) {
                selectedFuelQuality = Fuel.Quality.values()[position]
            }
        }
    }

    private fun populateRoadTypeSpinner() {
        val roadTypes = resources.getStringArray(R.array.road_types).toList()
        val adapter = GenericSpinnerAdapter(this, roadTypes)
        spn_road_type.adapter = adapter
        spn_road_type.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(spinner: AdapterView<*>?) { }

            override fun onItemSelected(spinner: AdapterView<*>?, itemView: View?, position: Int, p3: Long) {
                selectedRoadType = RoadType.values()[position]
            }
        }
    }

    private fun getResult() {
        val noBlankFields = origin != null
                && destination != null
                && selectedVehicle != null
                && selectedTankLevel != null
                && selectedFuelType != null
                && selectedFuelQuality != null
                && selectedRoadType != null


        if (noBlankFields) {
            presenter.estimateCostAndFuelAmount(this,
                    origin!!,
                    destination!!,
                    selectedFuelType!!,
                    selectedFuelQuality!!,
                    selectedVehicle!!,
                    selectedTankLevel!!,
                    selectedRoadType!!)
        }
    }

    private fun showResult() {
        group_result.visibility = View.VISIBLE

        val costStr = formatPriceAsCurrency(cost!!, locale)
        txt_estimated_cost.text = getString(R.string.estimated_cost_format, costStr)
        txt_estimated_fuel_amount.text = getString(R.string.estimated_fuel_amount_format, fuelAmount)
    }
    // endregion

}