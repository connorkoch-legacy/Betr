package com.csci448.betr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_account_page.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.find_friends.*

class FindFriends: AppCompatActivity() {

    companion object {
        //Creates an intent for the OptionsActivity to be returned to the calling activity or fragment
        fun newIntent(context: Context?): Intent {
            return Intent(context, FindFriends::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_friends)
//        tempButton.setOnClickListener{
//            val intent = Intent(this, CreateBet::class.java)
//            startActivity(intent)
//        }

        val toolbar: Toolbar = findViewById(R.id.toolbar_friends)
        setSupportActionBar(toolbar)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        friend_recycler.layoutManager = LinearLayoutManager(this)

        val tempUser = User("Test User", "pass")
        val tempFriend = User("Friend 1", "pass1")
        val tempFriend2 = User("Friend 2", "pass2")
        var userFriendList = mutableListOf<String>(tempFriend.username, tempFriend2.username)
        tempUser.friendList = userFriendList

        friend_recycler.adapter = FriendAdapter(tempUser)

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