package com.braincorp.petrolwatcher.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.adapters.VehicleTypeAdapter
import com.braincorp.petrolwatcher.listeners.OnFragmentInflatedListener
import com.braincorp.petrolwatcher.model.*
import com.braincorp.petrolwatcher.preferences.PreferenceManager
import com.braincorp.petrolwatcher.utils.fuelTypeListToString
import com.braincorp.petrolwatcher.utils.vehicleTypeToDrawable

class VehicleDetailsFragment : Fragment(), AdaptableUi {

    companion object {
        private const val ARG_UI_MODE = "ui_mode"
        private const val ARG_VEHICLE = "vehicle"

        fun newInstance(uiMode: AdaptableUi.Mode,
                        onFragmentInflatedListener: OnFragmentInflatedListener,
                        vehicle: Vehicle? = null): VehicleDetailsFragment {
            val instance = VehicleDetailsFragment()
            instance.setOnFragmentInflatedListener(onFragmentInflatedListener)
            val args = Bundle()
            args.putSerializable(ARG_UI_MODE, uiMode)
            args.putParcelable(ARG_VEHICLE, vehicle)
            instance.arguments = args
            return instance
        }
    }

    private lateinit var textViewManufacturerAndName: TextView
    private lateinit var textViewYear: TextView
    private lateinit var imageViewVehicleType: ImageView
    private lateinit var textViewFuelTypes: TextView
    private lateinit var textViewFuelConsumption: TextView
    private lateinit var buttonDelete: ImageButton

    private lateinit var editTextName: EditText
    private lateinit var editTextManufacturer: EditText
    private lateinit var editTextYear: EditText
    private lateinit var spinnerVehicleType: Spinner
    private lateinit var checkBoxAutogas: CheckBox
    private lateinit var checkBoxDiesel: CheckBox
    private lateinit var checkBoxEthanol: CheckBox
    private lateinit var checkBoxPetrol: CheckBox
    private lateinit var editTextFuelConsumption: EditText
    private lateinit var divider: View

