package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.adapters.PetrolStationAdapter
import com.braincorp.petrolwatcher.database.PetrolStationDatabase
import com.braincorp.petrolwatcher.fragments.PetrolStationDetailsFragment
import com.braincorp.petrolwatcher.listeners.OnItemClickListener
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.model.PetrolStation
import com.braincorp.petrolwatcher.utils.removeFragment
import com.braincorp.petrolwatcher.utils.replaceFragmentPlaceholder
import com.braincorp.petrolwatcher.utils.showErrorDialogue
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_petrol_stations.*
import kotlinx.android.synthetic.main.content_petrol_stations.*

class PetrolStationsActivity : AppCompatActivity(), View.OnClickListener,
        AdaptableUi, OnItemClickListener, ValueEventListener, OnCompleteListener<Void> {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, PetrolStationsActivity::class.java)
        }
    }

    private var fragment: PetrolStationDetailsFragment? = null
    private var petrolStation: PetrolStation? = null
    private var petrolStations: Array<PetrolStation>? = null
    private var uiMode: AdaptableUi.Mode = AdaptableUi.Mode.INITIAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petrol_stations)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fabPetrolStations.setOnClickListener(this)
        prepareInitialMode()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabPetrolStations -> handleFabClick()
        }
    }

    override fun onItemClick(position: Int) {
        petrolStation = petrolStations!![position]
        prepareViewMode()
    }

    override fun onCancelled(error: DatabaseError?) {
        if (window.isActive)
            showErrorDialogue(R.string.error_finding_petrol_stations)
    }

    override fun onDataChange(snapshot: DataSnapshot?) {
        val list = ArrayList<PetrolStation>()
        snapshot?.children?.forEach {
            val petrolStation = PetrolStation(it)
            list.add(petrolStation)
        }
        petrolStations = list.toTypedArray()
        if (petrolStations != null && petrolStations!!.isNotEmpty()) {
            textViewNoPetrolStations.visibility = GONE
            populateRecyclerView(petrolStations!!)
        } else {
            textViewNoPetrolStations.visibility = VISIBLE
            recyclerViewPetrolStations.visibility = GONE
        }
    }

    override fun onComplete(task: Task<Void>) {
        if (task.isSuccessful) prepareInitialMode()
        else showErrorDialogue(R.string.error_saving_petrol_station)
    }

    private fun handleFabClick() {
        when (uiMode) {
            AdaptableUi.Mode.CREATE, AdaptableUi.Mode.EDIT -> {
                save()
                prepareInitialMode()
            }

            AdaptableUi.Mode.VIEW -> prepareEditMode()
            AdaptableUi.Mode.INITIAL -> prepareCreateMode()
        }
    }

    private fun populateRecyclerView(items: Array<PetrolStation>) {
        recyclerViewPetrolStations.visibility = VISIBLE
        recyclerViewPetrolStations.layoutManager = LinearLayoutManager(this)
        val adapter = PetrolStationAdapter(context = this, items = items,
                onItemClickListener = this)
        recyclerViewPetrolStations.adapter = adapter
    }

    override fun prepareInitialMode() {
        uiMode = AdaptableUi.Mode.INITIAL
        fabPetrolStations.setImageResource(R.drawable.ic_add)

        detachFragment()
        PetrolStationDatabase.select(valueEventListener = this)
    }

    override fun prepareCreateMode() {
        uiMode = AdaptableUi.Mode.CREATE
        fabPetrolStations.setImageResource(R.drawable.ic_save)
        recyclerViewPetrolStations.visibility = GONE
        attachFragment()
    }

    override fun prepareEditMode() {
        uiMode = AdaptableUi.Mode.EDIT
        fabPetrolStations.setImageResource(R.drawable.ic_save)
        recyclerViewPetrolStations.visibility = GONE
        attachFragment(petrolStation)
    }

    override fun prepareViewMode() {
        uiMode = AdaptableUi.Mode.VIEW
        fabPetrolStations.setImageResource(R.drawable.ic_edit)
        recyclerViewPetrolStations.visibility = GONE
        attachFragment(petrolStation)
    }

    private fun attachFragment(petrolStation: PetrolStation? = null) {
        fragment = PetrolStationDetailsFragment.newInstance(petrolStation, uiMode)
        placeholderPetrolStations.visibility = VISIBLE
        replaceFragmentPlaceholder(R.id.placeholderPetrolStations, fragment!!)
    }

    private fun detachFragment() {
        if (fragment != null) removeFragment(fragment!!)
        placeholderPetrolStations.visibility = GONE
    }

    private fun save() {
        petrolStation = fragment?.getData()
        if (petrolStation!!.allFieldsAreValid()) {
            PetrolStationDatabase.insertOrUpdate(petrolStation!!, this)
        } else {
            showErrorDialogue(R.string.all_fields_are_required)
        }
    }

}