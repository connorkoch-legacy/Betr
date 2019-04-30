package com.csci448.betr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
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

        val tempUser = User("Test User", "pass")
        val tempFriend = User("Friend 1", "pass1")
        val tempFriend2 = User("Friend 2", "pass2")
        var userFriendList = mutableListOf<String>(tempFriend.username, tempFriend2.username)
        var friendFriendList = mutableListOf<String>(tempFriend2.username)
        tempUser.friendList = userFriendList
        tempFriend.friendList = friendFriendList

        var userBet = Bet("I bet that the moon is cheese", tempUser.username, tempFriend.username, 100.00)
        var friendBet = Bet("I bet that dogs are bigger than cats", tempFriend.username, tempFriend2.username, 17.8932)
        var userBetList = mutableListOf<Bet>(userBet, friendBet)
        var friendBetList = mutableListOf<Bet>(friendBet)
        tempUser.betList = userBetList
        tempFriend.betList = userBetList
        tempFriend2.betList = friendBetList

        betpage_recyclerview.layoutManager = LinearLayoutManager(this)
        betpage_recyclerview.adapter = BetPageAdapter(this, tempUser) {
            if( it == 0){
                var intent = Intent(this, FinishedBet::class.java)
                startActivity(intent)
            }else if(it == 1){
                var intent = Intent(this, OngoingBet::class.java)
                startActivity(intent)
            }else{
//                var intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
            }
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar_bets)
        setSupportActionBar(toolbar)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
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

    fun finishbet(){
        var intent = Intent(this, FinishedBet::class.java)
        startActivity(intent)
    }


}

