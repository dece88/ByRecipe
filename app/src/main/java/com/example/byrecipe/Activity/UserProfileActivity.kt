package com.example.byrecipe.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.byrecipe.Model.User
import com.example.byrecipe.R
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var user: User

    companion object{
        const val USER = "session user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        setUserProfile() //function to get and set session user

        //Set of button
        val btnEditProfile: Button = findViewById(R.id.user_profile_button_editProfile)
        btnEditProfile.setOnClickListener(this)
    }

    fun setUserProfile(){
        user = intent.getParcelableExtra(USER) as User //get session User2
        user_profile_text_name.setText(user.fullname)
        user_profile_text_email.setText(user.email)
        user_profile_text_address.setText(user.address)
        user_profile_text_phone.setText(user.noPhone)
        user_profile_text_gender.setText(user.gender)
        user_profile_text_age.setText(user.age.toString())
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.user_profile_button_editProfile -> {
                val moveToEditProfile = Intent(this@UserProfileActivity, EditUserProfileActivity::class.java)
                moveToEditProfile.putExtra(EditUserProfileActivity.USER, user)
                startActivity(moveToEditProfile)
            }
        }
    }
}
