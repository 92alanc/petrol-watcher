package com.braincorp.petrolwatcher.utils

import android.annotation.SuppressLint
import android.support.v4.app.FragmentManager
import com.braincorp.petrolwatcher.base.BaseDialogueFragment

/**
 * Shows a dialogue fragment
 *
 * @param fragmentManager the fragment manager
 * @param dialogueFragment the dialogue fragment
 */
// The transaction is committed by the show function in the dialogue fragment
@SuppressLint("CommitTransaction")
fun showDialogueFragment(fragmentManager: FragmentManager,
                         dialogueFragment: BaseDialogueFragment) {
    val transaction = fragmentManager.beginTransaction()
    val previous = fragmentManager.findFragmentByTag(dialogueFragment.tag())

    previous?.let {
        transaction.remove(it)
    }
    transaction.addToBackStack(null)

    dialogueFragment.show(fragmentManager, dialogueFragment.tag())
}