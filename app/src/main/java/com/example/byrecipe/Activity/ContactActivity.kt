package com.example.byrecipe.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.byrecipe.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_menu.*
import kotlinx.android.synthetic.main.layout_dashboard.*
import kotlinx.android.synthetic.main.layout_side_menu.*

class ContactActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        onSetNavigationDrawerEvents()
    }

    private fun onSetNavigationDrawerEvents() {
        navigationBar.setOnClickListener(this)
        ll_First.setOnClickListener(this)
        ll_Second.setOnClickListener(this)
        ll_Third.setOnClickListener(this)
        ll_Fourth.setOnClickListener(this)
        ll_Fifth.setOnClickListener(this)
        ll_Sixth.setOnClickListener(this)
        ll_Seventh.setOnClickListener(this)
        iv_logout.setOnClickListener(this)
        tv_logout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.navigationBar -> {
                drawerLayout.openDrawer(navigationView, true)
            }
            R.id.ll_First -> {
                val moveToHome = Intent(this@ContactActivity, MainActivity::class.java)
                startActivity(moveToHome)
                finish()
            }
            R.id.ll_Second -> {
                showToast("ll_Second")
                drawerLayout.closeDrawer(navigationView, true)
            }
            R.id.ll_Third -> {
                showToast("ll_Third")
                drawerLayout.closeDrawer(navigationView, true)
            }
            R.id.ll_Fourth -> {
                val moveToContact = Intent(this@ContactActivity, ContactActivity::class.java)
                startActivity(moveToContact)
            }
            R.id.ll_Fifth -> {
                showToast("ll_Fifth")
                drawerLayout.closeDrawer(navigationView, true)
            }
            R.id.ll_Sixth -> {
                val moveToLogin = Intent(this@ContactActivity, LoginActivity::class.java)
                startActivity(moveToLogin)
            }
            R.id.ll_Seventh -> {
                val moveToSignUp = Intent(this@ContactActivity, SignUpActivity::class.java)
                startActivity(moveToSignUp)
            }
            else -> {
                showToast("Default")
                drawerLayout.closeDrawer(navigationView, true)
            }

//            R.id.button_myProfile -> {
//                if(intent.getParcelableExtra<User>(USER) != null){
//                    dbUser = DBHelperUser(this)
//                    user = dbUser.addUserSetFirst(intent.getParcelableExtra(USER) as User) //get session User
//                    val moveToProfile = Intent(this@MainActivity, UserProfileActivity::class.java)
//                    moveToProfile.putExtra(UserProfileActivity.USER, user)
//                    startActivity(moveToProfile)
//                } else {
//                    val moveToLogin = Intent(this@MainActivity, LoginActivity::class.java)
//                    startActivity(moveToLogin)
//                }
//            }
//
//
//            R.id.button_logout -> {
//                val builder = AlertDialog.Builder(this)
//                builder.setTitle("Log Out!")
//                builder.setMessage("Are you sure want to Log Out?")
//                builder.setPositiveButton("Log Out", DialogInterface.OnClickListener { _, _ ->
//                    val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
//                    mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
//                    mGoogleSignInClient.signOut()
//                })
//                builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
//                    //
//                })
//                builder.show()
//            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView, true)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}

