package com.braincorp.petrolwatcher.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.constraint.ConstraintSet.WRAP_CONTENT
import android.support.v4.content.ContextCompat
import android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import com.braincorp.petrolwatcher.R
import kotlinx.android.synthetic.main.view_price_map.view.*

class PriceMapView(context: Context, attrs: AttributeSet)
    : ConstraintLayout(context, attrs), View.OnClickListener {

    private val fuelTypeSpinners = ArrayList<Spinner>()
    private val fuelQualitySpinners = ArrayList<Spinner>()
    private val editTexts = ArrayList<EditText>()
    private val addButtons = ArrayList<ImageButton>()
    private val removeButtons = ArrayList<ImageButton>()

    private var fieldCount: Int = 1
    private var selectedViewPosition: Int = 0

    init {
        inflate(context, R.layout.view_price_map, this)
        bindViews()
        fuelTypeSpinners.add(spinnerFuelType)
        fuelQualitySpinners.add(spinnerFuelQuality)
        editTexts.add(editTextPrice)
        addButtons.add(buttonAdd)
        removeButtons.add(buttonRemove)

        parseAttrs(attrs)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonAdd -> addField()
            R.id.buttonRemove -> if (fieldCount > 1) removeField()
        }
    }

    private fun bindViews() {

    }

    private fun parseAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PriceMapView)
        fieldCount = typedArray.getInt(R.styleable.PriceMapView_field_count, 1)
        typedArray.recycle()
    }

    private fun addField() {
        fieldCount++
        addFuelTypeSpinner()
        addFuelQualitySpinner()
        addEditText()
        addRemoveButton()
        addAddButton()
    }

    private fun removeField() {
        fieldCount--
        removeFuelTypeSpinner()
        removeFuelQualitySpinner()
        removeEditText()
        removeRemoveButton()
        removeAddButton()
    }

    private fun addFuelTypeSpinner() {
        val spinner = Spinner(context)

        fuelTypeSpinners.add(spinner)
    }

    private fun addFuelQualitySpinner() {
        val spinner = Spinner(context)

        fuelQualitySpinners.add(spinner)
    }

    private fun addEditText() {
        val editText = EditText(context)
        editText.inputType = TYPE_NUMBER_FLAG_DECIMAL
        editText.hint = context.getString(R.string.price)

        editTexts.add(editText)
    }

    private fun addRemoveButton() {
        val button = ImageButton(context)

        val layoutParams = ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.topToTop = PARENT_ID
        layoutParams.rightToRight = PARENT_ID

        button.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        button.setImageResource(R.drawable.ic_remove)

        removeButtons.add(button)
    }

    private fun addAddButton() {
        val button = ImageButton(context)
        button.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        button.setImageResource(R.drawable.ic_add)

        addButtons.add(button)
    }

    private fun removeFuelTypeSpinner() {

    }

    private fun removeFuelQualitySpinner() {

    }

    private fun removeEditText() {

    }

    private fun removeRemoveButton() {

    }

    private fun removeAddButton() {

    }

}