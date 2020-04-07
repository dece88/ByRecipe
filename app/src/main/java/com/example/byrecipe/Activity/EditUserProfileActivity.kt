package com.example.byrecipe.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.byrecipe.Fragment.EditProfilePasswordFragment
import com.example.byrecipe.Model.User
import com.example.byrecipe.R
import kotlinx.android.synthetic.main.activity_edit_user_profile.*

class EditUserProfileActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var user: User

    companion object{
        const val USER = "session user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_profile)

        setUserProfile() //function to get and set session user

        //Set of button
        val btnChangePassword: Button = findViewById(R.id.edit_profile_button_changePassword)
        btnChangePassword.setOnClickListener(this)
    }

    fun setUserProfile(){
        user = intent.getParcelableExtra(USER) as User //get session User2
        edit_profile_edit_fullname.setText(user.fullname)
        edit_profile_edit_nophone.setText(user.noPhone)
        edit_profile_edit_address.setText(user.address)
        edit_profile_edit_gender.setText(user.gender)
        edit_profile_edit_age.setText(user.age.toString())
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.edit_profile_button_changePassword -> {
                val mFragmentManager = supportFragmentManager
                val mEditProfilePasswordFragment = EditProfilePasswordFragment()
                val fragment = mFragmentManager.findFragmentByTag(EditProfilePasswordFragment::class.java.simpleName)

                if(fragment !is EditProfilePasswordFragment){
                    mFragmentManager.beginTransaction().add(R.id.edit_profile_frame_changePassword, mEditProfilePasswordFragment, EditProfilePasswordFragment::class.java.simpleName).commit()
                } else {
                    mFragmentManager.beginTransaction().replace(R.id.edit_profile_frame_changePassword, mEditProfilePasswordFragment, EditProfilePasswordFragment::class.java.simpleName).commit()
                }
            }
        }
    }
}
