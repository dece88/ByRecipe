package com.example.byrecipe.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.byrecipe.Model.User
import com.example.byrecipe.R

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var user: User? = null //session user

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
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button_login -> {
                val moveToLogin = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(moveToLogin)
            }

            R.id.button_myProfile -> {
                if(intent.getParcelableExtra<User>(USER) != null){
                    user = intent.getParcelableExtra(USER) as User //get session User
                    val moveToProfile = Intent(this@MainActivity, UserProfileActivity::class.java)
                    moveToProfile.putExtra(UserProfileActivity.USER, user)
                    startActivity(moveToProfile)
                } else {
                    val moveToLogin = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(moveToLogin)
                }
            }
        }
    }
}
