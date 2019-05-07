package com.csci448.betr

import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.main_recyclerview_listitem.view.*
import java.util.*

class MainAdapter(val user: User, val sortedBets: MutableList<Bet>, var meOrFriend: Int) :
    RecyclerView.Adapter<MainAdapter.CustomViewHolder>() { //meOrFriend = 0, me ; = 1, friend

    class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    private var betCounter = 0

    //Return number of bets to display in the recyclerview, and also populate the orderedBets priority queue
    override fun getItemCount(): Int {
        return sortedBets.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CustomViewHolder(layoutInflater.inflate(R.layout.main_recyclerview_listitem, parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var userName1: String = ""
        var userName2: String = ""
        var currentBet: Bet? = null

        currentBet = sortedBets[position]

        if(meOrFriend == 0) {
            userName1 = "You"
            userName2 = currentBet.betAcceptor
        } else {
            userName1 = currentBet.betCreator
            userName2 = currentBet.betAcceptor
        }

        holder.view.item_textview_1.text = "$userName1 bet $userName2:"
        holder.view.item_textview_2.text = "\t" + currentBet?.betText
        holder.view.item_textview_3.text = "$" + "%.2f".format(currentBet?.betAmount)
        //set the profle pic if it's not null, else it's default
        if(user.profilePic != "") {
            val imageBytes = Base64.decode(user.profilePic, 0)
            val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            holder.view.item_imageview.setImageBitmap(image)
        }

        betCounter++
    }
}
