package com.braincorp.petrolwatcher.feature.vehicles.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.AdaptableUi

class VehicleDetailsNewFragment : Fragment(), AdaptableUi {

    private var uiMode = AdaptableUi.Mode.INITIAL

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_vehicle_details_new, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareUi()
    }

    override fun prepareInitialMode() {

    }

    override fun prepareCreateMode() {

    }

    override fun prepareEditMode() {

    }

    override fun prepareViewMode() {

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