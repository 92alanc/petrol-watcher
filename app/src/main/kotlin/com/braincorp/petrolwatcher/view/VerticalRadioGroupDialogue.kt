package com.braincorp.petrolwatcher.view

import android.content.Context
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.settings.model.Configuration

class VerticalRadioGroupDialogue(context: Context,
                                 data: Map<Configuration, Int?>)
    : RadioGroupDialogue(context,
        DialogueType.VERTICAL,
        R.layout.dialogue_radio_group_vertical,
        data)