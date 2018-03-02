package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils.isEmpty
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.adapters.FuelQualityAdapter
import com.braincorp.petrolwatcher.adapters.FuelTypeAdapter
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.model.Fuel
import kotlinx.android.synthetic.main.activity_fuels.*

class FuelsActivity : AppCompatActivity(), View.OnClickListener, AdaptableUi {

    companion object {
        const val EXTRA_FUEL = "fuel"

        private const val EXTRA_UI_MODE = "ui_mode"

        private const val KEY_FUEL_TYPE = "fuel_type"
        private const val KEY_FUEL_QUALITY = "fuel_quality"
        private const val KEY_PRICE = "price"

        fun getIntent(context: Context, fuel: Fuel? = null,
                      uiMode: AdaptableUi.Mode): Intent {
            return Intent(context, FuelsActivity::class.java).putExtra(EXTRA_FUEL, fuel)
                    .putExtra(EXTRA_UI_MODE, uiMode)
        }
    }

    private var fuel: Fuel? = null
    private var uiMode = AdaptableUi.Mode.INITIAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fuels)

        buttonOk.setOnClickListener(this)

        parseIntent()
        populateSpinners()

        if (savedInstanceState != null) parseSavedInstanceState(savedInstanceState)
        prepareUi()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(KEY_FUEL_TYPE, spinnerFuelType.selectedItem as Fuel.Type)
        outState?.putSerializable(KEY_FUEL_QUALITY, spinnerFuelQuality.selectedItem as Fuel.Quality)

        val stringValue = editTextPrice.text.toString()
        if (!isEmpty(stringValue)) outState?.putFloat(KEY_PRICE, stringValue.toFloat())
        else outState?.putFloat(KEY_PRICE, 0f)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        parseSavedInstanceState(savedInstanceState)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonOk -> save()
        }
    }

    override fun prepareInitialMode() {
        prepareCreateMode()
    }

    override fun prepareCreateMode() {
        editTextPrice.text = null
    }

    override fun prepareEditMode() {
        spinnerFuelType.setSelection(fuel!!.type.ordinal)
        spinnerFuelQuality.setSelection(fuel!!.quality.ordinal)
        editTextPrice.setText(fuel!!.price.toString())
    }

    override fun prepareViewMode() {
        prepareEditMode()
    }

    private fun parseIntent() {
        fuel = intent.getParcelableExtra(EXTRA_FUEL)
    }

    private fun parseSavedInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) return

        val fuelType = savedInstanceState.getSerializable(KEY_FUEL_TYPE) as Fuel.Type
        val fuelQuality = savedInstanceState.getSerializable(KEY_FUEL_QUALITY) as Fuel.Quality
        val price = savedInstanceState.getFloat(KEY_PRICE)

        spinnerFuelType.setSelection(fuelType.ordinal)
        spinnerFuelQuality.setSelection(fuelQuality.ordinal)
        if (price != 0f) editTextPrice.setText(price.toString())
    }

    private fun populateSpinners() {
        val fuelTypeAdapter = FuelTypeAdapter(context = this)
        spinnerFuelType.adapter = fuelTypeAdapter

        val fuelQualityAdapter = FuelQualityAdapter(context = this)
        spinnerFuelQuality.adapter = fuelQualityAdapter
    }

    private fun prepareUi() {
        when (uiMode) {
            AdaptableUi.Mode.INITIAL -> prepareInitialMode()
            AdaptableUi.Mode.CREATE -> prepareCreateMode()
            AdaptableUi.Mode.EDIT -> prepareEditMode()
            AdaptableUi.Mode.VIEW -> prepareViewMode()
        }
    }

    private fun save() {
        val fuelType = spinnerFuelType.selectedItem as Fuel.Type
        val fuelQuality = spinnerFuelQuality.selectedItem as Fuel.Quality
        val price = if (isEmpty(editTextPrice.text.toString())) 0f
        else editTextPrice.text.toString().toFloat()

        if (fuel == null) fuel = Fuel(fuelType, fuelQuality, price)

        val data = Intent().putExtra(EXTRA_FUEL, fuel)
        setResult(RESULT_OK, data)
        finish()
    }

}