package com.braincorp.petrolwatcher.base

interface BaseContract {
    /**
     * Base MVP view
     *
     * @param T the type of the presenter
     */
    interface View<T: Presenter> {
        val presenter: T
    }

    /**
     * Base MVP presenter
     */
    interface Presenter
}