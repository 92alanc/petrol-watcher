package com.braincorp.petrolwatcher.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.Rating
import com.braincorp.petrolwatcher.utils.ratingToColour
import com.braincorp.petrolwatcher.utils.ratingToString

class RatingAdapter(context: Context) : ArrayAdapter<Rating>(context,
        R.layout.item_rating, R.id.textViewRating, Rating.values()) {

    private val values = Rating.values()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val row = getDropDownView(position, convertView, parent)
        return row.findViewById(R.id.textViewRating)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val row = inflater.inflate(R.layout.item_rating, parent, false)

        val textView = row.findViewById<TextView>(R.id.textViewRating)
        val rating = values[position]
        textView.text = context.ratingToString(rating)

        val colour = context.ratingToColour(rating)
        textView.setTextColor(colour)

        return row
    }

}