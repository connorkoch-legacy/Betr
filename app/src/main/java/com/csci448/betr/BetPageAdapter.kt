package com.csci448.betr

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.bet_page_recyclerview_listitem.view.*
import java.text.SimpleDateFormat
import java.util.*

class BetPageAdapter(val active: Activity, val bet: MutableList<Bet>, val itemClick: (Array<String>) -> Unit) : RecyclerView.Adapter<MainAdapter.CustomViewHolder>() {

    var betCounter = 0

    companion object {
        private const val LOG_TAG = "TEST"
    }

    override fun getItemCount(): Int {

        return bet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MainAdapter.CustomViewHolder(
            layoutInflater.inflate(
                R.layout.bet_page_recyclerview_listitem,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MainAdapter.CustomViewHolder, position: Int) {

        holder.view.item_textview.text = bet[betCounter].betText

        var current = 0
        var request = "none"



        // Following code is to compare the current date with the end date to see if the bet has expired
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.US)
        val cal = Calendar.getInstance()
        val currentDate = cal.time
        cal.time = sdf.parse(bet[betCounter].dateEnd)
        val endDate = cal.time

        // If statement to categorize the type of bets
        // 0: Ongoing
        // 1: Request
        // 2: Finished Need Vote
        // 3: Old Bets
        if (bet[betCounter].winner != null) { // If the winner field in Bet is set that means this is an old bet
            holder.view.item_imagevie.setImageResource(R.drawable.history_bet)
            current = 3
        } else if (endDate.compareTo(currentDate) <= 0) { // If the current date is past the end date of the bet, this bet has ended, need to vote outcome
            holder.view.item_imagevie.setImageResource(R.drawable.complete_bet)
            current = 2
        } else if (bet[betCounter].accepted == 0) { // If the accept field is 0, that means the bet is a request and needs to be accepted
            holder.view.item_imagevie.setImageResource(R.drawable.request_bet)
            current = 1
        } else if(bet[betCounter].accepted == 1) { // If the accept field is 1, this means it has been accepted and that its an on going bet
            holder.view.item_imagevie.setImageResource(R.drawable.ongoing_bet)
            current = 0
        }

        // I used this to store the index of the bet.
        // I did this because I didn't use callback because we were low on time. This was a quicker fix
        holder.view.temp.text = betCounter.toString()


        holder.view.betpage_recyclerview_listitem.setOnClickListener {
            //Array
            // 1: current (type of bet)
            // 2: bool (yes request change or no)
            // 3: bet index
            var a = arrayOf<String>(current.toString(), request, holder.view.temp.text.toString())
            itemClick(a)
        }

        betCounter++

    }
}
