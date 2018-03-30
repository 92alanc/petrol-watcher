package com.braincorp.petrolwatcher.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.braincorp.petrolwatcher.R

class GenericSpinnerAdapter<T>(context: Context, private val data: List<T>)
    : ArrayAdapter<T>(context, R.layout.item_spinner_simple, R.id.textView, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val row = getDropDownView(position, convertView, parent)
        return row.findViewById(R.id.textView)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val row = inflater.inflate(R.layout.item_spinner_simple, parent, false)

        val value = data[position]
        val textView = row.findViewById<TextView>(R.id.textView)
        textView.text = value.toString()

        return row
    }

}