package com.example.smartfin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val editTextusername = findViewById<EditText>(R.id.editText_username)
        val editTextpassword = findViewById<EditText>(R.id.editText_password)
        val b_login = findViewById<Button>(R.id.b_login)

        b_login.setOnClickListener {
            val username = editTextusername.text.toString()
            val password = editTextpassword.text.toString()

            if (isValidCredentials(username, password)) {
                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidCredentials(username: String, password: String): Boolean {
        return username.isNotEmpty() && password.isNotEmpty()
    }
}
