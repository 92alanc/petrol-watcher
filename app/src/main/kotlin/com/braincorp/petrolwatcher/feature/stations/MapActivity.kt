package com.braincorp.petrolwatcher.feature.stations

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.authenticator.OnUserDataFoundListener
import com.braincorp.petrolwatcher.feature.auth.utils.fillImageView
import com.braincorp.petrolwatcher.feature.stations.contract.MapActivityContract
import com.braincorp.petrolwatcher.feature.stations.presenter.MapActivityPresenter
import com.braincorp.petrolwatcher.utils.startMainActivity
import com.braincorp.petrolwatcher.utils.startProfileActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.app_bar_map.*

/**
 * The activity where the map containing all petrol stations
 * nearby is shown
 */
class MapActivity : AppCompatActivity(),
        View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        MapActivityContract.View {

    override lateinit var presenter: MapActivityContract.Presenter

    private val authenticator = DependencyInjection.authenticator
    private val mapController = DependencyInjection.mapController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        setSupportActionBar(toolbar)
        presenter = MapActivityPresenter(view = this)
        fab.setOnClickListener(this)
        mapController.startMap(supportFragmentManager, R.id.map, this)
    }

    override fun onStart() {
        super.onStart()
        bindNavigationDrawer()
    }

    override fun onClick(v: View) {
        when (v.id) {

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_profile -> startProfileActivity()
            R.id.item_sign_out -> presenter.signOut()
        }

        return true
    }

    override fun onMapReady(map: GoogleMap) {

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

        imgProfile.setOnClickListener(this)

        authenticator.getUserData(object: OnUserDataFoundListener {
            override fun onUserDataFound(displayName: String?, profilePictureUri: Uri?) {
                txtName.text = displayName
                fillImageView(profilePictureUri, imgProfile, progressBar = progressBar)
            }
        })
    }

}