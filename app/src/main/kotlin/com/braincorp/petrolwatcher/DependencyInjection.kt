package com.braincorp.petrolwatcher

import com.braincorp.petrolwatcher.feature.auth.authenticator.Authenticator
import com.braincorp.petrolwatcher.feature.auth.authenticator.FirebaseAuthenticator
import com.braincorp.petrolwatcher.feature.auth.imageHandler.AppImageHandler
import com.braincorp.petrolwatcher.feature.auth.imageHandler.ImageHandler

/**
 * The dependencies used in the app
 */
open class DependencyInjection {

    /**
     * Gets the authenticator
     *
     * @return the authenticator
     */
    open fun getAuthenticator(): Authenticator = FirebaseAuthenticator()

    /**
     * Gets the image handler
     *
     * @return the image handler
     */
    open fun getImageHandler(): ImageHandler = AppImageHandler()

}