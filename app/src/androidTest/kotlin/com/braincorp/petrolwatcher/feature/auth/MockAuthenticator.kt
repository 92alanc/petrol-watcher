package com.braincorp.petrolwatcher.feature.auth

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.TestActivity
import com.braincorp.petrolwatcher.feature.auth.authenticator.Authenticator
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import org.mockito.Mock
import org.mockito.Mockito.mock

object MockAuthenticator : Authenticator {

    @Mock
    private val googleAuthResult = mock(AuthResult::class.java)

    @Mock
    private val facebookLoginResult = mock(LoginResult::class.java)

    @Mock
    override val facebookCallbackManager: CallbackManager = mock(CallbackManager::class.java)

    @Mock
    private val firebaseUser = mock(FirebaseUser::class.java)

    var authSuccess = true

    var userLoggedIn = false

    /**
     * Signs in with e-mail and password
     *
     * @param email the e-mail address
     * @param password the password
     * @param onSuccessListener the success listener
     * @param onFailureListener the failure listener
     */
    override fun signIn(email: String, password: String,
                        onSuccessListener: OnSuccessListener<AuthResult>,
                        onFailureListener: OnFailureListener) {
        if (email == "test123@test.com" && password == "abcd1234")
            onSuccessListener.onSuccess(googleAuthResult)
        else
            onFailureListener.onFailure(Exception())
    }

    /**
     * Creates an e-mail and password based account
     *
     * @param email the e-mail address
     * @param password the password
     * @param onSuccessListener the success listener
     * @param onFailureListener the failure listener
     */
    override fun signUp(email: String, password: String,
                        onSuccessListener: OnSuccessListener<AuthResult>,
                        onFailureListener: OnFailureListener) {
        if (email == "test123@test.com" && password == "abcd1234")
            onSuccessListener.onSuccess(googleAuthResult)
        else
            onFailureListener.onFailure(Exception())
    }

    /**
     * Signs in with a Google account
     *
     * @param activity the activity
     * @param onConnectionFailedListener the listener for connection
     *                                   failure events
     */
    override fun signInWithGoogle(activity: AppCompatActivity,
                                  onConnectionFailedListener: GoogleApiClient.OnConnectionFailedListener) : Intent {
        return Intent(activity, TestActivity::class.java)
    }

    /**
     * Signs in with a Facebook account
     *
     * @param activity the activity
     * @param callback the Facebook async authentication response
     *                 handler
     */
    override fun signInWithFacebook(activity: AppCompatActivity,
                                    callback: FacebookCallback<LoginResult>) {
        if (authSuccess)
            callback.onSuccess(facebookLoginResult)
        else
            callback.onError(FacebookException())
    }

    /**
     * Determines whether a Google sign in intent
     * is successful
     *
     * @param intent the Google sign in intent
     *
     * @return true if positive, otherwise false
     */
    override fun isGoogleSignInSuccessful(intent: Intent?): Boolean = authSuccess

    /**
     * Gets the currently logged in user, if any
     *
     * @return the currently logged in user
     */
    override fun getCurrentUser(): FirebaseUser? {
        return if (userLoggedIn) firebaseUser
        else null
    }

}