package com.braincorp.petrolwatcher.feature.stations

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.stations.contract.CreatePetrolStationActivityContract
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.braincorp.petrolwatcher.feature.stations.presenter.CreatePetrolStationActivityPresenter
import com.braincorp.petrolwatcher.utils.startPetrolStationListActivity
import kotlinx.android.synthetic.main.activity_create_petrol_station.*
import kotlinx.android.synthetic.main.content_create_petrol_station.*

/**
 * The activity where petrol stations are created
 */
class CreatePetrolStationActivity : AppCompatActivity(),
        CreatePetrolStationActivityContract.View,
        View.OnClickListener {

    override lateinit var presenter: CreatePetrolStationActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_petrol_station)
        setupToolbar()
        presenter = CreatePetrolStationActivityPresenter(view = this)
        fab.setOnClickListener(this)
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

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun save() {
        val name = edt_name.text.toString()
        val address = edt_address.text.toString()
        val petrolStation = PetrolStation(name, address)
        presenter.savePetrolStation(petrolStation)
    }

}