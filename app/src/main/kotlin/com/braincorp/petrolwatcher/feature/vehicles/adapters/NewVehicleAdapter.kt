package com.braincorp.petrolwatcher.feature.vehicles.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.vehicles.model.NewVehicleModel
import com.braincorp.petrolwatcher.listeners.OnItemClickListener

class NewVehicleAdapter(private val context: Context,
                        private val data: ArrayList<NewVehicleModel>,
                        private val onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<NewVehicleAdapter.VehicleHolder>() {

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_vehicle_new, parent, false)
        return VehicleHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: VehicleHolder, position: Int) {
        val vehicle = data[position]
        var manufacturer = vehicle.manufacturer
        manufacturer = manufacturer.replace(manufacturer[0], manufacturer[0].toUpperCase())
        val manufacturerAndName = "$manufacturer ${vehicle.name}"

        holder.textViewManufacturerAndName.text = manufacturerAndName
        holder.textViewYear.text = vehicle.year.toString()
    }

    class VehicleHolder(itemView: View, private val onItemClickListener: OnItemClickListener)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val textViewManufacturerAndName: TextView
        val textViewYear: TextView

        init {
            itemView.setOnClickListener(this)

            textViewManufacturerAndName = itemView.findViewById(R.id.textViewManufacturerAndName)
            textViewYear = itemView.findViewById(R.id.textViewYear)
        }

        override fun onClick(v: View) {
            onItemClickListener.onItemClick(layoutPosition)
        }

    }

}