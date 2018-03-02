package com.braincorp.petrolwatcher.fragments

import android.app.Activity.RESULT_OK
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.activities.FuelsActivity
import com.braincorp.petrolwatcher.adapters.FuelAdapter
import com.braincorp.petrolwatcher.listeners.OnItemClickListener
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.model.Fuel

class FuelsFragment : Fragment(), View.OnClickListener, OnItemClickListener {

    companion object {
        private const val ARG_FUELS = "fuels"

        private const val REQUEST_CODE_FUEL = 999

        fun newInstance(fuels: MutableSet<Fuel>?): FuelsFragment {
            val instance = FuelsFragment()
            val args = Bundle()

            args.putParcelableArray(ARG_FUELS, fuels?.toTypedArray())

            instance.arguments = args
            return instance
        }
    }

    private lateinit var recyclerViewFuels: RecyclerView
    private lateinit var buttonAdd: Button

    private var selectedFuel: Fuel? = null
    private var fuels: MutableSet<Fuel>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fuels, container, false)
        bindViews(view)
        parseArgs()
        populateRecyclerView()
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_FUEL && resultCode == RESULT_OK) {
            selectedFuel = data!!.getParcelableExtra(FuelsActivity.EXTRA_FUEL)
            if (!fuels!!.contains(selectedFuel!!)) {
                fuels!!.add(selectedFuel!!)
            } else {
                val fuelToUpdate = fuels!!.find {
                    it.type == selectedFuel!!.type && it.quality == selectedFuel!!.quality
                }!!

                fuelToUpdate.price = selectedFuel!!.price
            }

            recyclerViewFuels.adapter.notifyDataSetChanged()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonAdd -> addFuel()
        }
    }

    override fun onItemClick(position: Int) {
        selectedFuel = fuels!!.toList()[position]
        val intent = FuelsActivity.getIntent(context = activity, uiMode = AdaptableUi.Mode.EDIT)
        startActivityForResult(intent, REQUEST_CODE_FUEL)
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
    }

    private fun addFuel() {
        val intent = FuelsActivity.getIntent(context = activity, uiMode = AdaptableUi.Mode.CREATE)
        startActivityForResult(intent, REQUEST_CODE_FUEL)
    }

    private fun populateRecyclerView() {
        if (fuels == null) fuels = mutableSetOf()
        val adapter = FuelAdapter(activity, fuels!!, onItemClickListener = this)
        recyclerViewFuels.adapter = adapter
    }

}