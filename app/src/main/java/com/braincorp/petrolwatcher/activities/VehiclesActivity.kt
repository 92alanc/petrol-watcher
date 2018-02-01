package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.database.VehicleDatabase
import com.braincorp.petrolwatcher.fragments.VehicleDetailsFragment
import com.braincorp.petrolwatcher.model.UiMode
import com.braincorp.petrolwatcher.utils.removeFragment
import com.braincorp.petrolwatcher.utils.replaceFragmentPlaceholder
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_vehicles.*
import kotlinx.android.synthetic.main.content_vehicles.*

class VehiclesActivity : BaseActivity(), View.OnClickListener, OnCompleteListener<Void> {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, VehiclesActivity::class.java)
        }
    }

    private var fragment: VehicleDetailsFragment? = null
    private var uiMode = UiMode.VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicles)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fabVehicles.setOnClickListener(this)
        prepareUi()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabVehicles -> handleFabClick()
        }
    }

    override fun onComplete(task: Task<Void>) {
        if (task.isSuccessful) {
            // TODO: refresh recyclerView
        }
    }

    private fun handleFabClick() {
        when (uiMode) {
            UiMode.VIEW -> prepareCreateMode()
            else -> save()
        }
    }

    private fun loadFragment() {
        placeholderVehicles.visibility = VISIBLE

        fragment = VehicleDetailsFragment.newInstance(uiMode)
        replaceFragmentPlaceholder(R.id.placeholderVehicles, fragment!!)
    }

    private fun removeFragment() {
        removeFragment(fragment!!)
        placeholderVehicles.visibility = GONE
    }

    private fun prepareUi() {
        when (uiMode) {
            UiMode.CREATE -> prepareCreateMode()
            UiMode.EDIT -> prepareEditMode()
            UiMode.VIEW -> prepareViewMode()
        }
    }

    private fun prepareCreateMode() {
        recyclerViewVehicles.visibility = GONE
        loadFragment()
    }

    private fun prepareEditMode() {
        recyclerViewVehicles.visibility = GONE
        loadFragment()
    }

    private fun prepareViewMode() {
        recyclerViewVehicles.visibility = VISIBLE
        removeFragment()
    }

    private fun save() {
        val vehicle = fragment?.getVehicle()
        if (vehicle != null)
            VehicleDatabase.insertOrUpdate(vehicle, this)
    }

}