package com.braincorp.petrolwatcher.view

import android.app.Dialog
import android.content.Context
import android.support.annotation.StringRes
import com.braincorp.petrolwatcher.preferences.Configuration

class RadioGroupDialogueFactory {

    companion object {
        fun makeDialogue(context: Context,
                         @StringRes title: Int,
                         type: DialogueType,
                         data: Map<Configuration, Int?>): Dialog {
            val dialogue = when (type) {
                DialogueType.HORIZONTAL -> HorizontalRadioGroupDialogue(context)
                DialogueType.VERTICAL -> VerticalRadioGroupDialogue(context)
            }

            return dialogue
        }
    }

}