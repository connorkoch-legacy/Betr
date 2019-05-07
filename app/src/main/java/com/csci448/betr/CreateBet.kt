package com.csci448.betr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.create_bet.*
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class CreateBet: AppCompatActivity() {

    private lateinit var users: MutableList<User>
    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    var FRIEND_TAG = "sending friend"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_bet)
        var friendsList = intent.getParcelableArrayListExtra<User>("FRIEND_LIST").toMutableList()
        var currentUser = intent.getParcelableExtra<User>("LOGGED_IN_USER")
        var users = intent.getParcelableArrayListExtra<User>("USER_LIST").toMutableList()
        var friend = intent.getStringExtra(FRIEND_TAG)

        val toolbar: Toolbar = findViewById(R.id.toolbar_friends)
        setSupportActionBar(toolbar)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        createBetButton.setOnClickListener{
            //get values from text fields
            var friendUser:User = User()
            var tempBetText = TitleText.text.toString()
            var tempBetAmount = amountText.text.toString().toDouble()
            var tempdateEnd = dateText.text.toString()
            val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.US)
            val cal = Calendar.getInstance()
            for(tempUser in users){
                if(tempUser.username == friend){
                    friendUser = tempUser
                }
            }

            //update bet list of current user and friend
            var userBet:Bet = Bet(tempBetText, currentUser.username, friend, tempBetAmount, sdf.format(cal.time), tempdateEnd)
            var friendBet:Bet = Bet(tempBetText, friend, currentUser.username, tempBetAmount, sdf.format(cal.time), tempdateEnd)
            val tempBetListUser = currentUser.betList.toMutableList()
            tempBetListUser.add(userBet)
            currentUser.betList = tempBetListUser.toList()
            val tempBetListFriend = friendUser.betList.toMutableList()
            tempBetListFriend.add(friendBet)
            friendUser.betList = tempBetListFriend.toList()

            //push to database
            database.child("users").child(currentUser.username).setValue(currentUser)
            database.child("users").child(friendUser.username).setValue(friendUser)

            //create intent to go back to MainActivity
            var intent = Intent(this, MainActivity::class.java)
            intent.putParcelableArrayListExtra("FRIEND_LIST", ArrayList(users))
            intent.putParcelableArrayListExtra("USER_LIST", ArrayList(friendsList))
            intent.putExtra("LOGGED_IN_USER", currentUser)
            startActivity(intent)
        }

    }

    //Opens the sidebar when the menu icon in the toolbar is tapped
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}