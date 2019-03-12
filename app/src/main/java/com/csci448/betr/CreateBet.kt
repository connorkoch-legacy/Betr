package com.csci448.betr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.create_bet.*
import android.widget.Toast



class CreateBet: AppCompatActivity() {

    companion object {
        //Creates an intent for the OptionsActivity to be returned to the calling activity or fragment
        fun newIntent(context: Context?): Intent {
            return Intent(context, CreateBet::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_bet)
        selectDateButton.setOnClickListener{
            val toast = Toast.makeText(
                applicationContext,
                "This is where I'm going to add the select date widget",
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }
}