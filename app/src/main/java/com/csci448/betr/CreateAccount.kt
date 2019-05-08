package com.csci448.betr

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.util.EventLogTags
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login_page.*
import kotlinx.android.synthetic.main.create_account.*
import java.util.jar.Attributes

class CreateAccount: AppCompatActivity() {

    private lateinit var users: MutableList<User>
    private lateinit var notificationManager : NotificationManager

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    //CREATE ACCOUNT CREATION NOTIFICATION


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
                    val newUser = User(username, password)

                    //Push to database with username as key
                    //database.child("users").push(newUser.username)
                    database.child("users").child(newUser.username).setValue(newUser)

                    //_______________TEST
//                    val newUser1 = User("Roberto", "123")
//                    val newUser2 = User("Guadalupe", "123")
//                    val newUser3 = User("Bernoulli", "123")
//                    val newUser4 = User("Xiong", "123")
//                    var f1List = mutableListOf<String>(newUser2.username, newUser3.username, newUser4.username)
//                    newUser1.friendList = f1List
//                    users.add(newUser1)
//                    users.add(newUser2)
//                    users.add(newUser3)
//                    users.add(newUser4)
//
//                    key = database.child("users").push().key!!
//                    database.child("users").child(key).setValue(newUser1)
//                    key = database.child("users").push().key!!
//                    database.child("users").child(key).setValue(newUser2)
//                    key = database.child("users").push().key!!
//                    database.child("users").child(key).setValue(newUser3)
//                    key = database.child("users").push().key!!
//                    database.child("users").child(key).setValue(newUser4)



                    //_______________TEST


                    users.add(newUser)

                    //Create account creation notification
                    notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

                    createNotificationChannel()
                    val notification = NotificationCompat.Builder(this, "BETRCHANNEL")
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        //.setContentIntent(pendingIntent)
                        .setContentTitle("Betr")
                        .setContentText("Account Successfully Created!")
                        .setAutoCancel(true)
                        .build()

                    notificationManager.notify(0, notification)


                    //Send them to main activity logged in as newUser
                    var intent = Intent(this, MainActivity::class.java)
                    intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
                    intent.putExtra("LOGGED_IN_USER", newUser)
                    startActivity(intent)
                }
            }
        }

    }

    private fun createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel("BETRCHANNEL", "Betr", importance).apply{
                description = "Account Creation Notification"
            }
            notificationManager.createNotificationChannel(channel)

        }

    }
}