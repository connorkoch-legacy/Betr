package com.csci448.betr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.util.Log
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

        private const val LOG_TAG = "TEST"

        var users = mutableListOf<User>()
        var sortedBets = mutableListOf<Bet>()
        var currentUser: User = User()
        var updateCondition = 0

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bet_page)

        users = intent.getParcelableArrayListExtra<User>("USERS_KEY").toMutableList()
        currentUser = intent.getParcelableExtra<User>("CURRENT_USER_KEY")
        var currentUserFriends: MutableList<User> = mutableListOf()
        sortedBets = mutableListOf()

        updateCondition = intent.getIntExtra("DB_UPDATE", 0)

        if(updateCondition == 1){ //request update
            
        }else if(updateCondition == 2){ //finished bet update

        }

        Log.d(LOG_TAG, currentUser.username)

        //do some preprocessing to get all users in currentUser's friend list
        for(friendStr in currentUser.friendList){
            for(u in users){
                if(friendStr == u.username) currentUserFriends.add(u)
            }
        }

        for(bet in currentUser.betList) {
            sortedBets.add(bet)
        }

        betpage_recyclerview.layoutManager = LinearLayoutManager(this)
        betpage_recyclerview.adapter = BetPageAdapter(this, sortedBets) {

            if( it[0] == "0"){
                Log.d(LOG_TAG, "Ongoing")
                var intent = Intent(this, OngoingBet::class.java)
                intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
                intent.putExtra("LOGGED_IN_USER", currentUser)
                intent.putExtra("INDEX", it[2])
                startActivity(intent)
            }else if(it[0] == "1"){
                //REQUEST
                var intent = Intent(this, RequestBet::class.java)
                intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
                intent.putExtra("LOGGED_IN_USER", currentUser)
                intent.putExtra("INDEX", it[2])
                startActivity(intent)
            }else if(it[0] == "2"){
                Log.d(LOG_TAG, "Finished, Vote")
                var intent = Intent(this, FinishedBet::class.java)
                intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
                intent.putExtra("LOGGED_IN_USER", currentUser)
                intent.putExtra("INDEX", it[2])
                startActivity(intent)
            }else{
                Log.d(LOG_TAG, "Ongoing")
                var intent = Intent(this, OngoingBet::class.java)
                intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
                intent.putExtra("LOGGED_IN_USER", currentUser)
                intent.putExtra("INDEX", it[2])
                startActivity(intent)
            }

            Log.d(LOG_TAG, it[0])
            Log.d(LOG_TAG, it[1])
            Log.d(LOG_TAG, it[2])

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

    override fun onBackPressed() {
        Log.d(LOG_TAG,"REVERSO")
        var intent = Intent(this, MainActivity::class.java)
        intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
        intent.putExtra("LOGGED_IN_USER", currentUser)

        startActivity(intent)
        super.onBackPressed()

    }


}

