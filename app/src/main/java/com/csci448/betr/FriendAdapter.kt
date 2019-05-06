package com.csci448.betr

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.friend_recyclerview.view.*
import kotlinx.android.synthetic.main.main_recyclerview_listitem.view.*

class FriendAdapter(val user: User, context: Context): RecyclerView.Adapter<MainAdapter.CustomViewHolder>(){
    var friendCounter = 0
    var numFriends = 0
    var FRIEND_TAG = "sending friend"
    val LOG_TAG = "FROGGGGGGGG"
    var myContext = context

    override fun getItemCount(): Int {
        Log.d(LOG_TAG, user.friendList!!.size.toString())
        return user.friendList!!.size

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MainAdapter.CustomViewHolder(layoutInflater.inflate(R.layout.friend_recyclerview, parent, false))
    }

    override fun onBindViewHolder(holder: MainAdapter.CustomViewHolder, position: Int) {
        var userName1: String = ""
        userName1 = user.friendList!![position]
        holder.view.friend_textview_1.text = "$userName1"
        holder.view.setOnClickListener {
            var intent = Intent(myContext, CreateBet::class.java)
            intent.putExtra(FRIEND_TAG, userName1)
            myContext.startActivity(intent)
        }
        friendCounter++
    }

}