package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.adapters.VehicleAdapter
import com.braincorp.petrolwatcher.database.VehicleDatabase
import com.braincorp.petrolwatcher.fragments.VehicleDetailsFragment
import com.braincorp.petrolwatcher.listeners.OnFragmentInflatedListener
import com.braincorp.petrolwatcher.listeners.OnItemClickListener
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.model.Vehicle
import com.braincorp.petrolwatcher.utils.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_vehicles.*
import kotlinx.android.synthetic.main.content_vehicles.*

class VehiclesActivity : AppCompatActivity(), View.OnClickListener,
        OnFragmentInflatedListener, OnCompleteListener<Void>,
        OnItemClickListener, ValueEventListener, AdaptableUi {

    companion object {
        private const val EXTRA_DATA = "data"

        fun getIntent(context: Context, pickVehicle: Boolean = false): Intent {
            val intent = Intent(context, VehiclesActivity::class.java)
            if (pickVehicle) intent.action = ACTION_PICK
            return intent
        }
    }

    private var fragment: VehicleDetailsFragment? = null
    private var uiMode = AdaptableUi.Mode.INITIAL
    private var vehicle: Vehicle? = null
    private var vehicles: Array<Vehicle>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicles)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fabVehicles.setOnClickListener(this)
        prepareInitialMode()
    }

    override fun onBackPressed() {
        if (ACTION_PICK == intent.action) {
            setResult(RESULT_CANCELED)
            finish()
        } else super.onBackPressed()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabVehicles -> handleFabClick()
            R.id.buttonDelete -> promptDelete()
        }
    }

    override fun onFragmentInflated(fragment: Fragment) {
        (fragment as VehicleDetailsFragment).setDeleteButtonClickListener(this)
    }

    override fun onItemClick(position: Int) {
        vehicle = vehicles!![position]
        if (ACTION_PICK == intent.action) {
            val data = Intent().putExtra(EXTRA_DATA, vehicle)
            setResult(RESULT_OK, data)
            finish()
        } else {
            uiMode = AdaptableUi.Mode.VIEW
            prepareViewMode()
        }
    }

    override fun onComplete(task: Task<Void>) {
        if (task.isSuccessful)
            recyclerViewVehicles.adapter.notifyDataSetChanged()
    }

    override fun onDataChange(snapshot: DataSnapshot?) {
        val list = ArrayList<Vehicle>()
        snapshot?.children?.forEach {
            val vehicle = Vehicle(it)
            list.add(vehicle)
        }
        vehicles = list.toTypedArray()
        if (vehicles != null && vehicles!!.isNotEmpty()) {
            textViewNoVehicles.visibility = GONE
            populateRecyclerView(vehicles!!)
        } else {
            textViewNoVehicles.visibility = VISIBLE
            recyclerViewVehicles.visibility = GONE
        }
    }

    override fun onCancelled(error: DatabaseError?) {
        showErrorDialogue(R.string.error_finding_vehicles)
    }

    override fun prepareInitialMode() {
        uiMode = AdaptableUi.Mode.INITIAL
        fabVehicles.setImageResource(R.drawable.ic_add)

        removeFragment()
        VehicleDatabase.select(valueEventListener = this)
    }

    override fun prepareCreateMode() {
        uiMode = AdaptableUi.Mode.CREATE
        recyclerViewVehicles.visibility = GONE
        fabVehicles.setImageResource(R.drawable.ic_save)
        loadFragment(uiMode)
    }

    override fun prepareEditMode() {
        uiMode = AdaptableUi.Mode.EDIT
        fabVehicles.setImageResource(R.drawable.ic_save)
        loadFragment(uiMode, vehicle)
        recyclerViewVehicles.visibility = GONE
    }

    override fun prepareViewMode() {
        fabVehicles.setImageResource(R.drawable.ic_edit)

        uiMode = AdaptableUi.Mode.VIEW
        loadFragment(uiMode, vehicle)

        recyclerViewVehicles.visibility = GONE
    }

    private fun handleFabClick() {
        when (uiMode) {
            AdaptableUi.Mode.VIEW -> prepareEditMode()
            AdaptableUi.Mode.INITIAL -> prepareCreateMode()

            AdaptableUi.Mode.CREATE, AdaptableUi.Mode.EDIT -> {
                if (save()) prepareInitialMode()
            }
        }
    }

    private fun loadFragment(uiMode: AdaptableUi.Mode, vehicle: Vehicle? = null) {
        placeholderVehicles.visibility = VISIBLE
        fragment = VehicleDetailsFragment.newInstance(uiMode, this, vehicle)
        replaceFragmentPlaceholder(R.id.placeholderVehicles, fragment!!)
    }

    private fun removeFragment() {
        if (fragment != null) removeFragment(fragment!!)
        placeholderVehicles.visibility = GONE
    }

    private fun promptDelete() {
        showQuestionDialogue(R.string.delete_vehicle, R.string.are_you_sure, positiveFunc = {
            VehicleDatabase.delete(vehicle!!, OnCompleteListener {
                recyclerViewVehicles.adapter.notifyDataSetChanged()
                prepareInitialMode()
            })
        }, negativeFunc = { })
    }

    private fun populateRecyclerView(items: Array<Vehicle>) {
        recyclerViewVehicles.visibility = VISIBLE
        recyclerViewVehicles.layoutManager = LinearLayoutManager(this)
        val adapter = VehicleAdapter(context = this, items = items, onItemClickListener = this)
        recyclerViewVehicles.adapter = adapter
    }

    private fun save(): Boolean {
        val vehicle = fragment?.getVehicle()
        return if (vehicle != null) {
            if (vehicle.allFieldsAreValid()) {
                VehicleDatabase.insertOrUpdate(vehicle, this)
                true
            } else {
                showInformationDialogue(R.string.information, R.string.all_fields_are_required)
                false
            }
        } else false
    }

}