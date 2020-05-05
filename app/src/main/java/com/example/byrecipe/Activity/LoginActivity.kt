package com.example.byrecipe.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.byrecipe.DBHelper.DBHelperUser
import com.example.byrecipe.R

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import com.example.byrecipe.Model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),  View.OnClickListener {
    private lateinit var dbUser: DBHelperUser
    private var listUser:List<User> = ArrayList<User>()
    lateinit var mGoogleSignInClient: GoogleSignInClient
    var RC_SIGN_IN: Int = 0

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //configure sign-in to request the user data
        //basic profile include in DEFAULT_SIGN_IN
        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()

        //build a googlesigninclient with the option specified by gso
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val btnSignUpActivity: Button = findViewById(R.id.login_button_signup)
        btnSignUpActivity.setOnClickListener(this)

        val btnForgotPasswordActivity: Button = findViewById(R.id.login_button_forgot_password)
        btnForgotPasswordActivity.setOnClickListener(this)

        val btnSignInActivity: Button = findViewById(R.id.login_button_signin)
        btnSignInActivity.setOnClickListener(this)

        val btnSignInGoogle: Button = findViewById(R.id.login_button_google)
        btnSignInGoogle.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.login_button_signup ->{
                val moveToSignUp = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(moveToSignUp)
            }

            R.id.login_button_forgot_password ->{
                val moveToForgotPassword = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                startActivity(moveToForgotPassword)
            }

            R.id.login_button_signin -> {
                var user: User?= null
                dbUser = DBHelperUser(this) //pemanggilan DataBase
                var cek:Boolean = false
                listUser = dbUser.allUser
                for(data in listUser){
                    if(data.email.equals(login_edit_email.text.toString())){
                        if(data.password.equals(login_edit_password.text.toString())){
                            user = User(data.email, data.password, data.fullname, data.noPhone, data.address, data.gender, data.age, data.code)
                            cek = true
                            break
                        }
                    }
                }

                if(cek){
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Login Succes!")
                    builder.setMessage("Welcome! " + user?.fullname)
                    builder.setPositiveButton("Continue", DialogInterface.OnClickListener { _, _ ->
                        val moveToHome = Intent(this@LoginActivity, MainActivity::class.java)
                        moveToHome.putExtra(MainActivity.USER, user)
                        startActivity(moveToHome)
                        finish()
                    })
                    builder.show()

                } else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Alert!")
                    builder.setMessage("Your Password or Username is wrong!")
                    builder.show()
                }
            }

            R.id.login_button_google ->{
                val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK){
            var task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>){
        try {
            var account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)!!
            var user = User(account.email.toString(), "", account.displayName.toString(), "", "", "", 0, "")
            dbUser = DBHelperUser(this) //pemanggilan DataBase
            user = dbUser.addUserSetFirst(user)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Login Using Google Succes!")
            builder.setMessage("Welcome! " + account.displayName)
            builder.setPositiveButton("Continue", DialogInterface.OnClickListener { _, _ ->
                val moveToHome = Intent(this@LoginActivity, MainActivity::class.java)
                moveToHome.putExtra(MainActivity.USER, user)
                startActivity(moveToHome)
                finish()
            })
            builder.show()
        } catch (e: ApiException){
            Log.w("Google Sign In Error", "Sign In Result : failed code = " + e.statusCode)
            Toast.makeText(this@LoginActivity, "Failed", Toast.LENGTH_LONG).show()
        }
    }
}
