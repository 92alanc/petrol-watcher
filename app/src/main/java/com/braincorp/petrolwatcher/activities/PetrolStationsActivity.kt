package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.model.PetrolStation
import kotlinx.android.synthetic.main.activity_petrol_stations.*

class PetrolStationsActivity : AppCompatActivity(), View.OnClickListener, AdaptableUi {

    companion object {
        private const val KEY_FRAGMENT = "fragment"
        private const val KEY_PETROL_STATION = "petrol_station"
        private const val KEY_UI_MODE = "ui_mode"

        private const val TAG_PETROL_STATION_DETAILS = "petrol_station_details"

        fun getIntent(context: Context): Intent {
            return Intent(context, PetrolStationsActivity::class.java)
        }
    }

    private var fragment: Fragment? = null // TODO: replace with PetrolStationDetailsFragment
    private var petrolStation: PetrolStation? = null
    private var petrolStations: Array<PetrolStation>? = null
    private var uiMode = AdaptableUi.Mode.INITIAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petrol_stations)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fabPetrolStations.setOnClickListener(this)

        if (savedInstanceState != null)
            parseSavedInstanceState(savedInstanceState)
        prepareUi()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null)
            parseSavedInstanceState(savedInstanceState)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onClick(v: View?) {

    }

    override fun prepareInitialMode() {

    }

    override fun prepareCreateMode() {

    }

    override fun prepareEditMode() {

    }

    override fun prepareViewMode() {

    }

    private fun prepareUi() {
        when (uiMode) {
            AdaptableUi.Mode.INITIAL -> prepareInitialMode()
            AdaptableUi.Mode.CREATE -> prepareCreateMode()
            AdaptableUi.Mode.EDIT -> prepareEditMode()
            AdaptableUi.Mode.VIEW -> prepareViewMode()
        }
    }

    private fun parseSavedInstanceState(savedInstanceState: Bundle) {
        petrolStation = savedInstanceState.getParcelable(KEY_PETROL_STATION)
        uiMode = savedInstanceState.getSerializable(KEY_UI_MODE) as AdaptableUi.Mode

        val tag = savedInstanceState.getString(KEY_FRAGMENT)
        if (tag != null)
            fragment = supportFragmentManager.findFragmentByTag(tag) // TODO: as PetrolStationDetailsFragment
    }

}