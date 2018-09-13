package com.braincorp.petrolwatcher.feature.stations

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.stations.adapter.FuelAdapter
import com.braincorp.petrolwatcher.feature.stations.contract.CreatePetrolStationActivityContract
import com.braincorp.petrolwatcher.feature.stations.map.OnCurrentLocationFoundListener
import com.braincorp.petrolwatcher.feature.stations.model.Fuel
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.braincorp.petrolwatcher.feature.stations.presenter.CreatePetrolStationActivityPresenter
import com.braincorp.petrolwatcher.feature.stations.utils.updateFuelSet
import com.braincorp.petrolwatcher.ui.OnItemClickListener
import com.braincorp.petrolwatcher.utils.hasLocationPermission
import com.braincorp.petrolwatcher.utils.startFuelActivity
import com.braincorp.petrolwatcher.utils.startPetrolStationListActivity
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_create_petrol_station.*
import kotlinx.android.synthetic.main.content_create_petrol_station.*
import java.util.*

/**
 * The activity where petrol stations are created
 */
class CreatePetrolStationActivity : AppCompatActivity(),
        CreatePetrolStationActivityContract.View,
        View.OnClickListener,
        PlaceSelectionListener,
        OnCurrentLocationFoundListener,
        OnItemClickListener {

    companion object {
        private const val KEY_HAS_LOCATION_PERMISSION = "has_location_permission"
        private const val KEY_CURRENT_LOCATION = "current_location"
        private const val KEY_PETROL_STATION = "petrol_station"
        private const val REQUEST_CODE_LOCATION = 1234
        private const val REQUEST_CODE_FUEL = 5678
        private const val TAG = "PETROL_WATCHER"

        fun intent(context: Context, currentLocation: LatLng?): Intent {
            return Intent(context, CreatePetrolStationActivity::class.java)
                    .putExtra(KEY_CURRENT_LOCATION, currentLocation)
        }
    }

    override lateinit var presenter: CreatePetrolStationActivityContract.Presenter

    private var petrolStation = PetrolStation()
    private var hasLocationPermission = false
    private var currentLocation: LatLng? = null
    private lateinit var placeAutocompleteAddress: PlaceAutocompleteFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_petrol_station)
        setupToolbar()
        bindPlaceAutocompleteFragment()
        presenter = CreatePetrolStationActivityPresenter(view = this)
        fab.setOnClickListener(this)
        bt_add_fuel.setOnClickListener(this)
        setupLocationButton()
        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState)
        } else {
            hasLocationPermission = intent.getBooleanExtra(KEY_HAS_LOCATION_PERMISSION, false)
            currentLocation = intent.getParcelableExtra(KEY_CURRENT_LOCATION)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putParcelable(KEY_PETROL_STATION, petrolStation)
            putBoolean(KEY_HAS_LOCATION_PERMISSION, hasLocationPermission)
            putParcelable(KEY_CURRENT_LOCATION, currentLocation)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsGranted = grantResults.all { it == PERMISSION_GRANTED }
        if (requestCode == REQUEST_CODE_LOCATION && permissionsGranted)
            enableLocationButton()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_FUEL && resultCode == RESULT_OK) {
            val fuel = data!!.getParcelableExtra<Fuel>(FuelActivity.KEY_DATA)
            updateFuelSet(petrolStation.fuels, fuel)
            updateRecyclerView()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab -> save()
            R.id.bt_location -> useCurrentLocation()
            R.id.bt_add_fuel -> startFuelActivity(requestCode = REQUEST_CODE_FUEL)
        }
    }

    /**
     * Function triggered when a RecyclerView item is clicked
     *
     * @param position the position in the list
     */
    override fun onItemClick(position: Int) {
        val fuel = petrolStation.fuels.elementAt(position)
        startFuelActivity(fuel, REQUEST_CODE_FUEL)
    }

    /**
     * Shows the petrol station list
     */
    override fun showPetrolStationList() {
        startPetrolStationListActivity(finishCurrent = true,
                currentLocation = currentLocation)
    }

    /**
     * Shows an invalid petrol station dialogue
     */
    override fun showInvalidPetrolStationDialogue() {
        AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(R.string.invalid_petrol_station_data)
                .setIcon(R.drawable.ic_error)
                .setNeutralButton(R.string.ok, null)
                .show()
    }

    override fun onPlaceSelected(place: Place?) {
        if (place?.address != null) {
            petrolStation.address = place.address.toString()
            petrolStation.latLng = place.latLng
        }
    }

    /**
     * Function to be triggered when the current address
     * is found
     *
     * @param address the address
     * @param latLng the latitude and longitude
     * @param locale the locale
     */
    override fun onCurrentLocationFound(address: String, latLng: LatLng, locale: Locale) {
        placeAutocompleteAddress.setText(address)

        petrolStation.address = address
        petrolStation.latLng = latLng
        petrolStation.locale = locale
    }

    override fun onError(error: Status?) {
        Log.e(TAG, error?.statusMessage)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun save() {
        petrolStation.name = edt_name.text.toString()
        presenter.savePetrolStation(petrolStation)
    }

    private fun bindPlaceAutocompleteFragment() {
        placeAutocompleteAddress = fragmentManager.findFragmentById(R.id.edt_address) as PlaceAutocompleteFragment
        placeAutocompleteAddress.setHint(getString(R.string.address))
        placeAutocompleteAddress.setOnPlaceSelectedListener(this)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        with(savedInstanceState) {
            petrolStation = getParcelable(KEY_PETROL_STATION)
            hasLocationPermission = getBoolean(KEY_HAS_LOCATION_PERMISSION)
            currentLocation = getParcelable(KEY_CURRENT_LOCATION)
            placeAutocompleteAddress.setText(petrolStation.address)
        }
    }

    private fun setupLocationButton() {
        if (SDK_INT >= M) {
            if (hasLocationPermission())
                enableLocationButton()
            else
                requestPermissions(arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
        } else {
            enableLocationButton()
        }
    }

    private fun enableLocationButton() {
        bt_location.visibility = VISIBLE
        bt_location.setOnClickListener(this)
    }

    private fun useCurrentLocation() {
        presenter.getCurrentLocation(context = this, onCurrentLocationFoundListener =  this)
    }

    private fun updateRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        val adapter = FuelAdapter(onItemClickListener = this,
                data = petrolStation.fuels,
                locale = petrolStation.locale)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter
    }

}