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
import com.example.byrecipe.DBHelper.DBHelperUser
import com.example.byrecipe.Model.ListResep
import com.example.byrecipe.Model.Resep
import com.example.byrecipe.Model.User
import com.example.byrecipe.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.click_recipe.*
import kotlinx.android.synthetic.main.header_menu.*
import kotlinx.android.synthetic.main.layout_side_menu.*

class ReceiptActivity : AppCompatActivity(), View.OnClickListener{

    private var list = ArrayList<Resep>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        rv_recipe.setHasFixedSize(true)

        list.addAll(getListResep())
        showRecyclerList()
    }

    fun getListResep(): ArrayList<Resep> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.getStringArray(R.array.data_photo)

        val listResep = ArrayList<Resep>()
        for(position in dataName.indices){
            val resep = Resep(
                dataName[position],
                dataDescription[position],
                dataPhoto[position]
            )
            listResep.add(resep)
        }
        return listResep
    }

    private fun showRecyclerList(){
        rv_recipe.layoutManager = LinearLayoutManager(this)
        val listResepAdapter = ListResep(list)
        rv_recipe.adapter = listResepAdapter
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
                drawerLayout.openDrawer(navigationView2, true)
            }
            R.id.ll_First -> {
                showToast("Home")
                drawerLayout.closeDrawer(navigationView2, true)
                val moveToHome = Intent(this@ReceiptActivity,MainActivity::class.java)
                startActivity(moveToHome)
            }
            R.id.ll_Second -> {
                showToast("Books")
                drawerLayout.closeDrawer(navigationView2, true)
                val moveToRecipe = Intent(this@ReceiptActivity, ReceiptActivity::class.java)
                startActivity(moveToRecipe)
            }
            R.id.ll_Third -> {
                showToast("About")
                drawerLayout.closeDrawer(navigationView2, true)
            }
            R.id.ll_Fourth -> {
                showToast("Contact")
                drawerLayout.closeDrawer(navigationView2, true)
                val moveToContact = Intent(this@ReceiptActivity, ContactActivity::class.java)
                startActivity(moveToContact)
            }
            R.id.ll_Fifth -> {
                showToast("ll_Fifth")
                drawerLayout.closeDrawer(navigationView2, true)
            }
            R.id.ll_Sixth -> {
                val moveToLogin = Intent(this@ReceiptActivity, LoginActivity::class.java)
                startActivity(moveToLogin)
            }
            R.id.ll_Seventh -> {
                val moveToSignUp = Intent(this@ReceiptActivity, SignUpActivity::class.java)
                startActivity(moveToSignUp)
            }
            else -> {
                showToast("Default")
                drawerLayout.closeDrawer(navigationView2, true)
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@ReceiptActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView2)) {
            drawerLayout.closeDrawer(navigationView2, true)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}