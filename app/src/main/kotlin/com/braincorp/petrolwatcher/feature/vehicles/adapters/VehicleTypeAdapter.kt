package com.braincorp.petrolwatcher.feature.vehicles.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.braincorp.petrolwatcher.utils.vehicleTypeToDrawable
import com.braincorp.petrolwatcher.utils.vehicleTypeToString

class VehicleTypeAdapter(context: Context)
    : ArrayAdapter<Vehicle.Type>(context, R.layout.item_vehicle_type, R.id.textViewVehicleType,
                                                                                    Vehicle.Type.values()) {

    private val values = Vehicle.Type.values()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val row = getDropDownView(position, convertView, parent)
        return row.findViewById(R.id.textViewVehicleType)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val row = inflater.inflate(R.layout.item_vehicle_type, parent, false)
        val vehicleType = values[position]

        val imageView = row.findViewById<ImageView>(R.id.imageViewVehicleType)
        imageView.setImageDrawable(context.vehicleTypeToDrawable(vehicleType))

        val textView = row.findViewById<TextView>(R.id.textViewVehicleType)
        textView.text = context.vehicleTypeToString(vehicleType)

        return row
    }

}