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
import com.braincorp.petrolwatcher.fragments.FuelsFragment
import com.braincorp.petrolwatcher.fragments.PetrolStationDetailsFragment
import com.braincorp.petrolwatcher.listeners.OnItemClickListener
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.model.PetrolStation
import com.braincorp.petrolwatcher.utils.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_petrol_stations.*
import kotlinx.android.synthetic.main.content_petrol_stations.*

class PetrolStationsActivity : AppCompatActivity(), View.OnClickListener, OnItemClickListener,
        AdaptableUi, ValueEventListener, OnCompleteListener<Void> {

    companion object {
        private const val KEY_PETROL_STATION = "petrol_station"
        private const val KEY_PETROL_STATIONS = "petrol_stations"
        private const val KEY_TOP_FRAGMENT = "top_fragment"
        private const val KEY_BOTTOM_FRAGMENT = "bottom_fragment"
        private const val KEY_UI_MODE = "ui_mode"

        private const val TAG_TOP_FRAGMENT = "details"
        private const val TAG_BOTTOM_FRAGMENT = "fuels"

        fun getIntent(context: Context): Intent {
            return Intent(context, PetrolStationsActivity::class.java)
        }
    }

    private var petrolStation: PetrolStation? = null
    private var petrolStations: ArrayList<PetrolStation>? = null
    private var topFragment: PetrolStationDetailsFragment? = null
    private var bottomFragment: FuelsFragment? = null
    private var uiMode = AdaptableUi.Mode.INITIAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petrol_stations)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fabPetrolStations.setOnClickListener(this)
        buttonDelete.setOnClickListener(this)

        if (savedInstanceState != null)
            parseSavedInstanceState(savedInstanceState)
        prepareUi()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(KEY_UI_MODE, uiMode)
        outState?.putParcelable(KEY_PETROL_STATION, petrolStation)
        outState?.putParcelableArrayList(KEY_PETROL_STATIONS, petrolStations)
        outState?.putString(KEY_TOP_FRAGMENT, topFragment?.tag)
        outState?.putString(KEY_BOTTOM_FRAGMENT, bottomFragment?.tag)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null)
            parseSavedInstanceState(savedInstanceState)
    }

    override fun onBackPressed() {
        when {
            uiMode != AdaptableUi.Mode.INITIAL -> prepareInitialMode()
            uiMode == AdaptableUi.Mode.EDIT -> prepareViewMode()
            else -> super.onBackPressed()
        }
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

    override fun onDataChange(snapshot: DataSnapshot?) {
        val list = ArrayList<PetrolStation>()
        snapshot?.children?.forEach {
            val petrolStation = PetrolStation(it)
            list.add(petrolStation)
        }
        petrolStations = list

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

    override fun onComplete(task: Task<Void>) {
        if (task.isSuccessful)
            recyclerViewPetrolStations.adapter.notifyDataSetChanged()
    }

    override fun prepareInitialMode() {
        uiMode = AdaptableUi.Mode.INITIAL

        fabPetrolStations.setImageResource(R.drawable.ic_add)
        removeFragments()

        recyclerViewPetrolStations.visibility = VISIBLE
        groupPlaceholders.visibility = GONE
        buttonDelete.visibility = GONE

        if (petrolStations == null) PetrolStationDatabase.select(this)
        else populateRecyclerView()
    }

    override fun prepareCreateMode() {
        uiMode = AdaptableUi.Mode.CREATE

        recyclerViewPetrolStations.visibility = GONE
        groupPlaceholders.visibility = VISIBLE
        buttonDelete.visibility = GONE
        textViewNoPetrolStations.visibility = GONE

        fabPetrolStations.setImageResource(R.drawable.ic_save)
        loadFragments()
    }

    override fun prepareEditMode() {
        uiMode = AdaptableUi.Mode.EDIT

        recyclerViewPetrolStations.visibility = GONE
        groupPlaceholders.visibility = VISIBLE
        textViewNoPetrolStations.visibility = GONE

        fabPetrolStations.setImageResource(R.drawable.ic_save)
        loadFragments()

        checkDeleteAvailability()
    }

    override fun prepareViewMode() {
        uiMode = AdaptableUi.Mode.VIEW

        recyclerViewPetrolStations.visibility = GONE
        groupPlaceholders.visibility = VISIBLE
        textViewNoPetrolStations.visibility = GONE

        fabPetrolStations.setImageResource(R.drawable.ic_edit)
        loadFragments()

        checkDeleteAvailability()
    }

    @Suppress("UNCHECKED_CAST")
    private fun parseSavedInstanceState(savedInstanceState: Bundle) {
        uiMode = savedInstanceState.getSerializable(KEY_UI_MODE) as AdaptableUi.Mode
        petrolStation = savedInstanceState.getParcelable(KEY_PETROL_STATION)
        petrolStations = savedInstanceState.getParcelableArrayList(KEY_PETROL_STATIONS)
        if (petrolStations != null && petrolStations!!.isNotEmpty()) {
            textViewNoPetrolStations.visibility = GONE
            if (uiMode == AdaptableUi.Mode.INITIAL) populateRecyclerView()
            else recyclerViewPetrolStations.visibility = GONE
        } else {
            textViewNoPetrolStations.visibility = VISIBLE
            recyclerViewPetrolStations.visibility = GONE
        }

        val topFragmentTag = savedInstanceState.getString(KEY_TOP_FRAGMENT)
        if (topFragmentTag != null) {
            topFragment = fragmentManager.findFragmentByTag(topFragmentTag)
                    as PetrolStationDetailsFragment
        }

        val bottomFragmentTag = savedInstanceState.getString(KEY_BOTTOM_FRAGMENT)
        if (bottomFragmentTag != null) {
            bottomFragment = fragmentManager.findFragmentByTag(bottomFragmentTag)
                    as FuelsFragment
        }
    }

    private fun populateRecyclerView() {
        recyclerViewPetrolStations.visibility = VISIBLE
        recyclerViewPetrolStations.layoutManager = LinearLayoutManager(this)
        val adapter = PetrolStationAdapter(context = this, items = petrolStations!!,
                onItemClickListener = this)
        recyclerViewPetrolStations.adapter = adapter
    }

    private fun prepareUi() {
        when (uiMode) {
            AdaptableUi.Mode.INITIAL -> prepareInitialMode()
            AdaptableUi.Mode.CREATE -> prepareCreateMode()
            AdaptableUi.Mode.EDIT -> prepareEditMode()
            AdaptableUi.Mode.VIEW -> prepareViewMode()
        }
    }

    private fun loadTopFragment() {
        if (topFragment == null || uiMode == AdaptableUi.Mode.EDIT) {
            topFragment = PetrolStationDetailsFragment.newInstance(uiMode, petrolStation)
            replaceFragmentPlaceholder(R.id.placeholderTop, topFragment!!, TAG_TOP_FRAGMENT)
        }
    }

    private fun loadBottomFragment() {
        if (bottomFragment == null || uiMode == AdaptableUi.Mode.EDIT) {
            bottomFragment = FuelsFragment.newInstance(petrolStation?.fuels, uiMode)
            replaceFragmentPlaceholder(R.id.placeholderBottom, bottomFragment!!, TAG_BOTTOM_FRAGMENT)
        }
    }

    private fun loadFragments() {
        loadTopFragment()
        loadBottomFragment()
    }

    private fun removeTopFragment() {
        if (topFragment != null) {
            removeFragment(topFragment!!)
            topFragment = null
        }
    }

    private fun removeBottomFragment() {
        if (bottomFragment != null) {
            removeFragment(bottomFragment!!)
            bottomFragment = null
        }
    }

    private fun removeFragments() {
        removeTopFragment()
        removeBottomFragment()
    }

    private fun checkDeleteAvailability() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        buttonDelete.visibility = if (petrolStation!!.owner == uid) VISIBLE
        else GONE
    }

    private fun handleFabClick() {
        when (uiMode) {
            AdaptableUi.Mode.INITIAL -> prepareCreateMode()
            AdaptableUi.Mode.CREATE,
            AdaptableUi.Mode.EDIT -> if (save()) prepareInitialMode()
            AdaptableUi.Mode.VIEW -> prepareEditMode()
        }
    }

    private fun save(): Boolean {
        val name = topFragment!!.getName()
        val address = if (topFragment!!.getAddress() == null) "" else topFragment!!.getAddress()
        val fuels = bottomFragment!!.getFuels()

        if (uiMode == AdaptableUi.Mode.CREATE) {
            val owner = FirebaseAuth.getInstance().currentUser!!.uid
            petrolStation = PetrolStation()
            petrolStation!!.owner = owner
        }

        petrolStation!!.name = name
        petrolStation!!.address = address!!
        if (fuels != null) petrolStation!!.fuels = fuels

        return if (petrolStation!!.allFieldsAreValid()) {
            PetrolStationDatabase.insertOrUpdate(petrolStation!!, this)
            true
        } else {
            showInformationDialogue(R.string.information, R.string.all_fields_are_required)
            false
        }
    }

    private fun promptDelete() {
        showQuestionDialogue(R.string.delete_petrol_station, R.string.are_you_sure, positiveFunc = {
            val stationToDelete = petrolStation!!
            PetrolStationDatabase.delete(petrolStation!!, OnCompleteListener {
                petrolStations!!.remove(stationToDelete)
                recyclerViewPetrolStations.adapter.notifyDataSetChanged()
                prepareInitialMode()
            })
        }, negativeFunc = { })
    }

}