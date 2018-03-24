package com.braincorp.petrolwatcher.feature.petrolstations.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.petrolstations.model.Fuel
import com.braincorp.petrolwatcher.utils.fuelTypeToString

class FuelTypeAdapter(context: Context) : ArrayAdapter<Fuel.Type>(context,
                                                                  R.layout.item_spinner_simple,
                                                                  R.id.textView,
                                                                  Fuel.Type.values()) {

    private val items = Fuel.Type.values()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val row = getDropDownView(position, convertView, parent)
        return row.findViewById(R.id.textView)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val row = inflater.inflate(R.layout.item_spinner_simple, parent, false)

        val fuelType = items[position]
        val textView = row.findViewById<TextView>(R.id.textView)
        textView.text = context.fuelTypeToString(fuelType)

        return row
    }

}