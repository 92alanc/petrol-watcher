package com.braincorp.petrolwatcher.activities

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet.WRAP_CONTENT
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
import android.widget.ProgressBar

open class BaseActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindProgressBar()
    }

    fun showProgressBar() {
        window.setFlags(FLAG_NOT_TOUCHABLE, FLAG_NOT_TOUCHABLE)
        progressBar.visibility = VISIBLE
    }

    fun hideProgressBar() {
        window.clearFlags(FLAG_NOT_TOUCHABLE)
        progressBar.visibility = GONE
    }

    private fun bindProgressBar() {
        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyle)
        progressBar.isIndeterminate = true
        val params = ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
        params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
        addContentView(progressBar, params)

    }

}