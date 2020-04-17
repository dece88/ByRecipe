package com.example.byrecipe.Activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.byrecipe.DBHelper.DBHelperUser
import com.example.byrecipe.Model.User
import com.example.byrecipe.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var user: User? = null //session user
    private lateinit var dbUser: DBHelperUser
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    companion object{
        const val USER = "session user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set of button
        val btnLoginActivity: Button = findViewById(R.id.button_login)
        btnLoginActivity.setOnClickListener(this)

        val btnProfileActivity: Button = findViewById(R.id.button_myProfile)
        btnProfileActivity.setOnClickListener(this)

        val btnLogout: Button = findViewById(R.id.button_logout)
        btnLogout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button_login -> {
                val moveToLogin = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(moveToLogin)
            }

            R.id.button_myProfile -> {
                if(intent.getParcelableExtra<User>(USER) != null){
                    dbUser = DBHelperUser(this)
                    user = dbUser.addUserSetFirst(intent.getParcelableExtra(USER) as User) //get session User
                    val moveToProfile = Intent(this@MainActivity, UserProfileActivity::class.java)
                    moveToProfile.putExtra(UserProfileActivity.USER, user)
                    startActivity(moveToProfile)
                } else {
                    val moveToLogin = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(moveToLogin)
                }
            }

            R.id.button_logout -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Log Out!")
                builder.setMessage("Are you sure want to Log Out?")
                builder.setPositiveButton("Log Out", DialogInterface.OnClickListener { _, _ ->
                    val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
                    mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
                    mGoogleSignInClient.signOut()
                })
                builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
                    //
                })
                builder.show()
            }
        }
    }

    override fun onStart() {
        setHardcodeData()
        super.onStart()
    }

    fun setHardcodeData(){
        dbUser = DBHelperUser(this)
        var data = User("daniel@gmail.com", "123", "Daniel Silombo", "087824122724", "TKI", "Male", 20, "007")
        dbUser.addUserSetFirst(data)
    }
}
