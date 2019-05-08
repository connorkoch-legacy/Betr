package com.csci448.betr

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.NotificationCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_friend.*

class AddFriend : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var currentUser: User
    private lateinit var users: MutableList<User>
    private lateinit var notificationManager : NotificationManager


    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)

        var users = intent.getParcelableArrayListExtra<User>("USER_LIST").toMutableList()
        var currentUser = intent.getParcelableExtra<User>("LOGGED_IN_USER")

        find_friend_button.setOnClickListener {
            for(u in users){
                if(!(u.username in currentUser.friendList) && u.username == add_friend.text.toString()) {
                    //add friend to current user's friend list
                    var mutList = currentUser.friendList.toMutableList()
                    mutList.add(u.username)
                    currentUser.friendList = mutList.toList()
                    //add friend to other's list
                    mutList = u.friendList.toMutableList()
                    mutList.add(currentUser.username)
                    u.friendList = mutList.toList()

                    database.child("users").child(currentUser.username).setValue(currentUser)
                    database.child("users").child(u.username).setValue(u)

                    //Create account creation notification
                    notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

                    createNotificationChannel()
                    val notification = NotificationCompat.Builder(this, "BETRCHANNEL")
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        //.setContentIntent(pendingIntent)
                        .setContentTitle("Betr")
                        .setContentText("Friend Added")
                        .setAutoCancel(true)
                        .build()

                    notificationManager.notify(0, notification)

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
                    intent.putExtra("LOGGED_IN_USER", currentUser)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Username does not exist or is already your friend.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel("BETRCHANNEL", "Betr", importance).apply{
                description = "Friend Added"
            }
            notificationManager.createNotificationChannel(channel)

        }

    }
}