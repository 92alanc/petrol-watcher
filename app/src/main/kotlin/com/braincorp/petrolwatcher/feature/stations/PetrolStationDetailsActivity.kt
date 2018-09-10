package com.braincorp.petrolwatcher.feature.stations

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.stations.contract.PetrolStationDetailsActivityContract
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.braincorp.petrolwatcher.feature.stations.presenter.PetrolStationDetailsActivityPresenter
import com.braincorp.petrolwatcher.utils.startMapActivity
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_petrol_station_details.*
import kotlinx.android.synthetic.main.content_petrol_station_details.*

/**
 * The activity where petrol station details are shown
 * and edited
 */
class PetrolStationDetailsActivity : AppCompatActivity(),
        PetrolStationDetailsActivityContract.View,
        View.OnClickListener,
        PlaceSelectionListener {

    companion object {
        private const val KEY_EDIT_MODE = "edit_mode"
        private const val KEY_PETROL_STATION = "petrol_station"
        private const val TAG = "PETROL_WATCHER"

        fun intent(context: Context, petrolStation: PetrolStation): Intent {
            return Intent(context, PetrolStationDetailsActivity::class.java)
                    .putExtra(KEY_PETROL_STATION, petrolStation)
        }
    }

    override lateinit var presenter: PetrolStationDetailsActivityContract.Presenter

    private var editMode = false
    private var petrolStation = PetrolStation()

    private lateinit var placeAutocompleteAddress: PlaceAutocompleteFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petrol_station_details)
        setupToolbar()
        bindPlaceAutocompleteFragment()
        petrolStation = intent.getParcelableExtra(KEY_PETROL_STATION)
        fillReadOnlyFields()
        presenter = PetrolStationDetailsActivityPresenter(view = this)
        fab.setOnClickListener(this)
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putBoolean(KEY_EDIT_MODE, editMode)
            putParcelable(KEY_PETROL_STATION, petrolStation)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
    }

    override fun onBackPressed() {
        if (editMode) {
            editMode = false
            showReadOnlyFields()
        } else {
            super.onBackPressed()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab -> handleFabClick()
        }
    }

    /**
     * Shows the map activity
     */
    override fun showMap() {
        startMapActivity(finishCurrent = true)
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

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        with(savedInstanceState) {
            editMode = getBoolean(KEY_EDIT_MODE)
            petrolStation = getParcelable(KEY_PETROL_STATION)
        }

        if (editMode) {
            showEditableFields()
            fillEditableFields()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun bindPlaceAutocompleteFragment() {
        placeAutocompleteAddress = fragmentManager.findFragmentById(R.id.edt_address) as PlaceAutocompleteFragment
        placeAutocompleteAddress.setHint(getString(R.string.address))
        placeAutocompleteAddress.setOnPlaceSelectedListener(this)
    }

    private fun handleFabClick() {
        if (editMode) {
            updatePetrolStationData()
            presenter.savePetrolStation(petrolStation)
        } else {
            editMode = true
            fab.setImageResource(R.drawable.ic_save)
            showEditableFields()
            fillEditableFields()
        }
    }

    private fun showEditableFields() {
        group_read_only_fields.visibility = GONE
        group_editable_fields.visibility = VISIBLE
    }

    private fun showReadOnlyFields() {
        group_editable_fields.visibility = GONE
        group_read_only_fields.visibility = VISIBLE
    }

    private fun fillEditableFields() {
        edt_name.setText(petrolStation.name)
        placeAutocompleteAddress.setText(petrolStation.address)
    }

    private fun fillReadOnlyFields() {
        txt_name.text = petrolStation.name
        txt_address.text = petrolStation.address
    }

    private fun updatePetrolStationData() {
        petrolStation.name = edt_name.text.toString()
    }

}