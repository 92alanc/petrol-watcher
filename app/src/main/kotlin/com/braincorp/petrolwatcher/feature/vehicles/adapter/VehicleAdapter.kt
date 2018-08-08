package com.braincorp.petrolwatcher.feature.vehicles.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.vehicles.listeners.OnItemRemovedListener
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle

class VehicleAdapter(private val data: ArrayList<Vehicle>)
    : RecyclerView.Adapter<VehicleAdapter.VehicleHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleHolder {
        val context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.item_vehicle, parent, false)
        return VehicleHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VehicleHolder, position: Int) {
        val vehicle = data[position]
        holder.txtModel.text = "${vehicle.manufacturer} ${vehicle.model} ${vehicle.trimLevel}"
        holder.txtYear.text = vehicle.year.toString()
    }

    override fun getItemCount(): Int = data.size

    /**
     * Removes an item at a given position
     *
     * @param position the position
     */
    fun removeAt(position: Int, onItemRemovedListener: OnItemRemovedListener<Vehicle>) {
        onItemRemovedListener.onItemRemoved(data[position])
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    class VehicleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtModel: TextView = itemView.findViewById(R.id.txt_model)
        val txtYear: TextView = itemView.findViewById(R.id.txt_year)

    }

}