package com.csci448.betr

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_request_bet.*

class RequestBet : AppCompatActivity() {

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    companion object {
        //Creates an intent for the OptionsActivity to be returned to the calling activity or fragment
        private const val LOG_TAG = "TEST"

        var users = mutableListOf<User>()
        var sortedBets = mutableListOf<Bet>()
        var currentUser: User = User()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_bet)

        users = intent.getParcelableArrayListExtra<User>("USER_LIST").toMutableList()
        currentUser = intent.getParcelableExtra<User>("LOGGED_IN_USER")
        sortedBets = mutableListOf()
        var index = intent.getStringExtra("INDEX").toInt()

        for(bet in currentUser.betList) {
            sortedBets.add(bet)
        }

        back_button.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
            intent.putExtra("LOGGED_IN_USER", currentUser)
            startActivity(intent)
        }

        accept_buttonee.setOnClickListener {
            accept_buttonee.visibility = View.INVISIBLE
            decline_buttonee.visibility = View.INVISIBLE
            //accepted = 1, it is normal
            //accepted = 2, dont display
            sortedBets[index].accepted = 1
            for(a in users){
                if(a.username == currentUser.username){
                    a.betList = sortedBets
                    currentUser.betList = sortedBets

                    database.child("users").child(a.username).setValue(a)
                    database.child("users").child(currentUser.username).setValue(currentUser)
                    break
                }
            }
        }

        decline_buttonee.setOnClickListener {
            accept_buttonee.visibility = View.INVISIBLE
            decline_buttonee.visibility = View.INVISIBLE
            sortedBets.remove(sortedBets[index])
            for(a in users){
                if(a.username == currentUser.username){
                    a.betList = sortedBets
                    currentUser.betList = sortedBets

                    database.child("users").child(a.username).setValue(a)
                    database.child("users").child(currentUser.username).setValue(currentUser)
                    break
                }
            }
        }
    }

    //TODO: CONNOR ADD DATABSE LINE HERE DUDE @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //TODO; @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //TODO; @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    fun updateDatabase(){

    }
    //TODO; @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //TODO; @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //TODO; @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this, MainActivity::class.java)
        intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
        intent.putExtra("LOGGED_IN_USER", currentUser)
        startActivity(intent)
    }
}