package com.example.byrecipe.Activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.os.Bundle
import android.view.View
import com.example.byrecipe.DBHelper.DBHelperRecipe
import com.example.byrecipe.Model.Recipe
import com.example.byrecipe.Model.User
import com.example.byrecipe.R
import kotlinx.android.synthetic.main.perrecipe.*

class ClickReceiptActivity : AppCompatActivity(), View.OnClickListener {
    private var user:User ?= null
    private var recipe: Recipe? = null
    private var position: Int = 0
    private lateinit var recipeHelper: DBHelperRecipe

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val REQUEST_UPDATE = 200
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val USER = "session_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perrecipe)

        user = intent.getParcelableExtra(USER) as User //get session User2

        setRecipe()
        onBackButtonPressed()
    }

    public fun setRecipe(){
        recipe = intent.getParcelableExtra(EXTRA_NOTE) as Recipe
        text_deskripsi.setText(recipe!!.nama)
        text_waktu.setText(recipe!!.waktu)
        ingredients.setText(recipe!!.ingredients)
        keterangan.setText(recipe!!.tahapan)
    }

    private fun onBackButtonPressed() {
        back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.back -> {
                val moveback = Intent(this, ReceiptActivity::class.java)
                moveback.putExtra(BooksActivity.USER, user)
                startActivity(moveback)
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}

