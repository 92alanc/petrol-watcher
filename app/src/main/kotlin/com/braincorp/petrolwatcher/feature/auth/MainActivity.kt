package com.braincorp.petrolwatcher.feature.auth

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.auth.contract.MainContract
import com.braincorp.petrolwatcher.feature.auth.model.AuthErrorType
import com.braincorp.petrolwatcher.feature.auth.presenter.MainActivityPresenter
import com.braincorp.petrolwatcher.utils.startAuthenticationErrorActivity
import com.braincorp.petrolwatcher.utils.startEmailSignInActivity
import com.braincorp.petrolwatcher.utils.startMapActivity
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

    override lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainActivityPresenter(view = this,
                authenticator = DependencyInjection.authenticator)
        setupButtons()
    }

    override fun onStart() {
        super.onStart()
        if (presenter.isLoggedIn())
            showMap()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_GOOGLE_SIGN_IN)
            presenter.handleGoogleSignInIntent(data)

        if (presenter.callbackManager.onActivityResult(requestCode, resultCode, data))
            return
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_sign_in_google -> presenter.signInWithGoogle(this, REQUEST_CODE_GOOGLE_SIGN_IN)
            R.id.bt_sign_in_facebook -> presenter.signInWithFacebook(this)
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
        startMapActivity(finishCurrent = true)
    }

    private fun setupButtons() {
        bt_sign_in_google.setOnClickListener(this)
        bt_sign_in_facebook.setOnClickListener(this)
        bt_sign_in_email.setOnClickListener(this)
    }

}