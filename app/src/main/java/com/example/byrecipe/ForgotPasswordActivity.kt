package com.example.byrecipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class ForgotPasswordActivity : AppCompatActivity(),  View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val btnBackToLogin: Button = findViewById(R.id.forgot_password_button_backToLogin)
        btnBackToLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.forgot_password_button_backToLogin ->{
                finish()
            }

            R.id.forgot_password_button_confirm ->{

            }
        }
    }
}
