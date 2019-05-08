package com.csci448.betr

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.finished_bet_display_page.*

class FinishedBet : AppCompatActivity() {

    companion object {
        //Creates an intent for the OptionsActivity to be returned to the calling activity or fragment
        private const val LOG_TAG = "TEST"

        var users = mutableListOf<User>()
        var sortedBets = mutableListOf<Bet>()
        var currentUser: User = User()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_old_bet)

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
            for(a in users){
                if(a.username == currentUser.username){
                    a.betList = sortedBets
                    currentUser.betList = sortedBets
                    break
                }
            }
        }

        option_2.setOnClickListener {
            sortedBets[index].winner = sortedBets[index].betAcceptor
            for(a in users){
                if(a.username == currentUser.username){
                    a.betList = sortedBets
                    currentUser.betList = sortedBets
                    break
                }
            }
        }

    }

}