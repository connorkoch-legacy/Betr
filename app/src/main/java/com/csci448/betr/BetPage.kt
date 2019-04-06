package com.csci448.betr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_bet_page.*

class BetPage : AppCompatActivity() {

    companion object {
        //Creates an intent for the OptionsActivity to be returned to the calling activity or fragment
        fun newIntent(context: Context?): Intent {
            return Intent(context, BetPage::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bet_page)

        val tempUser = User("Test User", "pass", null, null)
        val tempFriend = User("Friend 1", "pass1", null, null)
        val tempFriend2 = User("Friend 2", "pass2", null, null)
        var userFriendList = mutableListOf<User>(tempFriend, tempFriend2)
        var friendFriendList = mutableListOf<User>(tempFriend2)
        tempUser.friendList = userFriendList
        tempFriend.friendList = friendFriendList

        var userBet = Bet("I bet that the moon is cheese", tempUser, tempFriend, 100.00)
        var friendBet = Bet("I bet that dogs are bigger than cats", tempFriend, tempFriend2, 17.8932)
        var userBetList = mutableListOf<Bet>(userBet, friendBet)
        var friendBetList = mutableListOf<Bet>(friendBet)
        tempUser.betList = userBetList
        tempFriend.betList = userBetList
        tempFriend2.betList = friendBetList

        betpage_recyclerview.layoutManager = LinearLayoutManager(this)
        betpage_recyclerview.adapter = BetPageAdapter(tempUser)


        temp_finished_bet_button.setOnClickListener {
            var intent = Intent(this, FinishedBet::class.java)
            startActivity(intent)
        }

        temp_ongoing_bet_button.setOnClickListener {
            var intent = Intent(this, OngoingBet::class.java)
            startActivity(intent)
        }

        back_button.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}

