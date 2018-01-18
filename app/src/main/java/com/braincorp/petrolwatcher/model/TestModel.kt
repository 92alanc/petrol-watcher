package com.braincorp.petrolwatcher.model

data class TestModel(val name: String,
                     val age: Int,
                     val fuel: Fuel) {

    val id = "$name$age"

    fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()
        map["id"] = id
        map["name"] = name
        map["age"] = age
        map["fuel"] = fuel
        return map
    }

}