package com.braincorp.petrolwatcher.robots

import android.content.Context
import android.support.annotation.IdRes
import android.support.test.InstrumentationRegistry.getTargetContext
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*

open class BaseRobot {

    val context: Context = getTargetContext()

    fun click(@IdRes viewId: Int): BaseRobot {
        onView(withId(viewId)).perform(ViewActions.click())
        return this
    }

    fun hideKeyboard(): BaseRobot {
        closeSoftKeyboard()
        return this
    }

    fun wait(millis: Long = 200L): BaseRobot {
        Thread.sleep(millis)
        return this
    }

    fun checkIfVisible(@IdRes viewId: Int): BaseRobot {
        onView(withId(viewId)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        return this
    }

    fun checkIfNotVisible(@IdRes viewId: Int): BaseRobot {
        onView(withId(viewId)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        return this
    }

    fun pressBackButton(): BaseRobot {
        pressBack()
        return this
    }

}