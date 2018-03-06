package com.braincorp.petrolwatcher.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window.FEATURE_NO_TITLE
import android.widget.RadioGroup
import com.braincorp.petrolwatcher.R

class VerticalRadioGroupDialogue(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(FEATURE_NO_TITLE)
        setContentView(R.layout.dialogue_radio_group_vertical)
    }

    fun getRadioGroup(): RadioGroup = findViewById(R.id.radioGroup)

}