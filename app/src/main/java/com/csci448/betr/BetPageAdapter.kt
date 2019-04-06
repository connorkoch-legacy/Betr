package com.csci448.betr

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.bet_page_recyclerview_listitem.view.*

class BetPageAdapter(val user: User?) : RecyclerView.Adapter<CustomViewHolder>() {

    var betCounter = 0

    override fun getItemCount(): Int {

        return user?.betList?.size ?: return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CustomViewHolder(layoutInflater.inflate(R.layout.bet_page_recyclerview_listitem, parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.view.item_textview.text = user!!.betList!![betCounter].betText
//        if(betCounter == 0) {
//            holder.view.item_imagevie.setImageDrawable(R.drawable.ongoing_bets_toolbar_button)
//        }else if(betCounter == 2){
//            holder.view.item_imagevie.setImageDrawable(R.drawable.complete_bets_toolbar_button)
//        }else{
//            holder.view.item_imagevie.setImageDrawable(R.drawable.request_bets_toolbar_button)
//            holder.view.accept_button.visibility = View.VISIBLE
//            holder.view.decline_button.visibility = View.VISIBLE
//        }
        betCounter++
    }
}