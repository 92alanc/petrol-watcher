package com.braincorp.petrolwatcher.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.listeners.OnItemClickListener
import com.braincorp.petrolwatcher.model.Vehicle
import com.braincorp.petrolwatcher.utils.fuelTypeListToString
import com.braincorp.petrolwatcher.utils.vehicleTypeToDrawable

class VehicleAdapter(private val context: Context,
                     private val items: ArrayList<Vehicle>,
                     private val onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<VehicleAdapter.VehicleHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VehicleHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_vehicle, parent, false)
        return VehicleHolder(onItemClickListener, view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VehicleHolder?, position: Int) {
        val vehicle = items[position]

        val drawable = context.vehicleTypeToDrawable(vehicle.vehicleType)
        holder?.imageViewVehicleType?.setImageDrawable(drawable)

        holder?.textViewManufacturerAndName?.text = "${vehicle.manufacturer} ${vehicle.name}"
        holder?.textViewFuelTypes?.text = context.fuelTypeListToString(vehicle.fuelTypes)
    }

    class VehicleHolder(private val onItemClickListener: OnItemClickListener,
                        itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val imageViewVehicleType: ImageView
        val textViewManufacturerAndName: TextView
        val textViewFuelTypes: TextView

        init {
            itemView.setOnClickListener(this)

            imageViewVehicleType = itemView.findViewById(R.id.imageViewVehicleType)
            textViewManufacturerAndName = itemView.findViewById(R.id.textViewManufacturerAndName)
            textViewFuelTypes = itemView.findViewById(R.id.textViewFuelTypes)
        }

        override fun onClick(v: View?) {
            onItemClickListener.onItemClick(layoutPosition)
        }

    }

}