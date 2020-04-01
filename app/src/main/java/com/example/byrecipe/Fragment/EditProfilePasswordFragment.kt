package com.example.byrecipe.Fragment


import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.getIntentOld
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.example.byrecipe.EditUserProfileActivity

import com.example.byrecipe.R

/**
 * A simple [Fragment] subclass.
 */
class EditProfilePasswordFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnChangePasswordFragment: Button = view.findViewById(R.id.edit_profile_button_confirm_changePassword)
        btnChangePasswordFragment.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.edit_profile_button_confirm_changePassword -> {
                val mEditProfilePasswordFragment = EditProfilePasswordFragment()
                val mFragmentManager = fragmentManager as FragmentManager
                mFragmentManager.beginTransaction().remove(mEditProfilePasswordFragment).commit()
                getActivity()?.finish()
            }
        }
    }
}
