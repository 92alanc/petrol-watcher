package com.braincorp.petrolwatcher.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.braincorp.petrolwatcher.R

/**
 * A generic spinner adapter
 */
class GenericSpinnerAdapter<T>(context: Context, private val data: List<T>)
    : ArrayAdapter<T>(context, R.layout.generic_spinner_item,
                      R.id.txt_spinner_item, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val row = getDropDownView(position, convertView, parent)
        return row.findViewById(R.id.txt_spinner_item)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val row = inflater.inflate(R.layout.generic_spinner_item, parent, false)

        val value = data[position]
        val textView = row.findViewById<TextView>(R.id.txt_spinner_item)
        textView.text = value.toString()

        return row
    }

}