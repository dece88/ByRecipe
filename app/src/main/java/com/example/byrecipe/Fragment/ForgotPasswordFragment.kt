package com.example.byrecipe.Fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.byrecipe.Activity.LoginActivity
import com.example.byrecipe.Activity.UserProfileActivity
import com.example.byrecipe.DBHelper.DBHelperUser
import com.example.byrecipe.Model.User

import com.example.byrecipe.R
import kotlinx.android.synthetic.main.fragment_edit_profile_password.*
import kotlinx.android.synthetic.main.fragment_forgot_password.*

/**
 * A simple [Fragment] subclass.
 */
class ForgotPasswordFragment : Fragment(), View.OnClickListener {

    lateinit var user: User
    private lateinit var dbUser: DBHelperUser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = getArguments()?.getParcelable<User>("dataUser")!!

        val btnChangePassword: Button = view.findViewById(R.id.forgot_password_fragment_button_changePassword)
        btnChangePassword.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.forgot_password_fragment_button_changePassword -> {

                if(!forgot_password_edit_newPassword.text.isEmpty()){
                    dbUser = DBHelperUser(this.context!!)
                    dbUser.updatePasswordUser(user.email.toString(), forgot_password_edit_newPassword.text.toString())

                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Change Password!")
                    builder.setMessage("Password has been changed!")
                    builder.setPositiveButton("Continue", DialogInterface.OnClickListener { _, _ ->
                        val fragment = fragmentManager?.findFragmentByTag(ForgotPasswordFragment::class.java.simpleName)
                        fragmentManager?.beginTransaction()?.remove(fragment!!)?.commit()
                        val moveToLogin = Intent (activity, LoginActivity::class.java)
                        startActivity(moveToLogin)
                        getActivity()?.finish()
                    })
                    builder.show()
                } else {
                    var builder = AlertDialog.Builder(context)
                    builder.setTitle("Alert")
                    builder.setMessage("Please fill the blank!")
                    builder.show()
                }
            }
        }
    }

}
