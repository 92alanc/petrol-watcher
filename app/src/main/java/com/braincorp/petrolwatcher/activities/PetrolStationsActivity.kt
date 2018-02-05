package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.adapters.PetrolStationAdapter
import com.braincorp.petrolwatcher.database.PetrolStationDatabase
import com.braincorp.petrolwatcher.listeners.OnItemClickListener
import com.braincorp.petrolwatcher.model.PetrolStation
import com.braincorp.petrolwatcher.model.UiMode
import com.braincorp.petrolwatcher.utils.removeFragment
import com.braincorp.petrolwatcher.utils.showErrorDialogue
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_petrol_stations.*
import kotlinx.android.synthetic.main.content_petrol_stations.*

class PetrolStationsActivity : BaseActivity(), View.OnClickListener,
        OnItemClickListener, ValueEventListener {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, PetrolStationsActivity::class.java)
        }
    }

    private var fragment: Fragment? = null
    private var petrolStations: Array<PetrolStation>? = null
    private var uiMode: UiMode? = null

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
            R.id.fabVehicles -> TODO("not implemented")
        }
    }

    override fun onItemClick(position: Int) {
        val petrolStation = petrolStations!![position]
        TODO("not implemented")
    }

    override fun onCancelled(error: DatabaseError?) {
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

    private fun populateRecyclerView(items: Array<PetrolStation>) {
        recyclerViewPetrolStations.visibility = VISIBLE
        recyclerViewPetrolStations.layoutManager = LinearLayoutManager(this)
        val adapter = PetrolStationAdapter(context = this, items = items,
                onItemClickListener = this)
        recyclerViewPetrolStations.adapter = adapter
    }

    private fun prepareInitialMode() {
        uiMode = null
        fabPetrolStations.setImageResource(R.drawable.ic_add)

        removeFragment()
        PetrolStationDatabase.select(valueEventListener = this)
    }

    private fun removeFragment() {
        if (fragment != null) removeFragment(fragment!!)
        placeholderPetrolStations.visibility = GONE
    }

}