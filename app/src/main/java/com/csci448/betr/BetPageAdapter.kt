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

        // 0: Ongoing
        // 1: Request
        // 2: Finished Need Vote
        // 3: Old Bets

        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.US)
        val cal = Calendar.getInstance()

        val currentDate = cal.time

        cal.time = sdf.parse(bet[betCounter].dateEnd)
        val endDate = cal.time

        if (bet[betCounter].winner != null) {
            holder.view.item_imagevie.setImageResource(R.drawable.history_bet)
            current = 3
            //calleee is less than is negative
        } else if (endDate.compareTo(currentDate) <= 0) {
            holder.view.item_imagevie.setImageResource(R.drawable.complete_bet)
            current = 2
        } else if (bet[betCounter].accepted == 0) {
            holder.view.item_imagevie.setImageResource(R.drawable.request_bet)
//            holder.view.accept_button.visibility = View.VISIBLE
//            holder.view.decline_button.visibility = View.VISIBLE
//            holder.view.accept_button.setOnClickListener {
//                Log.d(LOG_TAG, "Accepted lol")
//
//                holder.view.item_imagevie.setImageResource(R.drawable.ongoing_bet)
//                holder.view.accept_button.visibility = View.INVISIBLE
//                holder.view.decline_button.visibility = View.INVISIBLE
//
//                request = "true"
//
//                var texty = holder.view.temp.text.toString()
//                Log.d(LOG_TAG, texty)
//                bet[texty.toInt()].accepted = true
//
//            }
//            holder.view.decline_button.setOnClickListener {
//                Log.d(LOG_TAG, "Declined lol")
//
//                holder.view.item_imagevie.setImageResource(0)
//
//                holder.view.accept_button.visibility = View.INVISIBLE
//                holder.view.decline_button.visibility = View.INVISIBLE
//                holder.view.item_textview.visibility = View.INVISIBLE
//
//                //delete from database
//                request = "false"
//
//            }
            current = 1
        } else if(bet[betCounter].accepted == 1) {
            holder.view.item_imagevie.setImageResource(R.drawable.ongoing_bet)
            current = 0
        }

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
