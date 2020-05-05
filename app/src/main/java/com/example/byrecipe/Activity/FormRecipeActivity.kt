package com.example.byrecipe.Activity

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.example.byrecipe.DBHelper.DBHelperRecipe
import com.example.byrecipe.DBHelper.DBHelperUser
import com.example.byrecipe.Model.Recipe
import com.example.byrecipe.Model.User
import com.example.byrecipe.R
import kotlinx.android.synthetic.main.activity_form_recipe.*
import java.io.ByteArrayOutputStream
import java.io.InputStream

class FormRecipeActivity : AppCompatActivity(), View.OnClickListener {
    private var user: User ?= null
    private var isEdit = false
    lateinit var imageProfileRecipe: Uri
    private var recipe: Recipe? = null
    private var position: Int = 0
    private lateinit var recipeHelper: DBHelperRecipe
    private var dataImage: ByteArray ?= null

    companion object {
        const val USER = "session user"
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

        user = intent.getParcelableExtra(USER) as User //get session User2

        if(intent.getParcelableExtra<Recipe>(EXTRA_NOTE) != null){
            recipe = intent.getParcelableExtra(EXTRA_NOTE) as Recipe
        }

        if (recipe != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            recipe = Recipe(0,"", "", "", "", "", "")
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
        form_page_button_uploadImage.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.form_page_button -> {
                recipeHelper = DBHelperRecipe(this)
                val nama = form_page_nama.text.toString().trim()
                val waktu = form_page_time.text.toString().trim()
                var category: String = ""
                if(radio_bakery.isChecked){
                    category = "bakery"
                } else if(radio_steak.isChecked){
                    category = "steak"
                }
                val ingredients = form_page_ingredients.text.toString().trim()
                val tahapan = form_page_tahapan.text.toString().trim()

                if (nama.isEmpty() and waktu.isEmpty() and category.isEmpty() and ingredients.isEmpty() and tahapan.isEmpty()) {
                    return
                }

                recipe = Recipe(0, nama, ingredients, tahapan, waktu, category, user?.email)

                //val moveToReceipt = Intent(this, ReceiptActivity::class.java)
//            moveToReceipt.putExtra(EXTRA_NOTE, recipe)
//            moveToReceipt.putExtra(EXTRA_POSITION, position)

                if (isEdit) {
                    recipeHelper.updateRecipe(recipe!!)
                    Toast.makeText(this@FormRecipeActivity, "Gagal mengupdate data", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    var result = recipeHelper.addRecipe(recipe!!, this.dataImage!!, this.user!!)
                    if (result > 0) {
                        recipe?.id = result.toInt()
                        val moveToReceipt = Intent(this, ReceiptActivity::class.java)
                        moveToReceipt.putExtra(EXTRA_NOTE, recipe)
                        moveToReceipt.putExtra(ReceiptActivity.USER, user)
                        moveToReceipt.putExtra(EXTRA_POSITION, position)
                        startActivity(moveToReceipt)
                        finish()
                    } else {
                        Toast.makeText(this@FormRecipeActivity, "Berhasil menambah data", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            R.id.form_page_button_uploadImage -> {
                val gallery: Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(gallery, UserProfileActivity.PICK_IMAGE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if((resultCode == Activity.RESULT_OK) and (requestCode== UserProfileActivity.PICK_IMAGE)){
            imageProfileRecipe = data?.getData()!! //get result from gallery

            var inputImage: InputStream = this.getContentResolver().openInputStream(imageProfileRecipe)!! //buat ubah uri menjadi stream
            var bmImage: Bitmap = BitmapFactory.decodeStream(inputImage) //dari stream diubah menjadi bitmap
            inputImage.close()
            this.dataImage = getBitmapAsByteArray(bmImage) //mengubah bitmap menjadi byteArray karena BLOB type nya ByteArray

            //dbUser.updateImageProfile(user, dataImage) //update di database

            //var dataImageProfile: Bitmap? = dbUser.getImageProfile(user) //ambil dari database

            val imageView: ImageView = findViewById(R.id.form_page_image)
            imageView.setImageURI(imageProfileRecipe) //diset
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Alert!")
            builder.setMessage("Picture is broken!")
        }
    }

    fun getBitmapAsByteArray(bmImage: Bitmap): ByteArray{
        var outputStream: ByteArrayOutputStream = ByteArrayOutputStream()
        bmImage.compress(Bitmap.CompressFormat.JPEG, 0, outputStream)
        return outputStream.toByteArray()
    }
}
