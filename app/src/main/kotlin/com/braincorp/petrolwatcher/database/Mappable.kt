package com.braincorp.petrolwatcher.database

/**
 * An object that can be converted to a map
 */
interface Mappable {
    val id: String

    /**
     * Converts the object to a map
     *
     * @return the map
     */
    fun toMap(): Map<String, Any>
}