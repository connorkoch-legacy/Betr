package com.csci448.betr

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.main_recyclerview_listitem.view.*

class MainAdapter(val user: User, var meOrFriend: Int) : RecyclerView.Adapter<CustomViewHolder>() { //meOrFriend = 0, me ; = 1, friend

    var betCounter = 0
    var friendBets = mutableListOf<Bet>()

    override fun getItemCount(): Int {
        if(meOrFriend == 0) return user.betList!!.size
        else {
            for(i in 0 until user.friendList!!.size) {
                println(user.friendList!![i].betList!!.size)
                for(j in 0 until user.friendList!![i].betList!!.size){
                    friendBets.add(user.friendList!![i].betList!![j])
                }
            }
            return friendBets.size
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CustomViewHolder(layoutInflater.inflate(R.layout.main_recyclerview_listitem, parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var userName1: String = ""
        var userName2: String = ""
        var currentBet: Bet? = null

        if(meOrFriend == 0) {
            currentBet = user.betList!![betCounter]

            userName1 = "You"
            userName2 = currentBet.betAcceptor.userName
        } else {
            currentBet = friendBets[betCounter]

            userName1 = currentBet.betCreator.userName
            userName2 = currentBet.betAcceptor.userName
        }

        holder.view.item_textview_1.text = "$userName1 bet $userName2:"
        holder.view.item_textview_2.text = "\t" + currentBet?.betText
        holder.view.item_textview_3.text = "$" + "%.2f".format(currentBet?.betAmount)

        betCounter++
    }
}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}