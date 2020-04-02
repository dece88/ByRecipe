package com.example.byrecipe.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.byrecipe.R

class LoginActivity : AppCompatActivity(),  View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnSignUpActivity: Button = findViewById(R.id.login_button_signup)
        btnSignUpActivity.setOnClickListener(this)

        val btnForgotPasswordActivity: Button = findViewById(R.id.login_button_forgot_password)
        btnForgotPasswordActivity.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.login_button_signup ->{
                val btnSignUpActivity: Button = findViewById(R.id.login_button_signup)
                btnSignUpActivity.setBackgroundResource(R.drawable.rounded_border_button_white)
                val moveToSignUp = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(moveToSignUp)
            }

            R.id.login_button_forgot_password ->{
                val moveToForgotPassword = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                startActivity(moveToForgotPassword)
            }
        }
    }
}
