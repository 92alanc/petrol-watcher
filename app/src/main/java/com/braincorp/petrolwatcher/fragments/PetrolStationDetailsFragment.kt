package com.braincorp.petrolwatcher.fragments

import android.app.Fragment
import android.os.Bundle
import android.support.constraint.Group
import android.util.Log
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
import com.braincorp.petrolwatcher.utils.ratingToColour
import com.braincorp.petrolwatcher.utils.ratingToString
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener

class PetrolStationDetailsFragment : Fragment(), AdaptableUi,
        View.OnClickListener, PlaceSelectionListener {

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

    private var address: String? = null
    private var petrolStation: PetrolStation? = null
    private var rootView: View? = null
    private var uiMode = AdaptableUi.Mode.INITIAL

    private lateinit var textViewName: TextView
    private lateinit var textViewAddress: TextView
    private lateinit var textViewRating: TextView

    private lateinit var editTextName: EditText
    private lateinit var placeAutocompleteAddress: PlaceAutocompleteFragment

    private lateinit var buttonLocate: ImageButton
    private lateinit var buttonCurrentLocation: ImageButton

    private lateinit var groupEditableFields: Group
    private lateinit var groupNotEditableFields: Group

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (rootView != null) {
            val parent = rootView!!.parent as ViewGroup
            parent.removeView(rootView)
        }

        rootView = inflater.inflate(R.layout.fragment_petrol_station_details, container,
                    false)
        bindViews(rootView!!)
        parseArgs()
        prepareUi()

        return rootView
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonLocate -> TODO("not implemented")
            R.id.buttonCurrentLocation -> TODO("not implemented")
        }
    }

    override fun onPlaceSelected(place: Place?) {
        address = place?.address?.toString()
    }

    override fun onError(status: Status?) {
        Log.e(javaClass.name, status?.statusMessage)
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

    fun getAddress() = address

    private fun bindViews(view: View) {
        textViewName = view.findViewById(R.id.textViewName)
        textViewAddress = view.findViewById(R.id.textViewAddress)
        textViewRating = view.findViewById(R.id.textViewRating)

        editTextName = view.findViewById(R.id.editTextName)

        bindPlaceAutocompleteFragment()

        buttonLocate = view.findViewById(R.id.buttonLocate)
        buttonLocate.setOnClickListener(this)

        buttonCurrentLocation = view.findViewById(R.id.buttonCurrentLocation)
        buttonCurrentLocation.setOnClickListener(this)

        groupEditableFields = view.findViewById(R.id.groupEditableFields)
        groupNotEditableFields = view.findViewById(R.id.groupNotEditableFields)
    }

    private fun bindPlaceAutocompleteFragment() { // TODO: check compatibility with API 17
        placeAutocompleteAddress = childFragmentManager
                .findFragmentById(R.id.placeAutocompleteAddress) as PlaceAutocompleteFragment
        placeAutocompleteAddress.setOnPlaceSelectedListener(this)
        placeAutocompleteAddress.setHint(activity.getString(R.string.address))
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
        placeAutocompleteAddress.setText(petrolStation?.address)
    }

    private fun showRating() {
        textViewRating.visibility = VISIBLE
        textViewRating.text = activity.ratingToString(petrolStation!!.rating)
        textViewRating.setTextColor(activity.ratingToColour(petrolStation!!.rating))
    }

    private fun hideRating() {
        textViewRating.visibility = GONE
    }

    private fun showAddress() {
        val baseUrl = "https://www.google.com/maps/dir/?api=1"
        val origin = "&origin=${0}"
        val destination = "&destination=${0}"
        val mode = "&travelmode=driving"
    }

}