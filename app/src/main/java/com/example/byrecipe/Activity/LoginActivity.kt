package com.example.byrecipe.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.byrecipe.DBHelper.DBHelperUser
import com.example.byrecipe.R

import android.app.AlertDialog
import android.content.DialogInterface
import com.example.byrecipe.Model.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),  View.OnClickListener {
    private lateinit var dbUser: DBHelperUser
    private var listUser:List<User> = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnSignUpActivity: Button = findViewById(R.id.login_button_signup)
        btnSignUpActivity.setOnClickListener(this)

        val btnForgotPasswordActivity: Button = findViewById(R.id.login_button_forgot_password)
        btnForgotPasswordActivity.setOnClickListener(this)

        val btnSignInActivity: Button = findViewById(R.id.login_button_signin)
        btnSignInActivity.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.login_button_signup ->{
                //val btnSignUpActivity: Button = findViewById(R.id.login_button_signup)
                //btnSignUpActivity.setBackgroundResource(R.drawable.rounded_border_button_white)
                val moveToSignUp = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(moveToSignUp)
            }

            R.id.login_button_forgot_password ->{
                val moveToForgotPassword = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                startActivity(moveToForgotPassword)
            }

            R.id.login_button_signin -> {
                var user: User?= null
                dbUser = DBHelperUser(this)
                var cek:Boolean = false
                listUser = dbUser.allUser
                for(data in listUser){
                    if(data.email.equals(login_edit_email.text.toString())){
                        if(data.password.equals(login_edit_password.text.toString())){
                            user = User(data.email, data.password, data.fullname, data.noPhone, data.address, data.gender, data.age, data.image)
                            cek = true
                            break
                        }
                    }
                }

                if(cek){
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Login Succes!")
                    builder.setMessage("Welcome!")
//                    builder.setPositiveButton("Continue"){_, _ ->
//
//                    }
                    builder.setPositiveButton("Continue", DialogInterface.OnClickListener { _, _ ->
                        val moveToHome = Intent(this@LoginActivity, MainActivity::class.java)
                        moveToHome.putExtra(MainActivity.USER, user)
                        startActivity(moveToHome)
                    })
                    builder.show()

                } else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Alert!")
                    builder.setMessage("Your Password or Username is wrong!")
                    builder.show()
                }
            }
        }
    }
}
