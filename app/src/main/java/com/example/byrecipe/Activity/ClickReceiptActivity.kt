package com.example.byrecipe.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.byrecipe.R
import kotlinx.android.synthetic.main.perrecipe.*

class ClickReceiptActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perrecipe)

        onBackButtonPressed()
    }

    private fun onBackButtonPressed() {
        back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.backimg -> {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
