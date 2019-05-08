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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_bet_page.*

class BetPage : AppCompatActivity() {

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    companion object {
        //Creates an intent for the OptionsActivity to be returned to the calling activity or fragment
        fun newIntent(context: Context?): Intent {
            return Intent(context, BetPage::class.java)
        }

        private const val LOG_TAG = "TEST"

        var users = mutableListOf<User>()
        var sortedBets = mutableListOf<Bet>()
        var currentUser: User = User()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bet_page)

        // Get the list of user and current user from the intents passed in from MainActivity
        users = intent.getParcelableArrayListExtra<User>("USERS_KEY").toMutableList()
        currentUser = intent.getParcelableExtra<User>("CURRENT_USER_KEY")
        // Clear lists everytime this activity is created
        var currentUserFriends: MutableList<User> = mutableListOf()
        sortedBets = mutableListOf()

        // Do some preprocessing to get all users in currentUser's friend list
        for(friendStr in currentUser.friendList){
            for(u in users){
                if(friendStr == u.username) currentUserFriends.add(u)
            }
        }

        // Sort the list of bets from the current user
        for(bet in currentUser.betList) {
            sortedBets.add(bet)
        }

        // Creates the recycler view. The recycler view returns an Array of strings with information I needed
        // I could have used callbacks to do this part, but I only needed a single int so I decided trying to get callbacks to work wouldn't be worth it (time crunch)
        betpage_recyclerview.layoutManager = LinearLayoutManager(this)
        betpage_recyclerview.adapter = BetPageAdapter(this, sortedBets) {

            // it[0] gives us the type of bet, it[2] gives us the index so we know which bet to change
            // Depending on type, the next activity is called
            if( it[0] == "0"){
                Log.d(LOG_TAG, "Ongoing")

                var intent = Intent(this, OngoingBet::class.java)
                intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
                intent.putExtra("LOGGED_IN_USER", currentUser)
                intent.putExtra("INDEX", it[2])
                startActivity(intent)
            }else if(it[0] == "1"){
                Log.d(LOG_TAG, "Request")

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
                Log.d(LOG_TAG, "Old Bet")

                var intent = Intent(this, old_bet::class.java)
                intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
                intent.putExtra("LOGGED_IN_USER", currentUser)
                intent.putExtra("INDEX", it[2])
                startActivity(intent)
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

    //Override the backpressed function so that it sends the updated bets back properly
    override fun onBackPressed() {
        var intent = Intent(this, MainActivity::class.java)
        intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
        intent.putExtra("LOGGED_IN_USER", currentUser)

        startActivity(intent)
        super.onBackPressed()

    }


}

