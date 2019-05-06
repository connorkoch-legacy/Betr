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
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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

        //Get the grand list of users in db and get current logged in user from the intent from login page
        var users = intent.getParcelableArrayListExtra<User>("USER_LIST").toMutableList()
        var currentUser = intent.getParcelableExtra<User>("LOGGED_IN_USER")
        var currentUserFriends: MutableList<User> = mutableListOf()

        //do some preprocessing to get all users in currentUser's friend list
        for(friendStr in currentUser.friendList){
            for(u in users){
                if(friendStr == u.username) currentUserFriends.add(u)
            }
        }

        //temp bets for testing
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.US)
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, 2)
        var userBet3 = Bet("Should be last", currentUser.username, "Doug", 100.00, sdf.format(cal.time), sdf.format(cal.time))
        cal.add(Calendar.DAY_OF_YEAR, -4)
        var userBet = Bet("I bet that the moon is cheese", currentUser.username, "Doug", 100.00, sdf.format(cal.time), sdf.format(cal.time))
        cal.add(Calendar.DAY_OF_YEAR, -10)
        var userBet2 = Bet("Should be first", currentUser.username, "Doug", 100.00, sdf.format(cal.time), sdf.format(cal.time))

        currentUser.betList = mutableListOf(userBet, userBet2, userBet3)

        //Sets up the recycler view
        main_recyclerview.layoutManager = LinearLayoutManager(this)
        main_recyclerview.adapter = MainAdapter(currentUser, currentUserFriends, if(me_toggle_button.isChecked) 1 else 0)


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
            if(!me_toggle_button.isChecked) main_recyclerview.adapter = MainAdapter(currentUser, currentUserFriends, 0)


            me_toggle_button.isChecked = false
            friends_toggle_button.isChecked = true
        }

        friends_toggle_button.setOnClickListener {
            if(!friends_toggle_button.isChecked) main_recyclerview.adapter = MainAdapter(currentUser, currentUserFriends, 1)

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
