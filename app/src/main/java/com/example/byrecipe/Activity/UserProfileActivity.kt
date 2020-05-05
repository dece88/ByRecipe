package com.example.byrecipe.Activity

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import android.view.View
import android.webkit.ValueCallback
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.byrecipe.DBHelper.DBHelperUser
import com.example.byrecipe.Model.User
import com.example.byrecipe.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.navigationBar
import kotlinx.android.synthetic.main.click_recipe.*
import kotlinx.android.synthetic.main.header_menu.*
import kotlinx.android.synthetic.main.layout_side_menu.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class UserProfileActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var user: User
    private lateinit var dbUser: DBHelperUser
    lateinit var imageProfile: Uri
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    companion object{
        const val USER = "session user"
        const val PICK_IMAGE: Int = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.click_user_profile)

        onSetNavigationDrawerEvents()

        //Set of button
        val btnChangePicture: Button = findViewById(R.id.user_profile_button_changePicture)
        btnChangePicture.setOnClickListener(this)

        val btnEditProfile: FloatingActionButton = findViewById(R.id.user_profile_button_editProfile)
        btnEditProfile.setOnClickListener(this)
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

    fun setUserProfile(){
        dbUser = DBHelperUser(this)
        user = intent.getParcelableExtra(USER) as User //get session User2
//        dbUser.updateUser(user)
        user_profile_text_name.setText(user.fullname)
        user_profile_text_email.setText(user.email)
        user_profile_text_address.setText(user.address)
        user_profile_text_phone.setText(user.noPhone)
        user_profile_text_gender.setText(user.gender)
        user_profile_text_age.setText(user.age.toString())
        //getImage
        var dataImageProfile: Bitmap? = dbUser.getImageProfile(user) //ambil dari database
        if(dataImageProfile!=null){
            val imageView: ImageView = findViewById(R.id.profile_image)
            imageView.setImageBitmap(dataImageProfile) //diset
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.user_profile_button_editProfile -> {
                val moveToEditProfile = Intent(this@UserProfileActivity, EditUserProfileActivity::class.java)
                moveToEditProfile.putExtra(EditUserProfileActivity.USER, user)
                startActivity(moveToEditProfile)
                finish()
            }

            R.id.user_profile_button_changePicture -> {
                val gallery: Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(gallery, PICK_IMAGE)
            }

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
                val moveToMyRecipe = Intent(this, ReceiptActivity::class.java)
                moveToMyRecipe.putExtra(ReceiptActivity.USER, user)
                startActivity(moveToMyRecipe)
                showToast("ll_Fifth")
                drawerLayout.closeDrawer(navigationView, true)
            }
            R.id.ll_Sixth -> {
                val moveToLogin = Intent(this@UserProfileActivity, LoginActivity::class.java)
                startActivity(moveToLogin)
            }
            R.id.ll_Seventh -> {
                val moveToSignUp = Intent(this@UserProfileActivity, SignUpActivity::class.java)
                startActivity(moveToSignUp)
            }
            R.id.ll_Eigth -> {
                showToast("Account")
                drawerLayout.closeDrawer(navigationView,true)
            }

            R.id.button_logout -> {
                val builder = androidx.appcompat.app.AlertDialog.Builder(this)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if((resultCode == Activity.RESULT_OK) and (requestCode==PICK_IMAGE)){
            imageProfile = data?.getData()!! //get result from gallery
            
            var inputImage: InputStream = this.getContentResolver().openInputStream(imageProfile)!! //buat ubah uri menjadi stream
            var bmImage: Bitmap = BitmapFactory.decodeStream(inputImage) //dari stream diubah menjadi bitmap
            inputImage.close()
            var dataImage: ByteArray = getBitmapAsByteArray(bmImage) //mengubah bitmap menjadi byteArray karena BLOB type nya ByteArray
            dbUser = DBHelperUser(this)
            dbUser.updateImageProfile(user, dataImage) //update di database

            var dataImageProfile: Bitmap? = dbUser.getImageProfile(user) //ambil dari database

            val imageView: ImageView = findViewById(R.id.profile_image)
            imageView.setImageBitmap(dataImageProfile) //diset
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

    private fun showToast(message: String) {
        Toast.makeText(this@UserProfileActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView, true)
        } else {
            super.onBackPressed()
        }
    }

    override fun onStart() {
        setUserProfile() //function to get and set session user
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
