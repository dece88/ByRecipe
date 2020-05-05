package com.example.byrecipe.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.byrecipe.R
import kotlinx.android.synthetic.main.perrecipe.*

class ClickReceiptActivity : Activity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perrecipe)

        onBackButtonPressed()
    }

    private fun onBackButtonPressed() {
        backimg.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.backimg -> {
                val moveback = Intent(this,BooksActivity::class.java)
                startActivity(moveback)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}