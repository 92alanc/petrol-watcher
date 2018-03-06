package com.braincorp.petrolwatcher.view

import android.content.Context
import android.support.annotation.StringRes
import com.braincorp.petrolwatcher.preferences.Configuration

object RadioGroupDialogueFactory {

    fun makeDialogue(context: Context,
                     @StringRes title: Int,
                     type: DialogueType,
                     data: Map<Configuration, Int?>): RadioGroupDialogue {
        val dialogue = getView(context, type, data)
        dialogue.setTitle(title)
        return dialogue
    }

    private fun getView(context: Context,
                        type: DialogueType,
                        data: Map<Configuration, Int?>): RadioGroupDialogue {
        return when (type) {
            DialogueType.HORIZONTAL -> HorizontalRadioGroupDialogue(context, data)
            DialogueType.VERTICAL -> VerticalRadioGroupDialogue(context, data)
        }
    }

}