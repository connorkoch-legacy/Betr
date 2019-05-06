package com.csci448.betr

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.bet_page_recyclerview_listitem.view.*

class BetPageAdapter(val active: Activity, val user: User?, val itemClick: (Int) -> Unit) : RecyclerView.Adapter<MainAdapter.CustomViewHolder>() {

    var betCounter = 0

    override fun getItemCount(): Int {

        return user?.betList?.size ?: return 0
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

        holder.view.item_textview.text = user!!.betList!![betCounter].betText

        var current = 0

        if(betCounter == 0) {
            holder.view.item_imagevie.setImageResource(R.drawable.ongoing_bet)
            current = 1
        }else if(betCounter == 2){
            holder.view.item_imagevie.setImageResource(R.drawable.complete_bet)
            current = 2
        }else{
            holder.view.item_imagevie.setImageResource(R.drawable.request_bet)
            holder.view.accept_button.visibility = View.VISIBLE
            holder.view.decline_button.visibility = View.VISIBLE
            current = 3
        }

        betCounter++

        holder.view.betpage_recyclerview_listitem.setOnClickListener { itemClick(current)}
    }

}
