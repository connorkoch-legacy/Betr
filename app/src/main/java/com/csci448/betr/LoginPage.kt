package com.csci448.betr

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_page.*

class LoginPage : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_login_page)

        sign_up_button.setOnClickListener {
            var intent = Intent(this, CreateAccount::class.java)
            startActivity(intent)
        }

        login_button.setOnClickListener {
//            var intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)

            //REGEX TO CHECK STRING EMPTY

            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, create correct User object and move to main activity

                        var intent = MainActivity.newIntent(this)
                        //intent.putExtra(User)
                        startActivity(intent)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(baseContext, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }


    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
    }

}

