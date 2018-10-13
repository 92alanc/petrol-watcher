package com.braincorp.petrolwatcher.feature.stations

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity.START
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.authenticator.OnUserDataFoundListener
import com.braincorp.petrolwatcher.feature.stations.contract.MapActivityContract
import com.braincorp.petrolwatcher.feature.stations.listeners.OnPetrolStationsFoundListener
import com.braincorp.petrolwatcher.feature.stations.map.OnCurrentLocationFoundListener
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.braincorp.petrolwatcher.feature.stations.presenter.MapActivityPresenter
import com.braincorp.petrolwatcher.utils.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.app_bar_map.*
import java.util.*

/**
 * The activity where the map containing all petrol stations
 * nearby is shown
 */
class MapActivity : AppCompatActivity(),
        View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        MapActivityContract.View,
        OnPetrolStationsFoundListener,
        GoogleMap.OnMarkerClickListener,
        OnCurrentLocationFoundListener {

    private companion object {
        const val KEY_IS_MAP_READY = "is_map_ready"
        const val REQUEST_CODE_LOCATION = 1234
        const val TAG = "PETROL_WATCHER"
    }

    override lateinit var presenter: MapActivityContract.Presenter

    private var isMapReady = false
    private var petrolStations = ArrayList<PetrolStation>()
    private var currentLocation: LatLng? = null
    private lateinit var map: GoogleMap

    private val authenticator = DependencyInjection.authenticator
    private val mapController = DependencyInjection.mapController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        setSupportActionBar(toolbar)
        presenter = MapActivityPresenter(view = this)
        fab.setOnClickListener(this)
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
        if (!isMapReady)
            mapController.startMap(supportFragmentManager, R.id.map, this)
    }

    override fun onStart() {
        super.onStart()
        bindNavigationDrawer()
    }

    override fun onBackPressed() {
        if (drawer_map.isDrawerOpen(START))
            drawer_map.closeDrawer(START)
        else
            super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(KEY_IS_MAP_READY, isMapReady)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsGranted = grantResults.all { it == PERMISSION_GRANTED }
        if (requestCode == REQUEST_CODE_LOCATION && permissionsGranted)
            enableLocation(map)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab -> startCreatePetrolStationActivity(currentLocation = currentLocation)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_stations_nearby -> startList()
            R.id.item_profile -> startProfileActivity(editMode = true)
            R.id.item_my_vehicles -> startVehicleListActivity()
            R.id.item_cost_planning -> startCostPlanningActivity()
            R.id.item_sign_out -> signOut()
        }

        return true
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map

        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))

        if (SDK_INT >= M) {
            if (hasLocationPermission()) {
                enableLocation(map)
            } else {
                requestPermissions(arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION),
                        REQUEST_CODE_LOCATION)
            }
        } else {
            enableLocation(map)
        }

        presenter.fetchPetrolStations(onPetrolStationsFoundListener = this)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker == null) return false

        val petrolStation = petrolStations.first {
            it.latLng == marker.position
        }

        startPetrolStationDetailsActivity(petrolStation)
        return true
    }

    /**
     * Event triggered when a list of
     * petrol stations is found
     *
     * @param petrolStations the petrol stations found
     */
    override fun onPetrolStationsFound(petrolStations: ArrayList<PetrolStation>) {
        this.petrolStations = petrolStations
        DependencyInjection.mapController.addPetrolStationsToMap(map,
                petrolStations,
                onMarkerClickListener = this)
    }

    /**
     * Function to be triggered when the current address
     * is found
     *
     * @param address the address
     * @param city the city
     * @param country the country
     * @param latLng the latitude and longitude
     * @param locale the locale
     */
    override fun onCurrentLocationFound(address: String,
                                        city: String,
                                        country: String,
                                        latLng: LatLng,
                                        locale: Locale) {
        currentLocation = latLng
    }

    /**
     * Starts the main activity
     */
    override fun startMainActivity() {
        startMainActivity(finishCurrent = false)
        finishAffinity()
    }

    private fun bindNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(this, drawer_map, toolbar,
                R.string.description_navigation_open, R.string.description_navigation_closed)
        drawer_map.addDrawerListener(toggle)
        toggle.syncState()

        navigation_view.setNavigationItemSelectedListener(this)

        val headerView = navigation_view.getHeaderView(0)
        val imgProfile = headerView.findViewById<CircleImageView>(R.id.img_profile)
        val progressBar = headerView.findViewById<ProgressBar>(R.id.progress_bar)
        val txtName = headerView.findViewById<TextView>(R.id.txt_name)
        val txtVersion = headerView.findViewById<TextView>(R.id.txt_version)

        imgProfile.setOnClickListener(this)

        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        val versionName = packageInfo.versionName
        txtVersion.text = getString(R.string.version_format, versionName)

        authenticator.getUserData(object: OnUserDataFoundListener {
            override fun onUserDataFound(displayName: String?, profilePictureUri: Uri?) {
                txtName.text = displayName
                DependencyInjection.imageHandler.fillImageView(profilePictureUri, imgProfile,
                        progressBar = progressBar,
                        placeholderRes = R.drawable.ic_profile)
            }
        })
    }

    private fun enableLocation(map: GoogleMap) {
        try {
            map.isMyLocationEnabled = true
            mapController.zoomToDeviceLocation(map,
                    context = this,
                    onCurrentLocationFoundListener = this)
        } catch (e: SecurityException) {
            Log.d(TAG, "Error enabling location", e)
        }
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        isMapReady = savedInstanceState.getBoolean(KEY_IS_MAP_READY)
    }

    private fun startList() {
        startPetrolStationListActivity(currentLocation)
    }

    private fun signOut() {
        AlertDialog.Builder(this)
                .setTitle(R.string.sign_out)
                .setMessage(R.string.sign_out_confirmation)
                .setIcon(R.drawable.ic_sign_out)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes) { _, _ ->
                    presenter.signOut()
                }.show()
    }

}