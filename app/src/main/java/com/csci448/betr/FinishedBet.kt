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

        users = intent.getParcelableArrayListExtra<User>("USER_LIST").toMutableList()
        currentUser = intent.getParcelableExtra<User>("LOGGED_IN_USER")
        sortedBets = mutableListOf()
        var index = intent.getStringExtra("INDEX").toInt()

        for(bet in currentUser.betList) {
            sortedBets.add(bet)
        }

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

        option_1.setOnClickListener {
            sortedBets[index].winner = sortedBets[index].betCreator
            var otherDude = sortedBets[index].betCreator
            for(a in users){
                if(a.username == currentUser.username){
                    a.betList = sortedBets
                    currentUser.betList = sortedBets
                    database.child("users").child(a.username).setValue(a)
                    database.child("users").child(currentUser.username).setValue(currentUser)
                }
                if(a.username == otherDude){
                    a.betList = sortedBets
                    database.child("users").child(a.username).setValue(a)
                }
            }
        }

        option_2.setOnClickListener {
            sortedBets[index].winner = sortedBets[index].betAcceptor
            var otherDude = sortedBets[index].betCreator
            for(a in users){
                if(a.username == currentUser.username){
                    a.betList = sortedBets
                    currentUser.betList = sortedBets
                    database.child("users").child(a.username).setValue(a)
                    database.child("users").child(currentUser.username).setValue(currentUser)
                }
                if(a.username == otherDude){
                    a.betList = sortedBets
                    database.child("users").child(a.username).setValue(a)
                }
            }
        }

    }

}