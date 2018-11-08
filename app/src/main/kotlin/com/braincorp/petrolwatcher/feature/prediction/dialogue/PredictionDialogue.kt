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
import com.braincorp.petrolwatcher.feature.prediction.adapter.PredictionAdapter
import com.braincorp.petrolwatcher.feature.prediction.model.Prediction
import java.util.*

/**
 * The dialogue where fuel price prediction
 * are shown
 */
class PredictionDialogue : BaseDialogueFragment() {

    companion object {
        const val TAG = "prediction_dialogue"

        private const val KEY_PREDICTION = "prediction"
        private const val KEY_LOCALE = "locale"

        fun newInstance(prediction: Prediction,
                        locale: Locale) = PredictionDialogue().apply {
            val args = Bundle().apply {
                putParcelable(KEY_PREDICTION, prediction)
                putString(KEY_LOCALE, locale.toLanguageTag())
            }
            arguments = args
        }
    }

    private lateinit var prediction: Prediction
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
            prediction = it.getParcelable(KEY_PREDICTION)!!
            locale = Locale.forLanguageTag(it.getString(KEY_LOCALE))
        }
    }

    private fun populateRecyclerView(view: View) {
        view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = PredictionAdapter(prediction, locale)
        }
    }

}