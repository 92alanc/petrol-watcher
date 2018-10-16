package com.braincorp.petrolwatcher.feature.stations.dialogue

import android.os.Bundle
import android.support.constraint.Group
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.*
import com.braincorp.petrolwatcher.DependencyInjection
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.stations.model.PetrolStation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlin.math.roundToInt

/**
 * The dialogue where petrol stations are rated
 */
class RatingDialogue : DialogFragment(), View.OnClickListener, OnCompleteListener<Void> {

    companion object {
        const val TAG = "rating_dialogue"

        private const val KEY_PETROL_STATION = "petrol_station"

        fun newInstance(petrolStation: PetrolStation) = RatingDialogue().apply {
            val args = Bundle()
            args.putParcelable(KEY_PETROL_STATION, petrolStation)
            arguments = args
        }
    }

    private lateinit var txtName: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var btClose: ImageButton
    private lateinit var btOk: Button
    private lateinit var petrolStation: PetrolStation
    private lateinit var progressBar: ProgressBar
    private lateinit var content: Group

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialogue_rating, container, false)
        bindViews(view)
        petrolStation = getPetrolStation()
        prepareUi()
        return view
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_close -> dismiss()
            R.id.bt_ok -> save()
        }
    }

    override fun onComplete(task: Task<Void>) {
        progressBar.visibility = GONE
        content.visibility = VISIBLE

        if (task.isSuccessful) {
            dismiss()
        } else {
            Snackbar.make(btOk,
                    R.string.something_went_wrong,
                    Snackbar.LENGTH_SHORT).setAction(R.string.try_again) {
                save()
            }
        }
    }

    private fun bindViews(view: View) {
        with(view) {
            txtName = findViewById(R.id.txt_name)
            ratingBar = findViewById(R.id.rating_bar)
            btClose = findViewById(R.id.bt_close)
            btOk = findViewById(R.id.bt_ok)
            progressBar = findViewById(R.id.progress_bar)
            content = findViewById(R.id.group_content)
        }
    }

    private fun getPetrolStation() = arguments!!.getParcelable<PetrolStation>(KEY_PETROL_STATION)

    private fun prepareUi() {
        txtName.text = petrolStation.name
        btClose.setOnClickListener(this)
        btOk.setOnClickListener(this)
    }

    private fun save() {
        content.visibility = INVISIBLE
        progressBar.visibility = VISIBLE

        val newRating = ratingBar.rating.roundToInt()
        val newRatingCount = petrolStation.ratingCount + 1 // Add this rating to the count
        val ratingSum = petrolStation.ratingSum + newRating
        val averageRating = ratingSum.toFloat() / newRatingCount.toFloat()

        petrolStation.rating = averageRating.roundToInt()
        petrolStation.ratingCount = newRatingCount
        petrolStation.ratingSum = ratingSum

        DependencyInjection.databaseManager.savePetrolStation(petrolStation, this)
    }

}