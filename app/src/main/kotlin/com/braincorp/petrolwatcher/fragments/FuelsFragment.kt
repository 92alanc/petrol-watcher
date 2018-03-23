package com.braincorp.petrolwatcher.fragments

import android.app.Activity.RESULT_OK
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.activities.FuelsActivity
import com.braincorp.petrolwatcher.adapters.FuelAdapter
import com.braincorp.petrolwatcher.listeners.OnItemClickListener
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.model.Fuel
import com.braincorp.petrolwatcher.model.PetrolStation
import java.util.*

class FuelsFragment : Fragment(), View.OnClickListener, OnItemClickListener, AdaptableUi {

    companion object {
        private const val ARG_PETROL_STATION = "petrol_station"
        private const val ARG_UI_MODE = "ui_mode"

        private const val REQUEST_CODE_FUEL = 999

        fun newInstance(petrolStation: PetrolStation?,
                        uiMode: AdaptableUi.Mode): FuelsFragment {
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
    private lateinit var textViewNoPrices: TextView

    private var selectedFuel: Fuel? = null
    private var fuels: MutableSet<Fuel>? = null
    private var locale: Locale? = Locale.getDefault()
    private var uiMode = AdaptableUi.Mode.INITIAL

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fuels, container, false)
        bindViews(view)
        parseArgs()
        populateRecyclerView()
        prepareUi()
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_FUEL && resultCode == RESULT_OK) {
            selectedFuel = data!!.getParcelableExtra(FuelsActivity.EXTRA_FUEL)

            val isDuplicate = fuels!!.any {
                it.type == selectedFuel!!.type && it.quality == selectedFuel!!.quality
            }

            if (!isDuplicate) {
                fuels!!.add(selectedFuel!!)
            } else {
                val fuelToUpdate = fuels!!.find {
                    it.type == selectedFuel!!.type && it.quality == selectedFuel!!.quality
                }!!

                fuelToUpdate.price = selectedFuel!!.price
            }

            populateRecyclerView()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonAdd -> addFuel()
        }
    }

    override fun onItemClick(position: Int) {
        if (uiMode != AdaptableUi.Mode.EDIT) return
        selectedFuel = fuels!!.toList()[position]
        val intent = FuelsActivity.getIntent(context = activity, uiMode = AdaptableUi.Mode.EDIT)
        startActivityForResult(intent, REQUEST_CODE_FUEL)
    }

    override fun prepareInitialMode() {
        prepareViewMode()
    }

    override fun prepareCreateMode() {
        prepareEditMode()
    }

    override fun prepareEditMode() {
        uiMode = AdaptableUi.Mode.EDIT

        buttonAdd.visibility = VISIBLE
        textViewNoPrices.visibility = GONE
    }

    override fun prepareViewMode() {
        uiMode = AdaptableUi.Mode.VIEW

        buttonAdd.visibility = GONE

        textViewNoPrices.visibility = if (fuels == null || fuels!!.isEmpty()) VISIBLE
        else GONE
    }

    fun getFuels(): MutableSet<Fuel>? = fuels

    private fun prepareUi() {
        when (uiMode) {
            AdaptableUi.Mode.INITIAL -> prepareInitialMode()
            AdaptableUi.Mode.CREATE -> prepareCreateMode()
            AdaptableUi.Mode.EDIT -> prepareEditMode()
            AdaptableUi.Mode.VIEW -> prepareViewMode()
        }
    }

    private fun bindViews(view: View) {
        recyclerViewFuels = view.findViewById(R.id.recyclerViewFuels)
        val layoutManager = LinearLayoutManager(activity)
        recyclerViewFuels.layoutManager = layoutManager
        val divider = DividerItemDecoration(activity, layoutManager.orientation)
        recyclerViewFuels.addItemDecoration(divider)

        buttonAdd = view.findViewById(R.id.buttonAdd)
        buttonAdd.setOnClickListener(this)

        textViewNoPrices = view.findViewById(R.id.textViewNoPrices)
    }

    private fun parseArgs() {
        val petrolStation = arguments?.getParcelable<PetrolStation>(ARG_PETROL_STATION)
        fuels = petrolStation?.fuels
        locale = petrolStation?.locale
        uiMode = arguments?.getSerializable(ARG_UI_MODE) as AdaptableUi.Mode
    }

    private fun addFuel() {
        val intent = FuelsActivity.getIntent(context = activity, uiMode = AdaptableUi.Mode.CREATE)
        startActivityForResult(intent, REQUEST_CODE_FUEL)
    }

    private fun populateRecyclerView() {
        if (fuels == null) fuels = mutableSetOf()
        val adapter = FuelAdapter(activity, fuels!!, locale, onItemClickListener = this)
        recyclerViewFuels.adapter = adapter
    }

}