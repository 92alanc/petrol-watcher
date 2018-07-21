package com.braincorp.petrolwatcher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(RESULT_OK)
        finish()
    }

}