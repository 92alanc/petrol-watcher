package com.braincorp.petrolwatcher.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.listeners.OnFragmentInflatedListener
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.model.PetrolStation

class PetrolStationDetailsFragment : Fragment(), AdaptableUi {

    companion object {
        private const val ARG_UI_MODE = "ui_mode"
        private const val ARG_PETROL_STATION = "petrol_station"

        fun newInstance(uiMode: AdaptableUi.Mode,
                        onFragmentInflatedListener: OnFragmentInflatedListener,
                        petrolStation: PetrolStation? = null)
                : PetrolStationDetailsFragment {
            val instance = PetrolStationDetailsFragment()
            val args = Bundle()
            args.putSerializable(ARG_UI_MODE, uiMode)
            args.putParcelable(ARG_PETROL_STATION, petrolStation)
            instance.arguments = args
            instance.onFragmentInflatedListener = onFragmentInflatedListener
            return instance
        }
    }

    private var onFragmentInflatedListener: OnFragmentInflatedListener? = null
    private var petrolStation: PetrolStation? = null
    private var uiMode = AdaptableUi.Mode.INITIAL

    private lateinit var buttonDelete: ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_petrol_station_details, container,
                false)
        bindViews(view)
        parseArgs()
        prepareUi()
        onFragmentInflatedListener?.onFragmentInflated(this)
        return view
    }

    override fun prepareInitialMode() {
        TODO("not implemented")
    }

    override fun prepareCreateMode() {
        TODO("not implemented")
    }

    override fun prepareEditMode() {
        TODO("not implemented")
    }

    override fun prepareViewMode() {
        TODO("not implemented")
    }

    fun getPetrolStation(): PetrolStation {
        TODO("not implemented")
    }

    fun setDeleteButtonClickListener(onClickListener: View.OnClickListener) {
        buttonDelete.setOnClickListener(onClickListener)
    }

    private fun bindViews(view: View) {
        buttonDelete = view.findViewById(R.id.buttonDelete)
    }

    private fun parseArgs() {
        uiMode = arguments?.getSerializable(ARG_UI_MODE) as AdaptableUi.Mode
        petrolStation = arguments?.getParcelable(ARG_PETROL_STATION)
    }

    private fun prepareUi() {
        when (uiMode) {
            AdaptableUi.Mode.INITIAL -> prepareInitialMode()
            AdaptableUi.Mode.CREATE -> prepareCreateMode()
            AdaptableUi.Mode.EDIT -> prepareEditMode()
            AdaptableUi.Mode.VIEW -> prepareViewMode()
        }
    }

}