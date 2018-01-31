package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.model.UiMode
import kotlinx.android.synthetic.main.activity_vehicles.*

class VehiclesActivity : BaseActivity(), View.OnClickListener {

    // TODO: move the fields from this activity to a fragment

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, VehiclesActivity::class.java)
        }
    }

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

    private fun handleFabClick() {
        when (uiMode) {
            UiMode.VIEW -> prepareCreateMode()
            else -> save()
        }
    }

    private fun prepareUi() {
        when (uiMode) {
            UiMode.CREATE -> prepareCreateMode()
            UiMode.EDIT -> prepareEditMode()
            UiMode.VIEW -> prepareViewMode()
        }
    }

    private fun prepareCreateMode() {
        // TODO: implement
    }

    private fun prepareEditMode() {
        // TODO: implement
    }

    private fun prepareViewMode() {
        // TODO: implement
    }

    private fun save() {
        // TODO: implement
    }

}