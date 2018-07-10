package com.braincorp.petrolwatcher.feature.auth

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.contract.MainContract
import com.braincorp.petrolwatcher.feature.auth.model.AuthErrorType
import com.braincorp.petrolwatcher.feature.auth.presenter.MainActivityPresenter
import com.braincorp.petrolwatcher.feature.auth.utils.getActiveAccount
import com.braincorp.petrolwatcher.feature.auth.utils.getGoogleApiClient
import com.braincorp.petrolwatcher.feature.auth.utils.signInWithFacebook
import com.braincorp.petrolwatcher.feature.auth.utils.signInWithGoogle
import com.braincorp.petrolwatcher.utils.startAuthenticationErrorActivity
import com.braincorp.petrolwatcher.utils.startEmailSignInActivity
import com.facebook.CallbackManager
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_main.*

/**
 * The app's main activity.
 * Here is where the user can sign in either via
 * Google, Facebook or e-mail and password
 */
class MainActivity : AppCompatActivity(), View.OnClickListener, MainContract.View {

    private companion object {
        const val REQUEST_CODE_GOOGLE_SIGN_IN = 3892
    }

    override val presenter: MainActivityPresenter = MainActivityPresenter(view = this)

    private val callbackManager = CallbackManager.Factory.create()

    private lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        googleApiClient = getGoogleApiClient(presenter)
        setupButtons()
    }

    override fun onStart() {
        super.onStart()
        if (getActiveAccount() != null)
            showMap()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // When a Google sign in response is received
        if (requestCode == REQUEST_CODE_GOOGLE_SIGN_IN)
            presenter.handleGoogleSignInResult(data)

        if (callbackManager.onActivityResult(requestCode, resultCode, data))
            return
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_sign_in_google -> signInWithGoogle(googleApiClient, REQUEST_CODE_GOOGLE_SIGN_IN)
            R.id.bt_sign_in_facebook -> signInWithFacebook(callbackManager, presenter)
            R.id.bt_sign_in_email -> startEmailSignInActivity()
        }
    }

    /**
     * Shows an authentication error screen
     *
     * @param errorType the authentication error type
     */
    override fun showErrorScreen(errorType: AuthErrorType) {
        startAuthenticationErrorActivity(errorType)
    }

    /**
     * Shows the map activity
     */
    override fun showMap() {
        // TODO: show map activity
    }

    private fun setupButtons() {
        bt_sign_in_google.setOnClickListener(this)
        bt_sign_in_facebook.setOnClickListener(this)
        bt_sign_in_email.setOnClickListener(this)
    }

}