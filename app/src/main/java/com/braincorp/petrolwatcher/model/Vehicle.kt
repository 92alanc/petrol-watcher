package com.braincorp.petrolwatcher.model

data class Vehicle(var name: String, var fuelTypes: Array<FuelType>,
                   var year: Int, var kmPerLitre: Float) {

    val id = "$name $year - ${hashCode()}"

    override fun equals(other: Any?): Boolean {
        val sameObject = other is Vehicle
        return if (other != null) {
            val otherVehicle = other as Vehicle
            val sameName = name == otherVehicle.name
            val sameFuelType = fuelTypes
                    .filterIndexed { i, fuelType -> fuelType != otherVehicle.fuelTypes[i] }
                    .none()
            val sameYear = year == otherVehicle.year
            val sameKmPerLitre = kmPerLitre == otherVehicle.kmPerLitre

            sameName && sameFuelType && sameYear && sameKmPerLitre
        } else {
            sameObject
        }
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()
        map["id"] = id
        map["name"] = name
        map["fuel_types"] = fuelTypes
        map["year"] = year
        map["km_per_litre"] = kmPerLitre
        return map
    }

}