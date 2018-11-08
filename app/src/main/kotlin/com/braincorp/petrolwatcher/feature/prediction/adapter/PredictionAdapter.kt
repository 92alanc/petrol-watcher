package com.braincorp.petrolwatcher.feature.prediction.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.prediction.model.Prediction
import com.braincorp.petrolwatcher.feature.stations.adapter.FuelAdapter
import com.braincorp.petrolwatcher.utils.formatPriceAsCurrency
import java.util.*

/**
 * The adapter for prediction RecyclerView items
 */
class PredictionAdapter(private val data: Prediction,
                        private val locale: Locale) : RecyclerView.Adapter<FuelAdapter.FuelHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FuelAdapter.FuelHolder {
        this.context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.item_fuel, parent, false)
        return FuelAdapter.FuelHolder(itemView)
    }

    override fun onBindViewHolder(holder: FuelAdapter.FuelHolder, position: Int) {
        val entry = data.prices.entries.toList()[position]
        var fuel = entry.key
        val price = entry.value

        fuel = when (fuel) {
            Prediction.KEY_ETHANOL -> context.getString(R.string.ethanol)
            Prediction.KEY_DIESEL -> context.getString(R.string.diesel)
            Prediction.KEY_REGULAR_PETROL -> context.getString(R.string.regular_petrol)
            Prediction.KEY_PREMIUM_PETROL -> context.getString(R.string.premium_petrol)
            else -> context.getString(R.string.regular_petrol)
        }

        holder.txtFuel.text = fuel
        holder.txtPrice.text = formatPriceAsCurrency(price, locale)
    }

    override fun getItemCount(): Int = data.prices.size

}