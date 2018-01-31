package com.braincorp.petrolwatcher.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.UiMode

class VehicleDetailsFragment : Fragment() {

    companion object {
        private const val ARG_UI_MODE = "ui_mode"

        fun newInstance(uiMode: UiMode): VehicleDetailsFragment {
            val instance = VehicleDetailsFragment()
            val args = Bundle()
            args.putSerializable(ARG_UI_MODE, uiMode)
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

    private var uiMode = UiMode.VIEW

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_vehicle_details, container, false)
        bindViews(view)
        parseArgs()
        prepareUi()
        return view
    }

    private fun bindViews(view: View) {
        textViewName = view.findViewById(R.id.textViewVehicleName)
        textViewManufacturer = view.findViewById(R.id.textViewManufacturer)
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
    }

    private fun parseArgs() {
        uiMode = arguments?.getSerializable(ARG_UI_MODE) as UiMode
    }

    private fun prepareUi() {
        when (uiMode) {
            UiMode.CREATE -> prepareCreateMode()
            UiMode.EDIT -> prepareEditMode()
            UiMode.VIEW -> prepareViewMode()
        }
    }

    private fun prepareCreateMode() {

    }

    private fun prepareEditMode() {

    }

    private fun prepareViewMode() {

    }

}