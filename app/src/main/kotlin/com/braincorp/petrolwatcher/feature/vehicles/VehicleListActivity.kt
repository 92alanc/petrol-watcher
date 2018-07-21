package com.braincorp.petrolwatcher.feature.vehicles

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.vehicles.contract.VehicleListActivityContract
import com.braincorp.petrolwatcher.feature.vehicles.model.Vehicle
import com.braincorp.petrolwatcher.feature.vehicles.presenter.VehicleListActivityPresenter
import com.braincorp.petrolwatcher.utils.dependencyInjection

/**
 * The activity where a list of the user's
 * vehicles is displayed
 */
class VehicleListActivity : AppCompatActivity(), VehicleListActivityContract.View {

    override lateinit var presenter: VehicleListActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_list)
        presenter = VehicleListActivityPresenter(view = this,
                databaseManager = dependencyInjection().getDatabaseManager())
    }

    /**
     * Updates the list
     *
     * @param vehicles the vehicles
     */
    override fun updateList(vehicles: ArrayList<Vehicle>) {

    }

}