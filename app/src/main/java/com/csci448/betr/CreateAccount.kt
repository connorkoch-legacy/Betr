package com.csci448.betr

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_page.*
import kotlinx.android.synthetic.main.create_account.*

class CreateAccount: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_account)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        create_button.setOnClickListener {

            //If the email and pass are correct, Create the user in Firebase
            auth.createUserWithEmailAndPassword(email_edit.text.toString(), pass_edit.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success,
                        val user = auth.currentUser
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(baseContext, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null)
        val currentUser = auth.currentUser
    }
}