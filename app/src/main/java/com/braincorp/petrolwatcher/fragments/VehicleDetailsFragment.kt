package com.braincorp.petrolwatcher.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.adapters.VehicleTypeAdapter
import com.braincorp.petrolwatcher.model.FuelType
import com.braincorp.petrolwatcher.model.UiMode
import com.braincorp.petrolwatcher.model.Vehicle
import com.braincorp.petrolwatcher.model.VehicleType
import com.braincorp.petrolwatcher.preferences.PreferenceManager
import com.braincorp.petrolwatcher.preferences.SystemOfMeasurement
import com.braincorp.petrolwatcher.utils.fuelTypeListToString
import com.braincorp.petrolwatcher.utils.vehicleTypeToDrawable

class VehicleDetailsFragment : Fragment() {

    companion object {
        private const val ARG_UI_MODE = "ui_mode"
        private const val ARG_VEHICLE = "vehicle"

        fun newInstance(uiMode: UiMode, vehicle: Vehicle? = null): VehicleDetailsFragment {
            val instance = VehicleDetailsFragment()
            val args = Bundle()
            args.putSerializable(ARG_UI_MODE, uiMode)
            args.putParcelable(ARG_VEHICLE, vehicle)
            instance.arguments = args
            return instance
        }
    }

    private lateinit var textViewName: TextView
    private lateinit var textViewManufacturer: TextView
    private lateinit var textViewYear: TextView
    private lateinit var imageViewVehicleType: ImageView
    private lateinit var textViewFuelTypes: TextView
    private lateinit var textViewKmPerLitre: TextView

    private lateinit var editTextName: EditText
    private lateinit var editTextManufacturer: EditText
    private lateinit var editTextYear: EditText
    private lateinit var spinnerVehicleType: Spinner
    private lateinit var checkBoxAutogas: CheckBox
    private lateinit var checkBoxDiesel: CheckBox
    private lateinit var checkBoxEthanol: CheckBox
    private lateinit var checkBoxPetrol: CheckBox
    private lateinit var editTextKmPerLitre: EditText
    private lateinit var divider: View

