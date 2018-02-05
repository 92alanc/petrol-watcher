package com.braincorp.petrolwatcher.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat.getColor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.listeners.OnItemClickListener
import com.braincorp.petrolwatcher.model.PetrolStation
import com.braincorp.petrolwatcher.model.Rating
import com.braincorp.petrolwatcher.utils.ratingToString

class PetrolStationAdapter(private val context: Context,
                           private val items: Array<PetrolStation>,
                           private val onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<PetrolStationAdapter.PetrolStationHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PetrolStationHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_petrol_station, parent, false)
        return PetrolStationHolder(onItemClickListener, view)
    }

    override fun onBindViewHolder(holder: PetrolStationHolder?, position: Int) {
        val petrolStation = items[position]
        setName(holder, petrolStation)
        setDistance(holder, petrolStation)
        setRating(holder, petrolStation)
    }

    private fun setName(holder: PetrolStationHolder?, petrolStation: PetrolStation) {
        holder?.textViewName?.text = petrolStation.name
    }

    @SuppressLint("SetTextI18n")
    private fun setDistance(holder: PetrolStationHolder?, petrolStation: PetrolStation) {
        holder?.textViewDistance?.text = "0.5 km"
        // TODO: implement the real thing
    }

    private fun setRating(holder: PetrolStationHolder?, petrolStation: PetrolStation) {
        val colourRes = when (petrolStation.rating) {
            Rating.VERY_BAD -> R.color.red_dark
            Rating.BAD -> R.color.red
            Rating.OK -> R.color.amber
            Rating.GOOD -> R.color.green_light
            Rating.VERY_GOOD -> R.color.green
        }
        holder?.textViewRating?.setTextColor(getColor(context, colourRes))
        holder?.textViewRating?.text = context.ratingToString(petrolStation.rating)
    }

    class PetrolStationHolder(private val onItemClickListener: OnItemClickListener,
                              itemView: View)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val textViewName: TextView
        val textViewDistance: TextView
        val textViewRating: TextView

        init {
            itemView.setOnClickListener(this)

            textViewName = itemView.findViewById(R.id.textViewName)
            textViewDistance = itemView.findViewById(R.id.textViewDistance)
            textViewRating = itemView.findViewById(R.id.textViewRating)
        }

        override fun onClick(v: View?) {
            onItemClickListener.onItemClick(layoutPosition)
        }

    }

}