package com.braincorp.petrolwatcher.robots.action

import android.support.test.espresso.Espresso.closeSoftKeyboard
import com.braincorp.petrolwatcher.authentication.AuthenticationManager

open class BaseActionRobot {

    fun hideKeyboard(): BaseActionRobot {
        closeSoftKeyboard()
        return this
    }

    fun signIn(): BaseActionRobot {
        val email = "alcam.ukdev@gmail.com"
        val password = "abcd1234"
        AuthenticationManager.signIn(email, password)
        return this
    }

    fun wait(millis: Long = 200L): BaseActionRobot {
        Thread.sleep(millis)
        return this
    }

}