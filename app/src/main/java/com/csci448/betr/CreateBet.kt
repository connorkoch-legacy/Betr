package com.csci448.betr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.create_bet.*
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class CreateBet: AppCompatActivity() {

    private lateinit var users: MutableList<User>

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

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

        val toolbar: Toolbar = findViewById(R.id.toolbar_friends)
        setSupportActionBar(toolbar)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
    }

    //Opens the sidebar when the menu icon in the toolbar is tapped
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}