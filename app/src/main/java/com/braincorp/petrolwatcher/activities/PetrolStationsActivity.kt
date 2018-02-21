package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.listeners.OnFragmentInflatedListener
import com.braincorp.petrolwatcher.listeners.OnItemClickListener
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class PetrolStationsActivity : AppCompatActivity(), View.OnClickListener,
        OnItemClickListener, AdaptableUi, ValueEventListener,
        OnCompleteListener<Void>, OnFragmentInflatedListener {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, PetrolStationsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petrol_stations)
    }

    override fun onClick(v: View?) {
        TODO("not implemented")
    }

    override fun onItemClick(position: Int) {
        TODO("not implemented")
    }

    override fun onDataChange(snapshot: DataSnapshot?) {
        TODO("not implemented")
    }

    override fun onCancelled(error: DatabaseError?) {
        TODO("not implemented")
    }

    override fun onComplete(task: Task<Void>) {
        TODO("not implemented")
    }

    override fun onFragmentInflated(fragment: Fragment) {
        TODO("not implemented")
    }

    override fun prepareInitialMode() {
        TODO("not implemented")
    }

    override fun prepareCreateMode() {
        TODO("not implemented")
    }

    override fun prepareEditMode() {
        TODO("not implemented")
    }

    override fun prepareViewMode() {
        TODO("not implemented")
    }

}