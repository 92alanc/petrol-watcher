package com.braincorp.petrolwatcher.feature.stations.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.stations.model.Fuel
import com.braincorp.petrolwatcher.ui.OnItemClickListener
import com.braincorp.petrolwatcher.utils.formatPriceAsCurrency
import java.util.*

/**
 * The adapter for fuel RecyclerView items
 */
class FuelAdapter(private val onItemClickListener: OnItemClickListener?,
                  private val locale: Locale,
                  private val data: MutableSet<Fuel>) : RecyclerView.Adapter<FuelAdapter.FuelHolder>() {

    constructor(locale: Locale, data: MutableSet<Fuel>): this(null, locale, data)

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FuelHolder {
        this.context = parent.context
        val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.item_fuel, parent, false)
        return FuelHolder(onItemClickListener, itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FuelHolder, position: Int) {
        val fuel = data.elementAt(position)
        holder.txtFuel.text = "${context.getString(fuel.type.stringRes)} (${context.getString(fuel.quality.stringRes)})"
        holder.txtPrice.text = formatPriceAsCurrency(fuel.price, locale)
    }

    override fun getItemCount(): Int = data.size

    class FuelHolder(private val onItemClickListener: OnItemClickListener?,
                     itemView: View) : RecyclerView.ViewHolder(itemView) {

        constructor(itemView: View): this(null, itemView)

        val txtFuel: TextView = itemView.findViewById(R.id.txt_fuel)
        val txtPrice: TextView = itemView.findViewById(R.id.txt_price)

        init {
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(adapterPosition)
            }
        }



    }

}