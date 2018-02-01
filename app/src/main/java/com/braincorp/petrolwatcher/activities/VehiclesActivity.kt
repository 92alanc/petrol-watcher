package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.adapters.VehicleAdapter
import com.braincorp.petrolwatcher.database.VehicleDatabase
import com.braincorp.petrolwatcher.fragments.VehicleDetailsFragment
import com.braincorp.petrolwatcher.listeners.OnItemClickListener
import com.braincorp.petrolwatcher.model.UiMode
import com.braincorp.petrolwatcher.model.Vehicle
import com.braincorp.petrolwatcher.utils.removeFragment
import com.braincorp.petrolwatcher.utils.replaceFragmentPlaceholder
import com.braincorp.petrolwatcher.utils.showErrorDialogue
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_vehicles.*
import kotlinx.android.synthetic.main.content_vehicles.*

class VehiclesActivity : BaseActivity(), View.OnClickListener,
        OnCompleteListener<Void>, OnItemClickListener, ValueEventListener {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, VehiclesActivity::class.java)
        }
    }

    private var fragment: VehicleDetailsFragment? = null
    private var uiMode = UiMode.VIEW
    private var vehicles: Array<Vehicle>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicles)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fabVehicles.setOnClickListener(this)
        prepareViewMode()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabVehicles -> handleFabClick()
        }
    }

    override fun onItemClick(position: Int) {
        prepareEditMode(vehicles!![position])
    }

    override fun onComplete(task: Task<Void>) {
        if (task.isSuccessful)
            recyclerViewVehicles.adapter.notifyDataSetChanged()
    }

    override fun onDataChange(snapshot: DataSnapshot?) {
        val list = ArrayList<Vehicle>()
        snapshot?.children?.forEach {
            val vehicle = it.getValue(Vehicle::class.java)
            if (vehicle != null)
                list.add(vehicle)
        }
        vehicles = list.toTypedArray()
        if (vehicles != null) populateRecyclerView(vehicles!!)
    }

    override fun onCancelled(error: DatabaseError?) {
        showErrorDialogue(R.string.error_finding_vehicles)
    }

    private fun handleFabClick() {
        when (uiMode) {
            UiMode.VIEW -> {
                uiMode = UiMode.CREATE
                recyclerViewVehicles.visibility = GONE
                fabVehicles.setImageResource(R.drawable.ic_save)
                loadFragment(uiMode)
            }
            else -> {
                save()
                uiMode = UiMode.VIEW
                prepareViewMode()
            }
        }
    }

    private fun loadFragment(uiMode: UiMode, vehicle: Vehicle? = null) {
        placeholderVehicles.visibility = VISIBLE
        fragment = VehicleDetailsFragment.newInstance(uiMode, vehicle)
        replaceFragmentPlaceholder(R.id.placeholderVehicles, fragment!!)
    }

    private fun removeFragment() {
        if (fragment != null) removeFragment(fragment!!)
        placeholderVehicles.visibility = GONE
    }

    private fun prepareEditMode(vehicle: Vehicle) {
        fabVehicles.setImageResource(R.drawable.ic_save)

        uiMode = UiMode.EDIT
        loadFragment(uiMode, vehicle)

        recyclerViewVehicles.visibility = GONE
    }

    private fun prepareViewMode() {
        uiMode = UiMode.VIEW
        fabVehicles.setImageResource(R.drawable.ic_add)

        removeFragment()
        VehicleDatabase.select(valueEventListener = this)
        // showProgressBar()
        recyclerViewVehicles.visibility = VISIBLE
    }

    private fun populateRecyclerView(items: Array<Vehicle>) {
        recyclerViewVehicles.layoutManager = LinearLayoutManager(this)
        val adapter = VehicleAdapter(context = this, items = items, onItemClickListener = this)
        recyclerViewVehicles.adapter = adapter
        // hideProgressBar()
    }

    private fun save() {
        val vehicle = fragment?.getVehicle()
        if (vehicle != null)
            VehicleDatabase.insertOrUpdate(vehicle, this)
    }

}