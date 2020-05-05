package com.example.byrecipe.Activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.byrecipe.DBHelper.DBHelperUser
import com.example.byrecipe.Model.User
import com.example.byrecipe.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_menu.*
import kotlinx.android.synthetic.main.layout_dashboard.*
import kotlinx.android.synthetic.main.layout_side_menu.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var user: User? = null //session user
    private lateinit var dbUser: DBHelperUser
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    companion object{
        const val USER = "session user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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
        ll_Eigth.setOnClickListener(this)
        iv_logout.setOnClickListener(this)
        tv_logout.setOnClickListener(this)
        button_logout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.navigationBar -> {
                drawerLayout.openDrawer(navigationView, true)
            }
            R.id.ll_First -> {
                showToast("Home")
                drawerLayout.closeDrawer(navigationView, true)
            }
            R.id.ll_Second -> {
                if(intent.getParcelableExtra<User>(USER) != null){
                    dbUser = DBHelperUser(this)
                    user = dbUser.addUserSetFirst(intent.getParcelableExtra(USER) as User) //get session User
                    val moveToBooks = Intent(this@MainActivity, BooksActivity::class.java)
                    moveToBooks.putExtra(BooksActivity.USER, user)
                    startActivity(moveToBooks)
                } else {
                    val moveToLogin = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(moveToLogin)
                }
                showToast("Books")
                drawerLayout.closeDrawer(navigationView, true)
            }
            R.id.ll_Third -> {
                showToast("About")
                drawerLayout.closeDrawer(navigationView, true)
                val moveToAbout = Intent(this@MainActivity, AboutUsActivity::class.java)
                moveToAbout.putExtra(AboutUsActivity.USER, user)
                startActivity(moveToAbout)
            }
            R.id.ll_Fourth -> {
                showToast("Contact")
                drawerLayout.closeDrawer(navigationView, true)
                val moveToContact = Intent(this@MainActivity, ContactActivity::class.java)
                moveToContact.putExtra(ContactActivity.USER, user)
                startActivity(moveToContact)
            }
            R.id.ll_Fifth -> {
                if(intent.getParcelableExtra<User>(USER) != null){
                    dbUser = DBHelperUser(this)
                    user = dbUser.addUserSetFirst(intent.getParcelableExtra(USER) as User) //get session User
                    val moveToMyRecipe = Intent(this@MainActivity, ReceiptActivity::class.java)
                    moveToMyRecipe.putExtra(ReceiptActivity.USER, user)
                    startActivity(moveToMyRecipe)
                } else {
                    val moveToLogin = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(moveToLogin)
                }
                showToast("My Recipe")
                drawerLayout.closeDrawer(navigationView, true)
            }

            R.id.ll_Sixth -> {
                val moveToLogin = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(moveToLogin)
            }
            R.id.ll_Seventh -> {
                val moveToSignUp = Intent(this@MainActivity, SignUpActivity::class.java)
                startActivity(moveToSignUp)
            }

            R.id.ll_Eigth -> {
                if(intent.getParcelableExtra<User>(USER) != null){
                    dbUser = DBHelperUser(this)
                    user = dbUser.addUserSetFirst(intent.getParcelableExtra(USER) as User) //get session User
                    val moveToProfile = Intent(this@MainActivity, UserProfileActivity::class.java)
                    moveToProfile.putExtra(UserProfileActivity.USER, user)
                    startActivity(moveToProfile)
                } else {
                    val moveToLogin = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(moveToLogin)
                }
                showToast("Account")
                drawerLayout.closeDrawer(navigationView,true)
            }

            R.id.button_logout -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Log Out!")
                builder.setMessage("Are you sure want to Log Out?")
                builder.setPositiveButton("Log Out", DialogInterface.OnClickListener { _, _ ->
                    val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
                    mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
                    mGoogleSignInClient.signOut()
                    val moveToLogin = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(moveToLogin)
                    finish()
                })
                builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
                    //
                })
                builder.show()
            }
            else -> {
                showToast("Default")
                drawerLayout.closeDrawer(navigationView, true)
            }

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

    override fun onStart() {
        setHardcodeData()
        if(intent.getParcelableExtra<User>(USER) == null){
            button_logout.setVisibility(View.GONE)
        } else {
            button_logout.setVisibility(View.VISIBLE)
            ll_Seventh.setVisibility(View.GONE)
            ll_Sixth.setVisibility(View.GONE)
        }
        super.onStart()
    }

    fun setHardcodeData(){
        dbUser = DBHelperUser(this)
        var data = User("daniel@gmail.com", "123", "Daniel Silombo", "087824122724", "TKI", "Male", 20, "007")
        dbUser.addUserSetFirst(data)
    }
}
