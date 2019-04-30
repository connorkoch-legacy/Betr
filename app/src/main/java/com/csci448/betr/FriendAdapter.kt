package com.csci448.betr

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.friend_recyclerview.view.*
import kotlinx.android.synthetic.main.main_recyclerview_listitem.view.*

class FriendAdapter(val user: User): RecyclerView.Adapter<CustomViewHolder>(){
    var friendCounter = 0
    var numFriends = 0

    override fun getItemCount(): Int {
        for(i in 0 until user.friendList!!.size) {
            numFriends++
        }
        return numFriends
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CustomViewHolder(layoutInflater.inflate(R.layout.friend_recyclerview, parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var userName1: String = ""
        if(friendCounter > 0){
            friendCounter--
        }
//        userName1 = user.friendList!![friendCounter].username
//        holder.view.friend_textview_1.text = "$userName1"
        friendCounter++
    }

}