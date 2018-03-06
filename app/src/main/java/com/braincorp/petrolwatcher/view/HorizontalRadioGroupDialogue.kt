package com.braincorp.petrolwatcher.view

import android.content.Context
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.preferences.Configuration

class HorizontalRadioGroupDialogue(context: Context,
                                   data: Map<Configuration, Int?>)
    : RadioGroupDialogue(context,
        DialogueType.HORIZONTAL,
        R.layout.dialogue_radio_group_horizontal,
        data)