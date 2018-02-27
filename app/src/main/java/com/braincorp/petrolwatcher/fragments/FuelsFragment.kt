package com.braincorp.petrolwatcher.fragments

import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.model.Fuel
import com.braincorp.petrolwatcher.model.PetrolStation

class FuelsFragment : Fragment(), AdaptableUi, View.OnClickListener {

    companion object {
        private const val ARG_PETROL_STATION = "petrol_station"
        private const val ARG_UI_MODE = "ui_mode"

        fun newInstance(uiMode: AdaptableUi.Mode,
                        petrolStation: PetrolStation? = null): FuelsFragment {
            val instance = FuelsFragment()
            val args = Bundle()

            args.putParcelable(ARG_PETROL_STATION, petrolStation)
            args.putSerializable(ARG_UI_MODE, uiMode)

            instance.arguments = args
            return instance
        }
    }

    private lateinit var recyclerViewFuels: RecyclerView
    private lateinit var buttonAdd: Button

    private var petrolStation: PetrolStation? = null
    private var uiMode = AdaptableUi.Mode.INITIAL

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fuels, container, false)
        bindViews(view)
        parseArgs()
        prepareUi()
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonAdd -> addFuel()
        }
    }

    override fun prepareInitialMode() {
        prepareViewMode()
    }

    override fun prepareCreateMode() {
        recyclerViewFuels.visibility = GONE
    }

    override fun prepareEditMode() {
        prepareViewMode()
    }

    override fun prepareViewMode() {
        populateRecyclerView()
    }

    fun getFuels(): MutableSet<Fuel>? = petrolStation?.fuels

    private fun bindViews(view: View) {
        recyclerViewFuels = view.findViewById(R.id.recyclerViewFuels)

        buttonAdd = view.findViewById(R.id.buttonAdd)
        buttonAdd.setOnClickListener(this)
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

    private fun addFuel() {

    }

    private fun populateRecyclerView() {
        recyclerViewFuels.visibility = VISIBLE
    }

}