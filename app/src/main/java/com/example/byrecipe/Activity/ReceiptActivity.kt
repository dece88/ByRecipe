package com.example.byrecipe.Activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.byrecipe.Adapter.RecipeAdapter
import com.example.byrecipe.DBHelper.DBHelperRecipe
import com.example.byrecipe.Model.*
import com.example.byrecipe.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.activity_recipe.navigationBar
import kotlinx.android.synthetic.main.click_recipe.drawerLayout
import kotlinx.android.synthetic.main.click_recipe.navigationView
import kotlinx.android.synthetic.main.header_menu.*
import kotlinx.android.synthetic.main.layout_side_menu.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReceiptActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var user: User
    private lateinit var adapter: RecipeAdapter
    private lateinit var recipeHelper: DBHelperRecipe
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    companion object{
        const val USER = "session user"
        const val EXTRA_NOTE = "extra_note"
        const val STATUS = "status_recipe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.click_recipe)
        onSetNavigationDrawerEvents()

        user = intent.getParcelableExtra(USER) as User //get session User2
        recipeHelper = DBHelperRecipe(this)

        supportActionBar?.title = "Recipes"
        rv_recipe.layoutManager = LinearLayoutManager(this)
        rv_recipe.setHasFixedSize(true)
        adapter = RecipeAdapter(this)
        adapter.user = this.user
        rv_recipe.adapter = adapter

        // proses ambil data
        loadRecipesAsync()
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
        recipe_plus.setOnClickListener {
            val intent = Intent(this@ReceiptActivity, FormRecipeActivity::class.java)
            intent.putExtra(FormRecipeActivity.USER, user)
            startActivity(intent)
        }
    }

    private fun loadRecipesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            var recipes: ArrayList<Recipe>
            if(intent.getStringExtra(STATUS) == "alacarte"){
                kata.setText("Ala Carte")
                recipes = recipeHelper.allRecipe
            } else if(intent.getStringExtra(STATUS) == "bakery"){
                kata.setText("Bakery")
                recipes = recipeHelper.getRecipeByCategory(intent.getStringExtra(STATUS).toString())
            } else if (intent.getStringExtra(STATUS) == "steak"){
                kata.setText("Steak")
                recipes = recipeHelper.getRecipeByCategory(intent.getStringExtra(STATUS).toString())
            } else {
                kata.setText("My Recipe")
                recipes = recipeHelper.getAllUserRecipe(user)
            }


            if (recipes.size > 0) {
                adapter.listRecipes = recipes as ArrayList<Recipe>
            } else {
                adapter.listRecipes = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(rv_recipe, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.navigationBar -> {
                drawerLayout.openDrawer(navigationView, true)
            }
            R.id.ll_First -> {
                showToast("Home")
                drawerLayout.closeDrawer(navigationView, true)
                val moveToMain = Intent(this, MainActivity::class.java)
                moveToMain.putExtra(MainActivity.USER, user)
                startActivity(moveToMain)
                finish()
            }
            R.id.ll_Second -> {
                showToast("Books")
                drawerLayout.closeDrawer(navigationView, true)
                val moveToBooks = Intent(this, BooksActivity::class.java)
                moveToBooks.putExtra(BooksActivity.USER, user)
                startActivity(moveToBooks)
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
            }
            R.id.ll_Sixth -> {
                val moveToLogin = Intent(this@ReceiptActivity, LoginActivity::class.java)
                startActivity(moveToLogin)
            }
            R.id.ll_Seventh -> {
                val moveToSignUp = Intent(this@ReceiptActivity, SignUpActivity::class.java)
                startActivity(moveToSignUp)
            }
            R.id.ll_Eigth -> {
                if(intent.getParcelableExtra<User>(MainActivity.USER) != null){
                    val moveToProfile = Intent(this, UserProfileActivity::class.java)
                    moveToProfile.putExtra(UserProfileActivity.USER, user)
                    startActivity(moveToProfile)
                } else {
                    val moveToLogin = Intent(this, LoginActivity::class.java)
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
                    val moveToLogin = Intent(this, MainActivity::class.java)
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
        Toast.makeText(this@ReceiptActivity, message, Toast.LENGTH_SHORT).show()
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
        recipeHelper.close()
    }

    override fun onStart() {
        if(intent.getParcelableExtra<Recipe>(FormRecipeActivity.EXTRA_NOTE) != null){
            val recipe = intent.getParcelableExtra<Recipe>(FormRecipeActivity.EXTRA_NOTE) as Recipe
            adapter.addItem(recipe)
            rv_recipe.smoothScrollToPosition(adapter.itemCount - 1)
            showSnackbarMessage("Satu item berhasil ditambahkan")
            intent.putExtra(EXTRA_NOTE, "kosong")
        }

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