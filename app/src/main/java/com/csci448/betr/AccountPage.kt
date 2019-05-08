package com.csci448.betr

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.util.Base64
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_account_page.*
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.*
import java.io.ByteArrayOutputStream
import java.util.ArrayList
import java.util.jar.Manifest

class AccountPage : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var currentUser: User
    private lateinit var users: MutableList<User>

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    private val CAMERA_REQUEST_CODE = 1

    companion object {
        //Creates an intent for the OptionsActivity to be returned to the calling activity or fragment
        fun newIntent(context: Context?): Intent {
            return Intent(context, AccountPage::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_page)

        users = intent.getParcelableArrayListExtra<User>("USER_LIST").toMutableList()
        currentUser = intent.getParcelableExtra<User>("LOGGED_IN_USER")

        if(currentUser.profilePic != "") {
            val imageBytes = Base64.decode(currentUser.profilePic, 0)
            val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

          profile_picture.setImageBitmap(image)
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        profile_picture.setOnClickListener{
            val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            if(permission == PackageManager.PERMISSION_GRANTED) {
                val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (callCameraIntent.resolveActivity(packageManager) != null) {
                    startActivityForResult(callCameraIntent, CAMERA_REQUEST_CODE)
                }
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 101)
            }
        }

        change_pass_button.setOnClickListener{
            var intent = Intent(this, ChangePassword::class.java)
            intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
            intent.putExtra("LOGGED_IN_USER", currentUser)
            startActivity(intent)
        }

        friend_list_button.setOnClickListener{
            //create ongoingbets intent
            var intent = FindFriends.newIntent(this)
            //put extras
            startActivity(intent)
        }
    }

    //Opens the sidebar when the menu icon in the toolbar is tapped
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
//                val returnIntent = Intent()
//                returnIntent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
//                returnIntent.putExtra("LOGGED_IN_USER", currentUser)
//                setResult(Activity.RESULT_OK, returnIntent)
                var intent = MainActivity.newIntent(this)
                intent.putParcelableArrayListExtra("USER_LIST", ArrayList(users))
                intent.putExtra("LOGGED_IN_USER", currentUser)
                startActivity(intent)

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CAMERA_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK && data != null){
                //Sets the imageview to the picture just taken
                var picBitmap: Bitmap = data.extras.get("data") as Bitmap
                profile_picture.setImageBitmap(picBitmap)

                //Convert the bitmap to a bytestring so it can be stored in the db
                var baos = ByteArrayOutputStream()
                picBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                var byteString: String = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)

                //stores profile pic in user data
                currentUser.profilePic = byteString
                for(u in users){
                    if(currentUser.username == u.username) u.profilePic = byteString
                }

                //update the user data in the database
                database.child("users").child(currentUser.username).setValue(currentUser)
            }
        }
    }

}

