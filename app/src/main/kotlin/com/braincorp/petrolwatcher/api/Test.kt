package com.braincorp.petrolwatcher.api

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.api.model.Years
import com.braincorp.petrolwatcher.api.services.VehicleApi
import com.braincorp.petrolwatcher.utils.showErrorDialogue
import kotlinx.android.synthetic.main.test.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Test : AppCompatActivity(), Callback<Years> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)
        val api = VehicleApi.getService()
        button.setOnClickListener {
            api.getYears().enqueue(this)
        }
    }

    override fun onFailure(call: Call<Years>?, t: Throwable?) {
        showErrorDialogue(R.string.error)
    }

    @SuppressLint("SetTextI18n")
    override fun onResponse(call: Call<Years>?, response: Response<Years>) {
        if (response.isSuccessful) {
            val yearRange = response.body()
            textView.text = "Min: ${yearRange?.min}\nMax: ${yearRange?.max}"
        }
    }

}