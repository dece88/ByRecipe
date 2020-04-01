package com.example.byrecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.byrecipe.Fragment.EditProfilePasswordFragment

class EditUserProfileActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_profile)

        val btnChangePassword: Button = findViewById(R.id.edit_profile_button_changePassword)
        btnChangePassword.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.edit_profile_button_changePassword -> {
                val mFragmentManager = supportFragmentManager
                val mEditProfilePasswordFragment = EditProfilePasswordFragment()
                val fragment = mFragmentManager.findFragmentByTag(EditProfilePasswordFragment::class.java.simpleName)

                if(fragment !is EditProfilePasswordFragment){
                    mFragmentManager.beginTransaction().add(R.id.edit_profile_frame_changePassword, mEditProfilePasswordFragment, EditProfilePasswordFragment::class.java.simpleName).commit()
                    val btnChangePassword: Button = findViewById(R.id.edit_profile_button_changePassword)
                    btnChangePassword.setEnabled(false)
                }
            }
        }
    }
}
