package com.example.byrecipe.Activity

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.byrecipe.DBHelper.DBHelperUser
import com.example.byrecipe.Fragment.EditProfilePasswordFragment
import com.example.byrecipe.Model.User
import com.example.byrecipe.R
import kotlinx.android.synthetic.main.activity_edit_user_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile_password.*

class EditUserProfileActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var user: User
    private lateinit var dbUser: DBHelperUser

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

        val btnDone: Button = findViewById(R.id.edit_profile_button_done)
        btnDone.setOnClickListener(this)
    }

    fun setUserProfile(){
        user = intent.getParcelableExtra(USER) as User //get session User
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
                    var bundle = Bundle()
                    bundle.putParcelable("dataUser", user)
                    mEditProfilePasswordFragment.setArguments(bundle)
                    mFragmentManager.beginTransaction().add(R.id.edit_profile_frame_changePassword, mEditProfilePasswordFragment, EditProfilePasswordFragment::class.java.simpleName).commit()
                } else {
                    mFragmentManager.beginTransaction().replace(R.id.edit_profile_frame_changePassword, mEditProfilePasswordFragment, EditProfilePasswordFragment::class.java.simpleName).commit()
                }
            }

            R.id.edit_profile_button_done -> {
                user.fullname = edit_profile_edit_fullname.text.toString()
                user.noPhone = edit_profile_edit_nophone.text.toString()
                user.address = edit_profile_edit_address.text.toString()
                user.gender = edit_profile_edit_gender.text.toString()
                user.age = Integer.parseInt(edit_profile_edit_age.text.toString())
                dbUser = DBHelperUser(this)
                dbUser.updateUser(user)
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Change Profile!")
                builder.setMessage("Profile has been changed!")
                builder.setPositiveButton("Continue", DialogInterface.OnClickListener { _, _ ->
                    val moveToUserProfile = Intent (this@EditUserProfileActivity, UserProfileActivity::class.java)
                    moveToUserProfile.putExtra(UserProfileActivity.USER, user)
                    startActivity(moveToUserProfile)
                    finish()
                })
                builder.show()
            }
        }
    }
}
