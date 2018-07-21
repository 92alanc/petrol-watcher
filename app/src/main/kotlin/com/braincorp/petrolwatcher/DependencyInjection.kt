package com.braincorp.petrolwatcher

import com.braincorp.petrolwatcher.database.AppDatabaseManager
import com.braincorp.petrolwatcher.database.DatabaseManager
import com.braincorp.petrolwatcher.feature.auth.authenticator.AppAuthenticator
import com.braincorp.petrolwatcher.feature.auth.authenticator.Authenticator
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
    open fun getAuthenticator(): Authenticator = AppAuthenticator()

    /**
     * Gets the image handler
     *
     * @return the image handler
     */
    open fun getImageHandler(): ImageHandler = AppImageHandler()

    /**
     * Gets the database manager
     *
     * @return the database manager
     */
    open fun getDatabaseManager(): DatabaseManager = AppDatabaseManager()

}