package com.braincorp.petrolwatcher

import com.braincorp.petrolwatcher.database.DatabaseManager
import com.braincorp.petrolwatcher.database.MockDatabaseManager
import com.braincorp.petrolwatcher.feature.auth.authenticator.Authenticator
import com.braincorp.petrolwatcher.feature.auth.authenticator.MockAuthenticator
import com.braincorp.petrolwatcher.feature.auth.imageHandler.ImageHandler
import com.braincorp.petrolwatcher.feature.auth.imageHandler.MockImageHandler

/**
 * The dependencies used in tests
 */
class TestDependencyInjection : DependencyInjection() {

    /**
     * Gets the authenticator
     *
     * @return the authenticator
     */
    override fun getAuthenticator(): Authenticator = MockAuthenticator

    /**
     * Gets the image handler
     *
     * @return the image handler
     */
    override fun getImageHandler(): ImageHandler = MockImageHandler

    /**
     * Gets the database manager
     *
     * @return the database manager
     */
    override fun getDatabaseManager(): DatabaseManager = MockDatabaseManager

    /**
     * Gets the vehicles API base URL
     *
     * @return the base URL
     */
    override fun getVehiclesApiBaseUrl(): String = "/api/"

}