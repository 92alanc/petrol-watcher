package com.braincorp.petrolwatcher.base

interface BaseContract {
    interface View<T: Presenter> {
        val presenter: T
    }

    interface Presenter
}