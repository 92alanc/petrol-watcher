package com.braincorp.petrolwatcher.model

interface AdaptableUi {

    fun prepareInitialMode()

    fun prepareCreateMode()

    fun prepareEditMode()

    fun prepareViewMode()

    enum class Mode {

        INITIAL,
        CREATE,
        EDIT,
        VIEW

    }

}