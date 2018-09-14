package com.braincorp.petrolwatcher.feature.stations

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.stations.adapter.PetrolStationAdapter
import com.braincorp.petrolwatcher.feature.stations.contract.PetrolStationListActivityContract
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.braincorp.petrolwatcher.feature.stations.presenter.PetrolStationListActivityPresenter
import com.braincorp.petrolwatcher.ui.OnItemClickListener
import com.braincorp.petrolwatcher.utils.hasLocationPermission
import com.braincorp.petrolwatcher.utils.startPetrolStationDetailsActivity
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_petrol_station_list.*
import kotlinx.android.synthetic.main.content_petrol_station_list.*

/**
 * The activity where petrol stations are shown
 */
class PetrolStationListActivity : AppCompatActivity(),
        PetrolStationListActivityContract.View,
        OnItemClickListener {

    companion object {
        private const val KEY_CURRENT_LOCATION = "current_location"
        private const val KEY_HAS_LOCATION_PERMISSION = "has_location_permission"
        private const val KEY_PETROL_STATIONS = "petrol_stations"

        fun intent(context: Context,
                   currentLocation: LatLng?): Intent {
            return Intent(context, PetrolStationListActivity::class.java)
                    .putExtra(KEY_CURRENT_LOCATION, currentLocation)
        }
    }

    override lateinit var presenter: PetrolStationListActivityContract.Presenter

    private var petrolStations = ArrayList<PetrolStation>()
    private var hasLocationPermission = false
    private var currentLocation: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petrol_station_list)
        setupToolbar()
        presenter = PetrolStationListActivityPresenter(view = this)

        hasLocationPermission = hasLocationPermission()
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
        else
            currentLocation = intent.getParcelableExtra(KEY_CURRENT_LOCATION)

        if (petrolStations.isEmpty())
            fetchPetrolStations()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putParcelableArrayList(KEY_PETROL_STATIONS, petrolStations)
            putParcelable(KEY_CURRENT_LOCATION, currentLocation)
            putBoolean(KEY_HAS_LOCATION_PERMISSION, hasLocationPermission)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_petrol_station_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_refresh -> fetchPetrolStations()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Updates the petrol station list
     */
    override fun updateList(petrolStations: ArrayList<PetrolStation>) {
        this.petrolStations = petrolStations
        val adapter = PetrolStationAdapter(petrolStations,
                onItemClickListener = this,
                hasLocationPermission = hasLocationPermission,
                currentLocation = currentLocation)
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter

        progress_bar.visibility = GONE
        recycler_view.visibility = VISIBLE
    }

    /**
     * Function triggered when a RecyclerView item is clicked
     *
     * @param position the position in the list
     */
    override fun onItemClick(position: Int) {
        val petrolStation = petrolStations[position]
        startPetrolStationDetailsActivity(petrolStation)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        with(savedInstanceState) {
            petrolStations = getParcelableArrayList(KEY_PETROL_STATIONS)
            updateList(petrolStations)
            currentLocation = getParcelable(KEY_CURRENT_LOCATION)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun fetchPetrolStations() {
        recycler_view.visibility = GONE
        progress_bar.visibility = VISIBLE
        presenter.fetchPetrolStations(hasLocationPermission(), currentLocation)
    }

}