    private var systemOfMeasurement: SystemOfMeasurement = SystemOfMeasurement.METRIC
    private var uiMode = UiMode.VIEW
    private var vehicle: Vehicle? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val preferenceManager = PreferenceManager(context!!)
        systemOfMeasurement = preferenceManager.getSystemOfMeasurement()
        val view = inflater.inflate(R.layout.fragment_vehicle_details, container, false)
        bindViews(view)
        applyPreferences()
        parseArgs()
        prepareUi()
        return view
    }

    private fun applyPreferences() {
        when (systemOfMeasurement) {
            SystemOfMeasurement.IMPERIAL -> {
                editTextKmPerLitre.hint = context!!.getString(R.string.miles_per_gallon)
            }

            SystemOfMeasurement.METRIC -> {
                editTextKmPerLitre.hint = context!!.getString(R.string.km_per_litre)
            }
        }
    }

    private fun bindViews(view: View) {
        textViewName = view.findViewById(R.id.textViewVehicleName)
        textViewManufacturer = view.findViewById(R.id.textViewManufacturerAndName)
        textViewYear = view.findViewById(R.id.textViewYear)
        imageViewVehicleType = view.findViewById(R.id.imageViewVehicleType)
        textViewFuelTypes = view.findViewById(R.id.textViewFuelTypes)
        textViewKmPerLitre = view.findViewById(R.id.textViewKmPerLitre)

        editTextName = view.findViewById(R.id.editTextVehicleName)
        editTextManufacturer = view.findViewById(R.id.editTextManufacturer)
        editTextYear = view.findViewById(R.id.editTextYear)
        spinnerVehicleType = view.findViewById(R.id.spinnerVehicleType)
        checkBoxAutogas = view.findViewById(R.id.checkBoxAutogas)
        checkBoxDiesel = view.findViewById(R.id.checkBoxDiesel)
        checkBoxEthanol = view.findViewById(R.id.checkBoxEthanol)
        checkBoxPetrol = view.findViewById(R.id.checkBoxPetrol)
        editTextKmPerLitre = view.findViewById(R.id.editTextKmPerLitre)
        divider = view.findViewById(R.id.divider)
    }

    private fun parseArgs() {
        uiMode = arguments?.getSerializable(ARG_UI_MODE) as UiMode
        vehicle = arguments?.getParcelable(ARG_VEHICLE)
        Log.d("ALAN", "found ${vehicle?.fuelTypes} and ${vehicle?.kmPerLitre}")
    }

    private fun populateSpinner() {
        val adapter = VehicleTypeAdapter(context!!)
        spinnerVehicleType.adapter = adapter
    }

    private fun prepareUi() {
        when (uiMode) {
            UiMode.CREATE -> prepareCreateMode()
            UiMode.EDIT -> prepareEditMode()
            UiMode.VIEW -> prepareViewMode()
        }
    }

    private fun prepareCreateMode() {
        hideNotEditableViews()
        showEditableFields()
    }

    private fun prepareEditMode() {
        hideNotEditableViews()
        showEditableFields()
        fillEditableFields()
    }

    private fun prepareViewMode() {
        hideEditableFields()
        showNotEditableFields()
        fillNotEditableFields()
    }

    private fun hideNotEditableViews() {
        textViewName.visibility = GONE
        textViewManufacturer.visibility = GONE
        textViewYear.visibility = GONE
        imageViewVehicleType.visibility = GONE
        textViewFuelTypes.visibility = GONE
        textViewKmPerLitre.visibility = GONE
    }

    private fun showEditableFields() {
        editTextName.visibility = VISIBLE
        editTextManufacturer.visibility = VISIBLE
        editTextYear.visibility = VISIBLE
        spinnerVehicleType.visibility = VISIBLE
        checkBoxAutogas.visibility = VISIBLE
        checkBoxDiesel.visibility = VISIBLE
        checkBoxEthanol.visibility = VISIBLE
        checkBoxPetrol.visibility = VISIBLE
        editTextKmPerLitre.visibility = VISIBLE
        divider.visibility = VISIBLE
        populateSpinner()
    }

    private fun hideEditableFields() {
        editTextName.visibility = GONE
        editTextManufacturer.visibility = GONE
        editTextYear.visibility = GONE
        spinnerVehicleType.visibility = GONE
        checkBoxAutogas.visibility = GONE
        checkBoxDiesel.visibility = GONE
        checkBoxEthanol.visibility = GONE
        checkBoxPetrol.visibility = GONE
        editTextKmPerLitre.visibility = GONE
        divider.visibility = GONE
    }

    private fun showNotEditableFields() {
        textViewName.visibility = VISIBLE
        textViewManufacturer.visibility = VISIBLE
        textViewYear.visibility = VISIBLE
        imageViewVehicleType.visibility = VISIBLE
        textViewFuelTypes.visibility = VISIBLE
        textViewKmPerLitre.visibility = VISIBLE
    }

    private fun fillEditableFields() {
        editTextName.setText(vehicle!!.name)
        editTextManufacturer.setText(vehicle!!.manufacturer)
        editTextYear.setText(vehicle!!.year.toString())
        spinnerVehicleType.setSelection(vehicle!!.vehicleType.ordinal)
        vehicle!!.fuelTypes.forEach {
            when (it) {
                FuelType.AUTOGAS -> checkBoxAutogas.isChecked = true
                FuelType.DIESEL -> checkBoxDiesel.isChecked = true
                FuelType.ETHANOL -> checkBoxEthanol.isChecked = true
                FuelType.PETROL -> checkBoxPetrol.isChecked = true
            }
        }
        editTextKmPerLitre.setText(vehicle!!.kmPerLitre.toString())
    }

    @SuppressLint("SetTextI18n")
    private fun fillNotEditableFields() {
        textViewName.text = vehicle!!.name
        textViewManufacturer.text = vehicle!!.manufacturer
        textViewYear.text = vehicle!!.year.toString()

        val drawable = context!!.vehicleTypeToDrawable(vehicle!!.vehicleType)
        imageViewVehicleType.setImageDrawable(drawable)

        textViewFuelTypes.text = context!!.fuelTypeListToString(vehicle!!.fuelTypes)

        val unit = when (systemOfMeasurement) {
            SystemOfMeasurement.IMPERIAL -> context!!.getString(R.string.unit_miles_per_gallon)
            SystemOfMeasurement.METRIC -> context!!.getString(R.string.unit_km_per_litre)
        }
        textViewKmPerLitre.text = "${vehicle!!.kmPerLitre} $unit"
    }

    fun getVehicle(): Vehicle {
        if (uiMode == UiMode.VIEW) return vehicle!!
        if (uiMode == UiMode.CREATE) vehicle = Vehicle()

        vehicle!!.name = editTextName.text.toString()
        vehicle!!.manufacturer = editTextManufacturer.text.toString()
        vehicle!!.year = editTextYear.text.toString().toInt()

        val fuelTypesList = ArrayList<FuelType>()
        if (checkBoxAutogas.isChecked) fuelTypesList.add(FuelType.AUTOGAS)
        if (checkBoxDiesel.isChecked) fuelTypesList.add(FuelType.DIESEL)
        if (checkBoxEthanol.isChecked) fuelTypesList.add(FuelType.ETHANOL)
        if (checkBoxPetrol.isChecked) fuelTypesList.add(FuelType.PETROL)

        vehicle!!.fuelTypes = fuelTypesList

        vehicle!!.vehicleType = spinnerVehicleType.selectedItem as VehicleType
        vehicle!!.kmPerLitre = editTextKmPerLitre.text.toString().toFloat()

        return vehicle!!
    }

}