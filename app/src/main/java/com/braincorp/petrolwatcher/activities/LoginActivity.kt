package com.braincorp.petrolwatcher.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.authentication.AuthenticationManager
import com.braincorp.petrolwatcher.database.PetrolStationDatabase
import com.braincorp.petrolwatcher.model.Fuel
import com.braincorp.petrolwatcher.model.TestModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener, OnCompleteListener<Void> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        addButton.setOnClickListener(this)
        loginButton.setOnClickListener(this)
        createAccountButton.setOnClickListener(this)
        fetchButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.addButton -> {
                val testModel = TestModel("Alan", 25, Fuel.PETROL_REGULAR)
                PetrolStationDatabase.insert(testModel, this)
            }
            R.id.createAccountButton -> {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                AuthenticationManager.createUser(email, password, OnCompleteListener {
                    val text = if (it.isSuccessful) {
                        "User created successfully"
                    } else {
                        "Error creating user"
                    }
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                })
            }
            R.id.loginButton -> {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                AuthenticationManager.signIn(email, password, {
                    Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show()
                })
            }
            R.id.fetchButton -> {
                PetrolStationDatabase.select(object: ValueEventListener {
                    override fun onCancelled(error: DatabaseError?) {
                        Log.d("TAG", "Error")
                    }

                    override fun onDataChange(snapshot: DataSnapshot?) {
                        Log.d("TAG", "data received")
                    }
                })
            }
        }
    }

    override fun onComplete(task: Task<Void>) {
        val text = if (task.isSuccessful) {
            "Record created successfully"
        } else {
            "Error creating record"
        }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}
