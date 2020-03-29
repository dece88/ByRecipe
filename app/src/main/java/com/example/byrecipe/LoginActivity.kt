package com.example.byrecipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class LoginActivity : AppCompatActivity(),  View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnSignUpActivity : Button = findViewById(R.id.login_button_signup)
        btnSignUpActivity.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.login_button_signup ->{
                val moveToSignUp = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(moveToSignUp)
            }

        }
    }
}
