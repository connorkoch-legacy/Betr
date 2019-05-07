package com.csci448.betr

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.friend_recyclerview.view.*
import kotlinx.android.synthetic.main.main_recyclerview_listitem.view.*
import java.util.ArrayList

class FriendAdapter(val user: User, context: Context, friendUserList:MutableList<User>, userList:MutableList<User>): RecyclerView.Adapter<MainAdapter.CustomViewHolder>(){
    var friendCounter = 0
    var numFriends = 0
    var FRIEND_TAG = "sending friend"
    val LOG_TAG = "FROGGGGGGGG"
    var myContext = context
    var listOfFriendUsers = friendUserList
    var listOfUsers = userList

    override fun getItemCount(): Int {
        Log.d(LOG_TAG, listOfFriendUsers!!.size.toString())
        return listOfFriendUsers!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MainAdapter.CustomViewHolder(layoutInflater.inflate(R.layout.friend_recyclerview, parent, false))
    }

    override fun onBindViewHolder(holder: MainAdapter.CustomViewHolder, position: Int) {
        var userName1: String = ""
        userName1 = listOfFriendUsers!![position].username
        holder.view.friend_textview_1.text = "$userName1"
        holder.view.setOnClickListener {
            var intent = Intent(myContext, CreateBet::class.java)
            intent.putExtra(FRIEND_TAG, userName1)
            intent.putParcelableArrayListExtra("FRIEND_LIST", ArrayList(listOfUsers))
            intent.putParcelableArrayListExtra("USER_LIST", ArrayList(listOfFriendUsers))
            intent.putExtra("LOGGED_IN_USER", user)
            myContext.startActivity(intent)
        }
        if(listOfFriendUsers[position].profilePic != "") {
            val imageBytes = Base64.decode(listOfFriendUsers[position].profilePic, 0)
            val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            holder.view.item_imageviewFriend.setImageBitmap(image)
            //holder.view.item_imageview.setImageBitmap(image)
        }
        friendCounter++
    }

}