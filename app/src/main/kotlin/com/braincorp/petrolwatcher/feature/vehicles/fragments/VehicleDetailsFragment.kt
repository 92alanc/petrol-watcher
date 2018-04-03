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
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.braincorp.petrolwatcher.model.AdaptableUi
import kotlinx.android.synthetic.main.fragment_vehicle_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehicleDetailsFragment : Fragment(), AdaptableUi,
        AdapterView.OnItemSelectedListener, View.OnClickListener {

    companion object {
        private const val LOG_TAG = "VehicleDetailsFragment"

        private const val ARG_UI_MODE = "ui_mode"
        private const val ARG_VEHICLE = "vehicle"

        private const val KEY_INPUT_TYPE = "input_type"
        private const val KEY_YEAR = "year"
        private const val KEY_YEARS_LIST = "years_list"

        fun newInstance(uiMode: AdaptableUi.Mode = AdaptableUi.Mode.INITIAL,
                        vehicle: Vehicle? = null,
                        deleteButtonClickListener: View.OnClickListener): VehicleDetailsFragment {
            val instance = VehicleDetailsFragment()

            val args = Bundle()
            args.putSerializable(ARG_UI_MODE, uiMode)
            if (vehicle != null)
                args.putParcelable(ARG_VEHICLE, vehicle)
            instance.arguments = args

            instance.deleteButtonClickListener = deleteButtonClickListener

            return instance
        }
    }

    private val api = VehicleApi.getApi()

    private var uiMode = AdaptableUi.Mode.INITIAL
    private var vehicle = Vehicle()

    private var year: Int = -1
    private var yearsList = ArrayList<Int>()

    private var inputType = InputType.AUTOMATIC

    private lateinit var manufacturer: String
    private val manufacturersList = ArrayList<String>()

    private lateinit var model: String
    private val modelsList = ArrayList<String>()

    private lateinit var modelDetails: ModelDetails.Trims

    private lateinit var deleteButtonClickListener: View.OnClickListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_vehicle_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgs()
        if (savedInstanceState != null)
            parseSavedInstanceState(savedInstanceState)
        prepareUi()
        setupSpinners()
        buttonDelete.setOnClickListener(deleteButtonClickListener)
        buttonInputType.setOnClickListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putSerializable(ARG_UI_MODE, uiMode)
        outState?.putSerializable(KEY_INPUT_TYPE, inputType)

        outState?.putInt(KEY_YEAR, year)
        outState?.putIntegerArrayList(KEY_YEARS_LIST, yearsList)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonInputType -> changeInputType()
        }
    }

    override fun prepareInitialMode() {
        prepareViewMode()
    }

    override fun prepareCreateMode() {
        uiMode = AdaptableUi.Mode.CREATE

        groupManualInput.visibility = GONE
        groupLabelsManual.visibility = GONE
        groupTextViews.visibility = GONE
        buttonDelete.visibility = GONE

        groupAutomaticInput.visibility = VISIBLE
        groupLabelsAuto.visibility = VISIBLE
        buttonInputType.visibility = VISIBLE

        if (yearsList.isEmpty())
            loadYears()
    }

    override fun prepareEditMode() {
        uiMode = AdaptableUi.Mode.EDIT

        groupAutomaticInput.visibility = GONE
        groupLabelsAuto.visibility = GONE
        groupTextViews.visibility = GONE
        buttonInputType.visibility = GONE

        groupLabelsManual.visibility = VISIBLE
        groupManualInput.visibility = VISIBLE
        buttonDelete.visibility = VISIBLE

        fillEditTexts()
    }

    override fun prepareViewMode() {
        uiMode = AdaptableUi.Mode.VIEW

        groupAutomaticInput.visibility = GONE
        groupLabelsAuto.visibility = GONE
        groupManualInput.visibility = GONE
        groupLabelsManual.visibility = GONE
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

    fun getVehicle(): Vehicle? {
        if (uiMode == AdaptableUi.Mode.EDIT || inputType == InputType.MANUAL) {
            // TODO: handle cases where EditTexts are empty
            year = editTextYear.text.toString().toInt()
            manufacturer = editTextManufacturer.text.toString()
            model = editTextName.text.toString()

            modelDetails = ModelDetails.Trims()
            modelDetails.fuelCapacityLitres = editTextFuelCapacity.text
                    .toString()
                    .toInt()

            modelDetails.litresPer100KmCity = editTextAverageConsumptionCity.text
                    .toString()
                    .toFloat()

            modelDetails.litresPer100KmMotorway = editTextAverageConsumptionMotorway.text
                    .toString()
                    .toFloat()
        }

        vehicle.year = year
        vehicle.manufacturer = manufacturer
        vehicle.name = model
        vehicle.fuelCapacity = modelDetails.fuelCapacityLitres
        vehicle.litresPer100KmCity = modelDetails.litresPer100KmCity
        vehicle.litresPer100KmMotorway = modelDetails.litresPer100KmMotorway

        return vehicle
    }

    private fun changeInputType() {
        if (inputType == InputType.AUTOMATIC) toggleManualInput()
        else toggleAutomaticInput()
    }

    private fun toggleAutomaticInput() {
        inputType = InputType.AUTOMATIC

        buttonInputType.setText(R.string.manual_input)

        groupLabelsManual.visibility = GONE
        groupManualInput.visibility = GONE

        groupLabelsAuto.visibility = VISIBLE
        groupAutomaticInput.visibility = VISIBLE
    }

    private fun toggleManualInput() {
        inputType = InputType.MANUAL

        buttonInputType.setText(R.string.automatic_input)

        groupLabelsAuto.visibility = GONE
        groupAutomaticInput.visibility = GONE

        groupLabelsManual.visibility = VISIBLE
        groupManualInput.visibility = VISIBLE
    }

    private fun prepareUi() {
        when (uiMode) {
            AdaptableUi.Mode.INITIAL -> prepareInitialMode()
            AdaptableUi.Mode.CREATE -> prepareCreateMode()
            AdaptableUi.Mode.EDIT -> prepareEditMode()
            AdaptableUi.Mode.VIEW -> prepareViewMode()
        }
    }

    private fun parseSavedInstanceState(savedInstanceState: Bundle) {
        inputType = savedInstanceState.getSerializable(KEY_INPUT_TYPE) as InputType
        uiMode = savedInstanceState.getSerializable(ARG_UI_MODE) as AdaptableUi.Mode
        year = savedInstanceState.getInt(KEY_YEAR)
        yearsList = savedInstanceState.getIntegerArrayList(KEY_YEARS_LIST)
    }

    private fun parseArgs() {
        uiMode = arguments!!.getSerializable(ARG_UI_MODE) as AdaptableUi.Mode
        if (arguments!!.containsKey(ARG_VEHICLE))
            vehicle = arguments!!.getParcelable(ARG_VEHICLE)
    }

    private fun setupSpinners() {
        spinnerYear.onItemSelectedListener = this
        spinnerManufacturer.onItemSelectedListener = this
        spinnerModel.onItemSelectedListener = this
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
        spinnerManufacturer.adapter = null
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
        spinnerModel.adapter = null
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
                    val list = response.body()!!.list
                    if (list.isNotEmpty())
                        modelDetails = list[0]
                }
            }
        })
    }

    private fun fillTextViews() {
        var manufacturer = vehicle.manufacturer
        manufacturer = manufacturer.replace(manufacturer[0], manufacturer[0].toUpperCase())
        textViewManufacturerAndName.text = getString(R.string.manufacturer_and_name_format,
                manufacturer,
                vehicle.name)
        textViewYear.text = getString(R.string.year_format, vehicle.year)

        // TODO: make fields with unknown value explicit to user
        val fuelCapacity = if (vehicle.fuelCapacity == -1) getString(R.string.unknown_enter_data_manually)
        else vehicle.fuelCapacity.toString()

        textViewFuelCapacity.text = fuelCapacity

        val averageConsumptionMotorway = if (vehicle.litresPer100KmMotorway == -1f)
            getString(R.string.unknown_enter_data_manually)
        else vehicle.litresPer100KmMotorway.toString()

        textViewAverageConsumptionMotorway.text = averageConsumptionMotorway

        val averageConsumptionCity = if (vehicle.litresPer100KmCity == -1f)
            getString(R.string.unknown_enter_data_manually)
        else vehicle.litresPer100KmCity.toString()

        textViewAverageConsumptionCity.text = averageConsumptionCity
    }

    private fun fillEditTexts() {
        editTextYear.setText(vehicle.year.toString())
        editTextManufacturer.setText(vehicle.manufacturer)
        editTextName.setText(vehicle.name)

        val fuelCapacity = if (vehicle.fuelCapacity != -1) vehicle.fuelCapacity.toString()
        else null
        editTextFuelCapacity.setText(fuelCapacity)

        val consumptionMotorway = if (vehicle.litresPer100KmMotorway != -1f)
            String.format("%.1f", vehicle.litresPer100KmMotorway)
        else null
        editTextAverageConsumptionMotorway.setText(consumptionMotorway)

        val consumptionCity = if (vehicle.litresPer100KmCity != -1f)
            String.format("%.1f", vehicle.litresPer100KmCity)
        else null
        editTextAverageConsumptionCity.setText(consumptionCity)
    }

    private enum class InputType {
        AUTOMATIC,
        MANUAL
    }

}