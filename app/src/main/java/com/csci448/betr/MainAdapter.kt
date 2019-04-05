package com.csci448.betr

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.main_recyclerview_listitem.view.*

class MainAdapter(val user: User, var meOrFriend: Int) : RecyclerView.Adapter<CustomViewHolder>() { //meOrFriend = 0, me ; = 1, friend

    var betCounter = 0

    override fun getItemCount(): Int {
        return user.betList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CustomViewHolder(layoutInflater.inflate(R.layout.main_recyclerview_listitem, parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var userName1: String = ""
        var userName2: String = ""

        if(meOrFriend == 0) userName1 = "You"
        else userName1 =  = user.betList[betCounter].betC
        
        holder.view.item_textview_1.text = "$userName1 bet $userName2"

    }

}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}