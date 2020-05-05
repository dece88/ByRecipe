package com.example.byrecipe.Activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.byrecipe.Model.User
import com.example.byrecipe.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_menu.*
import kotlinx.android.synthetic.main.layout_dashboard.*
import kotlinx.android.synthetic.main.layout_side_menu.*


class BooksActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var user: User
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    companion object{
        const val USER = "session user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.click_books)

        val ALaCarteBtn = findViewById<Button>(R.id.alacarteBtn)
        ALaCarteBtn.setOnClickListener{
            val intent = Intent(this, ReceiptActivity::class.java)
            intent.putExtra(ReceiptActivity.USER, user)
            intent.putExtra(ReceiptActivity.STATUS, "alacarte")
            startActivity(intent)
        }

        val bakeryBtn = findViewById<Button>(R.id.bakeryBtn)
        bakeryBtn.setOnClickListener{
            val intent = Intent(this, ReceiptActivity::class.java)
            intent.putExtra(ReceiptActivity.USER, user)
            intent.putExtra(ReceiptActivity.STATUS, "bakery")
            startActivity(intent)
        }

        val steakBtn = findViewById<Button>(R.id.steakBtn)
        steakBtn.setOnClickListener{
            val intent = Intent(this, ReceiptActivity::class.java)
            intent.putExtra(ReceiptActivity.USER, user)
            intent.putExtra(ReceiptActivity.STATUS, "steak")
            startActivity(intent)
        }

        onSetNavigationDrawerEvents()
    }

    private fun onSetNavigationDrawerEvents() {
        user = intent.getParcelableExtra(USER) as User //get session User2
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
                val moveToMain = Intent(this, MainActivity::class.java)
                moveToMain.putExtra(MainActivity.USER, user)
                startActivity(moveToMain)
                finish()
                showToast("Home")
                drawerLayout.closeDrawer(navigationView, true)
            }
            R.id.ll_Second -> {
                showToast("Books")
                drawerLayout.closeDrawer(navigationView, true)
            }
            R.id.ll_Third -> {
                showToast("About")
                drawerLayout.closeDrawer(navigationView, true)
                val moveToAbout = Intent(this, AboutUsActivity::class.java)
                moveToAbout.putExtra(AboutUsActivity.USER, user)
                startActivity(moveToAbout)
            }
            R.id.ll_Fourth -> {
                showToast("Contact")
                drawerLayout.closeDrawer(navigationView, true)
                val moveToContact = Intent(this, ContactActivity::class.java)
                moveToContact.putExtra(ContactActivity.USER, user)
                startActivity(moveToContact)
            }
            R.id.ll_Fifth -> {
                showToast("ll_Fifth")
                drawerLayout.closeDrawer(navigationView, true)
                val moveToMyRecipe = Intent(this, ReceiptActivity::class.java)
                moveToMyRecipe.putExtra(ReceiptActivity.USER, user)
                startActivity(moveToMyRecipe)
            }
            R.id.ll_Sixth -> {
                val moveToLogin = Intent(this, LoginActivity::class.java)
                startActivity(moveToLogin)
            }
            R.id.ll_Seventh -> {
                val moveToSignUp = Intent(this, SignUpActivity::class.java)
                startActivity(moveToSignUp)
            }

            R.id.ll_Eigth -> {
                val moveToProfile = Intent(this, UserProfileActivity::class.java)
                moveToProfile.putExtra(UserProfileActivity.USER, user)
                startActivity(moveToProfile)
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
                    val moveToLogin = Intent(this@BooksActivity, MainActivity::class.java)
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
        if(intent.getParcelableExtra<User>(MainActivity.USER) == null){
            button_logout.setVisibility(View.GONE)
        } else {
            button_logout.setVisibility(View.VISIBLE)
            ll_Seventh.setVisibility(View.GONE)
            ll_Sixth.setVisibility(View.GONE)
        }
        super.onStart()
    }

}