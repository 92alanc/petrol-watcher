package com.braincorp.petrolwatcher.feature.vehicles.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle

/**
 * The adapter for the vehicle details spinner
 */
class VehicleDetailsAdapter(context: Context, private val data: ArrayList<Vehicle.Details>)
    : ArrayAdapter<Vehicle.Details>(context, R.layout.item_vehicle_details,
        R.id.txt_trim_level, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val row = getDropDownView(position, convertView, parent)
        return row.findViewById(R.id.txt_trim_level)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val row = inflater.inflate(R.layout.item_vehicle_details, parent, false)

        if (position % 2 == 1)
            row.setBackgroundColor(ContextCompat.getColor(context, R.color.grey))

        val value = data[position]
        val trimLevel = row.findViewById<TextView>(R.id.txt_trim_level)
        val fuelCapacity = row.findViewById<TextView>(R.id.txt_fuel_capacity)
        val avgConsumptionCity = row.findViewById<TextView>(R.id.txt_avg_consumption_city)
        val avgConsumptionMotorway = row.findViewById<TextView>(R.id.txt_avg_consumption_motorway)

        trimLevel.text = context.getString(R.string.trim_level_format, value.trimLevel)

        fuelCapacity.text = if (value.fuelCapacity <= 0)
            context.getString(R.string.capacity_format, "?")
        else
            context.getString(R.string.capacity_format, value.fuelCapacity.toString())

        avgConsumptionCity.text = if (value.avgConsumptionCity <= 0f)
            context.getString(R.string.avg_consumption_city_format, "?")
        else
            context.getString(R.string.avg_consumption_city_format,
                    String.format("%.2f", value.avgConsumptionCity))

        avgConsumptionMotorway.text = if (value.avgConsumptionMotorway <= 0f)
            context.getString(R.string.avg_consumption_motorway_format, "?")
        else
            context.getString(R.string.avg_consumption_motorway_format,
                    String.format("%.2f", value.avgConsumptionMotorway))

        return row
    }

}