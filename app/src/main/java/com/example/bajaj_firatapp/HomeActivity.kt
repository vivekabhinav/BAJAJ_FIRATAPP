package com.example.bajaj_firatapp


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    lateinit var tvHome:TextView  //declaring

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var name = intent.extras?.getString("AT")
        tvHome = findViewById(R.id.tvHome) //initializing
        tvHome.text = name
    }

    fun handleClick(view: android.view.View) {
        when(view.id){
            R.id.btnSend -> {  startDialer() }
            R.id.btnMain -> {startMain() }
        }
    }

    private fun startDialer() {
        var dialIntent: Intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:21345678"))
        startActivity(dialIntent)
    }

    private fun startMain() {
        var intent: Intent    //kotlin says you can't have a variable as null
        intent = Intent(this, MainActivity::class.java)  //no need to give the word new
        intent.putExtra("VT", "Vivek Tripathi")
        startActivityForResult(intent,123)
    }


    //result will arrive here -- photo, contact
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 123){ //RESULT_OK means its good to consume, 123 -- data being returned is of type contact
            tvHome.text = data?.extras?.getString("con")
        }
    }
}