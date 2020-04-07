package com.example.byrecipe.Activity

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.byrecipe.DBHelper.DBHelperUser
import com.example.byrecipe.Model.User
import com.example.byrecipe.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    internal lateinit var dbUser:DBHelperUser
    internal var listUser:List<User> = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        
        val btnRegister: Button = findViewById(R.id.register_button)
        btnRegister.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.register_button ->{
                dbUser = DBHelperUser(this)
                listUser = dbUser.allUser
                val user = User(register_edit_email.text.toString(), register_edit_password.text.toString().trim(), register_edit_fullname.text.toString(), register_edit_nophone.text.toString(), register_edit_address.text.toString(), register_edit_gender.text.toString(), Integer.parseInt(register_edit_age.text.toString()), "")
                if(register_edit_password.text.toString().trim().equals(register_edit_repassword.text.toString().trim())){
                    if(listUser.isEmpty()){
                        dbUser.addUser(user)
                        finish()
                    } else {
                        if(register_edit_email.text.toString().contains("@gmail.com")){
                            if(register_edit_gender.text.toString().equals("Male") or register_edit_gender.text.toString().equals("Female")){
                                var cek:Boolean = true
                                for(data in listUser){
                                    if(data.email.equals(register_edit_email.text.toString())){
                                        cek = false
                                        break
                                    }
                                }
                                if(cek){
                                    val builder = AlertDialog.Builder(this)
                                    builder.setTitle("Register Succes!")
                                    builder.setMessage("Data sudah berhasil di register!").setPositiveButton("Continue", DialogInterface.OnClickListener { dialog, which ->
                                        dbUser.addUser(user)
                                        finish()
                                    })
                                    builder.show()

                                } else {
                                    val builder = AlertDialog.Builder(this)
                                    builder.setTitle("Alert!")
                                    builder.setMessage("Email yang dipakai sudah terdaftar!")
                                    builder.show()
                                }
                            } else {
                                val builder = AlertDialog.Builder(this)
                                builder.setTitle("Alert!")
                                builder.setMessage("Gender salah input, isi dengan Male or Female!")
                                builder.show()
                            }
                        } else {
                            val builder = AlertDialog.Builder(this)
                            builder.setTitle("Alert!")
                            builder.setMessage("Email tidak sesuai dengan syaratnya!")
                            builder.show()
                        }
                    }
                } else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Alert!")
                    builder.setMessage("Password dan Repassword tidak sama!")
                    builder.show()
                }
            }
        }
    }
}
