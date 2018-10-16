package com.braincorp.petrolwatcher.feature.stations.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.braincorp.petrolwatcher.ui.OnItemClickListener
import com.google.android.gms.maps.model.LatLng

/**
 * The adapter for the petrol station RecyclerView
 */
class PetrolStationAdapter(private val data: ArrayList<PetrolStation>,
                           private val hasLocationPermission: Boolean,
                           private val currentLocation: LatLng?,
                           private val onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<PetrolStationAdapter.PetrolStationHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetrolStationHolder {
        this.context = parent.context
        val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.item_petrol_station, parent, false)
        return PetrolStationHolder(onItemClickListener, itemView)
    }

    override fun onBindViewHolder(holder: PetrolStationHolder, position: Int) {
        val petrolStation = data[position]
        holder.txtName.text = petrolStation.name

        holder.txtDistance.visibility = if (hasLocationPermission) {
            if (currentLocation != null) {
                val distance = DependencyInjection.mapController.getDistanceInMetres(currentLocation,
                        petrolStation.latLng)

                if (distance >= 1000)
                    holder.txtDistance.text = context.getString(R.string.distance_km_format, (distance / 1000))
                else
                    holder.txtDistance.text = context.getString(R.string.distance_m_format, distance)

                VISIBLE
            } else {
                GONE
            }
        } else {
            GONE
        }

        holder.ratingBar.rating = petrolStation.rating.toFloat()
        val ratingCountText = context.resources.getQuantityText(R.plurals.rated_by_format,
                petrolStation.ratingCount).toString()
        holder.txtRatingCount.text = String.format(ratingCountText, petrolStation.ratingCount)
    }

    override fun getItemCount(): Int = data.size

    class PetrolStationHolder(private val onItemClickListener: OnItemClickListener,
                              itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        val txtName: TextView = itemView.findViewById(R.id.txt_name)
        val txtDistance: TextView = itemView.findViewById(R.id.txt_distance)
        val ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar)
        val txtRatingCount: TextView = itemView.findViewById(R.id.txt_rating_count)

        override fun onClick(v: View) {
            onItemClickListener.onItemClick(adapterPosition)
        }

    }

}