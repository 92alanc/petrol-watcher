package com.braincorp.petrolwatcher.feature.stations

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.stations.contract.CreatePetrolStationActivityContract
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.braincorp.petrolwatcher.feature.stations.presenter.CreatePetrolStationActivityPresenter
import com.braincorp.petrolwatcher.utils.startPetrolStationListActivity
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_create_petrol_station.*
import kotlinx.android.synthetic.main.content_create_petrol_station.*

/**
 * The activity where petrol stations are created
 */
class CreatePetrolStationActivity : AppCompatActivity(),
        CreatePetrolStationActivityContract.View,
        View.OnClickListener,
        PlaceSelectionListener {

    private companion object {
        const val KEY_PETROL_STATION = "petrol_station"
        const val TAG = "PETROL_WATCHER"
    }

    override lateinit var presenter: CreatePetrolStationActivityContract.Presenter

    private var petrolStation = PetrolStation()
    private lateinit var placeAutocompleteAddress: PlaceAutocompleteFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_petrol_station)
        setupToolbar()
        bindPlaceAutocompleteFragment()
        presenter = CreatePetrolStationActivityPresenter(view = this)
        fab.setOnClickListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_PETROL_STATION, petrolStation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab -> save()
        }
    }

    /**
     * Shows the petrol station list
     */
    override fun showPetrolStationList() {
        startPetrolStationListActivity(finishCurrent = true)
    }

    /**
     * Shows an invalid petrol station dialogue
     */
    override fun showInvalidPetrolStationDialogue() {
        AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(R.string.invalid_petrol_station_data)
                .setIcon(R.drawable.ic_error)
                .setNeutralButton(R.string.ok, null)
                .show()
    }

    override fun onPlaceSelected(place: Place?) {
        if (place?.address != null) {
            petrolStation.address = place.address.toString()
            petrolStation.latLng = place.latLng
        }
    }

    override fun onError(error: Status?) {
        Log.e(TAG, error?.statusMessage)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun save() {
        petrolStation.name = edt_name.text.toString()
        presenter.savePetrolStation(petrolStation)
    }

    private fun bindPlaceAutocompleteFragment() {
        placeAutocompleteAddress = fragmentManager.findFragmentById(R.id.edt_address) as PlaceAutocompleteFragment
        placeAutocompleteAddress.setHint(getString(R.string.address))
        placeAutocompleteAddress.setOnPlaceSelectedListener(this)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        with(savedInstanceState) {
            petrolStation = getParcelable(KEY_PETROL_STATION)

            edt_name.setText(petrolStation.name)
            placeAutocompleteAddress.setText(petrolStation.address)
        }
    }

}