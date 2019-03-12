package com.csci448.betr

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.find_friends.*

class FindFriends: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_friends)
        tempButton.setOnClickListener{
            val intent = Intent(this, CreateBet::class.java)
            startActivity(intent)
        }


    }
}