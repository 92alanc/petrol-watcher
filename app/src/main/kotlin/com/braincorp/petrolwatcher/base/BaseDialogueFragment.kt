package com.braincorp.petrolwatcher.base

import android.support.v4.app.DialogFragment

/**
 * The base dialogue fragment
 */
abstract class BaseDialogueFragment : DialogFragment() {

    /**
     * Gets the dialogue tag
     *
     * @return the tag
     */
    abstract fun tag(): String

}