package com.example.smartfin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Register : AppCompatActivity() {
    private lateinit var dbRegistration: DBRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)
        dbRegistration = DBRegistration(this)
        val usernameEditText = findViewById<EditText>(R.id.editText_username)
        val emailEditText = findViewById<EditText>(R.id.editText_email)
        val passwordEditText = findViewById<EditText>(R.id.editText_password)
        val registerButton = findViewById<Button>(R.id.b_register)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val added = dbRegistration.addUser(username, email, password)
                if (added) {

                    Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()

                    // Clear EditText fields after registration

                    usernameEditText.text.clear()
                    emailEditText.text.clear()
                    passwordEditText.text.clear()

                    navigateToDashboard()
                } else {
                    Toast.makeText(this, "Failed to register user", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToDashboard() {
        val intent = Intent(this, Dashboard::class.java)
        startActivity(intent)
        finish()
    }
}

