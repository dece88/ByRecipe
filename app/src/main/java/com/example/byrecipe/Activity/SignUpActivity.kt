package com.example.byrecipe.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.byrecipe.DBHelper.DBHelper
import com.example.byrecipe.Model.User
import com.example.byrecipe.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    internal lateinit var db:DBHelper
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
                db = DBHelper(this)
                listUser = db.allUser
                val user = User(register_edit_email.text.toString(), register_edit_password.text.toString().trim(), register_edit_fullname.text.toString(), register_edit_nophone.text.toString(), register_edit_address.text.toString(), register_edit_gender.text.toString(), Integer.parseInt(register_edit_age.text.toString()), "")
                if(register_edit_password.text.toString().trim().equals(register_edit_repassword.text.toString().trim())){
                    if(listUser.isEmpty()){
                        db.addUser(user)
                        finish()
                    } else {
                        var cek:Boolean = true
                        for(data in listUser){
                            if(data.email.equals(register_edit_email.text.toString())){
                                cek = false
                                break
                            }
                        }
                        if(cek){
                            db.addUser(user)
                            finish()
                        } else {
                            println("Email yang dipakai sudah terdaftar!")
                        }
                    }
                } else {
                    println("Password dan Repassword tidak sama!")
                }
            }
        }
    }
}
