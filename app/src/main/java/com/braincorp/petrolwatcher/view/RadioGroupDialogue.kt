package com.braincorp.petrolwatcher.view

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window.FEATURE_NO_TITLE
import android.widget.RadioButton
import android.widget.RadioGroup
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.preferences.Configuration
import kotlin.math.roundToInt

open class RadioGroupDialogue(context: Context, @LayoutRes private val layoutRes: Int)
    : Dialog(context), DialogInterface.OnDismissListener {

    var onItemSelectedListener: OnItemSelectedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(FEATURE_NO_TITLE)
        setContentView(layoutRes)
        setOnDismissListener(this)
    }

    override fun onDismiss(dialogue: DialogInterface?) {
        val radioGroup = getRadioGroup()
        var index = 0
        while (index < radioGroup.childCount) {
            val radioButton = radioGroup.getChildAt(index) as RadioButton
            if (radioButton.isChecked) {
                onItemSelectedListener?.onItemSelected(index)
                break
            }
            index++
        }
    }

    fun inflate(data: Map<Configuration, Int?>) {
        val radioGroup = getRadioGroup()
        data.entries.forEachIndexed { index, entry ->
            val radioButton = makeRadioButton(entry, index)
            radioGroup.addView(radioButton)
        }
    }

    private fun getRadioGroup(): RadioGroup = findViewById(R.id.radioGroup)

    /**
     * Each entry represents a key/value pair composed of a configuration
     * and its accompanying drawable, if necessary.
     * The index is used for margins, since not all views within the
     * RadioGroup need to have a top or start margin.
     */
    private fun makeRadioButton(entry: Map.Entry<Configuration, Int?>, index: Int): RadioButton {
        val radioButton = RadioButton(context)

        val layoutParams = RadioGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        val margin = context.resources
                .getDimension(R.dimen.margin_default)
                .roundToInt()

        if (index > 0) layoutParams.topMargin = margin
        radioButton.layoutParams = layoutParams

        radioButton.text = entry.key.getText(context)
        val value = entry.value
        if (value != null) {
            val drawable = ContextCompat.getDrawable(context, value)
            radioButton.setCompoundDrawables(null, null, null, drawable)
            radioButton.compoundDrawablePadding = margin
        }

        return radioButton
    }

    interface OnItemSelectedListener {
        fun onItemSelected(index: Int)
    }

}