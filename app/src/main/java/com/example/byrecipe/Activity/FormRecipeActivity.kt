package com.example.byrecipe.Activity

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.byrecipe.DBHelper.DBHelperRecipe
import com.example.byrecipe.Model.Recipe
import com.example.byrecipe.R
import kotlinx.android.synthetic.main.activity_form_recipe.*

class FormRecipeActivity : AppCompatActivity(), View.OnClickListener {
    private var isEdit = false
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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_recipe)

        if(intent.getParcelableExtra<Recipe>(EXTRA_NOTE) != null){
            recipe = intent.getParcelableExtra(EXTRA_NOTE) as Recipe
        }

        if (recipe != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            recipe = Recipe("", "", "", "", "", "")
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = "Ubah"
            btnTitle = "Update"
            recipe?.let {
                form_page_nama.setText(it.nama)
                form_page_time.setText(it.waktu)
                form_page_tahapan.setText(it.tahapan)
                form_page_ingredients.setText(it.ingredients)
                if(it.category == "Steak"){
                    radio_steak.setChecked(true)
                } else if(it.category == "Bakery"){
                    radio_bakery.setChecked(true)
                }
            }
        } else {
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        form_page_button.text = btnTitle
        form_page_button.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view!!.id == R.id.form_page_button) {
            recipeHelper = DBHelperRecipe(this)
            val nama = form_page_nama.text.toString().trim()
            val waktu = form_page_time.text.toString().trim()
            var category: String = ""
            if(radio_bakery.isChecked){
                category = "Bakery"
            } else if(radio_steak.isChecked){
                category = "Steak"
            }
            val ingredients = form_page_ingredients.text.toString().trim()
            val tahapan = form_page_tahapan.text.toString().trim()

            if (nama.isEmpty() and waktu.isEmpty() and category.isEmpty() and ingredients.isEmpty() and tahapan.isEmpty()) {
                return
            }

            recipe = Recipe(nama, ingredients, tahapan, waktu, category, "")

            val intent = Intent()
            intent.putExtra(EXTRA_NOTE, recipe)
            intent.putExtra(EXTRA_POSITION, position)

            if (isEdit) {
                recipeHelper.updateRecipe(recipe!!)
                Toast.makeText(this@FormRecipeActivity, "Gagal mengupdate data", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                recipeHelper.addRecipe(recipe!!)
                Toast.makeText(this@FormRecipeActivity, "Berhasil menambah data", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
