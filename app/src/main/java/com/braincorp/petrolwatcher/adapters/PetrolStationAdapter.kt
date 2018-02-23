package com.braincorp.petrolwatcher.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.listeners.OnItemClickListener
import com.braincorp.petrolwatcher.model.PetrolStation
import com.braincorp.petrolwatcher.utils.ratingToColour
import com.braincorp.petrolwatcher.utils.ratingToString

class PetrolStationAdapter(private val context: Context,
                           private val items: ArrayList<PetrolStation>,
                           private val onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<PetrolStationAdapter.PetrolStationHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PetrolStationHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_petrol_station, parent, false)
        return PetrolStationHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: PetrolStationHolder?, position: Int) {
        val petrolStation = items[position]
        holder?.textViewName?.text = petrolStation.name
        holder?.textViewAddress?.text = petrolStation.address
        holder?.textViewRating?.text = context.ratingToString(petrolStation.rating)
        holder?.textViewRating?.setTextColor(context.ratingToColour(petrolStation.rating))
    }

    override fun getItemCount(): Int = items.size

    class PetrolStationHolder(itemView: View, private val onItemClickListener: OnItemClickListener)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewAddress: TextView = itemView.findViewById(R.id.textViewAddress)
        val textViewRating: TextView = itemView.findViewById(R.id.textViewRating)

        override fun onClick(v: View?) {
            onItemClickListener.onItemClick(layoutPosition)
        }

    }

}