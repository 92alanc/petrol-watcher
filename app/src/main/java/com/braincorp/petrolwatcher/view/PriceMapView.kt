package com.braincorp.petrolwatcher.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.braincorp.petrolwatcher.R

class PriceMapView(context: Context,
                   attrs: AttributeSet): ConstraintLayout(context, attrs) {

    private var fieldCount: Int = 1

    init {
        inflate(context, R.layout.view_price_map, this)
        parseAttrs(attrs)
    }

    private fun parseAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PriceMapView)
        fieldCount = typedArray.getInt(R.styleable.PriceMapView_field_count, 1)
        typedArray.recycle()
    }

}