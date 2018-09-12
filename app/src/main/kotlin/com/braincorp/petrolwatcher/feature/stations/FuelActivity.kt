package com.braincorp.petrolwatcher.feature.stations

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.stations.model.Fuel
import com.braincorp.petrolwatcher.ui.GenericSpinnerAdapter
import kotlinx.android.synthetic.main.activity_fuel.*
import java.math.BigDecimal
import java.math.MathContext

/**
 * The activity where a fuel is added
 */
class FuelActivity : AppCompatActivity() {

    companion object {
        const val KEY_DATA = "data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fuel)
        populateSpinners()
        fab.setOnClickListener {
            saveFuel()
        }
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED)
        super.onBackPressed()
    }

    private fun populateSpinners() {
        val types = Fuel.Type.values().map { getString(it.stringRes) }
        val qualities = Fuel.Quality.values().map { getString(it.stringRes) }

        val typeAdapter = GenericSpinnerAdapter(this, types)
        val qualityAdapter = GenericSpinnerAdapter(this, qualities)

        spn_fuel_type.adapter = typeAdapter
        spn_fuel_quality.adapter = qualityAdapter
    }

    private fun saveFuel() {
        val type = Fuel.Type.values()[spn_fuel_type.selectedItemPosition]
        val quality = Fuel.Quality.values()[spn_fuel_quality.selectedItemPosition]
        val priceText = edt_price.text.toString()
        val price = if (priceText.isNotBlank())
            BigDecimal(priceText).round(MathContext(3)) // Precision = 3
        else
            BigDecimal("0.0")
        val fuel = Fuel(type, quality, price)
        val data = Intent().putExtra(KEY_DATA, fuel)
        setResult(RESULT_OK, data)
        finish()
    }

}