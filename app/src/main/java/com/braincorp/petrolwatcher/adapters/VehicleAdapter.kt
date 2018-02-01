package com.braincorp.petrolwatcher.adapters

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
import com.braincorp.petrolwatcher.utils.vehicleTypeToDrawable

class VehicleAdapter(private val context: Context,
                     private val items: Array<Vehicle>,
                     private val onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<VehicleAdapter.VehicleHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VehicleHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_vehicle, parent, false)
        return VehicleHolder(onItemClickListener, view)
    }

    override fun onBindViewHolder(holder: VehicleHolder?, position: Int) {
        val vehicle = items[position]

        val drawable = context.vehicleTypeToDrawable(vehicle.vehicleType)
        holder?.imageViewVehicleType?.setImageDrawable(drawable)

        holder?.textViewManufacturer?.text = vehicle.manufacturer
        holder?.textViewName?.text = vehicle.name
    }

    class VehicleHolder(private val onItemClickListener: OnItemClickListener,
                        itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val imageViewVehicleType: ImageView
        val textViewManufacturer: TextView
        val textViewName: TextView

        init {
            itemView.setOnClickListener(this)

            imageViewVehicleType = itemView.findViewById(R.id.imageViewVehicleType)
            textViewManufacturer = itemView.findViewById(R.id.textViewManufacturer)
            textViewName = itemView.findViewById(R.id.textViewVehicleName)
        }

        override fun onClick(v: View?) {
            onItemClickListener.onItemClick(layoutPosition)
        }

    }

}