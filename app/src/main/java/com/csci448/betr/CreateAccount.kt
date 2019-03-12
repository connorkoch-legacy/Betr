package com.csci448.betr

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

class CreateAccount: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.create_account)
    }
}