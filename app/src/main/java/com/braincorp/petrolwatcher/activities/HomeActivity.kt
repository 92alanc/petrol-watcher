package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.braincorp.petrolwatcher.utils.showQuestionDialogue

class HomeActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.itemSignOut -> promptSignOut()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun launchLoginActivity() {
        val intent = LoginActivity.getIntent(context = this)
        startActivity(intent)
        finish()
    }

    private fun promptSignOut() {
        showQuestionDialogue(R.string.sign_out, R.string.question_sign_out,
                positiveFunc = {
                    AuthenticationManager.signOut()
                    launchLoginActivity()
                },
                negativeFunc = { })
    }

}
