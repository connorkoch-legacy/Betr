package com.csci448.betr

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login_page.*
import kotlinx.android.synthetic.main.create_account.*

class CreateAccount: AppCompatActivity() {

    private lateinit var users: MutableList<User>

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_account)

        users = intent.getParcelableArrayListExtra<User>("USER_LIST").toMutableList()

        create_button.setOnClickListener {
            val username = username_edit.text.toString()
            val password = pass_edit.text.toString()
            val passwordConfirm = confirmpass_edit.text.toString()

            val passwordRegex = "^[a-zA-Z0-9]{5,}$".toRegex()

            if(!passwordRegex.matches(password)){
                Toast.makeText(this, "Password field incorrect. Must be 5 or more alphanumeric characters.", Toast.LENGTH_SHORT).show()
            }
            else if (password != passwordConfirm){
                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
            }
            else{
                //Check to see if email already exists
                var matchFlag = false
                for(u in users){
                    if(u.username == username){
                        Toast.makeText(this, "Email already exists.", Toast.LENGTH_SHORT).show()
                        matchFlag = true
                        break
                    }
                }
                //Email doesn't match with any user, then create user in database
                if(!matchFlag){
                    //new user with no friends
                    //TODO: MAKE SURE WHEN A FRIEND OR BET IS ADDED TO THE CURRENT USER THE DATABASE IS UPDATED
                    //TODO: ADD FUNCTIONALITY TO PASS USERS LIST AND CURRENT USER BETWEEN ACTIVITIES
                    val newUser = User(username, password)

                    //Push to database with a random key and newUser as value
                    val key: String = database.child("users").push().key!!
                    database.child("users").child(key).setValue(newUser)
                    Log.d("CreateAccount", key)

                    users.add(newUser)

                    Toast.makeText(this, "Account created.", Toast.LENGTH_LONG).show()

                    //Send them to main activity logged in as newUser
                    var intent = Intent(this, MainActivity::class.java)
                    intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
                    intent.putExtra("LOGGED_IN_USER", newUser)
                    startActivity(intent)
                }
            }
        }

    }
}