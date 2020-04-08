package com.example.byrecipe.Fragment


import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.byrecipe.Activity.EditUserProfileActivity
import com.example.byrecipe.Activity.UserProfileActivity
import com.example.byrecipe.DBHelper.DBHelperUser
import com.example.byrecipe.Model.User

import com.example.byrecipe.R
import kotlinx.android.synthetic.main.fragment_edit_profile_password.*

/**
 * A simple [Fragment] subclass.
 */
class EditProfilePasswordFragment : Fragment(), View.OnClickListener {

    lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = getArguments()?.getParcelable<User>("dataUser")!!

        val btnChangePassword: Button = view.findViewById(R.id.edit_profile_fragment_button_changePassword)
        btnChangePassword.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.edit_profile_fragment_button_changePassword -> {
                //CHANGE PASSWORD
                if(edit_profile_edit_oldPassword.text.toString().equals(user.password)){
                    user.password = edit_profile_edit_newPassword.text.toString()
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Change Password!")
                    builder.setMessage("Password has been changed!")
                    builder.setPositiveButton("Continue", DialogInterface.OnClickListener { _, _ ->
                        val fragment = fragmentManager?.findFragmentByTag(EditProfilePasswordFragment::class.java.simpleName)
                        fragmentManager?.beginTransaction()?.remove(fragment!!)?.commit()
                        val moveToUserProfile = Intent (activity, UserProfileActivity::class.java)
                        moveToUserProfile.putExtra(UserProfileActivity.USER, user)
                        startActivity(moveToUserProfile)
                        getActivity()?.finish()
                    })
                    builder.show()
                } else {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Alert!")
                    builder.setMessage("Your old password is not same!")
                    builder.show()
                }
            }
        }
    }
}
