package com.csci448.betr

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login_page.*

class LoginPage : AppCompatActivity() {

    private val NEW_USER_REQUEST = 1

    private var users: MutableList<User> = mutableListOf()
    //create a reference to the db
    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        //add an event listener to the db to pull all data from (done only at the start of app on login)
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Data returned as mutableListOf Users
                //clear the users and bets list and populate "users" and "bets" with all users and bets from the DB
                users.clear()
                snapshot.child("users").children.mapNotNullTo(users) { it.getValue<User>(User::class.java) }
            }

            override fun onCancelled(p0: DatabaseError) {
                println("Cancelled bruh")
            }
        })

        sign_up_button.setOnClickListener {
            var intent = Intent(this, CreateAccount::class.java)
            //Send user list to make sure the new user doesn't choose an email that already exists
            intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
            startActivity(intent)
        }

        login_button.setOnClickListener {
            //check if fields are empty
            if(username.text.toString() == "" || password.text.toString() == "") {
                Toast.makeText(this, "Fields cannot be blank.", Toast.LENGTH_SHORT).show()
            }
            else {
                //find user in users list
                var existsFlag = false
                for(u in users){
                    if(u.username == username.text.toString() && u.password == password.text.toString()){
                        println("" + u.username + " " + u.password)
                        existsFlag = true
                        //if a user is found in the users list that matches the credentials, move to MainActivity
                        var intent = Intent(this, MainActivity::class.java)
                        intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
                        intent.putExtra("LOGGED_IN_USER", u)
                        startActivity(intent)
                    }
                }
                if(!existsFlag) Toast.makeText(this, "Account does not exist.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


