package com.braincorp.petrolwatcher

import com.braincorp.petrolwatcher.database.DatabaseManager
import com.braincorp.petrolwatcher.feature.auth.authenticator.Authenticator
import com.braincorp.petrolwatcher.feature.auth.imageHandler.ImageHandler
import com.braincorp.petrolwatcher.feature.stations.map.MapController

/**
 * The dependencies used in the app
 */
object DependencyInjection {

    /**
     * Initialises the dependency injection
     *
     * @param config the configuration
     */
    fun init(config: Config) {
        authenticator = config.authenticator
        imageHandler = config.imageHandler
        databaseManager = config.databaseManager
        mapController = config.mapController
        vehicleApiBaseUrl = config.vehicleApiBaseUrl
    }

    lateinit var authenticator: Authenticator
    lateinit var imageHandler: ImageHandler
    lateinit var databaseManager: DatabaseManager
    lateinit var mapController: MapController
    lateinit var vehicleApiBaseUrl: String

    /**
     * Configuration for the dependency injection
     *
     * @param authenticator the authenticator
     * @param imageHandler the image handler
     * @param databaseManager the database manager
     * @param mapController the map controller
     * @param vehicleApiBaseUrl the vehicle API base URL
     */
    data class Config(val authenticator: Authenticator,
                      val imageHandler: ImageHandler,
                      val databaseManager: DatabaseManager,
                      val mapController: MapController,
                      val vehicleApiBaseUrl: String)

}