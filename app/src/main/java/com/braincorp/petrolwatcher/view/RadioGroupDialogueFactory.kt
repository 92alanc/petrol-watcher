package com.braincorp.petrolwatcher.view

import android.content.Context
import com.braincorp.petrolwatcher.preferences.Configuration

object RadioGroupDialogueFactory {

    fun makeDialogue(context: Context,
                     type: DialogueType,
                     data: Map<Configuration, Int?>): RadioGroupDialogue {
        return when (type) {
            DialogueType.HORIZONTAL -> HorizontalRadioGroupDialogue(context, data)
            DialogueType.VERTICAL -> VerticalRadioGroupDialogue(context, data)
        }
    }

}