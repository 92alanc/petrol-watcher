package com.braincorp.petrolwatcher.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import com.braincorp.petrolwatcher.listeners.OnFragmentInflatedListener
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.model.PetrolStation

class PetrolStationDetailsFragment : Fragment() {

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

}