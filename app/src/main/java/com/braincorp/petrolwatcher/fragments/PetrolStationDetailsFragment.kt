package com.braincorp.petrolwatcher.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.adapters.FuelPriceAdapter
import com.braincorp.petrolwatcher.adapters.RatingAdapter
import com.braincorp.petrolwatcher.model.*
import com.braincorp.petrolwatcher.utils.fuelFloatMapToString
import com.braincorp.petrolwatcher.utils.ratingToColour
import com.braincorp.petrolwatcher.utils.ratingToString

class PetrolStationDetailsFragment : Fragment(), View.OnClickListener {

    companion object {
        private const val KEY_PETROL_STATION = "petrol_station"
        private const val KEY_UI_MODE = "ui_mode"

        fun newInstance(petrolStation: PetrolStation? = null,
                        uiMode: UiMode): PetrolStationDetailsFragment {
            val instance = PetrolStationDetailsFragment()
            val args = Bundle()
            args.putParcelable(KEY_PETROL_STATION, petrolStation)
            args.putSerializable(KEY_UI_MODE, uiMode)
            instance.arguments = args
            return instance
        }
    }

    private lateinit var textViewName: TextView
    private lateinit var textViewAddress: TextView
    private lateinit var divider: View
    private lateinit var textViewPrices: TextView
    private lateinit var textViewRating: TextView

    private lateinit var editTextName: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var recyclerViewPrices: RecyclerView
    private lateinit var buttonAdd: FloatingActionButton
    private lateinit var spinnerRating: Spinner

    private var prices: MutableList<Map.Entry<Pair<FuelType, FuelQuality>, Float>> = mutableListOf()
    private var petrolStation: PetrolStation? = null
    private var uiMode: UiMode = UiMode.VIEW

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_petrol_station_details, container,
                false)
        bindViews(view)
        parseArgs()
        prepareUi()
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonAdd -> addPrice()
        }
    }

    fun getData(): PetrolStation {
        if (uiMode == UiMode.VIEW) return petrolStation!!
        else if (uiMode == UiMode.CREATE) petrolStation = PetrolStation()

        petrolStation!!.name = editTextName.text.toString()
        petrolStation!!.address = editTextAddress.text.toString()

        val adapter = recyclerViewPrices.adapter as FuelPriceAdapter
        petrolStation!!.prices = adapter.getData()

        petrolStation!!.rating = spinnerRating.selectedItem as Rating

        return petrolStation!!
    }

    private fun bindViews(view: View) {
        textViewName = view.findViewById(R.id.textViewName)
        textViewAddress = view.findViewById(R.id.textViewAddress)
        divider = view.findViewById(R.id.divider)
        textViewPrices = view.findViewById(R.id.textViewPrices)
        textViewRating = view.findViewById(R.id.textViewRating)

        editTextName = view.findViewById(R.id.editTextPetrolStationName)
        editTextAddress = view.findViewById(R.id.editTextAddress)
        recyclerViewPrices = view.findViewById(R.id.priceMapView)
        buttonAdd = view.findViewById(R.id.buttonAdd)
        spinnerRating = view.findViewById(R.id.spinnerRating)
    }

    private fun parseArgs() {
        petrolStation = arguments?.getParcelable(KEY_PETROL_STATION) as PetrolStation
        uiMode = arguments?.getSerializable(KEY_UI_MODE) as UiMode
        if (petrolStation != null) prices = petrolStation!!.prices.entries.toMutableList()
    }

    private fun prepareUi() {
        when (uiMode) {
            UiMode.CREATE -> prepareCreateMode()
            UiMode.EDIT -> prepareEditMode()
            UiMode.VIEW -> prepareViewMode()
        }
    }

    private fun prepareCreateMode() {
        hideNotEditableFields()
        showEditableFields()
        fillNotEditableFields()
    }

    private fun prepareEditMode() {
        hideNotEditableFields()
        showEditableFields()
        fillEditableFields()
    }

    private fun prepareViewMode() {
        hideEditableFields()
        showNotEditableFields()
    }

    private fun showEditableFields() {
        editTextName.visibility = VISIBLE
        editTextAddress.visibility = VISIBLE
        recyclerViewPrices.visibility = VISIBLE
        spinnerRating.visibility = VISIBLE
        spinnerRating.adapter = RatingAdapter(context!!)
    }

    private fun hideEditableFields() {
        editTextName.visibility = GONE
        editTextAddress.visibility = GONE
        recyclerViewPrices.visibility = GONE
        spinnerRating.visibility = GONE
    }

    private fun showNotEditableFields() {
        textViewName.visibility = VISIBLE
        textViewAddress.visibility = VISIBLE
        divider.visibility = VISIBLE
        textViewPrices.visibility = VISIBLE
        textViewRating.visibility = VISIBLE
    }

    private fun hideNotEditableFields() {
        textViewName.visibility = GONE
        textViewAddress.visibility = GONE
        divider.visibility = GONE
        textViewPrices.visibility = GONE
        textViewRating.visibility = GONE
    }

    private fun fillEditableFields() {
        editTextName.setText(petrolStation!!.name)
        editTextAddress.setText(petrolStation!!.address)
        recyclerViewPrices.adapter = FuelPriceAdapter(context!!, prices)
        spinnerRating.setSelection(petrolStation!!.rating.ordinal)
    }

    private fun fillNotEditableFields() {
        textViewName.text = petrolStation!!.name
        textViewAddress.text = petrolStation!!.address
        textViewPrices.text = context!!.fuelFloatMapToString(petrolStation!!.prices)
        textViewRating.text = context!!.ratingToString(petrolStation!!.rating)
        textViewRating.setTextColor(context!!.ratingToColour(petrolStation!!.rating))
    }

    private fun addPrice() {
        // TODO("not implemented")
    }

}