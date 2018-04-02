package com.braincorp.petrolwatcher.feature.vehicles.fragments

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.adapters.GenericSpinnerAdapter
import com.braincorp.petrolwatcher.feature.vehicles.api.VehicleApi
import com.braincorp.petrolwatcher.feature.vehicles.api.model.Makes
import com.braincorp.petrolwatcher.feature.vehicles.api.model.ModelDetails
import com.braincorp.petrolwatcher.feature.vehicles.api.model.Models
import com.braincorp.petrolwatcher.feature.vehicles.api.model.Years
import com.braincorp.petrolwatcher.feature.vehicles.model.NewVehicleModel
import com.braincorp.petrolwatcher.model.AdaptableUi
import kotlinx.android.synthetic.main.fragment_vehicle_details_new.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehicleDetailsNewFragment : Fragment(), AdaptableUi, AdapterView.OnItemSelectedListener {

    companion object {
        private const val LOG_TAG = "VehicleDetailsFragment"

        private const val ARG_UI_MODE = "ui_mode"
        private const val ARG_VEHICLE = "vehicle"

        private const val KEY_YEAR = "year"
        private const val KEY_YEARS_LIST = "years_list"

        fun newInstance(uiMode: AdaptableUi.Mode = AdaptableUi.Mode.INITIAL,
                        vehicle: NewVehicleModel? = null): VehicleDetailsNewFragment {
            val instance = VehicleDetailsNewFragment()

            val args = Bundle()
            args.putSerializable(ARG_UI_MODE, uiMode)
            args.putParcelable(ARG_VEHICLE, vehicle)
            instance.arguments = args

            return instance
        }
    }

    private val api = VehicleApi.getApi()

    private var uiMode = AdaptableUi.Mode.INITIAL
    private var vehicle = NewVehicleModel()

    private var year: Int = -1
    private val yearsList = ArrayList<Int>()

    private lateinit var manufacturer: String
    private val manufacturersList = ArrayList<String>()

    private lateinit var model: String
    private val modelsList = ArrayList<String>()

    private lateinit var modelDetails: ModelDetails.Trims

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_vehicle_details_new, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgs()
        prepareUi()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putSerializable(ARG_UI_MODE, uiMode)

        outState?.putInt(KEY_YEAR, year)
        outState?.putIntegerArrayList(KEY_YEARS_LIST, yearsList)
    }

    override fun prepareInitialMode() {
        prepareViewMode()
    }

    override fun prepareCreateMode() {
        uiMode = AdaptableUi.Mode.CREATE
        if (yearsList.isEmpty())
            loadYears()
    }

    override fun prepareEditMode() {
        uiMode = AdaptableUi.Mode.EDIT
    }

    override fun prepareViewMode() {
        uiMode = AdaptableUi.Mode.VIEW

        groupAutomaticInput.visibility = GONE
        groupManualInput.visibility = GONE
        buttonInputType.visibility = GONE
        groupTextViews.visibility = VISIBLE

        fillTextViews()
    }

    override fun onNothingSelected(adapterView: AdapterView<*>) { }

    override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
        when (adapterView.id) {
            R.id.spinnerYear -> {
                year = yearsList[position]
                loadManufacturers(year)
            }

            R.id.spinnerManufacturer -> {
                manufacturer = manufacturersList[position]
                loadModels(year, manufacturer)
            }

            R.id.spinnerModel -> {
                model = modelsList[position]
                loadModelDetails(year, manufacturer, model)
            }
        }
    }

    fun getVehicle(): NewVehicleModel? = vehicle

    private fun prepareUi() {
        when (uiMode) {
            AdaptableUi.Mode.INITIAL -> prepareInitialMode()
            AdaptableUi.Mode.CREATE -> prepareCreateMode()
            AdaptableUi.Mode.EDIT -> prepareEditMode()
            AdaptableUi.Mode.VIEW -> prepareViewMode()
        }
    }

    private fun parseArgs() {
        uiMode = arguments!!.getSerializable(ARG_UI_MODE) as AdaptableUi.Mode
        vehicle = arguments!!.getParcelable(ARG_VEHICLE)
    }

    private fun loadYears() {
        api.getYears().enqueue(object: Callback<Years> {
            override fun onFailure(call: Call<Years>, t: Throwable) {
                Log.e(LOG_TAG, t.message, t)
            }

            override fun onResponse(call: Call<Years>, response: Response<Years>) {
                if (response.isSuccessful) {
                    val yearsRange = response.body()!!.range
                    for (year in yearsRange.max downTo yearsRange.min)
                        yearsList.add(year)

                    populateYearSpinner(yearsList)
                }
            }
        })
    }

    private fun populateYearSpinner(data: List<Int>) {
        val adapter = GenericSpinnerAdapter(activity, data)
        spinnerYear.adapter = adapter
    }

    private fun loadManufacturers(year: Int) {
        api.getMakes(year).enqueue(object: Callback<Makes> {
            override fun onFailure(call: Call<Makes>, t: Throwable) {
                Log.e(LOG_TAG, t.message, t)
            }

            override fun onResponse(call: Call<Makes>, response: Response<Makes>) {
                if (response.isSuccessful) {
                    val manufacturers = response.body()!!.list
                    for (manufacturer in manufacturers)
                        manufacturersList.add(manufacturer.id!!)

                    populateManufacturerSpinner(manufacturersList)
                }
            }
        })
    }

    private fun populateManufacturerSpinner(data: List<String>) {
        val adapter = GenericSpinnerAdapter(activity, data)
        spinnerManufacturer.adapter = adapter
    }

    private fun loadModels(year: Int, manufacturer: String) {
        api.getModels(year, manufacturer).enqueue(object: Callback<Models> {
            override fun onFailure(call: Call<Models>, t: Throwable) {
                Log.e(LOG_TAG, t.message, t)
            }

            override fun onResponse(call: Call<Models>, response: Response<Models>) {
                if (response.isSuccessful) {
                    val models = response.body()!!.list
                    for (model in models)
                        modelsList.add(model.name!!)

                    populateModelSpinner(modelsList)
                }
            }
        })
    }

    private fun populateModelSpinner(data: List<String>) {
        val adapter = GenericSpinnerAdapter(activity, data)
        spinnerModel.adapter = adapter
    }

    private fun loadModelDetails(year: Int, manufacturer: String, model: String) {
        api.getDetails(year, manufacturer, model).enqueue(object: Callback<ModelDetails> {
            override fun onFailure(call: Call<ModelDetails>, t: Throwable) {
                Log.e(LOG_TAG, t.message, t)
            }

            override fun onResponse(call: Call<ModelDetails>, response: Response<ModelDetails>) {
                if (response.isSuccessful) {
                    modelDetails = response.body()!!.list[0]
                }
            }
        })
    }

    private fun fillTextViews() {
        textViewManufacturerAndName.text = getString(R.string.manufacturer_and_name_format,
                                                     vehicle.manufacturer,
                                                     vehicle.name)
        textViewYear.text = getString(R.string.year_format, vehicle.year)
        textViewFuelCapacity.text = getString(R.string.fuel_capacity_format, vehicle.fuelCapacity)
        textViewFuelConsumptionMotorway.text = getString(R.string.fuel_efficiency_motorway_format,
                                                         vehicle.litresPer100KmMotorway)
        textViewFuelConsumptionCity.text = getString(R.string.fuel_efficiency_city_format,
                                                     vehicle.litresPer100KmCity)
    }

}