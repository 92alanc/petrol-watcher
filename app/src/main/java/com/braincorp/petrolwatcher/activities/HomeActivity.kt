package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity.START
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.braincorp.petrolwatcher.utils.showInformationDialogue
import com.braincorp.petrolwatcher.utils.showQuestionDialogue
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        bindNavigationDrawer()
        fabHome.setOnClickListener(this)
        checkEmailVerification()
    }

    override fun onBackPressed() {
        if (drawer_home.isDrawerOpen(START))
            drawer_home.closeDrawer(START)
        else
            finish()
    }

    override fun onClick(v: View?) {

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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

        val headerView = navigationView.getHeaderView(0)
        val imageViewProfile = headerView.findViewById<CircleImageView>(R.id.imageViewProfile)
        val textViewDisplayName = headerView.findViewById<TextView>(R.id.textViewDisplayName)
        val textViewEmail = headerView.findViewById<TextView>(R.id.textViewEmail)

        Picasso.with(this).load(AuthenticationManager.USER?.photoUrl)
                .placeholder(R.drawable.ic_profile)
                .into(imageViewProfile)

        textViewDisplayName.text = AuthenticationManager.USER?.displayName
        textViewEmail.text = AuthenticationManager.USER?.email
    }

    private fun checkEmailVerification() {
        val isEmailVerified = AuthenticationManager.USER?.isEmailVerified
        if (isEmailVerified != null && !isEmailVerified) {
            showInformationDialogue(R.string.email_not_verified,
                    R.string.message_email_not_verified, {
                launchLoginActivity()
                finish()
            })
        }
    }

    private fun launchLoginActivity() {
        val intent = LoginActivity.getIntent(context = this)
        startActivity(intent)
        finish()
    }

    private fun launchProfileActivity() {
        val intent = ProfileActivity.getIntent(context = this, newAccount = false)
        startActivity(intent)
    }

    private fun promptSignOut() {
        showQuestionDialogue(R.string.sign_out, R.string.question_sign_out,
                positiveFunc = {
                    AuthenticationManager.signOut()
                    launchLoginActivity()
                },
                negativeFunc = { })
    }

}
