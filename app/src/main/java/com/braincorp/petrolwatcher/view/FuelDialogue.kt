package com.braincorp.petrolwatcher.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.View
import android.view.Window.FEATURE_NO_TITLE
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.adapters.FuelQualityAdapter
import com.braincorp.petrolwatcher.adapters.FuelTypeAdapter
import com.braincorp.petrolwatcher.model.Fuel

class FuelDialogue(context: Context, private val fuel: Fuel? = null) : Dialog(context),
        View.OnClickListener {

    private lateinit var spinnerFuelType: Spinner
    private lateinit var spinnerFuelQuality: Spinner
    private lateinit var editTextPrice: EditText
    private lateinit var textViewOk: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.dialogue_fuel)
        bindViews()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textViewOk -> dismiss()
        }
    }

    fun getPrice(): Float {
        return if (!isEmpty(editTextPrice.text.toString())) {
            editTextPrice.text.toString().toFloat()
        } else {
            0f
        }
    }

    fun setPrice(price: Float) {
        editTextPrice.setText(price.toString())
    }

    fun getFuelType(): Fuel.Type {
        return spinnerFuelType.selectedItem as Fuel.Type
    }

    fun setFuelType(fuelType: Fuel.Type) {
        spinnerFuelType.setSelection(fuel!!.type.ordinal)
    }

    fun getFuelQuality(): Fuel.Quality {
        return spinnerFuelQuality.selectedItem as Fuel.Quality
    }

    fun setFuelQuality(fuelQuality: Fuel.Quality) {
        spinnerFuelQuality.setSelection(fuel!!.quality.ordinal)
    }

    private fun bindViews() {
        spinnerFuelType = findViewById(R.id.spinnerFuelType)
        val fuelTypeAdapter = FuelTypeAdapter(context)
        spinnerFuelType.adapter = fuelTypeAdapter

        spinnerFuelQuality = findViewById(R.id.spinnerFuelQuality)
        val fuelQualityAdapter = FuelQualityAdapter(context)
        spinnerFuelQuality.adapter = fuelQualityAdapter

        editTextPrice = findViewById(R.id.editTextPrice)

        textViewOk = findViewById(R.id.textViewOk)
        textViewOk.setOnClickListener(this)
    }

}