package com.example.smartfin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.loginButton)
        val signupButton = findViewById<Button>(R.id.signupButton)
        loginButton.setOnClickListener {
            val intent = Intent(this, Login:: class.java)
            startActivity(intent)
            finish()
        }
        signupButton.setOnClickListener {
            val intent =Intent(this, Register:: class.java)
            startActivity(intent)
            finish()

        }
    }
}