package com.braincorp.petrolwatcher.ui

/**
 * A UI that can adapt between different states
 * by applying the principle of state machines
 */
interface MultiStateUi {

    var uiState: State

    /**
     * Prepares the creation state
     */
    fun prepareCreationState()

    /**
     * Prepares the edit state
     */
    fun prepareEditState()

    /**
     * Prepares the initial state
     */
    fun prepareInitialState()

    /**
     * Prepares the read-only state
     */
    fun prepareReadOnlyState()

    /**
     * Prepares the UI based on its state
     *
     * @param uiState the UI state
     */
    fun prepareUi(uiState: State) {
        when (uiState) {
            State.CREATION -> prepareCreationState()
            State.EDIT -> prepareEditState()
            State.INITIAL -> prepareInitialState()
            State.READ_ONLY -> prepareReadOnlyState()
        }
    }

    enum class State {
        CREATION,
        EDIT,
        INITIAL,
        READ_ONLY
    }

}