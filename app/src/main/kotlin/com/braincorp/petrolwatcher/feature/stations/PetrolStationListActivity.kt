package com.braincorp.petrolwatcher.feature.stations

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
import com.braincorp.petrolwatcher.utils.startPetrolStationDetailsActivity
import kotlinx.android.synthetic.main.activity_petrol_station_list.*
import kotlinx.android.synthetic.main.content_petrol_station_list.*

/**
 * The activity where petrol stations are shown
 */
class PetrolStationListActivity : AppCompatActivity(),
        PetrolStationListActivityContract.View,
        OnItemClickListener {

    private companion object {
        const val KEY_PETROL_STATIONS = "petrol_stations"
    }

    override lateinit var presenter: PetrolStationListActivityContract.Presenter

    private var petrolStations = ArrayList<PetrolStation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petrol_station_list)
        setupToolbar()
        presenter = PetrolStationListActivityPresenter(view = this)
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
        if (petrolStations.isEmpty())
            fetchPetrolStations()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_PETROL_STATIONS, petrolStations)
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
        val adapter = PetrolStationAdapter(petrolStations, onItemClickListener = this)
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
        petrolStations = savedInstanceState.getParcelableArrayList(KEY_PETROL_STATIONS)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun fetchPetrolStations() {
        recycler_view.visibility = GONE
        progress_bar.visibility = VISIBLE
        presenter.fetchPetrolStations()
    }

}