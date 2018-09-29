package com.braincorp.petrolwatcher.utils

import android.widget.AdapterView
import android.widget.Spinner
import com.braincorp.petrolwatcher.ui.GenericSpinnerAdapter

/**
 * Populates a spinner with a generic value type
 *
 * @param spinner the spinner
 * @param values the values to populate the spinner
 * @param onItemSelectedListener the callback to be triggered
 *                               when an item is selected
 */
fun <T> populateSpinner(spinner: Spinner,
                        values: T,
                        onItemSelectedListener: AdapterView.OnItemSelectedListener) {
    with (spinner) {
        adapter = null
        if (values is IntRange)
            adapter = GenericSpinnerAdapter(spinner.context, values.toList())
        else if (values is ArrayList<*>)
            adapter = GenericSpinnerAdapter(spinner.context, values)
        this.onItemSelectedListener = onItemSelectedListener
    }
}
