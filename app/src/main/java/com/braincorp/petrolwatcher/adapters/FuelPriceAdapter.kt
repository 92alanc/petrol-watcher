package com.braincorp.petrolwatcher.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.FuelQuality
import com.braincorp.petrolwatcher.model.FuelType

class FuelPriceAdapter(private val context: Context,
                       private val data: MutableList<Map.Entry<Pair<FuelType, FuelQuality>, Float>>)
    : RecyclerView.Adapter<FuelPriceAdapter.FuelPriceHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FuelPriceHolder {
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_fuel_price, parent, false)
        return FuelPriceHolder(itemView)
    }

    override fun onBindViewHolder(holder: FuelPriceHolder?, position: Int) {
        val price = data[position]
        holder?.spinnerFuelType?.adapter = FuelTypeAdapter(context)
        holder?.spinnerFuelQuality?.adapter = FuelQualityAdapter(context)
        if (price.value > 0) holder?.editTextPrice?.setText(price.value.toString())
        holder?.buttonRemove?.setOnClickListener {
            data.removeAt(position)
        }
    }

    override fun getItemCount(): Int = data.size

    fun getData(): Map<Pair<FuelType, FuelQuality>, Float> {
        val map = HashMap<Pair<FuelType, FuelQuality>, Float>()
        data.forEach {
            val type = it.key.first
            val quality = it.key.second
            val price = it.value
            map[Pair(type, quality)] = price
        }
        return map
    }

    class FuelPriceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val spinnerFuelType: Spinner = itemView.findViewById(R.id.spinnerFuelType)
        val spinnerFuelQuality: Spinner = itemView.findViewById(R.id.spinnerFuelQuality)
        val editTextPrice: EditText = itemView.findViewById(R.id.editTextPrice)
        val buttonRemove: ImageButton = itemView.findViewById(R.id.buttonRemove)

    }

}