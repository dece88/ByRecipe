package com.example.byrecipe.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.byrecipe.R

class UserProfileActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val btnEditProfile: Button = findViewById(R.id.user_profile_button_editProfile)
        btnEditProfile.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.user_profile_button_editProfile -> {
                val moveToEditProfile = Intent(this@UserProfileActivity, EditUserProfileActivity::class.java)
                startActivity(moveToEditProfile)
            }
        }
    }
}
