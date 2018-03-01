package com.braincorp.petrolwatcher.fragments

import android.app.Fragment
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.listeners.OnItemClickListener
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.model.Fuel
import com.braincorp.petrolwatcher.utils.showFuelDialogue
import com.braincorp.petrolwatcher.view.FuelDialogue

class FuelsFragment : Fragment(), AdaptableUi, View.OnClickListener,
        DialogInterface.OnDismissListener, OnItemClickListener {

    companion object {
        private const val ARG_FUELS = "fuels"
        private const val ARG_UI_MODE = "ui_mode"

        fun newInstance(uiMode: AdaptableUi.Mode,
                        fuels: MutableSet<Fuel>?): FuelsFragment {
            val instance = FuelsFragment()
            val args = Bundle()

            args.putParcelableArray(ARG_FUELS, fuels?.toTypedArray())
            args.putSerializable(ARG_UI_MODE, uiMode)

            instance.arguments = args
            return instance
        }
    }

    private lateinit var recyclerViewFuels: RecyclerView
    private lateinit var buttonAdd: Button

    private var fuel: Fuel? = null
    private var fuels: MutableSet<Fuel>? = null
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

    override fun onItemClick(position: Int) {
        fuel = fuels!!.toList()[position]
        activity.showFuelDialogue(fuel = fuel, onDismissListener = this)
    }

    override fun onDismiss(dialogue: DialogInterface) { // TODO: 99% sure it's not working
        val fuelDialogue = dialogue as FuelDialogue
        val type = fuelDialogue.getFuelType()
        val quality = fuelDialogue.getFuelQuality()
        val price = fuelDialogue.getPrice()

        if (fuel != null) {
            fuels!!.forEach {
                if (it.type == type && it.quality == quality) {
                    it.price = price
                }
            }
        } else {
            fuel = Fuel(type, quality, price)
            if (fuels == null) fuels = ArrayList<Fuel>().toMutableSet()

            if (fuels!!.none { f -> f.type == type && f.quality == quality }) {
                fuels!!.add(fuel!!)
                recyclerViewFuels.adapter.notifyDataSetChanged()
            }
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

    fun getFuels(): MutableSet<Fuel>? = fuels

    private fun bindViews(view: View) {
        recyclerViewFuels = view.findViewById(R.id.recyclerViewFuels)

        buttonAdd = view.findViewById(R.id.buttonAdd)
        buttonAdd.setOnClickListener(this)
    }

    @Suppress("UNCHECKED_CAST")
    private fun parseArgs() {
        val array = arguments?.getParcelableArray(ARG_FUELS)
        if (array != null)
            fuels = array.toMutableSet() as MutableSet<Fuel>
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
        activity.showFuelDialogue(onDismissListener = this)
    }

    private fun populateRecyclerView() {
        recyclerViewFuels.visibility = VISIBLE
    }

}