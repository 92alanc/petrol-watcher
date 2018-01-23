package com.braincorp.petrolwatcher.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.braincorp.petrolwatcher.utils.showQuestionDialogue
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_profile.*

class ProfileActivity : BaseActivity(), View.OnClickListener, OnCompleteListener<Void> {

    companion object {
        private const val EXTRA_EDIT = "edit"

        fun getIntent(context: Context, edit: Boolean = false): Intent {
            return Intent(context, ProfileActivity::class.java).putExtra(EXTRA_EDIT, edit)
        }
    }

    private var editMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setOnClickListeners()
        if (AuthenticationManager.isSignedIn())
            fillViews()
        parseIntent()
        if (editMode)
            prepareEditMode()
    }

    override fun onBackPressed() {
        if (editMode) {
            showQuestionDialogue(title = R.string.changes_not_saved,
                                 message = R.string.question_changes_not_saved,
                                 positiveFunc = {
                                    super.onBackPressed()
                                 },
                                 negativeFunc = { })
        } else super.onBackPressed()
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.fabProfile) {
            if (!editMode) {
                prepareEditMode()
            } else {
                save()
            }
        } else if (view?.id == R.id.imageViewProfile) {

        }
    }

    override fun onComplete(task: Task<Void>) {
        if (task.isSuccessful) {
            val intent = if (editMode) {
                HomeActivity.getIntent(context = this)
            } else {
                LoginActivity.getIntent(context = this)
            }
            startActivity(intent)
        }
    }

    private fun fillViews() {
        imageViewProfile.setImageURI(AuthenticationManager.USER?.photoUrl)

        textViewDisplayName.text = AuthenticationManager.USER?.displayName
        textViewEmail.text = AuthenticationManager.USER?.email

        editTextDisplayName.setText(AuthenticationManager.USER?.displayName,
                                    TextView.BufferType.EDITABLE)
    }

    private fun parseIntent() {
        editMode = intent.getBooleanExtra(EXTRA_EDIT, false)
    }

    private fun prepareEditMode() {
        editMode = true

        textViewDisplayName.visibility = GONE
        textViewEmail.visibility = GONE

        editTextDisplayName.visibility = VISIBLE

        fabProfile.setImageResource(R.drawable.ic_save)
    }

    private fun save() {
        val displayName = editTextDisplayName.text.toString()

        AuthenticationManager.USER?.updateProfile(UserProfileChangeRequest.Builder()
                                                          .setDisplayName(displayName)
                                                          .build())?.addOnCompleteListener(this)
    }

    private fun setOnClickListeners() {
        fabProfile.setOnClickListener(this)
        imageViewProfile.setOnClickListener(this)
    }

}
