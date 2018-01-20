package com.braincorp.petrolwatcher.robots.action

import android.support.test.espresso.Espresso.closeSoftKeyboard

open class BaseActionRobot {

    fun hideKeyboard(): BaseActionRobot {
        closeSoftKeyboard()
        return this
    }

    fun wait(millis: Long = 200L): BaseActionRobot {
        Thread.sleep(millis)
        return this
    }

}