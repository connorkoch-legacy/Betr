package com.csci448.betr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bet_page.*

class BetPage : AppCompatActivity() {

    companion object {
        //Creates an intent for the OptionsActivity to be returned to the calling activity or fragment
        fun newIntent(context: Context?): Intent {
            return Intent(context, BetPage::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bet_page)

        temp_finished_bet_button.setOnClickListener {
            var intent = Intent(this, FinishedBet::class.java)
            startActivity(intent)
        }

        temp_ongoing_bet_button.setOnClickListener {
            var intent = Intent(this, OngoingBet::class.java)
            startActivity(intent)
        }

        back_button.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}

