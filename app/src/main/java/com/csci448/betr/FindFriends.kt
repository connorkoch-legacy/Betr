package com.csci448.betr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
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
        tempButton.setOnClickListener{
            val intent = Intent(this, CreateBet::class.java)
            startActivity(intent)
        }
        friend_recycler.layoutManager = LinearLayoutManager(this)

        val tempUser = User("Test User", "pass", null, null)
        val tempFriend = User("Friend 1", "pass1", null, null)
        val tempFriend2 = User("Friend 2", "pass2", null, null)
        var userFriendList = mutableListOf<User>(tempFriend, tempFriend2)
        tempUser.friendList = userFriendList

        friend_recycler.adapter = FriendAdapter(tempUser)

    }
}