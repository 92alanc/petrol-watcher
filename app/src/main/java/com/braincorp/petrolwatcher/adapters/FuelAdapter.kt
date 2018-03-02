package com.braincorp.petrolwatcher.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.listeners.OnItemClickListener
import com.braincorp.petrolwatcher.model.Fuel
import com.braincorp.petrolwatcher.utils.floatToCurrencyString
import com.braincorp.petrolwatcher.utils.fuelQualityToString
import com.braincorp.petrolwatcher.utils.fuelTypeToString

class FuelAdapter(private val context: Context,
                  private val items: MutableSet<Fuel>,
                  private val onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<FuelAdapter.FuelHolder>() {

    private val list = items.toList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FuelHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_fuel, parent, false)
        return FuelHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: FuelHolder?, position: Int) {
        val fuel = list[position]

        @SuppressLint("SetTextI18n")
        holder?.textViewFuel?.text = "${context.fuelTypeToString(fuel.type)} (${context.fuelQualityToString(fuel.quality)}):"
        holder?.textViewPrice?.text = floatToCurrencyString(fuel.price)
    }

    override fun getItemCount(): Int = items.size

    class FuelHolder(itemView: View,
                     private val onItemClickListener: OnItemClickListener)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val textViewFuel: TextView = itemView.findViewById(R.id.textViewFuel)
        val textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemClickListener.onItemClick(layoutPosition)
        }

    }

}