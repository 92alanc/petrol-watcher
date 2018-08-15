package com.braincorp.petrolwatcher.feature.vehicles

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.vehicles.adapter.SwipeToDeleteCallback
import com.braincorp.petrolwatcher.feature.vehicles.adapter.VehicleAdapter
import com.braincorp.petrolwatcher.feature.vehicles.contract.VehicleListActivityContract
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.braincorp.petrolwatcher.feature.vehicles.presenter.VehicleListActivityPresenter
import com.braincorp.petrolwatcher.ui.OnItemClickListener
import com.braincorp.petrolwatcher.utils.dependencyInjection
import com.braincorp.petrolwatcher.utils.startCreateVehicleActivity
import kotlinx.android.synthetic.main.activity_vehicle_list.*

/**
 * The activity where a list of the user's
 * vehicles is displayed
 */
class VehicleListActivity : AppCompatActivity(), VehicleListActivityContract.View,
        OnItemClickListener {

    override lateinit var presenter: VehicleListActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_list)
        presenter = VehicleListActivityPresenter(view = this,
                databaseManager = dependencyInjection().getDatabaseManager())
        presenter.fetchVehicles()
        setupAddButton()
    }

    /**
     * Updates the list
     *
     * @param vehicles the vehicles
     */
    override fun updateList(vehicles: ArrayList<Vehicle>) {
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

    /**
     * Function triggered when a RecyclerView item is clicked
     *
     * @param position the position in the list
     */
    override fun onItemClick(position: Int) {
        // TODO: start vehicle details activity
    }

    private fun setupAddButton() {
        fab.setOnClickListener {
            startCreateVehicleActivity()
        }
    }

}