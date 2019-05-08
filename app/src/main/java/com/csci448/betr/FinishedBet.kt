package com.csci448.betr

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.finished_bet_display_page.*

class FinishedBet : AppCompatActivity() {

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
        setContentView(R.layout.finished_bet_display_page)

        // Get values passed in from intents
        users = intent.getParcelableArrayListExtra<User>("USER_LIST").toMutableList()
        currentUser = intent.getParcelableExtra<User>("LOGGED_IN_USER")
        sortedBets = mutableListOf()
        var index = intent.getStringExtra("INDEX").toInt()

        for(bet in currentUser.betList) {
            sortedBets.add(bet)
        }

        // Update text field with the passed in intent values
        title_witle.text = sortedBets[index].betText
        bet_amount.text = "Bet Amount: ${sortedBets[index].betAmount.toString()}"
        participants.text = "Participants: ${sortedBets[index].betCreator.toString()} and ${sortedBets[index].betAcceptor.toString()}"
        option_1.text = sortedBets[index].betCreator
        option_2.text = sortedBets[index].betAcceptor


        back_button.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
            intent.putExtra("LOGGED_IN_USER", currentUser)
            startActivity(intent)
        }

        // If the user selects this option (the bet creator), he is the winner of the bet
        option_1.setOnClickListener {
            //Change the winner to the creator
            sortedBets[index].winner = sortedBets[index].betCreator
            var otherDude = sortedBets[index].betCreator// The bet effects two people, so two users in the users list must be updated
            for(a in users){
                //This if block handles updating the database and intent for the current user
                if(a.username == currentUser.username){
                    a.betList = sortedBets
                    currentUser.betList = sortedBets
                    database.child("users").child(a.username).setValue(a)
                    database.child("users").child(currentUser.username).setValue(currentUser)
                }
                //This if block handles updating the database for the other person this bet effects
                if(a.username == otherDude){
                    a.betList = sortedBets
                    database.child("users").child(a.username).setValue(a)
                }
            }
        }

        // If the user selects this option (the bet acceptor), he is the winner of the bet
        option_2.setOnClickListener {
            //Change the winner to the acceptor
            sortedBets[index].winner = sortedBets[index].betAcceptor
            var otherDude = sortedBets[index].betCreator // The bet effects two people, so two users in the users list must be updated
            for(a in users){
                //This if block handles updating the database and intent for the current user
                if(a.username == currentUser.username){
                    a.betList = sortedBets
                    currentUser.betList = sortedBets
                    database.child("users").child(a.username).setValue(a)
                    database.child("users").child(currentUser.username).setValue(currentUser)
                }
                //This if block handles updating the database for the other person this bet effects
                if(a.username == otherDude){
                    a.betList = sortedBets
                    database.child("users").child(a.username).setValue(a)
                }
            }
        }

    }

}