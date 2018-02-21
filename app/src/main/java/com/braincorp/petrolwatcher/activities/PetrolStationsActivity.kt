package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.adapters.PetrolStationAdapter
import com.braincorp.petrolwatcher.database.PetrolStationDatabase
import com.braincorp.petrolwatcher.fragments.PetrolStationDetailsFragment
import com.braincorp.petrolwatcher.listeners.OnFragmentInflatedListener
import com.braincorp.petrolwatcher.listeners.OnItemClickListener
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.model.PetrolStation
import com.braincorp.petrolwatcher.utils.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_petrol_stations.*
import kotlinx.android.synthetic.main.content_petrol_stations.*

class PetrolStationsActivity : AppCompatActivity(), View.OnClickListener,
        OnItemClickListener, AdaptableUi, ValueEventListener,
        OnCompleteListener<Void>, OnFragmentInflatedListener {

    companion object {
        private const val KEY_FRAGMENT = "fragment"
        private const val KEY_PETROL_STATION = "petrol_station"
        private const val KEY_PETROL_STATIONS = "petrol_stations"
        private const val KEY_UI_MODE = "ui_mode"

        private const val TAG_PETROL_STATION_DETAILS = "petrol_station_details"

        fun getIntent(context: Context): Intent {
            return Intent(context, PetrolStationsActivity::class.java)
        }
    }

    private var fragment: PetrolStationDetailsFragment? = null
    private var petrolStation: PetrolStation? = null
    private var petrolStations: Array<PetrolStation>? = null
    private var uiMode = AdaptableUi.Mode.INITIAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petrol_stations)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fabPetrolStations.setOnClickListener(this)

        if (savedInstanceState != null)
            parseSavedInstanceState(savedInstanceState)
        prepareUi()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(KEY_UI_MODE, uiMode)
        outState?.putParcelable(KEY_PETROL_STATION, petrolStation)
        outState?.putParcelableArray(KEY_PETROL_STATIONS, petrolStations)
        outState?.putString(KEY_FRAGMENT, fragment?.tag)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null)
            parseSavedInstanceState(savedInstanceState)
    }

    override fun onBackPressed() {
        if (uiMode != AdaptableUi.Mode.INITIAL) prepareInitialMode()
        else super.onBackPressed()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabPetrolStations -> handleFabClick()
            R.id.buttonDelete -> promptDelete()
        }
    }

    override fun onItemClick(position: Int) {
        petrolStation = petrolStations!![position]
        prepareViewMode()
    }

    override fun onComplete(task: Task<Void>) {
        if (task.isSuccessful)
            recyclerViewPetrolStations.adapter.notifyDataSetChanged()
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
            populateRecyclerView()
        } else {
            textViewNoPetrolStations.visibility = VISIBLE
            recyclerViewPetrolStations.visibility = GONE
        }
    }

    override fun onCancelled(error: DatabaseError?) {
        if (!isFinishing)
            showErrorDialogue(R.string.error_finding_petrol_stations)
    }

    override fun onFragmentInflated(fragment: Fragment) {
        (fragment as PetrolStationDetailsFragment).setDeleteButtonClickListener(this)
    }

    override fun prepareInitialMode() {
        uiMode = AdaptableUi.Mode.INITIAL
        fragment = null
        fabPetrolStations.setImageResource(R.drawable.ic_add)

        removeFragment()
        if (petrolStations == null)
            PetrolStationDatabase.select(valueEventListener = this)
    }

    override fun prepareCreateMode() {
        uiMode = AdaptableUi.Mode.CREATE
        recyclerViewPetrolStations.visibility = GONE
        fabPetrolStations.setImageResource(R.drawable.ic_save)
        loadFragment(uiMode)
    }

    override fun prepareEditMode() {
        uiMode = AdaptableUi.Mode.EDIT
        fabPetrolStations.setImageResource(R.drawable.ic_save)
        recyclerViewPetrolStations.visibility = GONE
        loadFragment(uiMode)
    }

    override fun prepareViewMode() {
        uiMode = AdaptableUi.Mode.VIEW
        fabPetrolStations.setImageResource(R.drawable.ic_edit)
        recyclerViewPetrolStations.visibility = GONE
        loadFragment(uiMode)
    }

    private fun prepareUi() {
        when (uiMode) {
            AdaptableUi.Mode.INITIAL -> prepareInitialMode()
            AdaptableUi.Mode.CREATE -> prepareCreateMode()
            AdaptableUi.Mode.EDIT -> prepareEditMode()
            AdaptableUi.Mode.VIEW -> prepareViewMode()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun parseSavedInstanceState(savedInstanceState: Bundle) {
        petrolStation = savedInstanceState.getParcelable(KEY_PETROL_STATION)
        val array = savedInstanceState.getParcelableArray(KEY_PETROL_STATIONS)
        if (array != null)
            petrolStations = array as Array<PetrolStation>

        uiMode = savedInstanceState.getSerializable(KEY_UI_MODE) as AdaptableUi.Mode

        val tag = savedInstanceState.getString(KEY_FRAGMENT)
        if (tag != null)
            fragment = supportFragmentManager.findFragmentByTag(tag) as PetrolStationDetailsFragment
    }

    private fun populateRecyclerView() {
        recyclerViewPetrolStations.visibility = VISIBLE
        recyclerViewPetrolStations.layoutManager = LinearLayoutManager(this)
        val adapter = PetrolStationAdapter(context = this, items = petrolStations!!,
                onItemClickListener = this)
        recyclerViewPetrolStations.adapter = adapter
    }

    private fun handleFabClick() {
        when (uiMode) {
            AdaptableUi.Mode.INITIAL -> prepareCreateMode()
            AdaptableUi.Mode.VIEW -> prepareEditMode()

            AdaptableUi.Mode.CREATE, AdaptableUi.Mode.EDIT -> {
                if (save()) prepareInitialMode()
            }
        }
    }

    private fun promptDelete() {
        showQuestionDialogue(R.string.delete_petrol_station, R.string.are_you_sure, positiveFunc = {
            PetrolStationDatabase.delete(petrolStation!!, OnCompleteListener {
                recyclerViewPetrolStations.adapter.notifyDataSetChanged()
                prepareInitialMode()
            })
        }, negativeFunc = { })
    }

    private fun loadFragment(uiMode: AdaptableUi.Mode) {
        placeholderTop.visibility = VISIBLE
        if (fragment == null || uiMode == AdaptableUi.Mode.EDIT) {
            fragment = PetrolStationDetailsFragment.newInstance(uiMode, this,
                    petrolStation)
            replaceFragmentPlaceholder(R.id.placeholderTop, fragment!!,
                    TAG_PETROL_STATION_DETAILS)
        }
    }

    private fun removeFragment() {
        if (fragment != null) removeFragment(fragment!!)
        placeholderTop.visibility = GONE
    }

    private fun save(): Boolean {
        val petrolStation = fragment?.getPetrolStation()
        return if (petrolStation != null) {
            if (petrolStation.allFieldsAreValid()) {
                PetrolStationDatabase.insertOrUpdate(petrolStation, this)
                true
            } else {
                showInformationDialogue(R.string.information, R.string.all_fields_are_required)
                false
            }
        } else false
    }

}