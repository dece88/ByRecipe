package com.example.byrecipe.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.byrecipe.DBHelper.DBHelperUser
import com.example.byrecipe.Fragment.EditProfilePasswordFragment
import com.example.byrecipe.Fragment.ForgotPasswordFragment
import com.example.byrecipe.Model.User
import com.example.byrecipe.R
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity(),  View.OnClickListener {
    private lateinit var dbUser: DBHelperUser
    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val btnBackToLogin: Button = findViewById(R.id.forgot_password_button_confirm)
        btnBackToLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.forgot_password_button_confirm ->{
                dbUser = DBHelperUser(this) //pemanggilan DataBase
                var listUser:List<User> = dbUser.allUser
                var builder = AlertDialog.Builder(this)

                if((forgot_password_edit_email.text.isNotEmpty()) and (forgot_password_edit_code.text.isNotEmpty())) {
                    for (data in listUser) {
                        if (data.email.toString().equals(forgot_password_edit_email.text.toString())){
                            if(data.code.toString().equals(forgot_password_edit_code.text.toString())){
                                val mFragmentManager = supportFragmentManager
                                val mForgotPasswordFragment = ForgotPasswordFragment()
                                val fragment = mFragmentManager.findFragmentByTag(ForgotPasswordFragment::class.java.simpleName)
                                user = User(forgot_password_edit_email.text.toString(), "", "", "", "", "", 0, forgot_password_edit_code.text.toString())
                                if(fragment !is ForgotPasswordFragment){
                                    var bundle = Bundle()
                                    bundle.putParcelable("dataUser", user)
                                    mForgotPasswordFragment.setArguments(bundle)
                                    mFragmentManager.beginTransaction().add(R.id.forgot_password_frame_changePassword, mForgotPasswordFragment, ForgotPasswordFragment::class.java.simpleName).commit()
                                } else {
                                    mFragmentManager.beginTransaction().replace(R.id.forgot_password_frame_changePassword, mForgotPasswordFragment, ForgotPasswordFragment::class.java.simpleName).commit()
                                }
                            } else {
                                builder.setTitle("Alert")
                                builder.setMessage("Your code is wrong!")
                                builder.show()
                            }
                        }
                    }
                } else {
                    builder.setTitle("Alert")
                    builder.setMessage("Please fill the blank!")
                    builder.show()
                }
            }
        }
    }
}
