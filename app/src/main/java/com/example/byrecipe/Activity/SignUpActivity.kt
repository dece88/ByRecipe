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
    lateinit var user: User

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

                var cekField: Boolean = true

                //pengecekan setiap field
                if(!register_edit_email.text.isEmpty()){
                    if(!register_edit_nophone.text.isEmpty()){
                        if(!register_edit_address.text.isEmpty()){
                            if(!register_edit_gender.text.isEmpty()){
                                if(!register_edit_fullname.text.isEmpty()){
                                    if(!register_edit_age.text.isEmpty()){
                                        if(!register_edit_password.text.isEmpty()){
                                            if(!register_edit_repassword.text.isEmpty()){
                                                if(register_edit_code.text.isEmpty()){
                                                    dialogFunction("Alert!", "Please fill code!")
                                                    cekField = false
                                                }
                                            } else {
                                                dialogFunction("Alert!", "Please fill repassword!")
                                                cekField = false
                                            }
                                        } else {
                                            dialogFunction("Alert!", "Please flll password!")
                                            cekField = false
                                        }
                                    } else {
                                        dialogFunction("Alert!", "Please flll age!")
                                        cekField = false
                                    }
                                } else {
                                    dialogFunction("Alert!", "Please flll fullname!")
                                    cekField = false
                                }
                            } else {
                                dialogFunction("Alert!", "Please flll gender!")
                                cekField = false
                            }
                        } else {
                            dialogFunction("Alert!", "Please flll address!")
                            cekField = false
                        }
                    } else {
                        dialogFunction("Alert!", "Please flll no phone!")
                        cekField = false
                    }
                } else {
                    dialogFunction("Alert!", "Please fill email!")
                    cekField = false
                }

                //create model user
                user = User(
                    register_edit_email.text.toString(),
                    register_edit_password.text.toString().trim(),
                    register_edit_fullname.text.toString(),
                    register_edit_nophone.text.toString(),
                    register_edit_address.text.toString(),
                    register_edit_gender.text.toString(),
                    if(register_edit_age.text.isEmpty()){ 0 } else {Integer.parseInt(register_edit_age.text.toString())},
                    register_edit_code.text.toString()
                )

                //Pengecekan kebutuhan pengisian
                if(cekField) {
                    if (register_edit_password.text.toString().trim().equals(register_edit_repassword.text.toString().trim())) {
                        if (register_edit_email.text.toString().contains("@gmail.com")) {
                            if (register_edit_gender.text.toString().equals("Male") or register_edit_gender.text.toString().equals("Female")) {
                                var cek: Boolean = true
                                for (data in listUser) {
                                    if (data.email.equals(register_edit_email.text.toString())) {
                                        cek = false
                                        break
                                    }
                                }
                                if (cek) {
                                    val builder = AlertDialog.Builder(this)
                                    builder.setTitle("Register Succes!")
                                    builder.setMessage("Data has been register!").setPositiveButton("Continue", DialogInterface.OnClickListener { _, _ ->
                                            dbUser.addUser(user)
                                            finish()
                                        })
                                    builder.show()

                                } else {
                                    dialogFunction("Alert!", "Email has been used!")
                                }
                            } else {
                                dialogFunction("Alert!", "Wrong Input, Please fill with Male or Female!")
                            }
                        } else {
                            dialogFunction("Alert!", "Email is not correct!")
                        }
                    } else {
                        dialogFunction("Alert!", "Password and Repassword not same!")
                    }
                }
            }
        }
    }

    fun dialogFunction(title: String, message: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }
}
