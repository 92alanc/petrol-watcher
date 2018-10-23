package com.braincorp.petrolwatcher.feature.prediction.dialogue

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.base.BaseDialogueFragment
import com.braincorp.petrolwatcher.feature.prediction.model.Prediction
import com.braincorp.petrolwatcher.feature.stations.adapter.FuelAdapter
import java.util.*

/**
 * The dialogue where fuel price predictions
 * are shown
 */
class PredictionsDialogue : BaseDialogueFragment() {

    companion object {
        const val TAG = "predictions_dialogue"

        private const val KEY_PREDICTIONS = "predictions"
        private const val KEY_LOCALE = "locale"

        fun newInstance(predictions: ArrayList<Prediction>,
                        locale: Locale) = PredictionsDialogue().apply {
            val args = Bundle().apply {
                putParcelableArrayList(KEY_PREDICTIONS, predictions)
                putString(KEY_LOCALE, locale.toLanguageTag())
            }
            arguments = args
        }
    }

    private lateinit var predictions: ArrayList<Prediction>
    private lateinit var locale: Locale

    /**
     * Gets the dialogue tag
     *
     * @return the tag
     */
    override fun tag(): String = TAG

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialogue_predictions, container, false)
        view.findViewById<ImageButton>(R.id.bt_close).apply {
            setOnClickListener {
                dismiss()
            }
        }
        parseArgs()
        populateRecyclerView(view)
        return view
    }

    private fun parseArgs() {
        arguments!!.let {
            predictions = it.getParcelableArrayList(KEY_PREDICTIONS)!!
            locale = Locale.forLanguageTag(it.getString(KEY_LOCALE))
        }
    }

    private fun populateRecyclerView(view: View) {
        val data = predictions.asSequence().map {
            it.fuelData
        }.toMutableSet()

        view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = FuelAdapter(Locale.getDefault(), data)
        }
    }

}