    private var onFragmentInflatedListener: OnFragmentInflatedListener? = null
    private var systemOfMeasurement: SystemOfMeasurement = SystemOfMeasurement.METRIC
    private var uiMode = AdaptableUi.Mode.VIEW
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
        onFragmentInflatedListener?.onFragmentInflated(fragment = this)
        return view
    }

    override fun prepareInitialMode() {
        prepareViewMode()
    }

    override fun prepareCreateMode() {
        hideNotEditableViews()
        showEditableFields()
    }

    override fun prepareEditMode() {
        hideNotEditableViews()
        showEditableFields()
        fillEditableFields()
    }

    override fun prepareViewMode() {
        hideEditableFields()
        showNotEditableFields()
        fillNotEditableFields()
    }

    fun getVehicle(): Vehicle {
        if (uiMode == AdaptableUi.Mode.VIEW) return vehicle!!
        if (uiMode == AdaptableUi.Mode.CREATE) vehicle = Vehicle()

        vehicle!!.name = editTextName.text.toString()
        vehicle!!.manufacturer = editTextManufacturer.text.toString()
        vehicle!!.year = if (!TextUtils.isEmpty(editTextYear.text.toString()))
            editTextYear.text.toString().toInt()
        else 0

        val fuelTypesList = ArrayList<FuelType>()
        if (checkBoxAutogas.isChecked) fuelTypesList.add(FuelType.LPG)
        if (checkBoxDiesel.isChecked) fuelTypesList.add(FuelType.DIESEL)
        if (checkBoxEthanol.isChecked) fuelTypesList.add(FuelType.ETHANOL)
        if (checkBoxPetrol.isChecked) fuelTypesList.add(FuelType.PETROL)

        vehicle!!.fuelTypes = fuelTypesList

        vehicle!!.vehicleType = spinnerVehicleType.selectedItem as VehicleType
        vehicle!!.fuelConsumption = if (!TextUtils.isEmpty(editTextFuelConsumption.text.toString()))
            editTextFuelConsumption.text.toString().toFloat()
        else 0f

        return vehicle!!
    }

    fun setDeleteButtonClickListener(clickListener: View.OnClickListener) {
        buttonDelete.setOnClickListener(clickListener)
    }

    fun setOnFragmentInflatedListener(onFragmentInflatedListener: OnFragmentInflatedListener?) {
        this.onFragmentInflatedListener = onFragmentInflatedListener
    }

    private fun applyPreferences() {
        when (systemOfMeasurement) {
            SystemOfMeasurement.IMPERIAL -> {
                editTextFuelConsumption.hint = context!!.getString(R.string.miles_per_gallon)
            }

            SystemOfMeasurement.METRIC -> {
                editTextFuelConsumption.hint = context!!.getString(R.string.km_per_litre)
            }
        }
    }

    private fun bindViews(view: View) {
        textViewManufacturerAndName = view.findViewById(R.id.textViewManufacturerAndName)
        textViewYear = view.findViewById(R.id.textViewYear)
        imageViewVehicleType = view.findViewById(R.id.imageViewVehicleType)
        textViewFuelTypes = view.findViewById(R.id.textViewFuelTypes)
        textViewFuelConsumption = view.findViewById(R.id.textViewFuelConsumption)
        buttonDelete = view.findViewById(R.id.buttonDelete)

        editTextName = view.findViewById(R.id.editTextVehicleName)
        editTextManufacturer = view.findViewById(R.id.editTextManufacturer)
        editTextYear = view.findViewById(R.id.editTextYear)
        spinnerVehicleType = view.findViewById(R.id.spinnerVehicleType)
        checkBoxAutogas = view.findViewById(R.id.checkBoxLpg)
        checkBoxDiesel = view.findViewById(R.id.checkBoxDiesel)
        checkBoxEthanol = view.findViewById(R.id.checkBoxEthanol)
        checkBoxPetrol = view.findViewById(R.id.checkBoxPetrol)
        editTextFuelConsumption = view.findViewById(R.id.editTextFuelConsumption)
        divider = view.findViewById(R.id.divider)
    }

    private fun parseArgs() {
        uiMode = arguments?.getSerializable(ARG_UI_MODE) as AdaptableUi.Mode
        vehicle = arguments?.getParcelable(ARG_VEHICLE)
        Log.d("ALAN", "found ${vehicle?.fuelTypes} and ${vehicle?.fuelConsumption}")
    }

    private fun populateSpinner() {
        val adapter = VehicleTypeAdapter(context!!)
        spinnerVehicleType.adapter = adapter
    }

    private fun prepareUi() {
        when (uiMode) {
            AdaptableUi.Mode.INITIAL -> prepareInitialMode()
            AdaptableUi.Mode.CREATE -> prepareCreateMode()
            AdaptableUi.Mode.EDIT -> prepareEditMode()
            AdaptableUi.Mode.VIEW -> prepareViewMode()
        }
    }

    private fun hideNotEditableViews() {
        textViewManufacturerAndName.visibility = GONE
        textViewYear.visibility = GONE
        imageViewVehicleType.visibility = GONE
        textViewFuelTypes.visibility = GONE
        textViewFuelConsumption.visibility = GONE
        buttonDelete.visibility = GONE
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
        editTextFuelConsumption.visibility = VISIBLE
        divider.visibility = VISIBLE
        populateSpinner()

        val unit = when (systemOfMeasurement) {
            SystemOfMeasurement.IMPERIAL -> context!!.getString(R.string.unit_miles_per_gallon)
            SystemOfMeasurement.METRIC -> context!!.getString(R.string.unit_km_per_litre)
        }

        val hint = "${context!!.getString(R.string.fuel_consumption)} ($unit)"
        editTextFuelConsumption.hint = hint
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
        editTextFuelConsumption.visibility = GONE
        divider.visibility = GONE
    }

    private fun showNotEditableFields() {
        textViewManufacturerAndName.visibility = VISIBLE
        textViewYear.visibility = VISIBLE
        imageViewVehicleType.visibility = VISIBLE
        textViewFuelTypes.visibility = VISIBLE
        textViewFuelConsumption.visibility = VISIBLE
        buttonDelete.visibility = VISIBLE
    }

    private fun fillEditableFields() {
        editTextName.setText(vehicle!!.name)
        editTextManufacturer.setText(vehicle!!.manufacturer)
        editTextYear.setText(vehicle!!.year.toString())
        spinnerVehicleType.setSelection(vehicle!!.vehicleType.ordinal)
        vehicle!!.fuelTypes.forEach {
            when (it) {
                FuelType.LPG -> checkBoxAutogas.isChecked = true
                FuelType.DIESEL -> checkBoxDiesel.isChecked = true
                FuelType.ETHANOL -> checkBoxEthanol.isChecked = true
                FuelType.PETROL -> checkBoxPetrol.isChecked = true
            }
        }
        editTextFuelConsumption.setText(vehicle!!.fuelConsumption.toString())
    }

    @SuppressLint("SetTextI18n")
    private fun fillNotEditableFields() {
        textViewManufacturerAndName.text = "${vehicle!!.manufacturer} ${vehicle!!.name}"
        textViewYear.text = vehicle!!.year.toString()

        val drawable = context!!.vehicleTypeToDrawable(vehicle!!.vehicleType)
        imageViewVehicleType.setImageDrawable(drawable)

        textViewFuelTypes.text = context!!.fuelTypeListToString(vehicle!!.fuelTypes)

        val unit = when (systemOfMeasurement) {
            SystemOfMeasurement.IMPERIAL -> context!!.getString(R.string.unit_miles_per_gallon)
            SystemOfMeasurement.METRIC -> context!!.getString(R.string.unit_km_per_litre)
        }
        textViewFuelConsumption.text = "${vehicle!!.fuelConsumption} $unit"
    }

}