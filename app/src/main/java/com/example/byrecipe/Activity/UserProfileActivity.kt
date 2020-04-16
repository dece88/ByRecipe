package com.example.byrecipe.Activity

import android.app.Activity
import android.app.AlertDialog
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
import com.example.byrecipe.DBHelper.DBHelperUser
import com.example.byrecipe.Model.User
import com.example.byrecipe.R
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class UserProfileActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var user: User
    private lateinit var dbUser: DBHelperUser
    lateinit var imageProfile: Uri

    companion object{
        const val USER = "session user"
        const val PICK_IMAGE: Int = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        setUserProfile() //function to get and set session user

        //Set of button
        val btnChangePicture: Button = findViewById(R.id.user_profile_button_changePicture)
        btnChangePicture.setOnClickListener(this)
        val btnEditProfile: Button = findViewById(R.id.user_profile_button_editProfile)
        btnEditProfile.setOnClickListener(this)
    }

    fun setUserProfile(){
        user = intent.getParcelableExtra(USER) as User //get session User2
        dbUser = DBHelperUser(this)
        dbUser.updateUser(user)
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
            }

            R.id.user_profile_button_changePicture -> {
                val gallery: Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(gallery, PICK_IMAGE)
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

}
