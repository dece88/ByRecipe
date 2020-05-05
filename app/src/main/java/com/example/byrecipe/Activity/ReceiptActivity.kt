package com.example.byrecipe.Activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.byrecipe.DBHelper.DBHelperRecipe
import com.example.byrecipe.DBHelper.DBHelperUser
import com.example.byrecipe.Model.*
import com.example.byrecipe.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.click_recipe.*
import kotlinx.android.synthetic.main.header_menu.*
import kotlinx.android.synthetic.main.layout_side_menu.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ReceiptActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var adapter: RecipeAdapter
    private var list = ArrayList<Recipe>()
    private lateinit var recipeHelper: DBHelperRecipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.click_recipe)
        onSetNavigationDrawerEvents()
        recipeHelper = DBHelperRecipe(this)

        supportActionBar?.title = "Notes"
        rv_recipe.layoutManager = LinearLayoutManager(this)
        rv_recipe.setHasFixedSize(true)
        adapter = RecipeAdapter(this)
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
        iv_logout.setOnClickListener(this)
        tv_logout.setOnClickListener(this)
        recipe_plus.setOnClickListener {
            val intent = Intent(this@ReceiptActivity, FormRecipeActivity::class.java)
            startActivityForResult(intent, FormRecipeActivity.REQUEST_ADD)
        }
    }

    private fun loadRecipesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val recipes = recipeHelper.allRecipe

            if (recipes.size > 0) {
                adapter.listRecipes = recipes as ArrayList<Recipe>
            } else {
                adapter.listRecipes = ArrayList()
                showSnackbarMessage("Tidak ada data saat ini")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            when (requestCode) {
                FormRecipeActivity.REQUEST_ADD -> if (resultCode == FormRecipeActivity.RESULT_ADD) {
                    val recipe = data.getParcelableExtra<Recipe>(FormRecipeActivity.EXTRA_NOTE)
                    adapter.addItem(recipe)
                    rv_recipe.smoothScrollToPosition(adapter.itemCount - 1)
                    showSnackbarMessage("Satu item berhasil ditambahkan")
                }
                FormRecipeActivity.REQUEST_UPDATE -> when (resultCode) {
                    FormRecipeActivity.RESULT_UPDATE -> {
                        val recipe = data.getParcelableExtra<Recipe>(FormRecipeActivity.EXTRA_NOTE)
                        val position = data.getIntExtra(FormRecipeActivity.EXTRA_POSITION, 0)

                        adapter.updateItem(position, recipe)
                        rv_recipe.smoothScrollToPosition(position)
                        showSnackbarMessage("Satu item berhasil diubah")
                    }
                    FormRecipeActivity.RESULT_DELETE -> {
                        val position = data.getIntExtra(FormRecipeActivity.EXTRA_POSITION, 0)
                        adapter.removeItem(position)
                        showSnackbarMessage("Satu item berhasil dihapus")
                    }
                }
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
                val moveToHome = Intent(this@ReceiptActivity,MainActivity::class.java)
                startActivity(moveToHome)
                finish()
            }
            R.id.ll_Second -> {
                showToast("Books")
                drawerLayout.closeDrawer(navigationView, true)
                val moveToRecipe = Intent(this@ReceiptActivity, ReceiptActivity::class.java)
                startActivity(moveToRecipe)
            }
            R.id.ll_Third -> {
                showToast("About")
                drawerLayout.closeDrawer(navigationView, true)
            }
            R.id.ll_Fourth -> {
                showToast("Contact")
                drawerLayout.closeDrawer(navigationView, true)
                val moveToContact = Intent(this@ReceiptActivity, ContactActivity::class.java)
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
            R.id.recipe_plus -> {
                val moveToAddRecipe = Intent(this@ReceiptActivity, FormRecipeActivity::class.java)
                startActivity(moveToAddRecipe)
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

}