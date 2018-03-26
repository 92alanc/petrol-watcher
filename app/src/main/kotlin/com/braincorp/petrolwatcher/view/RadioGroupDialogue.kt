package com.braincorp.petrolwatcher.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window.FEATURE_NO_TITLE
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.settings.listeners.OnConfigurationSelectedListener
import com.braincorp.petrolwatcher.feature.settings.model.Configuration

open class RadioGroupDialogue(context: Context,
                              private val type: DialogueType,
                              @LayoutRes private val layoutRes: Int,
                              private val data: Map<Configuration, Int?>)
    : Dialog(context), View.OnClickListener {

    var onConfigurationSelectedListener: OnConfigurationSelectedListener? = null

    private lateinit var textViewTitle: TextView
    private lateinit var textViewOk: TextView
    private lateinit var textViewCancel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(FEATURE_NO_TITLE)
        setContentView(layoutRes)
        bindViews()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textViewCancel -> dismiss()
            R.id.textViewOk -> notifySelectedConfiguration()
        }
    }

    override fun setTitle(@StringRes title: Int) {
        textViewTitle.text = context.getString(title)
    }

    fun inflate() {
        val radioGroup = getRadioGroup()
        radioGroup.invalidate()
        if (radioGroup.childCount > 0)
            radioGroup.removeAllViews()
        data.entries.forEachIndexed { index, entry ->
            val radioButton = makeRadioButton(entry, index)
            radioGroup.addView(radioButton)
        }
    }

    private fun bindViews() {
        textViewTitle = findViewById(R.id.textViewTitle)

        textViewOk = findViewById(R.id.textViewOk)
        textViewOk.setOnClickListener(this)

        textViewCancel = findViewById(R.id.textViewCancel)
        textViewCancel.setOnClickListener(this)
    }

    private fun notifySelectedConfiguration() {
        val radioGroup = getRadioGroup()
        var index = 0
        val configurations = data.entries.toTypedArray()

        while (index < radioGroup.childCount) {
            val radioButton = radioGroup.getChildAt(index) as RadioButton
            if (radioButton.isChecked) {
                val configuration = configurations[index].key
                onConfigurationSelectedListener?.onConfigurationSelected(configuration)
                break
            }
            index++
        }

        dismiss()
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
                .toInt()

        if (index > 0) {
            when (type) {
                DialogueType.HORIZONTAL -> layoutParams.marginStart = margin
                DialogueType.VERTICAL -> layoutParams.topMargin = margin
            }
        }
        radioButton.layoutParams = layoutParams

        radioButton.text = entry.key.getDescription(context)
        val value = entry.value
        if (value != null) {
            val drawable = ContextCompat.getDrawable(context, value)
            radioButton.setCompoundDrawables(null, null, null, drawable)
            radioButton.compoundDrawablePadding = margin
        }

        return radioButton
    }

}