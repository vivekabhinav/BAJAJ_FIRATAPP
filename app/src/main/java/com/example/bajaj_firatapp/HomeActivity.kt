package com.example.bajaj_firatapp


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        var name = intent.extras?.getString("VT")
        var tvHome = findViewById<TextView>(R.id.tvHome)
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
        var intent: Intent
        intent = Intent(this, MainActivity::class.java)
        intent.putExtra("AT", "Abhinav Tripathi")
        startActivity(intent)
    }
}