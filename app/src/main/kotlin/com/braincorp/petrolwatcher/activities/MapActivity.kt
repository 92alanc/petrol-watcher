package com.braincorp.petrolwatcher.activities

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity.START
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.braincorp.petrolwatcher.model.AdaptableUi
import com.braincorp.petrolwatcher.utils.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.app_bar_map.*

class MapActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, OnMapReadyCallback {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MapActivity::class.java)
        }
    }

    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        setSupportActionBar(toolbar)
        bindNavigationDrawer()

        fabMap.setOnClickListener(this)
        startMap()
    }

    override fun onStop() {
        super.onStop()
        if (drawer_home.isDrawerOpen(START))
            drawer_home.closeDrawer(START)
    }

    override fun onBackPressed() {
        if (drawer_home.isDrawerOpen(START)) drawer_home.closeDrawer(START)
        else super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_map, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.itemSettings -> launchSettingsActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOCATION) {
            val permissionGranted = grantResults[0] == PERMISSION_GRANTED
                    && grantResults[1] == PERMISSION_GRANTED
            if (permissionGranted) {
                fabMap.visibility = VISIBLE
                loadMapWithCurrentLocation(map)
            } else {
                fabMap.visibility = GONE
                loadMapWithoutCurrentLocation(map)
            }
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        applyMapTheme(map)

        if (SDK_INT >= M && !hasLocationPermission()) {
            fabMap.visibility = GONE
            requestPermissions(arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION),
                    REQUEST_CODE_LOCATION)
        } else {
            fabMap.visibility = VISIBLE
            loadMapWithCurrentLocation(map)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabMap -> {
                getCurrentLocation(OnCompleteListener {
                    val location = it.result
                    val latLng = LatLng(location.latitude, location.longitude)
                    map?.zoomToLocation(latLng)
                })
            }

            R.id.imageViewProfile -> {
                drawer_home.closeDrawer(START)
                launchProfileActivity()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemStationsNearby -> launchPetrolStationsActivity()
            R.id.itemProfile -> launchProfileActivity()
            R.id.itemSignOut -> promptSignOut()
        }
        return true
    }

    private fun bindNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(this, drawer_home, toolbar,
                R.string.description_navigation_open, R.string.description_navigation_closed)
        drawer_home.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        val user = FirebaseAuth.getInstance().currentUser

        val headerView = navigationView.getHeaderView(0)

        val imageViewProfile = headerView.findViewById<CircleImageView>(R.id.imageViewProfile)
        imageViewProfile.setOnClickListener(this)

        val progressBar = headerView.findViewById<ProgressBar>(R.id.progressBar) // TODO

        val textViewDisplayName = headerView.findViewById<TextView>(R.id.textViewDisplayName)
        val textViewEmail = headerView.findViewById<TextView>(R.id.textViewEmail)

        fillImageView(user?.photoUrl, imageViewProfile, progressBar = progressBar)

        textViewDisplayName.text = user?.displayName
        textViewEmail.text = user?.email
    }

    private fun launchLoginActivity() {
        val intent = LoginActivity.getIntent(context = this)
        startActivity(intent)
        finish()
    }

    private fun launchProfileActivity() {
        val intent = ProfileActivity.getIntent(context = this, uiMode = AdaptableUi.Mode.VIEW)
        startActivity(intent)
    }

    private fun launchPetrolStationsActivity() {
        val intent = PetrolStationsActivity.getIntent(context = this)
        startActivity(intent)
    }

    private fun launchSettingsActivity() {
        val intent = SettingsActivity.getIntent(context = this)
        startActivity(intent)
    }

    private fun promptSignOut() {
        showQuestionDialogue(R.string.sign_out, R.string.question_sign_out,
                positiveFunc = {
                    AuthenticationManager.signOut {
                        launchLoginActivity()
                        finish()
                    }
                },
                negativeFunc = { })
    }

    private fun startMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

}
