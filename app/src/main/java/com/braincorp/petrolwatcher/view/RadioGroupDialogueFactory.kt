package com.braincorp.petrolwatcher.view

import android.content.Context
import android.support.annotation.StringRes
import com.braincorp.petrolwatcher.preferences.Configuration

object RadioGroupDialogueFactory {

    fun makeDialogue(context: Context,
                     @StringRes title: Int,
                     type: DialogueType,
                     data: Map<Configuration, Int?>): RadioGroupDialogue {
        val dialogue = getView(context, type)
        dialogue.setTitle(title)
        dialogue.inflate(data)
        return dialogue
    }

    private fun getView(context: Context, type: DialogueType): RadioGroupDialogue {
        return when (type) {
            DialogueType.HORIZONTAL -> HorizontalRadioGroupDialogue(context)
            DialogueType.VERTICAL -> VerticalRadioGroupDialogue(context)
        }
    }

}