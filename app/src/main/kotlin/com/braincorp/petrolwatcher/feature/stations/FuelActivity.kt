package com.braincorp.petrolwatcher.feature.stations

import android.content.Context
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

        private const val KEY_FUEL = "fuel"

        fun intent(context: Context, fuel: Fuel? = null): Intent {
            return Intent(context, FuelActivity::class.java)
                    .putExtra(KEY_FUEL, fuel)
        }
    }

    private var fuel: Fuel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fuel)
        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState)
        } else {
            fuel = intent.getParcelableExtra(KEY_FUEL)
            fillViews()
        }
        fab.setOnClickListener {
            exportFuel()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_FUEL, fuel)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED)
        super.onBackPressed()
    }

    private fun fillViews() {
        val types = Fuel.Type.values().map { getString(it.stringRes) }
        val qualities = Fuel.Quality.values().map { getString(it.stringRes) }

        val typeAdapter = GenericSpinnerAdapter(this, types)
        val qualityAdapter = GenericSpinnerAdapter(this, qualities)

        spn_fuel_type.adapter = typeAdapter
        spn_fuel_quality.adapter = qualityAdapter

        fuel?.let {
            spn_fuel_type.setSelection(types.indexOf(getString(it.type.stringRes)))
            spn_fuel_quality.setSelection(qualities.indexOf(getString(it.quality.stringRes)))
            edt_price.setText(it.price.round(MathContext(3)).toPlainString())
        }
    }

    private fun exportFuel() {
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

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        fuel = savedInstanceState.getParcelable(KEY_FUEL)
        fillViews()
    }

}