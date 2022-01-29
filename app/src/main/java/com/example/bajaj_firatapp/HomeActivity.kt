package com.example.bajaj_firatapp


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class HomeActivity : AppCompatActivity(), View.OnFocusChangeListener {
    lateinit var tvHome:TextView  //declaring
    lateinit var etContact: EditText
    lateinit var etEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //var name = intent.extras?.getString("AT")
        tvHome = findViewById(R.id.tvHome)
        //  tvHome.text = name

        etContact = findViewById(R.id.etContact)
        etEmail = findViewById(R.id.etEmail)

        etContact.setOnFocusChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        storeState();
    }

    private fun storeState() {

        var contact: String = etContact.text.toString()
        var email = etEmail.text.toString()

        var sharedPreferences = getSharedPreferences("home_state_prefs", MODE_PRIVATE)

        var editor = sharedPreferences.edit()

        editor.putString("cont",contact)
        editor.putString("eml",email)

        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        restoreState();
    }

    private fun restoreState() {

        var sharedPreferences = getSharedPreferences("home_state_prefs", MODE_PRIVATE)
        var contact = sharedPreferences.getString("cont","")
        var email = sharedPreferences.getString("eml","")

        etContact.setText(contact)
        etEmail.setText(email)

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

        intent.putExtra("VT", "Vivek Tripathi")
        startActivityForResult(intent,123)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 123){
            tvHome.text = data?.extras?.getString("con")
        }
    }

    override fun onFocusChange(p0: View?, isFocussed: Boolean) {
        if(isFocussed){
            Toast.makeText(this,"focussed",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"lost focus",Toast.LENGTH_SHORT).show()

        }
    }
}