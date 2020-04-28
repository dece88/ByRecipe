package com.example.byrecipe.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.byrecipe.R


class BooksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        val ALaCarteBtn = findViewById<Button>(R.id.alacarteBtn)
        ALaCarteBtn.setOnClickListener{
            val intent = Intent(this, ALaCarte::class.java)
            startActivity(intent)
        }

        val bakeryBtn = findViewById<Button>(R.id.bakeryBtn)
        bakeryBtn.setOnClickListener{
            val intent = Intent(this, BakeryActivity::class.java)
            startActivity(intent)
        }

        val steakBtn = findViewById<Button>(R.id.steakBtn)
        steakBtn.setOnClickListener{
            val intent = Intent(this, BakeryActivity::class.java)
            startActivity(intent)
        }
    }

}
