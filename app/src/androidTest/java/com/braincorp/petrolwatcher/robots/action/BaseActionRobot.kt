package com.braincorp.petrolwatcher.robots.action

import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.uiautomator.UiDevice

open class BaseActionRobot {

    fun hideKeyboard(): BaseActionRobot {
        closeSoftKeyboard()
        return this
    }

    fun restoreDeviceOrientation(): BaseActionRobot {
        UiDevice.getInstance(getInstrumentation()).setOrientationNatural()
        return this
    }

    fun rotateDeviceClockwise(): BaseActionRobot {
        UiDevice.getInstance(getInstrumentation()).setOrientationRight()
        return this
    }

    fun wait(millis: Long = 200L): BaseActionRobot {
        Thread.sleep(millis)
        return this
    }

}