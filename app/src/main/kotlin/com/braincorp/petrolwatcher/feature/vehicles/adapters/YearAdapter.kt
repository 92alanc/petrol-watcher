package com.braincorp.petrolwatcher.feature.vehicles.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.braincorp.petrolwatcher.R

class YearAdapter(context: Context, private val objects: List<Int>)
    : ArrayAdapter<Int>(context, R.layout.item_spinner_simple, R.id.textView, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val row = getDropDownView(position, convertView, parent)
        return row.findViewById(R.id.textView)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val row = inflater.inflate(R.layout.item_spinner_simple, parent, false)

        val year = objects[position]
        val textView = row.findViewById<TextView>(R.id.textView)
        textView.text = year.toString()

        return row
    }

}