package com.braincorp.petrolwatcher.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.UiMode
import com.braincorp.petrolwatcher.view.PriceMapView

class PetrolStationDetailsFragment : Fragment(), View.OnClickListener {

    companion object {
        private const val KEY_UI_MODE = "ui_mode"

        fun newInstance(uiMode: UiMode): PetrolStationDetailsFragment {
            val instance = PetrolStationDetailsFragment()
            val args = Bundle()
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
    private lateinit var priceMapView: PriceMapView
    private lateinit var spinnerRating: Spinner

    private var uiMode: UiMode = UiMode.VIEW

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_petrol_station_details, container,
                false)
        bindViews(view)
        parseArgs()
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonAdd -> TODO("not implemented")
        }
    }

    private fun bindViews(view: View) {
        textViewName = view.findViewById(R.id.textViewName)
        textViewAddress = view.findViewById(R.id.textViewAddress)
        divider = view.findViewById(R.id.divider)
        textViewPrices = view.findViewById(R.id.textViewPrices)
        textViewRating = view.findViewById(R.id.textViewRating)

        editTextName = view.findViewById(R.id.editTextPetrolStationName)
        editTextAddress = view.findViewById(R.id.editTextAddress)
        priceMapView = view.findViewById(R.id.priceMapView)
        spinnerRating = view.findViewById(R.id.spinnerRating)
    }

    private fun parseArgs() {
        uiMode = arguments?.getSerializable(KEY_UI_MODE) as UiMode
    }

}