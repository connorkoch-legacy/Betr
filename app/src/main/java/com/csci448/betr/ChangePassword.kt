package com.csci448.betr

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePassword : AppCompatActivity() {

    private lateinit var currentUser: User
    private lateinit var users: MutableList<User>

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        users = intent.getParcelableArrayListExtra<User>("USER_LIST").toMutableList()
        currentUser = intent.getParcelableExtra("LOGGED_IN_USER")

        change_button.setOnClickListener {
            val password = password.text.toString()
            val passwordConfirm = password_confirm.text.toString()

            val passwordRegex = "^[a-zA-Z0-9]{5,}$".toRegex()

            //Make sure the password is correct and matches with the confirm
            if(!passwordRegex.matches(password)){
                Toast.makeText(this, "Password field incorrect. Must be 5 or more alphanumeric characters.", Toast.LENGTH_SHORT).show()
            }
            else if (password != passwordConfirm){
                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
            }
            else{
                //update info in current user and user list
                currentUser.password = password
                for(u in users){
                    if(u.username == currentUser.username) {
                        u.password = password
                    }
                }

                //Set corresponding user values in db to user with updated password
                database.child("users").child(currentUser.username).setValue(currentUser)

                val intent = Intent(this, AccountPage::class.java)
                intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
                intent.putExtra("LOGGED_IN_USER", currentUser)
                startActivity(intent)
            }
        }
    }
}