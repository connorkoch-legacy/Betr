package com.csci448.betr

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    companion object {
        //Creates an intent for the MainActivity to be returned to the calling activity or fragment
        fun newIntent(context: Context?): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Sets up the recycler view
        main_recyclerview.layoutManager = LinearLayoutManager(this)
        //TEST USER --------------------------------------------------
        val tempUser = User("Test User", "pass", null, null)
        val tempFriend = User("Friend 1", "pass1", null, null)
        val tempFriend2 = User("Friend 2", "pass2", null, null)
        var userFriendList = mutableListOf<User>(tempFriend, tempFriend2)
        var friendFriendList = mutableListOf<User>(tempFriend2)
        tempUser.friendList = userFriendList
        tempFriend.friendList = friendFriendList

        var userBet = Bet("I bet that the moon is cheese", tempUser, tempFriend, 100.00)
        var friendBet = Bet("I bet that dogs are bigger than cats", tempFriend, tempFriend2, 17.8932)
        var userBetList = mutableListOf<Bet>(userBet, friendBet)
        var friendBetList = mutableListOf<Bet>(friendBet)
        tempUser.betList = userBetList
        tempFriend.betList = userBetList
        tempFriend2.betList = friendBetList
        //END TEST USER --------------------------------------------------
        main_recyclerview.adapter = MainAdapter(tempUser, if(me_toggle_button.isChecked) 1 else 0)


        //this creates the custom toolbar with the drawer icon and ongoing bets icon
        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        ongoing_bets_toolbar_button.setOnClickListener{
            //create ongoingbets intent
            var intent = BetPage.newIntent(this)
            //put extras
            startActivity(intent)
        }

        //Listener for sidebar menu items
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.sidebar_account -> {
                    var intent: Intent = AccountPage.newIntent(this)
                    startActivity(intent)
                }
                R.id.sidebar_make_bet -> {
                    var intent: Intent = FindFriends.newIntent(this)
                    startActivity(intent)
                }
                else -> {

                }
            }
            true
        }

        fab.setOnClickListener {
            var intent = Intent(this, FindFriends::class.java)
            startActivity(intent)
        }

        me_toggle_button.setOnClickListener {
            if(!me_toggle_button.isChecked) main_recyclerview.adapter = MainAdapter(tempUser, 0)


            me_toggle_button.isChecked = false
            friends_toggle_button.isChecked = true
        }

        friends_toggle_button.setOnClickListener {
            if(!friends_toggle_button.isChecked) main_recyclerview.adapter = MainAdapter(tempUser, 1)

            friends_toggle_button.isChecked = false
            me_toggle_button.isChecked = true
        }

    }

    //Opens the sidebar when the menu icon in the toolbar is tapped
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

}
