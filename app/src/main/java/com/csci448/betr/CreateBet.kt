package com.csci448.betr

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.create_bet.*
import android.widget.Toast



class CreateBet: AppCompatActivity() {

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