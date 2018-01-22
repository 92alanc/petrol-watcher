package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.braincorp.petrolwatcher.R

class ProfileActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, ProfileActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }

}
