package com.braincorp.petrolwatcher.robots

import android.support.annotation.IdRes
import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*

open class BaseRobot {

    fun hideKeyboard(): BaseRobot {
        closeSoftKeyboard()
        return this
    }

    fun wait(millis: Long = 200L): BaseRobot {
        Thread.sleep(millis)
        return this
    }

    fun checkIfVisible(@IdRes viewId: Int): BaseRobot {
        onView(withId(viewId)).check(matches(isDisplayed()))
        return this
    }

    fun checkIfNotVisible(@IdRes viewId: Int): BaseRobot {
        onView(withId(viewId)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        return this
    }

}