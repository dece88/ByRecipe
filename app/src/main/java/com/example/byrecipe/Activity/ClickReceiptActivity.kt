package com.example.byrecipe.Activity

<<<<<<< HEAD
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
=======
import android.app.Activity
import android.content.Intent
>>>>>>> a847f74eb2fc59f8d918124fc550ad819c4c6685
import android.os.Bundle
import android.view.View
import com.example.byrecipe.R
import kotlinx.android.synthetic.main.perrecipe.*

<<<<<<< HEAD
class ClickReceiptActivity : AppCompatActivity(), View.OnClickListener {
=======
class ClickReceiptActivity : Activity(), View.OnClickListener {
>>>>>>> a847f74eb2fc59f8d918124fc550ad819c4c6685

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perrecipe)

        onBackButtonPressed()
    }

    private fun onBackButtonPressed() {
<<<<<<< HEAD
        back.setOnClickListener(this)
=======
        backimg.setOnClickListener(this)
>>>>>>> a847f74eb2fc59f8d918124fc550ad819c4c6685
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.backimg -> {
<<<<<<< HEAD
                finish()
=======
                val moveback = Intent(this,BooksActivity::class.java)
                startActivity(moveback)
>>>>>>> a847f74eb2fc59f8d918124fc550ad819c4c6685
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
<<<<<<< HEAD
}
=======

}
>>>>>>> a847f74eb2fc59f8d918124fc550ad819c4c6685
