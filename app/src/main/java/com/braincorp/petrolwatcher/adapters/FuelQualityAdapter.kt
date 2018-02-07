package com.braincorp.petrolwatcher.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.FuelQuality
import com.braincorp.petrolwatcher.utils.fuelQualityToUserFriendlyString

class FuelQualityAdapter(context: Context) : ArrayAdapter<FuelQuality>(context,
        R.layout.item_fuel_quality, R.id.textViewFuelQuality, FuelQuality.values()) {

    private val values = FuelQuality.values()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val row = getDropDownView(position, convertView, parent)
        return row.findViewById(R.id.textViewFuelQuality)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val row = inflater.inflate(R.layout.item_fuel_quality, parent, false)

        val textView = row.findViewById<TextView>(R.id.textViewFuelQuality)
        textView.text = context.fuelQualityToUserFriendlyString(values[position])

        return row
    }

}