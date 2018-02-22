package com.braincorp.petrolwatcher.fragments

import android.os.Bundle
import android.support.constraint.Group
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.model.PetrolStation

class PetrolStationDetailsFragment : Fragment(), AdaptableUi {

    companion object {
        private const val ARG_UI_MODE = "ui_mode"
        private const val ARG_PETROL_STATION = "petrol_station"

        fun newInstance(uiMode: AdaptableUi.Mode, petrolStation: PetrolStation? = null)
                : PetrolStationDetailsFragment {
            val instance = PetrolStationDetailsFragment()
            val args = Bundle()

            args.putParcelable(ARG_PETROL_STATION, petrolStation)
            args.putSerializable(ARG_UI_MODE, uiMode)

            instance.arguments = args
            return instance
        }
    }

    private var petrolStation: PetrolStation? = null
    private var uiMode = AdaptableUi.Mode.INITIAL

    private lateinit var textViewName: TextView
    private lateinit var textViewAddress: TextView
    private lateinit var textViewRating: TextView

    private lateinit var editTextName: EditText
    private lateinit var editTextAddress: EditText

    private lateinit var buttonLocate: ImageButton

    private lateinit var groupEditableFields: Group
    private lateinit var groupNotEditableFields: Group

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_petrol_station_details, container, false)
        bindViews(view)
        parseArgs()
        prepareUi()
        return view
    }

    override fun prepareInitialMode() {
        prepareViewMode()
    }

    override fun prepareCreateMode() {
        textViewRating.visibility = GONE
        hideNotEditableFields()
        showEditableFields()
        hideRating()
    }

    override fun prepareEditMode() {
        textViewRating.visibility = VISIBLE
        hideNotEditableFields()
        showEditableFields()
        fillEditableFields()
        showRating()
    }

    override fun prepareViewMode() {
        textViewRating.visibility = VISIBLE
        hideEditableFields()
        showNotEditableFields()
        fillNotEditableFields()
        showRating()
    }

    fun getName() = editTextName.text.toString()

    fun getAddress() = editTextAddress.text.toString()

    private fun bindViews(view: View) {
        textViewName = view.findViewById(R.id.textViewName)
        textViewAddress = view.findViewById(R.id.textViewAddress)
        textViewRating = view.findViewById(R.id.textViewRating)

        editTextName = view.findViewById(R.id.editTextName)
        editTextAddress = view.findViewById(R.id.editTextAddress)

        buttonLocate = view.findViewById(R.id.buttonLocate)

        groupEditableFields = view.findViewById(R.id.groupEditableFields)
        groupNotEditableFields = view.findViewById(R.id.groupNotEditableFields)
    }

    private fun parseArgs() {
        petrolStation = arguments?.getParcelable(ARG_PETROL_STATION)
        uiMode = arguments?.getSerializable(ARG_UI_MODE) as AdaptableUi.Mode
    }

    private fun prepareUi() {
        when (uiMode) {
            AdaptableUi.Mode.INITIAL -> prepareInitialMode()
            AdaptableUi.Mode.CREATE -> prepareCreateMode()
            AdaptableUi.Mode.EDIT -> prepareEditMode()
            AdaptableUi.Mode.VIEW -> prepareViewMode()
        }
    }

    private fun hideNotEditableFields() {
        groupNotEditableFields.visibility = GONE
    }

    private fun showNotEditableFields() {
        groupNotEditableFields.visibility = VISIBLE
    }

    private fun hideEditableFields() {
        groupEditableFields.visibility = GONE
    }

    private fun showEditableFields() {
        groupEditableFields.visibility = VISIBLE
    }

    private fun fillNotEditableFields() {
        textViewName.text = petrolStation?.name
        textViewAddress.text = petrolStation?.address
    }

    private fun fillEditableFields() {
        editTextName.setText(petrolStation?.name)
        editTextAddress.setText(petrolStation?.address)
    }

    private fun showRating() {
        textViewRating.visibility = VISIBLE
    }

    private fun hideRating() {
        textViewRating.visibility = GONE
    }

}