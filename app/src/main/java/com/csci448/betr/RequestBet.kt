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

        // Get the values passed in from intents
        users = intent.getParcelableArrayListExtra<User>("USER_LIST").toMutableList()
        currentUser = intent.getParcelableExtra<User>("LOGGED_IN_USER")
        sortedBets = mutableListOf()
        var index = intent.getStringExtra("INDEX").toInt()

        // Sort the bet list
        for(bet in currentUser.betList) {
            sortedBets.add(bet)
        }

        //Set the different text values to the information given from the intent
        title_witle.text = sortedBets[index].betText
        bet_amount.text = "Bet Amount: ${sortedBets[index].betAmount.toString()}"
        bet_requestor.text = "Bet Creator: ${sortedBets[index].betCreator.toString()}"

        // This is important because it updates the main list of users and bets for all the different activities
        back_button.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
            intent.putExtra("LOGGED_IN_USER", currentUser)
            startActivity(intent)
        }

        // If the user accepts the bet this will run
        accept_buttonee.setOnClickListener {
            accept_buttonee.visibility = View.INVISIBLE
            decline_buttonee.visibility = View.INVISIBLE
            //accepted = 1, it is normal
            //accepted = 2, dont display
            sortedBets[index].accepted = 1 // Change accept paramter in Bets to 1 to show it is now an ongoing bet
            var otherDude = sortedBets[index].betCreator // Bets effect the creator and requestor, Need to update two users in the list
            for(a in users){
                // This is to update the current user in both the currentUser object and the user list
                if(a.username == currentUser.username){
                    a.betList = sortedBets
                    currentUser.betList = sortedBets

                    // Update the database accordingly
                    database.child("users").child(a.username).setValue(a)
                    database.child("users").child(currentUser.username).setValue(currentUser)
                }
                // This is to update the user list for the other person that this bet effects
                if(a.username == otherDude){
                    a.betList = sortedBets
                    database.child("users").child(a.username).setValue(a)
                }
            }
        }

        // If the user declines the bet this will run
        decline_buttonee.setOnClickListener {
            accept_buttonee.visibility = View.INVISIBLE
            decline_buttonee.visibility = View.INVISIBLE
            sortedBets.remove(sortedBets[index])
            var otherDude = sortedBets[index].betCreator // Bets effect the creator and requestor, Need to update two users in the list
            for(a in users){
                // This is to update the current user in both the currentUser object and the user list
                if(a.username == currentUser.username){
                    a.betList = sortedBets
                    currentUser.betList = sortedBets

                    // Update the database accordingly
                    database.child("users").child(a.username).setValue(a)
                    database.child("users").child(currentUser.username).setValue(currentUser)
                }
                // This is to update the user list for the other person that this bet effects
                if(a.username == otherDude){
                    a.betList = sortedBets
                    database.child("users").child(a.username).setValue(a)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this, MainActivity::class.java)
        intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
        intent.putExtra("LOGGED_IN_USER", currentUser)
        startActivity(intent)
    }
}
