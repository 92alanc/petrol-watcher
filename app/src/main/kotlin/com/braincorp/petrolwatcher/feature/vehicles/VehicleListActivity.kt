package com.braincorp.petrolwatcher.feature.vehicles

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View.GONE
import android.view.View.VISIBLE
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.vehicles.adapter.SwipeToDeleteCallback
import com.braincorp.petrolwatcher.feature.vehicles.adapter.VehicleAdapter
import com.braincorp.petrolwatcher.feature.vehicles.contract.VehicleListActivityContract
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.braincorp.petrolwatcher.feature.vehicles.presenter.VehicleListActivityPresenter
import com.braincorp.petrolwatcher.ui.OnItemClickListener
import com.braincorp.petrolwatcher.utils.startCreateVehicleActivity
import com.braincorp.petrolwatcher.utils.startVehicleDetailsActivity
import kotlinx.android.synthetic.main.activity_vehicle_list.*

/**
 * The activity where a list of the user's
 * vehicles is displayed
 */
class VehicleListActivity : AppCompatActivity(), VehicleListActivityContract.View,
        OnItemClickListener {

    private companion object {
        const val KEY_VEHICLES = "vehicles"
    }

    override lateinit var presenter: VehicleListActivityContract.Presenter

    private var vehicles = arrayListOf<Vehicle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_list)
        presenter = VehicleListActivityPresenter(view = this,
                databaseManager = DependencyInjection.databaseManager)

        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
        else
            fetchVehicles()

        setupAddButton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_VEHICLES, vehicles)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            restoreInstanceState(it)
        }
    }

    /**
     * Updates the list
     *
     * @param vehicles the vehicles
     */
    override fun updateList(vehicles: ArrayList<Vehicle>) {
        this.vehicles = vehicles
        progress_bar.visibility = GONE

        if (vehicles.isEmpty()) {
            txt_no_vehicles.visibility = VISIBLE
            recycler_view.visibility = GONE
        } else {
            txt_no_vehicles.visibility = GONE
            recycler_view.visibility = VISIBLE

            val adapter = VehicleAdapter(vehicles, this)
            val layoutManager = LinearLayoutManager(this)
            recycler_view.layoutManager = layoutManager
            recycler_view.adapter = adapter

            val swipeHandler = object : SwipeToDeleteCallback(this) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val vehicleAdapter = recycler_view.adapter as VehicleAdapter
                    vehicleAdapter.removeAt(viewHolder.adapterPosition,
                            presenter as VehicleListActivityPresenter)
                }
            }

            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(recycler_view)
        }
    }

    /**
     * Function triggered when a RecyclerView item is clicked
     *
     * @param position the position in the list
     */
    override fun onItemClick(position: Int) {
        val vehicle = vehicles[position]
        startVehicleDetailsActivity(vehicle)
    }

    private fun fetchVehicles() {
        progress_bar.visibility = VISIBLE
        presenter.fetchVehicles()
    }

    private fun setupAddButton() {
        fab.setOnClickListener {
            startCreateVehicleActivity()
        }
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        vehicles = savedInstanceState.getParcelableArrayList(KEY_VEHICLES)!!
        updateList(vehicles)
    }

}